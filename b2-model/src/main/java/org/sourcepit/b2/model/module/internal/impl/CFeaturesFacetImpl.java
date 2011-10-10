/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.impl;

import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.common.internal.util.AnnotationUtils;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.Reference;
import org.sourcepit.b2.model.module.internal.util.FacetUtils;
import org.sourcepit.b2.model.module.internal.util.ProjectUtils;

/**
 * @author Bernd
 */
public class CFeaturesFacetImpl extends FeaturesFacetImpl
{
   @Override
   public FeatureProject getProjectById(String name)
   {
      return ProjectUtils.getProjectById(projects, name);
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
   public FeatureProject resolveReference(Reference reference)
   {
      return FacetUtils.resolveReference(this, reference);
   }
}
