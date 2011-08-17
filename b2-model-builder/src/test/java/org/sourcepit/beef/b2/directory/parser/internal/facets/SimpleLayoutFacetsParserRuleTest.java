/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.internal.facets;

import java.io.File;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.beef.b2.internal.model.B2ModelFactory;
import org.sourcepit.beef.b2.internal.model.BasicModule;
import org.sourcepit.beef.b2.internal.model.FeaturesFacet;
import org.sourcepit.beef.b2.internal.model.PluginsFacet;
import org.sourcepit.beef.b2.internal.model.Project;
import org.sourcepit.beef.b2.internal.model.ProjectFacet;
import org.sourcepit.beef.b2.internal.model.SitesFacet;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.ConverterUtils;

public class SimpleLayoutFacetsParserRuleTest extends AbstractModuleParserTest
{
   public void testBasic() throws Exception
   {
      final SimpleLayoutFacetsParserRule parserRule = lookupSimpleLayoutFacetsParserRule();
      assertNotNull(parserRule);
   }

   public void testNonFacetDir() throws Exception
   {
      final File pluginDir = workspace.importResources("composed-component/simple-layout/example.core");
      assertTrue(pluginDir.exists());

      final SimpleLayoutFacetsParserRule parserRule = lookupSimpleLayoutFacetsParserRule();
      assertNull(parserRule.parse(pluginDir, ConverterUtils.TEST_CONVERTER));
   }

   public void testFacetDir() throws Exception
   {
      final File facetDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(facetDir.exists());

      final SimpleLayoutFacetsParserRule parserRule = lookupSimpleLayoutFacetsParserRule();

      final FacetsParseResult<ProjectFacet<? extends Project>> result = parserRule.parse(facetDir,
         ConverterUtils.TEST_CONVERTER);
      assertNotNull(result);

      assertEquals("simple", result.getLayout());

      final List<ProjectFacet<? extends Project>> facets = result.getFacets();
      assertEquals(4, facets.size());

      BasicModule dummyComponent = B2ModelFactory.eINSTANCE.createBasicModule();
      dummyComponent.setLayoutId(result.getLayout());
      dummyComponent.getFacets().addAll(facets);

      assertSimpleLayout(dummyComponent);
   }

   public static void assertSimpleLayout(BasicModule module)
   {
      assertEquals("simple", module.getLayoutId());

      EList<PluginsFacet> pluginsFacets = module.getFacets(PluginsFacet.class);
      assertEquals(2, pluginsFacets.size());

      PluginsFacet pluginsFacet = pluginsFacets.get(0);
      assertEquals("plugins", pluginsFacet.getName());
      assertEquals(2, pluginsFacet.getProjects().size());

      pluginsFacet = pluginsFacets.get(1);
      assertEquals("tests", pluginsFacet.getName());
      assertEquals(2, pluginsFacet.getProjects().size());

      EList<FeaturesFacet> features = module.getFacets(FeaturesFacet.class);
      assertEquals(1, features.size());

      EList<SitesFacet> sites = module.getFacets(SitesFacet.class);
      assertEquals(1, sites.size());
   }

   public void testCompositeComponentDir() throws Exception
   {
      File moduleDir = workspace.importResources("composed-component");
      assertTrue(moduleDir.exists());

      final SimpleLayoutFacetsParserRule parserRule = lookupSimpleLayoutFacetsParserRule();
      assertNull(parserRule.parse(moduleDir, ConverterUtils.TEST_CONVERTER));
   }

   private SimpleLayoutFacetsParserRule lookupSimpleLayoutFacetsParserRule() throws Exception
   {
      return (SimpleLayoutFacetsParserRule) lookup(AbstractFacetsParserRule.class, "simple");
   }
}
