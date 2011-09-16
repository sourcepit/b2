/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.common.internal.impl;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.sourcepit.beef.b2.model.common.CommonFactory;
import org.sourcepit.beef.b2.model.common.CommonPackage;
import org.sourcepit.beef.b2.model.module.internal.impl.AbstractCustomModelFactoryImplTest;

public class CCommonFactoryImplTest extends AbstractCustomModelFactoryImplTest
{
   protected String eNsUri()
   {
      return CommonPackage.eNS_URI;
   }

   @Override
   protected Class<? extends EFactory> eCustomFactoryImplClass()
   {
      return CCommonFactoryImpl.class;
   }

   @Override
   protected Class<? extends EFactory> eFactoryImplClass()
   {
      return CommonFactoryImpl.class;
   }

   @Override
   protected EPackage ePackageInstance()
   {
      return CommonPackage.eINSTANCE;
   }

   @Override
   protected EFactory eFactoryInstance()
   {
      return CommonFactory.eINSTANCE;
   }
}
