/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.maven.internal.wrapper;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sourcepit.beef.b2.common.internal.utils.DescriptorUtils;
import org.sourcepit.beef.b2.common.internal.utils.DescriptorUtils.AbstractDescriptorResolutionStrategy;
import org.sourcepit.beef.b2.common.internal.utils.DescriptorUtils.IDescriptorResolutionStrategy;
import org.sourcepit.beef.b2.maven.internal.converter.IModuleDescriptorConverter;

@Component(role = ModuleDescriptorProcessor.class)
public class ModuleDescriptorProcessor
{
   @Requirement
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
