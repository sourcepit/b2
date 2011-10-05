/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.cleaner;

import java.io.File;

import javax.inject.Named;

@Named
public class PomCleaner implements IModuleGarbageCollector
{
   public boolean isGarbage(File file)
   {
      final File parentDir = file.getParentFile();
      if (isInModule(file) && isMavenProject(parentDir))
      {
         if (file.isFile() && "pom.xml".equals(file.getName()))
         {
            return true;
         }

         if (file.isDirectory() && "target".equals(file.getName()))
         {
            return true;
         }
      }
      return false;
   }

   private boolean isMavenProject(File parentDir)
   {
      return new File(parentDir, "pom.xml").exists();
   }

   private boolean isInModule(File file)
   {
      final File parentDir = file.getParentFile();
      if (parentDir == null)
      {
         return false;
      }
      if (isModuleDir(parentDir))
      {
         return true;
      }
      return isInModule(parentDir);
   }

   private boolean isModuleDir(File parentDir)
   {
      return new File(parentDir, "module.xml").exists();
   }
}
