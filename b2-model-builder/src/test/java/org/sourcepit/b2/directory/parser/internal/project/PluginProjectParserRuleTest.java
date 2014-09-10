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

package org.sourcepit.b2.directory.parser.internal.project;

import java.io.File;

import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.PropertiesMap;

/**
 * @author Bernd
 */
public class PluginProjectParserRuleTest extends AbstractModuleParserTest
{
   public void testBasic() throws Exception
   {
      final PluginProjectParserRule parserRule = lookupPluginProjectParserRule();
      assertNotNull(parserRule);
   }

   public void testNull() throws Exception
   {
      final PluginProjectParserRule parserRule = lookupPluginProjectParserRule();
      assertNull(parserRule.parse(null, null));
   }

   public void testNonPluginDir() throws Exception
   {
      workspace.importResources("composed-component/simple-layout");

      final File moduleDir = workspace.getDir();
      assertTrue(moduleDir.exists());

      final PluginProjectParserRule parserRule = lookupPluginProjectParserRule();
      assertNull(parserRule.parse(moduleDir, B2ModelBuildingRequest.newDefaultProperties()));
   }

   public void testParsePluginDirectory() throws Exception
   {
      final PropertiesMap properties = B2ModelBuildingRequest.newDefaultProperties();

      workspace.importResources("composed-component/simple-layout/example.core");

      File pluginDir = workspace.getDir();
      // final File pluginDir = TestResourcesUtils.getTestResource("composed-component/simple-layout/example.core");
      assertTrue(pluginDir.exists());

      final PluginProjectParserRule parserRule = lookupPluginProjectParserRule();
      PluginProject project = parserRule.detect(pluginDir, properties);
      assertNotNull(project);
      assertNull(project.getId());

      parserRule.initialize(project, properties);
      assertEquals(pluginDir, project.getDirectory());
      assertEquals("example.core", project.getId());
      assertEquals("1.0.0.qualifier", project.getVersion());
      assertFalse(project.isTestPlugin());
      assertFalse(project.isFragment());
   }

   public void testParseTestPluginDirectory() throws Exception
   {
      final PropertiesMap properties = B2ModelBuildingRequest.newDefaultProperties();

      workspace.importResources("composed-component/simple-layout/example.core.tests");

      final File pluginDir = workspace.getDir();
      assertTrue(pluginDir.exists());

      final PluginProjectParserRule parserRule = lookupPluginProjectParserRule();
      PluginProject project = parserRule.detect(pluginDir, properties);
      assertNotNull(project);
      assertNull(project.getId());

      parserRule.initialize(project, properties);
      assertEquals(pluginDir, project.getDirectory());
      assertEquals("example.core.tests", project.getId());
      assertEquals("1.0.0.qualifier", project.getVersion());
      assertTrue(project.isTestPlugin());
      assertFalse(project.isFragment());
   }

   public void testParseFragmentDirectory() throws Exception
   {
      final PropertiesMap properties = B2ModelBuildingRequest.newDefaultProperties();

      workspace.importResources("composed-component/simple-layout/example.core.fragment");

      final File pluginDir = workspace.getDir();
      assertTrue(pluginDir.exists());

      final PluginProjectParserRule parserRule = lookupPluginProjectParserRule();
      PluginProject project = parserRule.detect(pluginDir, properties);
      assertNotNull(project);
      assertNull(project.getId());

      parserRule.initialize(project, properties);
      assertEquals(pluginDir, project.getDirectory());
      assertEquals("example.core.fragment", project.getId());
      assertEquals("1.0.0.qualifier", project.getVersion());
      assertFalse(project.isTestPlugin());
      assertTrue(project.isFragment());
   }

   private PluginProjectParserRule lookupPluginProjectParserRule() throws Exception
   {
      return (PluginProjectParserRule) lookup(AbstractProjectParserRule.class, "plugin");
   }
}
