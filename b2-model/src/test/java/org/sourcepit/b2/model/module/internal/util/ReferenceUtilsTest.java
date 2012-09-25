/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
      fp.setVersion("1.0.0.qualifer");
      
      assertTrue(fr.isSatisfiableBy(fp));
      
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
