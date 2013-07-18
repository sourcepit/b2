/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleFiles
{
   private final File moduleDir;

   public ModuleFiles(File moduleDir)
   {
      this.moduleDir = moduleDir;
   }

   public File getModuleDir()
   {
      return moduleDir;
   }

   public boolean isModuleFile(File file, boolean allowDerived)
   {
      if (isHidden(file))
      {
         return false;
      }
      if (!allowDerived && isDerived(file))
      {
         return false;
      }
      return isParentOf(moduleDir, file);
   }

   private static boolean isParentOf(File dir, File file)
   {
      final File parent = file.getParentFile();
      if (dir.equals(parent))
      {
         return true;
      }
      return parent == null ? false : isParentOf(dir, parent);
   }

   private final Map<File, Boolean> hiddenCache = new ConcurrentHashMap<File, Boolean>();

   public boolean isHidden(File file)
   {
      Boolean hidden = hiddenCache.get(file);
      if (hidden == null)
      {
         hidden = Boolean.valueOf(computeIsHidden(file));
         hiddenCache.put(file, hidden);
      }
      return hidden.booleanValue();
   }

   private boolean computeIsHidden(File file)
   {
      return false;
   }


   private final Map<File, Boolean> derivedCache = new ConcurrentHashMap<File, Boolean>();

   public boolean isDerived(File file)
   {
      Boolean derived = derivedCache.get(file);
      if (derived == null)
      {
         derived = Boolean.valueOf(computeIsDerived(file));
         derivedCache.put(file, derived);
      }
      return derived.booleanValue();
   }

   private boolean computeIsDerived(File file)
   {
      return false;
   }

   public void accept(ModuleFileVisitor visitor)
   {
      accept(getModuleDir(), visitor, false, 0, -1);
   }


   private void accept(File file, final ModuleFileVisitor visitor, final boolean visitDerived, final int currentDepth,
      final int maxDepth)
   {
      if (maxDepth <= currentDepth || maxDepth == -1)
      {
         file.listFiles(new FileFilter()
         {
            @Override
            public boolean accept(File file)
            {
               if (isModuleFile(file, visitDerived) && visitor.visit(file, isDerived(file)))
               {
                  ModuleFiles.this.accept(file, visitor, visitDerived, currentDepth + 1, maxDepth);
               }
               return false;
            }
         });
      }
   }
}
