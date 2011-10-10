/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.util;

import junit.framework.TestCase;

import org.sourcepit.b2.model.module.util.Identifier;

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
