/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.Properties;

import junit.framework.TestCase;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.Repository;

public class ModelCutterTest extends TestCase
{
   private ModelCutter modelCutter = new ModelCutter();

   public void testString() throws Exception
   {
      Model target = new Model();
      target.setGroupId("foo");

      Model source = target.clone();

      modelCutter.cut(target, source);
      assertNull(target.getGroupId());
      assertEquals("foo", source.getGroupId());

      target.setGroupId("bar");
      modelCutter.cut(target, source);
      assertEquals("bar", target.getGroupId());
      assertEquals("foo", source.getGroupId());
   }

   public void testRepository() throws Exception
   {
      Repository repository = new Repository();
      repository.setId("foo");
      repository.setUrl("fooUrl");

      Repository repository2 = new Repository();
      repository2.setId("bar");
      repository2.setUrl("barUrl");

      Model target = new Model();
      target.getRepositories().add(repository);
      target.getRepositories().add(repository2);

      Model source = target.clone();

      modelCutter.cut(target, source);
      assertTrue(target.getRepositories().isEmpty());

      target.getRepositories().add(repository);
      target.getRepositories().add(repository2);

      source.getRepositories().remove(0);

      assertEquals(2, target.getRepositories().size());
      assertEquals(1, source.getRepositories().size());

      modelCutter.cut(target, source);
      assertEquals(1, target.getRepositories().size());
   }

   public void testProperties() throws Exception
   {
      Properties targetProperties = new Properties();
      targetProperties.setProperty("foo", "bar");
      targetProperties.setProperty("bar", "foo");

      Model target = new Model();
      target.setProperties(targetProperties);

      Model source = target.clone();
      source.getProperties().remove("bar");

      assertEquals(2, target.getProperties().size());
      assertEquals(1, source.getProperties().size());

      modelCutter.cut(target, source);
      assertEquals(1, target.getProperties().size());
      assertEquals(1, source.getProperties().size());

      assertEquals("foo", target.getProperties().getProperty("bar"));
   }

   public void testBuild() throws Exception
   {
      Model target = new Model();
      target.setBuild(new Build());
      target.getBuild().setDirectory("foo");

      Plugin plugin = new Plugin();
      plugin.setGroupId("foo");
      plugin.setArtifactId("bar");
      plugin.setVersion("1");

      target.getBuild().getPlugins().add(plugin);

      target.getBuild().setPluginManagement(new PluginManagement());
      target.getBuild().getPluginManagement().getPlugins().add(plugin.clone());

      Model source = target.clone();

      modelCutter.cut(target, source);
      assertNull(target.getBuild());

      target = source.clone();
      assertEquals(1, target.getBuild().getPluginManagement().getPlugins().size());
      assertEquals(1, source.getBuild().getPluginManagement().getPlugins().size());

      source.getBuild().getPluginManagement().getPlugins().clear();
      assertEquals(1, target.getBuild().getPluginManagement().getPlugins().size());
      assertEquals(0, source.getBuild().getPluginManagement().getPlugins().size());

      modelCutter.cut(target, source);
      assertNotNull(target.getBuild());
      assertEquals(0, target.getBuild().getPlugins().size());
      assertEquals(1, target.getBuild().getPluginManagement().getPlugins().size());
   }
}
