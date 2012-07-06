/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.model.io.ModelReader;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Test;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.internal.maven.B2SessionInitializer;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest2;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.maven.testing.MavenExecutionResult2;
import org.sourcepit.maven.bootstrap.participation.BootstrapSession;

public class PomGenerator2Test extends AbstractB2SessionWorkspaceTest2
{
   @Inject
   private LegacySupport legacySupport;

   @Inject
   private B2SessionInitializer b2SessionInitializer;

   @Inject
   private IB2ModelBuilder modelBuilder;

   @Inject
   private PomGenerator pomGenerator;

   @Test
   public void testModuleTemplatesOrdering() throws Exception
   {
      final File projectDir = getWs().getRoot();

      // create test project
      new DefaultTemplateCopier().copy("module-pom.xml", projectDir, getEnvironment().newProperties());

      final File moduleFile = new File(projectDir, "module.xml");
      new File(projectDir, "module-pom.xml").renameTo(moduleFile);

      assertTrue(moduleFile.exists());

      Model modulePom = readPom(moduleFile);
      modulePom.setGroupId("org.sourcepit.b2");
      modulePom.setArtifactId(projectDir.getName());
      modulePom.setVersion("1-SNAPSHOT");

      // get default tycho-surefire-plugin configuration
      Plugin plugin = modulePom.getBuild().getPluginManagement().getPluginsAsMap()
         .get("org.eclipse.tycho:tycho-surefire-plugin");
      assertNotNull(plugin);
      assertNull(plugin.getConfiguration());

      // ad custom configuration
      Xpp3Dom conf = new Xpp3Dom("configuration");
      conf.addChild(new Xpp3Dom("foo"));
      plugin.setConfiguration(conf);

      writePom(modulePom, moduleFile);

      // generate maven pom
      generatePom(projectDir);

      final Model generatedPom = readPom(new File(projectDir, "pom.xml"));

      // get generated tycho-surefire-plugin configuration
      final Plugin generatedPlugin = generatedPom.getBuild().getPluginManagement().getPluginsAsMap()
         .get("org.eclipse.tycho:tycho-surefire-plugin");
      assertNotNull(generatedPlugin);

      // assert that plugin configuration from module.xml overrides the configuration from the default template
      final Xpp3Dom generatedConf = (Xpp3Dom) generatedPlugin.getConfiguration();
      assertNotNull(generatedConf);
      assertNotNull(generatedConf.getChild("foo"));
   }

   private void generatePom(final File projectDir) throws Exception
   {
      final MavenExecutionResult2 result = buildProject(new File(projectDir, "module.xml"));
      final MavenSession mavenSession = result.getSession();
      legacySupport.setSession(mavenSession);

      final MavenProject project = mavenSession.getProjects().get(0);

      final Properties properties = new Properties();
      properties.putAll(mavenSession.getSystemProperties());
      properties.putAll(mavenSession.getUserProperties());

      mavenSession.setCurrentProject(project);

      final BootstrapSession bootSession = new BootstrapSession(mavenSession, mavenSession.getProjects(),
         Collections.<File> emptyList());
      bootSession.setCurrentBootstrapProject(project);

      b2SessionInitializer.initialize(bootSession, properties);

      final B2Request b2Request = b2SessionInitializer.newB2Request(project);

      final AbstractModule module = modelBuilder.build(b2Request);

      final B2GenerationRequest request = new B2GenerationRequest();
      request.setModule(module);
      request.setConverter(b2Request.getConverter());
      request.setModelCache(b2Request.getModelCache());
      request.setTemplates(b2Request.getTemplates());

      final B2Generator generator = new B2Generator(Collections.singletonList(pomGenerator),
         Collections.<B2GeneratorLifecycleParticipant> emptyList());
      generator.generate(request);
   }

   private Model readPom(File pom)
   {
      final Map<String, String> options = new HashMap<String, String>();
      options.put(ModelReader.IS_STRICT, Boolean.FALSE.toString());
      try
      {
         return new DefaultModelReader().read(pom, options);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   private void writePom(Model model, File file)
   {
      try
      {
         new DefaultModelWriter().write(file, null, model);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }
}
