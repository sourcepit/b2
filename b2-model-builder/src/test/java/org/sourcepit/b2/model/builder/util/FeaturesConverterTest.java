/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

      String brandingPluginId;

      brandingPluginId = converter.getBrandingPluginId(moduleProperties, "foo", "", false);
      assertEquals("foo.branding", brandingPluginId);

      brandingPluginId = converter.getBrandingPluginId(moduleProperties, "foo", null, false);
      assertEquals("foo.branding", brandingPluginId);

      brandingPluginId = converter.getBrandingPluginId(moduleProperties, "foo", "plugins", false);
      assertEquals("foo.plugins.branding", brandingPluginId);

      brandingPluginId = converter.getBrandingPluginId(moduleProperties, "foo", "plugins", true);
      assertEquals("foo.plugins.sources.branding", brandingPluginId);

      moduleProperties.put("b2.featuresSourceClassifier", "murks");
      brandingPluginId = converter.getBrandingPluginId(moduleProperties, "foo", "plugins", true);
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
