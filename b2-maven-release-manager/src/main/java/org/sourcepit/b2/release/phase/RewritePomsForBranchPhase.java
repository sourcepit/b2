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
