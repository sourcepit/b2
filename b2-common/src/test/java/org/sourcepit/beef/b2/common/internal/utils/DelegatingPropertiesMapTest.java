/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.common.internal.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

public class DelegatingPropertiesMapTest extends TestCase
{
   public void testToJavaProperties() throws Exception
   {
      Map<String, String> delegate = new HashMap<String, String>();
      delegate.put("key", "value");

      PropertiesMap propertiesMap = new DelegatingPropertiesMap(delegate);
      assertEquals("value", propertiesMap.get("key"));
      assertEquals("value2", propertiesMap.get("key2", "value2"));
      assertTrue(propertiesMap.containsKey("key"));
      assertFalse(propertiesMap.containsKey("key2"));
      
      Properties javaProperties = propertiesMap.toJavaProperties();
      assertEquals("value", javaProperties.getProperty("key"));
      assertEquals("value2", javaProperties.getProperty("key2", "value2"));
      assertTrue(javaProperties.containsKey("key"));
      assertFalse(javaProperties.containsKey("key2"));
   }
   
   public void testToJavaProperties2() throws Exception
   {
      PropertiesMap defaults = new LinkedPropertiesMap();
      defaults.put("key2", "value2");
      
      LinkedPropertiesMap delegate = new LinkedPropertiesMap();
      delegate.setDefaultProperties(defaults);
      delegate.put("key", "value");

      PropertiesMap propertiesMap = new DelegatingPropertiesMap(delegate);
      assertEquals("value", propertiesMap.get("key"));
      assertEquals("value2", propertiesMap.get("key2", "value2"));
      assertTrue(propertiesMap.containsKey("key"));
      assertFalse(propertiesMap.containsKey("key2"));
      
      Properties javaProperties = propertiesMap.toJavaProperties();
      assertEquals("value", javaProperties.getProperty("key"));
      assertEquals("value2", javaProperties.getProperty("key2", "value2"));
      assertTrue(javaProperties.containsKey("key"));
      assertFalse(javaProperties.containsKey("key2"));
   }
}
