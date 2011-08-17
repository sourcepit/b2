/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.b2.examples.structured.module;

import junit.framework.TestCase;

/**
 * @author Bernd
 */
public class ExampleUtilsTest extends TestCase
{
   public void testConvertString() throws Exception
   {
      assertEquals("hans", ExampleUtils.convertSrtring("Hans", false));
   }
}
