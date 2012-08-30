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
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.builder.util.Converter2;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.internal.util.EWalkerImpl;
import org.sourcepit.common.utils.path.PathMatcher;
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
      if (!pluginsFacets.isEmpty())
      {
         final FeaturesFacet featuresFacet = createFeaturesFacet("facet-features");
         for (PluginsFacet pluginsFacet : pluginsFacets)
         {
            interpolatePluginFeatures(module, featuresFacet, pluginsFacet, moduleProperties, converter);
         }
         module.getFacets().add(featuresFacet);
      }

      interpolateAssemblyFeatures(module, moduleProperties, converter);
   }

   private void interpolateAssemblyFeatures(AbstractModule module, PropertiesSource moduleProperties,
      Converter2 converter)
   {
      final List<String> assemplyNames = converter.getAssemblyNames(moduleProperties);
      if (!assemplyNames.isEmpty())
      {
         FeaturesFacet featuresFacet = createFeaturesFacet("assembly-features");
         for (String assemblyName : assemplyNames)
         {
            final String featureId = deriveFeatureId(module, assemblyName, moduleProperties, converter);
            final String facetName = featuresFacet.getName();

            final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
            final String path = layout.pathOfFacetMetaData(module, facetName, featureId);

            final FeatureProject featureProject = ModuleModelFactory.eINSTANCE.createFeatureProject();
            featureProject.setDerived(true);
            featureProject.setId(featureId);
            featureProject.setDirectory(new File(path));

            featureProject.putAnnotationEntry("b2", "assemblyName", assemblyName);

            featuresFacet.getProjects().add(featureProject);
         }

         module.getFacets().add(featuresFacet);

         for (final FeatureProject featureProject : featuresFacet.getProjects())
         {
            final String assemblyName = featureProject.getAnnotationEntry("b2", "assemblyName");

            final EWalkerImpl eWalker;
            eWalker = createIncludesAppenderForAssembly(moduleProperties, converter, featureProject, assemblyName);
            eWalker.walk(module.getFacets(FeaturesFacet.class));
            eWalker.walk(module.getFacets(PluginsFacet.class));

            addCustomIncludesAndRequirements(featureProject, assemblyName, moduleProperties, converter);
         }
      }
   }


   private void addCustomIncludesAndRequirements(final FeatureProject featureProject, String assemplyName,
      PropertiesSource moduleProperties, Converter2 converter)
   {
      // TODO check duplicated includes
      final List<FeatureInclude> includedFeatures;
      includedFeatures = converter.getIncludedFeaturesForAssembly(moduleProperties, assemplyName);
      featureProject.getIncludedFeatures().addAll(includedFeatures);

      final List<PluginInclude> includedPlugins;
      includedPlugins = converter.getIncludedPluginsForAssembly(moduleProperties, assemplyName);
      featureProject.getIncludedPlugins().addAll(includedPlugins);

      // TODO calc inter assembly dependencies
      // TODO calc inter module dependencies
      final List<RuledReference> requiredFeatures;
      requiredFeatures = converter.getRequiredFeaturesForAssembly(moduleProperties, assemplyName);
      featureProject.getRequiredFeatures().addAll(requiredFeatures);

      final List<RuledReference> requiredPlugins;
      requiredPlugins = converter.getRequiredPluginsForAssembly(moduleProperties, assemplyName);
      featureProject.getRequiredPlugins().addAll(requiredPlugins);
   }

   private void interpolatePluginFeatures(AbstractModule module, FeaturesFacet featuresFacet,
      PluginsFacet pluginsFacet, PropertiesSource moduleProperties, Converter2 converter)
   {
      FeatureProject featureProject = createFeatureProject(module, featuresFacet, pluginsFacet, moduleProperties,
         converter, false);
      featuresFacet.getProjects().add(featureProject);

      if (sourceService.isSourceBuildEnabled(moduleProperties))
      {
         featureProject = createFeatureProject(module, featuresFacet, pluginsFacet, moduleProperties, converter, true);
         featuresFacet.getProjects().add(featureProject);
      }
   }

   private FeatureProject createFeatureProject(AbstractModule module, FeaturesFacet featuresFacet,
      PluginsFacet pluginsFacet, PropertiesSource moduleProperties, Converter2 converter, boolean isSource)
   {
      final String featureId = deriveFeatureId(module, pluginsFacet, moduleProperties, converter, isSource);

      final FeatureProject featureProject = ModuleModelFactory.eINSTANCE.createFeatureProject();
      featureProject.setDerived(true);
      featureProject.setId(featureId);

      final String facetName = pluginsFacet.getName();
      featureProject.putAnnotationEntry("b2", "facetName", facetName);

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      featureProject.setDirectory(new File(layout.pathOfFacetMetaData(module, featuresFacet.getName(),
         featureProject.getId())));


      final IncludesAppender includesAppender;
      includesAppender = createIncludesAppenderForFacet(moduleProperties, converter, isSource, featureProject,
         facetName);
      includesAppender.walk(pluginsFacet.getProjects());

      addCustomIncludesAndRequirements(featureProject, pluginsFacet, moduleProperties, converter, isSource);

      return featureProject;
   }


   private void addCustomIncludesAndRequirements(FeatureProject featureProject, PluginsFacet pluginsFacet,
      PropertiesSource moduleProperties, Converter2 converter, boolean isSource)
   {
      final String facetName = pluginsFacet.getName();

      // TODO check duplicated includes
      final List<FeatureInclude> includedFeatures;
      includedFeatures = converter.getIncludedFeaturesForFacet(moduleProperties, facetName, isSource);
      featureProject.getIncludedFeatures().addAll(includedFeatures);

      final List<PluginInclude> includedPlugins;
      includedPlugins = converter.getIncludedPluginsForFacet(moduleProperties, facetName, isSource);
      featureProject.getIncludedPlugins().addAll(includedPlugins);

      // TODO calc inter facet dependencies
      // TODO calc inter module dependencies
      final List<RuledReference> requiredFeatures;
      requiredFeatures = converter.getRequiredFeaturesForFacet(moduleProperties, facetName, isSource);
      featureProject.getRequiredFeatures().addAll(requiredFeatures);

      final List<RuledReference> requiredPlugins;
      requiredPlugins = converter.getRequiredPluginsForFacet(moduleProperties, facetName, isSource);
      featureProject.getRequiredPlugins().addAll(requiredPlugins);
   }

   private String deriveFeatureId(AbstractModule module, String assemblyName, PropertiesSource properties,
      Converter2 converter)
   {
      final String classifier = converter.getAssemblyClassifier(properties, assemblyName);
      return converter.getFeatureId(properties, module.getId(), classifier, false);
   }

   private String deriveFeatureId(AbstractModule module, PluginsFacet pluginsFacet, PropertiesSource properties,
      Converter2 converter, boolean isSource)
   {
      final String classifier = converter.getFacetClassifier(properties, pluginsFacet.getName());
      return converter.getFeatureId(properties, module.getId(), classifier, isSource);
   }

   private FeaturesFacet createFeaturesFacet(String facetName)
   {
      final FeaturesFacet featuresFacet = ModuleModelFactory.eINSTANCE.createFeaturesFacet();
      featuresFacet.setDerived(true);
      featuresFacet.setName(facetName);
      return featuresFacet;
   }

   private IncludesAppender createIncludesAppenderForAssembly(final PropertiesSource moduleProperties,
      final Converter2 converter, final FeatureProject featureProject, final String assemblyName)
   {
      return new IncludesAppender(unpackStrategy, featureProject, false)
      {
         @Override
         protected String getSourcePluginId(PluginProject pp)
         {
            throw new UnsupportedOperationException();
         }
   
         @Override
         protected PathMatcher createFeatureMatcher()
         {
            return converter.getFeatureMatcherForAssembly(moduleProperties, assemblyName);
         }
   
         @Override
         protected PathMatcher createPluginMatcher()
         {
            return converter.getPluginMatcherForAssembly(moduleProperties, assemblyName);
         }
      };
   }

   private IncludesAppender createIncludesAppenderForFacet(final PropertiesSource moduleProperties,
      final Converter2 converter, boolean isSource, final FeatureProject featureProject, final String facetName)
   {
      final IncludesAppender includesAppender;
      includesAppender = new IncludesAppender(unpackStrategy, featureProject, isSource)
      {
         @Override
         protected String getSourcePluginId(PluginProject pp)
         {
            return converter.getSourcePluginId(moduleProperties, pp.getId());
         }
   
         @Override
         protected PathMatcher createPluginMatcher()
         {
            return converter.getPluginMatcherForFacet(moduleProperties, facetName);
         }
   
         @Override
         protected PathMatcher createFeatureMatcher()
         {
            return null;
         }
      };
      return includesAppender;
   }

   private static abstract class IncludesAppender extends EWalkerImpl
   {
      private final FeatureProject targetProject;
      private final PathMatcher featureMatcher;
      private final PathMatcher pluginMatcher;
      private final UnpackStrategy unpackStrategy;
      private final boolean isSource;
   
      private IncludesAppender(UnpackStrategy unpackStrategy, FeatureProject targetProject, boolean isSource)
      {
         this.unpackStrategy = unpackStrategy;
         this.targetProject = targetProject;
         this.featureMatcher = createFeatureMatcher();
         this.pluginMatcher = createPluginMatcher();
         this.isSource = isSource;
      }
   
      @Override
      protected boolean visit(EObject eObject)
      {
         if (eObject != targetProject)
         {
            if (eObject instanceof FeatureProject)
            {
               process((FeatureProject) eObject);
            }
            else if (eObject instanceof PluginProject)
            {
               process((PluginProject) eObject);
            }
         }
         return eObject instanceof FeaturesFacet || eObject instanceof PluginsFacet;
      }
   
      private void process(FeatureProject fp)
      {
         if (featureMatcher.isMatch(fp.getId()))
         {
            final FeatureInclude inc = ModuleModelFactory.eINSTANCE.createFeatureInclude();
            inc.setId(fp.getId());
            inc.setVersion(fp.getVersion());
            targetProject.getIncludedFeatures().add(inc);
         }
      }
   
      private void process(PluginProject pp)
      {
         final String pluginId = isSource ? getSourcePluginId(pp) : pp.getId();
         if (pluginMatcher.isMatch(pluginId))
         {
            final PluginInclude inc = ModuleModelFactory.eINSTANCE.createPluginInclude();
            inc.setId(pluginId);
            inc.setVersion(pp.getVersion());
            if (isSource)
            {
               inc.setUnpack(false);
            }
            else
            {
               inc.setUnpack(unpackStrategy.isUnpack(pp));
            }
            targetProject.getIncludedPlugins().add(inc);
         }
      }
   
      protected abstract String getSourcePluginId(PluginProject pp);
   
      protected abstract PathMatcher createPluginMatcher();
   
      protected abstract PathMatcher createFeatureMatcher();
   }
}
