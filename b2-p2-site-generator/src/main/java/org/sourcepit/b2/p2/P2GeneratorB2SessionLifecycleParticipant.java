/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.p2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.RepositorySystem;
import org.sourcepit.b2.execution.AbstractB2SessionLifecycleParticipant;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.execution.B2SessionLifecycleParticipant;
import org.sourcepit.b2.internal.maven.BootstrapSessionService;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.common.utils.lang.ThrowablePipe;
import org.sourcepit.osgify.maven.p2.P2UpdateSiteGenerator;

@Named
public class P2GeneratorB2SessionLifecycleParticipant extends AbstractB2SessionLifecycleParticipant
   implements
      B2SessionLifecycleParticipant
{
   @Inject
   private P2UpdateSiteGenerator updateSiteGenerator;
   
   @Inject
   private LegacySupport legacySupport;
   
   @Inject
   private RepositorySystem repositorySystem;

   @Override
   public void prePrepareProject(B2Session b2Session, ModuleProject project, B2Request request)
   {
      super.prePrepareProject(b2Session, project, request);
      
      final MavenProject bootProject = legacySupport.getSession().getCurrentProject();
      
      File targetDir = new File(bootProject.getBuild().getDirectory());
      List<ArtifactRepository> remoteArtifactRepositories = bootProject.getRemoteArtifactRepositories();
      ArtifactRepository localRepository = legacySupport.getSession().getLocalRepository();
      String repositoryName = bootProject.getName();

      Artifact artifact = repositorySystem.createArtifact("org.apache.maven", "maven-model", "3.0.4", "jar");

      MavenSession session = legacySupport.getSession();
      try
      {

         final MavenSession clone = session.clone();

         final List<MavenProject> clones = new ArrayList<MavenProject>();
         for (MavenProject mavenProject : bootSession.getBootstrapProjects())
         {
            clones.add(mavenProject.clone());
         }

         clone.setProjects(clones);

         legacySupport.setSession(clone);

         updateSiteGenerator.generateUpdateSite(targetDir, artifact, remoteArtifactRepositories, localRepository,
            repositoryName, true, 0);
      }
      finally
      {
         legacySupport.setSession(session);
      }
   }

   @Override
   public void postPrepareProject(B2Session session, ModuleProject project, B2Request request, AbstractModule module,
      ThrowablePipe errors)
   {
      super.postPrepareProject(session, project, request, module, errors);
   }
}
