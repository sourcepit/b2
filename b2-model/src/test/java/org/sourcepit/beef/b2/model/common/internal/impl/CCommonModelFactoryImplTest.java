/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.common.internal.impl;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.sourcepit.beef.b2.model.common.CommonModelFactory;
import org.sourcepit.beef.b2.model.common.CommonModelPackage;
import org.sourcepit.beef.b2.model.module.internal.impl.AbstractCustomModelFactoryImplTest;

public class CCommonModelFactoryImplTest extends AbstractCustomModelFactoryImplTest
{
   protected String eNsUri()
   {
      return CommonModelPackage.eNS_URI;
   }

   @Override
   protected Class<? extends EFactory> eCustomFactoryImplClass()
   {
      return CCommonModelFactoryImpl.class;
   }

   @Override
   protected Class<? extends EFactory> eFactoryImplClass()
   {
      return CommonModelFactoryImpl.class;
   }

   @Override
   protected EPackage ePackageInstance()
   {
      return CommonModelPackage.eINSTANCE;
   }

   @Override
   protected EFactory eFactoryInstance()
   {
      return CommonModelFactory.eINSTANCE;
   }
}
