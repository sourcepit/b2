/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder.util;

import org.sourcepit.beef.b2.model.session.B2Session;

public class B2SessionService implements IB2SessionService
{
   private B2Session currentSession;

   public void setCurrentSession(B2Session currentSession)
   {
      this.currentSession = currentSession;
   }

   public B2Session getCurrentSession()
   {
      return currentSession;
   }
}
