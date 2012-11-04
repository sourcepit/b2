/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addFeatureInclude;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addFeatureProject;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addFeatureRequirement;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addPluginInclude;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addPluginProject;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addPluginRequirement;
import static org.sourcepit.b2.model.harness.ModelTestHarness.createBasicModule;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.setFacetName;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.junit.Before;
import org.junit.Test;
import org.sourcepit.b2.model.harness.ModelTestHarness;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.PluginProject;


public class TargetPlatformRequirementsCollectorTest
{
   private TestResolutionContextResolver resolutionContext;
   private TargetPlatformRequirementsCollector collector;

   @Before
   public void setUp()
   {
      resolutionContext = new TestResolutionContextResolver();
      collector = new TargetPlatformRequirementsCollector(resolutionContext);
   }

   @Test
   public void testNullArgs()
   {
      try
      {
         collector.collectRequirements((AbstractModule) null, false);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      try
      {
         collector.collectRequirements((PluginProject) null);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }
   }

   @Test
   public void testCollectModuleRequirements()
   {
      BasicModule module = ModelTestHarness.createBasicModule("module-1");

      List<Dependency> requirements;
      requirements = collector.collectRequirements(module, false);
      assertTrue(requirements.isEmpty());
      requirements = collector.collectRequirements(module, true);
      assertTrue(requirements.isEmpty());

      BasicModule module2 = createBasicModule("module-2");
      resolutionContext.getRequiredFeatures().add(addFeatureProject(module2, "features", "feature2", "1.0.0"));

      BasicModule module3 = ModelTestHarness.createBasicModule("module-3");
      resolutionContext.getRequiredFeatures().add(addFeatureProject(module3, "features", "feature3", "1.0.0"));
      resolutionContext.getRequiredTestFeatures().add(addFeatureProject(module3, "features", "test.feature3", "1.0.0"));

      requirements = collector.collectRequirements(module, false);
      assertEquals(2, requirements.size());

      Dependency dependency;
      dependency = requirements.get(0);
      assertEquals(null, dependency.getGroupId());
      assertEquals("feature2", dependency.getArtifactId());
      assertEquals("1.0.0", dependency.getVersion());
      assertEquals("eclipse-feature", dependency.getType());

      dependency = requirements.get(1);
      assertEquals(null, dependency.getGroupId());
      assertEquals("feature3", dependency.getArtifactId());
      assertEquals("1.0.0", dependency.getVersion());
      assertEquals("eclipse-feature", dependency.getType());

      requirements = collector.collectRequirements(module, true);
      assertEquals(3, requirements.size());

      dependency = requirements.get(0);
      assertEquals(null, dependency.getGroupId());
      assertEquals("feature2", dependency.getArtifactId());
      assertEquals("1.0.0", dependency.getVersion());
      assertEquals("eclipse-feature", dependency.getType());

      dependency = requirements.get(1);
      assertEquals(null, dependency.getGroupId());
      assertEquals("feature3", dependency.getArtifactId());
      assertEquals("1.0.0", dependency.getVersion());
      assertEquals("eclipse-feature", dependency.getType());

      dependency = requirements.get(2);
      assertRequirement("test.feature3", "1.0.0", "eclipse-feature", dependency);
   }

   @Test
   public void testCollectPluginRequirements()
   {
      BasicModule module = createBasicModule("module-1");
      PluginProject pluginProject = addPluginProject(module, "plugins", "plugin1", "1.0.0");

      try
      {
         collector.collectRequirements(pluginProject);
      }
      catch (IllegalStateException e)
      { // expected
      }

      FeatureProject featureProject = addFeatureProject(module, "features", "feature1", "1.0.0");
      setFacetName(featureProject, "plugins");
      addPluginInclude(featureProject, pluginProject);

      List<Dependency> requirements;
      requirements = collector.collectRequirements(pluginProject);
      assertTrue(requirements.isEmpty());

      // test required features and plugins are included in requirements
      addPluginRequirement(featureProject, "org.eclipse.core.runtime", "3.4.0");
      requirements = collector.collectRequirements(pluginProject);
      assertEquals(1, requirements.size());

      Dependency dependency;
      dependency = requirements.get(0);
      assertRequirement("org.eclipse.core.runtime", "[3.4.0,4.0.0)", "eclipse-plugin", dependency);

      addFeatureRequirement(featureProject, "org.eclipse.platform", "4.2.0");
      requirements = collector.collectRequirements(pluginProject);
      assertEquals(2, requirements.size());

      dependency = requirements.get(0);
      assertRequirement("org.eclipse.platform", "[4.2.0,5.0.0)", "eclipse-feature", dependency);

      dependency = requirements.get(1);
      assertRequirement("org.eclipse.core.runtime", "[3.4.0,4.0.0)", "eclipse-plugin", dependency);

      // test included features and plugins are included in requirements
      addPluginInclude(featureProject, "org.eclipse.swt", "3.2.0");
      requirements = collector.collectRequirements(pluginProject);
      assertEquals(3, requirements.size());

      dependency = requirements.get(0);
      assertRequirement("org.eclipse.swt", "3.2.0", "eclipse-plugin", dependency);

      dependency = requirements.get(1);
      assertRequirement("org.eclipse.platform", "[4.2.0,5.0.0)", "eclipse-feature", dependency);

      dependency = requirements.get(2);
      assertRequirement("org.eclipse.core.runtime", "[3.4.0,4.0.0)", "eclipse-plugin", dependency);

      addFeatureInclude(featureProject, "org.eclipse.sdk", "3.6.0");
      requirements = collector.collectRequirements(pluginProject);
      assertEquals(4, requirements.size());

      dependency = requirements.get(0);
      assertRequirement("org.eclipse.sdk", "3.6.0", "eclipse-feature", dependency);

      dependency = requirements.get(1);
      assertRequirement("org.eclipse.swt", "3.2.0", "eclipse-plugin", dependency);

      dependency = requirements.get(2);
      assertRequirement("org.eclipse.platform", "[4.2.0,5.0.0)", "eclipse-feature", dependency);

      dependency = requirements.get(3);
      assertRequirement("org.eclipse.core.runtime", "[3.4.0,4.0.0)", "eclipse-plugin", dependency);

      // test dominate module requirements
      BasicModule module2 = createBasicModule("module-2");
      resolutionContext.getRequiredFeatures().add(
         addFeatureProject(module2, "features", "org.eclipse.platform", "3.0.0"));
      requirements = collector.collectRequirements(pluginProject);
      assertEquals(4, requirements.size());

      dependency = requirements.get(0);
      assertRequirement("org.eclipse.sdk", "3.6.0", "eclipse-feature", dependency);

      dependency = requirements.get(1);
      assertRequirement("org.eclipse.swt", "3.2.0", "eclipse-plugin", dependency);
      
      dependency = requirements.get(2);
      assertRequirement("org.eclipse.platform", "[4.2.0,5.0.0)", "eclipse-feature", dependency);

      dependency = requirements.get(3);
      assertRequirement("org.eclipse.core.runtime", "[3.4.0,4.0.0)", "eclipse-plugin", dependency);
   }

   static void assertRequirement(String id, String versionRange, String type, Dependency dependency)
   {
      assertEquals(null, dependency.getGroupId());
      assertEquals(id, dependency.getArtifactId());
      assertEquals(versionRange, dependency.getVersion());
      assertEquals(type, dependency.getType());
   }
}
