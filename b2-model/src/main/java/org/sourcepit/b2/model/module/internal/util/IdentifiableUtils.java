/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.util;

import org.sourcepit.b2.model.module.Identifiable;
import org.sourcepit.b2.model.module.util.Identifier;

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
