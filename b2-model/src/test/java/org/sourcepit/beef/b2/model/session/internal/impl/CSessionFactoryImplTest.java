/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.session.internal.impl;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.sourcepit.beef.b2.model.module.internal.impl.AbstractCustomModelFactoryImplTest;
import org.sourcepit.beef.b2.model.session.SessionFactory;
import org.sourcepit.beef.b2.model.session.SessionPackage;

public class CSessionFactoryImplTest extends AbstractCustomModelFactoryImplTest
{

   @Override
   protected String eNsUri()
   {
      return SessionPackage.eNS_URI;
   }

   @Override
   protected Class<? extends EFactory> eCustomFactoryImplClass()
   {
      return CSessionFactoryImpl.class;
   }

   @Override
   protected Class<? extends EFactory> eFactoryImplClass()
   {
      return SessionFactoryImpl.class;
   }

   @Override
   protected EPackage ePackageInstance()
   {
      return SessionPackage.eINSTANCE;
   }

   @Override
   protected EFactory eFactoryInstance()
   {
      return SessionFactory.eINSTANCE;
   }

}
