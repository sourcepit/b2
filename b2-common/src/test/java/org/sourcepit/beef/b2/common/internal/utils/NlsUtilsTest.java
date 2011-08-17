/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.common.internal.utils;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.sourcepit.tools.shared.resources.harness.AbstractWorkspaceTest;

public class NlsUtilsTest extends AbstractWorkspaceTest
{
   public void testExtractLocale_Null() throws Exception
   {
      try
      {
         NlsUtils.extractLocale(null, null, null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      Locale locale;

      locale = NlsUtils.extractLocale("module.properties", "module", ".properties");
      assertNull(locale);

      locale = NlsUtils.extractLocale("de", null, null);
      assertEquals(new Locale("de"), locale);

      locale = NlsUtils.extractLocale("de_DE", null, null);
      assertEquals(new Locale("de", "DE"), locale);

      locale = NlsUtils.extractLocale("th_TH_TH", null, null);
      assertEquals(new Locale("th", "TH", "TH"), locale);

      locale = NlsUtils.extractLocale("hans_th_TH_TH.properties", "hans", ".properties");
      assertEquals(new Locale("th", "TH", "TH"), locale);

      // test hidden regex
      locale = NlsUtils.extractLocale("ha([a-z]+)ns_de.properties", "ha([a-z]+)ns", ".properties");
      assertEquals(new Locale("de"), locale);
   }

   public void testGetNlsPropertyFiles() throws Exception
   {
      final File moduleDir = getWorkspace().importResources("component-properties");
      final Map<Locale, File> fileToLocaleMap = NlsUtils.getNlsPropertyFiles(moduleDir, "module", "properties");
      assertNotNull(fileToLocaleMap);
      assertEquals(2, fileToLocaleMap.size());

      File defaultProperties = fileToLocaleMap.remove(NlsUtils.DEFAULT_LOCALE);
      assertNotNull(defaultProperties);
      assertEquals("module.properties", defaultProperties.getName());

      File deProperties = fileToLocaleMap.remove(new Locale("de"));
      assertNotNull(deProperties);
      assertEquals("module_de.properties", deProperties.getName());

      assertTrue(fileToLocaleMap.isEmpty());
   }

   public void testInjectNlsProperties() throws Exception
   {
      final File moduleDir = getWorkspace().importResources("component-properties");
      Properties result = new Properties();
      NlsUtils.injectNlsProperties(result, moduleDir, "module", "properties");

      assertEquals(6, result.size());
      assertEquals("Hello", result.getProperty("name"));
      assertEquals("Hallo", result.getProperty("nls_de.name"));
      assertEquals("This is text belongs to ${name}", result.getProperty("description"));
      assertEquals("Dieser Text gehört zu ${nls_de.name}", result.getProperty("nls_de.description"));
      assertEquals("This key is missing in the de properties file", result.getProperty("defaultOnly"));
      assertEquals("This key is missing in the de properties file", result.getProperty("nls_de.defaultOnly"));
   }
}
