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
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.interpolation.internal.module.DefaultIncludesAndRequirementsResolver;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.internal.util.ReferenceUtils;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class TestPluginDependenciesGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
   private static final String TARGET_PLATFORM_PLUGIN_KEY = Plugin.constructKey("org.eclipse.tycho",
      "target-platform-configuration");
   @Inject
   private BasicConverter basicConverter;

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.PROJECT_RESOURCE_FILTER;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(PluginProject.class);
   }

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates)
   {
      final PluginProject pluginProject = (PluginProject) inputElement;

      final String groupId = basicConverter.getNameSpace(properties);

      if (pluginProject.isTestPlugin())
      {
         final List<Dependency> surefireDependencies = new ArrayList<Dependency>();
         determineTestDependencies(pluginProject, groupId, surefireDependencies);

         if (!surefireDependencies.isEmpty())
         {
            final File pomFile = resolvePomFile(pluginProject);
            final Model model = readMavenModel(pomFile);

            final AbstractModule module = pluginProject.getParent().getParent();
            final Model moduleModel = readMavenModel(resolvePomFile(module));

            Plugin targetConfigPlugin = getTargetConfigurationPlugin(model);
            if (targetConfigPlugin == null)
            {
               final Plugin superPlugin = getTargetConfigurationPlugin(moduleModel);
               if (superPlugin != null)
               {
                  targetConfigPlugin = superPlugin.clone();
                  model.getBuild().getPlugins().add(targetConfigPlugin);
               }
            }

            if (targetConfigPlugin == null)
            {
               targetConfigPlugin = new Plugin();
               targetConfigPlugin.setGroupId("org.eclipse.tycho");
               targetConfigPlugin.setArtifactId("target-platform-configuration");
               targetConfigPlugin.setVersion("${tycho.version}");

               model.getBuild().getPlugins().add(targetConfigPlugin);
            }

            model.getBuild().flushPluginMap();

            addTestDependencies(targetConfigPlugin, surefireDependencies);
            writeMavenModel(pomFile, model);
         }
      }
   }

   private Plugin getTargetConfigurationPlugin(Model model)
   {
      final Build build = model.getBuild();
      return build == null ? null : build.getPluginsAsMap().get(TARGET_PLATFORM_PLUGIN_KEY);
   }

   private void addTestDependencies(Plugin plugin, List<Dependency> surefireDependencies)
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
      addAllUnique(dependencyList, surefireDependencies);

      clearChildren(extraRequirements);

      for (Dependency dependency : dependencyList)
      {
         extraRequirements.addChild(toRequirementNode(dependency));
      }
   }

   private static void determineTestDependencies(PluginProject project, final String groupId,
      final List<Dependency> requirements)
   {
      final FeatureProject testFeature = DefaultIncludesAndRequirementsResolver.findFeatureProjectForPluginsFacet(
         project.getParent(), false);

      if (testFeature != null)
      {
         for (RuledReference requiredFeature : testFeature.getRequiredFeatures())
         {
            Dependency requirement = toRequirement(requiredFeature);
            requirement.setType("eclipse-feature");
            requirements.add(requirement);
         }

         for (RuledReference requiredPlugin : testFeature.getRequiredPlugins())
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

   public static void addAllUnique(List<Dependency> dest, List<Dependency> src)
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
