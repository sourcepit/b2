/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.common.internal.utils;

import org.sourcepit.b2.common.internal.utils.PropertiesUtils;

import junit.framework.TestCase;

public class PropertiesUtilsTest extends TestCase
{
   public void testEscapeJavaProperties() throws Exception
   {
      assertEquals("\\u00FC", PropertiesUtils.escapeJavaProperties("ü"));

      String result = PropertiesUtils.escapeJavaProperties("   Hans\\\n\r im ''Glück''!");
      assertEquals("\\   Hans\\\\\\n\\r im ''Gl\\u00FCck''\\!", result);
   }
}
