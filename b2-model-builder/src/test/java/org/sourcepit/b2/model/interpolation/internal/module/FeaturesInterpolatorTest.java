/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;
import org.sourcepit.b2.model.builder.util.Converter2;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.guplex.test.GuplexTest;

public class FeaturesInterpolatorTest extends GuplexTest
{
   @Override
   protected boolean isUseIndex()
   {
      return true;
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
      assertEquals("facet-features", featuresFacet.getName());
      assertEquals(4, featuresFacet.getProjects().size());

      FeatureProject mainFeature = featuresFacet.getProjects().get(0);
      assertEquals("foo.main.feature", mainFeature.getId());
      assertEquals(new File(".b2/facet-features/foo.main.feature"), mainFeature.getDirectory());
      assertEquals(0, mainFeature.getIncludedFeatures().size());
      assertEquals(1, mainFeature.getIncludedPlugins().size());
      assertEquals(0, mainFeature.getRequiredFeatures().size());
      assertEquals(0, mainFeature.getRequiredPlugins().size());

      PluginInclude mainPluginInclude = mainFeature.getIncludedPlugins().get(0);
      assertEquals("plugin.foo", mainPluginInclude.getId());
      assertEquals("1.0.0.qualifier", mainPluginInclude.getVersion());

      FeatureProject srcMainFeature = featuresFacet.getProjects().get(1);
      assertEquals("foo.main.sources.feature", srcMainFeature.getId());
      assertEquals(new File(".b2/facet-features/foo.main.sources.feature"), srcMainFeature.getDirectory());
      assertEquals(0, srcMainFeature.getIncludedFeatures().size());
      assertEquals(1, srcMainFeature.getIncludedPlugins().size());

      PluginInclude srcMainPluginInclude = srcMainFeature.getIncludedPlugins().get(0);
      assertEquals("plugin.foo.source", srcMainPluginInclude.getId());
      assertEquals("1.0.0.qualifier", srcMainPluginInclude.getVersion());

      FeatureProject testFeature = featuresFacet.getProjects().get(2);
      assertEquals("foo.tests.feature", testFeature.getId());
      assertEquals(new File(".b2/facet-features/foo.tests.feature"), testFeature.getDirectory());
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
      Converter2 converter = gLookup(Converter2.class);
      
      FeaturesInterpolator interpolator;
      interpolator = new FeaturesInterpolator(sourceService, layoutManager, unpackStrategy, converter);

      interpolator.interpolate(module, moduleProperties);
   }

   private static FeatureProject getFeatureProject(BasicModule module, String id)
   {
      StrictReference ref = ModuleModelFactory.eINSTANCE.createStrictReference();
      ref.setId(id);
      return module.resolveReference(ref, FeaturesFacet.class);
   }

   private BasicModule createBasicModule(String id)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final BasicModule module = eFactory.createBasicModule();
      module.setId(id);
      module.setLayoutId("structured");
      return module;
   }

   private static PluginProject createPluginProject(String id, String version)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final PluginProject plugin = eFactory.createPluginProject();
      plugin.setId(id);
      plugin.setVersion(version);
      return plugin;
   }

   private static PluginsFacet createPluginsFacet(String name)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final PluginsFacet pluginsFacet = eFactory.createPluginsFacet();
      pluginsFacet.setName(name);
      return pluginsFacet;
   }

}
