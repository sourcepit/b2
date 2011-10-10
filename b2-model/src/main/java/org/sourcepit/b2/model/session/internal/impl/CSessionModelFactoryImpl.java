/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session.internal.impl;

import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;

public class CSessionModelFactoryImpl extends SessionModelFactoryImpl
{
   @Override
   public B2Session createB2Session()
   {
      return new CSessionImpl();
   }

   @Override
   public ModuleProject createModuleProject()
   {
      return new CModuleProjectImpl();
   }

   @Override
   public ModuleDependency createModuleDependency()
   {
      return new CModuleDependencyImpl();
   }
}
