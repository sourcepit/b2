/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.b2.internal.maven.util.TychoXpp3Utils.addExtraRequirements;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.interpolation.internal.module.DefaultIncludesAndRequirementsResolver;
import org.sourcepit.b2.model.interpolation.internal.module.ResolutionContextResolver;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.internal.util.ReferenceUtils;
import org.sourcepit.common.manifest.osgi.Version;
import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.common.collect.SetMultimap;

@Named
public class TargetPlatformConfigurationGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   private static final String TYCHO_VERSION = "${tycho.version}";

   private static final String TARGET_PLATFORM_PLUGIN_ARTIFACT_ID = "target-platform-configuration";

   private static final String TYCHO_GROUP_ID = "org.eclipse.tycho";

   private static final String TARGET_PLATFORM_PLUGIN_KEY = Plugin.constructKey(TYCHO_GROUP_ID,
      TARGET_PLATFORM_PLUGIN_ARTIFACT_ID);

   private final ResolutionContextResolver contextResolver;

   @Inject
   public TargetPlatformConfigurationGenerator(ResolutionContextResolver contextResolver)
   {
      this.contextResolver = contextResolver;
   }

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.PROJECT_RESOURCE_FILTER;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(AbstractModule.class);
      inputTypes.add(PluginProject.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates)
   {
      final AbstractModule module;
      final PluginProject pluginProject;
      if (inputElement instanceof AbstractModule)
      {
         module = (AbstractModule) inputElement;
         pluginProject = null;
      }
      else
      {
         pluginProject = (PluginProject) inputElement;
         module = pluginProject.getParent().getParent();
      }

      generate(module, pluginProject, properties);
   }

   private void generate(AbstractModule module, PluginProject pluginProject, PropertiesSource properties)
   {
      final List<Dependency> requirements = determineRequirements(module, pluginProject, properties);

      if (!requirements.isEmpty())
      {
         final List<Model> hierarchy = new ArrayList<Model>();
         if (pluginProject != null)
         {
            hierarchy.add(readMavenModel(resolvePomFile(pluginProject)));
         }
         hierarchy.add(readMavenModel(resolvePomFile(module)));

         final File pomFile = resolvePomFile(pluginProject == null ? module : pluginProject);
         final Model model = hierarchy.get(0);

         final Plugin targetConfigPlugin = adoptTargetConfigurationPlugin(hierarchy, model);

         addExtraRequirements(targetConfigPlugin, requirements);
         
         writeMavenModel(pomFile, model);
      }
   }

   static Plugin adoptTargetConfigurationPlugin(List<Model> hierarchy, Model target)
   {
      Plugin plugin = null;

      for (Model model : hierarchy)
      {
         plugin = getTargetConfigurationPlugin(model);
         if (plugin != null)
         {
            if (!model.equals(target))
            {
               plugin = plugin.clone();
            }
            break;
         }
      }

      if (plugin == null)
      {
         plugin = new Plugin();
         plugin.setGroupId(TYCHO_GROUP_ID);
         plugin.setArtifactId(TARGET_PLATFORM_PLUGIN_ARTIFACT_ID);
         plugin.setVersion(TYCHO_VERSION);
      }

      Build build = target.getBuild();
      if (build == null)
      {
         build = new Build();
         target.setBuild(build);
      }

      final List<Plugin> plugins = build.getPlugins();
      if (!plugins.contains(plugin))
      {
         plugins.add(plugin);
         target.getBuild().flushPluginMap();
      }

      return plugin;
   }

   private List<Dependency> determineRequirements(AbstractModule module, PluginProject pluginProject,
      PropertiesSource properties)
   {
      final List<Dependency> requirements = new ArrayList<Dependency>();
      if (pluginProject == null)
      {
         addModuleRequirements(requirements, module, false);
      }
      else
      {
         addPluginRequirements(requirements, module, pluginProject, properties);

         final List<Dependency> defaultRequirements = new ArrayList<Dependency>();
         addModuleRequirements(defaultRequirements, module, false);
         if (isEqual(requirements, defaultRequirements))
         {
            requirements.clear();
         }
      }
      return requirements;
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

   private void addPluginRequirements(final List<Dependency> requirements, AbstractModule module,
      PluginProject pluginProject, PropertiesSource properties)
   {
      final FeatureProject featureProject = findIncludingFeature(pluginProject);

      boolean isTestScope = pluginProject.isTestPlugin();
      if (!isTestScope && featureProject != null)
      {
         isTestScope = B2MetadataUtils.isTestFeature(featureProject);
      }

      addModuleRequirements(requirements, module, isTestScope);

      if (featureProject != null)
      {
         addFeatureRequirements(requirements, featureProject);
      }
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

         requirements.add(dependency);
      }
   }

   private static Plugin getTargetConfigurationPlugin(Model model)
   {
      final Build build = model.getBuild();
      return build == null ? null : build.getPluginsAsMap().get(TARGET_PLATFORM_PLUGIN_KEY);
   }

   private static void addFeatureRequirements(final List<Dependency> requirements, FeatureProject featureProject)
   {
      for (RuledReference requiredFeature : featureProject.getRequiredFeatures())
      {
         Dependency requirement = toRequirement(requiredFeature);
         requirement.setType("eclipse-feature");
         requirements.add(requirement);
      }

      for (RuledReference requiredPlugin : featureProject.getRequiredPlugins())
      {
         final Dependency requirement = toRequirement(requiredPlugin);
         requirement.setType("eclipse-plugin");
         requirements.add(requirement);
      }
   }

   private static FeatureProject findIncludingFeature(PluginProject project)
   {
      return DefaultIncludesAndRequirementsResolver.findFeatureProjectForPluginsFacet(project.getParent(), false);
   }

   private static Dependency toRequirement(RuledReference featureReference)
   {
      Dependency dependency = new Dependency();
      dependency.setArtifactId(featureReference.getId());
      dependency.setVersion(ReferenceUtils.toVersionRange(featureReference.getVersion(),
         featureReference.getVersionMatchRule()).toString());
      return dependency;
   }


}
