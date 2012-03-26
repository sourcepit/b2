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
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.execution.B2RequestFactory;
import org.sourcepit.b2.execution.B2SessionRunner;
import org.sourcepit.b2.internal.scm.svn.SCM;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.maven.bootstrap.participation.BootstrapParticipant;
import org.sourcepit.maven.bootstrap.participation.BootstrapSession;

@Named
public class B2BootstrapParticipant implements BootstrapParticipant
{
   @Inject
   private LegacySupport legacySupport;

   @Inject
   private B2SessionService sessionService;

   @Inject
   private SCM scm;

   @Inject
   private B2SessionInitializer b2SessionInitializer;

   @Inject
   private B2SessionRunner sessionRunner;

   public void beforeBuild(BootstrapSession bootSession, final MavenProject bootProject)
   {
      final MavenSession mavenSession = legacySupport.getSession();
      final Properties properties = new Properties();
      properties.putAll(mavenSession.getSystemProperties());
      properties.putAll(mavenSession.getUserProperties());

      final B2Session b2Session = b2SessionInitializer.initialize(bootSession, properties);

      final B2RequestFactory b2RequestFactory = new B2RequestFactory()
      {
         public B2Request newRequest(B2Session session)
         {
            return b2SessionInitializer.newB2Request(bootProject);
         }
      };

      sessionRunner.prepareNext(b2Session, b2RequestFactory);
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
