/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.its;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import junit.framework.AssertionFailedError;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.OS;
import org.apache.commons.io.IOUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.session.SessionModelPackage;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.ExternalProcess;
import org.sourcepit.common.testing.Workspace;

public class MixedTestsFacetIt
{
   private final static boolean DEBUG = false;

   protected Environment environment = Environment.get("env-it.properties");

   @Rule
   public ExternalProcess process = new ExternalProcess();

   @Rule
   public Workspace workspace = new Workspace(new File(environment.getBuildDir(), "ws"), false);

   @Test
   public void test() throws Exception
   {
      final BasicModule module = (BasicModule) buildModel(false);
      assertThat(module.getFacets().size(), Is.is(2));

      final PluginsFacet plugins = module.getFacetByName("plugins");
      assertNotNull(plugins);
      assertThat(plugins.getProjects().size(), Is.is(1));

      final PluginsFacet tests = module.getFacetByName("tests");
      assertNotNull(tests);
      assertThat(tests.getProjects().size(), Is.is(2));

      final Model pom = loadMavenModel(module);
      assertThat(3, Is.is(pom.getModules().size()));
   }

   private Model loadMavenModel(final BasicModule module) throws FileNotFoundException, IOException,
      XmlPullParserException
   {
      final File pomFile = new File(module.getDirectory(), "pom.xml");
      assertTrue(pomFile.exists());

      final InputStream inputStream = new FileInputStream(pomFile);
      try
      {
         return new MavenXpp3Reader().read(new BufferedInputStream(inputStream));
      }
      finally
      {
         IOUtils.closeQuietly(inputStream);
      }
   }

   private AbstractModule buildModel(boolean interpolate) throws IOException
   {
      final File moduleDir = getResource(getClass().getSimpleName());
      final Map<String, String> envs = environment.newEnvs();
      final CommandLine cmd = newMavenCmd("-e", "-B", "-Dtycho.mode=maven",
         "-Db2.skipInterpolator=" + String.valueOf(!interpolate), "clean");
      process.execute(envs, moduleDir, cmd);
      return loadModel(moduleDir);
   }

   private AbstractModule loadModel(final File moduleDir) throws IOException
   {
      ModuleModelPackage.eINSTANCE.getClass();
      SessionModelPackage.eINSTANCE.getClass();

      File modelFile = new File(moduleDir, ".b2/b2.module");
      assertTrue(modelFile.exists());

      final InputStream inputStream = new BufferedInputStream(new FileInputStream(modelFile));
      try
      {
         Resource resource = new XMIResourceImpl();
         resource.load(inputStream, null);
         return (AbstractModule) resource.getContents().get(0);
      }
      finally
      {
         IOUtils.closeQuietly(inputStream);
      }
   }

   protected CommandLine newCmd(File binDir, String bat, String sh, String... arguments)
   {
      final CommandLine cmd;
      if (OS.isFamilyWindows() || OS.isFamilyWin9x())
      {
         cmd = process.newCommandLine(new File(binDir, bat));
      }
      else if (OS.isFamilyUnix() || OS.isFamilyMac())
      {
         cmd = process.newCommandLine("sh", new File(binDir, sh).getAbsolutePath());
      }
      else
      {
         throw new AssertionFailedError("Os family");
      }
      cmd.addArguments(arguments);
      return cmd;
   }

   protected CommandLine newMavenCmd(String... arguments)
   {
      final String sh = isDebug() ? "mvnDebug" : "mvn";
      final String bat = sh + ".bat";
      final File binDir = new File(environment.getMavenHome(), "/bin");
      return newCmd(binDir, bat, sh, arguments);
   }

   protected boolean isDebug()
   {
      return environment.isDebugAllowed() && DEBUG;
   }

   private File getResource(String path) throws IOException
   {
      final File resourcesDir = environment.getPropertyAsFile("it.resources");
      assertTrue(resourcesDir.exists());
      final File resource = workspace.importDir(new File(resourcesDir, path));
      assertTrue(resource.exists());
      return resource;
   }
}
