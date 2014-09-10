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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.sisu.launch.InjectedTest;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;

public class ProjectResourcesPreprocessorTest extends InjectedTest
{
   private Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = new Workspace(new File(env.getBuildDir(), "test-ws"), false);

   @Inject
   private ProjectResourcesPreprocessor preprocessor;

   @Test
   public void testDetect() throws IOException
   {
      final File baseDir = ws.getRoot();
      assertNull(preprocessor.detect(baseDir, new LinkedPropertiesMap()));

      File mfFile = new File(baseDir, "META-INF/MANIFEST.MF");
      mfFile.getParentFile().mkdirs();
      mfFile.createNewFile();

      BundleManifestResourceImpl mfResource = new BundleManifestResourceImpl(
         URI.createFileURI(mfFile.getAbsolutePath()));
      mfResource.getContents().add(BundleManifestFactory.eINSTANCE.createBundleManifest());
      mfResource.save(null);

      assertNull(preprocessor.detect(baseDir, new LinkedPropertiesMap()));

      FileUtils.moveDirectory(mfFile.getParentFile(), new File(baseDir, "res/META-INF"));

      assertNotNull(preprocessor.detect(baseDir, new LinkedPropertiesMap()));
   }

}
