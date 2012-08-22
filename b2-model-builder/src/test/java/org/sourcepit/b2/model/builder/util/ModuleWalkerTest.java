/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;

public class ModuleWalkerTest
{

   @Test
   public void test()
   {
      final ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;

      BasicModule subModule1 = eFactory.createBasicModule();
      BasicModule subModule2 = eFactory.createBasicModule();

      CompositeModule compositeModule = eFactory.createCompositeModule();
      compositeModule.getModules().add(subModule1);
      compositeModule.getModules().add(subModule2);

      final List<AbstractModule> visitedModules = new ArrayList<AbstractModule>();

      new ModuleWalker()
      {
         @Override
         protected boolean doVisit(EObject eObject)
         {
            if (eObject instanceof AbstractModule)
            {
               visitedModules.add((AbstractModule) eObject);
            }
            return true;
         }
      }.walk(compositeModule);
      
      assertEquals(1, visitedModules.size());
      assertEquals(compositeModule, visitedModules.get(0));
   }

}
