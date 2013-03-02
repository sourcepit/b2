/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.util.List;
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
import org.sourcepit.b2.model.module.AbstractModule;
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
         public B2Request newRequest(List<File> projectDirs, int currentIdx)
         {
            return b2SessionInitializer.newB2Request(mavenSession, bootProject);
         }
      };

      final List<File> projectDirs = sessionService.getCurrentProjectDirs();

      final int idx = projectDirs.indexOf(bootProject.getBasedir());
      checkState(idx > -1);

      sessionRunner.prepareNext(projectDirs, idx, b2RequestFactory);
   }

   public void afterBuild(MavenSession bootSession, MavenProject bootProject, MavenSession actualSession)
   {
      final String setScmIgnoresProp = bootProject.getProperties().getProperty("b2.scm.setScmIgnores",
         System.getProperty("b2.scm.setScmIgnores", "false"));

      final boolean isSetScmIgnores = Boolean.valueOf(setScmIgnoresProp).booleanValue();
      if (isSetScmIgnores)
      {
         final File projectDir = bootProject.getBasedir();

         final AbstractModule module = getCurrentModule(projectDir);
         if (module != null)
         {
            scm.doSetScmIgnores(module);
         }
      }
   }

   private AbstractModule getCurrentModule(final File projectDir)
   {
      for (AbstractModule module : sessionService.getCurrentModules())
      {
         if (projectDir.equals(module.getDirectory()))
         {
            return module;
         }
      }
      return null;
   }
}
