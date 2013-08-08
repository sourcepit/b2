/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static org.sourcepit.common.utils.file.FileUtils.isParentOf;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleFiles
{
   public static final int FLAG_FORBIDDEN = 0x04;

   public static final int FLAG_HIDDEN = 0x02;

   public static final int FLAG_DERIVED = 0x01;

   private final File moduleDir;

   private Map<File, Integer> fileFlags;

   private final Map<File, Integer> aggregatedFlags = new ConcurrentHashMap<File, Integer>();

   public ModuleFiles(File moduleDir, Map<File, Integer> fileFlags)
   {
      this.moduleDir = moduleDir;
      this.fileFlags = fileFlags;
   }

   public File getModuleDir()
   {
      return moduleDir;
   }

   public boolean isModuleFile(File file)
   {
      return isModuleFile(file, ~FLAG_DERIVED);
   }

   public boolean isModuleFile(File file, boolean includeHidden, boolean includeDerived)
   {
      return isModuleFile(file, toFlagMask(includeHidden, includeDerived));
   }

   private static int toFlagMask(boolean includeHidden, boolean includeDerived)
   {
      int flagMask = 0xff;
      if (includeHidden)
      {
         flagMask ^= FLAG_HIDDEN;
      }
      if (includeDerived)
      {
         flagMask ^= FLAG_DERIVED;
      }
      return flagMask;
   }

   private boolean isModuleFile(File file, int flagMask)
   {
      if ((getFlags(file) & flagMask) != 0)
      {
         return false;
      }
      return isParentOf(moduleDir, file);
   }

   public boolean isHidden(File file)
   {
      return hasFlag(file, FLAG_HIDDEN);
   }

   public boolean isDerived(File file)
   {
      return hasFlag(file, FLAG_DERIVED);
   }

   public boolean hasFlag(File file, int flag)
   {
      return (getFlags(file) & flag) != 0;
   }

   public int getFlags(File file)
   {
      if (file.equals(moduleDir))
      {
         return 0;
      }
      else
      {
         Integer oFlags = aggregatedFlags.get(file);
         if (oFlags == null)
         {
            final int flags = getFlags(file.getParentFile()) | getDirectFlags(file);
            oFlags = Integer.valueOf(flags);
            aggregatedFlags.put(file, oFlags);
         }
         return oFlags.intValue();
      }
   }

   private int getDirectFlags(File file)
   {
      final Integer oFlags = fileFlags.get(file);
      return oFlags == null ? 0 : oFlags.intValue();
   }

   public void accept(FileVisitor visitor)
   {
      accept(getModuleDir(), visitor, ~FLAG_DERIVED, 0, -1);
   }

   public void accept(File file, FileVisitor visitor)
   {
      final int flagMask = ~FLAG_DERIVED;
      if (file.equals(getModuleDir()) || isModuleFile(file, flagMask))
      {
         accept(file, visitor, flagMask, 0, -1);
      }
   }

   public void accept(FileVisitor visitor, boolean visitHidden, boolean visitDerived)
   {
      accept(getModuleDir(), visitor, toFlagMask(visitHidden, visitDerived), 0, -1);
   }

   void accept(File file, final FileVisitor visitor, final int flagMask, final int currentDepth, final int maxDepth)
   {
      if (maxDepth <= currentDepth || maxDepth == -1)
      {
         file.listFiles(new FileFilter()
         {
            @Override
            public boolean accept(File child)
            {
               final int flags = getFlags(child);
               if ((flags & flagMask) == 0 && visitor.visit(child, flags))
               {
                  ModuleFiles.this.accept(child, visitor, flagMask, currentDepth + 1, maxDepth);
               }
               return false;
            }
         });
      }
   }
}
