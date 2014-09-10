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

package org.sourcepit.b2.internal.cleaner;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;
import static org.sourcepit.common.utils.path.PathUtils.getRelativePath;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.sourcepit.b2.files.FileVisitor;
import org.sourcepit.b2.files.ModuleDirectory;

@Named
@Singleton
public class FileServie implements IFileService
{
   private class Cleaner implements FileVisitor<IOException>
   {
      private final File moduleDir;
      private int counter = 0;

      public int getCounter()
      {
         return counter;
      }

      private Cleaner(File moduleDir)
      {
         this.moduleDir = moduleDir;
      }

      @Override
      public boolean visit(File file, int flags) throws IOException
      {
         if ((FLAG_DERIVED & flags) != 0)
         {
            logger.debug("Deleting '" + getRelativePath(file, moduleDir, separator) + "'");
            forceDelete(file);
            counter++;
            return false;
         }
         return true;
      }
   }

   @Inject
   private Logger logger;

   private final Set<String> ignored;

   public FileServie()
   {
      ignored = new HashSet<String>();
      ignored.add(".svn");
      ignored.add(".CVS");
      ignored.add(".git");
   }

   public void clean(ModuleDirectory moduleDirectory) throws IOException
   {
      final File moduleDir = moduleDirectory.getFile();

      int counter = 0;

      final File fileFlagsFile = new File(moduleDir, ".b2/moduleDirectory.properties");
      if (fileFlagsFile.exists())
      {
         counter += cleanFilesFromPreviousBuild(moduleDir, fileFlagsFile);
      }
      counter += doClean(moduleDir, moduleDirectory);

      logger.info("Deleted " + counter + " files");
   }

   private int cleanFilesFromPreviousBuild(final File moduleDir, final File fileFlagsFile) throws IOException
   {
      final ModuleDirectory moduleDirectory = ModuleDirectory.load(moduleDir, fileFlagsFile);
      return doClean(moduleDir, moduleDirectory);
   }

   private int doClean(final File moduleDir, final ModuleDirectory moduleDirectory) throws IOException
   {
      final Cleaner cleaner = new Cleaner(moduleDir);
      moduleDirectory.accept(cleaner, true, true);
      return cleaner.getCounter();
   }

}
