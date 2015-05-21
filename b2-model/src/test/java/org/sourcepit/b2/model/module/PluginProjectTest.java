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

package org.sourcepit.b2.model.module;

import junit.framework.TestCase;

/**
 * @author Bernd
 */
public class PluginProjectTest extends TestCase {
   public void testIsFragment() throws Exception {
      PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setId("my.fragment");
      assertFalse(pluginProject.isFragment());

      pluginProject.setFragmentHostVersion("1.0.0");
      assertFalse(pluginProject.isFragment());

      pluginProject.setFragmentHostSymbolicName("my");
      assertTrue(pluginProject.isFragment());

      pluginProject.setFragmentHostVersion(null);
      assertTrue(pluginProject.isFragment());
   }
}
