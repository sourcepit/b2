/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.StrictReference;

public class FeatureProjectParserRuleTest extends AbstractModuleParserTest
{
   public void testBasic() throws Exception
   {
      final FeatureProjectParserRule parserRule = lookupFeatureProjectParserRule();
      assertNotNull(parserRule);
   }

   public void testNull() throws Exception
   {
      final FeatureProjectParserRule parserRule = lookupFeatureProjectParserRule();
      assertNull(parserRule.parse(null, null));
   }

   public void testNonPluginDir() throws Exception
   {
      final File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.exists());

      final FeatureProjectParserRule parserRule = lookupFeatureProjectParserRule();
      assertNull(parserRule.parse(moduleDir, ConverterUtils.TEST_CONVERTER));
   }

   public void testParseFeatureDirectory() throws Exception
   {
      final File featureDir = workspace.importResources("composed-component/simple-layout/example.feature");
      assertTrue(featureDir.exists());

      final FeatureProjectParserRule parserRule = lookupFeatureProjectParserRule();
      FeatureProject project = parserRule.parse(featureDir, ConverterUtils.TEST_CONVERTER);
      assertNotNull(project);
      assertEquals(featureDir, project.getDirectory());
      assertEquals("example.feature", project.getId());
      assertEquals("1.0.0.qualifier", project.getVersion());

      EList<StrictReference> featureIncludes = project.getIncludedFeatures();
      assertEquals(1, featureIncludes.size());
      assertEquals("org.eclipse.platform", featureIncludes.get(0).getId());

      EList<PluginInclude> pluginIncludes = project.getIncludedPlugins();
      assertEquals(2, pluginIncludes.size());
      assertEquals("example.core", pluginIncludes.get(0).getId());
      assertEquals(false, pluginIncludes.get(0).isUnpack());
      assertEquals("example.core.tests", pluginIncludes.get(1).getId());
      assertEquals(true, pluginIncludes.get(1).isUnpack());
   }

   private FeatureProjectParserRule lookupFeatureProjectParserRule() throws Exception
   {
      return (FeatureProjectParserRule) lookup(AbstractProjectParserRule.class, "feature");
   }
}
