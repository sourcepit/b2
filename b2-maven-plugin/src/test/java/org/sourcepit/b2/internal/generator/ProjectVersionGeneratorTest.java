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
import static org.junit.Assert.assertNull;

import java.io.File;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.junit.Test;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.maven.AbstractB2MavenPluginTest;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;

public class ProjectVersionGeneratorTest extends AbstractB2MavenPluginTest {
   @Test
   public void testModuleSnapshotVersion() throws Exception {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      AbstractModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.setAnnotationData("maven", "pomFile", pomFile.getAbsolutePath());
      module.setVersion("1.0.0.qualifier");

      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(module, new LinkedPropertiesMap(), null, new ModuleDirectory(pomFile.getParentFile(), null));

      Model converted = new DefaultModelReader().read(pomFile, null);

      assertEquals("1.0.0-SNAPSHOT", converted.getProperties().getProperty("module.version"));
      assertEquals("1.0.0-SNAPSHOT", converted.getProperties().getProperty("module.tychoVersion"));
      assertEquals("1.0.0.qualifier", converted.getProperties().getProperty("module.osgiVersion"));
      assertEquals("1.0.0-SNAPSHOT", converted.getProperties().getProperty("module.mavenVersion"));

      assertEquals("1.0.0-SNAPSHOT", converted.getProperties().getProperty("project.tychoVersion"));
      assertEquals("1.0.0.qualifier", converted.getProperties().getProperty("project.osgiVersion"));
      assertEquals("1.0.0-SNAPSHOT", converted.getProperties().getProperty("project.mavenVersion"));
   }

   @Test
   public void testProjectSnapshotVersion() throws Exception {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.setAnnotationData("maven", "pomFile", pomFile.getAbsolutePath());
      project.setVersion("1.0.0.qualifier");

      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(project, new LinkedPropertiesMap(), null, new ModuleDirectory(pomFile.getParentFile(), null));

      Model converted = new DefaultModelReader().read(pomFile, null);

      assertNull(converted.getProperties().getProperty("module.version"));
      assertNull(converted.getProperties().getProperty("module.tychoVersion"));
      assertNull(converted.getProperties().getProperty("module.osgiVersion"));
      assertNull(converted.getProperties().getProperty("module.mavenVersion"));

      assertEquals("1.0.0-SNAPSHOT", converted.getProperties().getProperty("project.tychoVersion"));
      assertEquals("1.0.0.qualifier", converted.getProperties().getProperty("project.osgiVersion"));
      assertEquals("1.0.0-SNAPSHOT", converted.getProperties().getProperty("project.mavenVersion"));
   }

   @Test
   public void testModuleQualifedVersion() throws Exception {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      AbstractModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.setAnnotationData("maven", "pomFile", pomFile.getAbsolutePath());
      module.setVersion("1.0.0.rc1");

      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(module, new LinkedPropertiesMap(), null, new ModuleDirectory(pomFile.getParentFile(), null));

      Model converted = new DefaultModelReader().read(pomFile, null);

      assertEquals("1.0.0-rc1", converted.getProperties().getProperty("module.version"));
      assertEquals("1.0.0.rc1", converted.getProperties().getProperty("module.tychoVersion"));
      assertEquals("1.0.0.rc1", converted.getProperties().getProperty("module.osgiVersion"));
      assertEquals("1.0.0-rc1", converted.getProperties().getProperty("module.mavenVersion"));

      assertEquals("1.0.0.rc1", converted.getProperties().getProperty("project.tychoVersion"));
      assertEquals("1.0.0.rc1", converted.getProperties().getProperty("project.osgiVersion"));
      assertEquals("1.0.0-rc1", converted.getProperties().getProperty("project.mavenVersion"));
   }

   @Test
   public void testProjectQualifedVersion() throws Exception {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.setAnnotationData("maven", "pomFile", pomFile.getAbsolutePath());
      project.setVersion("1.0.0.rc1");

      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(project, new LinkedPropertiesMap(), null, new ModuleDirectory(pomFile.getParentFile(), null));

      Model converted = new DefaultModelReader().read(pomFile, null);

      assertNull(converted.getProperties().getProperty("module.version"));
      assertNull(converted.getProperties().getProperty("module.tychoVersion"));
      assertNull(converted.getProperties().getProperty("module.osgiVersion"));
      assertNull(converted.getProperties().getProperty("module.mavenVersion"));

      assertEquals("1.0.0.rc1", converted.getProperties().getProperty("project.tychoVersion"));
      assertEquals("1.0.0.rc1", converted.getProperties().getProperty("project.osgiVersion"));
      assertEquals("1.0.0-rc1", converted.getProperties().getProperty("project.mavenVersion"));
   }

   @Test
   public void testModuleVersion() throws Exception {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      AbstractModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.setAnnotationData("maven", "pomFile", pomFile.getAbsolutePath());
      module.setVersion("1.0.0");

      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(module, new LinkedPropertiesMap(), null, new ModuleDirectory(pomFile.getParentFile(), null));

      Model converted = new DefaultModelReader().read(pomFile, null);

      assertEquals("1.0.0", converted.getProperties().getProperty("module.version"));
      assertEquals("1.0.0", converted.getProperties().getProperty("module.tychoVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("module.osgiVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("module.mavenVersion"));
      ;

      assertEquals("1.0.0", converted.getProperties().getProperty("project.tychoVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("project.osgiVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("project.mavenVersion"));
   }

   @Test
   public void testProjectVersion() throws Exception {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.setAnnotationData("maven", "pomFile", pomFile.getAbsolutePath());
      project.setVersion("1.0.0");

      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(project, new LinkedPropertiesMap(), null, new ModuleDirectory(pomFile.getParentFile(), null));

      Model converted = new DefaultModelReader().read(pomFile, null);

      assertNull(converted.getProperties().getProperty("module.version"));
      assertNull(converted.getProperties().getProperty("module.tychoVersion"));
      assertNull(converted.getProperties().getProperty("module.osgiVersion"));
      assertNull(converted.getProperties().getProperty("module.mavenVersion"));

      assertEquals("1.0.0", converted.getProperties().getProperty("project.tychoVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("project.osgiVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("project.mavenVersion"));
   }

}
