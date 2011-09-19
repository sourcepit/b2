/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import org.eclipse.emf.ecore.EFactory;
import org.sourcepit.beef.b2.model.module.ModuleModelFactory;
import org.sourcepit.beef.b2.model.module.ModuleModelPackage;

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
