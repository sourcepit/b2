/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;

public class ConverterUtils
{

   public static PropertiesSource newDefaultTestConverter(PropertiesMap properties)
   {
      PropertiesMap defaultProperties = B2ModelBuildingRequest.newDefaultProperties();
      if (properties != null)
      {
         defaultProperties.putAll(properties);
      }
      return defaultProperties;
   }

}
