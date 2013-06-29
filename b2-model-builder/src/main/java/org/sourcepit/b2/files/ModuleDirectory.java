/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.valueOf;
import static java.util.Collections.sort;
import static org.sourcepit.common.utils.file.FileUtils.isParentOf;
import static org.sourcepit.common.utils.path.PathUtils.getRelativePath;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotNull;

import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class ModuleDirectory
{
   public static final int DEPTH_INFINITE = -1;

   public static final int MASK_NONE = 0xffffffff;

   public static final int FLAG_MODULE_DIR = 0x00000008;

   public static final int FLAG_FORBIDDEN = 0x00000004;

   public static final int FLAG_HIDDEN = 0x00000002;

   public static final int FLAG_DERIVED = 0x00000001;

   private final File moduleDir;

   private final Map<File, Integer> fileFlags;

   private final Map<File, Integer> aggregatedFlags = new ConcurrentHashMap<File, Integer>();

   public static ModuleDirectory load(final File moduleDir, final File srcFile)
   {
      final PropertiesMap props = new LinkedPropertiesMap();
      props.load(srcFile);
      final Map<File, Integer> fileFlags = new HashMap<File, Integer>();
      for (Entry<String, String> entry : props.entrySet())
      {
         final File file = new File(moduleDir, entry.getKey());
         final Integer flags = Integer.valueOf(entry.getValue());
         fileFlags.put(file, flags);
      }
      return new ModuleDirectory(moduleDir, fileFlags);
   }
   
   public static void save(ModuleDirectory moduleDirectory, File destFile)
   {
      final File moduleDir = moduleDirectory.getFile();
      final Map<File, Integer> fileToFlagsMap = moduleDirectory.getFileFlags();

      final List<File> files = new ArrayList<File>(fileToFlagsMap.keySet());
      sort(files, new Comparator<File>()
      {
         @Override
         public int compare(File f1, File f2)
         {
            return f1.getPath().compareToIgnoreCase(f2.getPath());
         }
      });

      final PropertiesMap out = new LinkedPropertiesMap();
      for (File file : files)
      {
         final int flags = fileToFlagsMap.get(file).intValue();
         out.put(getRelativePath(file, moduleDir, "/"), valueOf(flags));
      }

      out.store(destFile);
   }

   public ModuleDirectory(@NotNull File moduleDir, Map<File, Integer> fileFlags)
   {
      checkArgument(moduleDir != null);
      this.moduleDir = moduleDir;
      if (fileFlags == null)
      {
         this.fileFlags = new HashMap<File, Integer>();
      }
      else
      {
         this.fileFlags = new HashMap<File, Integer>(fileFlags);
      }
   }

   Map<File, Integer> getFileFlags()
   {
      return fileFlags;
   }

   public File getFile()
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

   public void addFlags(File file, int flags)
   {
      fileFlags.put(file, Integer.valueOf(getDirectFlags(file) | flags));
      aggregatedFlags.remove(file);
   }

   public void removeFlags(File file, int flags)
   {
      fileFlags.put(file, Integer.valueOf(getDirectFlags(file) & ~flags));
      aggregatedFlags.remove(file);
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
      acceptRecursive(getFile(), visitor, 0, depth, ~flags);
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
      if (file.equals(getFile()) || isModuleFile(file, flagMask))
      {
         acceptRecursive(file, visitor, 0, depth, flagMask);
      }
   }

   public void accept(FileVisitor visitor, boolean visitHidden, boolean visitDerived)
   {
      acceptRecursive(getFile(), visitor, 0, DEPTH_INFINITE, toFlagMask(visitHidden, visitDerived));
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
                  ModuleDirectory.this.acceptRecursive(child, visitor, currentDepth + 1, maxDepth, flagMask);
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

   public List<File> getFiles(final FileVisitor filter, int flags)
   {
      final List<File> files = new ArrayList<File>();
      accept(new FileVisitor()
      {
         @Override
         public boolean visit(File file, int flags)
         {
            if (filter.visit(file, flags))
            {
               files.add(file);
            }
            return false;
         }
      }, 0, flags);
      return files;
   }
}