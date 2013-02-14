/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.its;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import junit.framework.AssertionFailedError;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.OS;
import org.apache.commons.io.IOUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.junit.Rule;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.SessionModelPackage;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.ExternalProcess;
import org.sourcepit.common.testing.Workspace;

public abstract class AbstractB2IT
{
   protected Environment environment = Environment.get(getItPropertiesPath());

   @Rule
   public ExternalProcess process = new ExternalProcess();

   @Rule
   public Workspace workspace = new Workspace(new File(environment.getBuildDir(), "ws"), false);

   protected String getItPropertiesPath()
   {
      return "env-it.properties";
   }

   protected int build(final File moduleDir, String... args) throws IOException
   {
      final Map<String, String> envs = environment.newEnvs();
      final CommandLine cmd = newMavenCmd(environment.getMavenHome(), args);
      return process.execute(envs, moduleDir, cmd);
   }

   protected int build(File mavenHome, final File moduleDir, String... args) throws IOException
   {
      final Map<String, String> envs = environment.newEnvs();
      envs.put("M2_HOME", mavenHome.getAbsolutePath());

      final CommandLine cmd = newMavenCmd(mavenHome, args);
      return process.execute(envs, moduleDir, cmd);
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

   protected CommandLine newMavenCmd(File mavenHome, String... arguments)
   {
      final String sh = environment.isDebugAllowed() && isDebug() ? "mvnDebug" : "mvn";
      final String bat = sh + ".bat";
      final File binDir = new File(mavenHome, "/bin");
      return newCmd(binDir, bat, sh, arguments);
   }

   protected abstract boolean isDebug();

   protected File getResource(String path) throws IOException
   {
      final File resourcesDir = environment.getPropertyAsFile("it.resources");
      assertTrue(resourcesDir.exists());
      final File resource = workspace.importDir(new File(resourcesDir, path));
      assertTrue(resource.exists());
      return resource;
   }

   protected AbstractModule loadModule(final File moduleDir) throws IOException
   {
      ModuleModelPackage.eINSTANCE.getClass();
      SessionModelPackage.eINSTANCE.getClass();

      File modelFile = new File(moduleDir, ".b2/b2.module");
      assertTrue(modelFile.exists());

      return (AbstractModule) loadModel(modelFile);
   }

   protected B2Session loadSession(final File moduleDir) throws IOException
   {
      ModuleModelPackage.eINSTANCE.getClass();
      SessionModelPackage.eINSTANCE.getClass();

      File modelFile = new File(moduleDir, ".b2/b2.session");
      assertTrue(modelFile.exists());

      return (B2Session) loadModel(modelFile);
   }

   private EObject loadModel(File modelFile) throws FileNotFoundException, IOException
   {
      final InputStream inputStream = new BufferedInputStream(new FileInputStream(modelFile));
      try
      {
         Resource resource = new XMIResourceImpl();
         resource.load(inputStream, null);
         return resource.getContents().get(0);
      }
      finally
      {
         IOUtils.closeQuietly(inputStream);
      }
   }

   protected Model loadMavenModel(final AbstractModule module) throws FileNotFoundException, IOException,
      XmlPullParserException
   {
      return loadMavenModel(module.getDirectory());
   }

   protected Model loadMavenModel(File moduleDir) throws FileNotFoundException, IOException, XmlPullParserException
   {
      final File pomFile = new File(moduleDir, "pom.xml");
      return readMavenModel(pomFile);
   }

   protected static Model readMavenModel(final File pomFile) throws FileNotFoundException, IOException,
      XmlPullParserException
   {
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

   protected static void writeMavenModel(final File pomFile, Model pom) throws FileNotFoundException, IOException
   {
      assertTrue(pomFile.exists());

      final OutputStream outputStream = new FileOutputStream(pomFile);
      try
      {
         new MavenXpp3Writer().write(new BufferedOutputStream(outputStream), pom);
      }
      finally
      {
         IOUtils.closeQuietly(outputStream);
      }
   }
}
