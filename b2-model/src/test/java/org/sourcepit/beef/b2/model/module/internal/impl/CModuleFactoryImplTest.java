/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import org.eclipse.emf.ecore.EFactory;
import org.sourcepit.beef.b2.model.module.ModuleFactory;
import org.sourcepit.beef.b2.model.module.ModulePackage;

public class CModuleFactoryImplTest extends AbstractCustomModelFactoryImplTest
{
   protected String eNsUri()
   {
      return ModulePackage.eNS_URI;
   }

   protected Class<? extends EFactory> eCustomFactoryImplClass()
   {
      return CModuleFactoryImpl.class;
   }

   @Override
   protected Class<? extends EFactory> eFactoryImplClass()
   {
      return ModuleFactoryImpl.class;
   }

   protected ModulePackage ePackageInstance()
   {
      return ModulePackage.eINSTANCE;
   }

   protected ModuleFactory eFactoryInstance()
   {
      return ModuleFactory.eINSTANCE;
   }

}
