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

package org.sourcepit.b2.internal.generator;

import java.io.File;

import org.apache.maven.model.Model;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class PomGeneratorTest extends AbstractPomGeneratorTest {
   @Override
   protected String setUpModulePath() {
      return "composed-component/simple-layout";
   }

   public void testArtifactId() throws Exception {
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

   public void testSkipFacets() throws Exception {
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
