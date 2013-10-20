/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.cleaner;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;
import static org.sourcepit.common.utils.path.PathUtils.getRelativePath;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.sourcepit.b2.files.FileVisitor;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

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

   public void clean(ModuleDirectory moduleFiles) throws IOException
   {
      final File moduleDir = moduleFiles.getFile();

      int counter = 0;

      final File fileFlagsFile = new File(moduleDir, ".b2/moduleFiles.properties");
      if (fileFlagsFile.exists())
      {
         counter += cleanFilesFromPreviousBuild(moduleDir, fileFlagsFile);
      }
      counter += doClean(moduleDir, moduleFiles);

      logger.info("Deleted " + counter + " files");
   }

   private int cleanFilesFromPreviousBuild(final File moduleDir, final File fileFlagsFile) throws IOException
   {
      final ModuleDirectory moduleFiles = loadModuleFiles(moduleDir, fileFlagsFile);
      return doClean(moduleDir, moduleFiles);
   }

   private int doClean(final File moduleDir, final ModuleDirectory moduleFiles) throws IOException
   {
      final Cleaner cleaner = new Cleaner(moduleDir);
      moduleFiles.accept(cleaner, true, true);
      return cleaner.getCounter();
   }

   private static ModuleDirectory loadModuleFiles(final File moduleDir, final File fileFlagsFile)
   {
      final PropertiesMap props = new LinkedPropertiesMap();
      props.load(fileFlagsFile);
      final Map<File, Integer> fileFlags = new HashMap<File, Integer>();
      for (Entry<String, String> entry : props.entrySet())
      {
         final File file = new File(moduleDir, entry.getKey());
         final Integer flags = Integer.valueOf(entry.getValue());
         fileFlags.put(file, flags);
      }
      return new ModuleDirectory(moduleDir, fileFlags);
   }
}
