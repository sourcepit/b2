/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session.internal.impl;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.sourcepit.b2.model.module.internal.impl.AbstractCustomModelFactoryImplTest;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.b2.model.session.SessionModelPackage;

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
