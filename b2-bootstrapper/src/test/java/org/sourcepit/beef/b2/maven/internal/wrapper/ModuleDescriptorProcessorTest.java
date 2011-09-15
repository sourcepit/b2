/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.maven.internal.wrapper;

import java.io.File;
import java.util.List;

import org.sourcepit.beef.b2.test.resources.internal.harness.AbstractPlexusWorkspaceTest;

public class ModuleDescriptorProcessorTest extends AbstractPlexusWorkspaceTest
{
   public void testFindModuleDescriptors() throws Exception
   {
      File baseDir = workspace.importResources("composed-component");
      assertTrue(baseDir.canRead());

      List<File> descriptors = lookup().findModuleDescriptors(baseDir, null);
      assertNotNull(descriptors);
      assertEquals(3, descriptors.size());
      // order differs on win and linux...
      // assertEquals(new File(baseDir, "simple-layout/module.xml"), descriptors.get(0));
      // assertEquals(new File(baseDir, "structured-layout/module.xml"), descriptors.get(1));
      assertEquals(new File(baseDir, "module.xml"), descriptors.get(2));
   }

   protected ModuleDescriptorProcessor lookup() throws Exception
   {
      return lookup(ModuleDescriptorProcessor.class);
   }
}
