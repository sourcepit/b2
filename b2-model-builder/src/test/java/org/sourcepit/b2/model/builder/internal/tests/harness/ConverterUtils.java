/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
