/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.module;

import java.io.File;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.EList;
import org.sourcepit.beef.b2.common.internal.utils.NlsUtils;
import org.sourcepit.beef.b2.directory.parser.module.IModuleParser;
import org.sourcepit.beef.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.beef.b2.internal.model.BasicModule;
import org.sourcepit.beef.b2.internal.model.PluginProject;
import org.sourcepit.beef.b2.internal.model.PluginsFacet;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.ConverterUtils;

public class ModuleParserTest extends AbstractModuleParserTest
{
   public void testBasic() throws Exception
   {
      IModuleParser parser = lookup();
      assertNotNull(parser);
   }

   public void testNull() throws Exception
   {
      IModuleParser parser = lookup();
      try
      {
         parser.parse(null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);

      try
      {
         parser.parse(request);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      request.setModuleDirectory(new File(""));
      request.setConverter(null);

      try
      {
         parser.parse(request);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }
   }

   public void testSimpleComponent_WithoutNlsProperties() throws Exception
   {
      File coreResources = workspace.importResources("composed-component/simple-layout");
      assertTrue(coreResources.canRead());

      FileUtils.forceDelete(new File(coreResources, "module.properties"));
      FileUtils.forceDelete(new File(coreResources, "module_de.properties"));

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(coreResources);

      ModuleParser modelParser = lookup();
      BasicModule module = (BasicModule) modelParser.parse(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals(module.getId(), request.getConverter().getModuleId(coreResources));
      assertEquals(module.getVersion(), request.getConverter().getModuleVersion());

      EList<Locale> locales = module.getLocales();
      assertEquals(1, locales.size());
      assertEquals(NlsUtils.DEFAULT_LOCALE, locales.get(0));
   }

   public void testSimpleComponent() throws Exception
   {
      File coreResources = workspace.importResources("composed-component/simple-layout");
      assertTrue(coreResources.canRead());

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(coreResources);

      ModuleParser modelParser = lookup();
      BasicModule module = (BasicModule) modelParser.parse(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals(module.getId(), request.getConverter().getModuleId(coreResources));
      assertEquals(module.getVersion(), request.getConverter().getModuleVersion());

      EList<Locale> locales = module.getLocales();
      assertFalse(locales.isEmpty());
      assertEquals(new Locale(""), locales.get(0)); // default locale
      assertEquals(new Locale("de"), locales.get(1));

      assertEquals(4, module.getFacets().size());

      PluginsFacet pluginsFacet = module.getFacetByName("plugins");
      assertNotNull(pluginsFacet);

      EList<PluginProject> plugins = pluginsFacet.getProjects();
      assertEquals(2, plugins.size());

      PluginsFacet testsFacet = module.getFacetByName("tests");
      assertNotNull(testsFacet);

      EList<PluginProject> tests = pluginsFacet.getProjects();
      assertEquals(2, tests.size());
   }

   private ModuleParser lookup() throws Exception
   {
      return lookup(ModuleParser.class);
   }
}
