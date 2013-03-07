/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.junit.Test;
import org.sourcepit.b2.maven.AbstractB2MavenPluginTest;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;

public class ProjectVersionGeneratorTest extends AbstractB2MavenPluginTest
{
   @Test
   public void testModuleSnapshotVersion() throws Exception
   {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      AbstractModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.putAnnotationEntry("maven", "pomFile", pomFile.getAbsolutePath());
      module.setVersion("1.0.0.qualifier");
      
      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(module, new LinkedPropertiesMap(), null);

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
   public void testProjectSnapshotVersion() throws Exception
   {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.putAnnotationEntry("maven", "pomFile", pomFile.getAbsolutePath());
      project.setVersion("1.0.0.qualifier");
      
      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(project, new LinkedPropertiesMap(), null);

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
   public void testModuleQualifedVersion() throws Exception
   {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      AbstractModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.putAnnotationEntry("maven", "pomFile", pomFile.getAbsolutePath());
      module.setVersion("1.0.0.rc1");
      
      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(module, new LinkedPropertiesMap(), null);

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
   public void testProjectQualifedVersion() throws Exception
   {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.putAnnotationEntry("maven", "pomFile", pomFile.getAbsolutePath());
      project.setVersion("1.0.0.rc1");
      
      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(project, new LinkedPropertiesMap(), null);

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
   public void testModuleVersion() throws Exception
   {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      AbstractModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.putAnnotationEntry("maven", "pomFile", pomFile.getAbsolutePath());
      module.setVersion("1.0.0");
      
      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(module, new LinkedPropertiesMap(), null);

      Model converted = new DefaultModelReader().read(pomFile, null);
      
      assertEquals("1.0.0", converted.getProperties().getProperty("module.version"));
      assertEquals("1.0.0", converted.getProperties().getProperty("module.tychoVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("module.osgiVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("module.mavenVersion"));;
      
      assertEquals("1.0.0", converted.getProperties().getProperty("project.tychoVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("project.osgiVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("project.mavenVersion"));
   }
   
   @Test
   public void testProjectVersion() throws Exception
   {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.putAnnotationEntry("maven", "pomFile", pomFile.getAbsolutePath());
      project.setVersion("1.0.0");
      
      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(project, new LinkedPropertiesMap(), null);

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
