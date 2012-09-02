/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;
import org.sourcepit.b2.model.builder.util.FeaturesConverter;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.Identifiable;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.guplex.test.GuplexTest;

// TODO UC #1: Module with pure plugin
// TODO UC #2: Module with plugins + tests
// TODO UC #3: Erase facet classifier in feature id
// TODO UC #4: Module with different assemblies, e.g. main, sdk, test
// TODO UC #5: Erase assembly classifier in feature id
// TODO UC #6: Module that requires other Module
// TODO UC #7. Module that includes other Module (aggregator)
public class FeaturesInterpolatorTest extends GuplexTest
{
   @Override
   protected boolean isUseIndex()
   {
      return true;
   }

   @Test
   public void testUC_1_SinglePlugin_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(1, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());
   }

   @Test
   public void testUC_1_SinglePlugin_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(3, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.main.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Test
   public void testUC_2_PluginsAndTests_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      plugin = createPluginProject("plugin.foo.tests", "1.0.0.qualifier");
      pluginsFacet = createPluginsFacet("tests");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(2, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());
   }

   @Test
   public void testUC_2_PluginsAndTests_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      plugin = createPluginProject("plugin.foo.tests", "1.0.0.qualifier");
      pluginsFacet = createPluginsFacet("tests");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(6, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.main.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(5);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());
   }

   @Test
   public void testUC_3_EraseFacetClassifier_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      plugin = createPluginProject("plugin.foo.tests", "1.0.0.qualifier");
      pluginsFacet = createPluginsFacet("tests");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.facets[\"plugins\"].classifier", "");

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(2, featuresFacet.getProjects().size());

      FeatureProject featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());
   }

   @Test
   public void testUC_3_EraseFacetClassifier_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      plugin = createPluginProject("plugin.foo.tests", "1.0.0.qualifier");
      pluginsFacet = createPluginsFacet("tests");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.facets[\"plugins\"].classifier", "");

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(6, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.main.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(5);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Test
   public void testUC_4_CustomAssemblies_PublicSdkTest_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      plugin = createPluginProject("plugin.foo.tests", "1.0.0.qualifier");
      pluginsFacet = createPluginsFacet("tests");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**"); // should be
                                                                                                       // default?
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(2, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertEquals("public, sdk", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());
   }

   @Test
   public void testUC_4_CustomAssemblies_PublicSdkTest_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      plugin = createPluginProject("plugin.foo.tests", "1.0.0.qualifier");
      pluginsFacet = createPluginsFacet("tests");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "public, sdk, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"public\"].featuresFilter", "!**.sources.**,!**.tests.**"); // should be
                                                                                                       // default?
      moduleProperties.put("b2.assemblies[\"sdk\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(6, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertEquals("public", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.sdk.feature", "1.0.0.qualifier", featureProject);
      assertEquals("sdk", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertEquals("public", featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(5);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Test
   public void testUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_NoSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo.doc", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("doc");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      plugin = createPluginProject("plugin.foo.tests", "1.0.0.qualifier");
      pluginsFacet = createPluginsFacet("tests");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.assemblies[\"main\"].classifier", "");

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(4, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.doc.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Test
   public void testUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_WithSource() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo.doc", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("doc");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      plugin = createPluginProject("plugin.foo.tests", "1.0.0.qualifier");
      pluginsFacet = createPluginsFacet("tests");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "false"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies[\"main\"].featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies[\"test\"].featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.assemblies[\"main\"].classifier", "");

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertTrue(featuresFacet.isDerived());
      assertEquals(8, featuresFacet.getProjects().size());

      FeatureProject featureProject;
      featureProject = featuresFacet.getProjects().get(0);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.doc.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(1);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.doc.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(2);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(3);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.plugins.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(4);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(5);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.tests.sources.feature", "1.0.0.qualifier", featureProject);
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(0, featureProject.getIncludedFeatures().size());
      assertEquals(1, featureProject.getIncludedPlugins().size());

      featureProject = featuresFacet.getProjects().get(6);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.feature", "1.0.0.qualifier", featureProject);
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(4, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      FeatureInclude featureInclude;
      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.doc.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("doc", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(2);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(3);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("plugins", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureProject = featuresFacet.getProjects().get(7);
      assertTrue(featureProject.isDerived());
      assertIdentifiable("foo.test.feature", "1.0.0.qualifier", featureProject);
      assertEquals("test", featureProject.getAnnotationEntry("b2", "assemblyNames"));
      assertNull(featureProject.getAnnotationEntry("b2", "facetName"));
      assertEquals(2, featureProject.getIncludedFeatures().size());
      assertEquals(0, featureProject.getIncludedPlugins().size());

      featureInclude = featureProject.getIncludedFeatures().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));

      featureInclude = featureProject.getIncludedFeatures().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", featureInclude);
      assertNull(featureInclude.getAnnotationEntry("b2", "assemblyNames"));
      assertEquals("tests", featureInclude.getAnnotationEntry("b2", "facetName"));
   }

   @Test
   public void testSourceBuild() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      // moduleProperties.put("build.sources", "true"); // true is default
      moduleProperties.put("b2.assemblies", "main");

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertEquals(3, featuresFacet.getProjects().size());

      FeatureProject featureProject;

      featureProject = featuresFacet.getProjects().get(0);
      assertEquals("foo.plugins.feature", featureProject.getId());
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));

      featureProject = featuresFacet.getProjects().get(1);
      assertEquals("foo.plugins.sources.feature", featureProject.getId());
      assertNull(featureProject.getAnnotationEntry("b2", "assemblyNames"));

      featureProject = featuresFacet.getProjects().get(2);
      assertEquals("foo.main.feature", featureProject.getId());
      assertEquals("main", featureProject.getAnnotationEntry("b2", "assemblyNames"));
   }

   @Test
   public void testReplaceAssemblyFeatureWithSingleFacetFeature() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("plugins");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "false");
      moduleProperties.put("b2.assemblies", "main, bla");

      // interpolate
      interpolate(module, moduleProperties);

      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertEquals(1, featuresFacet.getProjects().size());

      FeatureProject featureProject = featuresFacet.getProjects().get(0);
      assertEquals("foo.plugins.feature", featureProject.getId());

      String assemblyNames = featureProject.getAnnotationEntry("b2", "assemblyNames");
      assertEquals("main, bla", assemblyNames);
   }

   @Test
   public void testOverall()
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject plugin = createPluginProject("plugin.foo", "1.0.0.qualifier");
      PluginsFacet pluginsFacet = createPluginsFacet("main");
      pluginsFacet.getProjects().add(plugin);
      module.getFacets().add(pluginsFacet);

      pluginsFacet = createPluginsFacet("tests");
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.facets[\"tests\"].requiredPlugins", "org.junit:4.10");


      // interpolate
      interpolate(module, moduleProperties);


      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(1, featuresFacets.size());

      FeaturesFacet featuresFacet = featuresFacets.get(0);
      assertEquals("features", featuresFacet.getName());
      assertEquals(4, featuresFacet.getProjects().size());

      FeatureProject mainFeature = featuresFacet.getProjects().get(0);
      assertEquals("foo.main.feature", mainFeature.getId());
      assertEquals(new File(".b2/features/foo.main.feature"), mainFeature.getDirectory());
      assertEquals(0, mainFeature.getIncludedFeatures().size());
      assertEquals(1, mainFeature.getIncludedPlugins().size());
      assertEquals(0, mainFeature.getRequiredFeatures().size());
      assertEquals(0, mainFeature.getRequiredPlugins().size());

      PluginInclude mainPluginInclude = mainFeature.getIncludedPlugins().get(0);
      assertEquals("plugin.foo", mainPluginInclude.getId());
      assertEquals("1.0.0.qualifier", mainPluginInclude.getVersion());

      FeatureProject srcMainFeature = featuresFacet.getProjects().get(1);
      assertEquals("foo.main.sources.feature", srcMainFeature.getId());
      assertEquals(new File(".b2/features/foo.main.sources.feature"), srcMainFeature.getDirectory());
      assertEquals(0, srcMainFeature.getIncludedFeatures().size());
      assertEquals(1, srcMainFeature.getIncludedPlugins().size());

      PluginInclude srcMainPluginInclude = srcMainFeature.getIncludedPlugins().get(0);
      assertEquals("plugin.foo.source", srcMainPluginInclude.getId());
      assertEquals("1.0.0.qualifier", srcMainPluginInclude.getVersion());

      FeatureProject testFeature = featuresFacet.getProjects().get(2);
      assertEquals("foo.tests.feature", testFeature.getId());
      assertEquals(new File(".b2/features/foo.tests.feature"), testFeature.getDirectory());
      assertEquals(0, testFeature.getIncludedFeatures().size());
      assertEquals(0, testFeature.getIncludedPlugins().size());
      assertEquals(0, testFeature.getRequiredFeatures().size());
      assertEquals(1, testFeature.getRequiredPlugins().size());
   }

   @Test
   public void testIncludedFeatures() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginsFacet pluginsFacet = createPluginsFacet("main");
      module.getFacets().add(pluginsFacet);

      pluginsFacet = createPluginsFacet("tests");
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.facets[\"main\"].includedSourceFeatures", "org.eclipse.platform:3.8.0");
      moduleProperties.put("b2.facets[\"tests\"].includedFeatures", "org.junit:4.10:optional");


      // interpolate
      interpolate(module, moduleProperties);

      FeatureProject mainFeature = getFeatureProject(module, "foo.main.feature");
      assertNotNull(mainFeature);
      assertEquals(0, mainFeature.getIncludedFeatures().size());
      assertEquals(0, mainFeature.getIncludedPlugins().size());
      assertEquals(0, mainFeature.getRequiredFeatures().size());
      assertEquals(0, mainFeature.getRequiredPlugins().size());

      FeatureProject srcMainFeature = getFeatureProject(module, "foo.main.sources.feature");
      assertNotNull(srcMainFeature);
      assertEquals(1, srcMainFeature.getIncludedFeatures().size());
      assertEquals(0, srcMainFeature.getIncludedPlugins().size());
      assertEquals(0, srcMainFeature.getRequiredFeatures().size());
      assertEquals(0, srcMainFeature.getRequiredPlugins().size());

      FeatureInclude featureInclude = srcMainFeature.getIncludedFeatures().get(0);
      assertEquals("org.eclipse.platform", featureInclude.getId());
      assertEquals("3.8.0", featureInclude.getVersion());
      assertFalse(featureInclude.isOptional());

      FeatureProject testFeature = getFeatureProject(module, "foo.tests.feature");
      assertNotNull(testFeature);
      assertEquals(1, testFeature.getIncludedFeatures().size());
      assertEquals(0, testFeature.getIncludedPlugins().size());
      assertEquals(0, testFeature.getRequiredFeatures().size());
      assertEquals(0, testFeature.getRequiredPlugins().size());

      featureInclude = testFeature.getIncludedFeatures().get(0);
      assertEquals("org.junit", featureInclude.getId());
      assertEquals("4.10", featureInclude.getVersion());
      assertTrue(featureInclude.isOptional());
   }

   @Test
   public void testIncludedPlugins() throws Exception
   {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginsFacet pluginsFacet = createPluginsFacet("main");
      module.getFacets().add(pluginsFacet);

      pluginsFacet = createPluginsFacet("tests");
      module.getFacets().add(pluginsFacet);

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.facets[\"main\"].includedSourcePlugins", "org.eclipse.platform:3.8.0");
      moduleProperties.put("b2.facets[\"tests\"].includedPlugins", "org.junit:4.10:unpack");


      // interpolate
      interpolate(module, moduleProperties);

      FeatureProject mainFeature = getFeatureProject(module, "foo.main.feature");
      assertNotNull(mainFeature);
      assertEquals(0, mainFeature.getIncludedFeatures().size());
      assertEquals(0, mainFeature.getIncludedPlugins().size());
      assertEquals(0, mainFeature.getRequiredFeatures().size());
      assertEquals(0, mainFeature.getRequiredPlugins().size());

      FeatureProject srcMainFeature = getFeatureProject(module, "foo.main.sources.feature");
      assertNotNull(srcMainFeature);
      assertEquals(0, srcMainFeature.getIncludedFeatures().size());
      assertEquals(1, srcMainFeature.getIncludedPlugins().size());
      assertEquals(0, srcMainFeature.getRequiredFeatures().size());
      assertEquals(0, srcMainFeature.getRequiredPlugins().size());

      PluginInclude featureInclude = srcMainFeature.getIncludedPlugins().get(0);
      assertEquals("org.eclipse.platform", featureInclude.getId());
      assertEquals("3.8.0", featureInclude.getVersion());
      assertFalse(featureInclude.isUnpack());

      FeatureProject testFeature = getFeatureProject(module, "foo.tests.feature");
      assertNotNull(testFeature);
      assertEquals(0, testFeature.getIncludedFeatures().size());
      assertEquals(1, testFeature.getIncludedPlugins().size());
      assertEquals(0, testFeature.getRequiredFeatures().size());
      assertEquals(0, testFeature.getRequiredPlugins().size());

      featureInclude = testFeature.getIncludedPlugins().get(0);
      assertEquals("org.junit", featureInclude.getId());
      assertEquals("4.10", featureInclude.getVersion());
      assertTrue(featureInclude.isUnpack());
   }

   private void interpolate(BasicModule module, PropertiesMap moduleProperties)
   {
      ISourceService sourceService = gLookup(ISourceService.class);
      LayoutManager layoutManager = gLookup(LayoutManager.class);
      UnpackStrategy unpackStrategy = mock(UnpackStrategy.class);
      FeaturesConverter converter = gLookup(FeaturesConverter.class);

      FeaturesInterpolator interpolator;
      interpolator = new FeaturesInterpolator(sourceService, layoutManager, unpackStrategy, converter);

      interpolator.interpolate(module, moduleProperties);
   }

   public static FeatureProject getFeatureProject(BasicModule module, String id)
   {
      StrictReference ref = ModuleModelFactory.eINSTANCE.createStrictReference();
      ref.setId(id);
      return module.resolveReference(ref, FeaturesFacet.class);
   }

   public static BasicModule createBasicModule(String id)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final BasicModule module = eFactory.createBasicModule();
      module.setId(id);
      module.setVersion("1.0.0.qualifier");
      module.setLayoutId("structured");
      return module;
   }

   public static PluginProject createPluginProject(String id, String version)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final PluginProject plugin = eFactory.createPluginProject();
      plugin.setId(id);
      plugin.setVersion(version);
      return plugin;
   }

   public static PluginsFacet createPluginsFacet(String name)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final PluginsFacet pluginsFacet = eFactory.createPluginsFacet();
      pluginsFacet.setName(name);
      return pluginsFacet;
   }

   public static void assertReference(String expectedId, String expectedVersion, AbstractReference reference)
   {
      assertEquals(expectedId, reference.getId());
      assertEquals(expectedVersion, reference.getVersion());
   }

   public static void assertIdentifiable(String expectedId, String expectedVersion, Identifiable identifiable)
   {
      assertEquals(expectedId, identifiable.getId());
      assertEquals(expectedVersion, identifiable.getVersion());
   }

}
