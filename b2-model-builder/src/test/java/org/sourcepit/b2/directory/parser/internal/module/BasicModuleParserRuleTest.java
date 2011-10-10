/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.io.File;

import org.sourcepit.b2.directory.parser.internal.facets.SimpleLayoutFacetsParserRuleTest;
import org.sourcepit.b2.directory.parser.internal.facets.StructuredLayoutFacetsParserRuleTest;
import org.sourcepit.b2.directory.parser.internal.module.AbstractModuleParserRule;
import org.sourcepit.b2.directory.parser.internal.module.BasicModuleParserRule;
import org.sourcepit.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.BasicModule;

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
