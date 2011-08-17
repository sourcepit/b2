/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.internal.util;

import junit.framework.TestCase;

import org.sourcepit.beef.b2.model.util.Identifier;

/**
 * @author Bernd
 */
public class ReferenceUtilsTest extends TestCase
{
   public void testIsSatisfiableBy() throws Exception
   {
      assertTrue(ReferenceUtils.isSatisfiableBy("foo", "1.0", new Identifier("foo", "1.0")));
      assertTrue(ReferenceUtils.isSatisfiableBy("foo", "1.0", new Identifier("foo", "2.0")));
      assertTrue(ReferenceUtils.isSatisfiableBy("foo", "[1,1]", new Identifier("foo", "1")));
      assertFalse(ReferenceUtils.isSatisfiableBy("foo", "[1,1]", new Identifier("foo", "2")));
      assertFalse(ReferenceUtils.isSatisfiableBy("foo", "[1,1]", new Identifier("foo", "0.0.0")));

      assertTrue(ReferenceUtils.isSatisfiableBy("foo", "[1,2)", new Identifier("foo", "1.9.9")));
      assertTrue(ReferenceUtils.isSatisfiableBy("foo", "[1,2)", new Identifier("foo", "1")));
      assertFalse(ReferenceUtils.isSatisfiableBy("foo", "[1,2)", new Identifier("foo", "2")));
      assertFalse(ReferenceUtils.isSatisfiableBy("foo", "[1,2)", new Identifier("foo", "0")));

      assertTrue(ReferenceUtils.isSatisfiableBy("foo", "1.0.0.qualifier", new Identifier("foo", "1.0.0.qualifier")));
      assertTrue(ReferenceUtils.isSatisfiableBy("foo", "1.0.0", new Identifier("foo", "1.0.0.qualifier")));
      assertTrue(ReferenceUtils.isSatisfiableBy("foo", "[1,2]", new Identifier("foo", "1.0.0.qualifier")));
      assertTrue(ReferenceUtils.isSatisfiableBy("foo", "[1,2.0.0.qualifier]", new Identifier("foo", "2.0.0.qualifier")));

      assertFalse(ReferenceUtils.isSatisfiableBy("foo", "[1,2.0.0]", new Identifier("foo", "2.0.0.qualifier")));
      assertFalse(ReferenceUtils.isSatisfiableBy("foo", "[1,2.0.0)", new Identifier("foo", "2.0.0.qualifier")));
   }
}
