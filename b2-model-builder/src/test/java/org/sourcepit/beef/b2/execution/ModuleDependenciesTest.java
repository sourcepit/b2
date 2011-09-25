/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.execution;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.beef.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.beef.b2.model.session.ModuleDependency;
import org.sourcepit.beef.b2.model.session.ModuleProject;

/**
 * @author Bernd
 */
public class ModuleDependenciesTest extends AbstractB2SessionWorkspaceTest
{
   @Override
   protected String setUpModulePath()
   {
      return "reactor-build";
   }

   public void testModuleDependencies() throws Exception
   {
      ModuleProject rcpProject = getModuleProjectByArtifactId("rcp");

      EList<ModuleDependency> dependencies = rcpProject.getDependencies();
      assertEquals(2, dependencies.size());

      assertEquals("rcp.help", dependencies.get(0).getArtifactId());
      assertEquals("rcp.ui", dependencies.get(1).getArtifactId());
   }
}
