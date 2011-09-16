/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;

public abstract class AbstractCustomModelFactoryImplTest extends TestCase
{
   public void testImpl() throws Exception
   {
      // bernd: if this test fails edit B2ModelFactoryImpl.init() in a way that is will instantiate the
      // CB2ModelFactoryImpl factory instead of B2ModelFactoryImpl
      Class<? extends EFactory> eFactoryImplClass = eCustomFactoryImplClass();
      EFactory eFactory = eFactoryInstance();
      assertTrue(eFactoryImplClass.isAssignableFrom(eFactory.getClass()));
   }

   public void testNsUri() throws Exception
   {
      EFactory eFactory = eFactoryInstance();
      EPackage ePackage = ePackageInstance();
      String nsUri = eNsUri();
      testNsUri(eFactory, ePackage, nsUri);
   }

   protected abstract String eNsUri();

   protected abstract Class<? extends EFactory> eCustomFactoryImplClass();

   protected abstract Class<? extends EFactory> eFactoryImplClass();

   protected abstract EPackage ePackageInstance();

   protected abstract EFactory eFactoryInstance();

   protected void testNsUri(EFactory eFactory, EPackage ePackage, String nsUri) throws Exception
   {
      try
      {
         assertSame(eFactory, ePackage.getEFactoryInstance());

         Class<? extends EFactory> eFactoryImplClass = eFactoryImplClass();

         EFactory mockFactory = eFactoryImplClass.newInstance();
         ePackage.setEFactoryInstance(mockFactory);

         Registry eRegistry = EPackage.Registry.INSTANCE;
         EFactory eFactoryFromRegistry = (EFactory) eRegistry.getEFactory(nsUri);
         assertNotNull(eFactoryFromRegistry);

         assertSame(mockFactory, eFactoryFromRegistry);
         assertSame(mockFactory, ePackage.getEFactoryInstance());

         // bernd: if this assertion fails you have to remove the not from the @generated annotation of the build model
         // factories init method, because the NS URI has changed in the model

         assertSame(mockFactory, eFactoryImplClass.getMethod("init").invoke(null));
      }
      finally
      {
         ePackage.setEFactoryInstance(eFactory);

         EFactory eFactoryFromRegistry = (EFactory) EPackage.Registry.INSTANCE.getEFactory(nsUri);
         assertNotNull(eFactoryFromRegistry);
         assertSame(eFactory, eFactoryFromRegistry);
         assertSame(eFactory, ePackage.getEFactoryInstance());
      }
   }
}
