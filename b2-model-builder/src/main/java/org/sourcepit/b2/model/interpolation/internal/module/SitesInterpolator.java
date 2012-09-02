/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.builder.util.SitesConverter;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.Identifiable;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.collections.MultiValueHashMap;
import org.sourcepit.common.utils.collections.MultiValueMap;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class SitesInterpolator
{
   private final SitesConverter converter;

   @Inject
   public SitesInterpolator(SitesConverter converter)
   {
      this.converter = converter;
   }

   public void interpolate(AbstractModule module, PropertiesSource moduleProperties)
   {
      final List<String> assemblyNames = converter.getAssemblyNames(moduleProperties);
      if (!assemblyNames.isEmpty())
      {
         final SitesFacet sitesFacet = ModuleModelFactory.eINSTANCE.createSitesFacet();
         sitesFacet.setDerived(true);
         sitesFacet.setName("sites");
         module.getFacets().add(sitesFacet);

         final Set<String> knownCategoryNames = getThisModulesCategoryNames(module);
         final Map<String, FeatureProject> assemblyFeatures = getAssemblyFeatures(module);
         for (String assemblyName : assemblyNames)
         {
            final SiteProject siteProject = ModuleModelFactory.eINSTANCE.createSiteProject();
            siteProject.setDerived(true);

            final String moduleId = module.getId();
            final String classifier = converter.getAssemblyClassifier(moduleProperties, assemblyName);
            siteProject.setId(converter.getSiteId(moduleProperties, moduleId, classifier));

            // TODO setVersion in featuresinterpolator??
            siteProject.setVersion(module.getVersion());

            final FeatureProject assemblyFeature = assemblyFeatures.get(assemblyName);
            if (assemblyFeature == null)
            {
               throw new IllegalStateException("Unable to find feature for assembly " + assemblyName);
            }
            addCategories(siteProject, assemblyFeature, knownCategoryNames);

            sitesFacet.getProjects().add(siteProject);
         }
      }
   }

   private void addCategories(SiteProject siteProject, FeatureProject assemblyFeature, Set<String> knownCategoryNames)
   {
      final MultiValueMap<String, StrictReference> facetNameToFeatureRefs = new MultiValueHashMap<String, StrictReference>();

      for (FeatureInclude featureInclude : assemblyFeature.getIncludedFeatures())
      {
         String facetName = featureInclude.getAnnotationEntry("b2", "facetName");
         if (facetName == null || !knownCategoryNames.contains(facetName))
         {
            // TODO map unknown features to site categories
            // TODO warn if realy unknown
            facetName = "unknown";
         }
         facetNameToFeatureRefs.get(facetName, true).add(toStrictReference(featureInclude));
      }


      Category category;

      category = ModuleModelFactory.eINSTANCE.createCategory();
      category.setName("assembly-feature");
      category.getFeatureReferences().add(toStrictReference(assemblyFeature));
      siteProject.getCategories().add(category);

      for (Entry<String, Collection<StrictReference>> entry : facetNameToFeatureRefs.entrySet())
      {
         category = ModuleModelFactory.eINSTANCE.createCategory();
         category.setName(entry.getKey());
         category.getFeatureReferences().addAll(entry.getValue());
         siteProject.getCategories().add(category);
      }
   }

   // TODO util class
   private static StrictReference toStrictReference(final Identifiable identifiable)
   {
      final StrictReference inc = ModuleModelFactory.eINSTANCE.createStrictReference();
      inc.setId(identifiable.getId());
      inc.setVersion(identifiable.getVersion());
      return inc;
   }

   // TODO util class
   private static StrictReference toStrictReference(final AbstractStrictReference reference)
   {
      final StrictReference inc = ModuleModelFactory.eINSTANCE.createStrictReference();
      inc.setId(reference.getId());
      inc.setVersion(reference.getVersion());
      return inc;
   }

   private Set<String> getThisModulesCategoryNames(AbstractModule module)
   {
      final Set<String> categoryNames = new LinkedHashSet<String>();
      for (AbstractFacet abstractFacet : module.getFacets())
      {
         if (!abstractFacet.isDerived())
         {
            categoryNames.add(abstractFacet.getName());
         }
      }
      return categoryNames;
   }

   private static Map<String, FeatureProject> getAssemblyFeatures(AbstractModule module)
   {
      final Map<String, FeatureProject> assemblyFeatures = new HashMap<String, FeatureProject>();
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         for (FeatureProject featureProject : featuresFacet.getProjects())
         {
            final List<String> assemblyNames = new ArrayList<String>();
            FeaturesInterpolator.split(assemblyNames, featureProject.getAnnotationEntry("b2", "assemblyNames"));

            for (String assemblyName : assemblyNames)
            {
               if (assemblyFeatures.put(assemblyName, featureProject) != null)
               {
                  throw new IllegalStateException("Assembly with several assembly features detected: " + assemblyName);
               }
            }
         }
      }
      return assemblyFeatures;
   }
}
