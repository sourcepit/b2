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

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.ReleaseExecutionException;
import org.apache.maven.shared.release.ReleaseFailureException;
import org.apache.maven.shared.release.ReleaseResult;
import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.apache.maven.shared.release.env.ReleaseEnvironment;
import org.sourcepit.b2.release.B2ReleaseHelper;

public class ScmCheckModificationsPhase extends org.apache.maven.shared.release.phase.ScmCheckModificationsPhase {
   private B2ReleaseHelper releaseHelper;

   @Override
   public ReleaseResult execute(ReleaseDescriptor releaseDescriptor, ReleaseEnvironment releaseEnvironment,
      List<MavenProject> reactorProjects) throws ReleaseExecutionException, ReleaseFailureException {
      addB2ModificationExcludes(releaseDescriptor);
      return super.execute(releaseDescriptor, releaseEnvironment, releaseHelper.adaptModuleProjects(reactorProjects));
   }

   @Override
   public ReleaseResult simulate(ReleaseDescriptor releaseDescriptor, ReleaseEnvironment releaseEnvironment,
      List<MavenProject> reactorProjects) throws ReleaseExecutionException, ReleaseFailureException {
      addB2ModificationExcludes(releaseDescriptor);
      return super.simulate(releaseDescriptor, releaseEnvironment, releaseHelper.adaptModuleProjects(reactorProjects));
   }

   private static void addB2ModificationExcludes(ReleaseDescriptor releaseDescriptor) {
      final Set<String> modificationExcludes = new LinkedHashSet<String>(
         releaseDescriptor.getCheckModificationExcludes());
      modificationExcludes.add("**" + File.separator + ".b2");
      modificationExcludes.add("**" + File.separator + "pom.xml");
      modificationExcludes.add("**" + File.separator + "module.xml.backup");
      modificationExcludes.add("**" + File.separator + "module.xml.tag");
      modificationExcludes.add("**" + File.separator + "module.xml.next");
      modificationExcludes.add("**" + File.separator + "module.xml.branch");
      modificationExcludes.add("**" + File.separator + "module.xml.releaseBackup");
      releaseDescriptor.setCheckModificationExcludes(new ArrayList<String>(modificationExcludes));
   }
}
