/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import java.io.File;
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

   static Map<File, Integer> determineFileFlags(Collection<FileFlagsProvider> infoProviders, File moduleDir,
      PropertiesSource properties)
   {
      final Map<File, Integer> fileToFlagsMap = new HashMap<File, Integer>();
      for (FileFlagsProvider infoProvider : infoProviders)
      {
         Map<File, Integer> providedFlags = infoProvider.getFileFlags(moduleDir, properties);
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
      return fileToFlagsMap;
   }

   private static int getFlags(final Map<File, Integer> fileToFlagsMap, File file)
   {
      final Integer oFlags = fileToFlagsMap.get(file);
      return oFlags == null ? 0 : oFlags.intValue();
   }
}
