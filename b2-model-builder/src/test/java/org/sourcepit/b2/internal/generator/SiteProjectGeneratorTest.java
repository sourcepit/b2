/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addSiteProject;
import static org.sourcepit.b2.model.harness.ModelTestHarness.createBasicModule;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.addAssemblyClassifier;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.addAssemblyName;

import java.io.File;
import java.util.Locale;

import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.nls.NlsUtils;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

import com.google.common.base.Optional;

public class SiteProjectGeneratorTest extends AbstractTestEnvironmentTest
{
   @Test
   public void testSiteProperties()
   {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("b2.module.categories.included.labelAppendix", "(included Features)");
      properties.put("nls_de.b2.module.categories.included.labelAppendix", "(enthaltene Features)");
      properties.put("b2.assemblies.sdk.classifierLabel", "SDK");

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(properties));

      final BasicModule module = createBasicModule("foo");
      module.getLocales().add(NlsUtils.DEFAULT_LOCALE);
      module.getLocales().add(Locale.GERMAN);

      SiteProject siteProject = addSiteProject(module, "sites", "foo.site");
      siteProject.setDirectory(ws.getRoot());

      addFeatureReference(siteProject, "assembled", "foo.sdk.feature");
      addFeatureReference(siteProject, "included", "foo.plugins.feature");
      addFeatureReference(siteProject, "included", "foo.plugins.sources.feature");

      addAssemblyName(siteProject, "sdk");
      addAssemblyClassifier(siteProject, "");

      final SiteProjectGenerator generator = gLookup(SiteProjectGenerator.class);
      generator.generate(siteProject, properties, templates);

      File siteDir = siteProject.getDirectory();
      assertTrue(siteDir.exists());

      PropertiesMap featureProperties;

      // default locale
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(siteDir, "site.properties"));
      assertEquals("Core Plug-ins", featureProperties.get("categories.assembled.name"));
      assertEquals("Core Plug-ins (included Features)", featureProperties.get("categories.included.name"));

      // de
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(siteDir, "site_de.properties"));
      assertEquals("Kern Plug-ins", featureProperties.get("categories.assembled.name"));
      assertEquals("Kern Plug-ins (enthaltene Features)", featureProperties.get("categories.included.name"));
   }

   @Test
   public void testSiteProperties_WithClassifier()
   {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("b2.module.categories.included.labelAppendix", "(included Features)");
      properties.put("nls_de.b2.module.categories.included.labelAppendix", "(enthaltene Features)");
      properties.put("b2.assemblies.sdk.classifierLabel", "SDK");

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(properties));

      final BasicModule module = createBasicModule("foo");
      module.getLocales().add(NlsUtils.DEFAULT_LOCALE);
      module.getLocales().add(Locale.GERMAN);

      SiteProject siteProject = addSiteProject(module, "sites", "foo.site");
      siteProject.setDirectory(ws.getRoot());

      addFeatureReference(siteProject, "assembled", "foo.sdk.feature");
      addFeatureReference(siteProject, "included", "foo.plugins.feature");
      addFeatureReference(siteProject, "included", "foo.plugins.sources.feature");

      addAssemblyName(siteProject, "sdk");
      addAssemblyClassifier(siteProject, "sdk");

      final SiteProjectGenerator generator = gLookup(SiteProjectGenerator.class);
      generator.generate(siteProject, properties, templates);

      File siteDir = siteProject.getDirectory();
      assertTrue(siteDir.exists());

      PropertiesMap featureProperties;

      // default locale
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(siteDir, "site.properties"));
      assertEquals("Core Plug-ins SDK", featureProperties.get("categories.assembled.name"));
      assertEquals("Core Plug-ins SDK (included Features)", featureProperties.get("categories.included.name"));

      // de
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(siteDir, "site_de.properties"));
      assertEquals("Kern Plug-ins SDK", featureProperties.get("categories.assembled.name"));
      assertEquals("Kern Plug-ins SDK (enthaltene Features)", featureProperties.get("categories.included.name"));
   }

   private StrictReference addFeatureReference(SiteProject siteProject, String categoryName, String featureId)
   {
      Category category = getCategory(siteProject, categoryName);
      if (category == null)
      {
         category = createCategory(siteProject, categoryName);
         siteProject.getCategories().add(category);
      }

      StrictReference strictReference = ModuleModelFactory.eINSTANCE.createStrictReference();
      strictReference.setId(featureId);
      strictReference.setVersion(siteProject.getParent().getParent().getVersion());
      category.getFeatureReferences().add(strictReference);

      return strictReference;
   }

   private Category getCategory(SiteProject siteProject, String categoryName)
   {
      for (Category category : siteProject.getCategories())
      {
         if (categoryName.equals(category.getName()))
         {
            return category;
         }
      }
      return null;
   }

   private Category createCategory(SiteProject siteProject, String categoryName)
   {
      Category category = ModuleModelFactory.eINSTANCE.createCategory();
      category.setName(categoryName);
      return category;
   }

}
