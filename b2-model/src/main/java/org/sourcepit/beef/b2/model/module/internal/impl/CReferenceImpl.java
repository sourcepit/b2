/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import org.sourcepit.beef.b2.model.module.internal.util.ReferenceUtils;
import org.sourcepit.beef.b2.model.module.util.Identifier;

/**
 * @author Bernd
 */
public class CReferenceImpl extends ReferenceImpl
{
   @Override
   public boolean isSatisfiableBy(Identifier identifier)
   {
      return ReferenceUtils.isSatisfiableBy(this, identifier);
   }

   @Override
   public void setStrictVersion(String version)
   {
      ReferenceUtils.setStrictVersion(this, version);
   }
}
