/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static org.sourcepit.b2.files.ModuleFiles.FLAG_FORBIDDEN;

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
public class ModuleFilesFactroy
{
   private final Collection<FileFlagsProvider> infoProviders;

   @Inject
   public ModuleFilesFactroy(List<FileFlagsProvider> infoProviders)
   {
      this.infoProviders = infoProviders;
   }

   public ModuleFiles create(File moduleDir, PropertiesSource properties)
   {
      final Map<File, Integer> fileToFlagsMap = determineFileFlags(infoProviders, moduleDir, properties);
      return new ModuleFiles(moduleDir, fileToFlagsMap);
   }

   static Map<File, Integer> determineFileFlags(Collection<FileFlagsProvider> flagsProviders, File moduleDir,
      PropertiesSource properties)
   {
      final Map<File, Integer> fileToFlagsMap = new HashMap<File, Integer>();
      getFileFlags(flagsProviders, moduleDir, properties, fileToFlagsMap);
      collectFileFlags(flagsProviders, fileToFlagsMap, moduleDir, properties);
      return fileToFlagsMap;
   }

   private static void getFileFlags(Collection<FileFlagsProvider> flagsProviders, File moduleDir,
      PropertiesSource properties, final Map<File, Integer> fileToFlagsMap)
   {
      for (FileFlagsProvider flagsProvider : flagsProviders)
      {
         final Map<File, Integer> providedFlags = flagsProvider.getFileFlags(moduleDir, properties);
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
   }

   private static void collectFileFlags(Collection<FileFlagsProvider> flagsProviders,
      final Map<File, Integer> fileToFlagsMap, File moduleDir, PropertiesSource properties)
   {
      final FileFlagsInvestigator investigator = getFileFlagsInvestigator(flagsProviders, moduleDir, properties);
      if (investigator != null)
      {
         collectFileFlags(moduleDir, fileToFlagsMap, investigator);
      }
   }

   private static FileFlagsInvestigator getFileFlagsInvestigator(Collection<FileFlagsProvider> flagsProviders,
      File moduleDir, PropertiesSource properties)
   {
      final List<FileFlagsInvestigator> investigators = new ArrayList<FileFlagsInvestigator>(flagsProviders.size());
      for (FileFlagsProvider flagsProvider : flagsProviders)
      {
         addInvestigator(investigators, flagsProvider, moduleDir, properties);
      }
      return investigators.isEmpty() ? null : new FileFlagsInvestigator()
      {
         @Override
         public int getFileFlags(File file)
         {
            int flags = 0;
            for (FileFlagsInvestigator investigator : investigators)
            {
               flags |= investigator.getFileFlags(file);
            }
            return flags;
         }
      };
   }

   private static void collectFileFlags(File moduleDir, final Map<File, Integer> fileToFlagsMap,
      final FileFlagsInvestigator investigator)
   {
      new ModuleFiles(moduleDir, fileToFlagsMap).accept(new FileVisitor()
      {
         @Override
         public boolean visit(File file, int flags)
         {
            final int fileFlags = investigator.getFileFlags(file);
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
   }

   private static void addInvestigator(final List<FileFlagsInvestigator> investigators,
      FileFlagsProvider fileFlagsProvider, File moduleDir, PropertiesSource properties)
   {
      if (fileFlagsProvider instanceof FileFlagsInvestigatorFactroy)
      {
         final FileFlagsInvestigatorFactroy investigatorFactroy = (FileFlagsInvestigatorFactroy) fileFlagsProvider;
         final FileFlagsInvestigator investigator = investigatorFactroy.createFileFlagsCollector(moduleDir, properties);
         if (investigator != null)
         {
            investigators.add(investigator);
         }
      }
   }

   private static int getFlags(final Map<File, Integer> fileToFlagsMap, File file)
   {
      final Integer oFlags = fileToFlagsMap.get(file);
      return oFlags == null ? 0 : oFlags.intValue();
   }
}
