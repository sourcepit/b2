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

import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.getAssemblyNames;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sourcepit.b2.model.builder.util.SitesConverter;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.modeling.Annotation;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.common.base.Preconditions;

@Named
public class SitesInterpolator {
   private final SitesConverter converter;

   private final LayoutManager layoutManager;

   @Inject
   public SitesInterpolator(SitesConverter converter, LayoutManager layoutManager) {
      this.converter = converter;
      this.layoutManager = layoutManager;
   }

   public void interpolate(AbstractModule module, PropertiesSource moduleProperties) {
      final List<String> assemblyNames = converter.getAssemblyNames(moduleProperties);
      if (!assemblyNames.isEmpty()) {
         final SitesFacet sitesFacet = ModuleModelFactory.eINSTANCE.createSitesFacet();
         sitesFacet.setDerived(true);
         sitesFacet.setName("sites");

         interpolate(module, sitesFacet, assemblyNames, moduleProperties);

         if (!sitesFacet.getProjects().isEmpty()) {
            module.getFacets().add(sitesFacet);
         }
      }
   }

   private void interpolate(AbstractModule module, final SitesFacet sitesFacet, final List<String> assemblyNames,
      PropertiesSource moduleProperties) {
      for (String assemblyName : assemblyNames) {
         final Collection<AbstractStrictReference> installableUnits = getInstallableUnits(module, assemblyName,
            moduleProperties);
         if (!installableUnits.isEmpty()) {
            final SiteProject siteProject = createSiteProject(module, moduleProperties, sitesFacet, assemblyName);
            sitesFacet.getProjects().add(siteProject);

            final Set<AbstractStrictReference> categorizedIUs = new LinkedHashSet<AbstractStrictReference>();

            for (String categoryName : converter.getAssemblyCategories(moduleProperties, assemblyName)) {
               final Category category = ModuleModelFactory.eINSTANCE.createCategory();
               category.setName(categoryName);

               final PathMatcher matcher = converter.getAssemblyCategoryFeatureMatcher(moduleProperties,
                  module.getId(), assemblyName, categoryName);

               for (AbstractStrictReference iu : installableUnits) {
                  final Set<String> ids = new LinkedHashSet<String>();
                  ids.add(iu.getId());
                  ids.addAll(B2MetadataUtils.getReplacedFeatureIds(iu));

                  if (isMatch(matcher, ids)) {
                     category.getInstallableUnits().add(iu);
                     categorizedIUs.add(iu);
                  }
               }

               if (!category.getInstallableUnits().isEmpty()) {
                  siteProject.getCategories().add(category);
               }
            }

            installableUnits.removeAll(categorizedIUs);

            for (AbstractStrictReference notCategorizedIUs : installableUnits) {
               siteProject.getInstallableUnits().add(notCategorizedIUs);
            }

         }
      }
   }

   public static List<ProductDefinition> getProductDefinitionsForAssembly(AbstractModule module, String assemblyName) {
      final List<ProductDefinition> productDefinitions = new ArrayList<ProductDefinition>();
      for (ProductsFacet productsFacet : module.getFacets(ProductsFacet.class)) {
         for (ProductDefinition productDefinition : productsFacet.getProductDefinitions()) {
            if (getAssemblyNames(productDefinition).contains(assemblyName)) {
               productDefinitions.add(productDefinition);
            }
         }
      }
      return productDefinitions;
   }

   private Collection<AbstractStrictReference> getInstallableUnits(AbstractModule module, String assemblyName,
      PropertiesSource moduleProperties) {
      final Collection<AbstractStrictReference> allIUs = new ArrayList<AbstractStrictReference>();

      final FeatureProject assemblyFeature = DefaultIncludesAndRequirementsResolver.findFeatureProjectForAssembly(
         module, assemblyName);
      if (assemblyFeature != null) {
         allIUs.addAll(getAllFeatures(assemblyName, assemblyFeature, moduleProperties));
      }

      final List<ProductDefinition> productDefinitions = getProductDefinitionsForAssembly(module, assemblyName);
      for (ProductDefinition productDefinition : productDefinitions) {
         allIUs.add(toStrictReference(productDefinition));
      }

      return allIUs;
   }

   private Set<FeatureInclude> getAllFeatures(String assemblyName, FeatureProject assemblyFeature,
      PropertiesSource moduleProperties) {
      final PathMatcher featureFilter = converter.getAssemblySiteFeatureMatcher(moduleProperties, assemblyName);

      final Set<FeatureInclude> allFeatures = new LinkedHashSet<FeatureInclude>();
      if (featureFilter.isMatch(assemblyFeature.getId())) {
         allFeatures.add(B2ModelUtils.toFeatureInclude(assemblyFeature));
      }

      for (FeatureInclude featureInclude : assemblyFeature.getIncludedFeatures()) {
         if (featureFilter.isMatch(featureInclude.getId())) {
            allFeatures.add(EcoreUtil.copy(featureInclude));
         }
      }

      return allFeatures;
   }

   private boolean isMatch(final PathMatcher matcher, final Set<String> featureIds) {
      if (!matcher.getExcludes().isEmpty()) {
         for (String featureId : featureIds) {
            if (matcher.isExclude(featureId)) {
               return false;
            }
         }
      }

      for (String featureId : featureIds) {
         if (matcher.isInclude(featureId)) {
            return true;
         }
      }

      return false;
   }

   private SiteProject createSiteProject(AbstractModule module, PropertiesSource moduleProperties,
      SitesFacet sitesFacet, String assemblyName) {
      final SiteProject siteProject = ModuleModelFactory.eINSTANCE.createSiteProject();
      siteProject.setDerived(true);
      siteProject.setId(converter.getSiteIdForAssembly(moduleProperties, module.getId(), assemblyName));
      siteProject.setVersion(module.getVersion());

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      siteProject.setDirectory(new File(layout.pathOfFacetMetaData(module, sitesFacet.getName(), siteProject.getId())));

      B2MetadataUtils.addAssemblyName(siteProject, assemblyName);
      B2MetadataUtils.addAssemblyClassifier(siteProject,
         converter.getAssemblyClassifier(moduleProperties, assemblyName));

      return siteProject;
   }

   private static StrictReference toStrictReference(ProductDefinition productDefinition) {
      final StrictReference ref = ModuleModelFactory.eINSTANCE.createStrictReference();
      ref.setId(productDefinition.getAnnotationData("product", "uid"));
      ref.setVersion(productDefinition.getAnnotationData("product", "version"));

      Preconditions.checkNotNull(ref.getId());
      Preconditions.checkNotNull(ref.getVersion());

      final Annotation b2Metadata = B2MetadataUtils.getB2Metadata(productDefinition);
      if (b2Metadata != null) {
         ref.getAnnotations().add(EcoreUtil.copy(b2Metadata));
      }

      return ref;
   }
}
