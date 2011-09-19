/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.maven.internal.wrapper;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sourcepit.beef.b2.common.internal.utils.PathMatcher;
import org.sourcepit.beef.b2.maven.internal.converter.IModuleDescriptorConverter;

@Component(role = ModuleDescriptorProcessor.class)
public class ModuleDescriptorProcessor
{
   @Requirement
   private Map<String, IModuleDescriptorConverter> fileNameToConverterMap;

   public void findModuleDescriptors(MavenSession session, Collection<File> descriptors,
      Collection<File> skippeDescriptors)
   {
      final String filterPattern = session.getUserProperties().getProperty("b2.modules",
         session.getSystemProperties().getProperty("b2.modules"));
      final File baseDir = new File(session.getRequest().getBaseDirectory());
      findModuleDescriptors(baseDir, descriptors, skippeDescriptors, filterPattern);
   }

   protected void findModuleDescriptors(final File baseDir, Collection<File> descriptors,
      Collection<File> skippeDescriptors, String filterPattern)
   {
      final PathMatcher defaultMatcher = PathMatcher.parseFilePatterns(baseDir, filterPattern == null
         ? "**"
         : filterPattern);

      final File moduleFile = findDescriptorFile(baseDir);
      if (moduleFile != null)
      {
         collectModuleFiles(descriptors, skippeDescriptors, baseDir, moduleFile, defaultMatcher);
         descriptors.add(moduleFile);
      }
   }

   private void collectModuleFiles(final Collection<File> descriptors, final Collection<File> skippeDescriptors,
      File moduleDir, File moduleDescriptor, final PathMatcher defaultMacher)
   {
      final PathMatcher macher = resolveMacher(moduleDescriptor, defaultMacher);

      moduleDir.listFiles(new FileFilter()
      {
         public boolean accept(File member)
         {
            final File moduleFile = findDescriptorFile(member);
            if (moduleFile != null)
            {
               collectModuleFiles(descriptors, skippeDescriptors, member, moduleFile, defaultMacher);
               // skipped ?
               if (!macher.isMatch(member.getPath()))
               {
                  skippeDescriptors.add(moduleFile);
               }
               descriptors.add(moduleFile);
            }
            return false;
         }
      });
   }

   private PathMatcher resolveMacher(File moduleDescriptor, PathMatcher defaultMacher)
   {
      try
      {
         final Model model = convert(moduleDescriptor);
         final String filterPattern = model.getProperties().getProperty("b2.modules");
         if (filterPattern != null)
         {
            return PathMatcher.parseFilePatterns(moduleDescriptor.getParentFile(), filterPattern);
         }
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
      return defaultMacher;
   }

   private File findDescriptorFile(File directory)
   {
      if (directory.isDirectory() && directory.canRead())
      {
         for (String fileName : fileNameToConverterMap.keySet())
         {
            final File projectFile = new File(directory, fileName);
            if (projectFile.isFile() && projectFile.canRead())
            {
               return projectFile;
            }
         }
      }
      return null;
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
