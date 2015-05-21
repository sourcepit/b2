/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.internal.maven;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.sourcepit.b2.execution.B2LifecycleRunner;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.execution.B2RequestFactory;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.internal.scm.svn.SCM;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.maven.bootstrap.participation.BootstrapParticipant;

@Named
public class B2BootstrapParticipant implements BootstrapParticipant {
   @Inject
   private SCM scm;

   @Inject
   @Named("maven")
   private B2RequestFactory b2RequestFactory;

   @Inject
   private B2LifecycleRunner b2LifecycleRunner;

   @Inject
   private Logger logger;

   public void beforeBuild(MavenSession bootSession, final MavenProject bootProject, MavenSession actualSession) {
      logger.info("");
      logger.info("------------------------------------------------------------------------");
      logger.info("Bootstrapping " + bootProject.getName() + " " + bootProject.getVersion());
      logger.info("------------------------------------------------------------------------");

      final List<File> projectDirs = new ArrayList<File>();
      for (MavenProject project : bootSession.getProjects()) {
         projectDirs.add(project.getBasedir());
      }

      final int idx = bootSession.getProjects().indexOf(bootProject);
      checkState(idx > -1);

      final B2Request b2Request = b2RequestFactory.newRequest(projectDirs, idx);

      final AbstractModule module = b2LifecycleRunner.prepareNext(projectDirs, idx, b2Request);
      bootProject.setContextValue(AbstractModule.class.getName(), module);
      bootProject.setContextValue(ModuleDirectory.class.getName(), b2Request.getModuleDirectory());
   }

   public void afterBuild(MavenSession bootSession, MavenProject bootProject, MavenSession actualSession) {
      final String setScmIgnoresProp = bootProject.getProperties().getProperty("b2.scm.setScmIgnores",
         System.getProperty("b2.scm.setScmIgnores", "false"));

      final boolean isSetScmIgnores = Boolean.valueOf(setScmIgnoresProp).booleanValue();
      if (isSetScmIgnores) {
         final AbstractModule module = (AbstractModule) bootProject.getContextValue(AbstractModule.class.getName());
         final ModuleDirectory moduleDirectory = (ModuleDirectory) bootProject.getContextValue(ModuleDirectory.class.getName());
         if (module != null && moduleDirectory != null) {
            scm.doSetScmIgnores(moduleDirectory, module);
         }
      }
   }
}
