/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.b2.model.harness.ModelTestHarness.assertIdentifiable;
import static org.sourcepit.b2.model.harness.ModelTestHarness.assertReference;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.builder.util.SitesConverter;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.props.PropertiesMap;

public class SitesInterpolatorTest extends AbstractInterpolatorUseCasesTest
{

   @Override
   protected void interpolate(AbstractModule module, PropertiesMap moduleProperties)
   {
      super.interpolate(module, moduleProperties);

      new SitesInterpolator(gLookup(SitesConverter.class), gLookup(LayoutManager.class)).interpolate(module,
         moduleProperties);
   }

   @Override
   protected void assertUC_1_SinglePlugin_NoSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(1, sitesFacet.getProjects().size());

      SiteProject siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());

      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_1_SinglePlugin_WithSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(1, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());

      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());

      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_2_PluginsAndTests_NoSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(1, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_2_PluginsAndTests_WithSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_3_EraseFacetClassifier_NoSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());

      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());

      assertEquals(1, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());

      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_3_EraseFacetClassifier_WithSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());

      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_4_CustomAssemblies_PublicSdkTest_NoSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());

      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public, sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.sdk.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.sdk.site"), siteProject.getDirectory());
      assertEquals(1, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public, sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(2);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(1, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());

      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_4_CustomAssemblies_PublicSdkTest_WithSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());

      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.sdk.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.sdk.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(2);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_NoSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(1, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_WithSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(4, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.doc.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(2);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(3);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_6_InterFacetRequirements_NoSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(1, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_6_InterFacetRequirements_WithSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(4, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.doc.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(2);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(3);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_6_InterModuleFacetRequirements_NoSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("bar.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/bar.main.site"), siteProject.getDirectory());

      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("bar.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/bar.test.site"), siteProject.getDirectory());
      assertEquals(1, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("bar.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_NoSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.public.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public, sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.sdk.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.sdk.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public, sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(2);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("bar.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_WithSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.public.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.sdk.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.sdk.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(4, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(2);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(3);
      assertReference("bar.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(2);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(4, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(2);
      assertReference("bar.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(3);
      assertReference("bar.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeAggregate_NoSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.public.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public, sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.sdk.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.sdk.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public, sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(2);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("bar.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeAggregate_WithSource(BasicModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.public.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.sdk.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.sdk.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(3, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(2);
      assertReference("bar.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(2);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(3, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(2);
      assertReference("bar.test.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_NoSource(CompositeModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("core.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("ui.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("core.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("ui.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }
   
   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_ModeAggregate_NoSource(CompositeModule module)
   {
      assertUC_8_AggregateContentOfCompositeModule_NoSource(module);
   }

   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_WithSource(CompositeModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(4, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("core.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("core.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(2);
      assertReference("ui.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(3);
      assertReference("ui.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(4, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("core.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("core.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(2);
      assertReference("ui.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(3);
      assertReference("ui.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_ModeAggregate_WithSource(CompositeModule module)
   {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      StrictReference reference;
      reference = category.getFeatureReferences().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("core.main.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("ui.main.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembly", category.getName());
      assertEquals(1, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("includes", category.getName());
      assertEquals(2, category.getFeatureReferences().size());

      reference = category.getFeatureReferences().get(0);
      assertReference("core.test.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getFeatureReferences().get(1);
      assertReference("ui.test.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }
}
