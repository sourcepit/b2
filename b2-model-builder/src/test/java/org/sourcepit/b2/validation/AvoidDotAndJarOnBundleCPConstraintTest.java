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

package org.sourcepit.b2.validation;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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

public class AvoidDotAndJarOnBundleCPConstraintTest {
   private Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = new Workspace(new File(env.getBuildDir(), "test-ws"), false);

   private AvoidDotAndJarOnBundleCPConstraint constraint;

   private RecordingLogger logger;

   @Before
   public void setUp() {
      final UnpackStrategy unpackStrategy = new DefaultUnpackStrategy();
      logger = new RecordingLogger();
      constraint = new AvoidDotAndJarOnBundleCPConstraint(unpackStrategy, logger);
   }

   @Test
   public void testValidate() throws Exception {
      final File bundleDir = ws.newDir("bundle");

      final BundleManifest manifest = BundleManifestFactory.eINSTANCE.createBundleManifest();
      manifest.setBundleSymbolicName(bundleDir.getName());
      manifest.setBundleVersion("1.0.0.qualifier");
      save(manifest);

      final PluginProject pluginProject = newPluginProject(bundleDir, manifest);

      logger.getMessages().clear();
      constraint.validate(pluginProject, new LinkedPropertiesMap(), true);
      assertThat(logger.getMessages().size(), is(0));

      ClassPathEntry cpe = BundleManifestFactory.eINSTANCE.createClassPathEntry();
      cpe.getPaths().add("bundle.jar");
      manifest.getBundleClassPath(true).add(cpe);
      save(manifest);

      logger.getMessages().clear();
      constraint.validate(pluginProject, new LinkedPropertiesMap(), true);
      assertThat(logger.getMessages().size(), is(0));

      cpe = BundleManifestFactory.eINSTANCE.createClassPathEntry();
      cpe.getPaths().add(".");
      manifest.getBundleClassPath(true).add(cpe);
      save(manifest);

      logger.getMessages().clear();
      constraint.validate(pluginProject, new LinkedPropertiesMap(), true);
      assertThat(logger.getMessages().size(), is(1));
      assertEquals(
         "WARN bundle: Bundle will be unpacked on installation but contains '.' on the bundles classpath declaration. This may lead to naked class files after unpacking. It is recomended to build these classes in its own JAR via the bundles build.properties. (no quick fix available)",
         logger.getMessages().get(0));
   }

   private PluginProject newPluginProject(final File bundleDir, final BundleManifest manifest) {
      final PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setId(manifest.getBundleSymbolicName().getSymbolicName());
      pluginProject.setBundleVersion(manifest.getBundleVersion().toString());
      pluginProject.setDirectory(bundleDir);
      pluginProject.setBundleManifest(manifest);
      return pluginProject;
   }

   private void save(BundleManifest manifest) throws IOException {
      final URI manifestUri = URI.createFileURI(ws.newFile("bundle/META-INF/MANIFEST.MF").getAbsolutePath());
      final ManifestResource manifestResource = new BundleManifestResourceImpl(manifestUri);
      manifestResource.getContents().add(manifest);
      manifestResource.save(null);
   }

}
