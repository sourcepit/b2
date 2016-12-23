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

package org.sourcepit.b2.internal.maven;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.apache.maven.project.MavenProject;
import org.junit.Test;
import org.sourcepit.common.utils.props.PropertiesSource;

public class MavenModulePropertiesFactoryTest {
   @Test
   public void testNoModuleVersions() {
      final MavenProject project = newMavenProject("groupId", "artifactId", "1.0-SNAPSHOT");
      final MavenSession session = newMavenSession(project);

      final MavenModulePropertiesFactory factory = new MavenModulePropertiesFactory();

      final PropertiesSource properties = factory.createModuleProperties(session, project);
      assertNotNull(properties);

      assertEquals("1.0.0-SNAPSHOT", properties.get("module.version"));
      assertEquals("1.0.0-SNAPSHOT", properties.get("module.mavenVersion"));
      assertEquals("1.0.0.qualifier", properties.get("module.osgiVersion"));
      assertEquals("1.0.0-SNAPSHOT", properties.get("module.tychoVersion"));
      assertEquals("1.0.0", properties.get("module.simpleVersion"));
      assertEquals("2.0.0", properties.get("module.nextMajorVersion"));
      assertEquals("1.1.0", properties.get("module.nextMinorVersion"));
      assertEquals("1.0.1", properties.get("module.nextMicroVersion"));
      assertEquals("1.0.0-SNAPSHOT", properties.get("project.mavenVersion"));
      assertEquals("1.0.0.qualifier", properties.get("project.osgiVersion"));
      assertEquals("1.0.0-SNAPSHOT", properties.get("project.tychoVersion"));
   }

   @Test
   public void testNoModuleId() {
      final MavenProject project = newMavenProject("groupId", "artifactId", "1.0-SNAPSHOT");
      final MavenSession session = newMavenSession(project);

      final MavenModulePropertiesFactory factory = new MavenModulePropertiesFactory();

      final PropertiesSource properties = factory.createModuleProperties(session, project);
      assertNotNull(properties);
      assertNull(properties.get("module.id"));
   }

   @Test
   public void testModuleId() {
      final Properties props = new Properties();
      props.setProperty("module.id", "winter is coming");

      final MavenProject project = newMavenProject("groupId", "artifactId", "1.0-SNAPSHOT", props);
      final MavenSession session = newMavenSession(project);

      final MavenModulePropertiesFactory factory = new MavenModulePropertiesFactory();

      final PropertiesSource properties = factory.createModuleProperties(session, project);
      assertNotNull(properties);
      assertEquals("winter is coming", properties.get("module.id"));
   }

   @Test
   public void testDoNotInheritModuleId() {
      final Properties masterProps = new Properties();
      masterProps.setProperty("module.id", "yoda");

      final MavenProject master = newMavenProject("foo", "master", "1.0-SNAPSHOT", masterProps);

      final MavenProject apprentice = newMavenProject(master, "foo", "apprentice", "1.0-SNAPSHOT", null);

      final MavenSession session = newMavenSession(master, apprentice);

      final MavenModulePropertiesFactory factory = new MavenModulePropertiesFactory();

      PropertiesSource properties = factory.createModuleProperties(session, master);
      assertNotNull(properties);
      assertEquals("yoda", properties.get("module.id"));

      properties = factory.createModuleProperties(session, apprentice);
      assertNotNull(properties);
      assertNull(properties.get("module.id"));
   }

   private static MavenSession newMavenSession(MavenProject... projects) {
      final MavenSession session = mock(MavenSession.class);
      when(session.getProjects()).thenReturn(asList(projects));
      when(session.getSystemProperties()).thenReturn(new Properties());
      when(session.getUserProperties()).thenReturn(new Properties());
      return session;
   }

   private static MavenProject newMavenProject(String groupId, String artifactId, String version) {
      return newMavenProject(null, groupId, artifactId, version, null);
   }

   private static MavenProject newMavenProject(String groupId, String artifactId, String version,
      Properties properties) {
      return newMavenProject(null, groupId, artifactId, version, properties);
   }

   private static MavenProject newMavenProject(MavenProject parent, String groupId, String artifactId, String version,
      Properties properties) {
      final Model originalModel = new Model();
      if (properties != null) {
         originalModel.setProperties(properties);
      }
      originalModel.getProperties().put("project.groupId", groupId);
      originalModel.getProperties().put("project.artifactId", artifactId);
      originalModel.getProperties().put("project.version", version);

      final MavenProject project = mock(MavenProject.class);
      when(project.getGroupId()).thenReturn(groupId);
      when(project.getArtifactId()).thenReturn(artifactId);
      when(project.getVersion()).thenReturn(version);
      when(project.getOriginalModel()).thenReturn(originalModel);

      Properties mergedProps = new Properties();
      if (parent != null) {
         mergedProps.putAll(parent.getProperties());
      }
      mergedProps.putAll(originalModel.getProperties());

      final Model model = originalModel.clone();
      model.setProperties(mergedProps);

      when(project.getModel()).thenReturn(model);
      when(project.getProperties()).thenReturn(model.getProperties());

      return project;
   }

}
