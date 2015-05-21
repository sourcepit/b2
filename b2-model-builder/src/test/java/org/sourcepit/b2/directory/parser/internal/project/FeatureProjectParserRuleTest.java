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

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.common.utils.props.PropertiesMap;

public class FeatureProjectParserRuleTest extends AbstractModuleParserTest {
   public void testBasic() throws Exception {
      final FeatureProjectParserRule parserRule = lookupFeatureProjectParserRule();
      assertNotNull(parserRule);
   }

   public void testNull() throws Exception {
      final FeatureProjectParserRule parserRule = lookupFeatureProjectParserRule();
      assertNull(parserRule.parse(null, null));
   }

   public void testNonPluginDir() throws Exception {
      final File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.exists());

      final FeatureProjectParserRule parserRule = lookupFeatureProjectParserRule();
      assertNull(parserRule.parse(moduleDir, B2ModelBuildingRequest.newDefaultProperties()));
   }

   public void testParseFeatureDirectory() throws Exception {
      PropertiesMap properties = B2ModelBuildingRequest.newDefaultProperties();

      final File featureDir = workspace.importResources("composed-component/simple-layout/example.feature");
      assertTrue(featureDir.exists());

      final FeatureProjectParserRule parserRule = lookupFeatureProjectParserRule();
      FeatureProject project = parserRule.detect(featureDir, properties);
      assertNotNull(project);
      assertNull(project.getId());

      parserRule.initialize(project, properties);
      assertEquals(featureDir, project.getDirectory());
      assertEquals("example.feature", project.getId());
      assertEquals("1.0.0.qualifier", project.getVersion());

      EList<FeatureInclude> featureIncludes = project.getIncludedFeatures();
      assertEquals(1, featureIncludes.size());
      assertEquals("org.eclipse.platform", featureIncludes.get(0).getId());

      EList<PluginInclude> pluginIncludes = project.getIncludedPlugins();
      assertEquals(2, pluginIncludes.size());
      assertEquals("example.core", pluginIncludes.get(0).getId());
      assertEquals(false, pluginIncludes.get(0).isUnpack());
      assertEquals("example.core.tests", pluginIncludes.get(1).getId());
      assertEquals(true, pluginIncludes.get(1).isUnpack());
   }

   private FeatureProjectParserRule lookupFeatureProjectParserRule() throws Exception {
      return (FeatureProjectParserRule) lookup(AbstractProjectParserRule.class, "feature");
   }
}
