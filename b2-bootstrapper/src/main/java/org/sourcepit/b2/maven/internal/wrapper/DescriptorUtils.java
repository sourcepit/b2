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
import java.io.FileFilter;
import java.util.Collection;

import org.sourcepit.common.utils.path.PathMatcher;

public final class DescriptorUtils
{
   private DescriptorUtils()
   {
      super();
   }

   public static void findModuleDescriptors(final File baseDir, Collection<File> descriptors,
      Collection<File> skippeDescriptors, IDescriptorResolutionStrategy resolver)
   {
      final File moduleFile = findDescriptorFile(baseDir, resolver);
      if (moduleFile != null)
      {
         collectModuleDirectory(descriptors, skippeDescriptors, baseDir, moduleFile, resolver);
         descriptors.add(moduleFile);
      }
   }

   private static void collectModuleDirectory(final Collection<File> descriptors,
      final Collection<File> skippeDescriptors, File moduleDir, final File moduleDescriptor,
      final IDescriptorResolutionStrategy resolver)
   {
      moduleDir.listFiles(new FileFilter()
      {
         public boolean accept(File member)
         {
            final File moduleFile = findDescriptorFile(member, resolver);
            if (moduleFile != null)
            {
               collectModuleDirectory(descriptors, skippeDescriptors, member, moduleFile, resolver);
               // skipped ?
               if (resolver.isSkipped(moduleDescriptor, moduleFile))
               {
                  skippeDescriptors.add(moduleFile);
               }
               descriptors.add(moduleFile);
            }
            return false;
         }
      });
   }


   private static File findDescriptorFile(File directory, IDescriptorResolutionStrategy resolver)
   {
      if (directory.isDirectory() && directory.canRead())
      {
         return resolver.getDescriptor(directory);
      }
      return null;
   }

   public static interface IDescriptorResolutionStrategy
   {
      File getDescriptor(File directory);

      boolean isSkipped(File parentDescriptor, File descriptor);
   }

   public abstract static class AbstractDescriptorResolutionStrategy implements IDescriptorResolutionStrategy
   {
      private PathMatcher defaultMatcher;

      public AbstractDescriptorResolutionStrategy(File baseDir, String defaultFilterPattern)
      {
         defaultMatcher = PathMatcher.parseFilePatterns(baseDir, defaultFilterPattern == null
            ? "**"
            : defaultFilterPattern);
      }

      public boolean isSkipped(File parentDescriptor, File descriptor)
      {
         return !getMacher(parentDescriptor).isMatch(descriptor.getParentFile().getPath());
      }

      protected PathMatcher getMacher(File parentDescriptor)
      {
         final String filterPattern = getFilterPattern(parentDescriptor);
         if (filterPattern != null)
         {
            return PathMatcher.parseFilePatterns(parentDescriptor.getParentFile(), filterPattern);
         }
         return defaultMatcher;
      }

      protected String getFilterPattern(File descriptor)
      {
         return null;
      }
   }
}
