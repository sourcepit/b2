/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
