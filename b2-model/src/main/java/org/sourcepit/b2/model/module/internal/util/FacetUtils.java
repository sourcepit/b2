/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
