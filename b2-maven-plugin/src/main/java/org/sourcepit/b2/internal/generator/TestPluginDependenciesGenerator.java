/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.sourcepit.common.maven.util.Xpp3Utils.clearChildren;
import static org.sourcepit.common.maven.util.Xpp3Utils.toDependencyNode;

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
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.internal.util.ReferenceUtils;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class TestPluginDependenciesGenerator extends AbstractPomGenerator implements IB2GenerationParticipant
{
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
         final List<Dependency> pomDependencies = new ArrayList<Dependency>();
         final List<Dependency> surefireDependencies = new ArrayList<Dependency>();
         determineTestDependencies(pluginProject, groupId, pomDependencies, surefireDependencies);

         if (!pomDependencies.isEmpty() || !surefireDependencies.isEmpty())
         {
            final File pomFile = resolvePomFile(pluginProject);
            final Model model = readMavenModel(pomFile);
            addTestDependencies(model, pomDependencies, surefireDependencies);
            writeMavenModel(pomFile, model);
         }
      }
   }

   private void addTestDependencies(Model model, List<Dependency> pomDependencies, List<Dependency> surefireDependencies)
   {
      addAllUnique(model.getDependencies(), pomDependencies);

      Build build = model.getBuild();
      if (build != null)
      {
         final String surefireKey = Plugin.constructKey("org.eclipse.tycho", "tycho-surefire-plugin");

         Plugin plugin = build.getPluginsAsMap().get(surefireKey);
         if (plugin != null)
         {
            Xpp3Dom configuration = (Xpp3Dom) plugin.getConfiguration();
            if (configuration == null)
            {
               configuration = new Xpp3Dom("configuration");
               plugin.setConfiguration(configuration);
            }

            Xpp3Dom dependencies = configuration.getChild("dependencies");
            if (dependencies == null)
            {
               dependencies = new Xpp3Dom("dependencies");
               configuration.addChild(dependencies);
            }

            final List<Dependency> dependencyList = readDependencies(dependencies);
            addAllUnique(dependencyList, surefireDependencies);

            clearChildren(dependencies);

            for (Dependency dependency : dependencyList)
            {
               dependencies.addChild(toDependencyNode(dependency));
            }
         }
      }
   }


   private List<Dependency> readDependencies(Xpp3Dom dependencies)
   {
      final List<Dependency> dependencyList = new ArrayList<Dependency>();
      for (Xpp3Dom dependency : dependencies.getChildren("dependency"))
      {
         dependencyList.add(newDependency(dependency));
      }
      return dependencyList;
   }

   private Dependency newDependency(Xpp3Dom dependency)
   {
      final Dependency result = new Dependency();
      result.setGroupId(extractNonEmptyValue(dependency.getChild("groupId")));
      result.setArtifactId(extractNonEmptyValue(dependency.getChild("artifactId")));
      result.setVersion(extractNonEmptyValue(dependency.getChild("version")));
      result.setClassifier(extractNonEmptyValue(dependency.getChild("classifier")));
      final String type = extractNonEmptyValue(dependency.getChild("type"));
      if (type != null)
      {
         result.setType(type);
      }
      result.setSystemPath(extractNonEmptyValue(dependency.getChild("systemPath")));
      result.setScope(extractNonEmptyValue(dependency.getChild("scope")));
      result.setOptional(extractNonEmptyValue(dependency.getChild("optional")));
      return result;
   }

   private String extractNonEmptyValue(Xpp3Dom node)
   {
      String value = node == null ? null : node.getValue();
      if (value != null)
      {
         value.trim();
         if (value.length() == 0)
         {
            value = null;
         }
      }
      return value;
   }


   private static void determineTestDependencies(PluginProject project, final String groupId,
      final List<Dependency> pomDependencies, final List<Dependency> surfireDependencies)
   {
      final FeatureProject testFeature = DefaultIncludesAndRequirementsResolver.findFeatureProjectForPluginsFacet(
         project.getParent(), false);

      final AbstractModule module = project.getParent().getParent();
      if (testFeature != null)
      {
         for (RuledReference requiredFeature : testFeature.getRequiredFeatures())
         {
            Dependency dependency = toDependency(requiredFeature);
            dependency.setType("eclipse-feature");
            surfireDependencies.add(dependency);

            FeatureProject feature = module.resolveReference(requiredFeature, FeaturesFacet.class);
            if (feature != null)
            {
               Dependency d = dependency.clone();
               d.setVersion(VersionUtils.toMavenVersion(feature.getVersion()));
               d.setGroupId(groupId);
               pomDependencies.add(d);
            }
         }

         for (RuledReference requiredPlugin : testFeature.getRequiredPlugins())
         {
            Dependency dependency = toDependency(requiredPlugin);
            dependency.setType("eclipse-plugin");
            surfireDependencies.add(dependency);

            PluginProject plugin = module.resolveReference(requiredPlugin, PluginsFacet.class);
            if (plugin != null)
            {
               Dependency d = dependency.clone();
               d.setVersion(VersionUtils.toMavenVersion(plugin.getVersion()));
               d.setGroupId(groupId);
               if (plugin.isTestPlugin())
               {
                  d.setType("eclipse-test-plugin");
               }
               pomDependencies.add(d);
            }
         }
      }
   }

   private static Dependency toDependency(RuledReference featureReference)
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
