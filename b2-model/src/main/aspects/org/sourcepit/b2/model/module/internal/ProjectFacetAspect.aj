/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal;

import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.internal.util.FacetUtils;
import org.sourcepit.b2.model.module.internal.util.ProjectUtils;

public aspect ProjectFacetAspect
{
   pointcut getProjectById(@SuppressWarnings("rawtypes") ProjectFacet facet, String id) : target(facet) && args(id) && execution(* getProjectById(String));

   pointcut resolveReference(@SuppressWarnings("rawtypes") ProjectFacet facet, AbstractReference reference) : target(facet) && args(reference) && execution(* resolveReference(AbstractReference));

   @SuppressWarnings("unchecked")
   Object around(@SuppressWarnings("rawtypes") ProjectFacet facet, String id) : getProjectById(facet, id)
   {
      return ProjectUtils.getProjectById(facet.getProjects(), id);
   }

   @SuppressWarnings("unchecked")
   Object around(@SuppressWarnings("rawtypes") ProjectFacet facet, AbstractReference reference) : resolveReference(facet, reference)
   {
      return FacetUtils.resolveReference(facet, reference);
   }
}
