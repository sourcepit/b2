/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.validation;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.b2.model.builder.util.BundleManifestReader;
import org.sourcepit.b2.model.builder.util.DefaultBundleManifestReader;
import org.sourcepit.b2.model.builder.util.DefaultUnpackStrategy;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.ClassPathEntry;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.manifest.resource.ManifestResource;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;

public class EclipseBundleShapeConstraintTest
{
   private Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = new Workspace(new File(env.getBuildDir(), "test-ws"), false);

   private EclipseBundleShapeConstraint constraint;

   private RecordingLogger logger;

   private BundleManifestReader manifestReader;

   @Before
   public void setUp()
   {
      manifestReader = new DefaultBundleManifestReader();
      final UnpackStrategy unpackStrategy = new DefaultUnpackStrategy(manifestReader);
      logger = new RecordingLogger();
      constraint = new EclipseBundleShapeConstraint(unpackStrategy, manifestReader, logger);
   }

   @Test
   public void testOk() throws Exception
   {
      final File bundleDir = ws.newDir("bundle");

      final BundleManifest manifest = BundleManifestFactory.eINSTANCE.createBundleManifest();
      manifest.setBundleSymbolicName(bundleDir.getName());
      manifest.setBundleVersion("1.0.0.qualifier");
      save(manifest);

      final PluginProject pluginProject = newPluginProject(bundleDir, manifest);

      logger.getMessages().clear();
      constraint.validate(pluginProject, new LinkedPropertiesMap(), true);
      assertThat(logger.getMessages().size(), is(0));
   }

   @Test
   public void testQuickFix() throws Exception
   {
      final File bundleDir = ws.newDir("bundle");

      BundleManifest manifest = BundleManifestFactory.eINSTANCE.createBundleManifest();
      manifest.setBundleSymbolicName(bundleDir.getName());
      manifest.setBundleVersion("1.0.0.qualifier");
      final ClassPathEntry cpe = BundleManifestFactory.eINSTANCE.createClassPathEntry();
      cpe.getPaths().add("bundle.jar");
      manifest.getBundleClassPath(true).add(cpe);
      save(manifest);

      final PluginProject pluginProject = newPluginProject(bundleDir, manifest);

      logger.getMessages().clear();
      constraint.validate(pluginProject, new LinkedPropertiesMap(), false);
      assertThat(logger.getMessages().size(), is(1));
      assertEquals("WARN bundle: Missing manifest entry: 'Eclipse-BundleShape: dir'. (quick fix available)", logger
         .getMessages().get(0));
      manifest = manifestReader.readManifest(pluginProject);
      assertNull(manifest.getHeaderValue("Eclipse-BundleShape"));

      logger.getMessages().clear();
      constraint.validate(pluginProject, new LinkedPropertiesMap(), true);
      assertThat(logger.getMessages().size(), is(1));
      assertEquals("WARN bundle: Missing manifest entry: 'Eclipse-BundleShape: dir'. (applied quick fix)", logger
         .getMessages().get(0));

      manifest = manifestReader.readManifest(pluginProject);
      assertEquals("dir", manifest.getHeaderValue("Eclipse-BundleShape"));
   }

   private PluginProject newPluginProject(final File bundleDir, final BundleManifest manifest)
   {
      final PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setId(manifest.getBundleSymbolicName().getSymbolicName());
      pluginProject.setBundleVersion(manifest.getBundleVersion().toString());
      pluginProject.setDirectory(bundleDir);
      return pluginProject;
   }

   private void save(BundleManifest manifest) throws IOException
   {
      final URI manifestUri = URI.createFileURI(ws.newFile("bundle/META-INF/MANIFEST.MF").getAbsolutePath());
      final ManifestResource manifestResource = new BundleManifestResourceImpl(manifestUri);
      manifestResource.getContents().add(manifest);
      manifestResource.save(null);
   }
}
