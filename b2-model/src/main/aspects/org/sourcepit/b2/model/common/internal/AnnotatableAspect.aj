/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.common.internal;

import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.common.internal.util.AnnotationUtils;

public aspect AnnotatableAspect
{
   pointcut getAnnotation(Annotatable a, String source): target(a) && args(source) && execution(Annotation getAnnotation(String));

   pointcut getAnnotationEntry(Annotatable a, String source, String key): target(a) && args(source, key) && execution(String getAnnotationEntry(String, String));

   pointcut putAnnotationEntry(Annotatable a, String source, String key, String value): target(a) && args(source, key, value) && execution(String putAnnotationEntry(String, String, String));

   Annotation around(Annotatable a, String source) : getAnnotation(a, source){
      return AnnotationUtils.getAnnotation(a.getAnnotations(), source);
   }

   String around(Annotatable a, String source, String key) : getAnnotationEntry(a, source, key){
      return AnnotationUtils.getAnnotationEntry(a.getAnnotations(), source, key);
   }

   String around(Annotatable a, String source, String key, String value) : putAnnotationEntry(a, source, key, value){
      return AnnotationUtils.putAnnotationEntry(a.getAnnotations(), source, key, value);
   }
}
