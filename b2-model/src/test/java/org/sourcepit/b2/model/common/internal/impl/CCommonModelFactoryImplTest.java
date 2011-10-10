/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.common.internal.impl;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.sourcepit.b2.model.common.CommonModelFactory;
import org.sourcepit.b2.model.common.CommonModelPackage;
import org.sourcepit.b2.model.module.internal.impl.AbstractCustomModelFactoryImplTest;

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
