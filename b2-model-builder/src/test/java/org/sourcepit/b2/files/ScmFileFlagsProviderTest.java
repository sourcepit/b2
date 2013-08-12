/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static org.junit.Assert.*;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_HIDDEN;

import java.io.File;
import java.util.Map;

import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class ScmFileFlagsProviderTest extends AbstractTestEnvironmentTest
{
   @Test
   public void testGetFileFlags()
   {
      File moduleDir = ws.getRoot();

      PropertiesMap properties = new LinkedPropertiesMap();

      ScmFileFlagsProvider provider = new ScmFileFlagsProvider();

      Map<File, Integer> flags = provider.getAlreadyKnownFileFlags(moduleDir, properties);
      assertEquals(0, flags.size());

      File svnDir = new File(moduleDir, ".SvN");
      svnDir.mkdir();

      flags = provider.getAlreadyKnownFileFlags(moduleDir, properties);
      assertEquals(1, flags.size());
      assertEquals(FLAG_HIDDEN | FLAG_FORBIDDEN, flags.get(svnDir).intValue());
   }

   @Test
   public void testFileInvestigator()
   {
      File moduleDir = ws.getRoot();
      PropertiesMap properties = new LinkedPropertiesMap();

      ScmFileFlagsProvider provider = new ScmFileFlagsProvider();

      FileFlagsInvestigator flagsInvestigator = provider.createFileFlagsInvestigator(moduleDir, properties);

      File gitDir = new File(moduleDir, ".git");
      gitDir.mkdir();

      assertEquals(FLAG_HIDDEN | FLAG_FORBIDDEN, flagsInvestigator.determineFileFlags(gitDir));

      File fooDir = new File(moduleDir, "foo");
      fooDir.mkdir();

      assertEquals(0, flagsInvestigator.determineFileFlags(fooDir));
   }

}
