/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import org.sourcepit.beef.b2.model.module.Annotation;
import org.sourcepit.beef.b2.model.module.internal.impl.FeatureProjectImpl;
import org.sourcepit.beef.b2.model.module.internal.util.AnnotationUtils;
import org.sourcepit.beef.b2.model.module.internal.util.IdentifiableUtils;
import org.sourcepit.beef.b2.model.module.util.Identifier;

public class CFeatureProjectImpl extends FeatureProjectImpl
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
