/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.cleaner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.logging.Logger;
import org.sourcepit.beef.b2.common.internal.utils.PathUtils;

@Named
public class FileServie implements IFileService
{
   @Inject
   private Logger logger;

   @Inject
   private List<IModuleGarbageCollector> participants;

   private final Set<String> ignored;

   public FileServie()
   {
      ignored = new HashSet<String>();
      ignored.add(".svn");
      ignored.add(".CVS");
      ignored.add(".git");
   }

   public void clean(final File dir)
   {
      final Collection<File> garbage = new LinkedHashSet<File>();
      accept(dir, new AbstractFileVisitor()
      {
         @Override
         public void visitGarbage(File file)
         {
            garbage.add(file);
         }
      });

      // don't delete files in visitor, because garbage collectors may relate on other garbage files to detect garbage,
      // e.g. pom.xml is needed to detect the target folder
      for (File file : garbage)
      {
         logger.info("Deleting " + (file.isDirectory() ? "directory" : "file") + " '"
            + PathUtils.getRelativePath(file, dir, File.separator) + "'");
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

   public void accept(File dir, final IFileVisitor visitor)
   {
      dir.listFiles(new FileFilter()
      {
         public boolean accept(File file)
         {
            if (ignored.contains(file.getName()))
            {
               return false;
            }

            for (IModuleGarbageCollector participant : participants)
            {
               if (participant.isGarbage(file))
               {
                  visitor.visitGarbage(file);
                  return false;
               }
            }
            if (visitor.visit(file) && file.isDirectory() && !new File(file, "module.xml").exists())
            {
               FileServie.this.accept(file, visitor);
            }
            return false;
         }
      });
   }
}
