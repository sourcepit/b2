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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.sourcepit.b2.model.module.VersionMatchRule.COMPATIBLE;
import static org.sourcepit.b2.model.module.VersionMatchRule.EQUIVALENT;
import static org.sourcepit.b2.model.module.VersionMatchRule.GREATER_OR_EQUAL;
import static org.sourcepit.b2.model.module.VersionMatchRule.PERFECT;

import java.util.List;

import org.junit.Test;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class ProductsConverterTest {

   @Test
   public void testGetIncludedPluginsForProduct() throws Exception {
      String productId = "foo";
      String propertyName = "plugins";

      String key1 = "b2.products." + productId + "." + propertyName;
      String key2 = "b2.products." + propertyName;

      testGetIncludedPluginsForProduct(key1, productId);
      testGetIncludedPluginsForProduct(key2, productId);
   }

   private void testGetIncludedPluginsForProduct(String key, String productId) {
      ProductsConverter converter = new DefaultConverter();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<RuledReference> result;

      // empty
      result = converter.getIncludedPluginsForProduct(properties, productId, GREATER_OR_EQUAL);
      assertEquals(0, result.size());

      properties.put(key, " ,, ");
      result = converter.getIncludedPluginsForProduct(properties, productId, GREATER_OR_EQUAL);
      assertEquals(0, result.size());

      // invalid
      properties.put(key, "foo.feature:!.0.0");
      try {
         result = converter.getIncludedPluginsForProduct(properties, productId, GREATER_OR_EQUAL);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      properties.put(key, "foo.feature:1.0.0:murks");
      try {
         result = converter.getIncludedPluginsForProduct(properties, productId, GREATER_OR_EQUAL);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      properties.put(key, "foo.feature:1.0.0:unpack:murks");
      try {
         result = converter.getIncludedPluginsForProduct(properties, productId, GREATER_OR_EQUAL);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      // valid
      properties.put(key, "foo.feature, foo.feature:1.0.0, foo.feature:2.0.0:compatible");
      result = converter.getIncludedPluginsForProduct(properties, productId, GREATER_OR_EQUAL);
      assertEquals(3, result.size());

      RuledReference include;
      include = result.get(0);
      assertEquals("foo.feature", include.getId());
      assertTrue(include.isSetVersion());
      assertEquals("0.0.0", include.getVersion());
      assertSame(GREATER_OR_EQUAL, include.getVersionMatchRule());

      include = result.get(1);
      assertEquals("foo.feature", include.getId());
      assertEquals("1.0.0", include.getVersion());
      assertSame(GREATER_OR_EQUAL, include.getVersionMatchRule());

      include = result.get(2);
      assertEquals("foo.feature", include.getId());
      assertEquals("2.0.0", include.getVersion());
      assertSame(COMPATIBLE, include.getVersionMatchRule());
   }

   @Test
   public void testGetIncludedSourceFeaturesForProduct() throws Exception {
      String productId = "foo";
      String propertyName = "features";

      String key1 = "b2.products." + productId + "." + propertyName;
      String key2 = "b2.products." + propertyName;

      testGetIncludedFeaturesForProduct(key1, productId);
      testGetIncludedFeaturesForProduct(key2, productId);

      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      ProductsConverter converter = new DefaultConverter();

      List<RuledReference> result = converter.getIncludedFeaturesForProduct(properties, productId, GREATER_OR_EQUAL);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   @Test
   public void testGetDefaultVersionMatchRuleForProduct() throws Exception {
      PropertiesMap properties = new LinkedPropertiesMap();

      ProductsConverter converter = new DefaultConverter();
      try {
         converter.getDefaultVersionMatchRuleForProduct(properties, "foo.product");
         fail();
      }
      catch (NullPointerException e) {
      }

      VersionMatchRule matchRule;

      properties.put("b2.products.defaultVersionMatchRule", "compatible");
      matchRule = converter.getDefaultVersionMatchRuleForProduct(properties, "foo.product");
      assertSame(COMPATIBLE, matchRule);

      properties.put("b2.products.foo.product.defaultVersionMatchRule", "perfect");
      matchRule = converter.getDefaultVersionMatchRuleForProduct(properties, "foo.product");
      assertSame(PERFECT, matchRule);
   }

   @Test
   public void testGetVersionMatchRuleForProductInclude() throws Exception {
      PropertiesMap properties = new LinkedPropertiesMap();

      ProductsConverter converter = new DefaultConverter();

      VersionMatchRule matchRule;

      matchRule = converter.getVersionMatchRuleForProductInclude(properties, "foo.product", "foo.feature", PERFECT);
      assertSame(PERFECT, matchRule);

      properties.put("b2.products.versionMatchRules", "foo.**,bar.**=compatible ;\n **=greaterOrEqual");

      matchRule = converter.getVersionMatchRuleForProductInclude(properties, "foo.product", "foo.feature", PERFECT);
      assertSame(COMPATIBLE, matchRule);

      matchRule = converter.getVersionMatchRuleForProductInclude(properties, "foo.product", "bar.plugin.source",
         PERFECT);
      assertSame(COMPATIBLE, matchRule);

      matchRule = converter.getVersionMatchRuleForProductInclude(properties, "foo.product", "hans.damp", PERFECT);
      assertSame(GREATER_OR_EQUAL, matchRule);

      properties.put("b2.products.foo.product.versionMatchRules", "**=equivalent");
      matchRule = converter.getVersionMatchRuleForProductInclude(properties, "foo.product", "hans.damp", PERFECT);
      assertSame(EQUIVALENT, matchRule);
   }

   private void testGetIncludedFeaturesForProduct(String key, String facetName) {
      ProductsConverter converter = new DefaultConverter();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<RuledReference> result;

      // empty
      result = converter.getIncludedFeaturesForProduct(properties, facetName, GREATER_OR_EQUAL);
      assertEquals(0, result.size());

      properties.put(key, " ,, ");
      result = converter.getIncludedFeaturesForProduct(properties, facetName, GREATER_OR_EQUAL);
      assertEquals(0, result.size());

      // invalid
      properties.put(key, "foo.feature:!.0.0");
      try {
         result = converter.getIncludedFeaturesForProduct(properties, facetName, GREATER_OR_EQUAL);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      properties.put(key, "foo.feature:1.0.0:murks");
      try {
         result = converter.getIncludedFeaturesForProduct(properties, facetName, GREATER_OR_EQUAL);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      properties.put(key, "foo.feature:1.0.0:optional:murks");
      try {
         result = converter.getIncludedFeaturesForProduct(properties, facetName, GREATER_OR_EQUAL);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      // valid
      properties.put(key, "foo.feature, foo.feature:1.0.0, foo.feature:2.0.0:compatible");
      result = converter.getIncludedFeaturesForProduct(properties, facetName, GREATER_OR_EQUAL);
      assertEquals(3, result.size());

      RuledReference requirement;
      requirement = result.get(0);
      assertEquals("foo.feature", requirement.getId());
      assertTrue(requirement.isSetVersion());
      assertEquals("0.0.0", requirement.getVersion());
      assertSame(GREATER_OR_EQUAL, requirement.getVersionMatchRule());

      requirement = result.get(1);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertSame(GREATER_OR_EQUAL, requirement.getVersionMatchRule());

      requirement = result.get(2);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("2.0.0", requirement.getVersion());
      assertSame(COMPATIBLE, requirement.getVersionMatchRule());
   }

   @Test
   public void testGetIncludedFeaturesForProduct_VersionMatchRules() throws Exception {
      ProductsConverter converter = new DefaultConverter();

      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("b2.products.features", "foo.feature,bar.feature");

      List<RuledReference> includes;
      RuledReference include;

      includes = converter.getIncludedFeaturesForProduct(properties, "foo.product", GREATER_OR_EQUAL);
      assertEquals(2, includes.size());

      include = includes.get(0);
      assertEquals("foo.feature", include.getId());
      assertEquals("0.0.0", include.getVersion());
      assertSame(GREATER_OR_EQUAL, include.getVersionMatchRule());

      include = includes.get(1);
      assertEquals("bar.feature", include.getId());
      assertEquals("0.0.0", include.getVersion());
      assertSame(GREATER_OR_EQUAL, include.getVersionMatchRule());

      properties.put("b2.products.versionMatchRules", "bar.**=perfect;foo.**=compatible;");

      includes = converter.getIncludedFeaturesForProduct(properties, "foo.product", GREATER_OR_EQUAL);
      assertEquals(2, includes.size());

      include = includes.get(0);
      assertEquals("foo.feature", include.getId());
      assertEquals("0.0.0", include.getVersion());
      assertSame(COMPATIBLE, include.getVersionMatchRule());

      include = includes.get(1);
      assertEquals("bar.feature", include.getId());
      assertEquals("0.0.0", include.getVersion());
      assertSame(PERFECT, include.getVersionMatchRule());
   }

   @Test
   public void testGetIncludedPluginsForProduct_VersionMatchRules() throws Exception {
      ProductsConverter converter = new DefaultConverter();

      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("b2.products.plugins", "foo.plugins,bar.plugins");

      List<RuledReference> includes;
      RuledReference include;

      includes = converter.getIncludedPluginsForProduct(properties, "foo.product", GREATER_OR_EQUAL);
      assertEquals(2, includes.size());

      include = includes.get(0);
      assertEquals("foo.plugins", include.getId());
      assertEquals("0.0.0", include.getVersion());
      assertSame(GREATER_OR_EQUAL, include.getVersionMatchRule());

      include = includes.get(1);
      assertEquals("bar.plugins", include.getId());
      assertEquals("0.0.0", include.getVersion());
      assertSame(GREATER_OR_EQUAL, include.getVersionMatchRule());

      properties.put("b2.products.versionMatchRules", "bar.**=perfect;foo.**=compatible;");

      includes = converter.getIncludedPluginsForProduct(properties, "foo.product", GREATER_OR_EQUAL);
      assertEquals(2, includes.size());

      include = includes.get(0);
      assertEquals("foo.plugins", include.getId());
      assertEquals("0.0.0", include.getVersion());
      assertSame(COMPATIBLE, include.getVersionMatchRule());

      include = includes.get(1);
      assertEquals("bar.plugins", include.getId());
      assertEquals("0.0.0", include.getVersion());
      assertSame(PERFECT, include.getVersionMatchRule());
   }

}
