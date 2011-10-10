/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import org.sourcepit.b2.internal.generator.VersionUtils;

import junit.framework.TestCase;

/**
 * VersionsUtilTest
 * 
 * @author Bernd
 */
public class VersionUtilTest extends TestCase
{
   public void testToBundleVersion() throws Exception
   {
      assertEquals("1.0.0.RC1", VersionUtils.toBundleVersion("1.0.0-RC1"));
      assertEquals("1.0.0", VersionUtils.toBundleVersion("1"));
      assertEquals("1.0.0.qualifier", VersionUtils.toBundleVersion("1.0.0-SnApShOt"));
      assertEquals("1.0.0.qualifier", VersionUtils.toBundleVersion("1.0-SNAPSHOT"));
      assertEquals("0.1.0", VersionUtils.toBundleVersion("0.1"));
      assertEquals("0.1.0", VersionUtils.toBundleVersion("0.1.0"));
      assertEquals("0.0.0", VersionUtils.toBundleVersion("0.0"));
   }
}
