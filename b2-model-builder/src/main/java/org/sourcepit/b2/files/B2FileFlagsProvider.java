/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static java.lang.Integer.valueOf;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_DERIVED;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_HIDDEN;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_MODULE_DIR;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class B2FileFlagsProvider implements FileFlagsProvider
{
   @Override
   public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties)
   {
      final Map<File, Integer> flags = new HashMap<File, Integer>();
      addB2Directory(moduleDir, flags);
      addNestedModules(flags, moduleDir, properties);
      return flags;
   }

   @Override
   public FileFlagsInvestigator createFileFlagsInvestigator(final File moduleDir, PropertiesSource properties)
   {
      final PathMatcher pathMatcher = newPathMatcher(moduleDir, properties);
      return pathMatcher == null ? null : new FileFlagsInvestigator()
      {
         @Override
         public int determineFileFlags(File file)
         {
            final boolean match = pathMatcher.isMatch(file.getPath());
            return match ? 0 : FLAG_FORBIDDEN;
         }
      };
   }

   private static void addB2Directory(File moduleDir, final Map<File, Integer> flags)
   {
      flags.put(new File(moduleDir, ".b2"), valueOf(FLAG_DERIVED | FLAG_HIDDEN));
   }

   private static void addNestedModules(final Map<File, Integer> flags, File moduleDir, PropertiesSource properties)
   {
      final File[] nestedMmoduleDirs = moduleDir.listFiles(new FileFilter()
      {
         @Override
         public boolean accept(File file)
         {
            return file.isDirectory() && new File(file, "module.xml").exists();
         }
      });

      if (nestedMmoduleDirs.length > 0)
      {
         final PathMatcher pathMatcher = newPathMatcher(moduleDir, properties);
         for (File nestedModuleDir : nestedMmoduleDirs)
         {
            if (pathMatcher == null || pathMatcher.isMatch(nestedModuleDir.getPath()))
            {
               flags.put(nestedModuleDir, valueOf(FLAG_FORBIDDEN | FLAG_HIDDEN | FLAG_MODULE_DIR));
            }
         }
      }
   }

   private static PathMatcher newPathMatcher(final File moduleDir, PropertiesSource properties)
   {
      final String filePatterns = properties.get("b2.modules");
      if (filePatterns == null)
      {
         return null;
      }
      return PathMatcher.parseFilePatterns(moduleDir, filePatterns);
   }

}
