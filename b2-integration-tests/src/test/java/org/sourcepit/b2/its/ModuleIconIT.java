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

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class ModuleIconIT extends AbstractB2IT {
   @Override
   protected boolean isDebug() {
      return false;
   }

   @Test
   public void test() throws Exception {
      final File moduleDir = getResource(getClass().getSimpleName());
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));

      AbstractModule module = loadModule(moduleDir);

      PluginsFacet brandings = module.getFacetByName("features-branding-plugins");

      PluginProject assemblyBranding = brandings.getProjectById("org.sourcepit.b2.its.ModuleIconIT.branding");
      assertNotNull(assemblyBranding);
      assertModuleIconConfigured(assemblyBranding.getDirectory());

      PluginProject pluginsBranding = brandings.getProjectById("org.sourcepit.b2.its.ModuleIconIT.plugins.branding");
      assertNotNull(pluginsBranding);
      assertModuleIconConfigured(pluginsBranding.getDirectory());

      PluginProject testsBranding = brandings.getProjectById("org.sourcepit.b2.its.ModuleIconIT.tests.branding");
      assertNotNull(testsBranding);
      assertModuleIconConfigured(testsBranding.getDirectory());
   }

   private static void assertModuleIconConfigured(File pluginDir) {
      assertTrue(new File(pluginDir, "module32.png").exists());

      final PropertiesMap buildProps = new LinkedPropertiesMap();
      buildProps.load(new File(pluginDir, "build.properties"));
      assertTrue(buildProps.get("bin.includes").contains("module32.png"));

      final PropertiesMap aboutIni = new LinkedPropertiesMap();
      aboutIni.load(new File(pluginDir, "about.ini"));
      assertEquals("module32.png", aboutIni.get("featureImage"));
   }
}
