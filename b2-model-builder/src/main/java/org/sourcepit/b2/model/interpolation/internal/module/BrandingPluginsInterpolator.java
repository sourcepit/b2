/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.sourcepit.b2.model.interpolation.internal.module.B2ModelUtils.toPluginInclude;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.builder.util.FeaturesConverter;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class BrandingPluginsInterpolator {
   private final LayoutManager layoutManager;

   private final FeaturesConverter converter;

   @Inject
   public BrandingPluginsInterpolator(LayoutManager layoutManager, FeaturesConverter converter) {
      this.layoutManager = layoutManager;
      this.converter = converter;
   }

   public void interpolate(AbstractModule module, FeaturesFacet featuresFacet, PropertiesSource moduleProperties) {
      if (!converter.isSkipBrandingPlugins(moduleProperties)) {
         final PluginsFacet pluginsFacet = ModuleModelFactory.eINSTANCE.createPluginsFacet();
         pluginsFacet.setDerived(true);
         pluginsFacet.setName(featuresFacet.getName() + "-branding-plugins");

         for (FeatureProject featureProject : featuresFacet.getProjects()) {
            final PluginProject pluginProject = createPluginProject(module, featureProject, moduleProperties);
            pluginProject.setDirectory(deriveProjectDir(module, pluginsFacet, pluginProject));

            pluginsFacet.getProjects().add(pluginProject);

            B2MetadataUtils.setBrandedFeature(pluginProject, featureProject.getId());
            B2MetadataUtils.setBrandingPlugin(featureProject, pluginProject.getId());

            final PluginInclude pluginInclude = toPluginInclude(module, pluginProject);
            pluginInclude.setUnpack(false);

            featureProject.getIncludedPlugins().add(0, pluginInclude);
         }

         if (!pluginsFacet.getProjects().isEmpty()) {
            module.getFacets().add(pluginsFacet);
         }
      }
   }

   private File deriveProjectDir(AbstractModule module, final PluginsFacet pluginsFacet,
      final PluginProject pluginProject) {
      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      return new File(layout.pathOfFacetMetaData(module, pluginsFacet.getName(), pluginProject.getId()));
   }

   private PluginProject createPluginProject(AbstractModule module, FeatureProject featureProject,
      PropertiesSource moduleProperties) {
      final String pluginId = deriveBrandingPluginId(module, featureProject, moduleProperties);

      final PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setDerived(true);
      pluginProject.setId(pluginId);
      pluginProject.setVersion(featureProject.getVersion());

      return pluginProject;
   }

   private String deriveBrandingPluginId(AbstractModule module, FeatureProject featureProject,
      PropertiesSource moduleProperties) {
      final String moduleId = module.getId();
      if (B2MetadataUtils.isAssemblyFeature(featureProject)) {
         return converter.getBrandingPluginIdForAssembly(moduleProperties, getAssemblyName(featureProject), moduleId);
      }
      else {
         final String facetName = B2MetadataUtils.getFacetName(featureProject);
         final boolean isSource = B2MetadataUtils.isFacetSourceFeature(featureProject);
         return converter.getBrandingPluginIdForFacet(moduleProperties, facetName, moduleId, isSource);
      }
   }

   private static String getAssemblyName(FeatureProject featureProject) {
      List<String> assemblyNames = B2MetadataUtils.getAssemblyNames(featureProject);
      if (assemblyNames.size() != 1) {
         throw new IllegalStateException();
      }
      return assemblyNames.get(0);
   }
}
