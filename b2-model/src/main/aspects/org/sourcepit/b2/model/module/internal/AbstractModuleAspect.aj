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

package org.sourcepit.b2.model.module.internal;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.b2.model.module.internal.util.FacetUtils;

public aspect AbstractModuleAspect
{
   pointcut getFacets(AbstractModule module, Class<? extends AbstractFacet> facetType): target(module) && args(facetType) && execution(EList getFacets(Class));

   pointcut hasFacets(AbstractModule module, Class<? extends AbstractFacet> facetType): target(module) && args(facetType) && execution(boolean hasFacets(Class));

   pointcut getFacetByName(AbstractModule module, String type): target(module) && args(type) && execution(AbstractFacet getFacetByName(String));

   pointcut resolveReference(AbstractModule module, AbstractReference reference,
      @SuppressWarnings("rawtypes") Class<? extends ProjectFacet> facetType) : target(module) && args(reference, facetType) && execution(Project resolveReference(AbstractReference, Class));

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
   Project around(AbstractModule module, AbstractReference reference,
      @SuppressWarnings("rawtypes") Class<? extends ProjectFacet> facetType) : resolveReference(module, reference, facetType)
   {
      return FacetUtils.resolveReference(module, reference, facetType, false);
   }
}
