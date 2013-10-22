/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.guplex.test.GuplexTest;

public class ProjectResourcesPreprocessorTest extends GuplexTest
{
   private Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = new Workspace(new File(env.getBuildDir(), "test-ws"), false);

   @Inject
   private ProjectResourcesPreprocessor preprocessor;

   @Override
   protected boolean isUseIndex()
   {
      return true;
   }

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
