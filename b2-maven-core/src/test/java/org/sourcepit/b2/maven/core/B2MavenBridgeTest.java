/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.maven.core;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.common.util.BasicEList;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.guplex.test.PlexusTest;

public class B2MavenBridgeTest extends PlexusTest
{
   private B2MavenBridge b2Bridge;

   @Before
   public void setUp() throws Exception
   {
      b2Bridge = lookup(B2MavenBridge.class);
   }

   @Test
   public void testConnectSessions()
   {
      MavenSession mavenSession = mock(MavenSession.class);
      B2Session b2Session = mock(B2Session.class);
      when(b2Session.getProjects()).thenReturn(new BasicEList<ModuleProject>());

      b2Bridge.connect(mavenSession, b2Session);

      assertThat(b2Bridge.getB2Session(mavenSession), IsEqual.equalTo(b2Session));

      try
      {
         b2Bridge.connect(mavenSession, b2Session);
         fail();
      }
      catch (IllegalStateException e)
      {
      }

      try
      {
         b2Bridge.connect(mavenSession, mock(B2Session.class));
         fail();
      }
      catch (IllegalStateException e)
      {
      }
   }

   @Test
   public void testConnectProjects()
   {
      // : B2Session
      // reactor : ModuleProject
      // module-a : ModuleProject
      // module-b : ModuleProject

      // reactor : CompositeModule
      // * module-a : BasicModule
      // * module-b : BasicModule

      // common project fodlers
      File reactorDir = new File("reactor");
      File projectADir = new File(reactorDir, "module-a");
      File projectASiteDir = new File(projectADir, "sites/site-a");
      File projectAPluginDir = new File(projectADir, "plugins/plugin-a");
      File projectBDir = new File(reactorDir, "module-b");
      File projectBPluginDir = new File(projectBDir, "plugin-b");
      File projectBTestDir = new File(projectBDir, "test-b");
      File projectBFeatureDir = new File(projectBDir, "feature-b");

      // module model
      SiteProject siteA = createSiteProject(projectASiteDir);
      SitesFacet sitesA = ModuleModelFactory.eINSTANCE.createSitesFacet();
      sitesA.getProjects().add(siteA);

      PluginProject pluginA = createPluginProject(projectAPluginDir);
      PluginsFacet pluginsA = ModuleModelFactory.eINSTANCE.createPluginsFacet();
      pluginsA.getProjects().add(pluginA);

      BasicModule moduleA = createBasicModule(projectADir);
      moduleA.getFacets().add(sitesA);
      moduleA.getFacets().add(pluginsA);

      PluginProject pluginB = createPluginProject(projectBPluginDir);
      PluginsFacet pluginsB = ModuleModelFactory.eINSTANCE.createPluginsFacet();
      pluginsB.getProjects().add(pluginB);

      PluginProject testB = createPluginProject(projectBTestDir);
      testB.setTestPlugin(true);
      PluginsFacet testsB = ModuleModelFactory.eINSTANCE.createPluginsFacet();
      testsB.getProjects().add(testB);

      FeatureProject featureB = createFeatureProject(projectBFeatureDir);
      FeaturesFacet featuresB = ModuleModelFactory.eINSTANCE.createFeaturesFacet();
      featuresB.getProjects().add(featureB);

      BasicModule moduleB = createBasicModule(projectBDir);
      moduleB.getFacets().add(pluginsB);
      moduleB.getFacets().add(testsB);
      moduleB.getFacets().add(featuresB);

      CompositeModule moduleR = createCompositeModule(reactorDir);
      moduleR.getModules().add(moduleA);
      moduleR.getModules().add(moduleB);

      // b2 session
      ModuleProject moduleProjectR = createModuleProject(reactorDir);
      ModuleProject moduleProjectA = createModuleProject(projectADir);
      ModuleProject moduleProjectB = createModuleProject(projectBDir);

      moduleProjectR.setModuleModel(moduleR);
      moduleProjectA.setModuleModel(moduleA);
      moduleProjectB.setModuleModel(moduleB);

      B2Session b2Session = SessionModelFactory.eINSTANCE.createB2Session();
      b2Session.getProjects().add(moduleProjectR);
      b2Session.getProjects().add(moduleProjectA);
      b2Session.getProjects().add(moduleProjectB);

      // Maven session
      MavenProject mavenProjectR = createMavenProject(reactorDir);
      MavenProject mavenProjectA = createMavenProject(projectADir);
      MavenProject mavenProjectASite = createMavenProject(projectASiteDir);
      MavenProject mavenProjectAPlugin = createMavenProject(projectAPluginDir);
      MavenProject mavenProjectB = createMavenProject(projectBDir);
      MavenProject mavenProjectBPlugin = createMavenProject(projectBPluginDir);
      MavenProject mavenProjectBTest = createMavenProject(projectBTestDir);
      MavenProject mavenProjectBFeature = createMavenProject(projectBFeatureDir);

      MavenSession mavenSession = mock(MavenSession.class);
      when(mavenSession.getProjects()).thenReturn(new ArrayList<MavenProject>());
      mavenSession.getProjects().add(mavenProjectR);
      mavenSession.getProjects().add(mavenProjectA);
      mavenSession.getProjects().add(mavenProjectASite);
      mavenSession.getProjects().add(mavenProjectAPlugin);
      mavenSession.getProjects().add(mavenProjectB);
      mavenSession.getProjects().add(mavenProjectBPlugin);
      mavenSession.getProjects().add(mavenProjectBTest);
      mavenSession.getProjects().add(mavenProjectBFeature);

      b2Bridge.connect(mavenSession, b2Session);

      assertThat(b2Bridge.getB2Session(mavenSession), Is.is(b2Session));
      assertThat(b2Bridge.getMavenSession(b2Session), Is.is(mavenSession));

      assertThat(b2Bridge.getModuleProject(mavenProjectR), Is.is(moduleProjectR));
      assertThat(b2Bridge.getModuleProject(mavenProjectA), Is.is(moduleProjectA));
      assertThat(b2Bridge.getModuleProject(mavenProjectB), Is.is(moduleProjectB));
      assertNull(b2Bridge.getModuleProject(mavenProjectASite));
      assertNull(b2Bridge.getModuleProject(mavenProjectAPlugin));
      assertNull(b2Bridge.getModuleProject(mavenProjectBPlugin));
      assertNull(b2Bridge.getModuleProject(mavenProjectBTest));
      assertNull(b2Bridge.getModuleProject(mavenProjectBFeature));
      assertThat(b2Bridge.getMavenProject(moduleProjectR), Is.is(mavenProjectR));
      assertThat(b2Bridge.getMavenProject(moduleProjectA), Is.is(mavenProjectA));
      assertThat(b2Bridge.getMavenProject(moduleProjectB), Is.is(mavenProjectB));

      assertThat(b2Bridge.getModule(mavenProjectR), Is.is((AbstractModule) moduleR));
      assertThat(b2Bridge.getModule(mavenProjectA), Is.is((AbstractModule) moduleA));
      assertThat(b2Bridge.getModule(mavenProjectB), Is.is((AbstractModule) moduleB));
      assertNull(b2Bridge.getModule(mavenProjectAPlugin));
      assertNull(b2Bridge.getModule(mavenProjectASite));
      assertNull(b2Bridge.getModule(mavenProjectBPlugin));
      assertNull(b2Bridge.getModule(mavenProjectBTest));
      assertNull(b2Bridge.getModule(mavenProjectBFeature));
      assertThat(b2Bridge.getMavenProject(moduleR), Is.is(mavenProjectR));

      assertNull(b2Bridge.getEclipseProject(mavenProjectR));
      assertNull(b2Bridge.getEclipseProject(mavenProjectA));
      assertNull(b2Bridge.getEclipseProject(mavenProjectB));
      assertThat(b2Bridge.getEclipseProject(mavenProjectASite), Is.is((Project) siteA));
      assertThat(b2Bridge.getEclipseProject(mavenProjectAPlugin), Is.is((Project) pluginA));
      assertThat(b2Bridge.getEclipseProject(mavenProjectBPlugin), Is.is((Project) pluginB));
      assertThat(b2Bridge.getEclipseProject(mavenProjectBTest), Is.is((Project) testB));
      assertThat(b2Bridge.getEclipseProject(mavenProjectBFeature), Is.is((Project) featureB));
      assertThat(b2Bridge.getMavenProject(siteA), Is.is(mavenProjectASite));
      assertThat(b2Bridge.getMavenProject(pluginA), Is.is(mavenProjectAPlugin));
      assertThat(b2Bridge.getMavenProject(pluginB), Is.is(mavenProjectBPlugin));
      assertThat(b2Bridge.getMavenProject(testB), Is.is(mavenProjectBTest));
      assertThat(b2Bridge.getMavenProject(featureB), Is.is(mavenProjectBFeature));

   }

