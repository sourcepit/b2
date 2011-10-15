/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.b2.model.module.Reference;
import org.sourcepit.b2.model.module.internal.util.FacetUtils;

public aspect AbstractModuleAspect
{
   pointcut getFacets(AbstractModule module, Class<? extends AbstractFacet> facetType): target(module) && args(facetType) && execution(EList getFacets(Class));

   pointcut hasFacets(AbstractModule module, Class<? extends AbstractFacet> facetType): target(module) && args(facetType) && execution(boolean hasFacets(Class));

   pointcut getFacetByName(AbstractModule module, String type): target(module) && args(type) && execution(AbstractFacet getFacetByName(String));

   pointcut resolveReference(AbstractModule module, Reference reference,
      @SuppressWarnings("rawtypes") Class<? extends ProjectFacet> facetType) : target(module) && args(reference, facetType) && execution(Project resolveReference(Reference, Class));

   Object around(AbstractModule module, Class<? extends AbstractFacet> facetType) : getFacets(module ,facetType)
   {
      return FacetUtils.getFacets(module.getFacets(), facetType);
   }

   boolean around(AbstractModule module, Class<? extends AbstractFacet> facetType) : hasFacets(module ,facetType)
   {
      return FacetUtils.hasFacets(module.getFacets(), facetType);
   }

   AbstractFacet around(AbstractModule module, String type) : getFacetByName(module ,type)
   {
      return FacetUtils.getFacetByName(module.getFacets(), type);
   }

   @SuppressWarnings("unchecked")
   Project around(AbstractModule module, Reference reference,
      @SuppressWarnings("rawtypes") Class<? extends ProjectFacet> facetType) : resolveReference(module, reference, facetType)
   {
      return FacetUtils.resolveReference(module, reference, facetType, false);
   }
}
