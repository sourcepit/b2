/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.RepositorySystem;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.execution.B2RequestFactory;
import org.sourcepit.b2.execution.B2SessionRunner;
import org.sourcepit.b2.internal.scm.svn.SCM;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.maven.bootstrap.participation.BootstrapParticipant;

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

   public void beforeBuild(MavenSession bootSession, final MavenProject bootProject, MavenSession actualSession)
   {
      final MavenSession mavenSession = legacySupport.getSession();
      final Properties properties = new Properties();
      properties.putAll(mavenSession.getSystemProperties());
      properties.putAll(mavenSession.getUserProperties());

      b2SessionInitializer.initialize(bootSession, properties);

      final B2RequestFactory b2RequestFactory = new B2RequestFactory()
      {
         public B2Request newRequest(B2Session session, int currentIdx)
         {
            return b2SessionInitializer.newB2Request(bootProject);
         }
      };

      sessionRunner.prepareNext(b2Session, b2RequestFactory);
   }

   public void afterBuild(MavenSession bootSession, MavenProject bootProject, MavenSession actualSession)
   {
      final B2Session b2Session = sessionService.getCurrentProjectDirs();
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
