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

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.BundleRequirement;
import org.sourcepit.common.manifest.osgi.PackageImport;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.props.PropertiesMap;


public class DefaultUIDetectorTest
{
   private Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = new Workspace(new File(env.getBuildDir(), "test-ws"), false);

   @Test
   public void testSymbolicName() throws IOException
   {
      final PropertiesMap properties = B2ModelBuildingRequest.newDefaultProperties();
      final DefaultUIDetector uiDetector = new DefaultUIDetector();

      PluginProject pluginProject = newPluginProject("org.sourcepit.foo");
      pluginProject.setBundleManifest(BundleManifestFactory.eINSTANCE.createBundleManifest());
      assertFalse(uiDetector.requiresUI(pluginProject, properties));

      pluginProject = newPluginProject("org.sourcepit.foo.ui");
      pluginProject.setBundleManifest(BundleManifestFactory.eINSTANCE.createBundleManifest());
      assertTrue(uiDetector.requiresUI(pluginProject, properties));
   }

   @Test
   public void testRequiredBundle() throws IOException
   {
      final PluginProject pluginProject = newPluginProject("org.sourcepit.foo");

      final BundleManifest manifest = readManifest(pluginProject);
      pluginProject.setBundleManifest(manifest);

      final PropertiesMap properties = B2ModelBuildingRequest.newDefaultProperties();
      final DefaultUIDetector uiDetector = new DefaultUIDetector();

      assertFalse(uiDetector.requiresUI(pluginProject, properties));

      addRequiredBundle(manifest, "org.eclipse.core.runtime");
      assertFalse(uiDetector.requiresUI(pluginProject, properties));

      addRequiredBundle(manifest, "org.eclipse.swt");
      assertTrue(uiDetector.requiresUI(pluginProject, properties));
   }

   @Test
   public void testImportPackage() throws IOException
   {
      final PluginProject pluginProject = newPluginProject("org.sourcepit.foo");

      final BundleManifest manifest = readManifest(pluginProject);
      pluginProject.setBundleManifest(manifest);

      final PropertiesMap properties = B2ModelBuildingRequest.newDefaultProperties();
      final DefaultUIDetector uiDetector = new DefaultUIDetector();

      assertFalse(uiDetector.requiresUI(pluginProject, properties));

      addImportPackage(manifest, "org.eclipse.core.runtime");
      assertFalse(uiDetector.requiresUI(pluginProject, properties));

      addImportPackage(manifest, "org.eclipse.swt");
      assertTrue(uiDetector.requiresUI(pluginProject, properties));
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
