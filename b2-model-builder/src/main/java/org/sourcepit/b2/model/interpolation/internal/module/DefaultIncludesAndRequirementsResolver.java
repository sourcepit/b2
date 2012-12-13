/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.sourcepit.b2.model.interpolation.internal.module.B2ModelUtils.toFeatureInclude;
import static org.sourcepit.b2.model.interpolation.internal.module.B2ModelUtils.toPluginInclude;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcepit.b2.model.builder.util.BasicConverter.AggregatorMode;
import org.sourcepit.b2.model.builder.util.FeaturesConverter;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.internal.util.EWalkerImpl;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleRequirement;
import org.sourcepit.common.manifest.osgi.PackageExport;
import org.sourcepit.common.manifest.osgi.PackageImport;
import org.sourcepit.common.manifest.osgi.Version;
import org.sourcepit.common.manifest.osgi.VersionRange;
import org.sourcepit.common.utils.collections.FilteredIterable;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

@Named
public class DefaultIncludesAndRequirementsResolver implements IncludesAndRequirementsResolver
{
   private final Logger log = LoggerFactory.getLogger(getClass());

   private final FeaturesConverter converter;

   private final UnpackStrategy unpackStrategy;

   private final ISourceService sourceService;

   private final ResolutionContextResolver resolver;

   @Inject
   public DefaultIncludesAndRequirementsResolver(FeaturesConverter converter, UnpackStrategy unpackStrategy,
      ISourceService sourceService, ResolutionContextResolver resolver)
   {
      this.converter = converter;
      this.unpackStrategy = unpackStrategy;
      this.sourceService = sourceService;
      this.resolver = resolver;
   }

