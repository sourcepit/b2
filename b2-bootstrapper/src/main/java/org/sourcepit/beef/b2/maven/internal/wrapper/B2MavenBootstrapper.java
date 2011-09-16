/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.maven.internal.wrapper;

import java.io.File;
import java.util.List;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sourcepit.beef.maven.wrapper.internal.session.AbstractMavenBootstrapper;
import org.sourcepit.beef.maven.wrapper.internal.session.ISessionListener;

@Component(role = ISessionListener.class)
public class B2MavenBootstrapper extends AbstractMavenBootstrapper
{
   @Requirement
   private ModuleDescriptorProcessor descriptorProcessor;

   protected List<File> getModuleDescriptors(MavenSession session) throws MavenExecutionException
   {
      final File baseDir = new File(session.getRequest().getBaseDirectory());
      return descriptorProcessor.findModuleDescriptors(baseDir);
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
