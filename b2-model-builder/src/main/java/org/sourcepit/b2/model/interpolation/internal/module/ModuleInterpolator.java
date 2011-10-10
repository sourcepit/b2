/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.common.internal.utils.PathMatcher;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.builder.util.IModelCache;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.IUnpackStrategy;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.module.IModuleInterpolationRequest;
import org.sourcepit.b2.model.interpolation.module.IModuleInterpolator;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.Reference;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;

@Named
public class ModuleInterpolator implements IModuleInterpolator
{
   @Inject
   private IAggregationService aggregationService;

   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private IUnpackStrategy unpackStrategy;

   @Inject
   private ISourceService sourceManager;

   public void interpolate(IModuleInterpolationRequest request)
   {
      checkRequest(request);
      AbstractModule module = request.getModule();

      final IModelCache cache = request.getModelCache();
      if (cache != null)
      {
         if (cache.get(module.getDirectory()) != null)
         {
            return;
         }
      }

      doInterpolate(module, request);
   }

   private void checkRequest(IModuleInterpolationRequest request)
   {
      if (request == null)
      {
         throw new IllegalArgumentException("Request must not be null.");
      }

      if (request.getModule() == null)
      {
         throw new IllegalArgumentException("Module must not be null.");
      }

      final IConverter converter = request.getConverter();
      if (converter == null)
      {
         throw new IllegalArgumentException("converter must not be null.");
      }
   }

   private void doInterpolate(AbstractModule module, IModuleInterpolationRequest request)
   {
      final boolean hasFestures = module.hasFacets(FeaturesFacet.class);
      if (!hasFestures)
      {
         interpolateFeatures(module, request.getConverter());
      }
      interpolateSites(module, request.getConverter());
   }

   private IInterpolationLayout getLayout(final String layoutId)
   {
      final IInterpolationLayout layout = layoutMap.get(layoutId);
      if (layout == null)
      {
         throw new UnsupportedOperationException("Layout " + layoutId + " is not supported.");
      }
      return layout;
   }

   private void interpolateSites(AbstractModule module, IConverter converter)
   {
      final SitesFacet sitesFacet = ModuleModelFactory.eINSTANCE.createSitesFacet();
      sitesFacet.setDerived(true);
      sitesFacet.setName(IInterpolationLayout.DEFAULT_SITES_FACET_NAME);

      for (String siteClassifer : converter.getSiteClassifiers())
      {
         final SiteProject siteProject = createSiteProject(module, converter, siteClassifer);

         for (String categoryClassifer : converter.getCategoryClassifiersForSite(siteClassifer))
         {
            final Category category = ModuleModelFactory.eINSTANCE.createCategory();
            category.setName(categoryClassifer);

            final PathMatcher matcher = converter.createIdMatcherForCategory(module.getLayoutId(), categoryClassifer);
            final List<FeatureInclude> featureIncs = new ArrayList<FeatureInclude>();
            collectFeatureIncludes(module, featureIncs, matcher, converter);
            
            for (FeatureProject includedFeatures : aggregationService.resolveCategoryIncludes(module, categoryClassifer,
               converter))
            {
               final FeatureInclude featureInclude = ModuleModelFactory.eINSTANCE.createFeatureInclude();
               featureInclude.setId(includedFeatures.getId());
               featureInclude.setVersionRange(includedFeatures.getVersion());
               featureIncs.add(featureInclude);
            }
            
            for (FeatureInclude featureInc : featureIncs)
            {
               final Reference featureRef = ModuleModelFactory.eINSTANCE.createReference();
               featureRef.setId(featureInc.getId());
               featureRef.setVersionRange(featureInc.getVersionRange());
               category.getFeatureReferences().add(featureRef);
            }

            if (!category.getFeatureReferences().isEmpty())
            {
               siteProject.getCategories().add(category);
            }
         }

         if (!siteProject.getCategories().isEmpty())
         {
            sitesFacet.getProjects().add(siteProject);
         }
      }

      if (!sitesFacet.getProjects().isEmpty())
      {
         module.getFacets().add(sitesFacet);
      }
   }

   private SiteProject createSiteProject(AbstractModule module, IConverter converter, final String classifier)
   {
      final IInterpolationLayout layout = getLayout(module.getLayoutId());

      final SiteProject site = ModuleModelFactory.eINSTANCE.createSiteProject();
      site.setDerived(true);
      site.setClassifier(classifier);
      site.setId(layout.idOfSiteProject(module, classifier));
      site.setVersion(converter.getModuleVersion());
      site.setDirectory(new File(layout.pathOfSiteProject(module, classifier)));

      return site;
   }

