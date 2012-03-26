/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.cleaner;

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
import javax.inject.Singleton;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.logging.Logger;
import org.sourcepit.b2.common.internal.utils.PathUtils;
import org.sourcepit.b2.directory.parser.internal.module.LifecyclePhase;
import org.sourcepit.common.utils.lang.ThrowablePipe;

@Named
@Singleton
public class FileServie implements IFileService
{
   @Inject
   private Logger logger;

   @Inject
   private List<IModuleGarbageCollector> participants;

   @Inject
   private List<ModuleCleanerLifecycleParticipant> lifecycleParticipants;

   private final Set<String> ignored;

   public FileServie()
   {
      ignored = new HashSet<String>();
      ignored.add(".svn");
      ignored.add(".CVS");
      ignored.add(".git");
   }

   public void clean(File dir)
   {
      newLifecyclePhase().execute(dir);
   }

   private LifecyclePhase<Void, File, ModuleCleanerLifecycleParticipant> newLifecyclePhase()
   {
      return new LifecyclePhase<Void, File, ModuleCleanerLifecycleParticipant>(lifecycleParticipants)
      {
         @Override
         protected void pre(ModuleCleanerLifecycleParticipant participant, File moduleDir)
         {
            participant.preClean(moduleDir);
         }

         @Override
         protected Void doExecute(File moduleDir)
         {
            doClean(moduleDir);
            return null;
         }

         @Override
         protected void post(ModuleCleanerLifecycleParticipant participant, File moduleDir, Void result,
            ThrowablePipe errors)
         {
            participant.postClean(moduleDir, errors);
         }
      };
   }

   private void doClean(final File dir)
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
         logger.debug("Deleting " + (file.isDirectory() ? "directory" : "file") + " '"
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
      logger.info("Deleted " + garbage.size() + " files");
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
