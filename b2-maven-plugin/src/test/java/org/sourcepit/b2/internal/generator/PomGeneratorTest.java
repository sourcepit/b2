/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;

import org.apache.maven.model.Model;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class PomGeneratorTest extends AbstractPomGeneratorTest
{
   @Override
   protected String setUpModulePath()
   {
      return "composed-component/simple-layout";
   }

   public void testArtifactId() throws Exception
   {
      BasicModule module = buildModel(getModuleDirs().get(0));
      assertNotNull(module);
      assertNoPomFiles(module.getDirectory());

      // Improvement #56
      assertEquals("org.sourcepit.b2.test.resources.simple.layout", module.getId());

      generatePom(module.getDirectory(), module, B2ModelBuildingRequest.newDefaultProperties());

      File pomFile = new File(module.getDirectory(), "pom.xml");
      assertTrue(pomFile.exists());

      Model pom = readMavenModel(pomFile);
      assertEquals("simple-layout", pom.getArtifactId());
   }

   public void testSkipFacets() throws Exception
   {
      BasicModule module = buildModel(getModuleDirs().get(0));
      assertNotNull(module);
      assertNoPomFiles(module.getDirectory());

      PluginsFacet facet = module.getFacets(PluginsFacet.class).get(0);
      assertNotNull(facet);

      assertIsGeneratorInput(facet);
      generatePom(module.getDirectory(), facet, B2ModelBuildingRequest.newDefaultProperties());

      IInterpolationLayout layout = getLayout(module);
      File facetPom = new File(layout.pathOfFacetMetaData(module, facet.getName(), "pom.xml"));

      // do not generate facet pom as default
      assertFalse(facetPom.exists());

      PropertiesMap properties = new LinkedPropertiesMap();
      properties.setBoolean("b2.pomGenerator.skipFacets", false);
      generatePom(module.getDirectory(), facet, properties);

      assertTrue(facetPom.exists());
   }
}
