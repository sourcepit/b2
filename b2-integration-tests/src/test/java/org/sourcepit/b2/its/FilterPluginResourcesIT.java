/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class FilterPluginResourcesIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return true;
   }

   @Test
   public void test() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());

      assertTrue(moduleDir.exists());
      int err = build(moduleDir, "-e", "-B", "install");
      assertThat(err, is(0));
   }

}
