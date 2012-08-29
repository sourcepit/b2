/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;
import org.sourcepit.b2.model.builder.util.Converter2;
import org.sourcepit.b2.model.builder.util.DefaultConverter2;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
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
   public void test()
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
      Converter2 converter = new DefaultConverter2();

      ISourceService sourceService = gLookup(ISourceService.class);
      LayoutManager layoutManager = gLookup(LayoutManager.class);
      UnpackStrategy unpackStrategy = mock(UnpackStrategy.class);
      FeaturesInterpolator interpolator = new FeaturesInterpolator(sourceService, layoutManager, unpackStrategy);

      // interpolate
      interpolator.interpolate(module, moduleProperties, converter);


      // assert
      EList<FeaturesFacet> featuresFacets = module.getFacets(FeaturesFacet.class);
      assertEquals(2, featuresFacets.size());

      FeaturesFacet mainFeatures = featuresFacets.get(0);
      assertEquals("main.features", mainFeatures.getName());

      assertEquals(2, mainFeatures.getProjects().size());

      FeatureProject mainFeature = mainFeatures.getProjects().get(0);
      assertEquals("foo.main.feature", mainFeature.getId());
      assertEquals(new File(".b2/features/foo.main.feature"), mainFeature.getDirectory());
      assertEquals(0, mainFeature.getIncludedFeatures().size());
      assertEquals(1, mainFeature.getIncludedPlugins().size());
      
      PluginInclude mainPluginInclude = mainFeature.getIncludedPlugins().get(0);
      assertEquals("plugin.foo", mainPluginInclude.getId());
      assertEquals("1.0.0.qualifier", mainPluginInclude.getVersionRange());
      
      FeatureProject srcMainFeature = mainFeatures.getProjects().get(1);
      assertEquals("foo.main.sources.feature", srcMainFeature.getId());
      assertEquals(new File(".b2/features/foo.main.sources.feature"), srcMainFeature.getDirectory());
      assertEquals(0, srcMainFeature.getIncludedFeatures().size());
      assertEquals(1, srcMainFeature.getIncludedPlugins().size());
      
      PluginInclude srcMainPluginInclude = srcMainFeature.getIncludedPlugins().get(0);
      assertEquals("plugin.foo.source", srcMainPluginInclude.getId());
      assertEquals("1.0.0.qualifier", srcMainPluginInclude.getVersionRange());

      FeaturesFacet testFeatures = featuresFacets.get(1);
      assertEquals("tests.features", testFeatures.getName());
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
