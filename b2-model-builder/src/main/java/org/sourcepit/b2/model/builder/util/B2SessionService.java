/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.model.session.B2Session;

@Named
@Singleton
public class B2SessionService implements IB2SessionService
{
   private B2Session currentSession;
   
   private ResourceSet resourceSet;

   public void setCurrentSession(B2Session currentSession)
   {
      this.currentSession = currentSession;
   }

   public B2Session getCurrentSession()
   {
      return currentSession;
   }
   
   public void setCurrentResourceSet(ResourceSet currentResourceSet)
   {
      resourceSet = currentResourceSet;
   }
   
   public ResourceSet getCurrentResourceSet()
   {
      return resourceSet;
   }
}
