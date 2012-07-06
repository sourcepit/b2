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
import org.sourcepit.maven.bootstrap.participation.BootstrapSession;
import org.sourcepit.maven.exec.intercept.MavenExecutionParticipant;

@Component(role = MavenExecutionParticipant.class)
public class B2Bootstrapper extends AbstractBootstrapper
{
   @Requirement
   private ModuleDescriptorProcessor descriptorProcessor;

   public B2Bootstrapper()
   {
      super("org.sourcepit.b2", "b2-maven-plugin");
   }

   protected void getModuleDescriptors(MavenSession session, Collection<File> descriptors,
      Collection<File> skippeDescriptors) throws MavenExecutionException
   {
      descriptorProcessor.findModuleDescriptors(session, descriptors, skippeDescriptors);
   }

   @Override
   protected void beforeBootstrapProjects(BootstrapSession bootstrapSession) throws MavenExecutionException
   {
      // empty
   }

   protected void afterWrapperProjectsInitialized(BootstrapSession bootstrapSession)
   {
      final List<MavenProject> bootProjects = bootstrapSession.getBootstrapProjects();
      if (!bootProjects.isEmpty())
      {
         final MavenProject mainProject = bootProjects.get(bootProjects.size() - 1);
         final File pom = (File) mainProject.getContextValue("pom");
         if (pom != null)
         {
            final MavenSession mavenSession = bootstrapSession.getMavenSession();
            
            final MavenExecutionRequest request = mavenSession.getRequest();
            request.setPom(pom);
         }
      }
   }
}
