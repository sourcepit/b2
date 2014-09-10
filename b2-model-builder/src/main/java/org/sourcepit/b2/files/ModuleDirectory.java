/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.files;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.valueOf;
import static java.util.Collections.sort;
import static org.sourcepit.common.utils.file.FileUtils.isParentOf;
import static org.sourcepit.common.utils.path.PathUtils.getRelativePath;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.sourcepit.common.constraints.NotNull;

import org.sourcepit.common.utils.file.FileUtils;
import org.sourcepit.common.utils.file.FileUtils.AbstractFileFilter;
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

   public <E extends Exception> void accept(FileVisitor<E> visitor) throws E
   {
      accept(visitor, DEPTH_INFINITE);
   }

   public <E extends Exception> void accept(FileVisitor<E> visitor, int depth) throws E
   {
      accept(visitor, depth, FLAG_DERIVED);
   }

   public <E extends Exception> void accept(FileVisitor<E> visitor, int depth, int flags) throws E
   {
      acceptRecursive(getFile(), visitor, 0, depth, ~flags);
   }

   public <E extends Exception> void accept(File file, FileVisitor<E> visitor) throws E
   {
      accept(file, visitor, DEPTH_INFINITE);
   }

   public <E extends Exception> void accept(File file, FileVisitor<E> visitor, int depth) throws E
   {
      accept(file, visitor, depth, FLAG_DERIVED);
   }

   public <E extends Exception> void accept(File file, FileVisitor<E> visitor, int depth, int flags) throws E
   {
      final int flagMask = ~flags;
      if (file.equals(getFile()) || isModuleFile(file, flagMask))
      {
         acceptRecursive(file, visitor, 0, depth, flagMask);
      }
   }

   public <E extends Exception> void accept(FileVisitor<E> visitor, boolean visitHidden, boolean visitDerived) throws E
   {
      acceptRecursive(getFile(), visitor, 0, DEPTH_INFINITE, toFlagMask(visitHidden, visitDerived));
   }

   private <E extends Exception> void acceptRecursive(File file, final FileVisitor<E> visitor, final int currentDepth,
      final int maxDepth, final int flagMask) throws E
   {
      if (currentDepth <= maxDepth || maxDepth == DEPTH_INFINITE)
      {
         FileUtils.listFiles(file, new AbstractFileFilter<E>()
         {
            @Override
            protected boolean acceptFile(File child) throws E
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

   public <E extends Exception> List<File> getFiles(final FileVisitor<E> filter, int flags) throws E
   {
      final List<File> files = new ArrayList<File>();
      accept(new FileVisitor<E>()
      {
         @Override
         public boolean visit(File file, int flags) throws E
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
