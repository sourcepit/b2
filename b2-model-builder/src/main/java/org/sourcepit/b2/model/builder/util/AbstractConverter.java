/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.plexus.interpolation.MapBasedValueSource;
import org.codehaus.plexus.interpolation.ValueSource;
import org.sourcepit.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.b2.common.internal.utils.PathMatcher;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.b2.common.internal.utils.PropertiesUtils;
import org.sourcepit.b2.internal.generator.PropertiesQuery;
import org.sourcepit.tools.shared.resources.harness.StringInterpolator;

/**
 * @author Bernd
 */
public abstract class AbstractConverter implements IConverter
{
   private List<ValueSource> valueSources;

   public StringInterpolator newInterpolator()
   {
      final StringInterpolator interpolator = new StringInterpolator();
      interpolator.setEscapeString("\\");
      interpolator.getValueSources().addAll(getValueSources());
      return interpolator;
   }

   public boolean isSkipInterpolator()
   {
      return Boolean.valueOf(getPropertiesMap().get("b2.skipInterpolator", "false")).booleanValue();
   }

   public boolean isSkipGenerator()
   {
      return Boolean.valueOf(getPropertiesMap().get("b2.skipGenerator", "false")).booleanValue();
   }

   public String interpolate(String value)
   {
      return interpolate(value, null);
   }

   public String interpolate(String value, PropertiesMap properties)
   {
      final StringInterpolator interpolator = newInterpolator();
      if (properties != null)
      {
         interpolator.getValueSources().add(0, new MapBasedValueSource(properties));
      }
      return interpolator.interpolate(value);
   }

   protected PropertiesMap loadConverterProperties()
   {
      final PropertiesMap propertiesMap = new LinkedPropertiesMap();
      propertiesMap.load(getClass().getClassLoader(), "META-INF/b2/converter.properties");
      return propertiesMap;
   }

   public final PropertiesMap getProperties()
   {
      return PropertiesUtils.unmodifiablePropertiesMap(getPropertiesMap());
   }

   protected abstract PropertiesMap getPropertiesMap();

   public String getFacetName(File facetFolder)
   {
      return facetFolder.getName();
   }

   public String toClassifierLabel(String classifier)
   {
      final StringBuilder sb = new StringBuilder();
      boolean nextUp = true;

      for (char c : classifier.toCharArray())
      {
         if (c == '.')
         {
            c = ' ';
         }
         sb.append(nextUp ? Character.toUpperCase(c) : c);
         nextUp = Character.isWhitespace(c);
      }

      //
      // if (classifier.length() > 1)
      // {
      // return classifier.substring(0, 1).toUpperCase() + classifier.substring(1).toLowerCase();
      // }
      return sb.toString();
   }

   public Collection<ValueSource> getValueSources()
   {
      if (valueSources == null)
      {
         valueSources = new ArrayList<ValueSource>();
         addValueSources(valueSources);
      }
      return valueSources;
   }

   protected void addValueSources(List<ValueSource> valueSources)
   {
      valueSources.add(new MapBasedValueSource(getPropertiesMap()));
   }

   public Set<String> getCategoryClassifiers()
   {
      return toSet(getPropertiesMap().get("categories"));
   }

   public Set<String> getSiteClassifiers()
   {
      final Set<String> classifers = new LinkedHashSet<String>();

      for (String siteName : getSiteNames())
      {
         final String defaultClassifer = toValidClassifier(siteName);

         // allow site classifiers rewrite
         classifers.add(getPropertiesMap().get("site" + spacer(defaultClassifer) + "classifier", defaultClassifer));
      }

      return classifers;
   }

   private Set<String> getSiteNames()
   {
      return toSet(getPropertiesMap().get("sites"));
   }

   public Set<String> getCategoryClassifiersForSite(String siteClassifer)
   {
      // we have to remap custom site classifers to the origin name that we can derive the default site classifier to
      // lookup the related categories
      final String siteName = getSiteNameByClassifer(siteClassifer);
      return toSet(getPropertiesMap().get("site" + spacer(toValidClassifier(siteName)) + "categories"));
   }

   private String getSiteNameByClassifer(String siteClassifer)
   {
      for (String siteName : getSiteNames())
      {
         final String defaultClassifer = toValidClassifier(siteName);
         final String customClassifer = getPropertiesMap().get("site" + spacer(defaultClassifer) + "classifier",
            defaultClassifer);
         if (siteClassifer.equals(customClassifer))
         {
            return siteName;
         }
      }
      return null;
   }

   public PathMatcher createIdMatcherForCategory(String layout, String categoryClassifer)
   {
      final String categoryFilterKey = "category" + spacer(categoryClassifer) + "filter";

      final PropertiesQuery query = new PropertiesQuery();
      query.addKey("layout" + spacer(layout) + categoryFilterKey);
      query.addKey(categoryFilterKey);
      query.setDefault("");

      final String filter = query.lookup(getValueSources());

      return PathMatcher.parsePackagePatterns(interpolate(filter));
   }

   private Set<String> toSet(String string)
   {
      final Set<String> set = new LinkedHashSet<String>();
      if (string != null)
      {
         for (String segment : string.split(","))
         {
            set.add(segment.trim());
         }
      }
      return set;
   }

   public Set<String> getFeatureClassifiers(Set<String> facetNames)
   {
      final Set<String> result = new LinkedHashSet<String>();
      String rawClassifers = getPropertiesMap().get("features");
      if (rawClassifers != null)
      {
         if (facetNames != null)
         {
            final String classifers = convertFacetNamesToClassifiersStringList(facetNames);
            if (classifers != null)
            {
               rawClassifers = rawClassifers.replaceAll("\\$\\{pluginFacets\\.classifers\\}", classifers);
            }
         }
         for (String rawClassifer : rawClassifers.split(","))
         {
            rawClassifer = rawClassifer.trim();
            if (!"${pluginFacets.classifers}".equals(rawClassifer))
            {
               result.add(rawClassifer);
            }
         }
      }
      return result;
   }

