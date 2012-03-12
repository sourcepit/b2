/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.maven.internal.wrapper;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sourcepit.maven.bootstrap.core.AbstractBootstrapper;
import org.sourcepit.maven.exec.intercept.MavenExecutionParticipant;

@Component(role = MavenExecutionParticipant.class)
public class B2Bootstrapper extends AbstractBootstrapper
{
   @Requirement
   private ModuleDescriptorProcessor descriptorProcessor;

   protected void getModuleDescriptors(MavenSession session, Collection<File> descriptors,
      Collection<File> skippeDescriptors) throws MavenExecutionException
   {
      descriptorProcessor.findModuleDescriptors(session, descriptors, skippeDescriptors);
   }

   @Override
   protected void beforeBootstrapProjects(MavenSession bootSession, List<MavenProject> bootProjects)
      throws MavenExecutionException
   {
      // empty
   }

   protected void afterWrapperProjectsInitialized(MavenSession bootSession, List<MavenProject> bootProjects)
   {
      if (!bootProjects.isEmpty())
      {
         final MavenProject mainProject = bootProjects.get(bootProjects.size() - 1);
         final File pom = (File) mainProject.getContextValue("pom");
         if (pom != null)
         {
            final MavenExecutionRequest request = bootSession.getRequest();
            request.setPom(pom);
         }
      }
   }
}
