/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VersionUtilsTest
{
   @Test
   public void testToTychoVersion()
   {
      assertEquals("1.2", VersionUtils.toTychoVersion("1.2"));
      assertEquals("1.2.3", VersionUtils.toTychoVersion("1.2.3"));
      assertEquals("1.2.3-SNAPSHOT", VersionUtils.toTychoVersion("1.2.3.qualifier"));
      assertEquals("1.2.3.rc1", VersionUtils.toTychoVersion("1.2.3.rc1"));
   }
   
   @Test
   public void testToMavenVersion()
   {
      assertEquals("1.2", VersionUtils.toMavenVersion("1.2"));
      assertEquals("1.2.3", VersionUtils.toMavenVersion("1.2.3"));
      assertEquals("1.2.3-SNAPSHOT", VersionUtils.toMavenVersion("1.2.3.qualifier"));
      assertEquals("1.2.3-rc1", VersionUtils.toMavenVersion("1.2.3.rc1"));
   }
   
   @Test
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
