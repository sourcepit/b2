/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;

import org.sourcepit.beef.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.beef.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.PluginsFacet;

public class PomGeneratorTest extends AbstractPomGeneratorTest
{
   @Override
   protected String setUpModulePath()
   {
      return "composed-component/simple-layout";
   }

   public void testSkipFacets() throws Exception
   {
      BasicModule module = buildModel(getCurrentModuleDir());
      assertNotNull(module);
      assertNoPomFiles(module.getDirectory());

      PluginsFacet facet = module.getFacets(PluginsFacet.class).get(0);
      assertNotNull(facet);
      
      assertIsGeneratorInput(facet);
      generatePom(facet, null);

      IInterpolationLayout layout = getLayout(module);
      File facetPom = new File(layout.pathOfFacetMetaData(module, facet.getName(), "pom.xml"));

      // do not generate facet pom as default
      assertFalse(facetPom.exists());

      PropertiesMap properties = new LinkedPropertiesMap();
      properties.setBoolean("b2.pomGenerator.skipFacets", false);
      generatePom(facet, properties);

      assertTrue(facetPom.exists());
   }
}
