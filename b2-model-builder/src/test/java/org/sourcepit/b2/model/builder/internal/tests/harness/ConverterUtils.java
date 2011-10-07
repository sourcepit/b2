/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import org.sourcepit.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.b2.model.builder.util.DefaultConverter;
import org.sourcepit.b2.model.builder.util.IConverter;

public final class ConverterUtils
{
   public static IConverter TEST_CONVERTER = newDefaultTestConverter(null);

   private ConverterUtils()
   {
      super();
   }

   public static DefaultConverter newDefaultTestConverter(PropertiesMap properties)
   {
      return new DefaultConverter("org.sourcepit.b2", "0.0.1.qualifier", properties);
   }
}
