/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
