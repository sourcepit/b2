/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_DERIVED;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_HIDDEN;
import static org.sourcepit.common.utils.path.PathUtils.getRelativePath;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.AssertionFailedError;

import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;

public class ModuleFilesTest extends AbstractTestEnvironmentTest
{
   @Test
   public void testFlags()
   {
      final File moduleDir = new File("").getAbsoluteFile();
      final File targetDir = new File(moduleDir, "target");
      final File moduleXml = new File(moduleDir, "module.xml");
      final File manifestMf = new File(moduleDir, "META-INF/MANIFEST.MF");

      final Map<File, Integer> fileFlags = new HashMap<File, Integer>();
      fileFlags.put(targetDir, Integer.valueOf(FLAG_HIDDEN | FLAG_DERIVED));
      fileFlags.put(manifestMf, Integer.valueOf(FLAG_DERIVED));

      final ModuleFiles files = new ModuleFiles(moduleDir, fileFlags);
      assertTrue(files.isHidden(targetDir));
      assertTrue(files.isDerived(targetDir));
      assertTrue(files.hasFlag(targetDir, FLAG_HIDDEN | FLAG_DERIVED));
      assertTrue(files.hasFlag(targetDir, FLAG_HIDDEN));
      assertTrue(files.hasFlag(targetDir, FLAG_DERIVED));

      final File fileInTarget = new File(targetDir, "foo/file.txt");
      assertTrue(files.isHidden(fileInTarget));
      assertTrue(files.isDerived(fileInTarget));
      assertTrue(files.hasFlag(fileInTarget, FLAG_HIDDEN | FLAG_DERIVED));
      assertTrue(files.hasFlag(fileInTarget, FLAG_HIDDEN));
      assertTrue(files.hasFlag(fileInTarget, FLAG_DERIVED));
      assertFalse(files.isModuleFile(fileInTarget));
      assertFalse(files.isModuleFile(fileInTarget, false, false));
      assertFalse(files.isModuleFile(fileInTarget, true, false));
      assertFalse(files.isModuleFile(fileInTarget, false, true));
      assertTrue(files.isModuleFile(fileInTarget, true, true));

      assertFalse(files.isHidden(moduleXml));
      assertFalse(files.isDerived(moduleXml));
      assertFalse(files.hasFlag(moduleXml, FLAG_HIDDEN | FLAG_DERIVED));
      assertFalse(files.hasFlag(moduleXml, FLAG_HIDDEN));
      assertFalse(files.hasFlag(moduleXml, FLAG_DERIVED));
      assertTrue(files.isModuleFile(moduleXml));
      assertTrue(files.isModuleFile(moduleXml, false, false));
      assertTrue(files.isModuleFile(moduleXml, true, false));
      assertTrue(files.isModuleFile(moduleXml, false, true));
      assertTrue(files.isModuleFile(moduleXml, true, true));

      assertFalse(files.isHidden(manifestMf));
      assertTrue(files.isDerived(manifestMf));
      assertTrue(files.hasFlag(manifestMf, FLAG_HIDDEN | FLAG_DERIVED));
      assertFalse(files.hasFlag(manifestMf, FLAG_HIDDEN));
      assertTrue(files.hasFlag(manifestMf, FLAG_DERIVED));
      assertTrue(files.isModuleFile(manifestMf));
      assertFalse(files.isModuleFile(manifestMf, false, false));
      assertFalse(files.isModuleFile(manifestMf, true, false));
      assertTrue(files.isModuleFile(manifestMf, false, true));
      assertTrue(files.isModuleFile(manifestMf, true, true));
   }

