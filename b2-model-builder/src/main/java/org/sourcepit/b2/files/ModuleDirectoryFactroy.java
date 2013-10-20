/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static org.sourcepit.b2.files.ModuleDirectory.FLAG_FORBIDDEN;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class ModuleDirectoryFactroy
{
   private final Collection<FileFlagsProvider> infoProviders;

   @Inject
   public ModuleDirectoryFactroy(List<FileFlagsProvider> infoProviders)
   {
      this.infoProviders = infoProviders;
   }

   public ModuleDirectory create(File moduleDir, PropertiesSource properties)
   {
      final Map<File, Integer> fileToFlagsMap = determineFileFlags(infoProviders, moduleDir, properties);
      return new ModuleDirectory(moduleDir, fileToFlagsMap);
   }

   static Map<File, Integer> determineFileFlags(Collection<FileFlagsProvider> flagsProviders, File moduleDir,
      PropertiesSource properties)
   {
      final Map<File, Integer> fileToFlagsMap = new HashMap<File, Integer>();
      collectAlreadyKnownFileFlags(flagsProviders, moduleDir, properties, fileToFlagsMap);
      collectDeterminedFileFlags(flagsProviders, fileToFlagsMap, moduleDir, properties);
      return fileToFlagsMap;
   }

   private static void collectAlreadyKnownFileFlags(Collection<FileFlagsProvider> flagsProviders, File moduleDir,
      PropertiesSource properties, final Map<File, Integer> fileToFlagsMap)
   {
      for (FileFlagsProvider flagsProvider : flagsProviders)
      {
         final Map<File, Integer> providedFlags = flagsProvider.getAlreadyKnownFileFlags(moduleDir, properties);
         applyFileFlags(fileToFlagsMap, providedFlags);
      }
   }

   private static void applyFileFlags(final Map<File, Integer> fileToFlagsMap, final Map<File, Integer> providedFlags)
   {
      if (providedFlags != null)
      {
         for (Entry<File, Integer> entry : providedFlags.entrySet())
         {
            final File file = entry.getKey();
            final int flags = getFlags(fileToFlagsMap, file) | getFlags(providedFlags, file);
            if (flags != 0)
            {
               fileToFlagsMap.put(file, Integer.valueOf(flags));
            }
         }
      }
   }

   private static void collectDeterminedFileFlags(Collection<FileFlagsProvider> flagsProviders,
      final Map<File, Integer> fileToFlagsMap, File moduleDir, PropertiesSource properties)
   {
      final FileFlagsInvestigator investigator = getFileFlagsInvestigator(flagsProviders, moduleDir, properties);
      if (investigator != null)
      {
         collectDeterminedFileFlags(moduleDir, fileToFlagsMap, investigator);
      }
   }

   private static FileFlagsInvestigator getFileFlagsInvestigator(Collection<FileFlagsProvider> flagsProviders,
      File moduleDir, PropertiesSource properties)
   {
      final List<FileFlagsInvestigator> investigators = new ArrayList<FileFlagsInvestigator>(flagsProviders.size());
      for (FileFlagsProvider flagsProvider : flagsProviders)
      {
         final FileFlagsInvestigator investigator = flagsProvider.createFileFlagsInvestigator(moduleDir, properties);
         if (investigator != null)
         {
            investigators.add(investigator);
         }
      }
      return investigators.isEmpty() ? null : new FileFlagsInvestigator()
      {
         @Override
         public int determineFileFlags(File file)
         {
            int flags = 0;
            for (FileFlagsInvestigator investigator : investigators)
            {
               flags |= investigator.determineFileFlags(file);
            }
            return flags;
         }

         @Override
         public Map<File, Integer> getAdditionallyFoundFileFlags()
         {
            final Map<File, Integer> fileToFlagsMap = new HashMap<File, Integer>();
            for (FileFlagsInvestigator investigator : investigators)
            {
               Map<File, Integer> fileFlags = investigator.getAdditionallyFoundFileFlags();
               if (fileFlags != null)
               {
                  applyFileFlags(fileToFlagsMap, fileFlags);
               }
            }
            return fileToFlagsMap;
         }
      };
   }

   private static void collectDeterminedFileFlags(File moduleDir, final Map<File, Integer> fileToFlagsMap,
      final FileFlagsInvestigator investigator)
   {
      new ModuleDirectory(moduleDir, fileToFlagsMap).accept(new FileVisitor<RuntimeException>()
      {
         @Override
         public boolean visit(File file, int flags)
         {
            final int fileFlags = investigator.determineFileFlags(file);
            if (fileFlags != 0)
            {
               fileToFlagsMap.put(file, getFlags(fileToFlagsMap, file) | fileFlags);
               if ((fileFlags & FLAG_FORBIDDEN) != 0)
               {
                  return false;
               }
            }
            return true;
         }
      }, true, true);

      final Map<File, Integer> additionallyFoundFileFlags = investigator.getAdditionallyFoundFileFlags();
      if (additionallyFoundFileFlags != null)
      {
         applyFileFlags(fileToFlagsMap, additionallyFoundFileFlags);
      }
   }

   private static int getFlags(final Map<File, Integer> fileToFlagsMap, File file)
   {
      final Integer oFlags = fileToFlagsMap.get(file);
      return oFlags == null ? 0 : oFlags.intValue();
   }
}
