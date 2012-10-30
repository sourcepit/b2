/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.initModuleDir;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.b2.model.interpolation.internal.module.EmptyResolutionContextResolver;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.common.maven.model.util.MavenModelUtils;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;

public class TargetPlatformConfigurationGeneratorTest
{
   private final Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = newWorkspace();

   protected Workspace newWorkspace()
   {
      return new Workspace(new File(env.getBuildDir(), "ws"), false);
   }

   public Environment getEnvironment()
   {
      return env;
   }

   protected File getResource(String path) throws IOException
   {
      File resources = getResourcesDir();
      File resource = new File(resources, path).getCanonicalFile();
      return ws.importFileOrDir(resource);
   }

   protected File getResourcesDir()
   {
      return getEnvironment().getResourcesDir();
   }

   @Test
   public void testEmptyModule() throws Exception
   {
      final File moduleDir = ws.getRoot();
      final String testName = moduleDir.getName();
      String artifactId = testName;
      
      final BasicModule module = initModuleDir(moduleDir, "org.sourcepit", artifactId, "1.0.0-SNAPSHOT");

      TargetPlatformConfigurationGenerator generator = new TargetPlatformConfigurationGenerator(
         new EmptyResolutionContextResolver());

      generator.generate(module, new LinkedPropertiesMap(), null);

      assertFalse(new File(moduleDir, "pom.xml").exists());
   }

   @Test
   public void testAdoptTargetConfigurationPlugin() throws Exception
   {
      final List<Model> hierarchy = new ArrayList<Model>();
      hierarchy.add(new Model());

      final Model target = hierarchy.get(0);

      Plugin plugin = TargetPlatformConfigurationGenerator.adoptTargetConfigurationPlugin(hierarchy, target);
      assertNotNull(plugin);

      assertEquals(1, target.getBuild().getPlugins().size());
      assertEquals(plugin, target.getBuild().getPlugins().get(0));

      assertEquals("org.eclipse.tycho", plugin.getGroupId());
      assertEquals("target-platform-configuration", plugin.getArtifactId());
      assertEquals("${tycho.version}", plugin.getVersion());
      assertNull(plugin.getConfiguration());
   }

   @Test
   public void testAdoptTargetConfigurationPlugin_UseExisting() throws Exception
   {
      final List<Model> hierarchy = new ArrayList<Model>();
      hierarchy.add(new Model());
      hierarchy.add(new Model());

      Plugin origin = MavenModelUtils.createPlugin("org.eclipse.tycho", "target-platform-configuration",
         "${tycho.version}");
      origin.setConfiguration(new Xpp3Dom("configuration"));
      hierarchy.get(0).setBuild(new Build());
      hierarchy.get(0).getBuild().getPlugins().add(origin);

      final Model target = hierarchy.get(0);

      Plugin plugin = TargetPlatformConfigurationGenerator.adoptTargetConfigurationPlugin(hierarchy, target);
      assertNotNull(plugin);

      assertEquals(1, target.getBuild().getPlugins().size());
      assertEquals(plugin, target.getBuild().getPlugins().get(0));

      assertEquals("org.eclipse.tycho", plugin.getGroupId());
      assertEquals("target-platform-configuration", plugin.getArtifactId());
      assertEquals("${tycho.version}", plugin.getVersion());
      assertNotNull(plugin.getConfiguration());
      assertNotNull(plugin.getConfiguration());
      assertSame(origin, plugin);
   }

   @Test
   public void testAdoptTargetConfigurationPlugin_ReuseFromParent() throws Exception
   {
      final List<Model> hierarchy = new ArrayList<Model>();
      hierarchy.add(new Model());
      hierarchy.add(new Model());

      Plugin origin = MavenModelUtils.createPlugin("org.eclipse.tycho", "target-platform-configuration",
         "${tycho.version}");
      origin.setConfiguration(new Xpp3Dom("configuration"));
      hierarchy.get(1).setBuild(new Build());
      hierarchy.get(1).getBuild().getPlugins().add(origin);

      final Model target = hierarchy.get(0);

      Plugin plugin = TargetPlatformConfigurationGenerator.adoptTargetConfigurationPlugin(hierarchy, target);
      assertNotNull(plugin);

      assertEquals(1, target.getBuild().getPlugins().size());
      assertEquals(plugin, target.getBuild().getPlugins().get(0));

      assertEquals("org.eclipse.tycho", plugin.getGroupId());
      assertEquals("target-platform-configuration", plugin.getArtifactId());
      assertEquals("${tycho.version}", plugin.getVersion());
      assertNotNull(plugin.getConfiguration());
      assertNotSame(origin, plugin);
   }
}
