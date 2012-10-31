/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.internal.util.ReferenceUtils;
import org.sourcepit.common.manifest.osgi.Version;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
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

         addIfNotContained(requirements, dependency);
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
      addFeatureRequirements(requirements, featureProject, pluginProject.getParent());
   }

   private static void addFeatureRequirements(final List<Dependency> requirements, FeatureProject featureProject,
      PluginsFacet pluginsFacet)
   {
      for (FeatureInclude includedFeature : featureProject.getIncludedFeatures())
      {
         Dependency requirement = toRequirement(includedFeature);
         requirement.setType("eclipse-feature");
         addIfNotContained(requirements, requirement);
      }

      for (PluginInclude includedPlugin : featureProject.getIncludedPlugins())
      {
         if (pluginsFacet.resolveReference(includedPlugin) == null)
         {
            Dependency requirement = toRequirement(includedPlugin);
            requirement.setType("eclipse-plugin");
            addIfNotContained(requirements, requirement);
         }
      }

      for (RuledReference requiredFeature : featureProject.getRequiredFeatures())
      {
         Dependency requirement = toRequirement(requiredFeature);
         requirement.setType("eclipse-feature");
         addIfNotContained(requirements, requirement);
      }

      for (RuledReference requiredPlugin : featureProject.getRequiredPlugins())
      {
         final Dependency requirement = toRequirement(requiredPlugin);
         requirement.setType("eclipse-plugin");
         addIfNotContained(requirements, requirement);
      }
   }

   private static void addIfNotContained(final List<Dependency> requirements, Dependency requirement)
   {
      for (Dependency dependency : requirements)
      {
         if (Objects.equal(dependency.getArtifactId(), requirement.getArtifactId()))
         {
            if (Objects.equal(dependency.getType(), requirement.getType()))
            {
               return;
            }   
         }
      }
      requirements.add(requirement);
   }

   private static FeatureProject findIncludingFeature(PluginProject project)
   {
      return DefaultIncludesAndRequirementsResolver.findFeatureProjectForPluginsFacet(project.getParent(), false);
   }

   private static Dependency toRequirement(RuledReference featureReference)
   {
      final Dependency dependency = new Dependency();
      dependency.setArtifactId(featureReference.getId());
      dependency.setVersion(ReferenceUtils.toVersionRange(featureReference.getVersion(),
         featureReference.getVersionMatchRule()).toString());
      return dependency;
   }

   private static Dependency toRequirement(AbstractStrictReference strictReference)
   {
      final Dependency dependency = new Dependency();
      dependency.setArtifactId(strictReference.getId());
      dependency.setVersion(strictReference.getVersion());
      return dependency;
   }

   private boolean isEqual(final List<Dependency> requirements, final List<Dependency> defaultRequirements)
   {
      final Set<String> keys1 = new HashSet<String>();
      for (Dependency dependency : requirements)
      {
         keys1.add(dependency.getManagementKey());
      }

      final Set<String> keys2 = new HashSet<String>();
      for (Dependency dependency : defaultRequirements)
      {
         keys2.add(dependency.getManagementKey());
      }

      return keys1.equals(keys2);
   }
}
