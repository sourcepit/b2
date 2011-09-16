/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.module;

import java.io.File;

import org.sourcepit.beef.b2.directory.parser.internal.facets.SimpleLayoutFacetsParserRuleTest;
import org.sourcepit.beef.b2.directory.parser.internal.facets.StructuredLayoutFacetsParserRuleTest;
import org.sourcepit.beef.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.module.BasicModule;

public class BasicModuleParserRuleTest extends AbstractModuleParserTest
{
   public void testBasic() throws Exception
   {
      BasicModuleParserRule parserRule = lookup();
      assertNotNull(parserRule);
   }

   public void testSimpleComponent() throws Exception
   {
      File coreResources = workspace.importResources("composed-component/simple-layout");
      assertTrue(coreResources.canRead());

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setModuleDirectory(coreResources);
      request.setConverter(ConverterUtils.TEST_CONVERTER);

      BasicModuleParserRule parserRule = lookup();
      BasicModule module = (BasicModule) parserRule.parse(request);
      assertNotNull(module);

      IConverter converter = request.getConverter();
      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals(module.getId(), converter.getModuleId(coreResources));
      assertEquals(module.getVersion(), converter.getModuleVersion());

      SimpleLayoutFacetsParserRuleTest.assertSimpleLayout(module);
   }

   public void testStructuredComponent() throws Exception
   {
      File coreResources = workspace.importResources("composed-component/structured-layout");
      assertTrue(coreResources.canRead());

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setModuleDirectory(coreResources);
      request.setConverter(ConverterUtils.TEST_CONVERTER);

      BasicModuleParserRule parserRule = lookup();
      BasicModule module = (BasicModule) parserRule.parse(request);
      assertNotNull(module);

      IConverter converter = request.getConverter();
      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals(module.getId(), converter.getModuleId(coreResources));
      assertEquals(module.getVersion(), converter.getModuleVersion());

      StructuredLayoutFacetsParserRuleTest.assertStructuredLayout(module);
   }

   public void testCompositeComponent() throws Exception
   {
      File coreResources = workspace.importResources("composed-component");
      assertTrue(coreResources.canRead());

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setModuleDirectory(coreResources);
      request.setConverter(ConverterUtils.TEST_CONVERTER);

      BasicModuleParserRule parserRule = lookup();
      BasicModule module = (BasicModule) parserRule.parse(request);
      assertNull(module);
   }

   private BasicModuleParserRule lookup() throws Exception
   {
      return (BasicModuleParserRule) lookup(AbstractModuleParserRule.class, "module");
   }
}
