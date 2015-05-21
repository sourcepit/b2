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

package org.sourcepit.b2.release;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.ScmFileSet;
import org.apache.maven.scm.ScmVersion;
import org.apache.maven.scm.command.checkin.CheckInScmResult;
import org.apache.maven.scm.command.edit.EditScmResult;
import org.apache.maven.scm.manager.NoSuchScmProviderException;
import org.apache.maven.scm.provider.ScmProvider;
import org.apache.maven.scm.repository.ScmRepository;
import org.apache.maven.scm.repository.ScmRepositoryException;
import org.apache.maven.settings.Settings;
import org.apache.maven.shared.release.ReleaseExecutionException;
import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.apache.maven.shared.release.env.ReleaseEnvironment;
import org.apache.maven.shared.release.scm.ReleaseScmCommandException;
import org.apache.maven.shared.release.scm.ReleaseScmRepositoryException;
import org.apache.maven.shared.release.scm.ScmRepositoryConfigurator;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.maven.core.B2MavenBridge;
import org.sourcepit.common.utils.lang.Exceptions;

@Named
public class B2ScmHelper {
   private static final String CTX_KEY_COMMIT_PROPOSALS = B2ScmHelper.class.getName() + "#commitProposals";

   private final ScmRepositoryConfigurator scmRepositoryConfigurator;

   private final B2MavenBridge bridge;

   @Inject
   public B2ScmHelper(ScmRepositoryConfigurator scmRepositoryConfigurator, LegacySupport buildContext) {
      this.scmRepositoryConfigurator = scmRepositoryConfigurator;
      bridge = B2MavenBridge.get(buildContext.getSession());
   }

   public void edit(MavenProject mavenProject, ReleaseDescriptor releaseDescriptor, Settings settings, File file,
      boolean simulate) {
      if (!simulate) {
         doScmEdit(releaseDescriptor, settings, file);
      }
      markForCommit(mavenProject, file);
   }

   private void doScmEdit(ReleaseDescriptor releaseDescriptor, Settings settings, File file) {
      ScmRepository scmRepository;
      ScmProvider provider;
      try {
         scmRepository = scmRepositoryConfigurator.getConfiguredRepository(releaseDescriptor, settings);
         provider = scmRepositoryConfigurator.getRepositoryProvider(scmRepository);
      }
      catch (ScmRepositoryException e) {
         throw Exceptions.pipe(new ReleaseScmRepositoryException(e.getMessage(), e.getValidationMessages()));
      }
      catch (NoSuchScmProviderException e) {
         throw Exceptions.pipe(new ReleaseExecutionException("Unable to configure SCM repository: " + e.getMessage(), e));
      }

      try {
         if (releaseDescriptor.isScmUseEditMode() || provider.requiresEditMode()) {
            EditScmResult scmResult = provider.edit(scmRepository,
               new ScmFileSet(new File(releaseDescriptor.getWorkingDirectory()), file));

            if (!scmResult.isSuccess()) {
               throw Exceptions.pipe(new ReleaseScmCommandException("Unable to enable editing on the file", scmResult));
            }
         }
      }
      catch (ScmException e) {
         throw Exceptions.pipe(new ReleaseExecutionException("An error occurred enabling edit mode: " + e.getMessage(),
            e));
      }
   }

   public void markForCommit(MavenProject mavenProject, File file) {
      final MavenProject moduleProject = determineModuleMavenProject(mavenProject);
      final ModuleDirectory moduleDirectory = bridge.getModuleDirectory(moduleProject);
      if (!moduleDirectory.isDerived(file)) {
         getCommitProposals(moduleProject).add(file);
      }
   }

   private MavenProject determineModuleMavenProject(MavenProject mavenProject) {
      MavenProject parentProject = mavenProject;
      while (parentProject != null) {
         if (bridge.getModule(parentProject) != null) {
            return parentProject;
         }
         parentProject = parentProject.getParent();
      }
      throw new IllegalStateException("Unable to determine module project for maven project " + mavenProject);
   }

   private static Set<File> getCommitProposals(MavenProject mavenProject) {
      @SuppressWarnings("unchecked")
      Set<File> files = (Set<File>) mavenProject.getContextValue(CTX_KEY_COMMIT_PROPOSALS);
      if (files == null) {
         files = new LinkedHashSet<File>();
         mavenProject.setContextValue(CTX_KEY_COMMIT_PROPOSALS, files);
      }
      return files;
   }

   public void performCheckins(ReleaseDescriptor releaseDescriptor, ReleaseEnvironment releaseEnvironment,
      List<MavenProject> mavenModuleProjects, String message) throws ReleaseScmRepositoryException,
      ReleaseExecutionException, ReleaseScmCommandException {
      ScmRepository repository;
      ScmProvider provider;
      try {
         repository = scmRepositoryConfigurator.getConfiguredRepository(releaseDescriptor,
            releaseEnvironment.getSettings());

         repository.getProviderRepository().setPushChanges(releaseDescriptor.isPushChanges());

         provider = scmRepositoryConfigurator.getRepositoryProvider(repository);
      }
      catch (ScmRepositoryException e) {
         throw new ReleaseScmRepositoryException(e.getMessage(), e.getValidationMessages());
      }
      catch (NoSuchScmProviderException e) {
         throw new ReleaseExecutionException("Unable to configure SCM repository: " + e.getMessage(), e);
      }

      if (releaseDescriptor.isCommitByProject()) {
         for (MavenProject project : mavenModuleProjects) {
            List<File> pomFiles = collectFiles(releaseDescriptor, project);
            ScmFileSet fileSet = new ScmFileSet(project.getFile().getParentFile(), pomFiles);

            checkin(provider, repository, fileSet, releaseDescriptor, message);
         }
      }
      else {
         List<File> pomFiles = collectFiles(releaseDescriptor, mavenModuleProjects);
         ScmFileSet fileSet = new ScmFileSet(new File(releaseDescriptor.getWorkingDirectory()), pomFiles);

         checkin(provider, repository, fileSet, releaseDescriptor, message);
      }
   }

   private void checkin(ScmProvider provider, ScmRepository repository, ScmFileSet fileSet,
      ReleaseDescriptor releaseDescriptor, String message) throws ReleaseExecutionException, ReleaseScmCommandException {
      CheckInScmResult result;
      try {
         result = provider.checkIn(repository, fileSet, (ScmVersion) null, message);
      }
      catch (ScmException e) {
         throw new ReleaseExecutionException("An error is occurred in the checkin process: " + e.getMessage(), e);
      }

      if (!result.isSuccess()) {
         throw new ReleaseScmCommandException("Unable to commit files", result);
      }
      if (releaseDescriptor.isRemoteTagging()) {
         releaseDescriptor.setScmReleasedPomRevision(result.getScmRevision());
      }
   }

   private List<File> collectFiles(ReleaseDescriptor releaseDescriptor, List<MavenProject> reactorProjects) {
      final List<File> files = new ArrayList<File>();
      for (MavenProject project : reactorProjects) {
         files.addAll(getCommitProposals(project));
      }
      return files;
   }

   private List<File> collectFiles(ReleaseDescriptor releaseDescriptor, MavenProject project) {
      return new ArrayList<File>(getCommitProposals(project));
   }
}
