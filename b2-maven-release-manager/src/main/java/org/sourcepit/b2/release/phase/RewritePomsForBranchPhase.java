/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.release.phase;

import java.util.List;

import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.ReleaseExecutionException;
import org.apache.maven.shared.release.ReleaseFailureException;
import org.apache.maven.shared.release.ReleaseResult;
import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.apache.maven.shared.release.env.ReleaseEnvironment;
import org.sourcepit.b2.release.B2ReleaseHelper;
import org.sourcepit.b2.release.B2ScmHelper;

public class RewritePomsForBranchPhase extends org.apache.maven.shared.release.phase.RewritePomsForBranchPhase
{
   private B2ReleaseHelper releaseHelper;

   private B2ScmHelper scmHelper;

   @Override
   public ReleaseResult execute(ReleaseDescriptor releaseDescriptor, ReleaseEnvironment releaseEnvironment,
      List<MavenProject> reactorProjects) throws ReleaseExecutionException, ReleaseFailureException
   {
      final List<MavenProject> mavenModuleProjects = releaseHelper.adaptModuleProjects(reactorProjects);
      final ReleaseResult result = super.execute(releaseDescriptor, releaseEnvironment, mavenModuleProjects);
      markModuleXmlsForCommit(mavenModuleProjects);
      return result;
   }

   @Override
   public ReleaseResult simulate(ReleaseDescriptor releaseDescriptor, ReleaseEnvironment releaseEnvironment,
      List<MavenProject> reactorProjects) throws ReleaseExecutionException, ReleaseFailureException
   {
      final List<MavenProject> mavenModuleProjects = releaseHelper.adaptModuleProjects(reactorProjects);
      final ReleaseResult result = super.simulate(releaseDescriptor, releaseEnvironment, mavenModuleProjects);
      markModuleXmlsForCommit(mavenModuleProjects);
      return result;
   }

   private void markModuleXmlsForCommit(List<MavenProject> mavenModuleProjects)
   {
      for (MavenProject mavenProject : mavenModuleProjects)
      {
         scmHelper.markForCommit(mavenProject, mavenProject.getFile());
      }
   }
}
