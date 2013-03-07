/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.harness;

import static org.junit.Assert.assertEquals;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.Identifiable;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;

public final class ModelTestHarness
{
   private ModelTestHarness()
   {
      super();
   }

   public static PluginProject addPluginProject(BasicModule module, String facetName, String pluginId,
      String pluginVersion)
   {
      PluginsFacet pluginsFacet = module.getFacetByName(facetName);
      if (pluginsFacet == null)
      {
         pluginsFacet = createPluginsFacet(facetName);
         module.getFacets().add(pluginsFacet);
      }

      PluginProject plugin = createPluginProject(pluginId, pluginVersion);
      pluginsFacet.getProjects().add(plugin);
      return plugin;
   }

   public static FeatureProject addFeatureProject(BasicModule module, String facetName, String featureId,
      String featureVersion)
   {
      FeaturesFacet featuresFacet = module.getFacetByName(facetName);
      if (featuresFacet == null)
      {
         featuresFacet = createFeaturesFacet(facetName);
         module.getFacets().add(featuresFacet);
      }

      FeatureProject feature = createFeatureProject(featureId, featureVersion);
      featuresFacet.getProjects().add(feature);
      return feature;
   }

   public static SiteProject addSiteProject(BasicModule module, String facetName, String siteId)
   {
      SitesFacet sitesFacet = module.getFacetByName(facetName);
      if (sitesFacet == null)
      {
         sitesFacet = createSitesFacet(facetName);
         module.getFacets().add(sitesFacet);
      }

      SiteProject site = createSiteProject(siteId, module.getVersion());
      sitesFacet.getProjects().add(site);
      return site;
   }

   public static FeatureProject getFeatureProject(AbstractModule module, String id, String version)
   {
      StrictReference ref = ModuleModelFactory.eINSTANCE.createStrictReference();
      ref.setId(id);
      ref.setVersion(version);
      return module.resolveReference(ref, FeaturesFacet.class);
   }

   public static FeatureProject getFeatureProject(AbstractModule module, String id)
   {
      return getFeatureProject(module, id, null);
   }

   public static CompositeModule createCompositeModule(String id)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final CompositeModule module = eFactory.createCompositeModule();
      module.setId(id);
      module.setVersion("1.0.0.qualifier");
      module.setLayoutId("composite");
      return module;
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
      plugin.setTestPlugin(id.endsWith(".tests"));

      final BundleManifest manifest = BundleManifestFactory.eINSTANCE.createBundleManifest();

      manifest.setBundleSymbolicName(id);
      manifest.setBundleVersion(version);

      plugin.setBundleManifest(manifest);

      // fake source
      plugin.setAnnotationData("java", "source.paths", "src");

      return plugin;
   }

   public static FeatureProject createFeatureProject(String id, String version)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final FeatureProject feature = eFactory.createFeatureProject();
      feature.setId(id);
      feature.setVersion(version);
      return feature;
   }

   public static SiteProject createSiteProject(String id, String version)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final SiteProject site = eFactory.createSiteProject();
      site.setId(id);
      site.setVersion(version);
      return site;
   }

   public static PluginsFacet createPluginsFacet(String name)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final PluginsFacet pluginsFacet = eFactory.createPluginsFacet();
      pluginsFacet.setName(name);
      return pluginsFacet;
   }

   public static FeaturesFacet createFeaturesFacet(String name)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final FeaturesFacet featuresFacet = eFactory.createFeaturesFacet();
      featuresFacet.setName(name);
      return featuresFacet;
   }

   public static SitesFacet createSitesFacet(String name)
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final SitesFacet sitesFacet = eFactory.createSitesFacet();
      sitesFacet.setName(name);
      return sitesFacet;
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

   public static PluginInclude addPluginInclude(FeatureProject featureProject, PluginProject pluginProject)
   {
      final PluginInclude pluginInclude = createPluginInclude(pluginProject.getId(), pluginProject.getVersion(), false);
      featureProject.getIncludedPlugins().add(pluginInclude);
      return pluginInclude;
   }

   private static PluginInclude createPluginInclude(String id, String version, boolean unpack)
   {
      final PluginInclude pluginInclude = ModuleModelFactory.eINSTANCE.createPluginInclude();
      pluginInclude.setId(id);
      pluginInclude.setVersion(version);
      pluginInclude.setUnpack(unpack);
      return pluginInclude;
   }

   public static RuledReference addPluginRequirement(FeatureProject featureProject, String id, String version)
   {
      final RuledReference requiredPlugin = createRuledReference(id, version);
      featureProject.getRequiredPlugins().add(requiredPlugin);
      return requiredPlugin;
   }

   public static RuledReference addFeatureRequirement(FeatureProject featureProject, String id, String version)
   {
      final RuledReference requiredFeature = createRuledReference(id, version);
      featureProject.getRequiredFeatures().add(requiredFeature);
      return requiredFeature;
   }

   private static RuledReference createRuledReference(String id, String version)
   {
      final RuledReference ruledReference = ModuleModelFactory.eINSTANCE.createRuledReference();
      ruledReference.setId(id);
      ruledReference.setVersion(version);
      return ruledReference;
   }

   public static PluginInclude addPluginInclude(FeatureProject featureProject, String id, String version)
   {
      final PluginInclude pluginInclude = createPluginInclude(id, version, false);
      featureProject.getIncludedPlugins().add(pluginInclude);
      return pluginInclude;
   }

   public static FeatureInclude addFeatureInclude(FeatureProject featureProject, String id, String version)
   {
      final FeatureInclude featureInclude = createFeatureInclude(id, version);
      featureProject.getIncludedFeatures().add(featureInclude);
      return featureInclude;
   }

   private static FeatureInclude createFeatureInclude(String id, String version)
   {
      final FeatureInclude featureInclude = ModuleModelFactory.eINSTANCE.createFeatureInclude();
      featureInclude.setId(id);
      featureInclude.setVersion(version);
      return featureInclude;
   }

}