   @Test
   public void testAccept() throws Exception
   {
      final File moduleDir = ws.getRoot();

      ModuleFilesBuilder fb = new ModuleFilesBuilder(moduleDir);
      fb.mkdir(".b2", FLAG_DERIVED | FLAG_HIDDEN);
      fb.mkdir("plugins/foo/resources", FLAG_HIDDEN);
      fb.mkfile("plugins/foo/resources/META-INF/MANIFEST.MF", 0);
      fb.mkfile("plugins/foo/META-INF/MANIFEST.MF", FLAG_DERIVED);
      fb.mkfile("plugins/foo/plugin.xml", 0);
      fb.mkdir(".git", FLAG_FORBIDDEN);
      fb.mkfile("module.xml", 0);

      final ModuleFiles mf = fb.toModuleFiles();

      RelPathCollector pc = new RelPathCollector(mf.getModuleDir());
      mf.accept(pc);

      Map<String, Integer> pathToFlags = pc.getVisiedFiles();
      assertEquals(6, pathToFlags.size());
      assertContains("module.xml", 0, pathToFlags);
      assertContains("plugins", 0, pathToFlags);
      assertContains("plugins/foo", 0, pathToFlags);
      assertContains("plugins/foo/META-INF", 0, pathToFlags);
      assertContains("plugins/foo/META-INF/MANIFEST.MF", 1, pathToFlags);
      assertContains("plugins/foo/plugin.xml", 0, pathToFlags);

      pc = new RelPathCollector(mf.getModuleDir());
      mf.accept(pc, true, true);
      pathToFlags = pc.getVisiedFiles();
      assertEquals(10, pathToFlags.size());
      assertContains(".b2", FLAG_DERIVED | FLAG_HIDDEN, pathToFlags);
      assertContains("module.xml", 0, pathToFlags);
      assertContains("plugins", 0, pathToFlags);
      assertContains("plugins/foo", 0, pathToFlags);
      assertContains("plugins/foo/META-INF", 0, pathToFlags);
      assertContains("plugins/foo/META-INF/MANIFEST.MF", 1, pathToFlags);
      assertContains("plugins/foo/plugin.xml", 0, pathToFlags);
      assertContains("plugins/foo/resources", FLAG_HIDDEN, pathToFlags);
      assertContains("plugins/foo/resources/META-INF", FLAG_HIDDEN, pathToFlags);
      assertContains("plugins/foo/resources/META-INF/MANIFEST.MF", FLAG_HIDDEN, pathToFlags);

      pc = new RelPathCollector(mf.getModuleDir());
      mf.accept(pc, true, false);
      pathToFlags = pc.getVisiedFiles();
      assertEquals(8, pathToFlags.size());
      assertContains("module.xml", 0, pathToFlags);
      assertContains("plugins", 0, pathToFlags);
      assertContains("plugins/foo", 0, pathToFlags);
      assertContains("plugins/foo/META-INF", 0, pathToFlags);
      assertContains("plugins/foo/plugin.xml", 0, pathToFlags);
      assertContains("plugins/foo/resources", FLAG_HIDDEN, pathToFlags);
      assertContains("plugins/foo/resources/META-INF", FLAG_HIDDEN, pathToFlags);
      assertContains("plugins/foo/resources/META-INF/MANIFEST.MF", FLAG_HIDDEN, pathToFlags);
   }

   private static void assertContains(String expectedPath, int expectedFlags, Map<String, Integer> actual)
   {
      final Integer flags = actual.get(expectedPath);
      assertNotNull(flags);
      assertEquals(expectedFlags, flags.intValue());
   }

   static class RelPathCollector implements FileVisitor
   {
      private final Map<String, Integer> visiedFiles = new HashMap<String, Integer>();

      private File baseDir;

      public RelPathCollector(File baseDir)
      {
         this.baseDir = baseDir;
      }

      @Override
      public boolean visit(File file, int flags)
      {
         final String path = getRelativePath(file, baseDir, "/");
         visiedFiles.put(path, valueOf(flags));
         return true;
      }

      Map<String, Integer> getVisiedFiles()
      {
         return visiedFiles;
      }
   }

   static class ModuleFilesBuilder
   {
      private final File baseDir;

      private Map<File, Integer> fileFlags = new HashMap<File, Integer>();

      ModuleFilesBuilder(File baseDir)
      {
         this.baseDir = baseDir;
      }

      File mkdir(String name, int flags)
      {
         final File dir = new File(baseDir, name);
         assertTrue(dir.mkdirs());
         if (flags > 0)
         {
            fileFlags.put(dir, valueOf(flags));
         }
         return dir;
      }

      File mkfile(String name, int flags)
      {
         final File file = new File(baseDir, name);
         file.getParentFile().mkdirs();
         assertTrue(file.getParentFile().exists());
         try
         {
            assertTrue(file.createNewFile());
         }
         catch (IOException e)
         {
            throw new AssertionFailedError("Expected to create file " + file.getPath() + " but exception occurred "
               + e.getLocalizedMessage());
         }
         if (flags > 0)
         {
            fileFlags.put(file, valueOf(flags));
         }
         return file;
      }

      ModuleFiles toModuleFiles()
      {
         return new ModuleFiles(baseDir, new HashMap<File, Integer>(fileFlags));
      }
   }


}
