/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.test.resources.internal.harness;

import org.codehaus.plexus.ContainerConfiguration;
import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.PlexusTestCase;
import org.sourcepit.tools.shared.resources.internal.harness.MavenTestWorkspace;

public abstract class AbstractPlexusWorkspaceTest extends PlexusTestCase
{
   protected MavenTestWorkspace workspace = new MavenTestWorkspace(this, false);

   @Override
   protected void customizeContainerConfiguration(ContainerConfiguration cc)
   {
      super.customizeContainerConfiguration(cc);
      cc.setClassPathScanning(PlexusConstants.SCANNING_INDEX).setAutoWiring(true).setName("maven");
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
