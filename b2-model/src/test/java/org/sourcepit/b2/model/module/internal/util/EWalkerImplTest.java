/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.module.internal.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;

public class EWalkerImplTest extends TestCase
{
   public void testReverse() throws Exception
   {
      CompositeModule module = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module.setId("module");
      CompositeModule module_1 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_1.setId("module_1");
      CompositeModule module_1_1 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_1_1.setId("module_1_1");
      CompositeModule module_2 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_2.setId("module_2");
      CompositeModule module_2_1 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_2_1.setId("module_2_1");

      module.getModules().add(module_1);
      module.getModules().add(module_2);

      module_1.getModules().add(module_1_1);
      module_2.getModules().add(module_2_1);

      final List<CompositeModule> modules = new ArrayList<CompositeModule>();

      new EWalkerImpl(true, true)
      {
         @Override
         protected boolean visit(EObject eObject)
         {
            modules.add((CompositeModule) eObject);
            return true;
         }
      }.walk(module);

      assertEquals(module_2_1.getId(), modules.get(0).getId());
      assertEquals(module_2.getId(), modules.get(1).getId());
      assertEquals(module_1_1.getId(), modules.get(2).getId());
      assertEquals(module_1.getId(), modules.get(3).getId());
      assertEquals(module.getId(), modules.get(4).getId());
   }

   public void testNormal() throws Exception
   {
      CompositeModule module = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module.setId("module");
      CompositeModule module_1 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_1.setId("module_1");
      CompositeModule module_1_1 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_1_1.setId("module_1_1");
      CompositeModule module_2 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_2.setId("module_2");
      CompositeModule module_2_1 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_2_1.setId("module_2_1");

      module.getModules().add(module_1);
      module.getModules().add(module_2);

      module_1.getModules().add(module_1_1);
      module_2.getModules().add(module_2_1);

      final List<CompositeModule> modules = new ArrayList<CompositeModule>();

      new EWalkerImpl(false, true)
      {
         @Override
         protected boolean visit(EObject eObject)
         {
            modules.add((CompositeModule) eObject);
            return true;
         }
      }.walk(module);

      assertEquals(module.getId(), modules.get(0).getId());
      assertEquals(module_1.getId(), modules.get(1).getId());
      assertEquals(module_1_1.getId(), modules.get(2).getId());
      assertEquals(module_2.getId(), modules.get(3).getId());
      assertEquals(module_2_1.getId(), modules.get(4).getId());
   }

   public void testStop() throws Exception
   {
      CompositeModule module = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module.setId("module");
      CompositeModule module_1 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_1.setId("module_1");
      CompositeModule module_1_1 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_1_1.setId("module_1_1");
      CompositeModule module_2 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_2.setId("module_2");
      CompositeModule module_2_1 = ModuleModelFactory.eINSTANCE.createCompositeModule();
      module_2_1.setId("module_2_1");

      module.getModules().add(module_1);
      module.getModules().add(module_2);

      module_1.getModules().add(module_1_1);
      module_2.getModules().add(module_2_1);

      final List<CompositeModule> modules = new ArrayList<CompositeModule>();

      new EWalkerImpl(false, true)
      {
         @Override
         protected boolean visit(EObject eObject)
         {
            modules.add((CompositeModule) eObject);
            return !"module_1".equals(((CompositeModule) eObject).getId());
         }
      }.walk(module);

      assertEquals(module.getId(), modules.get(0).getId());
      assertEquals(module_1.getId(), modules.get(1).getId());
      assertEquals(module_2.getId(), modules.get(2).getId());
      assertEquals(module_2_1.getId(), modules.get(3).getId());
   }
}
