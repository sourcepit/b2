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

package org.sourcepit.b2.directory.parser.internal.facets;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.sourcepit.b2.directory.parser.internal.project.ProjectDetector;
import org.sourcepit.b2.directory.parser.internal.project.ProjectParser;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.module.util.ModuleModelSwitch;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named("simple")
public class SimpleLayoutFacetsParserRule extends AbstractFacetsParserRule<ProjectFacet<? extends Project>> {
   private static final String LAYOUT = SimpleLayoutFacetsParserRule.class.getAnnotation(Named.class).value();

   @Inject
   private ProjectDetector projectDetector;

   @Inject
   private ProjectParser projectParser;

   @Override
   public FacetsParseResult<ProjectFacet<? extends Project>> parse(File directory, PropertiesSource properties) {
      final List<ProjectFacet<? extends Project>> facets = new ArrayList<ProjectFacet<? extends Project>>();
      if (directory == null || !directory.exists()) {
         return null;
      }

      if (projectDetector.detect(directory, properties) != null) {
         return null;
      }

      final List<PluginProject> fragments = new ArrayList<PluginProject>();
      final Map<String, PluginsFacet> bundleSymbolicNameToPluginsFacet = new HashMap<String, PluginsFacet>();
      for (File member : directory.listFiles()) {
         if (member.isDirectory()) {
            final Project project = projectParser.parse(member, properties);
            if (project != null) {
               PluginProject pluginProject = null;

               // store fragments for post processing
               if (project instanceof PluginProject) {
                  pluginProject = (PluginProject) project;
                  if (pluginProject.isFragment()) {
                     fragments.add(pluginProject);
                     continue;
                  }
               }

               ProjectFacet<? extends Project> facet = addProject(facets, project);
               if (facet == null) {
                  // TODO bernd error
               }

               if (pluginProject != null) {
                  bundleSymbolicNameToPluginsFacet.put(pluginProject.getId(), (PluginsFacet) facet);
               }
            }
         }
      }

      if (!fragments.isEmpty()) {

         for (PluginProject fragment : fragments) {
            final PluginsFacet fragmentFacet = discoverTargetFacetForFragment(facets, fragment);
            fragmentFacet.getProjects().add(fragment);
         }
      }

      if (facets.isEmpty()) {
         return null;
      }

      return new FacetsParseResult<ProjectFacet<? extends Project>>(LAYOUT, facets);
   }

   private PluginsFacet discoverTargetFacetForFragment(final List<ProjectFacet<? extends Project>> facets,
      PluginProject fragment) {
      for (ProjectFacet<? extends Project> facet : facets) {
         if (facet instanceof PluginsFacet && facet.getProjectById(fragment.getFragmentHostSymbolicName()) != null) {
            return (PluginsFacet) facet;
         }
      }

      // put fragemnts to other plugnins when their host bundles can not be found
      PluginsFacet mainFacet = null;

      for (ProjectFacet<? extends Project> facet : facets) {
         if (facet instanceof PluginsFacet && "plugins".equals(facet.getName())) {
            mainFacet = (PluginsFacet) facet;
         }
      }

      if (mainFacet == null) {
         mainFacet = ModuleModelFactory.eINSTANCE.createPluginsFacet();
         mainFacet.setName("plugins");
         facets.add(mainFacet);
      }

      return mainFacet;
   }

   private ProjectFacet<? extends Project> addProject(final List<ProjectFacet<?>> facets, Project project) {
      final ModuleModelSwitch<ProjectFacet<? extends Project>> moduleSwitch = new ModuleModelSwitch<ProjectFacet<? extends Project>>() {
         public ProjectFacet<? extends Project> caseFeatureProject(FeatureProject project) {
            final FeaturesFacet facet = getFeaturesFacete();
            facet.getProjects().add(project);
            return facet;
         }

         public ProjectFacet<? extends Project> caseSiteProject(SiteProject project) {
            final SitesFacet facet = getSitesFacete();
            facet.getProjects().add(project);
            return facet;
         }

         // TODO bernd determine correct plugin facete for fragments (a fragments facete is the facete of the host
         // bundle))
         public ProjectFacet<? extends Project> casePluginProject(PluginProject project) {
            final PluginsFacet facet = getPluginsFacete(project);
            facet.getProjects().add(project);
            return facet;
         }

         private FeaturesFacet getFeaturesFacete() {
            FeaturesFacet facet = (FeaturesFacet) getFaceteByName("features");
            if (facet == null) {
               facet = ModuleModelFactory.eINSTANCE.createFeaturesFacet();
               facet.setName("features");
               facets.add(facet);
            }
            return facet;
         }

         private SitesFacet getSitesFacete() {
            SitesFacet facet = (SitesFacet) getFaceteByName("sites");
            if (facet == null) {
               facet = ModuleModelFactory.eINSTANCE.createSitesFacet();
               facet.setName("sites");
               facets.add(facet);
            }
            return facet;
         }

         private PluginsFacet getPluginsFacete(PluginProject project) {
            final String name = project.isTestPlugin() ? "tests" : "plugins";
            PluginsFacet facet = (PluginsFacet) getFaceteByName(name);
            if (facet == null) {
               facet = ModuleModelFactory.eINSTANCE.createPluginsFacet();
               facet.setName(name);
               facets.add(facet);
            }
            return facet;
         }

         private ProjectFacet<? extends Project> getFaceteByName(final String name) {
            return CollectionUtils.find(facets, new Predicate<ProjectFacet<? extends Project>>() {
               public boolean evaluate(ProjectFacet<? extends Project> facet) {
                  return name.equals(facet.getName());
               }
            });
         }

      };
      return moduleSwitch.doSwitch(project);
   }
}
