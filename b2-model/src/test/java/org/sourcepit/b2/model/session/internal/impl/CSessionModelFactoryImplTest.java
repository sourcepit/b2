/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.session.internal.impl;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.sourcepit.b2.model.module.internal.impl.AbstractCustomModelFactoryImplTest;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.b2.model.session.SessionModelPackage;
import org.sourcepit.b2.model.session.internal.impl.CSessionModelFactoryImpl;
import org.sourcepit.b2.model.session.internal.impl.SessionModelFactoryImpl;

public class CSessionModelFactoryImplTest extends AbstractCustomModelFactoryImplTest
{

   @Override
   protected String eNsUri()
   {
      return SessionModelPackage.eNS_URI;
   }

   @Override
   protected Class<? extends EFactory> eCustomFactoryImplClass()
   {
      return CSessionModelFactoryImpl.class;
   }

   @Override
   protected Class<? extends EFactory> eFactoryImplClass()
   {
      return SessionModelFactoryImpl.class;
   }

   @Override
   protected EPackage ePackageInstance()
   {
      return SessionModelPackage.eINSTANCE;
   }

   @Override
   protected EFactory eFactoryInstance()
   {
      return SessionModelFactory.eINSTANCE;
   }

}
