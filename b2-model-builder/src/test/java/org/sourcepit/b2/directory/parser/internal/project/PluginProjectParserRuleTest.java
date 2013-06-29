/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
