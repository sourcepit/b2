/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.plugin.internal.DefaultLegacySupport;
import org.apache.maven.project.DefaultProjectDependenciesResolver;
import org.apache.maven.project.ProjectDependenciesResolver;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils.AbstractDescriptorResolutionStrategy;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils.IDescriptorResolutionStrategy;
import org.sourcepit.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Document;

import com.google.inject.Binder;

public abstract class AbstractB2SessionWorkspaceTest extends AbstractInjectedWorkspaceTest
{
   private List<File> projectDirs;

   @Override
   protected void setUp() throws Exception
   {
      super.setUp();

      final File moduleDir = setUpModuleDir();
      assertTrue(moduleDir.canRead());

      projectDirs = createSession(moduleDir.getAbsoluteFile());
      assertNotNull(projectDirs);
   }

   @Override
   public void configure(Binder binder)
   {
      super.configure(binder);
      binder.bind(LegacySupport.class).toInstance(new DefaultLegacySupport());
      binder.bind(ProjectDependenciesResolver.class).toInstance(new DefaultProjectDependenciesResolver());
   }

   protected List<File> getModuleDirs()
   {
      return projectDirs;
   }

   // HACK
   protected File getModuleDirByName(String name)
   {
      for (File projectDir : projectDirs)
      {
         final Document doc = XmlUtils.readXml(new File(projectDir, "module.xml"));
         if (name.equals(XmlUtils.queryText(doc, "/project/artifactId")))
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
