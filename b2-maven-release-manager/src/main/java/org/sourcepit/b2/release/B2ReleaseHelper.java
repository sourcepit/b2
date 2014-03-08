/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.release;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.ReleaseResult;
import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.apache.maven.shared.release.env.ReleaseEnvironment;
import org.eclipse.emf.common.util.URI;
import org.sourcepit.b2.maven.core.B2MavenBridge;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.manifest.resource.ManifestResource;
import org.sourcepit.common.utils.lang.Exceptions;

@Named
public class B2ReleaseHelper
{
   @Inject
   private B2MavenBridge bridge;

   @Inject
   private B2ScmHelper scmHelper;

   public List<MavenProject> adaptModuleProjects(List<MavenProject> reactorProjects)
   {
      final List<MavenProject> moduleProjects = new ArrayList<MavenProject>();
      for (MavenProject reactorProject : reactorProjects)
      {
         final MavenProject moduleProject = adaptModuleProject(reactorProject);
         if (moduleProject != null)
         {
            moduleProjects.add(moduleProject);
         }
      }
      return moduleProjects;
   }

   public MavenProject adaptModuleProject(MavenProject reactorProject)
   {
      MavenProject moduleProject = (MavenProject) reactorProject.getContextValue("b2.moduleProject");
      if (moduleProject == null)
      {
         if (bridge.getModule(reactorProject) != null)
         {
            moduleProject = (MavenProject) reactorProject.clone();
            moduleProject.setFile(new File(reactorProject.getBasedir(), "module.xml"));
            reactorProject.setContextValue("b2.moduleProject", moduleProject);
         }
      }
      return moduleProject;
   }

   private Map<MavenProject, Project> getEclipseProjects(List<MavenProject> mavenProjects, boolean includeDerived)
   {
      final Map<MavenProject, Project> result = new HashMap<MavenProject, Project>();
      for (MavenProject mavenProject : mavenProjects)
      {
         final Project eclipseProject = bridge.getEclipseProject(mavenProject);
         if (eclipseProject != null && (includeDerived || !eclipseProject.isDerived()))
         {
            result.put(mavenProject, eclipseProject);
         }
      }
      return result;
   }

   private String toOSGiVersion(final String mavenVersion)
   {
      final boolean isSnapshot = ArtifactUtils.isSnapshot(mavenVersion);
      int qualiIdx = mavenVersion.indexOf('-');
      if (qualiIdx > -1)
      {
         if (isSnapshot)
         {
            return mavenVersion.substring(0, qualiIdx) + ".qualifier";
         }
         else
         {
            char[] chars = mavenVersion.toCharArray();
            chars[qualiIdx] = '.';
            return new String(chars);
         }
      }
      return mavenVersion;
   }

   public void rewriteEclipseVersions(ReleaseResult result, ReleaseDescriptor releaseDescriptor,
      ReleaseEnvironment releaseEnvironment, List<MavenProject> mavenProjects, boolean release, boolean simulate)
   {
      final Map<MavenProject, Project> projectMap = getEclipseProjects(mavenProjects, false);
      if (!projectMap.isEmpty())
      {
         rewriteEclipseVersions(result, releaseDescriptor, releaseEnvironment, projectMap, release, simulate);
      }
   }

   private void rewriteEclipseVersions(ReleaseResult result, ReleaseDescriptor releaseDescriptor,
      ReleaseEnvironment releaseEnvironment, Map<MavenProject, Project> projectMap, boolean release, boolean simulate)
   {
      for (Entry<MavenProject, Project> entry : projectMap.entrySet())
      {
         MavenProject mavenProject = entry.getKey();
         Project eclipseProject = entry.getValue();
         if (eclipseProject instanceof PluginProject)
         {
            rewriteBundleVersion(result, releaseDescriptor, releaseEnvironment, mavenProject, release, simulate);
         }
         else
         {
            throw new UnsupportedOperationException(eclipseProject.getClass().getName() + " currently not supported");
         }
      }
   }

   private void rewriteBundleVersion(ReleaseResult result, ReleaseDescriptor releaseDescriptor,
      ReleaseEnvironment releaseEnvironment, MavenProject mavenProject, boolean release, boolean simulate)
   {
      final String mavenVersion = determineNewMavenVersion(releaseDescriptor, mavenProject, release);

      final String osgiVersion = toOSGiVersion(mavenVersion);

      final File manifestFile = new File(mavenProject.getBasedir(), "META-INF/MANIFEST.MF");
      if (!manifestFile.canRead())
      {
         throw Exceptions.pipe(new FileNotFoundException(manifestFile.getPath()));
      }

      scmHelper.edit(mavenProject, releaseDescriptor, releaseEnvironment.getSettings(), manifestFile, simulate);

      final BundleManifest manifest = readBundleManifest(manifestFile);
      manifest.setBundleVersion(osgiVersion);
      try
      {
         manifest.eResource().save(null);
      }
      catch (IOException e)
      {
         throw Exceptions.pipe(e);
      }
   }

   private String determineNewMavenVersion(ReleaseDescriptor releaseDescriptor, MavenProject mavenProject,
      boolean release)
   {
      @SuppressWarnings("unchecked")
      final Map<String, String> versionMap = release ? releaseDescriptor.getReleaseVersions() : releaseDescriptor
         .getDevelopmentVersions();
      String projectId = ArtifactUtils.versionlessKey(mavenProject.getGroupId(), mavenProject.getArtifactId());
      String mavenVersion = versionMap.get(projectId);
      if (mavenVersion == null)
      {
         final MavenProject moduleProject = determineModuleMavenProject(mavenProject);
         projectId = ArtifactUtils.versionlessKey(moduleProject.getGroupId(), moduleProject.getArtifactId());
         mavenVersion = versionMap.get(projectId);
      }
      return mavenVersion;
   }

   public MavenProject determineModuleMavenProject(MavenProject mavenProject)
   {
      MavenProject parentProject = mavenProject;
      while (parentProject != null)
      {
         if (bridge.getModule(parentProject) != null)
         {
            return parentProject;
         }
         parentProject = parentProject.getParent();
      }
      throw new IllegalStateException("Unable to determine module project for maven project " + mavenProject);
   }

   private BundleManifest readBundleManifest(final File manifestFile)
   {
      final ManifestResource resource = new BundleManifestResourceImpl(
         URI.createFileURI(manifestFile.getAbsolutePath()));
      try
      {
         resource.load(null);
      }
      catch (IOException e)
      {
         throw Exceptions.pipe(e);
      }
      return (BundleManifest) resource.getContents().get(0);
   }
}
