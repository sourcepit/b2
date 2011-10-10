/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.impl;

import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.common.internal.util.AnnotationUtils;
import org.sourcepit.b2.model.module.internal.util.IdentifiableUtils;
import org.sourcepit.b2.model.module.util.Identifier;


/**
 * @author Bernd
 */
public class CPluginProjectImpl extends PluginProjectImpl
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
   public boolean isFragment()
   {
      return getFragmentHostSymbolicName() != null;
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
