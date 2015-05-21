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

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getPlugin;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getPluginExecution;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
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
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.internal.maven.MavenB2RequestFactory;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest2;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.maven.model.util.MavenModelUtils;
import org.sourcepit.common.maven.testing.MavenExecutionResult2;
import org.sourcepit.common.maven.util.Xpp3Utils;

public class PomGenerator2Test extends AbstractB2SessionWorkspaceTest2 {
   @Inject
   private LegacySupport legacySupport;

   @Inject
   private MavenB2RequestFactory b2SessionInitializer;

   @Inject
   private IB2ModelBuilder modelBuilder;

   @Inject
   private PomGenerator pomGenerator;

   @Test
   public void testModuleTemplatesOrdering() throws Exception {
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
      Plugin plugin = modulePom.getBuild()
         .getPluginManagement()
         .getPluginsAsMap()
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
      final Plugin generatedPlugin = generatedPom.getBuild()
         .getPluginManagement()
         .getPluginsAsMap()
         .get("org.eclipse.tycho:tycho-surefire-plugin");
      assertNotNull(generatedPlugin);

      // assert that plugin configuration from module.xml overrides the configuration from the default template
      final Xpp3Dom generatedConf = (Xpp3Dom) generatedPlugin.getConfiguration();
      assertNotNull(generatedConf);
      assertNotNull(generatedConf.getChild("foo"));
   }

   @Test
   public void testConfigureReleasePreparationGoals() throws Exception {
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

      final String defaultGoals = modulePom.getProperties().getProperty("b2.release.preparationGoals");
      assertEquals("clean verify", defaultGoals);

      modulePom.getProperties().setProperty("b2.release.preparationGoals", "");

      writePom(modulePom, moduleFile);

      // generate maven pom
      generatePom(projectDir);

      final Model generatedPom = readPom(new File(projectDir, "pom.xml"));

      final String generatedGoals = generatedPom.getProperties().getProperty("b2.release.preparationGoals");
      assertEquals("", generatedGoals);

      // get generated tycho-surefire-plugin configuration
      final Plugin generatedPlugin = generatedPom.getBuild()
         .getPluginManagement()
         .getPluginsAsMap()
         .get("org.apache.maven.plugins:maven-release-plugin");
      assertNotNull(generatedPlugin);

      // assert that plugin configuration from module.xml overrides the configuration from the default template
      final Xpp3Dom generatedConf = (Xpp3Dom) generatedPlugin.getConfiguration();
      assertNotNull(generatedConf);
      Xpp3Dom child = generatedConf.getChild("preparationGoals");
      assertNotNull(child);
      assertEquals("${b2.release.preparationGoals}", child.getValue());
   }

   @Test
   public void testDisableDefaultInstallAndDeploy() throws Exception {
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

      // add custom config
      Plugin plugin = getPlugin(modulePom.getBuild(), true, "org.apache.maven.plugins", "maven-install-plugin",
         "${maven.javadocPlugin.version}");
      getPluginExecution(plugin, true, "default-install").setPhase("package");

      writePom(modulePom, moduleFile);

      // generate maven pom
      generatePom(projectDir);

      final Model generatedPom = readPom(new File(projectDir, "pom.xml"));

      plugin = getPlugin(generatedPom.getBuild(), false, "org.eclipse.tycho", "tycho-p2-plugin", null);
      assertNotNull(plugin);
      assertEquals("none", getPluginExecution(plugin, false, "default-update-local-index").getPhase());

      plugin = getPlugin(generatedPom.getBuild(), false, "org.apache.maven.plugins", "maven-source-plugin", null);
      assertNotNull(plugin);
      assertEquals("none", getPluginExecution(plugin, false, "attach-sources").getPhase());

      plugin = getPlugin(generatedPom.getBuild(), false, "org.apache.maven.plugins", "maven-javadoc-plugin", null);
      assertNotNull(plugin);
      assertEquals("none", getPluginExecution(plugin, false, "attach-javadocs").getPhase());

      plugin = getPlugin(generatedPom.getBuild(), false, "org.apache.maven.plugins", "maven-install-plugin", null);
      assertNotNull(plugin);
      assertEquals("none", getPluginExecution(plugin, false, "default-install").getPhase());

      plugin = getPlugin(generatedPom.getBuild(), false, "org.apache.maven.plugins", "maven-deploy-plugin", null);
      assertNotNull(plugin);
      assertEquals("none", getPluginExecution(plugin, false, "default-deploy").getPhase());
   }

