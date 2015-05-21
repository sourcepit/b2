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

package org.sourcepit.b2.model.module.internal.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;

public class EWalkerImplTest extends TestCase {
   public void testReverse() throws Exception {
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

      new EWalkerImpl(true, true) {
         @Override
         protected boolean visit(EObject eObject) {
            modules.add((CompositeModule) eObject);
            return true;
         }

         protected EList<? extends EObject> getChildren(EObject eObject) {
            if (eObject instanceof CompositeModule) {
               return ((CompositeModule) eObject).getModules();
            }
            return super.getChildren(eObject);
         };
      }.walk(module);

      assertEquals(module_2_1.getId(), modules.get(0).getId());
      assertEquals(module_2.getId(), modules.get(1).getId());
      assertEquals(module_1_1.getId(), modules.get(2).getId());
      assertEquals(module_1.getId(), modules.get(3).getId());
      assertEquals(module.getId(), modules.get(4).getId());
   }

   public void testNormal() throws Exception {
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

      new EWalkerImpl(false, true) {
         @Override
         protected boolean visit(EObject eObject) {
            modules.add((CompositeModule) eObject);
            return true;
         }

         protected EList<? extends EObject> getChildren(EObject eObject) {
            if (eObject instanceof CompositeModule) {
               return ((CompositeModule) eObject).getModules();
            }
            return super.getChildren(eObject);
         };
      }.walk(module);

      assertEquals(module.getId(), modules.get(0).getId());
      assertEquals(module_1.getId(), modules.get(1).getId());
      assertEquals(module_1_1.getId(), modules.get(2).getId());
      assertEquals(module_2.getId(), modules.get(3).getId());
      assertEquals(module_2_1.getId(), modules.get(4).getId());
   }

   public void testStop() throws Exception {
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

      new EWalkerImpl(false, true) {
         @Override
         protected boolean visit(EObject eObject) {
            modules.add((CompositeModule) eObject);
            return !"module_1".equals(((CompositeModule) eObject).getId());
         }

         protected EList<? extends EObject> getChildren(EObject eObject) {
            if (eObject instanceof CompositeModule) {
               return ((CompositeModule) eObject).getModules();
            }
            return super.getChildren(eObject);
         };
      }.walk(module);

      assertEquals(module.getId(), modules.get(0).getId());
      assertEquals(module_1.getId(), modules.get(1).getId());
      assertEquals(module_2.getId(), modules.get(2).getId());
      assertEquals(module_2_1.getId(), modules.get(3).getId());
   }
}
