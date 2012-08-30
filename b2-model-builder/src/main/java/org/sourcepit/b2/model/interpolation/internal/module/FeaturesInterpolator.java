/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.builder.util.Converter2;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class FeaturesInterpolator
{
   private final ISourceService sourceService;

   private final LayoutManager layoutManager;

   private final UnpackStrategy unpackStrategy;

   @Inject
   public FeaturesInterpolator(@NotNull ISourceService sourceService, @NotNull LayoutManager layoutManager,
      @NotNull UnpackStrategy unpackStrategy)
   {
      this.sourceService = sourceService;
      this.layoutManager = layoutManager;
      this.unpackStrategy = unpackStrategy;
   }

   public void interpolate(AbstractModule module, PropertiesSource moduleProperties, Converter2 converter)
   {
      final EList<PluginsFacet> pluginsFacets = module.getFacets(PluginsFacet.class);
      for (PluginsFacet pluginsFacet : pluginsFacets)
      {
         interpolate(module, pluginsFacet, moduleProperties, converter);
      }
   }

   private void interpolate(AbstractModule module, PluginsFacet pluginsFacet, PropertiesSource moduleProperties,
      Converter2 converter)
   {
      final FeaturesFacet featuresFacet = createFeaturesFacet(pluginsFacet);

      FeatureProject featureProject = createFeatureProject(module, pluginsFacet, moduleProperties, converter, false);
      featuresFacet.getProjects().add(featureProject);

      if (sourceService.isSourceBuildEnabled(moduleProperties))
      {
         featureProject = createFeatureProject(module, pluginsFacet, moduleProperties, converter, true);
         featuresFacet.getProjects().add(featureProject);
      }

      module.getFacets().add(featuresFacet);
   }

   private FeatureProject createFeatureProject(AbstractModule module, PluginsFacet pluginsFacet,
      PropertiesSource moduleProperties, Converter2 converter, boolean isSource)
   {
      FeatureProject featureProject = ModuleModelFactory.eINSTANCE.createFeatureProject();
      featureProject.setDerived(true);
      featureProject.setId(deriveFeatureId(module, pluginsFacet, moduleProperties, converter, isSource));

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      featureProject.setDirectory(new File(layout.pathOfFacetMetaData(module, "features", featureProject.getId())));

      for (PluginProject pluginProject : pluginsFacet.getProjects())
      {
         final PluginInclude pluginInclude = createPluginInclude(pluginProject, moduleProperties, converter, isSource);
         featureProject.getIncludedPlugins().add(pluginInclude);
      }

      final String facetName = pluginsFacet.getName();

      // TODO calc inter facet dependencies
      // TODO calc inter module dependencies
      final List<RuledReference> requiredFeatures = converter
         .getRequiredFeatures(moduleProperties, facetName, isSource);
      featureProject.getRequiredFeatures().addAll(requiredFeatures);

      final List<RuledReference> requiredPlugins = converter.getRequiredPlugins(moduleProperties, facetName, isSource);
      featureProject.getRequiredPlugins().addAll(requiredPlugins);

      return featureProject;
   }

   private PluginInclude createPluginInclude(PluginProject pluginProject, PropertiesSource moduleProperties,
      Converter2 converter, boolean isSource)
   {
      final String pluginId = isSource
         ? converter.getSourcePluginId(moduleProperties, pluginProject.getId())
         : pluginProject.getId();

      final PluginInclude pluginInclude = ModuleModelFactory.eINSTANCE.createPluginInclude();
      pluginInclude.setId(pluginId);
      pluginInclude.setVersion(pluginProject.getVersion());
      pluginInclude.setUnpack(unpackStrategy.isUnpack(pluginProject));

      return pluginInclude;
   }

   private String deriveFeatureId(AbstractModule module, PluginsFacet pluginsFacet, PropertiesSource properties,
      Converter2 converter, boolean isSource)
   {
      final String classifier = converter.getFacetClassifier(properties, pluginsFacet.getName());
      return converter.getFeatureId(properties, module.getId(), classifier, isSource);
   }

   private FeaturesFacet createFeaturesFacet(PluginsFacet pluginsFacet)
   {
      final FeaturesFacet featuresFacet = ModuleModelFactory.eINSTANCE.createFeaturesFacet();
      featuresFacet.setDerived(true);
      featuresFacet.setName(deriveFeaturesFacetName(pluginsFacet));
      return featuresFacet;
   }

   private String deriveFeaturesFacetName(PluginsFacet pluginsFacet)
   {
      return pluginsFacet.getName() + ".features";
   }
}
