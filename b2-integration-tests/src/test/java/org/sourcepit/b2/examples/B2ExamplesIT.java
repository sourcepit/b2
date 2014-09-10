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

package org.sourcepit.b2.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;


public class B2ExamplesIT extends AbstractB2ExamplesIT
{
   @Override
   protected boolean isDebug(String testName)
   {
      return false;
   }

   @Test
   public void testSimple_Module() throws Exception
   {
      execute();

      final File projectDir = getExampleProjectDir();

      final File b2Dir = new File(projectDir, ".b2");
      assertTrue(b2Dir.exists());

      final File featuresDir = new File(b2Dir, "features");
      assertTrue(featuresDir.exists());

      final File sitesDir = new File(b2Dir, "sites");
      assertTrue(sitesDir.exists());
   }

   @Test
   public void testReactor_Build() throws Exception
   {
      execute("reactor");
      execute("single");
   }

   @Test
   public void testStructured_Module() throws Exception
   {
      execute();
   }

   @Test
   public void testComposite_Module() throws Exception
   {
      execute();
   }

   @Test
   public void testRcp_Example() throws Exception
   {
      execute();
   }

   @Test
   public void testModule_With_Custom_Ant_Targets() throws Exception
   {
      execute();

      final File projectDir = getExampleProjectDir();

      PropertiesMap testProperties = new LinkedPropertiesMap();
      testProperties.load(new File(projectDir, "target/test.properties"));
      assertEquals("generate-resources", testProperties.get("maven.phase"));

      testProperties = new LinkedPropertiesMap();
      testProperties.load(new File(projectDir, "org.sourcepit.b2.examples.simple.module/target/test.properties"));
      assertEquals("generate-resources", testProperties.get("maven.phase"));

      testProperties = new LinkedPropertiesMap();
      testProperties.load(new File(projectDir, "org.sourcepit.b2.examples.simple.module.tests/target/test.properties"));
      assertEquals("generate-test-resources", testProperties.get("maven.phase"));
   }
}
