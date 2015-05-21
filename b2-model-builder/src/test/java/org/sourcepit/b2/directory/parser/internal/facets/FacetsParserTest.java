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

import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;

public class FacetsParserTest extends AbstractModuleParserTest {
   public void testBasic() throws Exception {
      FacetsParser facetsParser = lookup();
      assertNotNull(facetsParser);
   }

   public void testNull() throws Exception {
      FacetsParser facetsParser = lookup();

      try {
         facetsParser.parse(null, null);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      try {
         facetsParser.parse(new File(""), null);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      try {
         facetsParser.parse(null, B2ModelBuildingRequest.newDefaultProperties());
         fail();
      }
      catch (IllegalArgumentException e) {
      }
   }

   public void testNonFacetDir() throws Exception {
      final File pluginDir = workspace.importResources("composed-component/structured-layout/plugins/example.ui");
      assertTrue(pluginDir.exists());

      FacetsParser facetsParser = lookup();
      assertNull(facetsParser.parse(pluginDir, B2ModelBuildingRequest.newDefaultProperties()));
   }

   public void testSimpleLayout() throws Exception {
      File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.exists());

      FacetsParser facetsParser = lookup();

      FacetsParseResult<? extends AbstractFacet> result = facetsParser.parse(moduleDir,
         B2ModelBuildingRequest.newDefaultProperties());
      assertNotNull(result);

      assertEquals("simple", result.getLayout());

      List<? extends AbstractFacet> facets = result.getFacets();
      assertEquals(4, facets.size());

      BasicModule dummyComponent = ModuleModelFactory.eINSTANCE.createBasicModule();
      dummyComponent.setLayoutId(result.getLayout());
      dummyComponent.getFacets().addAll(facets);

      SimpleLayoutFacetsParserRuleTest.assertSimpleLayout(dummyComponent);
   }

   public void testStructuredLayout() throws Exception {
      File moduleDir = workspace.importResources("composed-component/structured-layout");
      assertTrue(moduleDir.exists());

      FacetsParser facetsParser = lookup();

      FacetsParseResult<? extends AbstractFacet> result = facetsParser.parse(moduleDir,
         B2ModelBuildingRequest.newDefaultProperties());
      assertNotNull(result);

      assertEquals("structured", result.getLayout());

      List<? extends AbstractFacet> facets = result.getFacets();
      assertEquals(5, facets.size());

      BasicModule dummyComponent = ModuleModelFactory.eINSTANCE.createBasicModule();
      dummyComponent.setLayoutId(result.getLayout());
      dummyComponent.getFacets().addAll(facets);

      StructuredLayoutFacetsParserRuleTest.assertStructuredLayout(dummyComponent);
   }

   public void testCompositeComponentDir() throws Exception {
      File moduleDir = workspace.importResources("composed-component");
      assertTrue(moduleDir.exists());

      FacetsParser facetsParser = lookup();

      FacetsParseResult<? extends AbstractFacet> result = facetsParser.parse(moduleDir,
         B2ModelBuildingRequest.newDefaultProperties());
      assertNull(result);
   }

   private FacetsParser lookup() throws Exception {
      return (FacetsParser) lookup(FacetsParser.class);
   }
}
