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

package org.sourcepit.b2.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.maven.artifact.InvalidRepositoryException;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.DefaultMavenExecutionResult;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Repository;
import org.apache.maven.model.RepositoryPolicy;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.project.ProjectSorter;
import org.apache.maven.repository.RepositorySystem;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.internal.impl.SimpleLocalRepositoryManagerFactory;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.NoLocalRepositoryManagerException;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils.AbstractDescriptorResolutionStrategy;
import org.sourcepit.b2.maven.internal.wrapper.DescriptorUtils.IDescriptorResolutionStrategy;
import org.sourcepit.b2.test.resources.internal.harness.AbstractPlexusWorkspaceTest;

public abstract class AbstractMavenSessionWorkspaceTest extends AbstractPlexusWorkspaceTest
{
   @Inject
   protected ProjectBuilder projectBuilder;

   @Inject
   protected RepositorySystem repositorySystem;

   @Inject
   protected SimpleLocalRepositoryManagerFactory localRepositoryManagerFactory;

   protected File moduleDir;

   protected MavenSession mavenSession;

   protected void setUp() throws Exception
   {
      super.setUp();
      repositorySystem = lookup(RepositorySystem.class);
      projectBuilder = lookup(ProjectBuilder.class);
      moduleDir = setUpModuleDir();
      mavenSession = createMavenSession(moduleDir, System.getProperties());
   }

   protected File setUpModuleDir()
   {
      return workspace.importResources(setUpModulePath());
   }

   protected abstract String setUpModulePath();

   @Override
   protected void tearDown() throws Exception
   {
      projectBuilder = null;
      super.tearDown();
   }

   protected MavenSession createMavenSession(File moduleDir, Properties executionProperties) throws Exception
   {
      final List<File> descriptors = new ArrayList<File>();

      final List<File> skippedDescriptors = new ArrayList<File>();
      collectModuleDescriptors(moduleDir, descriptors, skippedDescriptors);

      final File pom = findMainDescriptor(moduleDir, descriptors);

      MavenExecutionRequest request = createMavenExecutionRequest(pom);

      ProjectBuildingRequest configuration = new DefaultProjectBuildingRequest()
         .setLocalRepository(request.getLocalRepository()).setRemoteRepositories(request.getRemoteRepositories())
         .setPluginArtifactRepositories(request.getPluginArtifactRepositories())
         .setSystemProperties(executionProperties);

      final List<MavenProject> projects = new ArrayList<MavenProject>();
      for (File descriptor : descriptors)
      {
         projects.add(projectBuilder.build(descriptor, configuration).getProject());
      }

      initRepoSession(configuration);

      MavenSession session = new MavenSession(getContainer(), configuration.getRepositorySession(), request,
         new DefaultMavenExecutionResult());
      session.setProjects(new ProjectSorter(projects).getSortedProjects());

      return session;
   }

   protected File findMainDescriptor(File moduleDir, final List<File> descriptors)
   {
      for (File file : descriptors)
      {
         if (moduleDir.equals(file.getParentFile()))
         {
            return file;
         }
      }
      return null;
   }

   protected MavenExecutionRequest createMavenExecutionRequest(File pom) throws Exception
   {
      MavenExecutionRequest request = new DefaultMavenExecutionRequest().setPom(pom).setProjectPresent(true)
         .setShowErrors(true).setPluginGroups(Arrays.asList(new String[] { "org.apache.maven.plugins" }))
         .setLocalRepository(getLocalRepository()).setRemoteRepositories(getRemoteRepositories())
         .setPluginArtifactRepositories(getPluginArtifactRepositories())
         .setGoals(Arrays.asList(new String[] { "package" }));

      return request;
   }

   protected void initRepoSession(ProjectBuildingRequest request)
   {
      File localRepo = new File(request.getLocalRepository().getBasedir());
      LocalRepository localRepository = new LocalRepository(localRepo);
      DefaultRepositorySystemSession session = new DefaultRepositorySystemSession();
      try
      {
         session.setLocalRepositoryManager(localRepositoryManagerFactory.newInstance(session, localRepository));
      }
      catch (NoLocalRepositoryManagerException e)
      {
         throw new IllegalStateException(e);
      }
      request.setRepositorySession(session);
   }

   protected List<ArtifactRepository> getRemoteRepositories() throws InvalidRepositoryException
   {
      File repoDir = new File(getBasedir(), "src/test/remote-repo").getAbsoluteFile();

      RepositoryPolicy policy = new RepositoryPolicy();
      policy.setEnabled(true);
      policy.setChecksumPolicy("ignore");
      policy.setUpdatePolicy("always");

      Repository repository = new Repository();
      repository.setId(RepositorySystem.DEFAULT_REMOTE_REPO_ID);
      repository.setUrl("file://" + repoDir.toURI().getPath());
      repository.setReleases(policy);
      repository.setSnapshots(policy);

      return Arrays.asList(repositorySystem.buildArtifactRepository(repository));
   }

   protected List<ArtifactRepository> getPluginArtifactRepositories() throws InvalidRepositoryException
   {
      return getRemoteRepositories();
   }

   protected ArtifactRepository getLocalRepository() throws InvalidRepositoryException
   {
      File repoDir = new File(getBasedir(), "target/local-repo").getAbsoluteFile();
      return repositorySystem.createLocalRepository(repoDir);
   }

   protected void collectModuleDescriptors(File moduleDir, List<File> descriptors, List<File> skippedDescriptors)
   {
      final IDescriptorResolutionStrategy resolver = new AbstractDescriptorResolutionStrategy(moduleDir, null)
      {
         public File getDescriptor(File directory)
         {
            final File descriptor = new File(directory, "module.xml");
            if (descriptor.exists() && descriptor.isFile())
            {
               return descriptor;
            }
            return null;
         }
      };
      DescriptorUtils.findModuleDescriptors(moduleDir, descriptors, skippedDescriptors, resolver);
   }
}
