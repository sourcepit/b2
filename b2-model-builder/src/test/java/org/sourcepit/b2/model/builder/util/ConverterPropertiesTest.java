/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.sourcepit.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.b2.common.internal.utils.PathMatcher;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.b2.model.builder.util.DefaultConverter;
import org.sourcepit.b2.model.builder.util.IConverter;

public class ConverterPropertiesTest extends TestCase
{
   public void testFacetNameToClassifier() throws Exception
   {
      IConverter converter = new DefaultConverter("org.sourcepit.b2", "0.0.1.qualifier", null);
      try
      {
         converter.convertFacetNameToFeatureClassifier(null);
         fail();
      }
      catch (NullPointerException e)
      {
      }

      try
      {
         converter.convertFacetNameToFeatureClassifier("");
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      assertEquals("features", converter.convertFacetNameToFeatureClassifier("features"));
      assertEquals("features", converter.convertFacetNameToFeatureClassifier("Features"));

      // ist magic :-)
      // converts has to load and respect the filter rules defined in META-INF/b2/defaultConverter.properties
      assertEquals("", converter.convertFacetNameToFeatureClassifier("plugins"));
      assertEquals("", converter.convertFacetNameToFeatureClassifier("Plugins"));

      assertEquals("_3äng", converter.convertFacetNameToFeatureClassifier("3äng"));
      assertEquals("hans_wurst", converter.convertFacetNameToFeatureClassifier("Hans Wurst"));

      Set<String> facetNames = new LinkedHashSet<String>();
      facetNames.add("plugins");
      facetNames.add("tests");
      facetNames.add("examples");
   }

   public void testFacetNameToClassifier_WithRewrite() throws Exception
   {
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("facet.tests.classifier", "Foo");

      IConverter converter = new DefaultConverter("org.sourcepit.b2", "0.0.1.qualifier", properties);

      assertEquals("features", converter.convertFacetNameToFeatureClassifier("features"));
      assertEquals("", converter.convertFacetNameToFeatureClassifier("plugins"));

      assertEquals("foo", converter.convertFacetNameToFeatureClassifier("tests"));
   }

   public void testGetFeatureClassifiers() throws Exception
   {
      IConverter converter = new DefaultConverter("org.sourcepit.b2", "0.0.1.qualifier", null);

      Set<String> classifiers = converter.getFeatureClassifiers(null);
      assertEquals(Collections.singleton("sdk"), classifiers);

      classifiers = converter.getFeatureClassifiers(new HashSet<String>());
      assertEquals(Collections.singleton("sdk"), classifiers);

      try
      {
         converter.getFeatureClassifiers(Collections.singleton(""));
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      Set<String> pluginFacetNames = new LinkedHashSet<String>();
      pluginFacetNames.add("plugins");
      pluginFacetNames.add("tests");
      pluginFacetNames.add("doc");

      classifiers = converter.getFeatureClassifiers(pluginFacetNames);

      Set<String> expected = new LinkedHashSet<String>();
      expected.add("");
      expected.add("tests");
      expected.add("doc");
      expected.add("sdk");
      assertEquals(expected, classifiers);
   }

   public void testCreateIdMatcherForFeature() throws Exception
   {
      IConverter converter = new DefaultConverter("org.sourcepit.b2", "0.0.1.qualifier", null);
      try
      {
         converter.createIdMatcherForFeature("simple", null);
         fail();
      }
      catch (NullPointerException e)
      {
      }

      PathMatcher matcher = converter.createIdMatcherForFeature("simple", "sdk");
      assertNotNull(matcher);
      assertFalse(matcher.getExcludes().isEmpty());
      assertFalse(matcher.getIncludes().isEmpty());

      assertTrue(matcher.isMatch("foo.bar.feature"));
      assertFalse(matcher.isMatch("foo.tests.bar"));

      matcher = converter.createIdMatcherForFeature("simple", "murks");
      assertNotNull(matcher);
      assertTrue(matcher.getExcludes().isEmpty());
      assertTrue(matcher.getIncludes().isEmpty());

      matcher = converter.createIdMatcherForFeature("composite", "sdk");
      assertNotNull(matcher);
      assertTrue(matcher.getExcludes().isEmpty());
      assertFalse(matcher.getIncludes().isEmpty());
   }

   public void testGetSourceClassiferForFeature() throws Exception
   {
      IConverter converter = new DefaultConverter("org.sourcepit.b2", "0.0.1.qualifier", null);
      try
      {
         converter.getSourceClassiferForFeature(null);
         fail();
      }
      catch (NullPointerException e)
      {
      }

      assertEquals("sources", converter.getSourceClassiferForFeature(""));
      assertEquals("sources", converter.getSourceClassiferForFeature("sdk"));

      PropertiesMap map = new LinkedPropertiesMap();
      map.put("features.source.classifer", "foo");
      map.put("feature.source.classifer", "muh");
      map.put("feature.sdk.source.classifer", "bar");

      converter = new DefaultConverter("org.sourcepit.b2", "0.0.1.qualifier", map);

      assertEquals("muh", converter.getSourceClassiferForFeature(""));
      assertEquals("bar", converter.getSourceClassiferForFeature("sdk"));
      assertEquals("foo", converter.getSourceClassiferForFeature("max"));
   }

   public void testGetSourceClassiferForPlugin() throws Exception
   {
      IConverter converter = new DefaultConverter("org.sourcepit.b2", "0.0.1.qualifier", null);
      try
      {
         converter.getSourceClassiferForPlugin(null);
         fail();
      }
      catch (NullPointerException e)
      {
      }

      assertEquals("source", converter.getSourceClassiferForPlugin(""));
      assertEquals("source", converter.getSourceClassiferForPlugin("sdk"));

      PropertiesMap map = new LinkedPropertiesMap();
      map.put("plugins.source.classifer", "foo");
      map.put("plugin.source.classifer", "muh");
      map.put("plugin.sdk.source.classifer", "bar");

      converter = new DefaultConverter("org.sourcepit.b2", "0.0.1.qualifier", map);

      assertEquals("muh", converter.getSourceClassiferForPlugin(""));
      assertEquals("bar", converter.getSourceClassiferForPlugin("sdk"));
      assertEquals("foo", converter.getSourceClassiferForPlugin("max"));
   }

   public void _testCreateModuleDirectoryMacher() throws Exception
   {
      DefaultConverter converter = new DefaultConverter("org.sourcepit.b2", "0.0.1.qualifier", null);
      PathMatcher macher = converter.createModuleDirectoryMacher(new File("").getAbsoluteFile());
      assertNotNull(macher);
      assertTrue(macher.getIncludes().isEmpty());
      assertFalse(macher.getExcludes().isEmpty());

      assertFalse(macher.isMatch(new File(".svn").getAbsolutePath()));
      assertTrue(macher.isMatch(new File("rcp").getAbsolutePath()));
   }
}
