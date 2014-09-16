/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class FileFlagsIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void test() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));

      PropertiesMap props = new LinkedPropertiesMap();
      props.load(new File(moduleDir, ".b2/moduleDirectory.properties"));

      assertEquals(5, props.size());

      assertEquals("3", props.get(".b2")); // hidden, derived
      assertEquals("4", props.get("module-a")); // forbidden (via b2.modules property)
      assertEquals("14", props.get("module-b")); // module, forbidden, hidden

      assertEquals("6", props.get("target")); // derived, forbidden
      assertEquals("3", props.get("pom.xml")); // derived, hidden
   }
}
