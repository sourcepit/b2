/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import org.sourcepit.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;

/**
 * @author Bernd
 */
public class DefaultConverter extends AbstractConverter
{
   private final PropertiesMap properties;

   private final String nameSpace;

   private final String moduleVersion;

   public DefaultConverter(String nameSpace, String moduleVersion, PropertiesMap properties)
   {
      final LinkedPropertiesMap propertiesMap = new LinkedPropertiesMap();
      propertiesMap.setDefaultProperties(loadConverterProperties());
      if (properties != null)
      {
         propertiesMap.putAll(properties);
      }
      this.properties = propertiesMap;
      this.nameSpace = nameSpace;
      this.moduleVersion = moduleVersion;
   }

   public String getModuleVersion()
   {
      return moduleVersion;
   }

   @Override
   protected PropertiesMap getPropertiesMap()
   {
      return properties;
   }

   public String getNameSpace()
   {
      return nameSpace;
   }
}
