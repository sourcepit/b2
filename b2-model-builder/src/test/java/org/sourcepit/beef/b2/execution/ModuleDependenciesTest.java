/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.execution;

import org.sourcepit.beef.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;

/**
 * @author Bernd
 */
public class ModuleDependenciesTest extends AbstractB2SessionWorkspaceTest
{
   @Override
   protected String getModulePath()
   {
      return "reactor-build";
   }

   public void testModuleDependencies() throws Exception
   {
      System.out.println(b2Session);
   }
}
