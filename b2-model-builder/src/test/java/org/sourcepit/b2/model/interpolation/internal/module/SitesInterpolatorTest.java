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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addPluginProject;
import static org.sourcepit.b2.model.harness.ModelTestHarness.assertIdentifiable;
import static org.sourcepit.b2.model.harness.ModelTestHarness.assertReference;
import static org.sourcepit.b2.model.harness.ModelTestHarness.createBasicModule;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;
import org.sourcepit.b2.model.builder.util.SitesConverter;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class SitesInterpolatorTest extends AbstractInterpolatorUseCasesTest {

   @Override
   protected void interpolate(AbstractModule module, PropertiesMap moduleProperties) {
      super.interpolate(module, moduleProperties);

      new SitesInterpolator(lookup(SitesConverter.class), lookup(LayoutManager.class)).interpolate(module,
         moduleProperties);
   }

   @Override
   protected void assertUC_1_SinglePlugin_NoSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(1, sitesFacet.getProjects().size());

      SiteProject siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());

      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_1_SinglePlugin_WithSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(1, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());

      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());

      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_2_PluginsAndTests_NoSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_2_PluginsAndTests_WithSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_3_EraseFacetClassifier_NoSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());

      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
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
      assertEquals("assembled", category.getName());

      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_3_EraseFacetClassifier_WithSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_4_CustomAssemblies_PublicSdkTest_NoSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
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
      assertEquals("assembled", category.getName());

      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_4_CustomAssemblies_PublicSdkTest_WithSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_NoSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_5_AdditionalFacet_Doc_EraseAssemblyClassifier_WithSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(4, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.doc.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(2);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(3);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_6_InterFacetRequirements_NoSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_6_InterFacetRequirements_WithSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(4, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.doc.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.doc.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("doc", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(2);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(3);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_6_InterModuleFacetRequirements_NoSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("bar.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/bar.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("bar.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_NoSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.public.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("bar.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_WithSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.public.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(4, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(2);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(3);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(4, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(2);
      assertReference("bar.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(3);
      assertReference("bar.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeUnwrap_WithSource_Public_Sdk(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.public.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("bar.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));


      siteProject = sitesFacet.getProjects().get(1);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.test.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.test.site"), siteProject.getDirectory());
      assertEquals(2, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(4, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(2);
      assertReference("bar.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(3);
      assertReference("bar.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeAggregate_NoSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.public.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("bar.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_7_AggregateContentOfOtherModule_ModeAggregate_WithSource(BasicModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(3, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.public.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.public.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.public.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[public]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.sdk.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[sdk]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(3, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(2);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(3, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(2);
      assertReference("bar.test.feature", "1.0.0.qualifier", reference);
      assertEquals("bar", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

   }

   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_NoSource(CompositeModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("core.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("core.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("ui.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_ModeAggregate_NoSource(CompositeModule module) {
      assertUC_8_AggregateContentOfCompositeModule_NoSource(module);
   }

   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_WithSource(CompositeModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(4, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("core.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("core.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(2);
      assertReference("ui.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(3);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(4, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("core.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("core.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(2);
      assertReference("ui.tests.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(3);
      assertReference("ui.tests.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertEquals("tests", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Override
   protected void assertUC_8_AggregateContentOfCompositeModule_ModeAggregate_WithSource(CompositeModule module) {
      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("core.main.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
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
      assertEquals("assembled", category.getName());
      assertEquals(1, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("foo.test.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      category = siteProject.getCategories().get(1);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      reference = category.getInstallableUnits().get(0);
      assertReference("core.test.feature", "1.0.0.qualifier", reference);
      assertEquals("core", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("ui.test.feature", "1.0.0.qualifier", reference);
      assertEquals("ui", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[test]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertTrue(B2MetadataUtils.isTestFeature(reference));
   }

   @Test
   public void testEnsureAllFeaturesAreIncludedEvenWithoutCategory() throws Exception {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "true"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.main.categories", "included");

      // interpolate
      interpolate(module, moduleProperties);

      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(1, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      // not categorized
      assertEquals(1, siteProject.getInstallableUnits().size());
      reference = siteProject.getInstallableUnits().get(0);
      assertReference("foo.main.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertEquals("[main]", B2MetadataUtils.getAssemblyNames(reference).toString());
      assertNull(B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));
   }

   @Test
   public void testFeatureErasure() throws Exception {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      addPluginProject(module, "plugins", "foo.plugin", "1.0.0.qualifier");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "true"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.main.categories", "included");

      moduleProperties.put("b2.assemblies.main.siteFeaturesFilter", "!**.main.**");

      // interpolate
      interpolate(module, moduleProperties);

      EList<SitesFacet> siteFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, siteFacets.size());

      SitesFacet sitesFacet = siteFacets.get(0);
      assertEquals(1, sitesFacet.getProjects().size());

      SiteProject siteProject;
      siteProject = sitesFacet.getProjects().get(0);
      assertTrue(siteProject.isDerived());
      assertIdentifiable("foo.main.site", "1.0.0.qualifier", siteProject);
      assertEquals(new File(".b2/sites/foo.main.site"), siteProject.getDirectory());

      assertEquals(1, siteProject.getCategories().size());

      Category category;
      category = siteProject.getCategories().get(0);
      assertEquals("included", category.getName());
      assertEquals(2, category.getInstallableUnits().size());

      AbstractStrictReference reference;
      reference = category.getInstallableUnits().get(0);
      assertReference("foo.plugins.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertFalse(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      reference = category.getInstallableUnits().get(1);
      assertReference("foo.plugins.sources.feature", "1.0.0.qualifier", reference);
      assertEquals("foo", B2MetadataUtils.getModuleId(reference));
      assertEquals("1.0.0.qualifier", B2MetadataUtils.getModuleVersion(reference));
      assertTrue(B2MetadataUtils.getAssemblyNames(reference).isEmpty());
      assertEquals("plugins", B2MetadataUtils.getFacetName(reference));
      assertTrue(B2MetadataUtils.isSourceFeature(reference));
      assertFalse(B2MetadataUtils.isTestFeature(reference));

      // erased
      assertEquals(0, siteProject.getInstallableUnits().size());
   }

   @Test
   public void testProduct() throws Exception {
      // setup
      BasicModule module = createBasicModule("foo");
      module.setDirectory(new File(""));

      PluginProject pluginProject = addPluginProject(module, "plugins", "plugin.foo", "1.0.0.qualifier");

      String facetName = "products";

      ProductDefinition productDefinition = addProductDefinition(module, facetName, pluginProject.getId() + ".product",
         pluginProject);
      B2MetadataUtils.addAssemblyName(productDefinition, "main");
      B2MetadataUtils.addAssemblyClassifier(productDefinition, "");

      productDefinition = addProductDefinition(module, facetName, pluginProject.getId() + ".foo.product", pluginProject);
      B2MetadataUtils.addAssemblyName(productDefinition, "test");
      B2MetadataUtils.addAssemblyClassifier(productDefinition, "test");

      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("build.sources", "true"); // true is default
      moduleProperties.put("b2.assemblies", "main, test"); // should be default?
      moduleProperties.put("b2.assemblies.main.classifier", ""); // should be default?
      moduleProperties.put("b2.assemblies.main.featuresFilter", "!**.tests.**"); // should be default?
      moduleProperties.put("b2.assemblies.test.featuresFilter", "**.tests.**"); // should be default?

      moduleProperties.put("b2.assemblies.main.siteFeaturesFilter", "!**.main.**");

      // interpolate
      interpolate(module, moduleProperties);

      EList<SitesFacet> sitesFacets = module.getFacets(SitesFacet.class);
      assertEquals(1, sitesFacets.size());

      SitesFacet sitesFacet = sitesFacets.get(0);
      assertEquals(2, sitesFacet.getProjects().size());

      SiteProject siteProject = sitesFacet.getProjects().get(0);
      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(2, siteProject.getCategories().size());

      Category category = siteProject.getCategories().get(0);
      assertEquals(1, category.getInstallableUnits().size());

      category = siteProject.getCategories().get(1);
      assertEquals(3, category.getInstallableUnits().size());
      assertTrue(category.getInstallableUnits().get(0) instanceof FeatureInclude);
      assertTrue(category.getInstallableUnits().get(1) instanceof FeatureInclude);
      assertTrue(category.getInstallableUnits().get(2) instanceof StrictReference);

      AbstractStrictReference productIU = category.getInstallableUnits().get(2);
      assertEquals("plugin.foo.product", productIU.getId());
      assertEquals("1.0.0.qualifier", productIU.getVersion());

      siteProject = sitesFacet.getProjects().get(1);
      assertEquals(0, siteProject.getInstallableUnits().size());
      assertEquals(1, siteProject.getCategories().size());

      category = siteProject.getCategories().get(0);
      assertEquals(1, category.getInstallableUnits().size());
      assertTrue(category.getInstallableUnits().get(0) instanceof StrictReference);
   }

   private static ProductDefinition addProductDefinition(BasicModule module, String facetName, String productId,
      PluginProject pluginProject) {
      final StrictReference productPlugin = ModuleModelFactory.eINSTANCE.createStrictReference();
      productPlugin.setId(pluginProject.getId());
      productPlugin.setVersion(pluginProject.getVersion());

      final ProductDefinition productDefinition = ModuleModelFactory.eINSTANCE.createProductDefinition();
      productDefinition.setAnnotationData("product", "uid", productId);
      productDefinition.setAnnotationData("product", "version", pluginProject.getVersion());
      productDefinition.setProductPlugin(productPlugin);

      ProductsFacet productsFacet = module.getFacetByName(facetName);
      if (productsFacet == null) {
         productsFacet = createProductsFacet(facetName);
         module.getFacets().add(productsFacet);
      }

      productsFacet.getProductDefinitions().add(productDefinition);

      return productDefinition;
   }

   private static ProductsFacet createProductsFacet(String name) {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;
      final ProductsFacet productsFacet = eFactory.createProductsFacet();
      productsFacet.setName(name);
      return productsFacet;
   }
}
