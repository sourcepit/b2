/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
