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

package org.sourcepit.b2.directory.parser.internal.project;

import static java.lang.Integer.valueOf;
import static org.sourcepit.b2.directory.parser.internal.project.ProjectResourcesPreprocessor.getResourcesDir;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_FORBIDDEN;
import static org.sourcepit.common.utils.lang.Exceptions.pipe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.files.FileFlagsInvestigator;
import org.sourcepit.b2.files.FileFlagsProvider;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.resources.FileTraverser;
import org.sourcepit.common.utils.resources.Resource;
import org.sourcepit.common.utils.resources.ResourceVisitor;

@Named
public class ProjectResourcesFileFlagsProvider implements FileFlagsProvider
{
   private final ProjectDetector projectDetector;

   @Inject
   public ProjectResourcesFileFlagsProvider(ProjectDetector projectDetector)
   {
      this.projectDetector = projectDetector;
   }

   @Override
   public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties)
   {
      return null;
   }

   @Override
   public FileFlagsInvestigator createFileFlagsInvestigator(File moduleDir, final PropertiesSource properties)
   {
      return new ProjectResourcesFileFlagsInvestigator(projectDetector, properties);
   }

   private static class ProjectResourcesFileFlagsInvestigator implements FileFlagsInvestigator
   {
      private final ProjectDetector projectDetector;

      private final PropertiesSource properties;

      private final Map<File, Integer> fileFlags = new HashMap<File, Integer>();

      private ProjectResourcesFileFlagsInvestigator(ProjectDetector projectDetector, PropertiesSource properties)
      {
         this.projectDetector = projectDetector;
         this.properties = properties;
      }

      @Override
      public Map<File, Integer> getAdditionallyFoundFileFlags()
      {
         return fileFlags;
      }

      @Override
      public int determineFileFlags(File file)
      {
         final Integer flags = fileFlags.remove(file);
         if (flags != null)
         {
            return flags.intValue();
         }

         if (isProjectDir(file))
         {
            processProjectDir(file);
         }

         return 0;
      }

      private void processProjectDir(final File projectDir)
      {
         final File resourcesDir = getResourcesDir(projectDir, properties);
         if (resourcesDir.exists() && resourcesDir.isDirectory())
         {
            fileFlags.put(resourcesDir, valueOf(FLAG_FORBIDDEN));

            final ResourceVisitor visitor = new ResourceVisitor()
            {
               @Override
               public void visit(Resource resource, InputStream content) throws IOException
               {
                  final File file = new File(projectDir, resource.getPath().toString());
                  if (!resource.isDirectory() || !file.exists())
                  {
                     fileFlags.put(file, valueOf(FLAG_DERIVED));
                  }
               }
            };

            try
            {
               new FileTraverser(resourcesDir).accept(visitor);
            }
            catch (IOException e)
            {
               throw pipe(e);
            }
         }
      }

      private boolean isProjectDir(File file)
      {
         return file.isDirectory() && projectDetector.detect(file, properties) != null;
      }
   }

}
