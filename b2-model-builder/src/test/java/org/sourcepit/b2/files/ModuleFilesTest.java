/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_DERIVED;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_HIDDEN;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ModuleFilesTest
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

}
