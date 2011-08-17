/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.cleaner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.logging.Logger;

@Named
public class ModuleCleaner
{
   @Inject
   private Logger logger;

   @Inject
   private List<IModuleCleanerParticipant> participants;

   public void clean(File moduleDir)
   {
      logger.info("Cleaning module folder: " + moduleDir.getName());

      final Set<File> garbage = new LinkedHashSet<File>();
      collectGarbage(moduleDir, garbage);
      if (!garbage.isEmpty())
      {
         for (File file : garbage)
         {
            logger.info("Deleting " + (file.isDirectory() ? "directory" : "file") + " '" + file.getName() + "'");
            try
            {
               FileUtils.forceDelete(file);
            }
            catch (IOException e)
            {
               throw new IllegalStateException(e);
            }
         }
      }
   }

   private void collectGarbage(File dir, final Set<File> garbage)
   {
      dir.listFiles(new FileFilter()
      {
         public boolean accept(File file)
         {
            for (IModuleCleanerParticipant participant : participants)
            {
               if (participant.isGarbage(file))
               {
                  garbage.add(file);
                  return false;
               }
            }
            if (file.isDirectory() && !new File(file, "module.xml").exists())
            {
               collectGarbage(file, garbage);
            }
            return false;
         }
      });
   }
}
