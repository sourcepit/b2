/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;

public class CustomProjectIdsIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void test() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));
      
      AbstractModule module = loadModule(moduleDir);
      
      SitesFacet sites = module.getFacetByName("sites");
      
      SiteProject assemblySite = sites.getProjectById("org.sourcepit.assembly.site");
      assertNotNull(assemblySite);
      
      FeaturesFacet features = module.getFacetByName("features");
      
      FeatureProject assemblyFeature = features.getProjectById("org.sourcepit.assembly");
      assertNotNull(assemblyFeature);
      
      FeatureProject pluginsFeature = features.getProjectById("org.sourcepit.plugins");
      assertNotNull(pluginsFeature);
      
      FeatureProject testsFeature = features.getProjectById("org.sourcepit.tests");
      assertNotNull(testsFeature);
      
      PluginsFacet brandings = module.getFacetByName("features-branding-plugins");
      
      PluginProject assemblyBranding = brandings.getProjectById("org.sourcepit.assembly.branding");
      assertNotNull(assemblyBranding);
      
      PluginProject pluginsBranding = brandings.getProjectById("org.sourcepit.plugins.branding");
      assertNotNull(pluginsBranding);
      
      PluginProject testsBranding = brandings.getProjectById("org.sourcepit.tests.branding");
      assertNotNull(testsBranding);
   }
}
