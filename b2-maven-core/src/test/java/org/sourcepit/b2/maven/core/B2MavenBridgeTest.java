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

package org.sourcepit.b2.maven.core;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;

public class B2MavenBridgeTest extends TestCase {
   @Test
   public void testConnectProjects() {
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

      final ResourceSet resourceSet = new ResourceSetImpl();
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("gav", new ResourceFactoryImpl());
      resourceSet.getPackageRegistry().put(ModuleModelPackage.eNS_URI, ModuleModelPackage.eINSTANCE);

      Resource resourceR = resourceSet.createResource(B2MavenBridge.toArtifactURI(mavenProjectR, "module", null));
      resourceR.getContents().add(moduleR);

      Resource resourceA = resourceSet.createResource(B2MavenBridge.toArtifactURI(mavenProjectA, "module", null));
      resourceA.getContents().add(moduleA);

      Resource resourceB = resourceSet.createResource(B2MavenBridge.toArtifactURI(mavenProjectB, "module", null));
      resourceB.getContents().add(moduleB);

      resourceSet.getResources().add(resourceR);
      resourceSet.getResources().add(resourceA);
      resourceSet.getResources().add(resourceB);

      B2MavenBridge b2Bridge = B2MavenBridge.get(mavenSession, resourceSet);
      assertSame(b2Bridge, B2MavenBridge.get(mavenSession));

      for (MavenProject mavenProject : mavenSession.getProjects()) {
         if (B2MavenBridge.isModuleProject(resourceSet, mavenProject)) {
            assertNotNull(mavenProject.getArtifactId(), mavenProject.getContextValue(AbstractModule.class.getName()));
         }
         else {
            assertNotNull(mavenProject.getArtifactId(), mavenProject.getContextValue(Project.class.getName()));
         }
      }

      assertThat(b2Bridge.getModule(mavenProjectR), Is.is((AbstractModule) moduleR));
      assertThat(b2Bridge.getModule(mavenProjectA), Is.is((AbstractModule) moduleA));
      assertThat(b2Bridge.getModule(mavenProjectB), Is.is((AbstractModule) moduleB));
      assertNull(b2Bridge.getModule(mavenProjectAPlugin));
      assertNull(b2Bridge.getModule(mavenProjectASite));
      assertNull(b2Bridge.getModule(mavenProjectBPlugin));
      assertNull(b2Bridge.getModule(mavenProjectBTest));
      assertNull(b2Bridge.getModule(mavenProjectBFeature));

      assertNull(b2Bridge.getEclipseProject(mavenProjectR));
      assertNull(b2Bridge.getEclipseProject(mavenProjectA));
      assertNull(b2Bridge.getEclipseProject(mavenProjectB));
      assertThat(b2Bridge.getEclipseProject(mavenProjectASite), Is.is((Project) siteA));
      assertThat(b2Bridge.getEclipseProject(mavenProjectAPlugin), Is.is((Project) pluginA));
      assertThat(b2Bridge.getEclipseProject(mavenProjectBPlugin), Is.is((Project) pluginB));
      assertThat(b2Bridge.getEclipseProject(mavenProjectBTest), Is.is((Project) testB));
      assertThat(b2Bridge.getEclipseProject(mavenProjectBFeature), Is.is((Project) featureB));

      b2Bridge.disconnect(mavenSession);

      assertNull(b2Bridge.getModule(mavenProjectR));
      assertNull(b2Bridge.getModule(mavenProjectA));
      assertNull(b2Bridge.getModule(mavenProjectB));

      for (MavenProject mavenProject : mavenSession.getProjects()) {
         if (B2MavenBridge.isModuleProject(resourceSet, mavenProject)) {
            assertNull(mavenProject.getContextValue(AbstractModule.class.getName()));
         }
         else {
            assertNull(mavenProject.getContextValue(Project.class.getName()));
         }
      }
   }

   private static PluginProject createPluginProject(File projectDir) {
      PluginProject plugin = ModuleModelFactory.eINSTANCE.createPluginProject();
      plugin.setDirectory(projectDir);
      plugin.setId(projectDir.getName());
      return plugin;
   }

   private static FeatureProject createFeatureProject(File projectDir) {
      FeatureProject feature = ModuleModelFactory.eINSTANCE.createFeatureProject();
      feature.setDirectory(projectDir);
      feature.setId(projectDir.getName());
      return feature;
   }

   private static SiteProject createSiteProject(File projectDir) {
      SiteProject site = ModuleModelFactory.eINSTANCE.createSiteProject();
      site.setDirectory(projectDir);
      site.setId(projectDir.getName());
      return site;
   }

   private static CompositeModule createCompositeModule(File moduleDir) {
      CompositeModule moduleR = ModuleModelFactory.eINSTANCE.createCompositeModule();
      moduleR.setId(moduleDir.getName());
      moduleR.setDirectory(moduleDir);
      moduleR.setVersion("1");
      return moduleR;
   }

   private static BasicModule createBasicModule(File moduleDir) {
      BasicModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.setId(moduleDir.getName());
      module.setDirectory(moduleDir);
      module.setVersion("1");
      return module;
   }

   private static MavenProject createMavenProject(File baseDir) {
      MavenProject mavenProject = new MavenProject();
      mavenProject.setFile(new File(baseDir, "pom.xml"));
      mavenProject.setArtifactId(baseDir.getName());
      mavenProject.setGroupId(mavenProject.getArtifactId());
      mavenProject.setVersion("1");
      return mavenProject;
   }
}
