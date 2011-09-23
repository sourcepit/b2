/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.session.internal.impl;

import org.sourcepit.beef.b2.model.session.B2Session;
import org.sourcepit.beef.b2.model.session.ModuleDependency;
import org.sourcepit.beef.b2.model.session.ModuleProject;

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
