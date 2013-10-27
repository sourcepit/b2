/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.utils.file.FileUtils;
import org.sourcepit.common.utils.file.FileVisitor;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class PluginFileFlagsProviderTest extends AbstractTestEnvironmentTest
{
   @Inject
   private PluginFileFlagsProvider flagsProvider;

   @Test
   public void test() throws IOException
   {
      final File moduleDir = ws.getRoot();
      final PropertiesMap properties = new LinkedPropertiesMap();

      final File pluginsDir = new File(moduleDir, "plugins");
      pluginsDir.mkdir();

      final File pluginDir = new File(pluginsDir, "foo");
      pluginDir.mkdir();
      injectManifest(pluginDir);

      PropertiesMap buildProps = new LinkedPropertiesMap();
      buildProps.put("source.foo.jar", "src/");
      buildProps.put("source.nl/foo_de.jar", "src/");
      buildProps.store(new File(pluginDir, "build.properties"));

      final File pluginDir2 = new File(pluginsDir, "bar");
      pluginDir2.mkdir();
      injectManifest(pluginDir2);

      buildProps = new LinkedPropertiesMap();
      buildProps.put("source..", "src/");
      buildProps.store(new File(pluginDir2, "build.properties"));

      assertNull(flagsProvider.getAlreadyKnownFileFlags(moduleDir, properties));

      final FileFlagsInvestigator flagsInvestigator = flagsProvider.createFileFlagsInvestigator(moduleDir, properties);
      FileUtils.accept(moduleDir, new FileVisitor()
      {
         @Override
         public boolean visit(File file)
         {
            assertEquals(0, flagsInvestigator.determineFileFlags(file));
            return true;
         }
      });

      Map<File, Integer> flags = flagsInvestigator.getAdditionallyFoundFileFlags();
      assertEquals(2, flags.size());

      assertEquals(valueOf(FLAG_DERIVED), flags.get(new File(pluginDir, "foo.jar")));
      assertEquals(valueOf(FLAG_DERIVED), flags.get(new File(pluginDir, "nl/foo_de.jar")));
   }

   private static void injectManifest(final File pluginDir) throws IOException
   {
      BundleManifest mf = BundleManifestFactory.eINSTANCE.createBundleManifest();
      mf.setBundleSymbolicName(pluginDir.getName());
      mf.setBundleVersion("1.0.0.qualifier");

      Resource resource = new BundleManifestResourceImpl(URI.createFileURI(pluginDir.getPath()
         + "/META-INF/MANIFEST.MF"));
      resource.getContents().add(mf);
      resource.save(null);
   }

}
