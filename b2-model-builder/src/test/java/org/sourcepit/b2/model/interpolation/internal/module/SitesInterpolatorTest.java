/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.mockito.Mockito.mock;
import static org.sourcepit.b2.model.interpolation.internal.module.FeaturesInterpolatorTest.createBasicModule;
import static org.sourcepit.b2.model.interpolation.internal.module.FeaturesInterpolatorTest.createPluginProject;
import static org.sourcepit.b2.model.interpolation.internal.module.FeaturesInterpolatorTest.createPluginsFacet;

import java.io.File;

import org.junit.Test;
import org.sourcepit.b2.model.builder.util.FeaturesConverter;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.SitesConverter;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.utils.collections.MultiValueMap;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.guplex.test.GuplexTest;

public class SitesInterpolatorTest extends GuplexTest
{

   @Test
   public void testSinglePlugin() throws Exception
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
      moduleProperties.put("b2.assemblies", "main");

      // interpolate
      interpolate(module, moduleProperties);
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
   }

   private void interpolate(BasicModule module, PropertiesMap moduleProperties)
   {
      ISourceService sourceService = gLookup(ISourceService.class);
      LayoutManager layoutManager = gLookup(LayoutManager.class);
      UnpackStrategy unpackStrategy = mock(UnpackStrategy.class);
      FeaturesConverter converter = gLookup(FeaturesConverter.class);

      FeaturesInterpolator interpolator;
      interpolator = new FeaturesInterpolator(sourceService, layoutManager, converter,
         new AbstractIncludesAndRequirementsResolver(converter, unpackStrategy)
         {
            @Override
            protected void determineForeignResolutionContext(MultiValueMap<AbstractModule, String> moduleToAssemblies,
               AbstractModule module, boolean isTest)
            {
               // TODO Auto-generated method stub

            }
         });

      interpolator.interpolate(module, moduleProperties);

      SitesInterpolator sitesInterpolator;
      sitesInterpolator = new SitesInterpolator(gLookup(SitesConverter.class));

      sitesInterpolator.interpolate(module, moduleProperties);
   }
}
