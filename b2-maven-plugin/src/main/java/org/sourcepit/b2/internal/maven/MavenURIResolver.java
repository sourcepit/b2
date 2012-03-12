/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.common.util.URI;
import org.sourcepit.b2.model.common.util.ArtifactIdentifier;
import org.sourcepit.b2.model.common.util.ArtifactReference;
import org.sourcepit.b2.model.common.util.ArtifactURIResolver;

public class MavenURIResolver implements ArtifactURIResolver
{
   private final List<MavenProject> projects;
   private final MavenProject currentProject;

   private final List<MavenProjectURIResolver> projectURIResolvers = new ArrayList<MavenProjectURIResolver>();;

   public MavenURIResolver(List<MavenProject> projects, MavenProject currentProject)
   {
      this.projects = projects;
      this.currentProject = currentProject;
   }

   public List<MavenProjectURIResolver> getProjectURIResolvers()
   {
      return projectURIResolvers;
   }

   public URI resolve(ArtifactReference artifactReference)
   {
      URI uri = resolve(currentProject, artifactReference);
      if (uri != null)
      {
         return uri;
      }

      for (MavenProject project : projects)
      {
         if (project.equals(currentProject))
         {
            continue;
         }
         uri = resolve(project, artifactReference);
         if (uri != null)
         {
            return uri;
         }
      }

      return null;
   }

   private boolean isContainedInProject(final MavenProject project, ArtifactReference artifactReference)
   {
      return new ArtifactIdentifier(project.getGroupId(), project.getArtifactId(), project.getVersion(),
         artifactReference.getClassifier(), artifactReference.getType()).isTargetOf(artifactReference);
   }

   private URI resolve(MavenProject project, ArtifactReference artifactReference)
   {
      URI uri = null;
      if (isContainedInProject(project, artifactReference))
      {
         if (toArtifactIdentifier(project, null, "pom").isTargetOf(artifactReference))
         {
            uri = createProjectURI(project, null, "pom");
            if (uri != null)
            {
               return uri;
            }
         }

         if (project.getArtifact() != null)
         {
            uri = resolve(project.getArtifact(), artifactReference);
            if (uri != null)
            {
               return uri;
            }
         }

         uri = resolve(nullSafe(project.getAttachedArtifacts()), artifactReference);
         if (uri != null)
         {
            return uri;
         }

         uri = createProjectURI(project, artifactReference.getClassifier(), artifactReference.getType());
         if (uri != null)
         {
            return uri;
         }
      }

      uri = resolve(nullSafe(project.getArtifacts()), artifactReference);
      if (uri != null)
      {
         return uri;
      }

      uri = resolve(nullSafe(project.getDependencyArtifacts()), artifactReference);
      if (uri != null)
      {
         return uri;
      }

      return null;
   }

   @SuppressWarnings("unchecked")
   private static <T> Collection<T> nullSafe(Collection<T> list)
   {
      return list == null ? (Collection<T>) Collections.EMPTY_LIST : list;
   }

   private URI resolve(Collection<Artifact> artifacts, ArtifactReference artifactReference)
   {
      if (artifacts != null)
      {
         for (Artifact artifact : artifacts)
         {
            final URI uri = resolve(artifact, artifactReference);
            if (uri != null)
            {
               return uri;
            }
         }
      }
      return null;
   }

   private URI resolve(Artifact artifact, ArtifactReference artifactReference)
   {
      if (toArtifactIdentifier(artifact).isTargetOf(artifactReference))
      {
         return createArtifactUri(artifact);
      }
      return null;
   }

   private URI createProjectURI(MavenProject project, String classifier, String type)
   {
      if (classifier == null && "pom".equals(type))
      {
         final File file = project.getFile();
         if (file != null)
         {
            return URI.createFileURI(file.getAbsolutePath());
         }
      }

      for (MavenProjectURIResolver projectURIResolver : projectURIResolvers)
      {
         final URI uri = projectURIResolver.resolveProjectUri(project, classifier, type);
         if (uri != null)
         {
            return uri;
         }
      }

      return null;
   }


   private URI createArtifactUri(Artifact artifact)
   {
      final File file = artifact.getFile();
      if (file != null)
      {
         return URI.createFileURI(file.getAbsolutePath());
      }
      return null;
   }

   public static ArtifactIdentifier toArtifactIdentifier(MavenProject project, String classifier, String type)
   {
      return new ArtifactIdentifier(project.getGroupId(), project.getArtifactId(), project.getVersion(), classifier,
         type);
   }

   public static ArtifactIdentifier toArtifactIdentifier(Artifact artifact)
   {
      return new ArtifactIdentifier(artifact.getGroupId(), artifact.getArtifactId(), artifact.getBaseVersion(),
         artifact.getClassifier(), artifact.getType());
   }

   public static ArtifactReference toArtifactReference(MavenProject project, String classifier, String type)
   {
      return toArtifactIdentifier(project, classifier, type).toArtifactReference();
   }
}
