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

import static org.junit.Assert.*;

import org.junit.Test;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class FeaturesConverterTest
{

   @Test
   public void testGetBrandingPluginId()
   {
      FeaturesConverter converter = new DefaultConverter();

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.assemblies", "main, test");
      moduleProperties.put("b2.assemblies.main.classifier", "");

      String brandingPluginId;

      try
      {
         brandingPluginId = converter.getBrandingPluginIdForAssembly(moduleProperties, null, "foo");
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      brandingPluginId = converter.getBrandingPluginIdForAssembly(moduleProperties, "main", "foo");
      assertEquals("foo.branding", brandingPluginId);

      brandingPluginId = converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", false);
      assertEquals("foo.plugins.branding", brandingPluginId);

      brandingPluginId = converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", true);
      assertEquals("foo.plugins.sources.branding", brandingPluginId);

      moduleProperties.put("b2.featuresSourceClassifier", "murks");
      brandingPluginId = converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", true);
      assertEquals("foo.plugins.murks.branding", brandingPluginId);
   }

   @Test
   public void testGetBrandingPluginIdForAssembly() throws Exception
   {
      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.assemblies", "main, test");
      moduleProperties.put("b2.assemblies.main.classifier", "");

      FeaturesConverter converter = new DefaultConverter();

      try
      {
         converter.getBrandingPluginIdForAssembly(moduleProperties, null, "foo");
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      assertEquals("foo.branding", converter.getBrandingPluginIdForAssembly(moduleProperties, "main", "foo"));
      assertEquals("foo.test.branding", converter.getBrandingPluginIdForAssembly(moduleProperties, "test", "foo"));

      moduleProperties.put("b2.assemblies.main.brandingPluginId", "bar");

      assertEquals("bar", converter.getBrandingPluginIdForAssembly(moduleProperties, "main", "foo"));
      assertEquals("foo.test.branding", converter.getBrandingPluginIdForAssembly(moduleProperties, "test", "foo"));
   }

   @Test
   public void testGetBrandingPluginIdForFacet() throws Exception
   {
      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.assemblies", "main, test");
      moduleProperties.put("b2.assemblies.main.classifier", "");

      FeaturesConverter converter = new DefaultConverter();

      try
      {
         converter.getBrandingPluginIdForFacet(moduleProperties, null, "foo", false);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      assertEquals("foo.plugins.branding",
         converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", false));
      assertEquals("foo.tests.branding", converter.getBrandingPluginIdForFacet(moduleProperties, "tests", "foo", false));
      assertEquals("foo.plugins.sources.branding",
         converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", true));
      assertEquals("foo.tests.sources.branding",
         converter.getBrandingPluginIdForFacet(moduleProperties, "tests", "foo", true));

      moduleProperties.put("b2.facets.plugins.brandingPluginId", "bar");

      assertEquals("bar", converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", false));
      assertEquals("foo.tests.branding", converter.getBrandingPluginIdForFacet(moduleProperties, "tests", "foo", false));
      assertEquals("foo.plugins.sources.branding",
         converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", true));
      assertEquals("foo.tests.sources.branding",
         converter.getBrandingPluginIdForFacet(moduleProperties, "tests", "foo", true));

      moduleProperties.put("b2.facets.plugins.sourceBrandingPluginId", "bar.sources");

      assertEquals("bar", converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", false));
      assertEquals("foo.tests.branding", converter.getBrandingPluginIdForFacet(moduleProperties, "tests", "foo", false));
      assertEquals("bar.sources", converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", true));
      assertEquals("foo.tests.sources.branding",
         converter.getBrandingPluginIdForFacet(moduleProperties, "tests", "foo", true));
   }

   @Test
   public void testGetFeatureIdForAssembly() throws Exception
   {
      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.assemblies", "main, test");
      moduleProperties.put("b2.assemblies.main.classifier", "");

      FeaturesConverter converter = new DefaultConverter();

      try
      {
         converter.getFeatureIdForAssembly(moduleProperties, null, "foo");
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      assertEquals("foo.feature", converter.getFeatureIdForAssembly(moduleProperties, "main", "foo"));
      assertEquals("foo.test.feature", converter.getFeatureIdForAssembly(moduleProperties, "test", "foo"));

      moduleProperties.put("b2.assemblies.main.featureId", "bar");

      assertEquals("bar", converter.getFeatureIdForAssembly(moduleProperties, "main", "foo"));
      assertEquals("foo.test.feature", converter.getFeatureIdForAssembly(moduleProperties, "test", "foo"));
   }

   @Test
   public void testGetFeatureIdForFacet() throws Exception
   {
      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.assemblies", "main, test");
      moduleProperties.put("b2.assemblies.main.classifier", "");

      FeaturesConverter converter = new DefaultConverter();

      try
      {
         converter.getFeatureIdForFacet(moduleProperties, null, "foo", false);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      assertEquals("foo.plugins.feature", converter.getFeatureIdForFacet(moduleProperties, "plugins", "foo", false));
      assertEquals("foo.tests.feature", converter.getFeatureIdForFacet(moduleProperties, "tests", "foo", false));
      assertEquals("foo.plugins.sources.feature",
         converter.getFeatureIdForFacet(moduleProperties, "plugins", "foo", true));
      assertEquals("foo.tests.sources.feature", converter.getFeatureIdForFacet(moduleProperties, "tests", "foo", true));

      moduleProperties.put("b2.facets.plugins.featureId", "bar");

      assertEquals("bar", converter.getFeatureIdForFacet(moduleProperties, "plugins", "foo", false));
      assertEquals("foo.tests.feature", converter.getFeatureIdForFacet(moduleProperties, "tests", "foo", false));
      assertEquals("foo.plugins.sources.feature",
         converter.getFeatureIdForFacet(moduleProperties, "plugins", "foo", true));
      assertEquals("foo.tests.sources.feature", converter.getFeatureIdForFacet(moduleProperties, "tests", "foo", true));

      moduleProperties.put("b2.facets.plugins.sourceFeatureId", "bar.sources");

      assertEquals("bar", converter.getFeatureIdForFacet(moduleProperties, "plugins", "foo", false));
      assertEquals("foo.tests.feature", converter.getFeatureIdForFacet(moduleProperties, "tests", "foo", false));
      assertEquals("bar.sources", converter.getFeatureIdForFacet(moduleProperties, "plugins", "foo", true));
      assertEquals("foo.tests.sources.feature", converter.getFeatureIdForFacet(moduleProperties, "tests", "foo", true));
   }

   @Test
   public void testFeaturesAppendix() throws Exception
   {
      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.assemblies", "main, test");
      moduleProperties.put("b2.assemblies.main.classifier", "");

      FeaturesConverter converter = new DefaultConverter();

      assertEquals("foo.feature", converter.getFeatureIdForAssembly(moduleProperties, "main", "foo"));
      assertEquals("foo.test.feature", converter.getFeatureIdForAssembly(moduleProperties, "test", "foo"));
      assertEquals("foo.plugins.feature", converter.getFeatureIdForFacet(moduleProperties, "plugins", "foo", false));
      assertEquals("foo.plugins.sources.feature",
         converter.getFeatureIdForFacet(moduleProperties, "plugins", "foo", true));

      moduleProperties.put("b2.featuresAppendix", "");

      assertEquals("foo", converter.getFeatureIdForAssembly(moduleProperties, "main", "foo"));
      assertEquals("foo.test", converter.getFeatureIdForAssembly(moduleProperties, "test", "foo"));
      assertEquals("foo.plugins", converter.getFeatureIdForFacet(moduleProperties, "plugins", "foo", false));
      assertEquals("foo.plugins.sources", converter.getFeatureIdForFacet(moduleProperties, "plugins", "foo", true));
   }

   @Test
   public void testBrandingPluginsAppendix() throws Exception
   {
      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.assemblies", "main, test");
      moduleProperties.put("b2.assemblies.main.classifier", "");

      FeaturesConverter converter = new DefaultConverter();
      assertEquals("foo.branding", converter.getBrandingPluginIdForAssembly(moduleProperties, "main", "foo"));
      assertEquals("foo.test.branding", converter.getBrandingPluginIdForAssembly(moduleProperties, "test", "foo"));
      assertEquals("foo.plugins.branding",
         converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", false));
      assertEquals("foo.plugins.sources.branding",
         converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", true));

      moduleProperties.put("b2.brandingPluginsAppendix", "brand");

      assertEquals("foo.brand", converter.getBrandingPluginIdForAssembly(moduleProperties, "main", "foo"));
      assertEquals("foo.test.brand", converter.getBrandingPluginIdForAssembly(moduleProperties, "test", "foo"));
      assertEquals("foo.plugins.brand",
         converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", false));
      assertEquals("foo.plugins.sources.brand",
         converter.getBrandingPluginIdForFacet(moduleProperties, "plugins", "foo", true));
   }

   @Test
   public void testIsSkipBrandingPlugins()
   {
      FeaturesConverter converter = new DefaultConverter();

      PropertiesMap moduleProperties = new LinkedPropertiesMap();

      assertFalse(converter.isSkipBrandingPlugins(moduleProperties));

      moduleProperties.put("b2.skipBrandingPlugins", "true");
      assertTrue(converter.isSkipBrandingPlugins(moduleProperties));

      moduleProperties.put("b2.skipBrandingPlugins", "false");
      assertFalse(converter.isSkipBrandingPlugins(moduleProperties));
   }

}
