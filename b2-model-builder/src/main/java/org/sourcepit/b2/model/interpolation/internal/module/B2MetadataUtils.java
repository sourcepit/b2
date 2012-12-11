/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.module.FeatureProject;

public final class B2MetadataUtils
{
   private static final String BRANDING_PLUGIN = "brandingPlugin";
   private static final String BRANDED_FEATURE = "brandedFeature";
   private static final String FACET_CLASSIFIER = "facetClassifier";
   private static final String ASSEMBLY_CLASSIFIERS = "assemblyClassifiers";
   private static final String IS_TEST_PLUGIN = "isTestPlugin";
   private static final String MODULE_VERSION = "moduleVersion";
   private static final String MODULE_ID = "moduleId";
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

   public static void setModuleId(Annotatable annotatable, String value)
   {
      setB2Metadata(annotatable, MODULE_ID, value);
   }

   public static String getModuleId(Annotatable annotatable)
   {
      return annotatable.getAnnotationEntry(B2, MODULE_ID);
   }

   public static void setModuleVersion(Annotatable annotatable, String value)
   {
      setB2Metadata(annotatable, MODULE_VERSION, value);
   }

   public static String getModuleVersion(Annotatable annotatable)
   {
      return annotatable.getAnnotationEntry(B2, MODULE_VERSION);
   }

   public static String getClassifier(FeatureProject featureProject)
   {
      if (isAssemblyFeature(featureProject))
      {
         final List<String> assemblyClassifiers = B2MetadataUtils.getAssemblyClassifiers(featureProject);
         if (assemblyClassifiers.size() != 1)
         {
            throw new IllegalStateException();
         }
         return assemblyClassifiers.get(0);
      }
      else
      {
         return getFacetClassifier(featureProject);
      }
   }

   public static boolean isAssemblyFeature(FeatureProject featureProject)
   {
      final String facetName = B2MetadataUtils.getFacetName(featureProject);
      if (facetName != null)
      {
         return false;
      }
      else
      {
         final List<String> assemblyNames = B2MetadataUtils.getAssemblyNames(featureProject);
         if (assemblyNames.size() != 1)
         {
            throw new IllegalStateException();
         }
         return true;
      }
   }

   public static boolean isFacetSourceFeature(FeatureProject featureProject)
   {
      return !isAssemblyFeature(featureProject) && B2MetadataUtils.isSourceFeature(featureProject);
   }

   public static List<String> getAssemblyNames(Annotatable annotatable)
   {
      final List<String> names = new ArrayList<String>();
      split(names, annotatable.getAnnotationEntry(B2, ASSEMBLY_NAMES));
      return names;
   }

   public static void setAssemblyNames(Annotatable annotatable, List<String> assemblyNames)
   {
      setB2Metadata(annotatable, ASSEMBLY_NAMES, toString(assemblyNames));
   }

   public static void addAssemblyName(Annotatable annotatable, String assemblyName)
   {
      final List<String> assemblyNames = getAssemblyNames(annotatable);
      assemblyNames.add(assemblyName);
      setAssemblyNames(annotatable, assemblyNames);
   }

   public static List<String> getAssemblyClassifiers(Annotatable annotatable)
   {
      final List<String> classifiers = new ArrayList<String>();
      split(classifiers, annotatable.getAnnotationEntry(B2, ASSEMBLY_CLASSIFIERS));
      return classifiers;
   }

   public static void setAssemblyClassifiers(Annotatable annotatable, List<String> assemblyClassifiers)
   {
      setB2Metadata(annotatable, ASSEMBLY_CLASSIFIERS, toString(assemblyClassifiers));
   }

   public static void addAssemblyClassifier(Annotatable annotatable, String assemblyClassifier)
   {
      final List<String> assemblyClassifiers = getAssemblyClassifiers(annotatable);
      assemblyClassifiers.add(assemblyClassifier);
      setAssemblyClassifiers(annotatable, assemblyClassifiers);
   }

