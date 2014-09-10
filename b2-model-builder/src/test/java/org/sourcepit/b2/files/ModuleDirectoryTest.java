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
import static org.junit.Assert.*;
import static org.sourcepit.b2.files.ModuleDirectory.DEPTH_INFINITE;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_HIDDEN;
import static org.sourcepit.common.utils.path.PathUtils.getRelativePath;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.AssertionFailedError;

import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;

public class ModuleDirectoryTest extends AbstractTestEnvironmentTest
{
   @Test
   public void testFlags()
   {
      final File moduleDir = new File("").getAbsoluteFile();
      final File targetDir = new File(moduleDir, "target");
      final File moduleXml = new File(moduleDir, "module.xml");
      final File manifestMf = new File(moduleDir, "META-INF/MANIFEST.MF");

      final Map<File, Integer> fileFlags = new HashMap<File, Integer>();
      fileFlags.put(targetDir, Integer.valueOf(FLAG_HIDDEN | FLAG_FORBIDDEN));
      fileFlags.put(manifestMf, Integer.valueOf(FLAG_DERIVED));

      final ModuleDirectory files = new ModuleDirectory(moduleDir, fileFlags);
      assertTrue(files.isHidden(targetDir));
      assertFalse(files.isDerived(targetDir));
      assertTrue(files.hasFlag(targetDir, FLAG_HIDDEN | FLAG_FORBIDDEN));
      assertTrue(files.hasFlag(targetDir, FLAG_HIDDEN));
      assertTrue(files.hasFlag(targetDir, FLAG_FORBIDDEN)); // still derived for targets directly within module-dirs

      final File fileInTarget = new File(targetDir, "foo/file.txt");
      assertTrue(files.isHidden(fileInTarget));
      assertFalse(files.isDerived(fileInTarget));
      assertTrue(files.hasFlag(fileInTarget, FLAG_HIDDEN | FLAG_FORBIDDEN));
      assertTrue(files.hasFlag(fileInTarget, FLAG_HIDDEN));
      assertTrue(files.hasFlag(fileInTarget, FLAG_FORBIDDEN));
      assertFalse(files.isModuleFile(fileInTarget));
      assertFalse(files.isModuleFile(fileInTarget, false, false));
      assertFalse(files.isModuleFile(fileInTarget, true, false));
      assertFalse(files.isModuleFile(fileInTarget, false, true));
      assertFalse(files.isModuleFile(fileInTarget, true, true));

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
   public void testAcceptWithDepth() throws Exception
   {
      final File moduleDir = ws.getRoot();

      ModuleDirectoryBuilder fb = new ModuleDirectoryBuilder(moduleDir);
      fb.mkdir(".b2", FLAG_DERIVED | FLAG_HIDDEN);
      fb.mkdir("plugins/foo/resources", FLAG_HIDDEN);
      fb.mkfile("plugins/foo/resources/META-INF/MANIFEST.MF", 0);
      fb.mkfile("plugins/foo/META-INF/MANIFEST.MF", FLAG_DERIVED);
      fb.mkfile("plugins/foo/plugin.xml", 0);
      fb.mkdir(".git", FLAG_FORBIDDEN);
      fb.mkfile("module.xml", 0);

      final ModuleDirectory mf = fb.toModuleDirectory();

      RelPathCollector pc = new RelPathCollector(mf.getFile());
      mf.accept(pc, 0);

      Map<String, Integer> pathToFlags = pc.getVisiedFiles();
      assertEquals(2, pathToFlags.size());
      assertContains("module.xml", 0, pathToFlags);
      assertContains("plugins", 0, pathToFlags);

      pc = new RelPathCollector(mf.getFile());
      mf.accept(pc, 1);

      pathToFlags = pc.getVisiedFiles();
      assertEquals(3, pathToFlags.size());
      assertContains("module.xml", 0, pathToFlags);
      assertContains("plugins", 0, pathToFlags);
      assertContains("plugins/foo", 0, pathToFlags);

      pc = new RelPathCollector(mf.getFile());
      mf.accept(pc, 2);

      pathToFlags = pc.getVisiedFiles();
      assertEquals(5, pathToFlags.size());
      assertContains("module.xml", 0, pathToFlags);
      assertContains("plugins", 0, pathToFlags);
      assertContains("plugins/foo", 0, pathToFlags);
      assertContains("plugins/foo/META-INF", 0, pathToFlags);
      assertContains("plugins/foo/plugin.xml", 0, pathToFlags);

      pc = new RelPathCollector(mf.getFile());
      mf.accept(pc, ModuleDirectory.DEPTH_INFINITE);

      pathToFlags = pc.getVisiedFiles();
      assertContains("module.xml", 0, pathToFlags);
      assertContains("plugins", 0, pathToFlags);
      assertContains("plugins/foo", 0, pathToFlags);
      assertContains("plugins/foo/META-INF", 0, pathToFlags);
      assertContains("plugins/foo/META-INF/MANIFEST.MF", 1, pathToFlags);
      assertContains("plugins/foo/plugin.xml", 0, pathToFlags);
   }

   @Test
   public void testAccept() throws Exception
   {
      final File moduleDir = ws.getRoot();

      ModuleDirectoryBuilder fb = new ModuleDirectoryBuilder(moduleDir);
      fb.mkdir(".b2", FLAG_DERIVED | FLAG_HIDDEN);
      fb.mkdir("plugins/foo/resources", FLAG_HIDDEN);
      fb.mkfile("plugins/foo/resources/META-INF/MANIFEST.MF", 0);
      fb.mkfile("plugins/foo/META-INF/MANIFEST.MF", FLAG_DERIVED);
      fb.mkfile("plugins/foo/plugin.xml", 0);
      fb.mkdir(".git", FLAG_FORBIDDEN);
      fb.mkfile("module.xml", 0);

      final ModuleDirectory mf = fb.toModuleDirectory();

      RelPathCollector pc = new RelPathCollector(mf.getFile());
      mf.accept(pc);

      Map<String, Integer> pathToFlags = pc.getVisiedFiles();
      assertEquals(6, pathToFlags.size());
      assertContains("module.xml", 0, pathToFlags);
      assertContains("plugins", 0, pathToFlags);
      assertContains("plugins/foo", 0, pathToFlags);
      assertContains("plugins/foo/META-INF", 0, pathToFlags);
      assertContains("plugins/foo/META-INF/MANIFEST.MF", 1, pathToFlags);
      assertContains("plugins/foo/plugin.xml", 0, pathToFlags);

      pc = new RelPathCollector(mf.getFile());
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

      pc = new RelPathCollector(mf.getFile());
      mf.accept(pc, DEPTH_INFINITE, FLAG_DERIVED | FLAG_HIDDEN);
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

      pc = new RelPathCollector(mf.getFile());
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

      pc = new RelPathCollector(mf.getFile());
      mf.accept(pc, DEPTH_INFINITE, FLAG_HIDDEN);
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

   @Test
   public void testAddFlags() throws Exception
   {
      final File moduleDir = ws.getRoot();

      ModuleDirectory moduleDirectory = new ModuleDirectory(moduleDir, Collections.<File, Integer> emptyMap());

      File f = new File(moduleDir, "foo");
      f.createNewFile();

      assertEquals(0, moduleDirectory.getFlags(f));

      moduleDirectory.addFlags(f, FLAG_DERIVED);
      assertEquals(FLAG_DERIVED, moduleDirectory.getFlags(f));

      moduleDirectory.addFlags(f, FLAG_FORBIDDEN);
      assertEquals(FLAG_DERIVED | FLAG_FORBIDDEN, moduleDirectory.getFlags(f));
   }

   @Test
   public void testRemoveFlags() throws Exception
   {
      final File moduleDir = ws.getRoot();

      ModuleDirectory moduleDirectory = new ModuleDirectory(moduleDir, Collections.<File, Integer> emptyMap());

      File f = new File(moduleDir, "foo");
      f.createNewFile();

      assertEquals(0, moduleDirectory.getFlags(f));

      moduleDirectory.removeFlags(f, FLAG_DERIVED);
      assertEquals(0, moduleDirectory.getFlags(f));

      moduleDirectory.addFlags(f, FLAG_DERIVED | FLAG_FORBIDDEN);
      assertEquals(FLAG_DERIVED | FLAG_FORBIDDEN, moduleDirectory.getFlags(f));

      moduleDirectory.removeFlags(f, FLAG_DERIVED);
      assertEquals(FLAG_FORBIDDEN, moduleDirectory.getFlags(f));

      moduleDirectory.removeFlags(f, FLAG_FORBIDDEN);
      assertEquals(0, moduleDirectory.getFlags(f));
   }

   private static void assertContains(String expectedPath, int expectedFlags, Map<String, Integer> actual)
   {
      final Integer flags = actual.get(expectedPath);
      assertNotNull(flags);
      assertEquals(expectedFlags, flags.intValue());
   }

   static class RelPathCollector implements FileVisitor<RuntimeException>
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

   static class ModuleDirectoryBuilder
   {
      private final File baseDir;

      private Map<File, Integer> fileFlags = new HashMap<File, Integer>();

      ModuleDirectoryBuilder(File baseDir)
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

      ModuleDirectory toModuleDirectory()
      {
         return new ModuleDirectory(baseDir, new HashMap<File, Integer>(fileFlags));
      }
   }


}
