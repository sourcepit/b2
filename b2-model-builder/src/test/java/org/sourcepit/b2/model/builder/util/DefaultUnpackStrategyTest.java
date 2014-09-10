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

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.ClassPathEntry;

public class DefaultUnpackStrategyTest
{
   @Test
   public void testBinIncludes()
   {
      final DefaultUnpackStrategy unpackStrategy = new DefaultUnpackStrategy();

      BundleManifest manifest = BundleManifestFactory.eINSTANCE.createBundleManifest();

      PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setBundleManifest(manifest);

      assertFalse(unpackStrategy.isUnpack(pluginProject));

      pluginProject.setAnnotationData("build", "bin.includes", ".");

      assertFalse(unpackStrategy.isUnpack(pluginProject));

      pluginProject.setAnnotationData("build", "bin.includes", ".,foo.jar");

      assertTrue(unpackStrategy.isUnpack(pluginProject));
   }

   @Test
   public void testClasspathEntries()
   {
      final DefaultUnpackStrategy unpackStrategy = new DefaultUnpackStrategy();

      BundleManifest manifest = BundleManifestFactory.eINSTANCE.createBundleManifest();

      PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setBundleManifest(manifest);

      assertFalse(unpackStrategy.isUnpack(pluginProject));

      ClassPathEntry cpe = BundleManifestFactory.eINSTANCE.createClassPathEntry();
      cpe.getPaths().add(".");
      manifest.getBundleClassPath(true).add(cpe);

      assertFalse(unpackStrategy.isUnpack(pluginProject));

      cpe = BundleManifestFactory.eINSTANCE.createClassPathEntry();
      cpe.getPaths().add("foo.jar");
      manifest.getBundleClassPath(true).add(cpe);

      assertTrue(unpackStrategy.isUnpack(pluginProject));
   }

   @Test
   public void testEclipseBundleShape()
   {
      final DefaultUnpackStrategy unpackStrategy = new DefaultUnpackStrategy();

      BundleManifest manifest = BundleManifestFactory.eINSTANCE.createBundleManifest();

      PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setBundleManifest(manifest);

      assertFalse(unpackStrategy.isUnpack(pluginProject));
      manifest.setHeader("Eclipse-BundleShape", "dir");

      assertTrue(unpackStrategy.isUnpack(pluginProject));
   }

}
