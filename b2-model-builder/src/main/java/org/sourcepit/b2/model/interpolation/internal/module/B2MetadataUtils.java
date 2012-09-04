/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.common.Annotation;

public final class B2MetadataUtils
{
   private static final String IS_TEST_FEATURE = "isTestFeature";
   private static final String IS_SOURCE_FEATURE = "isSourceFeature";
   private static final String FACET_NAME = "facetName";
   private static final String ASSEMBLY_NAMES = "assemblyNames";
   private static final String B2 = "b2";

   private B2MetadataUtils()
   {
      super();
   }

   public static Annotation getB2Metadata(Annotatable annotatable)
   {
      return annotatable.getAnnotation(B2);
   }

   public static Set<String> getAssemblyNames(Annotatable annotatable)
   {
      final Set<String> names = new LinkedHashSet<String>();
      split(names, annotatable.getAnnotationEntry(B2, ASSEMBLY_NAMES));
      return names;
   }

   public static void setAssemblyNames(Annotatable annotatable, Set<String> assemblyNames)
   {
      annotatable.putAnnotationEntry(B2, ASSEMBLY_NAMES, toString(assemblyNames));
   }

   public static void addAssemblyName(Annotatable annotatable, String assemblyName)
   {
      final Set<String> assemblyNames = getAssemblyNames(annotatable);
      assemblyNames.add(assemblyName);
      setAssemblyNames(annotatable, assemblyNames);
   }

   public static String getFacetName(Annotatable annotatable)
   {
      return annotatable.getAnnotationEntry(B2, FACET_NAME);
   }

   public static void setFacetName(Annotatable annotatable, String facetName)
   {
      annotatable.putAnnotationEntry(B2, FACET_NAME, facetName);
   }

   public static void setSourceFeature(Annotatable annotatable, boolean isSource)
   {
      annotatable.putAnnotationEntry(B2, IS_SOURCE_FEATURE, toString(isSource));
   }

   public static boolean isSourceFeature(Annotatable annotatable)
   {
      return toBoolean(annotatable.getAnnotationEntry(B2, IS_SOURCE_FEATURE));
   }

   // TODO add model attribute to FeatureProject?
   public static void setTestFeature(Annotatable annotatable, boolean value)
   {
      annotatable.putAnnotationEntry(B2, IS_TEST_FEATURE, toString(value));
   }

   // TODO add model attribute to FeatureProject?
   public static boolean isTestFeature(Annotatable annotatable)
   {
      return toBoolean(annotatable.getAnnotationEntry(B2, IS_TEST_FEATURE));
   }

   private static void split(Collection<String> values, String rawValues)
   {
      if (rawValues != null)
      {
         for (String rawValue : rawValues.split(","))
         {
            String name = rawValue.trim();
            if (name.length() > 0)
            {
               values.add(name);
            }
         }
      }
   }

   private static boolean toBoolean(String value)
   {
      return Boolean.valueOf(value).booleanValue();
   }

   private static String toString(Collection<String> values)
   {
      if (values == null)
      {
         return null;
      }
      final StringBuilder sb = new StringBuilder();
      for (String value : values)
      {
         sb.append(value);
         sb.append(", ");
      }
      if (sb.length() > 0)
      {
         sb.deleteCharAt(sb.length() - 1);
         sb.deleteCharAt(sb.length() - 1);
      }
      return sb.length() > 0 ? sb.toString() : null;
   }

   private static String toString(boolean isSource)
   {
      return Boolean.toString(isSource);
   }
}
