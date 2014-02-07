/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class CustomModuleIdIT extends AbstractB2IT
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
      
      final File parentDir = new File(moduleDir, "parent");
      assertTrue(parentDir.exists());
      assertEquals("foo.parent", loadModule(parentDir).getId());
      assertEquals("foo.parent", loadMavenModel(parentDir).getProperties().getProperty("module.id"));
      
      final File moduleADir = new File(moduleDir, "module-a");
      assertTrue(moduleADir.exists());
      assertEquals("foo.a", loadModule(moduleADir).getId());
      assertEquals("foo.a", loadMavenModel(moduleADir).getProperties().getProperty("module.id"));
      
      // test "do not inherit module.id"
      final File moduleBDir = new File(moduleDir, "module-b");
      assertTrue(moduleBDir.exists());
      assertEquals("org.sourcepit.b2.its.module.b", loadModule(moduleBDir).getId());
      assertEquals("org.sourcepit.b2.its.module.b", loadMavenModel(moduleBDir).getProperties().getProperty("module.id"));
      
      assertEquals("org.sourcepit.b2.its.CustomModuleIdIT", loadModule(moduleDir).getId());
      assertEquals("org.sourcepit.b2.its.CustomModuleIdIT", loadMavenModel(moduleDir).getProperties().getProperty("module.id"));
   }
}
