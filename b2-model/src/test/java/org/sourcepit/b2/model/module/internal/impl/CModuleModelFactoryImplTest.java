/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.impl;

import org.eclipse.emf.ecore.EFactory;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.ModuleModelPackage;

public class CModuleModelFactoryImplTest extends AbstractCustomModelFactoryImplTest
{
   protected String eNsUri()
   {
      return ModuleModelPackage.eNS_URI;
   }

   protected Class<? extends EFactory> eCustomFactoryImplClass()
   {
      return CModuleModelFactoryImpl.class;
   }

   @Override
   protected Class<? extends EFactory> eFactoryImplClass()
   {
      return ModuleModelFactoryImpl.class;
   }

   protected ModuleModelPackage ePackageInstance()
   {
      return ModuleModelPackage.eINSTANCE;
   }

   protected ModuleModelFactory eFactoryInstance()
   {
      return ModuleModelFactory.eINSTANCE;
   }

}
