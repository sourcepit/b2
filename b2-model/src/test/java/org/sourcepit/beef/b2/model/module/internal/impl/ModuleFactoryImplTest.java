/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EPackage;
import org.sourcepit.beef.b2.model.module.ModuleFactory;
import org.sourcepit.beef.b2.model.module.ModulePackage;

public class ModuleFactoryImplTest extends TestCase
{
   public void testNsUrl() throws Exception
   {
      ModuleFactory eFactory = ModuleFactory.eINSTANCE;

      ModulePackage ePackage = ModulePackage.eINSTANCE;
      try
      {
         assertSame(eFactory, ePackage.getEFactoryInstance());

         ModuleFactory mockFactory = new ModuleFactoryImpl();
         ePackage.setEFactoryInstance(mockFactory);

         ModuleFactory eFactoryFromRegistry = (ModuleFactory) EPackage.Registry.INSTANCE
            .getEFactory(ModulePackage.eNS_URI);
         assertNotNull(eFactoryFromRegistry);

         assertSame(mockFactory, eFactoryFromRegistry);
         assertSame(mockFactory, ePackage.getEFactoryInstance());

         // bernd: if this assertion fails you have to remove the not from the @generated annotation of the build model
         // factories init method, because the NS URI has changed in the model
         assertSame(mockFactory, ModuleFactoryImpl.init());
      }
      finally
      {
         ePackage.setEFactoryInstance(eFactory);

         ModuleFactory eFactoryFromRegistry = (ModuleFactory) EPackage.Registry.INSTANCE
            .getEFactory(ModulePackage.eNS_URI);
         assertNotNull(eFactoryFromRegistry);
         assertSame(eFactory, eFactoryFromRegistry);
         assertSame(eFactory, ePackage.getEFactoryInstance());
      }
   }
}