   private String convertFacetNamesToClassifiersStringList(Collection<String> facetNames)
   {
      if (facetNames.isEmpty())
      {
         return null;
      }
      final StringBuilder facetClassifers = new StringBuilder();
      for (String facetName : facetNames)
      {
         facetClassifers.append(", ");
         facetClassifers.append(convertFacetNameToFeatureClassifier(facetName));
      }
      if (facetClassifers.length() > 0)
      {
         facetClassifers.deleteCharAt(0);
         facetClassifers.deleteCharAt(0);
      }
      return facetClassifers.toString();
   }

   public String createSourceFeatureClassifer(String featureClassifer)
   {
      final String sourceClassifer = getSourceClassiferForFeature(featureClassifer);
      if (featureClassifer.length() == 0)
      {
         return sourceClassifer;
      }
      else
      {
         return featureClassifer + "." + sourceClassifer;
      }
   }

   public String createSourcePluginId(String pluginId)
   {
      final String sourceClassifer = getSourceClassiferForPlugin(pluginId);
      if (pluginId.length() == 0)
      {
         return sourceClassifer;
      }
      else
      {
         return pluginId + "." + sourceClassifer;
      }
   }

   public String getSourceClassiferForFeature(String featureClassifer)
   {
      final String customKey = "feature" + spacer(featureClassifer) + "source.classifer";
      final String defaultKey = "features.source.classifer";
      return getSourceClassifer(customKey, defaultKey);
   }

   public String getSourceClassiferForPlugin(String pluginId)
   {
      final String customKey = "plugin" + spacer(pluginId) + "source.classifer";
      final String defaultKey = "plugins.source.classifer";
      return getSourceClassifer(customKey, defaultKey);
   }

   private String getSourceClassifer(final String customKey, final String defaultKey)
   {
      final String value = getPropertyValue(customKey, defaultKey);
      if (value.length() == 0)
      {
         throw new IllegalStateException("Source classifer must not be empty for:" + customKey + ", " + defaultKey);
      }
      return toValidClassifier(value);
   }

   private String getPropertyValue(final String customKey, final String defaultKey)
   {
      String value = getPropertiesMap().get(customKey);
      if (value == null)
      {
         value = getPropertiesMap().get(defaultKey);
      }
      if (value == null)
      {
         throw new IllegalStateException("No value found for:" + customKey + ", " + defaultKey);
      }
      return value;
   }

   public String convertFacetNameToFeatureClassifier(String facetName)
   {
      if (facetName.length() == 0)
      {
         throw new IllegalArgumentException("facetName must not be empty.");
      }
      final String defaultClassifer = toValidClassifier(facetName);
      return toValidClassifier(getPropertiesMap().get("facet" + spacer(defaultClassifer) + "classifier",
         defaultClassifer));
   }

   protected static String toValidClassifier(String string)
   {
      if (string.length() == 0)
      {
         return string;
      }
      return toValidId(string);
   }

   protected static String toValidId(String string)
   {
      return toJavaIdentifier(string.toLowerCase());
   }

   /**
    * Converts the specified string into a valid Java identifier. All illegal characters are replaced by underscores.
    * 
    * @param aString <i>(required)</i>. The string must contain at least one character.
    * @return <i>(required)</i>.
    */
   protected static String toJavaIdentifier(String aString)
   {
      if (aString.length() == 0)
      {
         return "_";
      }

      final StringBuilder res = new StringBuilder();
      int idx = 0;
      char c = aString.charAt(idx);
      if (Character.isJavaIdentifierStart(c))
      {
         res.append(c);
         idx++;
      }
      else if (Character.isJavaIdentifierPart(c))
      {
         res.append('_');
      }
      while (idx < aString.length())
      {
         c = aString.charAt(idx++);
         res.append(Character.isJavaIdentifierPart(c) ? c : '_');
      }
      return res.toString();
   }

   public PathMatcher createIdMatcherForFeature(String layout, String featureClassifer)
   {
      final String featureFilterKey = "feature" + spacer(featureClassifer) + "filter";

      final PropertiesQuery query = new PropertiesQuery();
      query.addKey("layout" + spacer(layout) + featureFilterKey);
      query.addKey(featureFilterKey);
      query.setDefault("");

      final String filter = query.lookup(getValueSources());

      return PathMatcher.parsePackagePatterns(interpolate(filter));
   }

   private static String spacer(String featureClassifer)
   {
      return featureClassifer.length() == 0 ? "." : "." + featureClassifer + ".";
   }

   protected PathMatcher createModuleDirectoryMacher(File baseDir)
   {
      final String filter = System.getProperty("b2.modules.filter", "**");

      // final PropertiesQuery query = new PropertiesQuery();
      // query.addKey("b2.modules.filter");
      // query.setDefault("**");
      // final String filter = query.lookup(getValueSources());

      return PathMatcher.parseFilePatterns(baseDir, interpolate(filter));
   }

   public boolean isPotentialModuleDirectory(File baseDir, File file)
   {
      if (file.isDirectory() && file.exists() && new File(file, "module.xml").exists())
      {
         final PathMatcher moduleDirMacher = createModuleDirectoryMacher(baseDir);
         if (moduleDirMacher.isMatch(file.getAbsolutePath()))
         {
            return true;
         }
      }
      return false;
   }
}
