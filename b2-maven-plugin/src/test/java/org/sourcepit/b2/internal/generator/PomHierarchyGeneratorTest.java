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
import java.io.IOException;

import javax.inject.Inject;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class PomHierarchyGeneratorTest extends AbstractPomGeneratorTest
{
   @Inject
   private PomHierarchyGenerator hierarchyGenerator;

   @Override
   protected String setUpModulePath()
   {
      return "composed-component/simple-layout";
   }

   public void testSkipFacets() throws Exception
   {
      BasicModule module = buildModel(getModuleDirs().get(0));
      assertNotNull(module);
      File moduleDir = module.getDirectory();
      assertNoPomFiles(moduleDir);

      PluginsFacet facet = module.getFacets(PluginsFacet.class).get(0);
      assertNotNull(facet);

      generateAllPoms(module, null);
      File modulePom = new File(moduleDir, "pom.xml");
      assertTrue(modulePom.exists());
      assertTrue(readMavenModel(modulePom).getModules().isEmpty());

      // do not generate facet pom as default
      File facetPom = new File(getLayout(module).pathOfFacetMetaData(module, facet.getName(), "pom.xml"));
      assertFalse(facetPom.exists());

      hierarchyGenerator.generate(module, B2ModelBuildingRequest.newDefaultProperties(), new DefaultTemplateCopier(),
         new ModuleDirectory(moduleDir, null));

      Model moduleModel = readMavenModel(modulePom);
      assertFalse(moduleModel.getModules().isEmpty());

      assertFalse(facetPom.exists());
      for (String modulePath : moduleModel.getModules())
      {
         File subPom = new File(moduleDir, modulePath);
         assertTrue(subPom.exists());
      }

      PropertiesMap properties = new LinkedPropertiesMap();
      properties.setBoolean("b2.pomGenerator.skipFacets", false);
      generateAllPoms(module, properties);
      assertTrue(facetPom.exists());

      hierarchyGenerator.generate(module, ConverterUtils.newDefaultTestConverter(properties),
         new DefaultTemplateCopier(), new ModuleDirectory(moduleDir, null));

      assertIsMavenModule(modulePom, facetPom);
      assertIsMavenParent(facetPom, modulePom);
   }

   private void assertIsMavenParent(File modulePom, File parentPom) throws IOException
   {
      Model moduleModel = readMavenModel(modulePom);
      Parent parent = moduleModel.getParent();
      assertNotNull(parent);
      File actualParent = new File(modulePom.getParentFile(), parent.getRelativePath()).getCanonicalFile();
      assertEquals(parentPom.getCanonicalFile(), actualParent);
   }

   private static void assertIsMavenModule(File parentPom, File modulePom)
   {
      Model moduleModel;
      moduleModel = readMavenModel(parentPom);
      for (String modulePath : moduleModel.getModules())
      {
         File subPom = new File(parentPom.getParentFile(), modulePath);
         assertTrue(subPom.exists());
         if (subPom.equals(modulePom))
         {
            return;
         }
      }
      fail();
   }
}
