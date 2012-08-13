/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.BundleRequirement;
import org.sourcepit.common.manifest.osgi.PackageImport;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;


public class DefaultUIDetectorTest
{
   private Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = new Workspace(new File(env.getBuildDir(), "test-ws"), false);

   @Test
   public void testSymbolicName() throws IOException
   {
      final IConverter converter = ConverterUtils.TEST_CONVERTER;
      final DefaultUIDetector uiDetector = new DefaultUIDetector(new DefaultBundleManifestReader());

      PluginProject pluginProject = newPluginProject("org.sourcepit.foo");
      assertFalse(uiDetector.requiresUI(pluginProject, converter));

      pluginProject = newPluginProject("org.sourcepit.foo.ui");
      assertTrue(uiDetector.requiresUI(pluginProject, converter));
   }

   @Test
   public void testRequiredBundle() throws IOException
   {
      final PluginProject pluginProject = newPluginProject("org.sourcepit.foo");

      final BundleManifest manifest = readManifest(pluginProject);

      final IConverter converter = ConverterUtils.TEST_CONVERTER;
      final DefaultUIDetector uiDetector = new DefaultUIDetector(new DefaultBundleManifestReader());

      assertFalse(uiDetector.requiresUI(pluginProject, converter));

      addRequiredBundle(manifest, "org.eclipse.core.runtime");
      assertFalse(uiDetector.requiresUI(pluginProject, converter));

      addRequiredBundle(manifest, "org.eclipse.swt");
      assertTrue(uiDetector.requiresUI(pluginProject, converter));
   }
   
   @Test
   public void testImportPackage() throws IOException
   {
      final PluginProject pluginProject = newPluginProject("org.sourcepit.foo");

      final BundleManifest manifest = readManifest(pluginProject);

      final IConverter converter = ConverterUtils.TEST_CONVERTER;
      final DefaultUIDetector uiDetector = new DefaultUIDetector(new DefaultBundleManifestReader());
      
      assertFalse(uiDetector.requiresUI(pluginProject, converter));

      addImportPackage(manifest, "org.eclipse.core.runtime");
      assertFalse(uiDetector.requiresUI(pluginProject, converter));

      addImportPackage(manifest, "org.eclipse.swt");
      assertTrue(uiDetector.requiresUI(pluginProject, converter));
   }

   private PluginProject newPluginProject(String bundleName) throws IOException
   {
      final PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setDirectory(new File(getWs().getRoot(), bundleName));
      pluginProject.setId(bundleName);
      
      final BundleManifest manifest = BundleManifestFactory.eINSTANCE.createBundleManifest();
      manifest.setBundleSymbolicName(pluginProject.getId());
      save(pluginProject.getDirectory(), manifest);
      return pluginProject;
   }

   private BundleManifest readManifest(PluginProject pluginProject) throws IOException
   {
      final File manifestFile = new File(pluginProject.getDirectory(), "META-INF/MANIFEST.MF");
      final Resource resource = new BundleManifestResourceImpl();
      resource.setURI(URI.createFileURI(manifestFile.getAbsolutePath()));
      resource.load(null);

      return (BundleManifest) resource.getContents().get(0);
   }
   
   private void addRequiredBundle(final BundleManifest manifest, String bundleName) throws IOException
   {
      BundleRequirement requirement = BundleManifestFactory.eINSTANCE.createBundleRequirement();
      requirement.getSymbolicNames().add(bundleName);
      manifest.getRequireBundle(true).add(requirement);
      manifest.eResource().save(null);
   }

   private void addImportPackage(final BundleManifest manifest, String bundleName) throws IOException
   {
      PackageImport requirement = BundleManifestFactory.eINSTANCE.createPackageImport();
      requirement.getPackageNames().add(bundleName);
      manifest.getImportPackage(true).add(requirement);
      manifest.eResource().save(null);
   }

   private void save(File pluginDir, BundleManifest manifest) throws IOException
   {
      final File manifestFile = new File(pluginDir, "META-INF/MANIFEST.MF");
      final Resource resource = new BundleManifestResourceImpl();
      resource.setURI(URI.createFileURI(manifestFile.getAbsolutePath()));
      resource.getContents().add(manifest);
      resource.save(null);
   }

   protected Workspace getWs()
   {
      return ws;
   }
}