   public void appendIncludesAndRequirements(PropertiesSource moduleProperties, AbstractModule module,
      FeatureProject assemblyFeature, String assemblyName)
   {
      final IncludesAndRequirementsHandler handler = new IncludesAndRequirementsHandler(assemblyFeature);

      final EWalkerImpl eWalker;
      eWalker = createIncludesAppenderForAssembly(moduleProperties, handler, assemblyName);
      eWalker.walk(module.getFacets(FeaturesFacet.class));
      eWalker.walk(module.getFacets(PluginsFacet.class));

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

   private static boolean isAssemblyFeatureIdMatch(PathMatcher matcher, Annotatable featureProjectOrInclude)
   {
      for (String featureIds : B2MetadataUtils.getReplacedFeatureIds(featureProjectOrInclude))
      {
         if (matcher.isMatch(featureIds))
         {
            return true;
         }
      }
      return false;
   }

   private void aggregateForeignFeatures(IncludesAndRequirementsHandler handler, PropertiesSource moduleProperties,
      AbstractModule module, FeatureProject assemblyFeature, String assemblyName)
   {
      final AggregatorMode mode = getAggregatorMode(moduleProperties, module, assemblyName);
      if (mode == AggregatorMode.OFF)
      {
         return;
      }

      final boolean includeForeignModules = isIncludeForeignModules(moduleProperties, module, assemblyName);

      final Set<FeatureProject> allFeatures = new LinkedHashSet<FeatureProject>();
      final Set<FeatureProject> visibleAssemblyFeatures = new LinkedHashSet<FeatureProject>();
      resolveIncludeResolutionContext(allFeatures, visibleAssemblyFeatures, module, assemblyFeature,
         includeForeignModules);

      if (mode == AggregatorMode.AGGREGATE)
      {
         final PathMatcher matcher = converter.getAggregatorFeatureMatcherForAssembly(moduleProperties, assemblyName);
         for (FeatureProject featureProject : allFeatures)
         {
            if (isAssemblyFeatureIdMatch(matcher, featureProject))
            {
               if (visibleAssemblyFeatures.contains(featureProject))
               {
                  handler.addFeatureInclude(toFeatureInclude(featureProject));
               }
               else
               {
                  StringBuilder sb = new StringBuilder();

                  sb.append("Cannot include feature '");
                  sb.append(featureProject.getId());
                  sb.append("' into assembly '");
                  sb.append(assemblyFeature.getId());
                  sb.append("' because it is out of scope. You have to require on of the following assemblies of module '");
                  sb.append(featureProject.getParent().getParent().getId());
                  sb.append("': ");
                  Iterator<String> assemblyNames = B2MetadataUtils.getAssemblyNames(assemblyFeature).iterator();
                  Iterator<String> assemblyClassifiers = B2MetadataUtils.getAssemblyClassifiers(assemblyFeature)
                     .iterator();

                  while (assemblyNames.hasNext())
                  {
                     String name = assemblyNames.next();
                     String classifier = assemblyClassifiers.next();
                     sb.append(name);
                     if (!name.equals(classifier))
                     {
                        sb.append(" (classifier='");
                        sb.append(classifier);
                        sb.append("')");
                     }
                     sb.append(", ");
                  }

                  sb.deleteCharAt(sb.length() - 1);
                  sb.deleteCharAt(sb.length() - 1);

                  log.warn(sb.toString());
               }
            }
         }
      }
      else if (mode == AggregatorMode.UNWRAP)
      {
         final PathMatcher featureMatcher = converter.getFeatureMatcherForAssembly(moduleProperties, assemblyName);
         final PathMatcher pluginMatcher = converter.getPluginMatcherForAssembly(moduleProperties, assemblyName);
         for (FeatureProject featureProject : visibleAssemblyFeatures)
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
                     handler.addPluginInclude(EcoreUtil.copy(pluginInclude));
                  }
               }
            }
            // TODO requirements?
         }
      }
   }

   private AggregatorMode getAggregatorMode(PropertiesSource moduleProperties, AbstractModule module,
      String assemblyName)
   {
      AggregatorMode mode = converter.getAggregatorMode(moduleProperties, assemblyName);
      if (mode == AggregatorMode.OFF && module instanceof CompositeModule)
      {
         mode = AggregatorMode.UNWRAP;
      }
      return mode;
   }

   private boolean isIncludeForeignModules(PropertiesSource moduleProperties, AbstractModule module, String assemblyName)
   {
      final AggregatorMode mode = converter.getAggregatorMode(moduleProperties, assemblyName);
      if (mode == AggregatorMode.OFF && module instanceof CompositeModule)
      {
         return false;
      }
      return true;
   }

   private void resolveIncludeResolutionContext(Set<FeatureProject> assemblyFeatures,
      Set<FeatureProject> visibleAssemblyFeatures, AbstractModule module, FeatureProject assemblyFeature,
      boolean includeForeignModules)
   {
      final SetMultimap<AbstractModule, FeatureProject> moduleToAssemblies = LinkedHashMultimap.create();

      if (module instanceof CompositeModule)
      {
         determineCompositeResolutionContext((CompositeModule) module, moduleToAssemblies);
      }

      if (includeForeignModules)
      {
         final boolean scopeTest = B2MetadataUtils.isTestFeature(assemblyFeature);
         moduleToAssemblies.putAll(resolver.resolveResolutionContext(module, scopeTest));
      }

      for (Entry<AbstractModule, Collection<FeatureProject>> entry : moduleToAssemblies.asMap().entrySet())
      {
         AbstractModule foreignModule = entry.getKey();

         if (foreignModule.equals(module))
         {
            throw new IllegalStateException();
         }

         for (FeaturesFacet featuresFacet : foreignModule.getFacets(FeaturesFacet.class))
         {
            for (FeatureProject featureProject : featuresFacet.getProjects())
            {
               assemblyFeatures.add(featureProject);
            }
         }

         visibleAssemblyFeatures.addAll(entry.getValue());
      }
   }

   private void determineCompositeResolutionContext(CompositeModule module,
      SetMultimap<AbstractModule, FeatureProject> moduleToAssemblies)
   {
      for (AbstractModule abstractModule : module.getModules())
      {
         for (FeaturesFacet featuresFacet : abstractModule.getFacets(FeaturesFacet.class))
         {
            for (FeatureProject featureProject : featuresFacet.getProjects())
            {
               if (!B2MetadataUtils.getAssemblyNames(featureProject).isEmpty())
               {
                  moduleToAssemblies.get(abstractModule).add(featureProject);
               }
            }
         }
      }
   }

   // TODO move to util and test it
   public static FeatureProject findFeatureProjectForAssembly(AbstractModule module, String assemblyName)
   {
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         for (FeatureProject featureProject : featuresFacet.getProjects())
         {
            final List<String> assemblyNames = B2MetadataUtils.getAssemblyNames(featureProject);
            if (assemblyNames.contains(assemblyName))
            {
               return featureProject;
            }
         }
      }
      return null;
   }

   public void appendIncludesAndRequirements(PropertiesSource moduleProperties, AbstractModule module,
      PluginsFacet pluginsFacet, FeatureProject facetFeatrue)
   {
      final String facetName = B2MetadataUtils.getFacetName(facetFeatrue);
      final PluginsFacet sourcePlugins = module.getFacetByName(facetName);
      final boolean isSource = B2MetadataUtils.isSourceFeature(facetFeatrue);

      final IncludesAndRequirementsHandler handler = new IncludesAndRequirementsHandler(facetFeatrue);

      // includes from module structure (plugin includes and assembly includes)
      final IncludesAppender includesAppender;
      includesAppender = createIncludesAppenderForFacet(moduleProperties, isSource, handler, facetName);
      includesAppender.walk(pluginsFacet.getProjects());

      if (!isSource)
      {
         final Iterable<PluginsFacet> resolutionContext = resolveRequirementResolutionContext(module, facetFeatrue,
            sourcePlugins);

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

            handler.addFeatureRequirement(featureRequirement);
         }
      }

      // custom includes and requirements
      final List<FeatureInclude> includedFeatures;
      includedFeatures = converter.getIncludedFeaturesForFacet(moduleProperties, facetName, isSource);
      handler.addFeatureIncludes(includedFeatures);

      final List<PluginInclude> includedPlugins;
      includedPlugins = converter.getIncludedPluginsForFacet(moduleProperties, facetName, isSource);
      handler.addPluginIncludes(includedPlugins);

      final List<RuledReference> requiredFeatures;
      requiredFeatures = converter.getRequiredFeaturesForFacet(moduleProperties, facetName, isSource);
      handler.addFeatureRequirements(requiredFeatures);

      final List<RuledReference> requiredPlugins;
      requiredPlugins = converter.getRequiredPluginsForFacet(moduleProperties, facetName, isSource);
      handler.addPluginRequirements(requiredPlugins);
   }


   private Iterable<PluginsFacet> resolveRequirementResolutionContext(AbstractModule module,
      FeatureProject facetFeature, PluginsFacet sourcePlugins)
   {
      Collection<PluginsFacet> result = new LinkedHashSet<PluginsFacet>();
      result.addAll(sourcePlugins.getParent().getFacets(PluginsFacet.class));

      final boolean scopeTest = B2MetadataUtils.isTestFeature(facetFeature);

      final SetMultimap<AbstractModule, FeatureProject> moduleToAssemblies;
      moduleToAssemblies = resolver.resolveResolutionContext(sourcePlugins.getParent(), scopeTest);

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
         if (sourceManifest != null && targetManifest != null && isRequiresBundle(sourceManifest, targetManifest))
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

   private IncludesAppender createIncludesAppenderForAssembly(final PropertiesSource moduleProperties,
      IncludesAndRequirementsHandler featureProject, final String assemblyName)
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

   private IncludesAppender createIncludesAppenderForFacet(final PropertiesSource moduleProperties, boolean isSource,
      final IncludesAndRequirementsHandler featureProject, final String facetName)
   {
      final IncludesAppender includesAppender;
      includesAppender = new IncludesAppender(unpackStrategy, featureProject, isSource)
      {
         @Override
         protected String getSourcePluginId(PluginProject pp)
         {
            if (sourceService.isSourceBuildEnabled(pp, moduleProperties))
            {
               return converter.getSourcePluginId(moduleProperties, pp.getId());
            }
            return null;
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
         if (addUnique(featureProject.getIncludedFeatures(), include))
         {
            processB2Metadata(include);
            removeAllWithId(featureProject.getRequiredFeatures(), include.getId());
         }
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
         if (addUnique(featureProject.getIncludedPlugins(), include))
         {
            processB2Metadata(include);
            removeAllWithId(featureProject.getRequiredPlugins(), include.getId());
         }
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
            if (addUnique(featureProject.getRequiredFeatures(), requirement))
            {
               processB2Metadata(requirement);
            }
         }
      }

      private void processB2Metadata(AbstractReference ref)
      {
         if (B2MetadataUtils.isTestFeature(ref))
         {
            B2MetadataUtils.setTestFeature(featureProject, true);
         }
         if (B2MetadataUtils.isTestPlugin(ref))
         {
            B2MetadataUtils.setTestFeature(featureProject, true);
         }
         if (B2MetadataUtils.isSourceFeature(ref))
         {
            B2MetadataUtils.setSourceFeature(featureProject, true);
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
            if (addUnique(featureProject.getRequiredPlugins(), requirement))
            {
               processB2Metadata(requirement);
            }
         }
      }

      private static <R extends AbstractReference> boolean addUnique(Collection<R> refs, R ref)
      {
         if (findRefById(refs, ref.getId()) == null)
         {
            return refs.add(ref);
         }
         return false;
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

   private static abstract class IncludesAppender extends EWalkerImpl
   {
      private final IncludesAndRequirementsHandler targetProject;
      private final PathMatcher featureMatcher;
      private final PathMatcher pluginMatcher;
      private final UnpackStrategy unpackStrategy;
      private final boolean isSource;

      private IncludesAppender(UnpackStrategy unpackStrategy, IncludesAndRequirementsHandler targetProject,
         boolean isSource)
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
            final FeatureInclude inc = toFeatureInclude(fp);
            targetProject.addFeatureInclude(inc);
         }
      }

      private void process(PluginProject pp)
      {
         final String pluginId = isSource ? getSourcePluginId(pp) : pp.getId();
         if (pluginId != null && pluginMatcher.isMatch(pluginId))
         {
            final PluginInclude inc = toPluginInclude(pp);
            inc.setId(pluginId);
            if (isSource)
            {
               inc.setUnpack(false);
            }
            else
            {
               inc.setUnpack(unpackStrategy.isUnpack(pp));
            }
            targetProject.addPluginInclude(inc);
         }
      }

      protected abstract String getSourcePluginId(PluginProject pp);

      protected abstract PathMatcher createPluginMatcher();

      protected abstract PathMatcher createFeatureMatcher();
   }
}
