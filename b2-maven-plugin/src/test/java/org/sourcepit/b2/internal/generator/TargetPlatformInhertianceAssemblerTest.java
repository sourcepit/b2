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

package org.sourcepit.b2.internal.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_GROUP_ID;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_TPC_PLUGIN_ARTIFACT_ID;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getConfiguration;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getPlugin;
import static org.sourcepit.common.maven.util.Xpp3Utils.addValueNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.inheritance.DefaultInheritanceAssembler;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Before;
import org.junit.Test;

public class TargetPlatformInhertianceAssemblerTest {
   private TargetPlatformInhertianceAssembler tpcAssembler;

   @Before
   public void setUp() {
      tpcAssembler = new TargetPlatformInhertianceAssembler(new DefaultInheritanceAssembler());
   }

   @Test
   public void testNullAndEmptyHierarchy() throws Exception {
      try {
         tpcAssembler.assembleTPCInheritance(null);
         fail();
      }
      catch (NullPointerException e) { // expected
      }

      try {
         final List<Model> hierarchy = new ArrayList<Model>();
         hierarchy.add(null);
         tpcAssembler.assembleTPCInheritance(hierarchy);
         fail();
      }
      catch (NullPointerException e) { // expected
      }

      try {
         tpcAssembler.assembleTPCInheritance(Collections.<Model> emptyList());
         fail();
      }
      catch (IllegalArgumentException e) { // expected
      }
   }

   @Test
   public void testTPCNotExists() throws Exception {
      final Model model = new Model();
      final List<Model> hierarchy = asList(model);

      tpcAssembler.assembleTPCInheritance(hierarchy);

      assertSame(model, hierarchy.get(0));
      assertNull(model.getBuild());
   }

   @Test
   public void testUseTPCFromModule() throws Exception {
      final List<Model> hierarchy = asList(new Model(), new Model());
      final Model pluginModel = hierarchy.get(0);
      final Model moduleModel = hierarchy.get(1);

      Plugin moduleTPC = getPlugin(moduleModel, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, true);
      Xpp3Dom moduleCfg = getConfiguration(moduleTPC, true);
      addValueNode(moduleCfg, "foo", "bar");

      tpcAssembler.assembleTPCInheritance(hierarchy);

      Plugin pluginTPC = getPlugin(pluginModel, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      assertNotNull(pluginTPC);
      assertNotSame(moduleTPC, pluginTPC);
      assertPluginEquals(moduleTPC, pluginTPC);

      Xpp3Dom pluginCfg = getConfiguration(pluginTPC, false);
      assertNotNull(pluginCfg);
      assertEquals(1, pluginCfg.getChildCount());
      assertEquals("bar", pluginCfg.getChild("foo").getValue());
   }


   @Test
   public void testUseTPCFromPlugin() throws Exception {
      final List<Model> hierarchy = asList(new Model(), new Model());
      final Model pluginModel = hierarchy.get(0);

      Plugin pluginTPC = getPlugin(pluginModel, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, true);
      Xpp3Dom pluginCfg = getConfiguration(pluginTPC, true);
      addValueNode(pluginCfg, "foo", "bar");

      tpcAssembler.assembleTPCInheritance(hierarchy);

      Plugin actualPluginTPC = getPlugin(pluginModel, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      assertNotNull(actualPluginTPC);
      // assertSame(pluginTPC, actualPluginTPC);
      assertEquals(1, pluginModel.getBuild().getPlugins().size());
      assertPluginEquals(pluginTPC, actualPluginTPC);
   }

   @Test
   public void testMergedTPC() throws Exception {
      final List<Model> hierarchy = asList(new Model(), new Model());
      final Model pluginModel = hierarchy.get(0);
      final Model moduleModel = hierarchy.get(1);

      Xpp3Dom pluginCfg = getConfiguration(getPlugin(pluginModel, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, true),
         true);
      addValueNode(pluginCfg, "pluginValue", "from plugin");

      Xpp3Dom moduleCfg = getConfiguration(getPlugin(moduleModel, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, true),
         true);
      addValueNode(moduleCfg, "moduleValue", "from module");

      tpcAssembler.assembleTPCInheritance(hierarchy);

      Plugin mergedTPC = getPlugin(pluginModel, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      assertNotNull(mergedTPC);

      Xpp3Dom configuration = getConfiguration(mergedTPC, false);
      assertNotNull(configuration);
      assertEquals(2, configuration.getChildCount());
   }

   private static void assertPluginEquals(Plugin expected, Plugin actual) {
      assertEquals(expected, actual);
      assertEquals(expected.getVersion(), actual.getVersion());
      assertEquals(expected.getConfiguration(), actual.getConfiguration());
   }
}
