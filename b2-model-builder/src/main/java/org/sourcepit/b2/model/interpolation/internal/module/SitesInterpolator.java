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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sourcepit.b2.model.builder.util.SitesConverter;
import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class SitesInterpolator
{
   private final SitesConverter converter;

   private final LayoutManager layoutManager;

   @Inject
   public SitesInterpolator(SitesConverter converter, LayoutManager layoutManager)
   {
      this.converter = converter;
      this.layoutManager = layoutManager;
   }

   public void interpolate(AbstractModule module, PropertiesSource moduleProperties)
   {
      final List<String> assemblyNames = converter.getAssemblyNames(moduleProperties);
      if (!assemblyNames.isEmpty())
      {
         final SitesFacet sitesFacet = ModuleModelFactory.eINSTANCE.createSitesFacet();
         sitesFacet.setDerived(true);
         sitesFacet.setName("sites");

         interpolate(module, sitesFacet, assemblyNames, moduleProperties);

         if (!sitesFacet.getProjects().isEmpty())
         {
            module.getFacets().add(sitesFacet);
         }
      }
   }

   private void interpolate(AbstractModule module, final SitesFacet sitesFacet, final List<String> assemblyNames,
      PropertiesSource moduleProperties)
   {
      for (String assemblyName : assemblyNames)
      {
         final FeatureProject assemblyFeature = DefaultIncludesAndRequirementsResolver
            .findFeatureProjectForAssembly(module, assemblyName);
         if (assemblyFeature != null)
         {
            interpolate(module, moduleProperties, sitesFacet, assemblyName, assemblyFeature);
         }
      }
   }

   private void interpolate(AbstractModule module, PropertiesSource moduleProperties, SitesFacet sitesFacet,
      String assemblyName, FeatureProject assemblyFeature)
   {
      final SiteProject siteProject = createSiteProject(module, moduleProperties, sitesFacet, assemblyName);
      sitesFacet.getProjects().add(siteProject);

      final Category assembly = ModuleModelFactory.eINSTANCE.createCategory();
      assembly.setName("assembly");
      assembly.getFeatureReferences().add(toStrictReference(assemblyFeature));
      siteProject.getCategories().add(assembly);

      final Category includes = ModuleModelFactory.eINSTANCE.createCategory();
      includes.setName("includes");

      for (FeatureInclude featureInclude : assemblyFeature.getIncludedFeatures())
      {
         includes.getFeatureReferences().add(toStrictReference(featureInclude));
      }

      if (!includes.getFeatureReferences().isEmpty())
      {
         siteProject.getCategories().add(includes);
      }
   }

   private SiteProject createSiteProject(AbstractModule module, PropertiesSource moduleProperties,
      SitesFacet sitesFacet, String assemblyName)
   {
      final SiteProject siteProject = ModuleModelFactory.eINSTANCE.createSiteProject();
      siteProject.setDerived(true);
      siteProject.setId(converter.getSiteId(moduleProperties, module.getId(),
         converter.getAssemblyClassifier(moduleProperties, assemblyName)));
      siteProject.setVersion(module.getVersion());

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      siteProject.setDirectory(new File(layout.pathOfFacetMetaData(module, sitesFacet.getName(), siteProject.getId())));
      
      B2MetadataUtils.addAssemblyName(siteProject, assemblyName);
      B2MetadataUtils.addAssemblyClassifier(siteProject, converter.getAssemblyClassifier(moduleProperties, assemblyName));

      return siteProject;
   }

   private static StrictReference toStrictReference(FeatureProject featureProject)
   {
      final StrictReference ref = ModuleModelFactory.eINSTANCE.createStrictReference();
      ref.setId(featureProject.getId());
      ref.setVersion(featureProject.getVersion());

      final Annotation b2Metadata = B2MetadataUtils.getB2Metadata(featureProject);
      if (b2Metadata != null)
      {
         ref.getAnnotations().add(EcoreUtil.copy(b2Metadata));
      }

      return ref;
   }

   private static StrictReference toStrictReference(FeatureInclude featureInclude)
   {
      final StrictReference ref = ModuleModelFactory.eINSTANCE.createStrictReference();
      ref.setId(featureInclude.getId());
      ref.setVersion(featureInclude.getVersion());

      final Annotation b2Metadata = B2MetadataUtils.getB2Metadata(featureInclude);
      if (b2Metadata != null)
      {
         ref.getAnnotations().add(EcoreUtil.copy(b2Metadata));
      }

      return ref;
   }
}
