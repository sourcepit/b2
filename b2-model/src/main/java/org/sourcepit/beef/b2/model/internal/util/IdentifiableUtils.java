/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.internal.util;

import org.sourcepit.beef.b2.model.module.Identifiable;
import org.sourcepit.beef.b2.model.util.Identifier;

public final class IdentifiableUtils
{
   public IdentifiableUtils()
   {
      super();
   }

   public static Identifier toIdentifier(Identifiable identifiable)
   {
      return new Identifier(identifiable.getId(), identifiable.getVersion());
   }

   public static boolean isIdentifyableBy(Identifiable identifiable, Identifier identifier)
   {
      return identifiable.toIdentifier().equals(identifier);
   }
}
