/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session;

import junit.framework.TestCase;

public class ModuleDependencyTest extends TestCase
{
   public void testIsSatisfiableBy() throws Exception
   {
      ModuleDependency d1 = SessionModelFactory.eINSTANCE.createModuleDependency();
      d1.setGroupId("d");
      d1.setArtifactId("d");
      d1.setVersionRange("[1.0.0,2.0.0)");

      ModuleProject p1 = SessionModelFactory.eINSTANCE.createModuleProject();
      p1.setGroupId("d");
      p1.setArtifactId("d");

      p1.setVersion("1.0.0");
      assertTrue(d1.isSatisfiableBy(p1));

      p1.setVersion("1.0.0-v2011");
      assertTrue(d1.isSatisfiableBy(p1));

      p1.setVersion("1.0.0.v2011");
      assertTrue(d1.isSatisfiableBy(p1));

      p1.setVersion("1.9.9");
      assertTrue(d1.isSatisfiableBy(p1));

      p1.setVersion("1.0");
      assertTrue(d1.isSatisfiableBy(p1));

      p1.setVersion("0.0.9");
      assertFalse(d1.isSatisfiableBy(p1));

      p1.setVersion("2.0.0");
      assertFalse(d1.isSatisfiableBy(p1));

      p1.setVersion("1.0-SNAPSHOT");
      assertFalse(d1.isSatisfiableBy(p1));

      d1.setVersionRange("1.0.0"); // actually means everything but 1.0.0 recommended
      p1.setVersion("1.0");
      assertTrue(d1.isSatisfiableBy(p1));

      p1.setVersion("2.1.1");
      assertFalse(d1.isSatisfiableBy(p1));

      p1.setVersion("0.1.1");
      assertFalse(d1.isSatisfiableBy(p1));
   }
}
