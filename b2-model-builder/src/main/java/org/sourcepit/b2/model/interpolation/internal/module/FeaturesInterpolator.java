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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.builder.util.FeaturesConverter;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.constraints.NotNull;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class FeaturesInterpolator {
   private final ISourceService sourceService;

   private final LayoutManager layoutManager;

   private final FeaturesConverter converter;

   private final IncludesAndRequirementsResolver includesAndRequirements;

   private final BrandingPluginsInterpolator brandingInterpolator;

   @Inject
   public FeaturesInterpolator(@NotNull ISourceService sourceService, @NotNull LayoutManager layoutManager,
      FeaturesConverter converter, IncludesAndRequirementsResolver includesAndRequirements,
      @NotNull BrandingPluginsInterpolator brandingInterpolator) {
      this.sourceService = sourceService;
      this.layoutManager = layoutManager;
      this.converter = converter;
      this.includesAndRequirements = includesAndRequirements;
      this.brandingInterpolator = brandingInterpolator;
   }

   public void interpolate(AbstractModule module, PropertiesSource moduleProperties) {
      final FeaturesFacet featuresFacet = createFeaturesFacet("features");

      final EList<PluginsFacet> pluginsFacets = module.getFacets(PluginsFacet.class);
      for (PluginsFacet pluginsFacet : pluginsFacets) {
         interpolatePluginFeatures(module, featuresFacet, pluginsFacet, moduleProperties);
      }

      final int size = featuresFacet.getProjects().size();
      if (size > 0) {
         module.getFacets().add(featuresFacet);

         for (PluginsFacet pluginsFacet : pluginsFacets) {
            final FeatureProject mainFeature = DefaultIncludesAndRequirementsResolver.findFeatureProjectForPluginsFacet(
               pluginsFacet, false);
            includesAndRequirements.appendIncludesAndRequirements(moduleProperties, pluginsFacet.getParent(),
               pluginsFacet, mainFeature);
            removeIfEmpty(featuresFacet, mainFeature);

            final FeatureProject sourceFeature = DefaultIncludesAndRequirementsResolver.findFeatureProjectForPluginsFacet(
               pluginsFacet, true);
            if (sourceFeature != null) {
               includesAndRequirements.appendIncludesAndRequirements(moduleProperties, pluginsFacet.getParent(),
                  pluginsFacet, sourceFeature);
               removeIfEmpty(featuresFacet, sourceFeature);
            }
         }
      }

      interpolateAssemblyFeatures(module, featuresFacet, moduleProperties);

      interpolateBrandingPlugins(module, featuresFacet, moduleProperties);

      if (size == 0 && !featuresFacet.getProjects().isEmpty()) {

         module.getFacets().add(featuresFacet);
      }
   }

   private void interpolateBrandingPlugins(AbstractModule module, FeaturesFacet featuresFacet,
      PropertiesSource moduleProperties) {
      if (!featuresFacet.getProjects().isEmpty()) {
         brandingInterpolator.interpolate(module, featuresFacet, moduleProperties);
      }
   }

   private static void removeIfEmpty(final FeaturesFacet featuresFacet, final FeatureProject featureProject) {
      if (isEmpty(featureProject)) {
         featuresFacet.getProjects().remove(featureProject);
      }
   }

   private static boolean isEmpty(FeatureProject sourceFeature) {
      if (!sourceFeature.getIncludedFeatures().isEmpty()) {
         return false;
      }
      if (!sourceFeature.getIncludedPlugins().isEmpty()) {
         return false;
      }
      return true;
   }

   private void interpolateAssemblyFeatures(AbstractModule module, FeaturesFacet featuresFacet,
      PropertiesSource moduleProperties) {
      final List<String> assemplyNames = converter.getAssemblyNames(moduleProperties);
      if (!assemplyNames.isEmpty()) {
         final List<FeatureProject> assemblyFeatures = new ArrayList<FeatureProject>();

         for (String assemblyName : assemplyNames) {
            final String featureId = deriveFeatureId(module, assemblyName, moduleProperties);
            final String facetName = featuresFacet.getName();

            final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
            final String path = layout.pathOfFacetMetaData(module, facetName, featureId);

            final FeatureProject featureProject = ModuleModelFactory.eINSTANCE.createFeatureProject();
            featureProject.setDerived(true);
            featureProject.setId(featureId);
            featureProject.setVersion(module.getVersion());
            featureProject.setDirectory(new File(path));

            B2MetadataUtils.setModuleId(featureProject, module.getId());
            B2MetadataUtils.setModuleVersion(featureProject, module.getVersion());
            B2MetadataUtils.addAssemblyName(featureProject, assemblyName);
            B2MetadataUtils.addAssemblyClassifier(featureProject,
               converter.getAssemblyClassifier(moduleProperties, assemblyName));
            B2MetadataUtils.addReplacedFeatureId(featureProject, featureProject.getId());

            includesAndRequirements.appendIncludesAndRequirements(moduleProperties, module, featureProject,
               assemblyName);

            // skip assembly feature if there is onle one single include
            final FeatureProject singleIncludedFeature = returnSingleIncludedFeatureProject(module, featureProject);
            if (singleIncludedFeature == null) {
               if (hasIncludes(featureProject)) {
                  assemblyFeatures.add(featureProject);
               }
            }
            else {
               B2MetadataUtils.addAssemblyName(singleIncludedFeature, assemblyName);
               B2MetadataUtils.addAssemblyClassifier(singleIncludedFeature,
                  converter.getAssemblyClassifier(moduleProperties, assemblyName));
               B2MetadataUtils.addReplacedFeatureId(singleIncludedFeature, featureProject.getId());
            }
         }
         // hide assembly features until all assembly names are processed
         featuresFacet.getProjects().addAll(assemblyFeatures);
      }
   }

   private static boolean hasIncludes(FeatureProject featureProject) {
      return !featureProject.getIncludedFeatures().isEmpty() || !featureProject.getIncludedPlugins().isEmpty();
   }

   private FeatureProject returnSingleIncludedFeatureProject(AbstractModule module, final FeatureProject featureProject) {
      if (isIncludesJustOneFeature(featureProject)) {
         return module.resolveReference(featureProject.getIncludedFeatures().get(0), FeaturesFacet.class);
      }
      return null;
   }

   private boolean isIncludesJustOneFeature(final FeatureProject featureProject) {
      return featureProject.getIncludedFeatures().size() == 1 && featureProject.getIncludedPlugins().isEmpty()
         && featureProject.getRequiredFeatures().isEmpty() && featureProject.getRequiredPlugins().isEmpty();
   }


   private void interpolatePluginFeatures(AbstractModule module, FeaturesFacet featuresFacet,
      PluginsFacet pluginsFacet, PropertiesSource moduleProperties) {
      FeatureProject featureProject = createFeatureProject(module, featuresFacet, pluginsFacet, moduleProperties, false);
      featuresFacet.getProjects().add(featureProject);

      if (sourceService.isSourceBuildEnabled(moduleProperties)) {
         featureProject = createFeatureProject(module, featuresFacet, pluginsFacet, moduleProperties, true);
         featuresFacet.getProjects().add(featureProject);
      }
   }

   private FeatureProject createFeatureProject(AbstractModule module, FeaturesFacet featuresFacet,
      PluginsFacet pluginsFacet, PropertiesSource moduleProperties, boolean isSource) {
      final String featureId = deriveFeatureId(module, pluginsFacet, moduleProperties, isSource);

      final FeatureProject featureProject = ModuleModelFactory.eINSTANCE.createFeatureProject();
      featureProject.setDerived(true);
      featureProject.setId(featureId);
      featureProject.setVersion(module.getVersion());

      final String facetName = pluginsFacet.getName();
      B2MetadataUtils.setModuleId(featureProject, module.getId());
      B2MetadataUtils.setModuleVersion(featureProject, module.getVersion());
      B2MetadataUtils.setFacetName(featureProject, facetName);
      B2MetadataUtils.setFacetClassifier(featureProject, converter.getFacetClassifier(moduleProperties, facetName));
      B2MetadataUtils.setSourceFeature(featureProject, isSource);

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      featureProject.setDirectory(new File(layout.pathOfFacetMetaData(module, featuresFacet.getName(),
         featureProject.getId())));

      return featureProject;
   }

   private String deriveFeatureId(AbstractModule module, String assemblyName, PropertiesSource properties) {
      return converter.getFeatureIdForAssembly(properties, assemblyName, module.getId());
   }

   private String deriveFeatureId(AbstractModule module, PluginsFacet pluginsFacet, PropertiesSource properties,
      boolean isSource) {
      return converter.getFeatureIdForFacet(properties, pluginsFacet.getName(), module.getId(), isSource);
   }

   private FeaturesFacet createFeaturesFacet(String facetName) {
      final FeaturesFacet featuresFacet = ModuleModelFactory.eINSTANCE.createFeaturesFacet();
      featuresFacet.setDerived(true);
      featuresFacet.setName(facetName);
      return featuresFacet;
   }
}
