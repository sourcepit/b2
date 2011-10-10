/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.test.resources.internal.harness;

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
