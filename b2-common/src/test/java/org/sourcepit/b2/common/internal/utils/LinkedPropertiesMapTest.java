/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.common.internal.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.sourcepit.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;

import junit.framework.TestCase;

public class LinkedPropertiesMapTest extends TestCase
{
   public void testGet() throws Exception
   {
      PropertiesMap propertiesMap = new LinkedPropertiesMap();
      assertEquals(null, propertiesMap.get("key"));
      propertiesMap.put("key", "value");
      assertEquals("value", propertiesMap.get("key"));
      assertEquals(null, propertiesMap.get("key2"));
      assertEquals("value2", propertiesMap.get("key2", "value2"));
   }

   public void testDefaultPropeties() throws Exception
   {
      LinkedPropertiesMap propertiesMap = new LinkedPropertiesMap();
      assertNull(propertiesMap.getDefaultProperties());
      assertNull(propertiesMap.get("key"));

      Map<String, String> defaults = new HashMap<String, String>();
      defaults.put("key", "value");

      propertiesMap.setDefaultProperties(defaults);

      assertSame(defaults, propertiesMap.getDefaultProperties());

      assertEquals("value", propertiesMap.get("key"));

      assertFalse(propertiesMap.containsKey("key"));

      propertiesMap.put("key", "newValue");
      assertEquals("newValue", propertiesMap.get("key"));
      assertTrue(propertiesMap.containsKey("key"));
   }

   public void testToJavaProperties() throws Exception
   {
      LinkedPropertiesMap propertiesMap = new LinkedPropertiesMap();
      propertiesMap.put("key", "value");

      Properties javaProperties = propertiesMap.toJavaProperties();
      assertEquals("value", javaProperties.getProperty("key"));
      assertNull(javaProperties.getProperty("key2"));
      assertTrue(javaProperties.containsKey("key"));
      assertFalse(javaProperties.containsKey("key2"));

      Map<String, String> defaults = new HashMap<String, String>();
      defaults.put("key2", "value2");
      propertiesMap.setDefaultProperties(defaults);

      javaProperties = propertiesMap.toJavaProperties();
      assertEquals("value", javaProperties.getProperty("key"));
      assertEquals("value2", javaProperties.getProperty("key2"));
      assertTrue(javaProperties.containsKey("key"));
      assertFalse(javaProperties.containsKey("key2"));
   }

   public void testBoolean() throws Exception
   {
      LinkedPropertiesMap propertiesMap = new LinkedPropertiesMap();
      assertTrue(propertiesMap.getBoolean("key", true));
      assertFalse(propertiesMap.getBoolean("key", false));

      propertiesMap.put("key", "true");
      assertTrue(propertiesMap.getBoolean("key", false));

      propertiesMap.put("key", "false");
      assertFalse(propertiesMap.getBoolean("key", true));

      propertiesMap.put("key", "murks");
      assertFalse(propertiesMap.getBoolean("key", true));
   }
}