/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.common.manifest.osgi.BundleHeaderName.BUNDLE_VERSION;

import java.io.File;
import java.util.jar.JarFile;

import org.junit.Test;
import org.sourcepit.b2.model.interpolation.internal.module.B2ModelUtils;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class ManifestFilterIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void test() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());

      final File templateMFFile = new File(moduleDir, "bundle.a/res/" + JarFile.MANIFEST_NAME);
      assertTrue(templateMFFile.exists());

      final BundleManifest templateMF = (BundleManifest) B2ModelUtils.readManifest(templateMFFile, false);
      assertEquals("${module.osgiVersion}", templateMF.getHeaderValue(BUNDLE_VERSION));

      final File filteredMFFile = new File(moduleDir, "bundle.a/" + JarFile.MANIFEST_NAME);
      assertFalse(filteredMFFile.exists());

      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));
      assertTrue(filteredMFFile.exists());

      final BundleManifest filteredMF = (BundleManifest) B2ModelUtils.readManifest(filteredMFFile, false);
      assertEquals("1.0.0.qualifier", filteredMF.getHeaderValue(BUNDLE_VERSION));

      PropertiesMap moduleFiles = new LinkedPropertiesMap();
      moduleFiles.load(new File(moduleDir, ".b2/moduleFiles.properties"));
      assertEquals(5, moduleFiles.size());
      assertEquals("3", moduleFiles.get(".b2")); // hidden
      assertEquals("4", moduleFiles.get("bundle.a/res")); // forbidden
      assertEquals("1", moduleFiles.get("bundle.a/META-INF")); // derived
      assertEquals("1", moduleFiles.get("bundle.a/META-INF/MANIFEST.MF")); // derived

      assertNull(moduleFiles.get("bundle.a/build/")); // not flagged because dir already exists
      assertEquals("1", moduleFiles.get("bundle.a/build/build.properties")); // derived
   }
}
