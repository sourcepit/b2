/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;

public class DefaultConverterTest {

   @Test
   public void testGetIncludedFeaturesForFacet() throws Exception {
      String facetName = "foo";
      String propertyName = "includedFeatures";
      boolean isSource = false;

      String key1 = "b2.facets." + facetName + "." + propertyName;
      String key2 = "b2." + propertyName;

      testGetIncludedFeatures(key1, facetName, isSource);
      testGetIncludedFeatures(key2, facetName, isSource);

      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      FeaturesConverter converter = new DefaultConverter();

      List<FeatureInclude> result = converter.getIncludedFeaturesForFacet(properties, facetName, isSource);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   @Test
   public void testGetIncludedSourceFeaturesForFacet() throws Exception {
      String facetName = "foo";
      String propertyName = "includedSourceFeatures";
      boolean isSource = true;

      String key1 = "b2.facets." + facetName + "." + propertyName;
      String key2 = "b2." + propertyName;

      testGetIncludedFeatures(key1, facetName, isSource);
      testGetIncludedFeatures(key2, facetName, isSource);

      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      FeaturesConverter converter = new DefaultConverter();

      List<FeatureInclude> result = converter.getIncludedFeaturesForFacet(properties, facetName, isSource);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   private void testGetIncludedFeatures(String key, String facetName, boolean isSource) {
      FeaturesConverter converter = new DefaultConverter();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<FeatureInclude> result;

      // empty
      result = converter.getIncludedFeaturesForFacet(properties, facetName, isSource);
      assertEquals(0, result.size());

      properties.put(key, " ,, ");
      result = converter.getIncludedFeaturesForFacet(properties, facetName, isSource);
      assertEquals(0, result.size());

      // invalid
      properties.put(key, "foo.feature:!.0.0");
      try {
         result = converter.getIncludedFeaturesForFacet(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      properties.put(key, "foo.feature:1.0.0:murks");
      try {
         result = converter.getIncludedFeaturesForFacet(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      properties.put(key, "foo.feature:1.0.0:optional:murks");
      try {
         result = converter.getIncludedFeaturesForFacet(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      // valid
      properties.put(key, "foo.feature, foo.feature:1.0.0, foo.feature:1.0.0:optional");
      result = converter.getIncludedFeaturesForFacet(properties, facetName, isSource);
      assertEquals(3, result.size());

      FeatureInclude requirement;
      requirement = result.get(0);
      assertEquals("foo.feature", requirement.getId());
      assertFalse(requirement.isSetVersion());
      assertEquals("0.0.0", requirement.getVersion());
      assertFalse(requirement.isOptional());

      requirement = result.get(1);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertFalse(requirement.isOptional());

      requirement = result.get(2);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertTrue(requirement.isOptional());
   }

   @Test
   public void testGetIncludedPluginsForFacet() throws Exception {
      String facetName = "foo";
      String propertyName = "includedPlugins";
      boolean isSource = false;

      String key1 = "b2.facets." + facetName + "." + propertyName;
      String key2 = "b2." + propertyName;

      testGetIncludedPlugins(key1, facetName, isSource);
      testGetIncludedPlugins(key2, facetName, isSource);

      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      FeaturesConverter converter = new DefaultConverter();

      List<PluginInclude> result = converter.getIncludedPluginsForFacet(properties, facetName, isSource);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   @Test
   public void testGetIncludedSourcePluginsForFacet() throws Exception {
      String facetName = "foo";
      String propertyName = "includedSourcePlugins";
      boolean isSource = true;

      String key1 = "b2.facets." + facetName + "." + propertyName;
      String key2 = "b2." + propertyName;

      testGetIncludedPlugins(key1, facetName, isSource);
      testGetIncludedPlugins(key2, facetName, isSource);

      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      FeaturesConverter converter = new DefaultConverter();

      List<PluginInclude> result = converter.getIncludedPluginsForFacet(properties, facetName, isSource);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   private void testGetIncludedPlugins(String key, String facetName, boolean isSource) {
      FeaturesConverter converter = new DefaultConverter();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<PluginInclude> result;

      // empty
      result = converter.getIncludedPluginsForFacet(properties, facetName, isSource);
      assertEquals(0, result.size());

      properties.put(key, " ,, ");
      result = converter.getIncludedPluginsForFacet(properties, facetName, isSource);
      assertEquals(0, result.size());

      // invalid
      properties.put(key, "foo.feature:!.0.0");
      try {
         result = converter.getIncludedPluginsForFacet(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      properties.put(key, "foo.feature:1.0.0:murks");
      try {
         result = converter.getIncludedPluginsForFacet(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      properties.put(key, "foo.feature:1.0.0:unpack:murks");
      try {
         result = converter.getIncludedPluginsForFacet(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      // valid
      properties.put(key, "foo.feature, foo.feature:1.0.0, foo.feature:1.0.0:unpack");
      result = converter.getIncludedPluginsForFacet(properties, facetName, isSource);
      assertEquals(3, result.size());

      PluginInclude include;
      include = result.get(0);
      assertEquals("foo.feature", include.getId());
      assertFalse(include.isSetVersion());
      assertEquals("0.0.0", include.getVersion());
      assertFalse(include.isUnpack());

      include = result.get(1);
      assertEquals("foo.feature", include.getId());
      assertEquals("1.0.0", include.getVersion());
      assertFalse(include.isUnpack());

      include = result.get(2);
      assertEquals("foo.feature", include.getId());
      assertEquals("1.0.0", include.getVersion());
      assertTrue(include.isUnpack());
   }

   @Test
   public void testGetRequiredFeaturesForFacet() throws Exception {
      final Method method = FeaturesConverter.class.getDeclaredMethod("getRequiredFeaturesForFacet",
         PropertiesSource.class, String.class, boolean.class);
      final boolean isSource = false;

      final String facetName = "foo";
      final String propertyName = "requiredFeatures";
      testGetRequiredFeaturesOrPluginsAndKeyOrdering(method, facetName, propertyName, isSource);
   }

   @Test
   public void testGetRequiredSourceFeatures() throws Exception {
      final Method method = FeaturesConverter.class.getDeclaredMethod("getRequiredFeaturesForFacet",
         PropertiesSource.class, String.class, boolean.class);
      final boolean isSource = true;

      final String facetName = "foo";
      final String propertyName = "requiredSourceFeatures";
      testGetRequiredFeaturesOrPluginsAndKeyOrdering(method, facetName, propertyName, isSource);
   }

   @Test
   public void testGetRequiredPluginsForFacet() throws Exception {
      final Method method = FeaturesConverter.class.getDeclaredMethod("getRequiredPluginsForFacet",
         PropertiesSource.class, String.class, boolean.class);
      final boolean isSource = false;

      final String facetName = "foo";
      final String propertyName = "requiredPlugins";
      testGetRequiredFeaturesOrPluginsAndKeyOrdering(method, facetName, propertyName, isSource);
   }

   @Test
   public void testGetRequiredSourcePluginsForFacet() throws Exception {
      final Method method = FeaturesConverter.class.getDeclaredMethod("getRequiredPluginsForFacet",
         PropertiesSource.class, String.class, boolean.class);
      final boolean isSource = true;

      final String facetName = "foo";
      final String propertyName = "requiredSourcePlugins";
      testGetRequiredFeaturesOrPluginsAndKeyOrdering(method, facetName, propertyName, isSource);
   }


   private void testGetRequiredFeaturesOrPluginsAndKeyOrdering(final Method method, final String facetName,
      String propertyName, boolean isSource) throws Exception {
      String key1 = "b2.facets." + facetName + "." + propertyName;
      testGetRequiredFeaturesOrPlugins(method, key1, facetName, isSource);

      String key2 = "b2." + propertyName;
      testGetRequiredFeaturesOrPlugins(method, key2, facetName, isSource);

      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      BasicConverter converter = new DefaultConverter();

      List<RuledReference> result = invoke(method, converter, properties, facetName, isSource);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   private void testGetRequiredFeaturesOrPlugins(final Method method, final String key, String facetName,
      boolean isSource) throws Exception {
      BasicConverter converter = new DefaultConverter();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<RuledReference> result;
      RuledReference requirement;

      // empty
      result = invoke(method, converter, properties, facetName, isSource);
      assertEquals(0, result.size());

      properties.put(key, " ,, ");
      result = invoke(method, converter, properties, facetName, isSource);
      assertEquals(0, result.size());

      // invalid
      properties.put(key, "foo.feature:!.0.0");
      try {
         result = invoke(method, converter, properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      properties.put(key, "foo.feature:1.0.0:murks");
      try {
         result = invoke(method, converter, properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      properties.put(key, "foo.feature:1.0.0:perfect:murks");
      try {
         result = invoke(method, converter, properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      // valid
      properties.put(key, "foo.feature, foo.feature:1.0.0, foo.feature:1.0.0:perfect");
      result = invoke(method, converter, properties, facetName, isSource);
      assertEquals(3, result.size());

      requirement = result.get(0);
      assertEquals("foo.feature", requirement.getId());
      assertTrue(requirement.isSetVersion());
      assertEquals("0.0.0", requirement.getVersion());
      assertSame(VersionMatchRule.GREATER_OR_EQUAL, requirement.getVersionMatchRule());

      requirement = result.get(1);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertSame(VersionMatchRule.GREATER_OR_EQUAL, requirement.getVersionMatchRule());

      requirement = result.get(2);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertSame(VersionMatchRule.PERFECT, requirement.getVersionMatchRule());
   }

   @SuppressWarnings("unchecked")
   private static List<RuledReference> invoke(final Method method, BasicConverter converter, PropertiesMap properties,
      String facetName, boolean isSource) throws Exception {
      try {
         return (List<RuledReference>) method.invoke(converter, properties, facetName, isSource);
      }
      catch (InvocationTargetException e) {
         Throwable t = e.getTargetException();
         if (t instanceof Exception) {
            throw (Exception) t;
         }
         if (t instanceof Error) {
            throw (Error) t;
         }
         throw new Error(t);
      }
   }


   @Test
   public void testFacetNameToClassifier() throws Exception {
      BasicConverter converter = new DefaultConverter();
      try {
         converter.getFacetClassifier(null, null);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      PropertiesMap properties = new LinkedPropertiesMap();
      try {
         converter.getFacetClassifier(properties, "");
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      assertEquals("features", converter.getFacetClassifier(properties, "features"));
      assertEquals("features", converter.getFacetClassifier(properties, "Features"));

      properties.put("b2.facets.plugins.classifier", "");

      assertEquals("", converter.getFacetClassifier(properties, "plugins"));
      assertEquals("plugins", converter.getFacetClassifier(properties, "Plugins"));

      assertEquals("_3äng", converter.getFacetClassifier(properties, "3äng"));
      assertEquals("hans_wurst", converter.getFacetClassifier(properties, "Hans Wurst"));
   }

   @Test
   public void testToValidIdentifier() throws Exception {
      try {
         DefaultConverter.toValidId(null);
         fail();
      }
      catch (NullPointerException e) {
      }

      assertEquals("_", DefaultConverter.toValidId(""));
      assertEquals("_", DefaultConverter.toValidId("_"));
      assertEquals("_3er", DefaultConverter.toValidId("3er"));
      assertEquals("hans_wurst", DefaultConverter.toValidId("hans wurst"));
   }

   @Test
   public void testSkipInterpolator() throws Exception {
      PropertiesMap properties = new LinkedPropertiesMap();

      BasicConverter converter = new DefaultConverter();
      assertFalse(converter.isSkipInterpolator(properties));

      properties.put("b2.skipInterpolator", "false");
      assertFalse(converter.isSkipInterpolator(properties));

      properties.put("b2.skipInterpolator", "true");
      assertTrue(converter.isSkipInterpolator(properties));
   }

   @Test
   public void testSkipGenerator() throws Exception {
      PropertiesMap properties = new LinkedPropertiesMap();

      BasicConverter converter = new DefaultConverter();
      assertFalse(converter.isSkipGenerator(properties));

      properties.put("b2.skipGenerator", "false");
      assertFalse(converter.isSkipGenerator(properties));

      properties.put("b2.skipGenerator", "true");
      assertTrue(converter.isSkipGenerator(properties));
   }

   @Test
   public void testGetUpdateSitesForProduct() throws Exception {
      ProductsConverter converter = new DefaultConverter();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<String> sites = converter.getUpdateSitesForProduct(properties, "foo");
      assertEquals(0, sites.size());

      properties.put("b2.products.sites", "http://localhost/p2, http://eclipse.org,,,");
      sites = converter.getUpdateSitesForProduct(properties, "foo");
      assertEquals(2, sites.size());
      assertEquals("http://localhost/p2", sites.get(0));
      assertEquals("http://eclipse.org", sites.get(1));

      properties.put("b2.products.foo.sites", "http://bar");
      sites = converter.getUpdateSitesForProduct(properties, "foo");
      assertEquals(1, sites.size());
      assertEquals("http://bar", sites.get(0));
   }

}
