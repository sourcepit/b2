/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.RepositoryUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.ModelBase;
import org.apache.maven.model.Profile;
import org.apache.maven.model.Repository;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.common.util.ArtifactIdentifier;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class P2RepositoryDependencyConverter extends AbstractPomGenerator implements IB2GenerationParticipant
{
   @Inject
   private LegacySupport legacySupport;

   @Inject
   private RepositorySystem repositorySystem;

   @Inject
   private Logger logger;

   @Override
   protected void generate(Annotatable inputElement, boolean skipFacets, PropertiesSource properties,
      ITemplates templates)
   {
      final MavenSession session = legacySupport.getSession();
      final MavenProject project = session.getCurrentProject();

      final List<Dependency> p2RepoDeps = determineP2RepositoryDependencies(project);
      if (!p2RepoDeps.isEmpty())
      {
         final File pomFile = resolvePomFile(inputElement);
         final Model pom = readMavenModel(pomFile);

         for (Dependency p2RepoDep : p2RepoDeps)
         {
            final Artifact artifact = resolveArtifact(project, p2RepoDep.getGroupId(), p2RepoDep.getArtifactId(),
               "zip", p2RepoDep.getVersion(), p2RepoDep.getClassifier());

            final ArtifactIdentifier ident = toArtifactIdentifier(artifact);

            final Repository repository = toRepository(ident, artifact.getFile());

            logger.info("Resolved dependency '" + ident + "' to local p2 repository '" + repository.getUrl() + "'");
            pom.getRepositories().add(repository);
         }

         filterDependencies(pom, p2RepoDeps);
         writeMavenModel(pomFile, pom);
      }
   }

   private static Repository toRepository(ArtifactIdentifier ident, File file)
   {
      final String path = file.getAbsolutePath().replace('\\', '/');
      final String siteUrl = "jar:file:" + path + "!/";

      final Repository repository = new Repository();
      repository.setId(ident.toString().replace(':', '_'));
      repository.setUrl(siteUrl);
      repository.setLayout("p2");

      return repository;
   }

   private static ArtifactIdentifier toArtifactIdentifier(final Artifact artifact)
   {
      final ArtifactIdentifier uniqueId = new ArtifactIdentifier(artifact.getGroupId(), artifact.getArtifactId(),
         artifact.getVersion(), artifact.getClassifier(), artifact.getType());
      return uniqueId;
   }

   private static List<Dependency> determineP2RepositoryDependencies(final MavenProject project)
   {
      final List<Dependency> dependencies = new ArrayList<Dependency>();
      for (Dependency dependency : project.getDependencies())
      {
         if ("p2-repository".equals(dependency.getType()))
         {
            dependencies.add(dependency);
         }
      }
      return dependencies;
   }

   private static void filterDependencies(final ModelBase mavenModel, final List<Dependency> blackList)
   {
      final List<Dependency> filteredDependencies = new ArrayList<Dependency>();

      for (Dependency dependency : mavenModel.getDependencies())
      {
         if (!containsDependency(blackList, dependency))
         {
            filteredDependencies.add(dependency);
         }
      }

      mavenModel.setDependencies(filteredDependencies);

      if (mavenModel instanceof Model)
      {
         for (Profile profile : ((Model) mavenModel).getProfiles())
         {
            filterDependencies(profile, blackList);
         }
      }
   }

   private static boolean containsDependency(final List<Dependency> dependencies, Dependency dependency)
   {
      for (Dependency d : dependencies)
      {
         if (dependency.getManagementKey().equals(d.getManagementKey()))
         {
            return true;
         }
      }
      return false;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(AbstractModule.class);
   }

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.MODULE_RESOURCE_FILTER;
   }

   private Artifact resolveArtifact(final MavenProject wrapperProject, String groupId, String artifactId,
      String extension, String version, String classifier)
   {
      final org.sonatype.aether.artifact.Artifact siteArtifact = new DefaultArtifact(groupId, artifactId, classifier,
         extension, version);

      ArtifactRequest request = new ArtifactRequest();
      request.setArtifact(siteArtifact);
      request.setRepositories(wrapperProject.getRemoteProjectRepositories());

      try
      {
         final ArtifactResult result = repositorySystem.resolveArtifact(legacySupport.getRepositorySession(), request);
         return RepositoryUtils.toArtifact(result.getArtifact());
      }
      catch (ArtifactResolutionException e)
      {
         throw new IllegalStateException(e);
      }
   }

}
