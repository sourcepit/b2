/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VersionUtilsTest {
   @Test
   public void testToTychoVersion() {
      assertEquals("1.2", VersionUtils.toTychoVersion("1.2"));
      assertEquals("1.2.3", VersionUtils.toTychoVersion("1.2.3"));
      assertEquals("1.2.3-SNAPSHOT", VersionUtils.toTychoVersion("1.2.3.qualifier"));
      assertEquals("1.2.3.rc1", VersionUtils.toTychoVersion("1.2.3.rc1"));
   }

   @Test
   public void testToMavenVersion() {
      assertEquals("1.2", VersionUtils.toMavenVersion("1.2"));
      assertEquals("1.2.3", VersionUtils.toMavenVersion("1.2.3"));
      assertEquals("1.2.3-SNAPSHOT", VersionUtils.toMavenVersion("1.2.3.qualifier"));
      assertEquals("1.2.3-rc1", VersionUtils.toMavenVersion("1.2.3.rc1"));
   }

   @Test
   public void testToBundleVersion() throws Exception {
      assertEquals("1.0.0.RC1", VersionUtils.toBundleVersion("1.0.0-RC1"));
      assertEquals("1.0.0", VersionUtils.toBundleVersion("1"));
      assertEquals("1.0.0.qualifier", VersionUtils.toBundleVersion("1.0.0-SnApShOt"));
      assertEquals("1.0.0.qualifier", VersionUtils.toBundleVersion("1.0-SNAPSHOT"));
      assertEquals("0.1.0", VersionUtils.toBundleVersion("0.1"));
      assertEquals("0.1.0", VersionUtils.toBundleVersion("0.1.0"));
      assertEquals("0.0.0", VersionUtils.toBundleVersion("0.0"));
   }
}
