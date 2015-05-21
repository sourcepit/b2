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

import static java.lang.Integer.valueOf;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_HIDDEN;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_MODULE_DIR;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class B2FileFlagsProvider implements FileFlagsProvider {
   @Override
   public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties) {
      final Map<File, Integer> flags = new HashMap<File, Integer>();
      addB2Directory(moduleDir, flags);
      addNestedModules(flags, moduleDir, properties);
      return flags;
   }

   @Override
   public FileFlagsInvestigator createFileFlagsInvestigator(final File moduleDir, PropertiesSource properties) {
      final PathMatcher pathMatcher = newPathMatcher(moduleDir, properties);
      return pathMatcher == null ? null : new FileFlagsInvestigator() {
         @Override
         public int determineFileFlags(File file) {
            final boolean match = pathMatcher.isMatch(file.getPath());
            return match ? 0 : FLAG_FORBIDDEN;
         }

         @Override
         public Map<File, Integer> getAdditionallyFoundFileFlags() {
            return null;
         }
      };
   }

   private static void addB2Directory(File moduleDir, final Map<File, Integer> flags) {
      flags.put(new File(moduleDir, ".b2"), valueOf(FLAG_DERIVED | FLAG_HIDDEN));
   }

   private static void addNestedModules(final Map<File, Integer> flags, File moduleDir, PropertiesSource properties) {
      final File[] nestedMmoduleDirs = moduleDir.listFiles(new FileFilter() {
         @Override
         public boolean accept(File file) {
            return file.isDirectory() && new File(file, "module.xml").exists();
         }
      });

      if (nestedMmoduleDirs.length > 0) {
         final PathMatcher pathMatcher = newPathMatcher(moduleDir, properties);
         for (File nestedModuleDir : nestedMmoduleDirs) {
            if (pathMatcher == null || pathMatcher.isMatch(nestedModuleDir.getPath())) {
               flags.put(nestedModuleDir, valueOf(FLAG_FORBIDDEN | FLAG_HIDDEN | FLAG_MODULE_DIR));
            }
         }
      }
   }

   private static PathMatcher newPathMatcher(final File moduleDir, PropertiesSource properties) {
      final String filePatterns = properties.get("b2.modules");
      if (filePatterns == null) {
         return null;
      }
      return PathMatcher.parseFilePatterns(moduleDir, filePatterns);
   }

}
