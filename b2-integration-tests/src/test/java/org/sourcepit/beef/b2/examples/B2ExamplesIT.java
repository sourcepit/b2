/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.examples;

import java.io.File;

import org.sourcepit.beef.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.beef.b2.common.internal.utils.PropertiesMap;


public class B2ExamplesIT extends AbstractB2ExamplesIT
{
   @Override
   protected boolean isDebug(String testName)
   {
      return false;
   }

   public void testSimple_Module() throws Exception
   {
      // execute();
   }

   public void testReactor_Build() throws Exception
   {
      // execute();
      // execute("reactor");
      // execute("single");
   }

   public void testStructured_Module() throws Exception
   {
      // execute();
   }

   public void testComposite_Module() throws Exception
   {
      // execute();
   }

   public void testRcp_Example() throws Exception
   {
      // execute();
   }

   public void testModule_With_Custom_Ant_Targets() throws Exception
   {
      // execute();
      //
      // PropertiesMap testProperties = new LinkedPropertiesMap();
      // testProperties.load(new File(exampleModuleDir, "target/test.properties"));
      // assertEquals("generate-resources", testProperties.get("maven.phase"));
      //
      // testProperties = new LinkedPropertiesMap();
      // testProperties.load(new File(exampleModuleDir,
      // "org.sourcepit.b2.examples.simple.module/target/test.properties"));
      // assertEquals("generate-resources", testProperties.get("maven.phase"));
      //
      // testProperties = new LinkedPropertiesMap();
      // testProperties.load(new File(exampleModuleDir,
      // "org.sourcepit.b2.examples.simple.module.tests/target/test.properties"));
      // assertEquals("generate-test-resources", testProperties.get("maven.phase"));
   }
}
