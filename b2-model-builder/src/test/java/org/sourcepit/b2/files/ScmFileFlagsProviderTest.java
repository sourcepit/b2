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

import static org.junit.Assert.assertEquals;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_HIDDEN;

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
