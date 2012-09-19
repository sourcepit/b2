/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder;

import org.sourcepit.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

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

   public static PropertiesMap newDefaultProperties()
   {
      final LinkedPropertiesMap defaultProperties = new LinkedPropertiesMap();
      defaultProperties.load(B2ModelBuildingRequest.class.getClassLoader(), "META-INF/b2/converter.properties");
      return defaultProperties;
   }
}
