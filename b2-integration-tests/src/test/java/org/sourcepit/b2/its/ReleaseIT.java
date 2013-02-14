/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.common.utils.io.IOResources.osgiIn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.Scm;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.sourcepit.b2.its.util.GitSCM;
import org.sourcepit.b2.its.util.SCM;
import org.sourcepit.b2.its.util.SvnSCM;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.manifest.resource.ManifestResource;
import org.sourcepit.common.utils.io.IOOperation;

public class ReleaseIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void testSvn() throws Exception
   {
      final File rootModuleDir = getResource(getClass().getSimpleName());
      final File repoDir = workspace.newDir("repo");
      SCM scm = new SvnSCM(repoDir, rootModuleDir);
      test(rootModuleDir, scm, false);
   }

   @Test
   public void testGit() throws Exception
   {
      final File rootModuleDir = getResource(getClass().getSimpleName());
      final SCM scm = new GitSCM(rootModuleDir);
      test(rootModuleDir, scm, false);
   }

   @Test
   public void testQualifiedReleaseVersion() throws Exception
   {
      final File rootModuleDir = getResource(getClass().getSimpleName());
      final File repoDir = workspace.newDir("repo");
      SCM scm = new SvnSCM(repoDir, rootModuleDir);

      final String releaseVersion = "2.0.0-rc1";
      final String developmentVersion = "3.0.0-SNAPSHOT";
      test(rootModuleDir, scm, false, releaseVersion, developmentVersion);
   }

   @Test
   public void testTwoStepRelease() throws Exception
   {
      final File rootModuleDir = getResource(getClass().getSimpleName());
      final File repoDir = workspace.newDir("repo");
      SCM scm = new SvnSCM(repoDir, rootModuleDir);
      test(rootModuleDir, scm, true);
   }

   private void test(final File rootModuleDir, SCM scm, boolean isTwoStep) throws FileNotFoundException, IOException,
      XmlPullParserException
   {
      final String releaseVersion = "2.0.0";
      final String developmentVersion = "3.0.0-SNAPSHOT";
      test(rootModuleDir, scm, isTwoStep, releaseVersion, developmentVersion);
   }

   private void test(final File rootModuleDir, SCM scm, boolean isTwoStep, final String releaseVersion,
      final String developmentVersion) throws FileNotFoundException, IOException, XmlPullParserException
   {
      final File moduleADir = new File(rootModuleDir, "module-a");
      assertTrue(moduleADir.exists());
      final File moduleBDir = new File(rootModuleDir, "module-b");
      assertTrue(moduleBDir.exists());

      final List<File> moduleXmls = new ArrayList<File>();
      collectModuleXmls(moduleXmls, rootModuleDir);
      assertThat(moduleXmls.size(), Is.is(3));

      setScm(scm, moduleXmls);

      scm.create();


      execRelease(rootModuleDir, releaseVersion, developmentVersion, isTwoStep);

      scm.switchVersion(releaseVersion);

      assertMavenModel(scm, rootModuleDir, releaseVersion);
      assertMavenModel(scm, moduleADir, releaseVersion);
      assertMavenModel(scm, moduleBDir, releaseVersion);

      BundleManifest bundleManifest = readBundleManifest(new File(moduleBDir, "bundle.b"));
      assertThat(bundleManifest.getBundleVersion().toString(), IsEqual.equalTo(releaseVersion.replace('-', '.')));

      scm.switchVersion(null);

      assertMavenModel(scm, rootModuleDir, developmentVersion);
      assertMavenModel(scm, moduleADir, developmentVersion);
      assertMavenModel(scm, moduleBDir, developmentVersion);

      bundleManifest = readBundleManifest(new File(moduleBDir, "bundle.b"));
      assertThat(bundleManifest.getBundleVersion().toString(),
         IsEqual.equalTo(developmentVersion.replaceAll("-SNAPSHOT", ".qualifier")));
   }

   private void execRelease(final File rootModuleDir, final String releaseVersion, final String developmentVersion,
      boolean isTwoStep) throws IOException
   {
      if (isTwoStep)
      {
         File b2Dir = environment.getMavenHome();

         File mavenDir = new File(environment.getBuildDir(), "maven-without-b2");
         FileUtils.copyDirectory(b2Dir, mavenDir);
         FileUtils.forceDelete(new File(mavenDir, "lib/ext"));

         List<String> args = new ArrayList<String>();
         args.add("-e");
         args.add("-B");
         args.add("clean");
         args.add("-Dtycho.mode=maven");

         build(b2Dir, rootModuleDir, args.toArray(new String[args.size()]));

         args = new ArrayList<String>();
         args.add("-e");
         args.add("-B");
         args.add("release:prepare");
         args.add("release:perform");
         args.add("-DreleaseVersion=" + releaseVersion);
         args.add("-DdevelopmentVersion=" + developmentVersion);
         args.add("-Dresume=false");
         args.add("-DignoreSnapshots=true");

         build(mavenDir, rootModuleDir, args.toArray(new String[args.size()]));
      }
      else
      {
         final List<String> args = new ArrayList<String>();
         args.add("-e");
         args.add("-B");
         args.add("clean");
         args.add("release:prepare");
         args.add("release:perform");
         args.add("-DreleaseVersion=" + releaseVersion);
         args.add("-DdevelopmentVersion=" + developmentVersion);
         args.add("-Dresume=false");
         args.add("-DignoreSnapshots=true");

         build(rootModuleDir, args.toArray(new String[args.size()]));
      }
   }

   private static BundleManifest readBundleManifest(final File projectDir)
   {
      final ManifestResource resource = new BundleManifestResourceImpl();
      final IOOperation<InputStream> ioop = new IOOperation<InputStream>(osgiIn(projectDir, "META-INF/MANIFEST.MF"))
      {
         @Override
         protected void run(InputStream openResource) throws IOException
         {
            resource.load(openResource, null);
         }
      };
      try
      {
         ioop.run();
         return (BundleManifest) resource.getContents().get(0);
      }
      finally
      {
         resource.getContents().clear();
      }
   }

   private static void assertMavenModel(SCM scm, File moduleDir, final String releaseVersion)
      throws FileNotFoundException, IOException, XmlPullParserException
   {
      Model model = readMavenModel(new File(moduleDir, "module.xml"));
      assertThat(model.getVersion(), IsEqual.equalTo(releaseVersion));
      Scm scmConnection = scm.createMavenScmModel(moduleDir, releaseVersion);
      assertScmEqual(model.getScm(), scmConnection);
   }

   private static void assertScmEqual(Scm actual, Scm expected)
   {
      if (actual == null)
      {
         assertNull(expected);
         return;
      }
      if (expected == null)
      {
         assertNull(actual);
         return;
      }
      assertThat(actual.getConnection(), IsEqual.equalTo(expected.getConnection()));
      assertThat(actual.getDeveloperConnection(), IsEqual.equalTo(expected.getDeveloperConnection()));
      assertThat(actual.getTag(), IsEqual.equalTo(expected.getTag()));
      assertThat(actual.getUrl(), IsEqual.equalTo(expected.getUrl()));
   }

   private static void setScm(SCM scm, final List<File> moduleXmls) throws FileNotFoundException, IOException,
      XmlPullParserException
   {
      for (File moduleXml : moduleXmls)
      {
         final File projectDir = moduleXml.getParentFile();
         final Scm scmModel = scm.createMavenScmModel(projectDir, null);
         if (scmModel != null)
         {
            final Model moduleModel = readMavenModel(moduleXml);
            moduleModel.setScm(scmModel);
            writeMavenModel(moduleXml, moduleModel);
         }
      }
   }


   private static void collectModuleXmls(List<File> moduleXmls, File projectDir)
   {
      moduleXmls.add(new File(projectDir, "module.xml"));

      for (File file : projectDir.listFiles())
      {
         if (file.isDirectory() && new File(file, "module.xml").exists())
         {
            moduleXmls.add(new File(file, "module.xml"));
         }
      }
   }

}