   private static PluginProject createPluginProject(File projectDir)
   {
      PluginProject plugin = ModuleModelFactory.eINSTANCE.createPluginProject();
      plugin.setDirectory(projectDir);
      plugin.setId(projectDir.getName());
      return plugin;
   }

   private static FeatureProject createFeatureProject(File projectDir)
   {
      FeatureProject feature = ModuleModelFactory.eINSTANCE.createFeatureProject();
      feature.setDirectory(projectDir);
      feature.setId(projectDir.getName());
      return feature;
   }

   private static SiteProject createSiteProject(File projectDir)
   {
      SiteProject site = ModuleModelFactory.eINSTANCE.createSiteProject();
      site.setDirectory(projectDir);
      site.setId(projectDir.getName());
      return site;
   }

   private static CompositeModule createCompositeModule(File moduleDir)
   {
      CompositeModule moduleR = ModuleModelFactory.eINSTANCE.createCompositeModule();
      moduleR.setId(moduleDir.getName());
      moduleR.setDirectory(moduleDir);
      moduleR.setVersion("1");
      return moduleR;
   }

   private static BasicModule createBasicModule(File moduleDir)
   {
      BasicModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.setId(moduleDir.getName());
      module.setDirectory(moduleDir);
      module.setVersion("1");
      return module;
   }

   private static ModuleProject createModuleProject(File baseDir)
   {
      ModuleProject moduleProject = SessionModelFactory.eINSTANCE.createModuleProject();
      moduleProject.setDirectory(baseDir);
      moduleProject.setArtifactId(baseDir.getName());
      moduleProject.setGroupId(moduleProject.getArtifactId());
      moduleProject.setVersion("1");
      return moduleProject;
   }

   private static MavenProject createMavenProject(File baseDir)
   {
      MavenProject mavenProject = new MavenProject();
      mavenProject.setFile(new File(baseDir, "pom.xml"));
      mavenProject.setArtifactId(baseDir.getName());
      mavenProject.setGroupId(mavenProject.getArtifactId());
      mavenProject.setVersion("1");
      return mavenProject;
   }
}
