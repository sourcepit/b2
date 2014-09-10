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

package org.sourcepit.b2.model.module.internal.util;

import junit.framework.TestCase;

import org.eclipse.osgi.service.resolver.VersionRange;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.VersionMatchRule;

/**
 * @author Bernd
 */
public class ReferenceUtilsTest extends TestCase
{
   public void testToVersionRange() throws Exception
   {
      // COMPATIBLE
      VersionRange versionRange = ReferenceUtils.toVersionRange("0.0.0", VersionMatchRule.COMPATIBLE);
      assertEquals("[0.0.0,1.0.0)", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.0.0", VersionMatchRule.COMPATIBLE);
      assertEquals("[1.0.0,2.0.0)", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.0", VersionMatchRule.COMPATIBLE);
      assertEquals("[1.1.0,2.0.0)", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.1", VersionMatchRule.COMPATIBLE);
      assertEquals("[1.1.1,2.0.0)", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.1.qualifier", VersionMatchRule.COMPATIBLE);
      assertEquals("[1.1.1,2.0.0)", versionRange.toString());

      // EQUIVALENT
      versionRange = ReferenceUtils.toVersionRange("0.0.0", VersionMatchRule.EQUIVALENT);
      assertEquals("[0.0.0,0.1.0)", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.0.0", VersionMatchRule.EQUIVALENT);
      assertEquals("[1.0.0,1.1.0)", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.0", VersionMatchRule.EQUIVALENT);
      assertEquals("[1.1.0,1.2.0)", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.1", VersionMatchRule.EQUIVALENT);
      assertEquals("[1.1.1,1.2.0)", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.1.qualifier", VersionMatchRule.EQUIVALENT);
      assertEquals("[1.1.1,1.2.0)", versionRange.toString());

      // GREATER_OR_EQUAL
      versionRange = ReferenceUtils.toVersionRange("0.0.0", VersionMatchRule.GREATER_OR_EQUAL);
      assertEquals("0.0.0", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.0.0", VersionMatchRule.GREATER_OR_EQUAL);
      assertEquals("1.0.0", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.0", VersionMatchRule.GREATER_OR_EQUAL);
      assertEquals("1.1.0", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.1", VersionMatchRule.GREATER_OR_EQUAL);
      assertEquals("1.1.1", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.1.qualifier", VersionMatchRule.GREATER_OR_EQUAL);
      assertEquals("1.1.1", versionRange.toString());

      // PERFECT
      versionRange = ReferenceUtils.toVersionRange("0.0.0", VersionMatchRule.PERFECT);
      assertEquals("[0.0.0,0.0.0]", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.0.0", VersionMatchRule.PERFECT);
      assertEquals("[1.0.0,1.0.0]", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.0", VersionMatchRule.PERFECT);
      assertEquals("[1.1.0,1.1.0]", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.1", VersionMatchRule.PERFECT);
      assertEquals("[1.1.1,1.1.1]", versionRange.toString());
      versionRange = ReferenceUtils.toVersionRange("1.1.1.qualifier", VersionMatchRule.PERFECT);
      assertEquals("[1.1.1.qualifier,1.1.1.qualifier]", versionRange.toString());
   }

   public void testFeatureRequirement() throws Exception
   {
      ModuleModelFactory eFactory = ModuleModelFactory.eINSTANCE;

      RuledReference fr = eFactory.createRuledReference();
      fr.setId("foo.feature");

      FeatureProject fp = eFactory.createFeatureProject();
      fp.setId("foo.feature");
      fp.setVersion("0.9.0.qualifer");
      assertTrue(fr.isSatisfiableBy(fp));

      fp.setVersion("1.0.0.qualifer");
      assertFalse(fr.isSatisfiableBy(fp));

      fr.setVersion("1.0.0");
      assertTrue(fr.isSatisfiableBy(fp));

      fr.setVersionMatchRule(VersionMatchRule.PERFECT);
      assertFalse(fr.isSatisfiableBy(fp));

      fr.setVersionMatchRule(VersionMatchRule.EQUIVALENT);
      assertTrue(fr.isSatisfiableBy(fp));

      fr.setVersionMatchRule(VersionMatchRule.COMPATIBLE);
      assertTrue(fr.isSatisfiableBy(fp));

      fr.setVersionMatchRule(VersionMatchRule.GREATER_OR_EQUAL);
      assertTrue(fr.isSatisfiableBy(fp));

      fp.setId("fooooo");
      assertFalse(fr.isSatisfiableBy(fp));

      // reference can't distinguish between different project types
      PluginProject pp = eFactory.createPluginProject();
      pp.setId("foo.feature");
      pp.setVersion("1.0.0");
      assertTrue(fr.isSatisfiableBy(pp));
   }
}
