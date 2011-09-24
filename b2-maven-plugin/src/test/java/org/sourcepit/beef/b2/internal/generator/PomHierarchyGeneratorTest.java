/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.sourcepit.beef.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.beef.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.PluginsFacet;

public class PomHierarchyGeneratorTest extends AbstractPomGeneratorTest
{
   @Inject
   private PomHierarchyGenerator hierarchyGenerator;

   @Override
   protected String getModulePath()
   {
      return "composed-component/simple-layout";
   }

   public void testSkipFacets() throws Exception
   {
      BasicModule module = buildModel(b2Session.getCurrentProject().getDirectory());
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

      hierarchyGenerator.generate(module, ConverterUtils.TEST_CONVERTER, new DefaultTemplateCopier());

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
         new DefaultTemplateCopier());

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
