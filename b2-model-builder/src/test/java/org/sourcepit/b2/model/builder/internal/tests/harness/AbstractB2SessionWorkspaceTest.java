/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.plugin.internal.DefaultLegacySupport;
import org.apache.maven.project.DefaultProjectDependenciesResolver;
import org.apache.maven.project.ProjectDependenciesResolver;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils.AbstractDescriptorResolutionStrategy;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils.IDescriptorResolutionStrategy;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;

import com.google.inject.Binder;

public abstract class AbstractB2SessionWorkspaceTest extends AbstractInjectedWorkspaceTest
{
   @Inject
   protected B2SessionService sessionService;

   @Override
   protected void setUp() throws Exception
   {
      super.setUp();

      final File moduleDir = setUpModuleDir();
      assertTrue(moduleDir.canRead());

      List<File> projectDirs = createSession(moduleDir.getAbsoluteFile());
      assertNotNull(projectDirs);

      sessionService.setCurrentProjectDirs(projectDirs);
      sessionService.setCurrentResourceSet(new ResourceSetImpl());
   }

   @Override
   public void configure(Binder binder)
   {
      super.configure(binder);
      binder.bind(LegacySupport.class).toInstance(new DefaultLegacySupport());
      binder.bind(ProjectDependenciesResolver.class).toInstance(new DefaultProjectDependenciesResolver());
   }

   protected List<File> getCurrentSession()
   {
      return sessionService.getCurrentProjectDirs();
   }

   // HACK
   protected File getModuleDirByName(String name)
   {
      for (File projectDir : sessionService.getCurrentProjectDirs())
      {
         if (name.equals(projectDir.getName()))
         {
            return projectDir;
         }
      }
      return null;
   }

   protected File setUpModuleDir()
   {
      return workspace.importResources(setUpModulePath());
   }

   protected abstract String setUpModulePath();

   protected List<File> createSession(File moduleDir)
   {
      final List<File> result = new ArrayList<File>();

      final List<File> descriptors = new ArrayList<File>();
      final List<File> skippedDescriptors = new ArrayList<File>();

      final IDescriptorResolutionStrategy resolver = new AbstractDescriptorResolutionStrategy(moduleDir, null)
      {
         public File getDescriptor(File directory)
         {
            final File descriptor = new File(directory, "module.xml");
            if (descriptor.exists() && descriptor.isFile())
            {
               return descriptor;
            }
            return null;
         }
      };

      DescriptorUtils.findModuleDescriptors(moduleDir, descriptors, skippedDescriptors, resolver);

      for (File descriptor : descriptors)
      {
         if (!skippedDescriptors.contains(descriptor))
         {
            result.add(descriptor.getParentFile());
         }
      }
      return result;
   }
}
