/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.createB2Session;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.createParsingRequest;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.initModuleDir;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.initPluginDir;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.mkdir;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.sourcepit.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.module.BasicModule;

public class BasicModuleParserRuleTest extends AbstractTestEnvironmentTest
{
   private File moduleDir;

   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();

      moduleDir = ws.getRoot();
      initModuleDir(moduleDir);

      final B2SessionService sessionService = gLookup(B2SessionService.class);
      sessionService.setCurrentSession(createB2Session(moduleDir));
   }

   @Test
   public void testEmptyModule() throws Exception
   {
      final BasicModuleParserRule rule = gLookup(BasicModuleParserRule.class);
      // TODO I think we should expect a simple module here because the module dir contains a module.xml file?
      final ModuleParsingRequest request = createParsingRequest(moduleDir);
      assertNull(rule.parse(request));
   }

   @Test
   public void testSimpleModule() throws Exception
   {
      initPluginDir(mkdir(moduleDir, "foo"));

      final BasicModuleParserRule rule = gLookup(BasicModuleParserRule.class);
      final ModuleParsingRequest request = createParsingRequest(moduleDir);
      final BasicModule module = rule.parse(request);

      assertNotNull(module);
      assertEquals(moduleDir, module.getDirectory());
      assertEquals("simple", module.getLayoutId());
      assertEquals(1, module.getFacets().size());
   }

   @Test
   public void testSimpleModuleWithPluginsAndTests() throws Exception
   {
      initPluginDir(mkdir(moduleDir, "foo"));
      initPluginDir(mkdir(moduleDir, "foo.tests"));

      final BasicModuleParserRule rule = gLookup(BasicModuleParserRule.class);
      final ModuleParsingRequest request = createParsingRequest(moduleDir);
      final BasicModule module = rule.parse(request);

      assertNotNull(module);
      assertEquals(moduleDir, module.getDirectory());
      assertEquals("simple", module.getLayoutId());
      assertEquals(2, module.getFacets().size());
   }

   @Test
   public void testStructuredModule() throws Exception
   {
      final File pluginsDir = mkdir(moduleDir, "plugins");
      final File testsDir = mkdir(moduleDir, "tests");
      final File examplesDir = mkdir(moduleDir, "examples");

      initPluginDir(mkdir(pluginsDir, "foo"));
      initPluginDir(mkdir(testsDir, "foo.tests"));
      initPluginDir(mkdir(examplesDir, "foo.example"));

      final BasicModuleParserRule rule = gLookup(BasicModuleParserRule.class);
      final ModuleParsingRequest request = createParsingRequest(moduleDir);
      final BasicModule module = rule.parse(request);

      assertNotNull(module);
      assertEquals(moduleDir, module.getDirectory());
      assertEquals("structured", module.getLayoutId());
      assertEquals(3, module.getFacets().size());
   }

}
