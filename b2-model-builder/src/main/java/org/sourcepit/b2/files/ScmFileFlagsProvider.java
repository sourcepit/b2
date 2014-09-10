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
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_HIDDEN;
import static org.sourcepit.common.utils.props.PropertiesUtils.split;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class ScmFileFlagsProvider implements FileFlagsProvider
{
   @Override
   public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties)
   {
      final Set<String> scmDirs = getScmDirectories(properties);
      final Map<File, Integer> flags = new HashMap<File, Integer>(scmDirs.size());
      moduleDir.listFiles(new FileFilter()
      {
         @Override
         public boolean accept(File file)
         {
            final int f = determineFlags(file, scmDirs);
            if (f != 0)
            {
               flags.put(file, valueOf(f));
            }
            return false;
         }
      });
      return flags;
   }

   @Override
   public FileFlagsInvestigator createFileFlagsInvestigator(File moduleDir, PropertiesSource properties)
   {
      final Set<String> scmDirs = getScmDirectories(properties);
      return new FileFlagsInvestigator()
      {
         @Override
         public int determineFileFlags(File file)
         {
            return determineFlags(file, scmDirs);
         }

         @Override
         public Map<File, Integer> getAdditionallyFoundFileFlags()
         {
            return null;
         }
      };
   }

   private static int determineFlags(File file, final Set<String> scmDirs)
   {
      if (file.isDirectory() && scmDirs.contains(normalize(file.getName())))
      {
         return FLAG_HIDDEN | FLAG_FORBIDDEN;
      }
      return 0;
   }

   private static Set<String> getScmDirectories(PropertiesSource properties)
   {
      final Set<String> result = new HashSet<String>()
      {
         private static final long serialVersionUID = 1L;

         @Override
         public boolean add(String e)
         {
            return super.add(normalize(e));
         }
      };

      split(result, properties.get("b2.scmDirectories", ".git, .svn, .hg, CVS, .bzr"), ",", true, true);

      return result;
   }

   private static String normalize(String str)
   {
      return str.toLowerCase();
   }

}
