/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.session.internal.impl;

import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.b2.model.session.Session;

public class CSessionFactoryImpl extends SessionFactoryImpl
{
   @Override
   public Session createSession()
   {
      return new CSessionImpl();
   }

   @Override
   public ModuleProject createModuleProject()
   {
      return new CModuleProjectImpl();
   }
}
