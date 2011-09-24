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
   protected String getModulePath()
   {
      return "reactor-build";
   }

   public void testModuleDependencies() throws Exception
   {
      ModuleProject rcpProject = getProject("rcp");

      EList<ModuleDependency> dependencies = rcpProject.getDependencies();
      assertEquals(2, dependencies.size());

      assertEquals("rcp.help", dependencies.get(0).getArtifactId());
      assertEquals("rcp.ui", dependencies.get(1).getArtifactId());
   }

   protected ModuleProject getProject(String artifactId)
   {
      for (ModuleProject project : b2Session.getProjects())
      {
         if (artifactId.equals(project.getArtifactId()))
         {
            return project;
         }
      }
      return null;
   }
}