   private void interpolateFeatures(AbstractModule module, IConverter converter)
   {
      final FeaturesFacet featuresFacet = ModuleModelFactory.eINSTANCE.createFeaturesFacet();
      featuresFacet.setDerived(true);
      featuresFacet.setName(IInterpolationLayout.DEFAULT_FEATURES_FACET_NAME);

      final EList<PluginsFacet> facets = module.getFacets(PluginsFacet.class);

      final Map<String, PluginsFacet> classiferToPluginFacetMap = createFeatureClassiferToPluginFacetMap(converter,
         facets);

      final Set<String> classifiers = getFeatureClassifers(converter, facets);

      final Set<String> moduleFeatures = new LinkedHashSet<String>();
      for (String classifer : classifiers)
      {
         final PluginsFacet pluginsFacet = classiferToPluginFacetMap.get(classifer);
         if (pluginsFacet == null)
         {
            moduleFeatures.add(classifer);
         }
         else
         {
            final List<FeatureProject> result = interpolateFacetFeature(module, pluginsFacet, converter, classifer);
            for (FeatureProject featureProject : result)
            {
               if (!isEmpty(featureProject))
               {
                  featuresFacet.getProjects().add(featureProject);
               }
            }
         }
      }

      if (!featuresFacet.getProjects().isEmpty())
      {
         module.getFacets().add(featuresFacet);
      }

      for (String classifer : moduleFeatures)
      {
         final List<FeatureProject> result = interpolateModuleFeature(module, converter, classifer);
         for (FeatureProject featureProject : result)
         {
            if (!isEmpty(featureProject))
            {
               featuresFacet.getProjects().add(featureProject);
            }
         }
      }

      if (!module.getFacets().contains(featuresFacet) && !featuresFacet.getProjects().isEmpty())
      {
         module.getFacets().add(featuresFacet);
      }
   }

   private boolean isEmpty(FeatureProject featureProject)
   {
      return featureProject.getIncludedFeatures().isEmpty() && featureProject.getIncludedPlugins().isEmpty();
   }

   private List<FeatureProject> interpolateFacetFeature(AbstractModule module, PluginsFacet pluginFacet,
      IConverter converter, String featureClassifier)
   {
      final List<FeatureProject> result = new ArrayList<FeatureProject>();

      final PathMatcher matcher = converter.createIdMatcherForFeature(module.getLayoutId(), featureClassifier);

      final List<PluginInclude> pluginIncs = new ArrayList<PluginInclude>();
      final List<PluginInclude> sourceIncs = new ArrayList<PluginInclude>();
      collectPluginIncludes(pluginFacet, pluginIncs, sourceIncs, matcher, converter);

      final FeatureProject featureProject = createFeatureProject(module, featureClassifier,
         converter.getModuleVersion());
      result.add(featureProject);
      featureProject.getIncludedPlugins().addAll(pluginIncs);

      if (!sourceIncs.isEmpty())
      {
         final FeatureProject sourcesFeatureProject = createFeatureProject(module,
            converter.createSourceFeatureClassifer(featureClassifier), converter.getModuleVersion());
         if (matcher.isMatch(sourcesFeatureProject.getId()))
         {
            sourcesFeatureProject.getIncludedPlugins().addAll(sourceIncs);
            result.add(sourcesFeatureProject);
         }
      }

      for (FeatureProject includedFeatures : aggregationService.resolveFeatureIncludes(module, featureClassifier,
         converter))
      {
         final FeatureInclude featureInclude = ModuleModelFactory.eINSTANCE.createFeatureInclude();
         featureInclude.setId(includedFeatures.getId());
         featureInclude.setVersionRange(includedFeatures.getVersion());
         featureProject.getIncludedFeatures().add(featureInclude);
      }

      return result;
   }

   private List<FeatureProject> interpolateModuleFeature(AbstractModule module, IConverter converter,
      String featureClassifer)
   {
      final List<FeatureProject> result = new ArrayList<FeatureProject>();

      final FeatureProject featureProject = createFeatureProject(module, featureClassifer, converter.getModuleVersion());

      final PathMatcher matcher = converter.createIdMatcherForFeature(module.getLayoutId(), featureClassifer);

      final List<PluginInclude> pluginIncs = new ArrayList<PluginInclude>();
      final List<PluginInclude> sourceIncs = new ArrayList<PluginInclude>();
      collectPluginIncludes(module, pluginIncs, sourceIncs, matcher, converter);

      final List<FeatureInclude> featureIncs = new ArrayList<FeatureInclude>();
      collectFeatureIncludes(module, featureIncs, matcher, converter);

      featureProject.getIncludedFeatures().addAll(featureIncs);
      featureProject.getIncludedPlugins().addAll(pluginIncs);
      featureProject.getIncludedPlugins().addAll(sourceIncs);

      result.add(featureProject);

      for (FeatureProject includedFeatures : aggregationService.resolveFeatureIncludes(module, featureClassifer,
         converter))
      {
         final FeatureInclude featureInclude = ModuleModelFactory.eINSTANCE.createFeatureInclude();
         featureInclude.setId(includedFeatures.getId());
         featureInclude.setVersionRange(includedFeatures.getVersion());
         featureProject.getIncludedFeatures().add(featureInclude);
      }

      return result;
   }

