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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.maven.model.Model;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.PluginsFacet;

public class MixedTestsFacetIT extends AbstractB2IT {
   @Override
   protected boolean isDebug() {
      return false;
   }

   @Test
   public void test() throws Exception {
      final BasicModule module = (BasicModule) buildModel(false);
      assertThat(module.getFacets().size(), Is.is(2));

      final PluginsFacet plugins = module.getFacetByName("plugins");
      assertNotNull(plugins);
      assertThat(plugins.getProjects().size(), Is.is(1));

      final PluginsFacet tests = module.getFacetByName("tests");
      assertNotNull(tests);
      assertThat(tests.getProjects().size(), Is.is(2));

      final Model pom = loadMavenModel(module);
      assertThat(3, Is.is(pom.getModules().size()));
   }

   private AbstractModule buildModel(boolean interpolate) throws IOException {
      final File moduleDir = getResource(getClass().getSimpleName());
      final Map<String, String> envs = environment.newEnvs();
      final CommandLine cmd = newMavenCmd(environment.getMavenHome(), "-e", "-B", "-Dtycho.mode=maven",
         "-Db2.skipInterpolator=" + String.valueOf(!interpolate), "clean");
      process.execute(envs, moduleDir, cmd);
      return loadModule(moduleDir);
   }
}
