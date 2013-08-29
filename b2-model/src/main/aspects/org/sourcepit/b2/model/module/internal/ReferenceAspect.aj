/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal;

import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.util.Identifiable;
import org.sourcepit.b2.model.module.internal.util.ReferenceUtils;

public aspect ReferenceAspect
{
   pointcut isSatisfiableBy(AbstractReference ref, Identifiable identifiable) : target(ref) && args(identifiable) && execution(boolean isSatisfiableBy(Identifiable));

   boolean around(AbstractReference ref, Identifiable identifiable) : isSatisfiableBy(ref, identifiable) {
      return ReferenceUtils.isSatisfiableBy(ref, identifiable);
   }
}
