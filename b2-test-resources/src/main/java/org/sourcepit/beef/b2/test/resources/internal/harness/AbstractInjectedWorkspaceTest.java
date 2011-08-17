/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.test.resources.internal.harness;

import org.sonatype.guice.bean.containers.InjectedTestCase;
import org.sourcepit.tools.shared.resources.internal.harness.MavenTestWorkspace;

public abstract class AbstractInjectedWorkspaceTest extends InjectedTestCase
{
   protected MavenTestWorkspace workspace = new MavenTestWorkspace(this, false);

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
