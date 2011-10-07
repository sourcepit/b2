/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder;

import org.sourcepit.b2.directory.parser.module.ModuleParsingRequest;

public class B2ModelBuildingRequest extends ModuleParsingRequest implements IB2ModelBuildingRequest
{
   private boolean interpolate;

   public boolean isInterpolate()
   {
      return interpolate;
   }

   public void setInterpolate(boolean interpolateDerivedElements)
   {
      this.interpolate = interpolateDerivedElements;
   }
}
