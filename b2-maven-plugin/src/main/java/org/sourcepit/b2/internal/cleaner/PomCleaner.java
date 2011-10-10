/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.cleaner;

import java.io.File;

import javax.inject.Named;

import org.sourcepit.b2.internal.cleaner.IModuleGarbageCollector;

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
