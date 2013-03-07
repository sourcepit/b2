/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
