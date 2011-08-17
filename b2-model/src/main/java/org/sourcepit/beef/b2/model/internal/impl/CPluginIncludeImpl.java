/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.internal.impl;

import org.sourcepit.beef.b2.model.internal.util.ReferenceUtils;
import org.sourcepit.beef.b2.model.util.Identifier;

public class CPluginIncludeImpl extends PluginIncludeImpl
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
