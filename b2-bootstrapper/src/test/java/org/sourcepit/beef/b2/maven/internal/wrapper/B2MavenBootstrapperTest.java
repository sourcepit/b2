/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.maven.internal.wrapper;

import java.io.File;
import java.util.List;

import org.sourcepit.tools.shared.resources.harness.AbstractWorkspaceTest;

/**
 * @author Bernd
 */
public class B2MavenBootstrapperTest extends AbstractWorkspaceTest
{
   public void testFindModuleDescriptors() throws Exception
   {
      File baseDir = workspace.importResources("composed-component");
      assertTrue(baseDir.canRead());

      List<File> descriptors = new B2MavenBootstrapper().findModuleDescriptors(baseDir);
      assertNotNull(descriptors);
      assertEquals(3, descriptors.size());
      // order differs on win and linux...
      // assertEquals(new File(baseDir, "simple-layout/module.xml"), descriptors.get(0));
      // assertEquals(new File(baseDir, "structured-layout/module.xml"), descriptors.get(1));
      assertEquals(new File(baseDir, "module.xml"), descriptors.get(2));
   }
}
