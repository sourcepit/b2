/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.b2.internal.generator.TychoXpp3Utils.readRequirements;
import static org.sourcepit.b2.internal.generator.TychoXpp3Utils.toRequirementNode;
import static org.sourcepit.common.maven.util.Xpp3Utils.clearChildren;

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
import org.apache.maven.plugin.LegacySupport;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.internal.maven.ModelContext;
import org.sourcepit.b2.internal.maven.ModelContextAdapterFactory;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.interpolation.internal.module.DefaultIncludesAndRequirementsResolver;
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
   private static final String TARGET_PLATFORM_PLUGIN_KEY = Plugin.constructKey("org.eclipse.tycho",
      "target-platform-configuration");

   @Inject
   private BasicConverter basicConverter;

   @Inject
   private LegacySupport buildContext;

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

      final List<Dependency> requirements = new ArrayList<Dependency>();
      if (pluginProject == null)
      {
         addModuleRequirements(requirements, false);
      }
      else
      {
         addPluginRequirements(requirements, pluginProject, properties);

         final List<Dependency> defaultRequirements = new ArrayList<Dependency>();
         addModuleRequirements(defaultRequirements, false);
         if (isEqual(requirements, defaultRequirements))
         {
            requirements.clear();
         }
      }

      if (!requirements.isEmpty())
      {
         final File pomFile = resolvePomFile(inputElement);
         final Model model = readMavenModel(pomFile);

         final Model moduleModel = readMavenModel(resolvePomFile(module));

         Plugin targetConfigPlugin = getTargetConfigurationPlugin(model);
         if (targetConfigPlugin == null)
         {
            final Plugin superPlugin = getTargetConfigurationPlugin(moduleModel);
            if (superPlugin != null)
            {
               targetConfigPlugin = superPlugin.clone();

               Build build = model.getBuild();
               if (build == null)
               {
                  build = new Build();
                  model.setBuild(build);
               }
               build.getPlugins().add(targetConfigPlugin);
            }
         }

         if (targetConfigPlugin == null)
         {
            targetConfigPlugin = new Plugin();
            targetConfigPlugin.setGroupId("org.eclipse.tycho");
            targetConfigPlugin.setArtifactId("target-platform-configuration");
            targetConfigPlugin.setVersion("${tycho.version}");

            Build build = model.getBuild();
            if (build == null)
            {
               build = new Build();
               model.setBuild(build);
            }
            build.getPlugins().add(targetConfigPlugin);
         }

         model.getBuild().flushPluginMap();

         addExtraRequirements(targetConfigPlugin, requirements);
         writeMavenModel(pomFile, model);
      }
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

   private void addPluginRequirements(final List<Dependency> requirements, final PluginProject pluginProject,
      PropertiesSource properties)
   {
      addModuleRequirements(requirements, pluginProject.isTestPlugin());
      final String groupId = basicConverter.getNameSpace(properties);
      determineRequirements(pluginProject, groupId, requirements);
   }

   private void addModuleRequirements(final List<Dependency> requirements, boolean isTestScope)
   {
      final ModelContext modelContext = ModelContextAdapterFactory.get(buildContext.getSession().getCurrentProject());
      final SetMultimap<AbstractModule, FeatureProject> scope = isTestScope
         ? modelContext.getTestScope()
         : modelContext.getMainScope();

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

   private Plugin getTargetConfigurationPlugin(Model model)
   {
      final Build build = model.getBuild();
      return build == null ? null : build.getPluginsAsMap().get(TARGET_PLATFORM_PLUGIN_KEY);
   }

   private void addExtraRequirements(Plugin plugin, List<Dependency> requirements)
   {
      Xpp3Dom configuration = (Xpp3Dom) plugin.getConfiguration();
      if (configuration == null)
      {
         configuration = new Xpp3Dom("configuration");
         plugin.setConfiguration(configuration);
      }

      Xpp3Dom dependencyResolution = configuration.getChild("dependency-resolution");
      if (dependencyResolution == null)
      {
         dependencyResolution = new Xpp3Dom("dependency-resolution");
         configuration.addChild(dependencyResolution);
      }

      Xpp3Dom extraRequirements = dependencyResolution.getChild("extraRequirements");
      if (extraRequirements == null)
      {
         extraRequirements = new Xpp3Dom("extraRequirements");
         dependencyResolution.addChild(extraRequirements);
      }

      final List<Dependency> dependencyList = readRequirements(extraRequirements);
      addAllUnique(dependencyList, requirements);

      clearChildren(extraRequirements);

      for (Dependency dependency : dependencyList)
      {
         extraRequirements.addChild(toRequirementNode(dependency));
      }
   }

   private static void determineRequirements(PluginProject project, final String groupId,
      final List<Dependency> requirements)
   {
      final FeatureProject featureProject = DefaultIncludesAndRequirementsResolver.findFeatureProjectForPluginsFacet(
         project.getParent(), false);

      if (featureProject != null)
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
   }

   private static Dependency toRequirement(RuledReference featureReference)
   {
      Dependency dependency = new Dependency();
      dependency.setArtifactId(featureReference.getId());
      dependency.setVersion(ReferenceUtils.toVersionRange(featureReference.getVersion(),
         featureReference.getVersionMatchRule()).toString());
      return dependency;
   }

   private static void addAllUnique(List<Dependency> dest, List<Dependency> src)
   {
      final Set<String> managementKeys = new HashSet<String>();
      for (Dependency dependency : dest)
      {
         managementKeys.add(dependency.getManagementKey());
      }
      for (Dependency dependency : src)
      {
         if (managementKeys.add(dependency.getManagementKey()))
         {
            dest.add(dependency);
         }
      }
   }
}
