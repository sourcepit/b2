/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sourcepit.b2.model.builder.util.BasicConverter.AggregatorMode;
import org.sourcepit.b2.model.builder.util.FeaturesConverter;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleRequirement;
import org.sourcepit.common.manifest.osgi.PackageExport;
import org.sourcepit.common.manifest.osgi.PackageImport;
import org.sourcepit.common.manifest.osgi.Version;
import org.sourcepit.common.manifest.osgi.VersionRange;
import org.sourcepit.common.utils.collections.FilteredIterable;
import org.sourcepit.common.utils.collections.LinkedMultiValuHashMap;
import org.sourcepit.common.utils.collections.MultiValueMap;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;


public abstract class AbstractIncludesAndRequirementsResolver implements IncludesAndRequirementsResolver
{
   private FeaturesConverter converter;

   public AbstractIncludesAndRequirementsResolver(FeaturesConverter converter)
   {
      this.converter = converter;
   }

   // TODO check duplicates
   public void appendIncludesAndRequirements(PropertiesSource moduleProperties, AbstractModule module,
      FeatureProject assemblyFeature, String assemblyName)
   {
      final List<FeatureInclude> result = new ArrayList<FeatureInclude>();
      aggregateForeignFeatures(result, moduleProperties, module, assemblyFeature, assemblyName);

      assemblyFeature.getIncludedFeatures().addAll(result);

      List<FeatureInclude> includedFeatures = converter.getIncludedFeaturesForAssembly(moduleProperties, assemblyName);
      assemblyFeature.getIncludedFeatures().addAll(includedFeatures);

      final List<PluginInclude> includedPlugins;
      includedPlugins = converter.getIncludedPluginsForAssembly(moduleProperties, assemblyName);
      assemblyFeature.getIncludedPlugins().addAll(includedPlugins);

      final List<RuledReference> requiredFeatures;
      requiredFeatures = converter.getRequiredFeaturesForAssembly(moduleProperties, assemblyName);
      assemblyFeature.getRequiredFeatures().addAll(requiredFeatures);

      final List<RuledReference> requiredPlugins;
      requiredPlugins = converter.getRequiredPluginsForAssembly(moduleProperties, assemblyName);
      assemblyFeature.getRequiredPlugins().addAll(requiredPlugins);
   }

   private void aggregateForeignFeatures(final List<FeatureInclude> result, PropertiesSource moduleProperties,
      AbstractModule module, FeatureProject assemblyFeature, String assemblyName)
   {
      final AggregatorMode mode = converter.getAggregatorMode(moduleProperties, assemblyName);
      if (mode != AggregatorMode.OFF)
      {
         final boolean isTest = Boolean.valueOf(assemblyFeature.getAnnotationEntry("b2", "isTestFeature"));
         final Iterable<FeatureProject> resolutionContext = resolveIncludeResolutionContext(module, isTest);
         if (mode == AggregatorMode.AGGREGATE)
         {
            final PathMatcher matcher = converter
               .getAggregatorFeatureMatcherForAssembly(moduleProperties, assemblyName);
            for (FeatureProject featureProject : resolutionContext)
            {
               if (matcher.isMatch(featureProject.getId()))
               {
                  result.add(toFeatureInclude(featureProject));
               }
            }
         }
         else if (mode == AggregatorMode.UNWRAP)
         {
            final PathMatcher featureMatcher = converter.getFeatureMatcherForAssembly(moduleProperties, assemblyName);
            final PathMatcher pluginMatcher = converter.getPluginMatcherForAssembly(moduleProperties, assemblyName);
            for (FeatureProject featureProject : resolutionContext)
            {
               for (FeatureInclude featureInclude : featureProject.getIncludedFeatures())
               {
                  if (featureMatcher.isMatch(featureInclude.getId()))
                  {
                     result.add(EcoreUtil.copy(featureInclude));
                  }
               }

               for (PluginInclude pluginInclude : featureProject.getIncludedPlugins())
               {
                  if (pluginMatcher.isMatch(pluginInclude.getId()))
                  {
                     // TODO
                     // result.add(EcoreUtil.copy(pluginInclude));
                  }
               }
               
               // TODO requirements?
            }
         }
      }
   }

   private FeatureInclude toFeatureInclude(FeatureProject featureProject)
   {
      FeatureInclude featureInclude = ModuleModelFactory.eINSTANCE.createFeatureInclude();
      featureInclude.setId(featureProject.getId());
      featureInclude.setVersion(featureInclude.getVersion());
      return featureInclude;
   }

