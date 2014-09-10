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

import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_HIDDEN;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;

public class ModuleDirectoryFactoryTest extends AbstractTestEnvironmentTest
{
   @Test
   public void testDetermineFileFlags()
   {
      Collection<FileFlagsProvider> providers = new HashSet<FileFlagsProvider>();

      providers.add(new AbstractFileFlagsProvider()
      {
         @Override
         public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties)
         {
            return singletonMap(new File(moduleDir, "target"), Integer.valueOf(FLAG_HIDDEN));
         }
      });

      providers.add(new AbstractFileFlagsProvider()
      {
         @Override
         public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties)
         {
            return singletonMap(new File(moduleDir, "target"), Integer.valueOf(FLAG_FORBIDDEN));
         }
      });

      providers.add(new AbstractFileFlagsProvider()
      {
         @Override
         public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties)
         {
            return singletonMap(new File(moduleDir, "foo"), Integer.valueOf(FLAG_HIDDEN));
         }
      });

      providers.add(new AbstractFileFlagsProvider()
      {
         @Override
         public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties)
         {
            return singletonMap(new File(moduleDir, "bar"), Integer.valueOf(FLAG_DERIVED));
         }
      });

      providers.add(new AbstractFileFlagsProvider()
      {
         @Override
         public Map<File, Integer> getAlreadyKnownFileFlags(File moduleDir, PropertiesSource properties)
         {
            return singletonMap(new File(moduleDir, "murks"), null);
         }
      });

      final File moduleDir = new File("");

      final Map<File, Integer> fileToFlagsMap = ModuleDirectoryFactory.determineFileFlags(providers, moduleDir,
         new LinkedPropertiesMap());
      assertEquals(3, fileToFlagsMap.size());

      int flags = fileToFlagsMap.get(new File(moduleDir, "target")).intValue();
      assertTrue((flags & FLAG_HIDDEN) != 0);
      assertTrue((flags & FLAG_FORBIDDEN) != 0);

      flags = fileToFlagsMap.get(new File(moduleDir, "foo")).intValue();
      assertTrue((flags & FLAG_HIDDEN) != 0);
      assertFalse((flags & FLAG_DERIVED) != 0);

      flags = fileToFlagsMap.get(new File(moduleDir, "bar")).intValue();
      assertFalse((flags & FLAG_HIDDEN) != 0);
      assertTrue((flags & FLAG_DERIVED) != 0);
   }

   @Test
   public void testDetermineFileFlagsWithInvestigator()
   {
      Collection<FileFlagsProvider> providers = new HashSet<FileFlagsProvider>();
      providers.add(new B2FileFlagsProvider());
      providers.add(new ScmFileFlagsProvider());

      File moduleDir = ws.getRoot();
      new File(moduleDir, ".b2/.svn/entries").mkdirs();
      new File(moduleDir, ".b2/foo").mkdirs();
      new File(moduleDir, ".svn/entries").mkdirs();
      new File(moduleDir, "foo").mkdirs();

      final Map<File, Integer> fileToFlagsMap = ModuleDirectoryFactory.determineFileFlags(providers, moduleDir,
         new LinkedPropertiesMap());
      assertEquals(3, fileToFlagsMap.size());

      assertEquals(FLAG_HIDDEN | FLAG_DERIVED, fileToFlagsMap.get(new File(moduleDir, ".b2")).intValue());
      assertEquals(FLAG_HIDDEN | FLAG_FORBIDDEN, fileToFlagsMap.get(new File(moduleDir, ".b2/.svn")).intValue());
      assertEquals(FLAG_HIDDEN | FLAG_FORBIDDEN, fileToFlagsMap.get(new File(moduleDir, ".svn")).intValue());
   }
}
