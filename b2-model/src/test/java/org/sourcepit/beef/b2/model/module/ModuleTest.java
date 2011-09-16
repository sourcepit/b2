/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.module;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.model.module.test.internal.harness.EcoreUtils;
import org.sourcepit.beef.b2.model.module.test.internal.harness.EcoreUtils.RunnableWithEObject;

public class ModuleTest extends TestCase
{
   public void testGetFacets() throws Exception
   {
      EcoreUtils.foreachSupertype(ModulePackage.eINSTANCE.getAbstractModule(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            AbstractModule module = (AbstractModule) eObject;
            assertNotNull(module.getFacets(PluginsFacet.class));

            module.getFacets().add(ModuleFactory.eINSTANCE.createFeaturesFacet());
            assertEquals(1, module.getFacets(FeaturesFacet.class).size());

            module.getFacets().add(ModuleFactory.eINSTANCE.createFeaturesFacet());
            assertEquals(2, module.getFacets(FeaturesFacet.class).size());

            module.getFacets().add(ModuleFactory.eINSTANCE.createSitesFacet());
            assertEquals(2, module.getFacets(FeaturesFacet.class).size());
            assertEquals(1, module.getFacets(SitesFacet.class).size());
            assertEquals(3, module.getFacets(ProjectFacet.class).size());
         }
      });
   }

   public void testHasFacets() throws Exception
   {
      EcoreUtils.foreachSupertype(ModulePackage.eINSTANCE.getAbstractModule(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            AbstractModule module = (AbstractModule) eObject;
            assertFalse(module.hasFacets(PluginsFacet.class));

            module.getFacets().add(ModuleFactory.eINSTANCE.createFeaturesFacet());
            assertTrue(module.hasFacets(FeaturesFacet.class));

            module.getFacets().add(ModuleFactory.eINSTANCE.createFeaturesFacet());
            assertTrue(module.hasFacets(FeaturesFacet.class));

            module.getFacets().add(ModuleFactory.eINSTANCE.createSitesFacet());
            assertTrue(module.hasFacets(FeaturesFacet.class));
            assertTrue(module.hasFacets(SitesFacet.class));
            assertTrue(module.hasFacets(ProjectFacet.class));
         }
      });
   }

   public void testGetFacetByType() throws Exception
   {
      EcoreUtils.foreachSupertype(ModulePackage.eINSTANCE.getAbstractModule(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            AbstractModule module = (AbstractModule) eObject;
            assertNull(module.getFacetByName(null));
            assertNull(module.getFacetByName("plugins"));

            FeaturesFacet features = ModuleFactory.eINSTANCE.createFeaturesFacet();
            features.setName("features");
            module.getFacets().add(features);
            assertNull(module.getFacetByName("plugins"));
            assertEquals(features, module.getFacetByName("features"));

            PluginsFacet plugins = ModuleFactory.eINSTANCE.createPluginsFacet();
            plugins.setName("plugins");
            module.getFacets().add(plugins);
            assertEquals(features, module.getFacetByName("features"));
            assertEquals(plugins, module.getFacetByName("plugins"));
         }
      });
   }

   public void testResolveReference() throws Exception
   {
      EcoreUtils.foreachSupertype(ModulePackage.eINSTANCE.getAbstractModule(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            AbstractModule module = (AbstractModule) eObject;
            try
            {
               module.resolveReference(null, PluginsFacet.class);
               fail();
            }
            catch (IllegalArgumentException e)
            {
            }

            PluginProject project = ModuleFactory.eINSTANCE.createPluginProject();
            project.setId("foo");
            project.setVersion("1.0.0");

            PluginProject project2 = ModuleFactory.eINSTANCE.createPluginProject();
            project2.setId("foo");
            project2.setVersion("2.0.0");

            PluginsFacet facet = ModuleFactory.eINSTANCE.createPluginsFacet();
            facet.getProjects().add(project);
            facet.getProjects().add(project2);

            module.getFacets().add(facet);

            Reference reference = ModuleFactory.eINSTANCE.createReference();
            reference.setId("foo");

            reference.setVersionRange("1.0.0");
            assertSame(project, module.resolveReference(reference, PluginsFacet.class));

            reference.setVersionRange("0.0.0");
            assertSame(project, module.resolveReference(reference, PluginsFacet.class));

            reference.setVersionRange("[0.0.0,1.0.0)");
            assertSame(null, module.resolveReference(reference, PluginsFacet.class));

            reference.setVersionRange("2.0.0");
            assertSame(project2, module.resolveReference(reference, PluginsFacet.class));

            reference.setVersionRange("[2.0.0,2.0.0]");
            assertSame(project2, module.resolveReference(reference, PluginsFacet.class));
         }
      });
   }
}
