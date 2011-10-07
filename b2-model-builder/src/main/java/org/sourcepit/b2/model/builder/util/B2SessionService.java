/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.model.session.B2Session;

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
