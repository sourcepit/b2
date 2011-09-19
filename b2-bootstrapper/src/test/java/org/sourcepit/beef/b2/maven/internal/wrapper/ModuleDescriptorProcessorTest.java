/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.maven.internal.wrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.sourcepit.beef.b2.test.resources.internal.harness.AbstractPlexusWorkspaceTest;

public class ModuleDescriptorProcessorTest extends AbstractPlexusWorkspaceTest
{
   public void testFindModuleDescriptors() throws Exception
   {
      File baseDir = workspace.importResources("composed-component");
      assertTrue(baseDir.canRead());

      final List<File> descriptors = new ArrayList<File>();
      final Collection<File> skippedDescriptors = new HashSet<File>();
      lookup().findModuleDescriptors(baseDir, descriptors, skippedDescriptors, null);

      assertNotNull(descriptors);
      assertEquals(3, descriptors.size());
      assertEquals(0, skippedDescriptors.size());
      // order differs on win and linux...
      // assertEquals(new File(baseDir, "simple-layout/module.xml"), descriptors.get(0));
      // assertEquals(new File(baseDir, "structured-layout/module.xml"), descriptors.get(1));
      assertEquals(new File(baseDir, "module.xml"), descriptors.get(2));
   }

   public void testFindModuleDescriptorsWithExclude() throws Exception
   {
      File baseDir = workspace.importResources("composed-component");
      assertTrue(baseDir.canRead());

      final List<File> descriptors = new ArrayList<File>();
      final List<File> skippedDescriptors = new ArrayList<File>();
      lookup().findModuleDescriptors(baseDir, descriptors, skippedDescriptors, "!simple-layout");

      assertNotNull(descriptors);
      assertEquals(3, descriptors.size());
      assertEquals(1, skippedDescriptors.size());
      assertEquals(new File(baseDir, "simple-layout/module.xml"), skippedDescriptors.get(0));

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
