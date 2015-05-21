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

package org.sourcepit.b2.its.util;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.maven.model.Scm;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.sourcepit.common.utils.lang.Exceptions;

public class GitSCM implements SCM {
   private final File repoDir;

   private final Collection<File> submoduleDirs;

   private Git git;

   public GitSCM(File repoDir, File... submoduleDirs) {
      this(repoDir, submoduleDirs == null ? null : Arrays.asList(submoduleDirs));
   }

   @SuppressWarnings("unchecked")
   public GitSCM(File repoDir, Collection<File> submoduleDirs) {
      this.repoDir = repoDir;
      this.submoduleDirs = submoduleDirs == null ? Collections.EMPTY_LIST : submoduleDirs;
   }

   public void create() {
      try {
         git = create(repoDir);
         for (File submoduleDir : submoduleDirs) {
            create(submoduleDir);
            git.submoduleAdd()
               .setPath(submoduleDir.getName())
               .setURI("file:///" + submoduleDir.getAbsolutePath())
               .call();
         }

         if (!submoduleDirs.isEmpty()) {
            git.add().addFilepattern(".gitmodules").call();
            git.commit().setMessage("Added sumodules").call();
            git.submoduleInit().call();
            git.submoduleUpdate().call();
         }
      }
      catch (GitAPIException e) {
         throw Exceptions.pipe(e);
      }
   }

   private static Git create(File repoDir) throws GitAPIException, NoFilepatternException, NoHeadException,
      NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException {
      final Git git = Git.init().setDirectory(repoDir).call();
      git.add().addFilepattern(".").call();
      git.commit().setAll(true).setMessage("Initial commit").call();
      return git;
   }

   public Scm createMavenScmModel(File projectDir, String version) {
      if (!projectDir.equals(repoDir)) {
         return null;
      }
      final Scm scm = new Scm();
      scm.setConnection("scm:git:file:///" + projectDir.getAbsolutePath().replace('\\', '/'));
      final boolean isSnapshot = version == null || version.endsWith("-SNAPSHOT");
      if (!isSnapshot) {
         scm.setTag(projectDir.getName() + "-" + version);
      }
      return scm;
   }

   public void switchVersion(String version) {
      final boolean isSnapshot = version == null || version.endsWith("-SNAPSHOT");
      try {
         git.checkout().setName(isSnapshot ? "master" : repoDir.getName() + "-" + version).call();
         git.reset().setMode(ResetType.HARD).call();
         git.clean().setIgnore(false).setCleanDirectories(true).call();
      }
      catch (Exception e) {
         throw Exceptions.pipe(e);
      }
   }

}
