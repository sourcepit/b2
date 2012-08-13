/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal;

import org.sourcepit.b2.model.module.Reference;
import org.sourcepit.b2.model.module.internal.util.ReferenceUtils;
import org.sourcepit.b2.model.module.util.Identifier;

public aspect ReferenceAspect
{
   pointcut isSatisfiableBy(Reference ref, Identifier identifier) : target(ref) && args(identifier) && execution(boolean isSatisfiableBy(Identifier));

   pointcut setStrictVersion(Reference ref, String version) : target(ref) && args(version) && execution(void setStrictVersion(String));

   boolean around(Reference ref, Identifier identifier) : isSatisfiableBy(ref, identifier) {
      return ReferenceUtils.isSatisfiableBy(ref, identifier);
   }

   void around(Reference ref, String version) : setStrictVersion(ref, version) {
      ReferenceUtils.setStrictVersion(ref, version);
   }
}
