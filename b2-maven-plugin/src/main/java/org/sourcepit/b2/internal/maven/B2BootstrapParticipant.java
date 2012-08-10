/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.bval.jsr303.ApacheValidationProvider;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.RepositorySystem;
import org.eclipse.tycho.core.UnknownEnvironmentException;
import org.eclipse.tycho.core.utils.ExecutionEnvironmentUtils;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.execution.B2RequestFactory;
import org.sourcepit.b2.execution.B2SessionRunner;
import org.sourcepit.b2.internal.scm.svn.SCM;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.common.utils.adapt.Adapters;
import org.sourcepit.maven.bootstrap.participation.BootstrapParticipant;
import org.sourcepit.maven.bootstrap.participation.BootstrapSession;
import org.sourcepit.osgify.maven.p2.P2UpdateSiteGenerator;

@Named
public class B2BootstrapParticipant implements BootstrapParticipant
{
   @Inject
   private LegacySupport legacySupport;

   @Inject
   private RepositorySystem repositorySystem;

   @Inject
   private B2SessionService sessionService;

   @Inject
   private SCM scm;

   @Inject
   private B2SessionInitializer b2SessionInitializer;

   @Inject
   private B2SessionRunner sessionRunner;

   @Inject
   private P2UpdateSiteGenerator updateSiteGenerator;

   public void beforeBuild(BootstrapSession bootSession, final MavenProject bootProject)
   {

      final MavenSession mavenSession = legacySupport.getSession();
      final Properties properties = new Properties();
      properties.putAll(mavenSession.getSystemProperties());
      properties.putAll(mavenSession.getUserProperties());

      final B2Session b2Session = b2SessionInitializer.initialize(bootSession, properties);
      mapSessions(bootSession, b2Session);

      final B2RequestFactory b2RequestFactory = new B2RequestFactory()
      {
         public B2Request newRequest(B2Session session)
         {
            return b2SessionInitializer.newB2Request(bootProject);
         }
      };

      File targetDir = new File(bootProject.getBuild().getDirectory());
      List<ArtifactRepository> remoteArtifactRepositories = bootProject.getRemoteArtifactRepositories();
      ArtifactRepository localRepository = legacySupport.getSession().getLocalRepository();
      String repositoryName = bootProject.getName();

      Artifact artifact = repositorySystem.createArtifact("org.apache.maven", "maven-model", "3.0.4", "jar");

      // ClassLoader classLoader = ApacheValidationProvider.class.getClassLoader();
      // Thread.currentThread().setContextClassLoader(classLoader);

      MavenSession session = legacySupport.getSession();
      try
      {

         MavenSession clone = session.clone();
         clone.setProjects(bootSession.getBootstrapProjects());

         legacySupport.setSession(clone);

         updateSiteGenerator.generateUpdateSite(targetDir, artifact, remoteArtifactRepositories, localRepository,
            repositoryName, true, 0);
      }
      finally
      {
         legacySupport.setSession(session);
      }


      sessionRunner.prepareNext(b2Session, b2RequestFactory);
   }

   private static void mapSessions(BootstrapSession bootSession, B2Session b2Session)
   {
      final MavenSession mavenSession = bootSession.getMavenSession();

      final BootstrapSession currentBootSession = Adapters.getAdapter(mavenSession, BootstrapSession.class);
      if (currentBootSession != null && !currentBootSession.equals(bootSession))
      {
         throw new IllegalStateException("Another bootstrap session is already mapped with the current maven session.");
      }

      if (currentBootSession == null)
      {
         Adapters.addAdapter(mavenSession, bootSession);
         Adapters.addAdapter(mavenSession, b2Session);
      }
   }

   public void afterBuild(BootstrapSession bootSession, MavenProject bootProject)
   {
      final B2Session b2Session = sessionService.getCurrentSession();
      for (ModuleProject project : b2Session.getProjects())
      {
         if (bootProject.getBasedir().equals(project.getDirectory()))
         {
            b2Session.setCurrentProject(project);
            break;
         }
      }

      final String setScmIgnoresProp = bootProject.getProperties().getProperty("b2.scm.setScmIgnores",
         System.getProperty("b2.scm.setScmIgnores", "false"));

      final boolean isSetScmIgnores = Boolean.valueOf(setScmIgnoresProp).booleanValue();
      if (isSetScmIgnores)
      {
         ModuleProject project = b2Session.getCurrentProject();
         if (project != null && project.getModuleModel() != null)
         {
            scm.doSetScmIgnores(project.getModuleModel());
         }
      }
   }
}