   private void collectFeatureIncludes(AbstractModule module, List<FeatureInclude> featureIncs, PathMatcher matcher,
      IConverter converter)
   {
      final List<FeaturesFacet> featuresFacets = new ArrayList<FeaturesFacet>();
      if (module instanceof CompositeModule)
      {
         CompositeModule compositeModule = (CompositeModule) module;

         collectFacets(module, FeaturesFacet.class, featuresFacets, false);
         for (AbstractModule abstractModule : compositeModule.getModules())
         {
            collectFacets(abstractModule, FeaturesFacet.class, featuresFacets, false);
         }
      }
      else
      {
         featuresFacets.addAll(module.getFacets(FeaturesFacet.class));
      }

      for (FeaturesFacet featuresFacet : featuresFacets)
      {
         collectFeatureIncludes(featuresFacet, featureIncs, matcher, converter);
      }
   }

   private <F extends AbstractFacet> void collectFacets(AbstractModule module, Class<F> facetType, List<F> facets,
      boolean recurive)
   {
      facets.addAll(module.getFacets(facetType));
      if (recurive && module instanceof CompositeModule)
      {
         for (AbstractModule abstractModule : ((CompositeModule) module).getModules())
         {
            collectFacets(abstractModule, facetType, facets, recurive);
         }
      }
   }

   private void collectFeatureIncludes(FeaturesFacet featuresFacet, List<FeatureInclude> featureIncs,
      PathMatcher matcher, IConverter converter)
   {
      for (FeatureProject featureProject : featuresFacet.getProjects())
      {
         if (matcher.isMatch(featureProject.getId()))
         {
            final FeatureInclude featureInclude = ModuleModelFactory.eINSTANCE.createFeatureInclude();
            featureInclude.setId(featureProject.getId());
            featureInclude.setVersionRange(featureProject.getVersion());
            featureIncs.add(featureInclude);
         }
      }
   }

   private void collectPluginIncludes(AbstractModule module, final List<PluginInclude> pluginIncs,
      final List<PluginInclude> sourceIncs, final PathMatcher matcher, IConverter converter)
   {
      for (PluginsFacet pluginsFacet : module.getFacets(PluginsFacet.class))
      {
         collectPluginIncludes(pluginsFacet, pluginIncs, sourceIncs, matcher, converter);
      }
   }

   private void collectPluginIncludes(PluginsFacet pluginsFacet, final List<PluginInclude> pluginIncs,
      final List<PluginInclude> sourceIncs, final PathMatcher matcher, IConverter converter)
   {
      for (PluginProject pluginProject : pluginsFacet.getProjects())
      {
         if (matcher.isMatch(pluginProject.getId()))
         {
            final PluginInclude pluginInclude = ModuleModelFactory.eINSTANCE.createPluginInclude();
            pluginInclude.setId(pluginProject.getId());
            pluginInclude.setVersionRange(pluginProject.getVersion());
            pluginInclude.setUnpack(isUnpack(pluginProject));
            pluginIncs.add(pluginInclude);

            if (isSourceBuildEnabled(pluginProject, converter))
            {
               final String sourcePluginId = converter.createSourcePluginId(pluginProject.getId());
               if (matcher.isMatch(sourcePluginId))
               {
                  final PluginInclude source = ModuleModelFactory.eINSTANCE.createPluginInclude();
                  source.setId(sourcePluginId);
                  source.setVersionRange(pluginProject.getVersion());

                  sourceIncs.add(source);
               }
            }
         }
      }
   }

   private Set<String> getFeatureClassifers(IConverter converter, final EList<PluginsFacet> facets)
   {
      final Set<String> pluginFacetNames = new LinkedHashSet<String>();
      for (PluginsFacet pluginsFacet : facets)
      {
         pluginFacetNames.add(pluginsFacet.getName());
      }

      return converter.getFeatureClassifiers(pluginFacetNames);
   }

   private Map<String, PluginsFacet> createFeatureClassiferToPluginFacetMap(IConverter converter,
      EList<PluginsFacet> facets)
   {
      final Map<String, PluginsFacet> classiferToFacetMap = new LinkedHashMap<String, PluginsFacet>();
      for (PluginsFacet pluginsFacet : facets)
      {
         classiferToFacetMap.put(converter.convertFacetNameToFeatureClassifier(pluginsFacet.getName()), pluginsFacet);
      }
      return classiferToFacetMap;
   }

   private FeatureProject createFeatureProject(AbstractModule module, final String classifier, String version)
   {
      final IInterpolationLayout layout = getLayout(module.getLayoutId());

      final FeatureProject featureProject = ModuleModelFactory.eINSTANCE.createFeatureProject();
      featureProject.setDerived(true);
      featureProject.setId(layout.idOfFeatureProject(module, classifier));
      featureProject.setVersion(version);
      featureProject.setDirectory(new File(layout.pathOfFeatureProject(module, classifier)));
      featureProject.setClassifier(classifier);

      return featureProject;
   }

   private boolean isSourceBuildEnabled(PluginProject pluginProject, IConverter converter)
   {
      return sourceManager == null ? false : sourceManager.isSourceBuildEnabled(pluginProject, converter);
   }

   private boolean isUnpack(PluginProject pluginProject)
   {
      return unpackStrategy == null ? false : unpackStrategy.isUnpack(pluginProject);
   }

}
