/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;

import org.apache.maven.model.Model;
import org.sourcepit.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.PluginsFacet;

public class PomGeneratorTest extends AbstractPomGeneratorTest
{
   @Override
   protected String setUpModulePath()
   {
      return "composed-component/simple-layout";
   }
   
   public void testArtifactId() throws Exception
   {
      BasicModule module = buildModel(getCurrentModuleDir());
      assertNotNull(module);
      assertNoPomFiles(module.getDirectory());
      
      // Improvement #56
      assertEquals("org.sourcepit.b2.test.resources.simple.layout", module.getId());
      
      generatePom(module, null);
      
      File pomFile = new File(module.getDirectory(), "pom.xml");
      assertTrue(pomFile.exists());
      
      Model pom = readMavenModel(pomFile);
      assertEquals("simple-layout", pom.getArtifactId());
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
