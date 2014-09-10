/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.internal.generator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.model.Dependency;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.interpolation.internal.module.DefaultIncludesAndRequirementsResolver;
import org.sourcepit.b2.model.interpolation.internal.module.ResolutionContextResolver;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.b2.model.module.internal.util.ReferenceUtils;
import org.sourcepit.common.manifest.osgi.Version;

import com.google.common.base.Objects;
import com.google.common.collect.SetMultimap;

@Named
public class TargetPlatformRequirementsCollector
{
   private final ResolutionContextResolver contextResolver;

   @Inject
   public TargetPlatformRequirementsCollector(ResolutionContextResolver contextResolver)
   {
      this.contextResolver = contextResolver;
   }

   public List<Dependency> collectRequirements(AbstractModule module, boolean isTestScope)
   {
      checkArgument(module != null, "Module may not be null.");

      final List<Dependency> requirements = new ArrayList<Dependency>();
      addModuleRequirements(requirements, module, isTestScope);
      return requirements;
   }

   public List<Dependency> collectRequirements(PluginProject pluginProject)
   {
      checkArgument(pluginProject != null, "Plugin project may not be null.");

      final List<Dependency> requirements = new ArrayList<Dependency>();
      final AbstractModule module = pluginProject.getParent().getParent();
      addPluginRequirements(requirements, module, pluginProject);
      return requirements;
   }

   private void addModuleRequirements(final List<Dependency> requirements, AbstractModule module, boolean isTestScope)
   {
      final SetMultimap<AbstractModule, FeatureProject> scope = contextResolver.resolveResolutionContext(module,
         isTestScope);

      for (FeatureProject featureProject : scope.values())
      {
         Dependency dependency = new Dependency();
         dependency.setArtifactId(featureProject.getId());
         final String version = Version.parse(featureProject.getVersion()).trimQualifier().toString();
         dependency.setVersion(version);
         dependency.setType("eclipse-feature");

         addUnique(requirements, dependency);
      }
   }

   private void addPluginRequirements(final List<Dependency> requirements, AbstractModule module,
      PluginProject pluginProject)
   {
      final FeatureProject featureProject = findIncludingFeature(pluginProject);
      checkState(featureProject != null, "Plug-in '" + pluginProject.getId() + "' is not contained in any feature.");

      boolean isTestScope = pluginProject.isTestPlugin();
      if (!isTestScope && featureProject != null)
      {
         isTestScope = B2MetadataUtils.isTestFeature(featureProject);
      }

      addModuleRequirements(requirements, module, isTestScope);
      addFeatureRequirements(requirements, featureProject);
   }

   private static void addFeatureRequirements(final List<Dependency> requirements, FeatureProject featureProject)
   {
      for (FeatureInclude includedFeature : featureProject.getIncludedFeatures())
      {
         Dependency requirement = toRequirement(includedFeature);
         requirement.setType("eclipse-feature");
         addUnique(requirements, requirement);
      }

      final AbstractModule module = featureProject.getParent().getParent();

      for (PluginInclude includedPlugin : featureProject.getIncludedPlugins())
      {
         if (module.resolveReference(includedPlugin, PluginsFacet.class) == null)
         {
            Dependency requirement = toRequirement(includedPlugin);
            requirement.setType("eclipse-plugin");
            addUnique(requirements, requirement);
         }
      }

      for (RuledReference requiredFeature : featureProject.getRequiredFeatures())
      {
         Dependency requirement = toRequirement(requiredFeature);
         requirement.setType("eclipse-feature");
         addUnique(requirements, requirement);
      }

      for (RuledReference requiredPlugin : featureProject.getRequiredPlugins())
      {
         final Dependency requirement = toRequirement(requiredPlugin);
         requirement.setType("eclipse-plugin");
         addUnique(requirements, requirement);
      }
   }

   private static void addUnique(final List<Dependency> requirements, Dependency requirement)
   {
      for (Dependency dependency : requirements.toArray(new Dependency[requirements.size()]))
      {
         if (Objects.equal(dependency.getArtifactId(), requirement.getArtifactId()))
         {
            if (Objects.equal(dependency.getType(), requirement.getType()))
            {
               requirements.remove(dependency);
            }
         }
      }
      requirements.add(requirement);
   }

   private static FeatureProject findIncludingFeature(PluginProject pluginProject)
   {
      FeatureProject featureProject = DefaultIncludesAndRequirementsResolver.findFeatureProjectForPluginsFacet(
         pluginProject.getParent(), false);
      if (featureProject == null)
      {
         final String featureId = B2MetadataUtils.getBrandedFeature(pluginProject);
         if (featureId != null)
         {
            final StrictReference reference = ModuleModelFactory.eINSTANCE.createStrictReference();
            reference.setId(featureId);

            final FeatureProject brandedFeature = pluginProject.getParent().getParent()
               .resolveReference(reference, FeaturesFacet.class);

            if (brandedFeature != null && isIncluded(brandedFeature, pluginProject))
            {
               featureProject = brandedFeature;
            }
         }
      }
      return featureProject;
   }

   private static boolean isIncluded(final FeatureProject featureProject, PluginProject pluginProject)
   {
      for (PluginInclude pluginInclude : featureProject.getIncludedPlugins())
      {
         if (pluginInclude.isSatisfiableBy(pluginProject))
         {
            return true;
         }
      }
      return false;
   }

   private static Dependency toRequirement(RuledReference featureReference)
   {
      final Dependency dependency = new Dependency();
      dependency.setArtifactId(featureReference.getId());
      dependency.setVersion(ReferenceUtils.toVersionRange(featureReference.getVersion(),
         featureReference.getVersionMatchRule()).toString());
      return dependency;
   }

   static Dependency toRequirement(AbstractStrictReference strictReference)
   {
      final Dependency dependency = new Dependency();
      dependency.setArtifactId(strictReference.getId());
      String version = strictReference.getVersion();
      if (version != null && version.endsWith(".qualifier"))
      {
         version = version.substring(0, version.length() - ".qualifier".length());
      }
      dependency.setVersion(version);
      return dependency;
   }
}
