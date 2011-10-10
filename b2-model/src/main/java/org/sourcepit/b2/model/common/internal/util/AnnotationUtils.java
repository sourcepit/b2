/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.common.internal.util;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.common.CommonModelFactory;

public final class AnnotationUtils
{
   private AnnotationUtils()
   {
      super();
   }

   public static Annotation getAnnotation(EList<Annotation> annotations, String source)
   {
      if (source == null)
      {
         throw new IllegalArgumentException("Source must not be null");
      }
      if (annotations == null)
      {
         throw new IllegalArgumentException("Annotations must not be null.");
      }
      for (Annotation annotation : annotations)
      {
         if (source.equals(annotation.getSource()))
         {
            return annotation;
         }
      }
      return null;
   }

   public static String getAnnotationEntry(EList<Annotation> annotations, String source, String key)
   {
      if (key == null)
      {
         throw new IllegalArgumentException("Key must not be null.");
      }
      final Annotation annotation = getAnnotation(annotations, source);
      if (annotation != null)
      {
         return annotation.getEntries().get(key);
      }
      return null;
   }

   public static String putAnnotationEntry(EList<Annotation> annotations, String source, String key, String value)
   {
      if (key == null)
      {
         throw new IllegalArgumentException("Key must not be null.");
      }
      Annotation annotation = getAnnotation(annotations, source);
      if (annotation == null)
      {
         annotation = CommonModelFactory.eINSTANCE.createAnnotation();
         annotation.setSource(source);
         annotations.add(annotation);
      }
      return annotation.getEntries().put(key, value);
   }
}
