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

package org.sourcepit.b2.model.module;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.test.internal.harness.EcoreUtils;
import org.sourcepit.b2.model.module.test.internal.harness.EcoreUtils.RunnableWithEObject;

public class ModuleTest extends TestCase
{
   public void testGetFacets() throws Exception
   {
      EcoreUtils.foreachSupertype(ModuleModelPackage.eINSTANCE.getAbstractModule(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            AbstractModule module = (AbstractModule) eObject;
            assertNotNull(module.getFacets(PluginsFacet.class));

            module.getFacets().add(ModuleModelFactory.eINSTANCE.createFeaturesFacet());
            assertEquals(1, module.getFacets(FeaturesFacet.class).size());

            module.getFacets().add(ModuleModelFactory.eINSTANCE.createFeaturesFacet());
            assertEquals(2, module.getFacets(FeaturesFacet.class).size());

            module.getFacets().add(ModuleModelFactory.eINSTANCE.createSitesFacet());
            assertEquals(2, module.getFacets(FeaturesFacet.class).size());
            assertEquals(1, module.getFacets(SitesFacet.class).size());
            assertEquals(3, module.getFacets(ProjectFacet.class).size());
         }
      });
   }

   public void testHasFacets() throws Exception
   {
      EcoreUtils.foreachSupertype(ModuleModelPackage.eINSTANCE.getAbstractModule(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            AbstractModule module = (AbstractModule) eObject;
            assertFalse(module.hasFacets(PluginsFacet.class));

            module.getFacets().add(ModuleModelFactory.eINSTANCE.createFeaturesFacet());
            assertTrue(module.hasFacets(FeaturesFacet.class));

            module.getFacets().add(ModuleModelFactory.eINSTANCE.createFeaturesFacet());
            assertTrue(module.hasFacets(FeaturesFacet.class));

            module.getFacets().add(ModuleModelFactory.eINSTANCE.createSitesFacet());
            assertTrue(module.hasFacets(FeaturesFacet.class));
            assertTrue(module.hasFacets(SitesFacet.class));
            assertTrue(module.hasFacets(ProjectFacet.class));
         }
      });
   }

   public void testGetFacetByType() throws Exception
   {
      EcoreUtils.foreachSupertype(ModuleModelPackage.eINSTANCE.getAbstractModule(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            AbstractModule module = (AbstractModule) eObject;
            assertNull(module.getFacetByName(null));
            assertNull(module.getFacetByName("plugins"));

            FeaturesFacet features = ModuleModelFactory.eINSTANCE.createFeaturesFacet();
            features.setName("features");
            module.getFacets().add(features);
            assertNull(module.getFacetByName("plugins"));
            assertEquals(features, module.getFacetByName("features"));

            PluginsFacet plugins = ModuleModelFactory.eINSTANCE.createPluginsFacet();
            plugins.setName("plugins");
            module.getFacets().add(plugins);
            assertEquals(features, module.getFacetByName("features"));
            assertEquals(plugins, module.getFacetByName("plugins"));
         }
      });
   }

   public void testResolveReference() throws Exception
   {
      EcoreUtils.foreachSupertype(ModuleModelPackage.eINSTANCE.getAbstractModule(), new RunnableWithEObject()
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

            PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
            project.setId("foo");
            project.setVersion("1.0.0");

            PluginProject project2 = ModuleModelFactory.eINSTANCE.createPluginProject();
            project2.setId("foo");
            project2.setVersion("2.0.0");

            PluginsFacet facet = ModuleModelFactory.eINSTANCE.createPluginsFacet();
            facet.getProjects().add(project);
            facet.getProjects().add(project2);

            module.getFacets().add(facet);

            PluginInclude reference = ModuleModelFactory.eINSTANCE.createPluginInclude();
            reference.setId("foo");

            reference.setVersion("1.0.0");
            assertSame(project, module.resolveReference(reference, PluginsFacet.class));

            reference.setVersion("0.0.0");
            assertSame(project, module.resolveReference(reference, PluginsFacet.class));

            reference.setVersion("2.0.0");
            assertSame(project2, module.resolveReference(reference, PluginsFacet.class));
         }
      });
   }
}
