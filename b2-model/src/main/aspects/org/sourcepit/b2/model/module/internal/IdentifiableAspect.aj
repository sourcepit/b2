/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal;

import org.sourcepit.b2.model.module.Identifiable;
import org.sourcepit.b2.model.module.internal.util.IdentifiableUtils;
import org.sourcepit.b2.model.module.util.Identifier;

public aspect IdentifiableAspect
{
   pointcut isIdentifyableBy(Identifiable identifiable, Identifier identifier): target(identifiable) && args(identifier) && execution(boolean isIdentifyableBy(Identifier));

   pointcut toIdentifier(Identifiable identifiable): target(identifiable)  && execution(Identifier toIdentifier());

   boolean around(Identifiable identifiable, Identifier identifier) : isIdentifyableBy(identifiable, identifier)
   {
      return IdentifiableUtils.isIdentifyableBy(identifiable, identifier);
   }

   Identifier around(Identifiable identifiable) : toIdentifier(identifiable)
   {
      return IdentifiableUtils.toIdentifier(identifiable);
   }
}
