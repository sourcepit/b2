/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sourcepit.b2.model.builder.util.BasicConverter.AggregatorMode;
import org.sourcepit.b2.model.builder.util.FeaturesConverter;
import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
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
   private static class IncludesAndRequirementsHandler
   {
      private final FeatureProject featureProject;

      public IncludesAndRequirementsHandler(FeatureProject featureProject)
      {
         this.featureProject = featureProject;
      }

      public void addFeatureIncludes(Collection<FeatureInclude> includes)
      {
         for (FeatureInclude include : includes)
         {
            addFeatureInclude(include);
         }
      }

      public void addFeatureInclude(FeatureInclude include)
      {
         addUnique(featureProject.getIncludedFeatures(), include);
         removeAllWithId(featureProject.getRequiredFeatures(), include.getId());
      }

      public void addPluginIncludes(Collection<PluginInclude> includes)
      {
         for (PluginInclude include : includes)
         {
            addPluginInclude(include);
         }
      }

      public void addPluginInclude(PluginInclude include)
      {
         addUnique(featureProject.getIncludedPlugins(), include);
         removeAllWithId(featureProject.getRequiredPlugins(), include.getId());
      }

      public void addFeatureRequirements(Collection<RuledReference> requirements)
      {
         for (RuledReference requirement : requirements)
         {
            addFeatureRequirement(requirement);
         }
      }

      public void addFeatureRequirement(RuledReference requirement)
      {
         if (findRefById(featureProject.getIncludedFeatures(), requirement.getId()) == null)
         {
            addUnique(featureProject.getRequiredFeatures(), requirement);
         }
      }

      public void addPluginRequirements(Collection<RuledReference> requirements)
      {
         for (RuledReference requirement : requirements)
         {
            addPluginRequirement(requirement);
         }
      }

      public void addPluginRequirement(RuledReference requirement)
      {
         if (findRefById(featureProject.getIncludedPlugins(), requirement.getId()) == null)
         {
            addUnique(featureProject.getRequiredPlugins(), requirement);
         }
      }

      private static <R extends AbstractReference> void addUnique(Collection<R> refs, R ref)
      {
         if (findRefById(refs, ref.getId()) == null)
         {
            refs.add(ref);
         }
      }

      private static <R extends AbstractReference> void removeAllWithId(Collection<R> refs, String id)
      {
         final List<R> obsolete = new ArrayList<R>();

         for (R ref : refs)
         {
            if (id.equals(ref.getId()))
            {
               obsolete.add(ref);
            }
         }

         if (!obsolete.isEmpty())
         {
            refs.removeAll(obsolete);
         }
      }

      private static <R extends AbstractReference> R findRefById(Collection<R> refs, String id)
      {
         for (R ref : refs)
         {
            if (id.equals(ref.getId()))
            {
               return ref;
            }
         }
         return null;
      }
   }

   private FeaturesConverter converter;

   public AbstractIncludesAndRequirementsResolver(FeaturesConverter converter)
   {
      this.converter = converter;
   }

   public void appendIncludesAndRequirements(PropertiesSource moduleProperties, AbstractModule module,
      FeatureProject assemblyFeature, String assemblyName)
   {
      final IncludesAndRequirementsHandler handler = new IncludesAndRequirementsHandler(assemblyFeature);
      aggregateForeignFeatures(handler, moduleProperties, module, assemblyFeature, assemblyName);

      final List<FeatureInclude> includedFeatures;
      includedFeatures = converter.getIncludedFeaturesForAssembly(moduleProperties, assemblyName);
      handler.addFeatureIncludes(includedFeatures);

      final List<PluginInclude> includedPlugins;
      includedPlugins = converter.getIncludedPluginsForAssembly(moduleProperties, assemblyName);
      handler.addPluginIncludes(includedPlugins);

      final List<RuledReference> requiredFeatures;
      requiredFeatures = converter.getRequiredFeaturesForAssembly(moduleProperties, assemblyName);
      handler.addFeatureRequirements(requiredFeatures);

      final List<RuledReference> requiredPlugins;
      requiredPlugins = converter.getRequiredPluginsForAssembly(moduleProperties, assemblyName);
      handler.addPluginRequirements(requiredPlugins);
   }

   private void aggregateForeignFeatures(IncludesAndRequirementsHandler handler, PropertiesSource moduleProperties,
      AbstractModule module, FeatureProject assemblyFeature, String assemblyName)
   {
      final AggregatorMode mode = converter.getAggregatorMode(moduleProperties, assemblyName);
      if (mode != AggregatorMode.OFF)
      {
         final boolean isTest = B2MetadataUtils.isTestFeature(assemblyFeature);
         final Iterable<FeatureProject> resolutionContext = resolveIncludeResolutionContext(module, isTest);
         if (mode == AggregatorMode.AGGREGATE)
         {
            final PathMatcher matcher = converter
               .getAggregatorFeatureMatcherForAssembly(moduleProperties, assemblyName);
            for (FeatureProject featureProject : resolutionContext)
            {
               if (matcher.isMatch(featureProject.getId()))
               {
                  handler.addFeatureInclude(toFeatureInclude(featureProject));
               }
            }
         }
         else if (mode == AggregatorMode.UNWRAP)
         {
            final PathMatcher featureMatcher = converter.getFeatureMatcherForAssembly(moduleProperties, assemblyName);
            final PathMatcher pluginMatcher = converter.getPluginMatcherForAssembly(moduleProperties, assemblyName);
            for (FeatureProject featureProject : resolutionContext)
            {
               final boolean isFacetFeature = B2MetadataUtils.getFacetName(featureProject) != null;
               if (isFacetFeature)
               {
                  if (featureMatcher.isMatch(featureProject.getId()))
                  {
                     handler.addFeatureInclude(toFeatureInclude(featureProject));
                  }
               }
               else
               {

                  for (FeatureInclude featureInclude : featureProject.getIncludedFeatures())
                  {
                     if (featureMatcher.isMatch(featureInclude.getId()))
                     {
                        handler.addFeatureInclude(EcoreUtil.copy(featureInclude));
                     }
                  }

                  for (PluginInclude pluginInclude : featureProject.getIncludedPlugins())
                  {
                     if (pluginMatcher.isMatch(pluginInclude.getId()))
                     {
                        handler.addPluginInclude(pluginInclude);
                     }
                  }
               }

               // TODO requirements?
            }
         }
      }
   }

   public static FeatureInclude toFeatureInclude(FeatureProject featureProject)
   {
      FeatureInclude featureInclude = ModuleModelFactory.eINSTANCE.createFeatureInclude();
      featureInclude.setId(featureProject.getId());
      featureInclude.setVersion(featureProject.getVersion());

      Annotation b2Metadata = B2MetadataUtils.getB2Metadata(featureProject);
      if (b2Metadata != null)
      {
         featureInclude.getAnnotations().add(EcoreUtil.copy(b2Metadata));
      }

      return featureInclude;
   }

   public static PluginInclude toPluginInclude(PluginProject pluginProject)
   {
      PluginInclude featureInclude = ModuleModelFactory.eINSTANCE.createPluginInclude();
      featureInclude.setId(pluginProject.getId());
      featureInclude.setVersion(pluginProject.getVersion());

      Annotation b2Metadata = B2MetadataUtils.getB2Metadata(pluginProject);
      if (b2Metadata != null)
      {
         featureInclude.getAnnotations().add(EcoreUtil.copy(b2Metadata));
      }

      PluginsFacet facet = pluginProject.getParent();
      AbstractModule module = facet.getParent();
      B2MetadataUtils.setModuleId(pluginProject, module.getId());
      B2MetadataUtils.setModuleVersion(pluginProject, module.getVersion());
      B2MetadataUtils.setFacetName(pluginProject, facet.getName());

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
      return result;
   }

   private static FeatureProject findFeatureProjectForAssembly(AbstractModule module, String assemblyName)
   {
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         for (FeatureProject featureProject : featuresFacet.getProjects())
         {
            final Set<String> assemblyNames = B2MetadataUtils.getAssemblyNames(featureProject);
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
      final String facetName = B2MetadataUtils.getFacetName(facetFeatrue);
      final PluginsFacet sourcePlugins = module.getFacetByName(facetName);
      final boolean isSource = B2MetadataUtils.isSourceFeature(facetFeatrue);

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
            if (facetName.equals(B2MetadataUtils.getFacetName(featureProject)))
            {
               if (isSource == B2MetadataUtils.isSourceFeature(featureProject))
               {
                  return featureProject;
               }
            }
         }
      }
      return null;
   }

}
