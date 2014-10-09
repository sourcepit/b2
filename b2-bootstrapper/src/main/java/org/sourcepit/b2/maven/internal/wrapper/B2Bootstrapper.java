/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.maven.internal.wrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.building.ModelProcessor;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.logging.Logger;
import org.sourcepit.maven.bootstrap.core.AbstractBootstrapper;
import org.sourcepit.maven.exec.intercept.MavenExecutionParticipant;

@Named
public class B2Bootstrapper extends AbstractBootstrapper implements MavenExecutionParticipant
{
   private static final String CONFIGURATION_SWITCH_SKIP_B2 = "skipB2";
   @Inject
   private Logger logger;


   @Override
   public void executionStarted(MavenSession arg0, MavenExecutionRequest arg1) throws MavenExecutionException
   {
      if (arg0.getUserProperties().containsKey(CONFIGURATION_SWITCH_SKIP_B2))
      {
         logger.info("");
         logger.info("------------------------------------------------------------------------");
         logger.info("       Skipping B2 Bootstrapping due to given Paramter -D" + CONFIGURATION_SWITCH_SKIP_B2 + ".");
         logger.info("------------------------------------------------------------------------");
      }
      else
      {
         super.executionStarted(arg0, arg1);
      }
   }


   @Override
   public void executionEnded(MavenSession actualSession, MavenExecutionResult executionResult)
   {
      if (!actualSession.getUserProperties().containsKey(CONFIGURATION_SWITCH_SKIP_B2))
      {
         super.executionEnded(actualSession, executionResult);
      }
   }

   private final ModuleDescriptorProcessor descriptorProcessor;

   @Inject
   public B2Bootstrapper(ModuleDescriptorProcessor descriptorProcessor, ModelProcessor modelProcessor)
   {
      super("org.sourcepit.b2", "b2-maven-plugin");
      this.descriptorProcessor = descriptorProcessor;
      if (!(modelProcessor instanceof B2ModelProcessor))
      {
         throw new IllegalArgumentException(
            "Maven default module processor is expected to ne overriden by a b2 specific implementation.");
      }
   }

   @Override
   protected boolean isAllowExtensionExtensions(MavenSession bootSession, MavenProject bootProject)
   {
      return true;
   }

   @Override
   protected void discoverProjectDescriptors(MavenSession session, Collection<File> descriptors,
      Collection<File> skippedDescriptors)
   {
      descriptorProcessor.findModuleDescriptors(session, descriptors, skippedDescriptors);
   }

   @Override
   protected String getDependencyResolutionRequired()
   {
      return null;
   }

   @Override
   protected List<ArtifactRepository> filterArtifactRepositories(List<ArtifactRepository> remoteRepositories)
   {
      final List<ArtifactRepository> result = new ArrayList<ArtifactRepository>();
      for (ArtifactRepository remoteRepository : remoteRepositories)
      {
         if (!"p2".equals(remoteRepository.getLayout().getId()))
         {
            result.add(remoteRepository);
         }
      }
      return result;
   }

   @Override
   protected void adjustActualSession(MavenSession bootSession, MavenSession actualSession)
   {
      final File baseDir;
      if (bootSession.getRequest().getPom() == null)
      {
         baseDir = new File(bootSession.getRequest().getBaseDirectory());
      }
      else
      {
         baseDir = bootSession.getRequest().getPom().getParentFile();
      }

      MavenProject mainBootProject = null;
      for (MavenProject bootProject : bootSession.getProjects())
      {
         if (baseDir.equals(bootProject.getBasedir()))
         {
            mainBootProject = bootProject;
            break;
         }
      }

      final File pom = (File) mainBootProject.getContextValue("pom");
      if (pom != null)
      {
         final MavenExecutionRequest request = actualSession.getRequest();
         request.setPom(pom);
      }
   }
}