   private Iterable<FeatureProject> resolveIncludeResolutionContext(AbstractModule module, boolean isTest)
   {
      final MultiValueMap<AbstractModule, String> moduleToAssemblies = new LinkedMultiValuHashMap<AbstractModule, String>(
         LinkedHashSet.class);

      determineForeignResolutionContext(moduleToAssemblies, module, isTest);

      final Collection<FeatureProject> result = new LinkedHashSet<FeatureProject>();
      for (Entry<AbstractModule, Collection<String>> entry : moduleToAssemblies.entrySet())
      {
         AbstractModule foreignModule = entry.getKey();

         if (foreignModule.equals(module))
         {
            throw new IllegalStateException();
         }

         for (String visibleAssembly : entry.getValue())
         {
            final FeatureProject assemblyFeature = findFeatureProjectForAssembly(foreignModule, visibleAssembly);
            if (assemblyFeature == null)
            {
               throw new IllegalStateException("Cannot find feature project for assembly " + visibleAssembly);
            }
            result.add(assemblyFeature);
         }
      }

      // final FeatureProject assemblyFeature = findFeatureProjectForAssembly(module, assemblyName);
      // if (assemblyFeature == null)
      // {
      // throw new IllegalStateException("Cannot find feature project for assembly " + assemblyName);
      // }
      return result;// new FilteredIterable<FeatureProject>(result, assemblyFeature);
   }

