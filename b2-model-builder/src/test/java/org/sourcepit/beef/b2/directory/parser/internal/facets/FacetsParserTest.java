/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.facets;

import java.io.File;
import java.util.List;

import org.sourcepit.beef.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.beef.b2.model.module.AbstractFacet;
import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.ModuleModelFactory;

public class FacetsParserTest extends AbstractModuleParserTest
{
   public void testBasic() throws Exception
   {
      FacetsParser facetsParser = lookup();
      assertNotNull(facetsParser);
   }

   public void testNull() throws Exception
   {
      FacetsParser facetsParser = lookup();

      try
      {
         facetsParser.parse(null, null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      try
      {
         facetsParser.parse(new File(""), null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      try
      {
         facetsParser.parse(null, ConverterUtils.TEST_CONVERTER);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }
   }

   public void testNonFacetDir() throws Exception
   {
      final File pluginDir = workspace.importResources("composed-component/structured-layout/plugins/example.ui");
      assertTrue(pluginDir.exists());

      FacetsParser facetsParser = lookup();
      assertNull(facetsParser.parse(pluginDir, ConverterUtils.TEST_CONVERTER));
   }

   public void testSimpleLayout() throws Exception
   {
      File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.exists());

      FacetsParser facetsParser = lookup();

      FacetsParseResult<? extends AbstractFacet> result = facetsParser.parse(moduleDir, ConverterUtils.TEST_CONVERTER);
      assertNotNull(result);

      assertEquals("simple", result.getLayout());

      List<? extends AbstractFacet> facets = result.getFacets();
      assertEquals(4, facets.size());

      BasicModule dummyComponent = ModuleModelFactory.eINSTANCE.createBasicModule();
      dummyComponent.setLayoutId(result.getLayout());
      dummyComponent.getFacets().addAll(facets);

      SimpleLayoutFacetsParserRuleTest.assertSimpleLayout(dummyComponent);
   }

   public void testStructuredLayout() throws Exception
   {
      File moduleDir = workspace.importResources("composed-component/structured-layout");
      assertTrue(moduleDir.exists());

      FacetsParser facetsParser = lookup();

      FacetsParseResult<? extends AbstractFacet> result = facetsParser.parse(moduleDir, ConverterUtils.TEST_CONVERTER);
      assertNotNull(result);

      assertEquals("structured", result.getLayout());

      List<? extends AbstractFacet> facets = result.getFacets();
      assertEquals(5, facets.size());

      BasicModule dummyComponent = ModuleModelFactory.eINSTANCE.createBasicModule();
      dummyComponent.setLayoutId(result.getLayout());
      dummyComponent.getFacets().addAll(facets);

      StructuredLayoutFacetsParserRuleTest.assertStructuredLayout(dummyComponent);
   }

   public void testCompositeComponentDir() throws Exception
   {
      File moduleDir = workspace.importResources("composed-component");
      assertTrue(moduleDir.exists());

      FacetsParser facetsParser = lookup();

      FacetsParseResult<? extends AbstractFacet> result = facetsParser.parse(moduleDir, ConverterUtils.TEST_CONVERTER);
      assertNull(result);
   }

   private FacetsParser lookup() throws Exception
   {
      return (FacetsParser) lookup(FacetsParser.class);
   }
}
