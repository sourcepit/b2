/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness.initModuleDir;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_GROUP_ID;
import static org.sourcepit.b2.internal.generator.TychoConstants.TYCHO_TPC_PLUGIN_ARTIFACT_ID;
import static org.sourcepit.b2.internal.maven.util.TychoXpp3Utils.readRequirements;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addFeatureProject;
import static org.sourcepit.b2.model.harness.ModelTestHarness.createBasicModule;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getConfiguration;
import static org.sourcepit.common.maven.model.util.MavenModelUtils.getPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.inheritance.DefaultInheritanceAssembler;
import org.apache.maven.model.io.DefaultModelReader;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.b2.model.builder.util.DefaultConverter;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;

public class TargetPlatformConfigurationGeneratorTest
{
   private final Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = newWorkspace();

   private BasicModule module;

   private TargetPlatformConfigurationGenerator generator;

   private TestResolutionContextResolver resolutionContext;

   private File pomFile;

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

   @Before
   public void setUp() throws Exception
   {
      final File moduleDir = ws.getRoot();
      final String testName = moduleDir.getName();
      String artifactId = testName;

      module = initModuleDir(moduleDir, "org.sourcepit", artifactId, "1.0.0-SNAPSHOT");

      File moduleFile = new File(moduleDir, "module.xml");
      assertTrue(moduleFile.exists());

      pomFile = new File(moduleDir, "pom.xml");
      FileUtils.copyFile(moduleFile, pomFile);
      assertTrue(pomFile.exists());

      module.setAnnotationData("maven", "pomFile", pomFile.getAbsolutePath());

      resolutionContext = new TestResolutionContextResolver();

      generator = new TargetPlatformConfigurationGenerator(new TargetPlatformAppender(
         new TargetPlatformInhertianceAssembler(new DefaultInheritanceAssembler()),
         new TargetPlatformRequirementsCollector(resolutionContext), new TargetPlatformRequirementsAppender()),
         new DefaultConverter());
   }

   @Test
   public void testEmptyRequirement() throws Exception
   {
      generator.generate(module, new LinkedPropertiesMap(), null);

      Model model = new DefaultModelReader().read(pomFile, null);
      Plugin plugin = getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      assertNull(plugin);
   }

   @Test
   public void testModuleRequirement() throws Exception
   {
      BasicModule module2 = createBasicModule("module-2");
      resolutionContext.getRequiredFeatures().add(addFeatureProject(module2, "features", "feature2", "1.0.0"));

      generator.generate(module, new LinkedPropertiesMap(), null);

      Model model = new DefaultModelReader().read(pomFile, null);
      Plugin plugin = getPlugin(model, TYCHO_GROUP_ID, TYCHO_TPC_PLUGIN_ARTIFACT_ID, false);
      assertNotNull(plugin);

      Xpp3Dom configurationNode = getConfiguration(plugin, false);

      Xpp3Dom requirementsNode = configurationNode.getChild("dependency-resolution").getChild("extraRequirements");

      List<Dependency> actualRequirements = readRequirements(requirementsNode);
      assertEquals(1, actualRequirements.size());
   }
}
