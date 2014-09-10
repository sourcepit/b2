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

package org.sourcepit.b2.maven.internal.wrapper;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.sourcepit.b2.maven.internal.converter.IModuleDescriptorConverter;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils.AbstractDescriptorResolutionStrategy;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils.IDescriptorResolutionStrategy;

@Named
public class ModuleDescriptorProcessor
{
   @Inject
   private Map<String, IModuleDescriptorConverter> fileNameToConverterMap;

   public void findModuleDescriptors(MavenSession session, Collection<File> descriptors,
      Collection<File> skippedDescriptors)
   {
      final String defaultFilterPattern = session.getUserProperties().getProperty("b2.modules",
         session.getSystemProperties().getProperty("b2.modules", "**"));

      final File baseDir = new File(session.getRequest().getBaseDirectory());

      final IDescriptorResolutionStrategy resolver = new AbstractDescriptorResolutionStrategy(baseDir,
         defaultFilterPattern)
      {
         public File getDescriptor(File directory)
         {
            for (String fileName : fileNameToConverterMap.keySet())
            {
               final File projectFile = new File(directory, fileName);
               if (projectFile.isFile() && projectFile.canRead())
               {
                  return projectFile;
               }
            }
            return null;
         }

         @Override
         protected String getFilterPattern(File descriptor)
         {
            try
            {
               return convert(descriptor).getProperties().getProperty("b2.modules");
            }
            catch (IOException e)
            {
               throw new IllegalArgumentException(e);
            }
         }
      };
      DescriptorUtils.findModuleDescriptors(baseDir, descriptors, skippedDescriptors, resolver);
   }

   public Model convert(final File descriptor) throws IOException
   {
      final IModuleDescriptorConverter converter = fileNameToConverterMap.get(descriptor.getName());
      if (converter != null)
      {
         return converter.convert(descriptor);
      }
      return null;
   }
}
