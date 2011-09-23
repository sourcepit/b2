/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder.internal.tests.harness;

import java.io.File;

import org.sourcepit.beef.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;

public class SessionWorkspaceTest extends AbstractInjectedWorkspaceTest
{
   public void testCreateSession() throws Exception
   {
      File coreResources = workspace.importResources("composed-component");
      assertTrue(coreResources.canRead());
      
      
   }
}
