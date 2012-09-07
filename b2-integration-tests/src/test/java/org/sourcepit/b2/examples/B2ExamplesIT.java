/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.examples;

import static org.junit.Assert.assertEquals;

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
