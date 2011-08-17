/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.internal.impl;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EPackage;
import org.sourcepit.beef.b2.internal.model.B2ModelFactory;
import org.sourcepit.beef.b2.internal.model.B2ModelPackage;

public class B2ModelFactoryImplTest extends TestCase
{
   public void testNsUrl() throws Exception
   {
      B2ModelFactory eFactory = B2ModelFactory.eINSTANCE;

      B2ModelPackage ePackage = B2ModelPackage.eINSTANCE;
      try
      {
         assertSame(eFactory, ePackage.getEFactoryInstance());

         B2ModelFactory mockFactory = new B2ModelFactoryImpl();
         ePackage.setEFactoryInstance(mockFactory);

         B2ModelFactory eFactoryFromRegistry = (B2ModelFactory) EPackage.Registry.INSTANCE
            .getEFactory(B2ModelPackage.eNS_URI);
         assertNotNull(eFactoryFromRegistry);

         assertSame(mockFactory, eFactoryFromRegistry);
         assertSame(mockFactory, ePackage.getEFactoryInstance());

         // bernd: if this assertion fails you have to remove the not from the @generated annotation of the build model
         // factories init method, because the NS URI has changed in the model
         assertSame(mockFactory, B2ModelFactoryImpl.init());
      }
      finally
      {
         ePackage.setEFactoryInstance(eFactory);

         B2ModelFactory eFactoryFromRegistry = (B2ModelFactory) EPackage.Registry.INSTANCE
            .getEFactory(B2ModelPackage.eNS_URI);
         assertNotNull(eFactoryFromRegistry);
         assertSame(eFactory, eFactoryFromRegistry);
         assertSame(eFactory, ePackage.getEFactoryInstance());
      }
   }
}
