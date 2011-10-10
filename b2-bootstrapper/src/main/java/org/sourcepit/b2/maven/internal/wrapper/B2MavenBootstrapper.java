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
import org.sourcepit.maven.wrapper.internal.session.AbstractMavenBootstrapper;
import org.sourcepit.maven.wrapper.internal.session.ISessionListener;

@Component(role = ISessionListener.class)
public class B2MavenBootstrapper extends AbstractMavenBootstrapper
{
   @Requirement
   private ModuleDescriptorProcessor descriptorProcessor;

   protected void getModuleDescriptors(MavenSession session, Collection<File> descriptors,
      Collection<File> skippeDescriptors) throws MavenExecutionException
   {
      descriptorProcessor.findModuleDescriptors(session, descriptors, skippeDescriptors);
   }

   @Override
   protected void beforeWrapperProjectsInitialized(MavenSession session, List<MavenProject> projects)
      throws MavenExecutionException
   {
      // empty
   }

   protected void afterWrapperProjectsInitialized(MavenSession session, List<MavenProject> bootstrapProjects)
   {
      if (!bootstrapProjects.isEmpty())
      {
         final MavenProject mainProject = bootstrapProjects.get(bootstrapProjects.size() - 1);
         final File pom = (File) mainProject.getContextValue("pom");
         if (pom != null)
         {
            final MavenExecutionRequest request = session.getRequest();
            request.setPom(pom);
         }
      }
   }
}
