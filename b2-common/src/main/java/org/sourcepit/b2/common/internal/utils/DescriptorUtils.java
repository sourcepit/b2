/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.common.internal.utils;

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
         collectModuleFiles(descriptors, skippeDescriptors, baseDir, moduleFile, resolver);
         descriptors.add(moduleFile);
      }
   }

   private static void collectModuleFiles(final Collection<File> descriptors, final Collection<File> skippeDescriptors,
      File moduleDir, final File moduleDescriptor, final IDescriptorResolutionStrategy resolver)
   {
      moduleDir.listFiles(new FileFilter()
      {
         public boolean accept(File member)
         {
            final File moduleFile = findDescriptorFile(member, resolver);
            if (moduleFile != null)
            {
               collectModuleFiles(descriptors, skippeDescriptors, member, moduleFile, resolver);
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
