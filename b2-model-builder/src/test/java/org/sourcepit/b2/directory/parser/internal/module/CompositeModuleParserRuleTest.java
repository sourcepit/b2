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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.sourcepit.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.b2.directory.parser.module.WhitelistModuleFilter;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;

public class CompositeModuleParserRuleTest extends AbstractTestEnvironmentTest
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
      final B2SessionService sessionService = gLookup(B2SessionService.class);
      sessionService.setCurrentProjectDirs(createB2Session(moduleDir));

      final BasicModuleParserRule rule = gLookup(BasicModuleParserRule.class);
      // TODO I think we should expect a simple module here because the module dir contains a module.xml file?
      final ModuleParsingRequest request = createParsingRequest(moduleDir);
      assertNull(rule.parse(request));
   }

   @Test
   public void testSimple() throws Exception
   {
      final File subModule1Dir = mkdir(moduleDir, "sub-module-1");
      initModuleDir(subModule1Dir);
      initPluginDir(mkdir(subModule1Dir, "foo1"));
      final File subModule2Dir = mkdir(moduleDir, "sub-module-2");
      initModuleDir(subModule2Dir);
      initPluginDir(mkdir(subModule2Dir, "foo2"));

      final ModuleParsingRequest request = createParsingRequest(moduleDir);

      prepareSession(request.getModulesCache(), subModule1Dir, subModule2Dir);

      final CompositeModuleParserRule rule = gLookup(CompositeModuleParserRule.class);
      final CompositeModule module = rule.parse(request);
      assertNotNull(module);
      assertEquals(moduleDir, module.getDirectory());
      assertEquals(2, module.getModules().size());
   }

   /*
    * TODO: This test ensures that only sub-modules which are contained in the current session will be visited. This
    * mechanism should be replaced by a recursion or visitor strategy that relies directly on the Maven session so that
    * we can delete the B2Session code
    */
   @Test
   public void testNotRecursive() throws Exception
   {
      final File subModule1Dir = mkdir(moduleDir, "sub-module-1");
      initPluginDir(mkdir(subModule1Dir, "foo1"));
      initModuleDir(subModule1Dir);
      final File subModule2Dir = mkdir(moduleDir, "sub-module-2");
      initModuleDir(subModule2Dir);
      initPluginDir(mkdir(subModule2Dir, "foo2"));

      final ModuleParsingRequest request = createParsingRequest(moduleDir);
      
      request.setModuleFilter(new WhitelistModuleFilter());
      prepareSession(request.getModulesCache());

      final CompositeModuleParserRule rule = gLookup(CompositeModuleParserRule.class);
      CompositeModule module = rule.parse(request);
      assertNotNull(module);
      assertEquals(moduleDir, module.getDirectory());
      assertEquals(0, module.getModules().size());

      request.setModuleFilter(new WhitelistModuleFilter(subModule1Dir));
      prepareSession(request.getModulesCache(), subModule1Dir);

      module = rule.parse(request);
      assertNotNull(module);
      assertEquals(moduleDir, module.getDirectory());
      assertEquals(1, module.getModules().size());
   }

   private void prepareSession(Map<File, AbstractModule> modules, File... subModuleDirs)
   {
      final List<File> moduleDirs = new ArrayList<File>();
      if (subModuleDirs != null)
      {
         Collections.addAll(moduleDirs, subModuleDirs);
      }
      moduleDirs.add(moduleDir);

      final List<File> session = createB2Session(moduleDirs.toArray(new File[moduleDirs.size()]));

      final B2SessionService sessionService = gLookup(B2SessionService.class);
      sessionService.setCurrentProjectDirs(session);
      sessionService.setCurrentModules(new ArrayList<AbstractModule>());

      if (subModuleDirs != null)
      {
         BasicModuleParserRule basicRule = gLookup(BasicModuleParserRule.class);
         for (int i = 0; i < subModuleDirs.length; i++)
         {
            BasicModule module = basicRule.doParse(createParsingRequest(subModuleDirs[i]));
            sessionService.getCurrentModules().add(module);
            modules.put(module.getDirectory(), module);
         }
      }
   }
}
