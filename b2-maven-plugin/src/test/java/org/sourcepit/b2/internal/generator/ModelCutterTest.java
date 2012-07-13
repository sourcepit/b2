/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.Repository;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;

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

   public void testPluginEqualPlugin() throws Exception
   {
      Model source = new Model();
      source.setBuild(new Build());
      source.getBuild().setPluginManagement(new PluginManagement());
      source.getBuild().getPluginManagement().getPlugins()
         .add(createPlugin("org.sourcepit.b2", "b2-test-plugin", "1.0.0"));

      Model target = new Model();
      target.setBuild(new Build());
      target.getBuild().setPluginManagement(new PluginManagement());
      target.getBuild().getPluginManagement().getPlugins()
         .add(createPlugin("org.sourcepit.b2", "b2-test-plugin", "1.0.0"));

      new ModelCutter().cut(target, source);
      assertNull(target.getBuild());
   }

   public void testPluginsWithDifferentVersions() throws Exception
   {
      Model source = new Model();
      source.setBuild(new Build());
      source.getBuild().setPluginManagement(new PluginManagement());
      source.getBuild().getPluginManagement().getPlugins()
         .add(createPlugin("org.sourcepit.b2", "b2-test-plugin", "1.0.0"));

      Model target = new Model();
      target.setBuild(new Build());
      target.getBuild().setPluginManagement(new PluginManagement());
      target.getBuild().getPluginManagement().getPlugins()
         .add(createPlugin("org.sourcepit.b2", "b2-test-plugin", "2.0.0"));

      new ModelCutter().cut(target, source);
      assertNotNull(target.getBuild());
      assertNotNull(target.getBuild().getPluginManagement());

      List<Plugin> plugins = target.getBuild().getPluginManagement().getPlugins();
      assertThat(plugins.size(), Is.is(1));

      Plugin plugin = plugins.get(0);
      assertThat(plugin.getGroupId(), IsEqual.equalTo("org.sourcepit.b2"));
      assertThat(plugin.getArtifactId(), IsEqual.equalTo("b2-test-plugin"));
      assertThat(plugin.getVersion(), IsEqual.equalTo("2.0.0"));
   }
   
   public void testPluginsWithEqualDependencies() throws Exception
   {
      Model source = new Model();
      source.setBuild(new Build());
      source.getBuild().setPluginManagement(new PluginManagement());
      Dependency sourceDependency = new Dependency();
      sourceDependency.setGroupId("foo");
      sourceDependency.setArtifactId("bar");
      sourceDependency.setVersion("1");
      Plugin sourcePlugin = createPlugin("org.sourcepit.b2", "b2-test-plugin", "1.0.0");
      sourcePlugin.getDependencies().add(sourceDependency);
      source.getBuild().getPluginManagement().getPlugins().add(sourcePlugin);

      Model target = new Model();
      target.setBuild(new Build());
      target.getBuild().setPluginManagement(new PluginManagement());
      Dependency targetDependency = new Dependency();
      targetDependency.setGroupId("foo");
      targetDependency.setArtifactId("bar");
      targetDependency.setVersion("1");
      Plugin targetPlugin = createPlugin("org.sourcepit.b2", "b2-test-plugin", "1.0.0");
      targetPlugin.getDependencies().add(targetDependency);
      target.getBuild().getPluginManagement().getPlugins().add(targetPlugin);

      new ModelCutter().cut(target, source);
      assertNull(target.getBuild());
   }

   public void testPluginsWithDifferentDependencies() throws Exception
   {
      Model source = new Model();
      source.setBuild(new Build());
      source.getBuild().setPluginManagement(new PluginManagement());
      Dependency sourceDependency = new Dependency();
      sourceDependency.setGroupId("foo");
      sourceDependency.setArtifactId("bar");
      sourceDependency.setVersion("1");
      Plugin sourcePlugin = createPlugin("org.sourcepit.b2", "b2-test-plugin", "1.0.0");
      sourcePlugin.getDependencies().add(sourceDependency);
      source.getBuild().getPluginManagement().getPlugins().add(sourcePlugin);

      Model target = new Model();
      target.setBuild(new Build());
      target.getBuild().setPluginManagement(new PluginManagement());
      Dependency targetDependency = new Dependency();
      targetDependency.setGroupId("foo");
      targetDependency.setArtifactId("bar");
      targetDependency.setVersion("2");
      Plugin targetPlugin = createPlugin("org.sourcepit.b2", "b2-test-plugin", "1.0.0");
      targetPlugin.getDependencies().add(targetDependency);
      target.getBuild().getPluginManagement().getPlugins().add(targetPlugin);

      new ModelCutter().cut(target, source);
      assertNotNull(target.getBuild());
      assertNotNull(target.getBuild().getPluginManagement());

      List<Plugin> plugins = target.getBuild().getPluginManagement().getPlugins();
      assertThat(plugins.size(), Is.is(1));
      
      Plugin plugin = plugins.get(0);
      assertThat(plugin.getGroupId(), IsEqual.equalTo("org.sourcepit.b2"));
      assertThat(plugin.getArtifactId(), IsEqual.equalTo("b2-test-plugin"));
      assertThat(plugin.getVersion(), IsEqual.equalTo("1.0.0"));
      
      List<Dependency> dependencies = plugin.getDependencies();
      assertThat(dependencies.size(), Is.is(1));
      
      Dependency dependency = dependencies.get(0);
      assertThat(dependency.getGroupId(), IsEqual.equalTo("foo"));
      assertThat(dependency.getArtifactId(), IsEqual.equalTo("bar"));
      assertThat(dependency.getVersion(), IsEqual.equalTo("2"));
   }

   private Plugin createPlugin(String groupId, String artifatcId, String version)
   {
      final Plugin plugin = new Plugin();
      plugin.setGroupId(groupId);
      plugin.setArtifactId(artifatcId);
      plugin.setVersion(version);
      return plugin;
   }
}
