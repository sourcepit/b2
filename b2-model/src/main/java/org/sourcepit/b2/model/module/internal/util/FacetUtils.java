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

package org.sourcepit.b2.model.module.internal.util;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;

/**
 * @author Bernd
 */
public final class FacetUtils
{
   private FacetUtils()
   {
      super();
   }

   @SuppressWarnings("unchecked")
   public static <T extends AbstractFacet> EList<T> getFacets(EList<AbstractFacet> facets, Class<T> facetType)
   {
      final EList<T> result = new BasicEList<T>();
      for (AbstractFacet facet : facets)
      {
         if (facetType.isAssignableFrom(facet.getClass()))
         {
            result.add((T) facet);
         }
      }
      return result;
   }

   @SuppressWarnings("unchecked")
   public static <F extends AbstractFacet> F getFacetByName(EList<AbstractFacet> facets, String type)
   {
      for (AbstractFacet moduleFacet : facets)
      {
         if (type != null && type.equals(moduleFacet.getName()))
         {
            return (F) moduleFacet;
         }
      }
      return null;
   }

   public static boolean hasFacets(EList<AbstractFacet> facets, Class<? extends AbstractFacet> facetType)
   {
      return !getFacets(facets, facetType).isEmpty();
   }

   public static <P extends Project, F extends ProjectFacet<P>> P resolveReference(AbstractModule module,
      AbstractReference reference, Class<F> facetType, boolean recursive)
   {
      if (reference == null)
      {
         throw new IllegalArgumentException("Reference must not be null.");
      }
      for (F facet : module.getFacets(facetType))
      {
         final P project = facet.resolveReference(reference);
         if (project != null)
         {
            return project;
         }
      }

      if (recursive && module instanceof CompositeModule)
      {
         CompositeModule cModule = (CompositeModule) module;
         for (AbstractModule subModule : cModule.getModules())
         {
            final P project = subModule.resolveReference(reference, facetType);
            if (project != null)
            {
               return project;
            }
         }
      }
      return null;
   }

   public static <P extends Project> P resolveReference(ProjectFacet<P> projectFacet, AbstractReference reference)
   {
      if (reference == null)
      {
         throw new IllegalArgumentException("Reference must not be null.");
      }
      for (P project : projectFacet.getProjects())
      {
         if (reference.isSatisfiableBy(project))
         {
            return project;
         }
      }
      return null;
   }
}
