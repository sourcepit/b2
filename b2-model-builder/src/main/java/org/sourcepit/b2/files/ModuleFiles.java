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
   public static final int DEPTH_INFINITE = -1;

   public static final int MASK_NONE = 0xffffffff;

   public static final int FLAG_FORBIDDEN = 0x00000004;

   public static final int FLAG_HIDDEN = 0x00000002;

   public static final int FLAG_DERIVED = 0x00000001;

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
      return isModuleFile(file, FLAG_DERIVED);
   }

   public boolean isModuleFile(File file, int flags)
   {
      return isModuleFile0(file, ~flags);
   }

   public boolean isModuleFile(File file, boolean includeHidden, boolean includeDerived)
   {
      return isModuleFile0(file, toFlagMask(includeHidden, includeDerived));
   }

   private boolean isModuleFile0(File file, int flagMask)
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
      accept(visitor, DEPTH_INFINITE);
   }

   public void accept(FileVisitor visitor, int depth)
   {
      accept(visitor, depth, FLAG_DERIVED);
   }

   public void accept(FileVisitor visitor, int depth, int flags)
   {
      acceptRecursive(getModuleDir(), visitor, 0, depth, ~flags);
   }

   public void accept(File file, FileVisitor visitor)
   {
      accept(file, visitor, DEPTH_INFINITE);
   }

   public void accept(File file, FileVisitor visitor, int depth)
   {
      accept(file, visitor, depth, FLAG_DERIVED);
   }

   public void accept(File file, FileVisitor visitor, int depth, int flags)
   {
      final int flagMask = ~flags;
      if (file.equals(getModuleDir()) || isModuleFile(file, flagMask))
      {
         acceptRecursive(file, visitor, 0, depth, flagMask);
      }
   }

   public void accept(FileVisitor visitor, boolean visitHidden, boolean visitDerived)
   {
      acceptRecursive(getModuleDir(), visitor, 0, DEPTH_INFINITE, toFlagMask(visitHidden, visitDerived));
   }

   private void acceptRecursive(File file, final FileVisitor visitor, final int currentDepth, final int maxDepth,
      final int flagMask)
   {
      if (currentDepth <= maxDepth || maxDepth == DEPTH_INFINITE)
      {
         file.listFiles(new FileFilter()
         {
            @Override
            public boolean accept(File child)
            {
               final int flags = getFlags(child);
               if ((flags & flagMask) == 0 && visitor.visit(child, flags))
               {
                  ModuleFiles.this.acceptRecursive(child, visitor, currentDepth + 1, maxDepth, flagMask);
               }
               return false;
            }
         });
      }
   }

   private static int toFlagMask(boolean includeHidden, boolean includeDerived)
   {
      int flagMask = MASK_NONE;
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
}
