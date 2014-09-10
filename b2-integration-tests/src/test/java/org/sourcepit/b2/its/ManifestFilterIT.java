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

import static org.apache.commons.io.FileUtils.forceDelete;
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

      final File templateMFFile = new File(moduleDir, "bundle.a/templates/" + JarFile.MANIFEST_NAME);
      assertTrue(templateMFFile.exists());

      final BundleManifest templateMF = (BundleManifest) B2ModelUtils.readManifest(templateMFFile, false);
      assertEquals("${module.osgiVersion}", templateMF.getHeaderValue(BUNDLE_VERSION));

      final File filteredMFFile = new File(moduleDir, "bundle.a/" + JarFile.MANIFEST_NAME);
      assertFalse(filteredMFFile.exists());

      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven",
         "-Db2.projects.resourcesDirectory=templates");
      assertThat(err, is(0));
      assertTrue(filteredMFFile.exists());

      final BundleManifest filteredMF = (BundleManifest) B2ModelUtils.readManifest(filteredMFFile, false);
      assertEquals("1.0.0.qualifier", filteredMF.getHeaderValue(BUNDLE_VERSION));

      PropertiesMap moduleDirectory = new LinkedPropertiesMap();
      moduleDirectory.load(new File(moduleDir, ".b2/moduleDirectory.properties"));
      assertEquals(11, moduleDirectory.size());
      assertEquals("3", moduleDirectory.get(".b2")); // hidden, derived
      assertEquals("6", moduleDirectory.get("target")); // hidden, forbidden
      assertEquals("3", moduleDirectory.get("pom.xml")); // hidden, derived

      assertEquals("4", moduleDirectory.get("bundle.a/templates")); // forbidden
      assertEquals("1", moduleDirectory.get("bundle.a/META-INF")); // derived
      assertEquals("1", moduleDirectory.get("bundle.a/META-INF/MANIFEST.MF")); // derived
      assertEquals("1", moduleDirectory.get("bundle.a/foo")); // derived
      assertEquals("1", moduleDirectory.get("bundle.a/foo/bar.txt")); // derived
      assertEquals("6", moduleDirectory.get("bundle.a/target")); // hidden, forbidden
      assertEquals("3", moduleDirectory.get("bundle.a/pom.xml")); // derived, hidden

      assertNull(moduleDirectory.get("bundle.a/build/")); // not flagged because dir already exists
      assertEquals("1", moduleDirectory.get("bundle.a/build/build.properties")); // derived

      final File fooDir = new File(moduleDir, "bundle.a/foo");
      assertTrue(fooDir.exists());

      final File resFooDir = new File(moduleDir, "bundle.a/templates/foo");
      assertTrue(resFooDir.exists());
      forceDelete(resFooDir);

      err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven", "-Db2.projects.resourcesDirectory=templates");
      assertThat(err, is(0));
      assertTrue(filteredMFFile.exists());

      assertFalse(fooDir.exists());

      moduleDirectory = new LinkedPropertiesMap();
      moduleDirectory.load(new File(moduleDir, ".b2/moduleDirectory.properties"));
      assertNull(moduleDirectory.get("bundle.a/foo")); // derived
      assertNull(moduleDirectory.get("bundle.a/foo/bar.txt")); // derived

   }
}
