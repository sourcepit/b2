/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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

public class ModuleDirectoryFactroyTest extends AbstractTestEnvironmentTest
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
            return singletonMap(new File(moduleDir, "target"), Integer.valueOf(FLAG_DERIVED));
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

      final Map<File, Integer> fileToFlagsMap = ModuleDirectoryFactroy.determineFileFlags(providers, moduleDir,
         new LinkedPropertiesMap());
      assertEquals(3, fileToFlagsMap.size());

      int flags = fileToFlagsMap.get(new File(moduleDir, "target")).intValue(); //FIXME
      assertTrue((flags & FLAG_HIDDEN) != 0);
      assertTrue((flags & FLAG_DERIVED) != 0);

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

      final Map<File, Integer> fileToFlagsMap = ModuleDirectoryFactroy.determineFileFlags(providers, moduleDir,
         new LinkedPropertiesMap());
      assertEquals(3, fileToFlagsMap.size());

      assertEquals(FLAG_HIDDEN | FLAG_DERIVED, fileToFlagsMap.get(new File(moduleDir, ".b2")).intValue());
      assertEquals(FLAG_HIDDEN | FLAG_FORBIDDEN, fileToFlagsMap.get(new File(moduleDir, ".b2/.svn")).intValue());
      assertEquals(FLAG_HIDDEN | FLAG_FORBIDDEN, fileToFlagsMap.get(new File(moduleDir, ".svn")).intValue());
   }
}
