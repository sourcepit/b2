/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.model.Model;
import org.apache.maven.model.Repository;
import org.junit.Test;

public class MavenDependenciesSiteGeneratorIT extends AbstractB2IT
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
      int err = build(moduleDir, "-e", "-B", "clean", "package", "-P", "!p2-repo");
      assertThat(err, is(0));

      final File pomDepsSiteDir = new File(moduleDir, ".b2/maven-dependencies");
      assertTrue(pomDepsSiteDir.exists());

      File bundleDir = new File(pomDepsSiteDir, "plugins");
      List<String> bundleKeys = getBundleKeys(bundleDir);
      assertEquals(4, bundleKeys.size());

      assertTrue(bundleKeys.contains("javax.activation_1.1.0"));
      assertTrue(bundleKeys.contains("javax.activation.source_1.1.0"));
      assertTrue(bundleKeys.contains("javax.mail.mail_1.4.2"));
      assertTrue(bundleKeys.contains("javax.mail.mail.source_1.4.2"));

      final Model pom = loadMavenModel(moduleDir);
      final List<Repository> repositories = pom.getRepositories();
      assertEquals(1, repositories.size());

      assertEquals(0, pom.getDependencies().size());

      final Repository repository = repositories.get(0);
      assertEquals("p2", repository.getLayout());
      assertEquals(pomDepsSiteDir.toURI().toURL(), new URL(repository.getUrl()));

      List<File> featureDirs = Arrays.asList(new File(moduleDir, ".b2/features").listFiles());
      assertEquals(5, featureDirs.size());

      File[] siteDirs = new File(moduleDir, ".b2/sites").listFiles();
      assertEquals(2, siteDirs.length);
      for (File siteDir : siteDirs)
      {
         bundleKeys = getBundleKeys(new File(siteDir, "target/repository/plugins"));

         assertTrue(bundleKeys.contains("javax.activation_1.1.0"));
         assertTrue(bundleKeys.contains("javax.mail.mail_1.4.2"));

         if (siteDir.getName().contains(".sdk."))
         {
            assertTrue(bundleKeys.contains("javax.activation.source_1.1.0"));
            assertTrue(bundleKeys.contains("javax.mail.mail.source_1.4.2"));
         }
      }
   }

   private List<String> getBundleKeys(File bundleDir)
   {
      final File[] bundles = bundleDir.listFiles();
      final List<String> bundleFileNames = new ArrayList<String>();
      for (File bundle : bundles)
      {
         String name = bundle.getName();
         name = name.substring(0, name.lastIndexOf('.')); // trim .jar
         name = name.substring(0, name.lastIndexOf('.')); // trim time stamp
         bundleFileNames.add(name);
      }
      return bundleFileNames;
   }
}
