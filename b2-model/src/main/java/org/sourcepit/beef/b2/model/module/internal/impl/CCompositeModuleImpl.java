/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.beef.b2.model.common.Annotation;
import org.sourcepit.beef.b2.model.module.AbstractFacet;
import org.sourcepit.beef.b2.model.module.Project;
import org.sourcepit.beef.b2.model.module.ProjectFacet;
import org.sourcepit.beef.b2.model.module.Reference;
import org.sourcepit.beef.b2.model.module.internal.util.AnnotationUtils;
import org.sourcepit.beef.b2.model.module.internal.util.FacetUtils;
import org.sourcepit.beef.b2.model.module.internal.util.IdentifiableUtils;
import org.sourcepit.beef.b2.model.module.util.Identifier;

public class CCompositeModuleImpl extends CompositeModuleImpl
{
   @Override
   public Identifier toIdentifier()
   {
      return IdentifiableUtils.toIdentifier(this);
   }

   @Override
   public boolean isIdentifyableBy(Identifier identifier)
   {
      return IdentifiableUtils.isIdentifyableBy(this, identifier);
   }

   @Override
   public <T extends AbstractFacet> EList<T> getFacets(Class<T> facetType)
   {
      return FacetUtils.getFacets(getFacets(), facetType);
   }

   @Override
   @SuppressWarnings("unchecked")
   public <F extends AbstractFacet> F getFacetByName(String type)
   {
      return (F) FacetUtils.getFacetByName(getFacets(), type);
   }

   @Override
   public boolean hasFacets(Class<? extends AbstractFacet> facetType)
   {
      return FacetUtils.hasFacets(getFacets(), facetType);
   }

   @Override
   public Annotation getAnnotation(String source)
   {
      return AnnotationUtils.getAnnotation(getAnnotations(), source);
   }

   @Override
   public String getAnnotationEntry(String source, String key)
   {
      return AnnotationUtils.getAnnotationEntry(getAnnotations(), source, key);
   }

   @Override
   public String putAnnotationEntry(String source, String key, String value)
   {
      return AnnotationUtils.putAnnotationEntry(getAnnotations(), source, key, value);
   }

   @Override
   public <P extends Project, F extends ProjectFacet<P>> P resolveReference(Reference reference, Class<F> facetType)
   {
      return FacetUtils.resolveReference(this, reference, facetType, true);
   }
}