   @Test
   public void testBug128_AllwaysSetB2MavenReleaseManager() throws Exception {
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

      modulePom.getProperties().setProperty("b2.version", "1");

      // add custom config
      Plugin plugin = getPlugin(modulePom.getBuild(), true, "org.apache.maven.plugins", "maven-release-plugin",
         "${maven.releasePlugin.version}");

      final Dependency dependency = new Dependency();
      dependency.setGroupId("foo");
      dependency.setArtifactId("bar");
      dependency.setVersion("1");

      plugin.getDependencies().clear();
      plugin.getDependencies().add(dependency);

      Xpp3Dom cfg = MavenModelUtils.getConfiguration(plugin, true);
      Xpp3Utils.addValueNode(cfg, "mavenExecutorId", "bar");
      Xpp3Utils.addValueNode(cfg, "foo", "bar");

      writePom(modulePom, moduleFile);

      // generate maven pom
      Properties properties = new Properties();
      properties.setProperty("maven.home", "my-user-home");
      generatePom(projectDir, properties);

      final Model generatedPom = readPom(new File(projectDir, "pom.xml"));

      // plugin
      plugin = getPlugin(generatedPom.getBuild(), true, "org.apache.maven.plugins", "maven-release-plugin", null);
      assertEquals(2, plugin.getDependencies().size());

      cfg = MavenModelUtils.getConfiguration(plugin, true);
      assertEquals(4, cfg.getChildCount());

      assertEquals("forked-path", cfg.getChild("mavenExecutorId").getValue());
      assertEquals("my-user-home", cfg.getChild("mavenHome").getValue());
      assertEquals("${b2.release.preparationGoals}", cfg.getChild("preparationGoals").getValue());
      assertEquals("bar", cfg.getChild("foo").getValue());

      // plugin management
      plugin = getPlugin(generatedPom.getBuild().getPluginManagement(), true, "org.apache.maven.plugins",
         "maven-release-plugin", null);
      assertEquals(1, plugin.getDependencies().size());

      cfg = MavenModelUtils.getConfiguration(plugin, true);
      assertEquals(3, cfg.getChildCount());

      assertEquals("forked-path", cfg.getChild("mavenExecutorId").getValue());
      assertEquals("my-user-home", cfg.getChild("mavenHome").getValue());
      assertEquals("${b2.release.preparationGoals}", cfg.getChild("preparationGoals").getValue());
   }

   private void generatePom(final File projectDir) throws Exception {
      generatePom(projectDir, null);
   }

   private void generatePom(final File projectDir, Properties userProperties) throws Exception {
      final MavenExecutionResult2 result = buildProject(new File(projectDir, "module.xml"), userProperties, false);
      final MavenSession mavenSession = result.getSession();
      legacySupport.setSession(mavenSession);

      final MavenProject project = mavenSession.getProjects().get(0);

      final Properties properties = new Properties();
      properties.putAll(mavenSession.getSystemProperties());
      properties.putAll(mavenSession.getUserProperties());

      mavenSession.setCurrentProject(project);

      final B2Request b2Request = b2SessionInitializer.newB2Request(mavenSession, project);

      final AbstractModule module = modelBuilder.build(b2Request);

      final B2GenerationRequest request = new B2GenerationRequest();
      request.setModule(module);
      request.setModuleDirectory(new ModuleDirectory(module.getDirectory(), null));
      request.setModuleProperties(b2Request.getModuleProperties());
      request.setTemplates(b2Request.getTemplates());

      final B2Generator generator = new B2Generator(Collections.singletonList(pomGenerator),
         Collections.<B2GeneratorLifecycleParticipant> emptyList());
      generator.generate(request);
   }

   private Model readPom(File pom) {
      final Map<String, String> options = new HashMap<String, String>();
      options.put(ModelReader.IS_STRICT, Boolean.FALSE.toString());
      try {
         return new DefaultModelReader().read(pom, options);
      }
      catch (IOException e) {
         throw new IllegalStateException(e);
      }
   }

   private void writePom(Model model, File file) {
      try {
         new DefaultModelWriter().write(file, null, model);
      }
      catch (IOException e) {
         throw new IllegalStateException(e);
      }
   }
}
