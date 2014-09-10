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

package org.sourcepit.b2.directory.parser.internal.facets;

import java.io.File;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.b2.model.module.SitesFacet;

public class StructuredLayoutFacetsParserRuleTest extends AbstractModuleParserTest
{
   public void testBasic() throws Exception
   {
      final StructuredLayoutFacetsParserRule parserRule = lookupParserRule();
      assertNotNull(parserRule);
   }

   public void testNonFacetDir() throws Exception
   {
      final File pluginDir = workspace.importResources("composed-component/structured-layout/plugins/example.ui");
      assertTrue(pluginDir.exists());

      final StructuredLayoutFacetsParserRule parserRule = lookupParserRule();
      assertNull(parserRule.parse(pluginDir, B2ModelBuildingRequest.newDefaultProperties()));
   }

   public void testCompositeComponentDir() throws Exception
   {
      File moduleDir = workspace.importResources("composed-component");
      assertTrue(moduleDir.exists());

      final StructuredLayoutFacetsParserRule parserRule = lookupParserRule();
      assertNull(parserRule.parse(moduleDir, B2ModelBuildingRequest.newDefaultProperties()));
   }

   public void testFacetDir() throws Exception
   {
      final File facetDir = workspace.importResources("composed-component/structured-layout");
      assertTrue(facetDir.exists());

      final StructuredLayoutFacetsParserRule parserRule = lookupParserRule();

      FacetsParseResult<ProjectFacet<? extends Project>> result = parserRule.parse(facetDir,
         B2ModelBuildingRequest.newDefaultProperties());
      assertNotNull(result);

      assertEquals("structured", result.getLayout());

      final List<ProjectFacet<? extends Project>> facets = result.getFacets();
      assertEquals(5, facets.size());

      BasicModule dummyComponent = ModuleModelFactory.eINSTANCE.createBasicModule();
      dummyComponent.setLayoutId(result.getLayout());
      dummyComponent.getFacets().addAll(facets);

      assertStructuredLayout(dummyComponent);
   }

   public void testBug49MixedTestsFacet() throws Exception
   {
      final File moduleDir = workspace.importResources("MixedTestsFacet");
      assertTrue(moduleDir.exists());

      final StructuredLayoutFacetsParserRule parserRule = lookupParserRule();
      final FacetsParseResult<ProjectFacet<? extends Project>> result = parserRule.parse(moduleDir,
         B2ModelBuildingRequest.newDefaultProperties());
      assertNotNull(result);

      final List<ProjectFacet<? extends Project>> facets = result.getFacets();
      assertEquals(2, facets.size());

      final PluginsFacet facet1 = (PluginsFacet) facets.get(0);
      final PluginsFacet facet2 = (PluginsFacet) facets.get(1);

      final File facet1Dir = getFacetDirectory(facet1);

      final PluginsFacet pluginsFacet;
      final PluginsFacet testsFacet;

      if ("plugins".equals(facet1Dir.getName()))
      {
         pluginsFacet = facet1;
         testsFacet = facet2;
      }
      else
      {
         pluginsFacet = facet2;
         testsFacet = facet1;
      }

      assertEquals(1, pluginsFacet.getProjects().size());
      assertEquals(2, testsFacet.getProjects().size());

      PluginProject bundleA = pluginsFacet.getProjects().get(0);
      assertFalse(bundleA.isTestPlugin());

      PluginProject bundleATests = testsFacet.getProjectById("bundle.a.tests");
      assertTrue(bundleATests.isTestPlugin());
      PluginProject bundleTestharness = testsFacet.getProjectById("bundle.testharness");
      assertFalse(bundleTestharness.isTestPlugin());
   }

   private static File getFacetDirectory(final PluginsFacet pluginsFacet)
   {
      final PluginProject pluginProject = pluginsFacet.getProjects().get(0);
      return pluginProject.getDirectory().getParentFile();
   }

   public static void assertStructuredLayout(BasicModule module)
   {
      assertEquals("structured", module.getLayoutId());

      EList<PluginsFacet> pluginsFacets = module.getFacets(PluginsFacet.class);
      assertEquals(3, pluginsFacets.size());

      PluginsFacet pluginsFacet = module.getFacetByName("additional-fragments");
      assertEquals("additional-fragments", pluginsFacet.getName());
      assertEquals(1, pluginsFacet.getProjects().size());

      pluginsFacet = module.getFacetByName("plugins");
      assertEquals("plugins", pluginsFacet.getName());
      assertEquals(1, pluginsFacet.getProjects().size());

      pluginsFacet = module.getFacetByName("tests");
      assertEquals("tests", pluginsFacet.getName());
      assertEquals(2, pluginsFacet.getProjects().size());

      EList<FeaturesFacet> features = module.getFacets(FeaturesFacet.class);
      assertEquals(1, features.size());

      EList<SitesFacet> sites = module.getFacets(SitesFacet.class);
      assertEquals(1, sites.size());
   }

   private StructuredLayoutFacetsParserRule lookupParserRule() throws Exception
   {
      return (StructuredLayoutFacetsParserRule) lookup(AbstractFacetsParserRule.class, "structured");
   }
}