   public static List<String> getReplacedFeatureIds(Annotatable annotatable)
   {
      final List<String> names = new ArrayList<String>();
      split(names, annotatable.getAnnotationEntry(B2, "replacedFeatureIds"));
      return names;
   }

   public static void setReplacedFeatureIds(Annotatable annotatable, List<String> featureIds)
   {
      setB2Metadata(annotatable, "replacedFeatureIds", toString(featureIds));
   }

   public static void addReplacedFeatureId(Annotatable annotatable, String featureId)
   {
      final List<String> featureIds = getReplacedFeatureIds(annotatable);
      featureIds.add(featureId);
      setReplacedFeatureIds(annotatable, featureIds);
   }

   public static String getFacetName(Annotatable annotatable)
   {
      return annotatable.getAnnotationEntry(B2, FACET_NAME);
   }

   public static void setFacetName(Annotatable annotatable, String value)
   {
      setB2Metadata(annotatable, FACET_NAME, value);
   }

   public static String getFacetClassifier(Annotatable annotatable)
   {
      return annotatable.getAnnotationEntry(B2, FACET_CLASSIFIER);
   }

   public static void setFacetClassifier(Annotatable annotatable, String value)
   {
      setB2Metadata(annotatable, FACET_CLASSIFIER, value);
   }

   public static void setSourceFeature(Annotatable annotatable, boolean value)
   {
      setB2Metadata(annotatable, IS_SOURCE_FEATURE, toString(value));
   }

   public static boolean isSourceFeature(Annotatable annotatable)
   {
      return toBoolean(annotatable.getAnnotationEntry(B2, IS_SOURCE_FEATURE));
   }

   // TODO add model attribute to FeatureProject?
   public static void setTestFeature(Annotatable annotatable, boolean value)
   {
      setB2Metadata(annotatable, IS_TEST_FEATURE, toString(value));
   }

   // TODO add model attribute to FeatureProject?
   public static boolean isTestFeature(Annotatable annotatable)
   {
      return toBoolean(annotatable.getAnnotationEntry(B2, IS_TEST_FEATURE));
   }

   public static void setTestPlugin(Annotatable annotatable, boolean value)
   {
      setB2Metadata(annotatable, IS_TEST_PLUGIN, toString(value));
   }

   public static boolean isTestPlugin(Annotatable annotatable)
   {
      return toBoolean(annotatable.getAnnotationEntry(B2, IS_TEST_PLUGIN));
   }

   private static void setB2Metadata(Annotatable annotatable, String key, String value)
   {
      if (value == null)
      {
         final Annotation b2Metadata = getB2Metadata(annotatable);
         if (b2Metadata != null)
         {
            b2Metadata.getEntries().removeKey(key);
         }
      }
      else
      {
         annotatable.putAnnotationEntry(B2, key, value);
      }
   }

   private static void split(Collection<String> values, String rawValues)
   {
      if (rawValues != null)
      {
         for (String rawValue : rawValues.split(","))
         {
            String name = rawValue.trim();
            values.add(name);
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
      return sb.toString();
   }

   private static String toString(boolean value)
   {
      return value ? Boolean.toString(value) : null;
   }

   public static void setBrandedFeature(Annotatable annotatable, String featureId)
   {
      setB2Metadata(annotatable, BRANDED_FEATURE, featureId);
   }

   public static String getBrandedFeature(Annotatable annotatable)
   {
      return annotatable.getAnnotationEntry(B2, BRANDED_FEATURE);
   }

   public static void setBrandingPlugin(Annotatable annotatable, String pluginId)
   {
      setB2Metadata(annotatable, BRANDING_PLUGIN, pluginId);
   }
   
   public static String getBrandingPlugin(Annotatable annotatable)
   {
      return annotatable.getAnnotationEntry(B2, BRANDING_PLUGIN);
   }
}
