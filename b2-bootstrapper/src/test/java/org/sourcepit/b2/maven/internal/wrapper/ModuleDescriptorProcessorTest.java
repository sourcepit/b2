/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.maven.internal.wrapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.sourcepit.b2.maven.internal.wrapper.ModuleDescriptorProcessor;
import org.sourcepit.b2.test.resources.internal.harness.AbstractPlexusWorkspaceTest;

public class ModuleDescriptorProcessorTest extends AbstractPlexusWorkspaceTest
{
   public void testFindModuleDescriptors() throws Exception
   {
      File baseDir = workspace.importResources("composed-component");
      assertTrue(baseDir.canRead());

      final MavenSession mavenSession = createMavenSession(baseDir);

      final List<File> descriptors = new ArrayList<File>();
      final Collection<File> skippedDescriptors = new HashSet<File>();
      lookup().findModuleDescriptors(mavenSession, descriptors, skippedDescriptors);

      assertNotNull(descriptors);
      assertEquals(3, descriptors.size());
      assertEquals(0, skippedDescriptors.size());
      // order differs on win and linux...
      // assertEquals(new File(baseDir, "simple-layout/module.xml"), descriptors.get(0));
      // assertEquals(new File(baseDir, "structured-layout/module.xml"), descriptors.get(1));
      assertEquals(new File(baseDir, "module.xml").getAbsoluteFile(), descriptors.get(2).getAbsoluteFile());
   }

   protected MavenSession createMavenSession(File baseDir)
   {
      MavenExecutionRequest request = new DefaultMavenExecutionRequest();
      request.setBaseDirectory(baseDir);
      MavenSession mavenSession = mock(MavenSession.class);
      when(mavenSession.getSystemProperties()).thenReturn(new Properties());
      when(mavenSession.getUserProperties()).thenReturn(new Properties());
      when(mavenSession.getRequest()).thenReturn(request);
      return mavenSession;
   }

   public void testFindModuleDescriptorsWithExclude() throws Exception
   {
      File baseDir = workspace.importResources("composed-component");
      assertTrue(baseDir.canRead());

      final MavenSession mavenSession = createMavenSession(baseDir);
      mavenSession.getUserProperties().setProperty("b2.modules", "!simple-layout");

      final List<File> descriptors = new ArrayList<File>();
      final List<File> skippedDescriptors = new ArrayList<File>();
      lookup().findModuleDescriptors(mavenSession, descriptors, skippedDescriptors);

      assertNotNull(descriptors);
      assertEquals(3, descriptors.size());
      assertEquals(1, skippedDescriptors.size());
      assertEquals(new File(baseDir, "simple-layout/module.xml").getAbsoluteFile(), skippedDescriptors.get(0)
         .getAbsoluteFile());

      // order differs on win and linux...
      // assertEquals(new File(baseDir, "simple-layout/module.xml"), descriptors.get(0));
      // assertEquals(new File(baseDir, "structured-layout/module.xml"), descriptors.get(1));
      assertEquals(new File(baseDir, "module.xml").getAbsoluteFile(), descriptors.get(2).getAbsoluteFile());
   }

   protected ModuleDescriptorProcessor lookup() throws Exception
   {
      return lookup(ModuleDescriptorProcessor.class);
   }
}
