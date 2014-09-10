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
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.internal.project.ProjectDetector;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named("structured")
public class StructuredLayoutFacetsParserRule extends AbstractFacetsParserRule<ProjectFacet<? extends Project>>
{
   private static final String LAYOUT = StructuredLayoutFacetsParserRule.class.getAnnotation(Named.class).value();

   @Inject
   @Named("simple")
   private AbstractFacetsParserRule<ProjectFacet<? extends Project>> simpleLayoutParserRule;

   @Inject
   private ProjectDetector projectDetector;

   @Override
   public FacetsParseResult<ProjectFacet<? extends Project>> parse(File directory, final PropertiesSource properties)
   {
      final List<ProjectFacet<? extends Project>> facets = new ArrayList<ProjectFacet<? extends Project>>();
      if (directory == null || !directory.exists())
      {
         return null;
      }

      if (projectDetector.detect(directory, properties) != null)
      {
         return null;
      }

      directory.listFiles(new FileFilter()
      {
         public boolean accept(File member)
         {
            if (member.exists() && member.isDirectory())
            {
               if (!new File(member, "module.xml").exists())
               {
                  ProjectFacet<? extends Project> facet = parseFacetFolder(member, properties);
                  if (facet != null)
                  {
                     facets.add(facet);
                  }
               }
            }
            return false;
         }
      });

      if (facets.isEmpty())
      {
         return null;
      }

      return new FacetsParseResult<ProjectFacet<? extends Project>>(LAYOUT, facets);
   }

   @SuppressWarnings("unchecked")
   private ProjectFacet<? extends Project> parseFacetFolder(File member, PropertiesSource properties)
   {
      final FacetsParseResult<ProjectFacet<? extends Project>> result = simpleLayoutParserRule
         .parse(member, properties);
      if (result == null)
      {
         return null;
      }

      final List<ProjectFacet<? extends Project>> facets = result.getFacets();

      if (facets == null || facets.isEmpty())
      {
         return null;
      }

      ProjectFacet<Project> f = null;
      for (ProjectFacet<? extends Project> facet : facets)
      {
         if (f == null)
         {
            f = (ProjectFacet<Project>) facet;
         }
         else if (f.getClass() == facet.getClass())
         {
            for (Project project : new ArrayList<Project>(facet.getProjects()))
            {
               f.getProjects().add(project);
            }
         }
         else
         {
            throw new IllegalStateException("Directory " + member.getName() + " contains projects of different kinds.");
         }
      }
      f.setName(member.getName());
      return f;
   }
}
