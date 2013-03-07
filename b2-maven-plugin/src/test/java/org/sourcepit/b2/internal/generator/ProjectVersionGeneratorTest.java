/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.junit.Test;
import org.sourcepit.b2.maven.AbstractB2MavenPluginTest;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;

public class ProjectVersionGeneratorTest extends AbstractB2MavenPluginTest
{
   @Test
   public void testConvertVersionProperties() throws IOException
   {
      Model model = new Model();
      model.setModelEncoding("UTF-8");
      model.setModelVersion("4.0.0");

      model.getProperties().setProperty("a", "${project.version}");
      model.getProperties().setProperty("b", "foo-${project.version}");
      model.getProperties().setProperty("c", "${project.osgiVersion}");
      model.getProperties().setProperty("d", "${project.mavenVersion}");
      model.getProperties().setProperty("e", "mööp");

      Dependency dependency = new Dependency();
      dependency.setVersion("${project.version}");
      model.getDependencies().add(dependency);

      File pomFile = getWorkspace().newFile();
      new DefaultModelWriter().write(pomFile, null, model);

      PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.putAnnotationEntry("maven", "pomFile", pomFile.getAbsolutePath());
      project.setVersion("1.0.0.rc1");

      ProjectVersionGenerator generator = new ProjectVersionGenerator();
      generator.generate(project, new LinkedPropertiesMap(), null);

      Model converted = new DefaultModelReader().read(pomFile, null);
      assertEquals("${project.mavenVersion}", converted.getProperties().getProperty("a"));
      assertEquals("foo-${project.mavenVersion}", converted.getProperties().getProperty("b"));
      assertEquals("${project.osgiVersion}", converted.getProperties().getProperty("c"));
      assertEquals("${project.mavenVersion}", converted.getProperties().getProperty("d"));
      assertEquals("mööp", converted.getProperties().getProperty("e"));
      
      assertEquals("${project.mavenVersion}", converted.getDependencies().get(0).getVersion());
   }
   
   @Test
   public void testSnapshotVersion() throws Exception
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
      
      assertEquals("1.0.0-SNAPSHOT", converted.getProperties().getProperty("project.tychoVersion"));
      assertEquals("1.0.0.qualifier", converted.getProperties().getProperty("project.osgiVersion"));
      assertEquals("1.0.0-SNAPSHOT", converted.getProperties().getProperty("project.mavenVersion"));
   }
   
   @Test
   public void testQualifedVersion() throws Exception
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
      
      assertEquals("1.0.0.rc1", converted.getProperties().getProperty("project.tychoVersion"));
      assertEquals("1.0.0.rc1", converted.getProperties().getProperty("project.osgiVersion"));
      assertEquals("1.0.0-rc1", converted.getProperties().getProperty("project.mavenVersion"));
   }
   
   @Test
   public void testVersion() throws Exception
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
      
      assertEquals("1.0.0", converted.getProperties().getProperty("project.tychoVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("project.osgiVersion"));
      assertEquals("1.0.0", converted.getProperties().getProperty("project.mavenVersion"));
   }

}