   private static FeatureProject findFeatureProjectForAssembly(AbstractModule module, String assemblyName)
   {
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         for (FeatureProject featureProject : featuresFacet.getProjects())
         {
            Collection<String> assemblyNames = new HashSet<String>();
            FeaturesInterpolator.split(assemblyNames, featureProject.getAnnotationEntry("b2", "assemblyNames"));
            if (assemblyNames.contains(assemblyName))
            {
               return featureProject;
            }
         }
      }
      return null;
   }

   protected abstract void determineForeignResolutionContext(MultiValueMap<AbstractModule, String> moduleToAssemblies,
      AbstractModule module, boolean isTest);

   // TODO check duplicates
   public void appendIncludesAndRequirements(PropertiesSource moduleProperties, AbstractModule module,
      FeatureProject facetFeatrue)
   {
      final String facetName = facetFeatrue.getAnnotationEntry("b2", "facetName");
      final PluginsFacet sourcePlugins = module.getFacetByName(facetName);
      final boolean isSource = Boolean.valueOf(facetFeatrue.getAnnotationEntry("b2", "isSourceFeature"));

      final List<RuledReference> featureRequirements = new ArrayList<RuledReference>();

      final Iterable<PluginsFacet> resolutionContext = resolveRequirementResolutionContext(module, sourcePlugins);

      for (PluginsFacet requiredPluginsFacet : determineRequiredPluginFacets(sourcePlugins, resolutionContext))
      {
         final FeatureProject featureProject = findFeatureProjectForPluginsFacet(requiredPluginsFacet, isSource);
         if (featureProject == null)
         {
            throw new IllegalStateException("Cannot find feature project for plugin facet "
               + requiredPluginsFacet.getName());
         }

         final RuledReference featureRequirement = ModuleModelFactory.eINSTANCE.createRuledReference();
         featureRequirement.setId(featureProject.getId());
         featureRequirement.setVersion(featureProject.getVersion());

         featureRequirements.add(featureRequirement);
      }
      facetFeatrue.getRequiredFeatures().addAll(featureRequirements);

      // custom includes and requirements
      final List<FeatureInclude> includedFeatures;
      includedFeatures = converter.getIncludedFeaturesForFacet(moduleProperties, facetName, isSource);
      facetFeatrue.getIncludedFeatures().addAll(includedFeatures);

      final List<PluginInclude> includedPlugins;
      includedPlugins = converter.getIncludedPluginsForFacet(moduleProperties, facetName, isSource);
      facetFeatrue.getIncludedPlugins().addAll(includedPlugins);

      final List<RuledReference> requiredFeatures;
      requiredFeatures = converter.getRequiredFeaturesForFacet(moduleProperties, facetName, isSource);
      facetFeatrue.getRequiredFeatures().addAll(requiredFeatures);

      final List<RuledReference> requiredPlugins;
      requiredPlugins = converter.getRequiredPluginsForFacet(moduleProperties, facetName, isSource);
      facetFeatrue.getRequiredPlugins().addAll(requiredPlugins);
   }


   private Iterable<PluginsFacet> resolveRequirementResolutionContext(AbstractModule module, PluginsFacet sourcePlugins)
   {
      final boolean isTest = isTest(sourcePlugins);

      Collection<PluginsFacet> result = new LinkedHashSet<PluginsFacet>();
      result.addAll(sourcePlugins.getParent().getFacets(PluginsFacet.class));

      MultiValueMap<AbstractModule, String> moduleToAssemblies = new LinkedMultiValuHashMap<AbstractModule, String>(
         LinkedHashSet.class);
      determineForeignResolutionContext(moduleToAssemblies, sourcePlugins.getParent(), isTest);

      final Collection<AbstractModule> collection = moduleToAssemblies.keySet();
      // TODO composit iterator
      for (AbstractModule foreignModule : collection)
      {
         if (foreignModule.equals(module))
         {
            throw new IllegalStateException();
         }

         result.addAll(foreignModule.getFacets(PluginsFacet.class));
      }

      return new FilteredIterable<PluginsFacet>(result, sourcePlugins);
   }

   private boolean isTest(PluginsFacet sourcePlugins)
   {
      for (PluginProject pluginProject : sourcePlugins.getProjects())
      {
         if (pluginProject.isTestPlugin())
         {
            return true;
         }
      }
      return false;
   }

   private static List<PluginsFacet> determineRequiredPluginFacets(PluginsFacet sourcePlugins,
      Iterable<PluginsFacet> resolutionContext)
   {
      final List<PluginsFacet> requiredPluginFacets = new ArrayList<PluginsFacet>();
      for (PluginProject sourcePlugin : sourcePlugins.getProjects())
      {
         for (PluginsFacet targetPlugins : resolutionContext)
         {
            if (isRequiresFacet(sourcePlugin, targetPlugins))
            {
               requiredPluginFacets.add(targetPlugins);
            }
         }
      }
      return requiredPluginFacets;
   }

   private static boolean isRequiresFacet(PluginProject sourcePlugin, PluginsFacet targetPlugins)
   {
      for (PluginProject targetPlugin : targetPlugins.getProjects())
      {
         final BundleManifest sourceManifest = sourcePlugin.getBundleManifest();
         final BundleManifest targetManifest = targetPlugin.getBundleManifest();
         if (isRequiresBundle(sourceManifest, targetManifest))
         {
            return true;
         }
      }
      return false;
   }

   private static boolean isRequiresBundle(BundleManifest src, BundleManifest target)
   {
      final EList<BundleRequirement> requireBundle = src.getRequireBundle();
      if (requireBundle != null)
      {
         final String symbolicName = target.getBundleSymbolicName().getSymbolicName();
         final Version version = target.getBundleVersion();
         if (isRequiredBundle(requireBundle, symbolicName, version))
         {
            return true;
         }
      }

      final EList<PackageImport> importPackage = src.getImportPackage();
      final EList<PackageExport> exportPackage = target.getExportPackage();
      if (importPackage != null && exportPackage != null && hasImportedPackages(importPackage, exportPackage))
      {
         return true;
      }

      return false;
   }

   private static boolean hasImportedPackages(final List<PackageImport> importPackage,
      final EList<PackageExport> exportPackage)
   {
      for (PackageExport packageExport : exportPackage)
      {
         if (isImportedPackage(importPackage, packageExport))
         {
            return true;
         }
      }
      return false;
   }


   private static boolean isImportedPackage(final List<PackageImport> importPackage, PackageExport packageExport)
   {
      for (String packageName : packageExport.getPackageNames())
      {
         for (PackageImport packageImport : importPackage)
         {
            if (packageImport.getPackageNames().contains(packageName))
            {
               final VersionRange requiredVersion = packageImport.getVersion();
               if (requiredVersion == null || requiredVersion.includes(packageExport.getVersion()))
               {
                  return true;
               }
            }
         }
      }
      return false;
   }

   private static boolean isRequiredBundle(List<BundleRequirement> requiredBundles, String symbolicName, Version version)
   {
      for (BundleRequirement requiredBundle : requiredBundles)
      {
         if (requiredBundle.getSymbolicNames().contains(symbolicName))
         {
            final VersionRange requiredVersion = requiredBundle.getBundleVersion();
            if (requiredVersion == null || requiredVersion.includes(version))
            {
               return true;
            }
         }
      }
      return false;
   }


   // TODO move to util and test it
   public static FeatureProject findFeatureProjectForPluginsFacet(PluginsFacet pluginsFacet, boolean isSource)
   {
      final AbstractModule module = pluginsFacet.getParent();
      final String facetName = pluginsFacet.getName();
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         for (FeatureProject featureProject : featuresFacet.getProjects())
         {
            if (facetName.equals(featureProject.getAnnotationEntry("b2", "facetName")))
            {
               if (isSource == Boolean.valueOf(featureProject.getAnnotationEntry("b2", "isSourceFeature")))
               {
                  return featureProject;
               }
            }
         }
      }
      return null;
   }

}
