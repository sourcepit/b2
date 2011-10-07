/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.module.internal.impl;

import org.sourcepit.b2.model.module.internal.impl.PluginIncludeImpl;
import org.sourcepit.b2.model.module.internal.util.ReferenceUtils;
import org.sourcepit.b2.model.module.util.Identifier;

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
