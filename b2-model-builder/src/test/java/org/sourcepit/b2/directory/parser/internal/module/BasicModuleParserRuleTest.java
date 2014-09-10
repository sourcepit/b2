/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.directory.parser.internal.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.createParsingRequest;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.initModuleDir;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.initPluginDir;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.mkdir;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.sourcepit.b2.directory.parser.module.ModuleParsingRequest;
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
   }

   @Test
   public void testEmptyModule() throws Exception
   {
      final BasicModuleParserRule rule = lookup(BasicModuleParserRule.class);
      // TODO I think we should expect a simple module here because the module dir contains a module.xml file?
      final ModuleParsingRequest request = createParsingRequest(moduleDir);
      assertNull(rule.parse(request));
   }

   @Test
   public void testSimpleModule() throws Exception
   {
      initPluginDir(mkdir(moduleDir, "foo"));

      final BasicModuleParserRule rule = lookup(BasicModuleParserRule.class);
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

      final BasicModuleParserRule rule = lookup(BasicModuleParserRule.class);
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

      final BasicModuleParserRule rule = lookup(BasicModuleParserRule.class);
      final ModuleParsingRequest request = createParsingRequest(moduleDir);
      final BasicModule module = rule.parse(request);

      assertNotNull(module);
      assertEquals(moduleDir, module.getDirectory());
      assertEquals("structured", module.getLayoutId());
      assertEquals(3, module.getFacets().size());
   }

}
