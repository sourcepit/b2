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

import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;

@Named("structured")
public class StructuredLayoutFacetsParserRule extends AbstractFacetsParserRule<ProjectFacet<? extends Project>>
{
   private final static String LAYOUT = StructuredLayoutFacetsParserRule.class.getAnnotation(Named.class).value();

   @Inject
   @Named("simple")
   private AbstractFacetsParserRule<ProjectFacet<? extends Project>> simpleLayoutParserRule;

   @Override
   public FacetsParseResult<ProjectFacet<? extends Project>> parse(File directory, final IConverter converter)
   {
      final List<ProjectFacet<? extends Project>> facets = new ArrayList<ProjectFacet<? extends Project>>();
      if (directory == null || !directory.exists())
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
                  ProjectFacet<? extends Project> facet = parseFacetFolder(member, converter);
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

   private ProjectFacet<? extends Project> parseFacetFolder(File member, IConverter converter)
   {
      final FacetsParseResult<ProjectFacet<? extends Project>> result = simpleLayoutParserRule.parse(member, converter);
      if (result == null)
      {
         return null;
      }

      final List<ProjectFacet<? extends Project>> facets = result.getFacets();

      if (facets == null || facets.isEmpty())
      {
         return null;
      }

      if (facets.size() > 1)
      {
         // TODO bernd: break ?!
      }

      ProjectFacet<? extends Project> facet = facets.get(0);
      facet.setName(converter.getFacetName(member));
      return facet;
   }
}
