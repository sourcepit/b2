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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.model.Model;
import org.apache.maven.model.Repository;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;

public class MavenDependenciesSiteGeneratorIT extends AbstractB2IT {
   @Override
   protected boolean isDebug() {
      return false;
   }

   @Test
   public void test() throws Exception {
      final File moduleDir = getResource(getClass().getSimpleName());
      int err = build(moduleDir, "-e", "-B", "clean", "package", "-P", "!p2-repo");
      assertThat(err, is(0));

      final File moduleADir = new File(moduleDir, "module-a");
      assertTrue(moduleADir.exists());
      final File moduleBDir = new File(moduleDir, "module-b");
      assertTrue(moduleBDir.exists());

      File pomDepsSiteDir = new File(moduleDir, ".b2/maven-dependencies");
      assertFalse(pomDepsSiteDir.exists());

      assertModuleA(moduleADir);
      assertModuleB(moduleBDir);
   }

   private void assertModuleA(final File moduleDir) throws FileNotFoundException, IOException, XmlPullParserException,
      MalformedURLException {
      File pomDepsSiteDir;
      pomDepsSiteDir = new File(moduleDir, ".b2/maven-dependencies");
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
      for (File siteDir : siteDirs) {
         bundleKeys = getBundleKeys(new File(siteDir, "target/repository/plugins"));

         assertTrue(bundleKeys.contains("javax.activation_1.1.0"));
         assertTrue(bundleKeys.contains("javax.mail.mail_1.4.2"));

         if (siteDir.getName().contains(".sdk.")) {
            assertTrue(bundleKeys.contains("javax.activation.source_1.1.0"));
            assertTrue(bundleKeys.contains("javax.mail.mail.source_1.4.2"));
         }
      }
   }

   private void assertModuleB(final File moduleDir) throws FileNotFoundException, IOException, XmlPullParserException,
      MalformedURLException {
      File pomDepsSiteDir;
      pomDepsSiteDir = new File(moduleDir, ".b2/maven-dependencies");
      assertTrue(pomDepsSiteDir.exists());

      File bundleDir = new File(pomDepsSiteDir, "plugins");
      List<String> bundleKeys = getBundleKeys(bundleDir);
      assertEquals(4, bundleKeys.size());

      assertTrue(bundleKeys.contains("org.hamcrest_1.3.0"));
      assertTrue(bundleKeys.contains("org.hamcrest.source_1.3.0"));
      assertTrue(bundleKeys.contains("org.junit_4.11.0"));
      assertTrue(bundleKeys.contains("org.junit.source_4.11.0"));

      final Model pom = loadMavenModel(moduleDir);
      final List<Repository> repositories = pom.getRepositories();
      assertEquals(1, repositories.size());

      assertEquals(0, pom.getDependencies().size());

      final Repository repository = repositories.get(0);
      assertEquals("p2", repository.getLayout());
      assertEquals(pomDepsSiteDir.toURI().toURL(), new URL(repository.getUrl()));

      List<File> featureDirs = Arrays.asList(new File(moduleDir, ".b2/features").listFiles());
      assertEquals(3, featureDirs.size());

      File[] siteDirs = new File(moduleDir, ".b2/sites").listFiles();
      assertEquals(2, siteDirs.length);
      for (File siteDir : siteDirs) {
         bundleKeys = getBundleKeys(new File(siteDir, "target/repository/plugins"));

         assertTrue(bundleKeys.contains("org.hamcrest_1.3.0"));
         assertTrue(bundleKeys.contains("org.junit_4.11.0"));

         if (siteDir.getName().contains(".sdk.")) {
            assertTrue(bundleKeys.contains("org.hamcrest.source_1.3.0"));
            assertTrue(bundleKeys.contains("org.junit.source_4.11.0"));
         }
      }
   }

   private static List<String> getBundleKeys(File bundleDir) {
      final File[] bundles = bundleDir.listFiles();
      final List<String> bundleFileNames = new ArrayList<String>();
      for (File bundle : bundles) {
         String name = bundle.getName();
         name = name.substring(0, name.lastIndexOf('.')); // trim .jar
         name = name.substring(0, name.lastIndexOf('.')); // trim time stamp
         bundleFileNames.add(name);
      }
      return bundleFileNames;
   }
}
