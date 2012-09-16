/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class ModuleDependenciesIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void testLocal() throws Exception
   {
      final File modulesDir = getResource(getClass().getSimpleName());

      // build module a
      final File moduleADir = new File(modulesDir, "module-a");
      assertTrue(moduleADir.exists());
      int err = build(moduleADir, "-e", "-B", "install", "-DfailIfNoTests=false");
      assertThat(err, is(0));

      // <b2.aggregator.mode>unwrap</b2.aggregator.mode>
      // build module b (depends on module a)
      final File moduleBDir = new File(modulesDir, "module-b");
      assertTrue(moduleBDir.exists());
      err = build(moduleBDir, "-e", "-B", "verify", "-DfailIfNoTests=false");
      assertThat(err, is(0));
   }

   @Test
   public void testLocal_ModeUnwrap() throws Exception
   {
      final File modulesDir = getResource(getClass().getSimpleName());

      // build module a
      final File moduleADir = new File(modulesDir, "module-a");
      assertTrue(moduleADir.exists());
      int err = build(moduleADir, "-e", "-B", "install", "-DfailIfNoTests=false");
      assertThat(err, is(0));

      // <b2.aggregator.mode>unwrap</b2.aggregator.mode>
      // build module b (depends on module a)
      final File moduleBDir = new File(modulesDir, "module-b");
      assertTrue(moduleBDir.exists());
      err = build(moduleBDir, "-e", "-B", "verify", "-DfailIfNoTests=false", "-Db2.aggregator.mode=unwrap");
      assertThat(err, is(0));
   }

   @Test
   public void testLocal_ModeAggregate() throws Exception
   {
      final File modulesDir = getResource(getClass().getSimpleName());

      // build module a
      final File moduleADir = new File(modulesDir, "module-a");
      assertTrue(moduleADir.exists());
      int err = build(moduleADir, "-e", "-B", "install", "-DfailIfNoTests=false");
      assertThat(err, is(0));

      // <b2.aggregator.mode>unwrap</b2.aggregator.mode>
      // build module b (depends on module a)
      final File moduleBDir = new File(modulesDir, "module-b");
      assertTrue(moduleBDir.exists());
      err = build(moduleBDir, "-e", "-B", "verify", "-DfailIfNoTests=false", "-Db2.aggregator.mode=aggregate");
      assertThat(err, is(0));
   }

}
