/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.session.internal.impl;

import org.sourcepit.beef.b2.model.common.Annotation;
import org.sourcepit.beef.b2.model.common.internal.util.AnnotationUtils;

/**
 * CModuleProjectImpl
 * 
 * @author Bernd
 */
public class CModuleProjectImpl extends ModuleProjectImpl
{
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
}
