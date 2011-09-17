/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.facets;

import java.io.File;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.FeaturesFacet;
import org.sourcepit.beef.b2.model.module.ModuleFactory;
import org.sourcepit.beef.b2.model.module.PluginsFacet;
import org.sourcepit.beef.b2.model.module.Project;
import org.sourcepit.beef.b2.model.module.ProjectFacet;
import org.sourcepit.beef.b2.model.module.SitesFacet;

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
      assertNull(parserRule.parse(pluginDir, ConverterUtils.TEST_CONVERTER));
   }

   public void testCompositeComponentDir() throws Exception
   {
      File moduleDir = workspace.importResources("composed-component");
      assertTrue(moduleDir.exists());

      final StructuredLayoutFacetsParserRule parserRule = lookupParserRule();
      assertNull(parserRule.parse(moduleDir, ConverterUtils.TEST_CONVERTER));
   }

   public void testFacetDir() throws Exception
   {
      final File facetDir = workspace.importResources("composed-component/structured-layout");
      assertTrue(facetDir.exists());

      final StructuredLayoutFacetsParserRule parserRule = lookupParserRule();

      FacetsParseResult<ProjectFacet<? extends Project>> result = parserRule.parse(facetDir,
         ConverterUtils.TEST_CONVERTER);
      assertNotNull(result);

      assertEquals("structured", result.getLayout());

      final List<ProjectFacet<? extends Project>> facets = result.getFacets();
      assertEquals(5, facets.size());

      BasicModule dummyComponent = ModuleFactory.eINSTANCE.createBasicModule();
      dummyComponent.setLayoutId(result.getLayout());
      dummyComponent.getFacets().addAll(facets);

      assertStructuredLayout(dummyComponent);
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
