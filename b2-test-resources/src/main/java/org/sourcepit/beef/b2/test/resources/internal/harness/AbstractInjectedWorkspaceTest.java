/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.test.resources.internal.harness;

import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.sonatype.guice.bean.containers.InjectedTestCase;
import org.sourcepit.tools.shared.resources.internal.harness.MavenTestWorkspace;

import com.google.inject.Binder;

public abstract class AbstractInjectedWorkspaceTest extends InjectedTestCase
{
   protected MavenTestWorkspace workspace = new MavenTestWorkspace(this, false);

   @Override
   public void configure(Binder binder)
   {
      super.configure(binder);
      binder.bind(Logger.class).toInstance(new ConsoleLogger());
   }
   
   @Override
   protected void setUp() throws Exception
   {
      workspace.startUp();
      super.setUp();
   }

   public MavenTestWorkspace getWorkspace()
   {
      return workspace;
   }

   @Override
   protected void tearDown() throws Exception
   {
      super.tearDown();
      workspace.tearDown();
   }
}
