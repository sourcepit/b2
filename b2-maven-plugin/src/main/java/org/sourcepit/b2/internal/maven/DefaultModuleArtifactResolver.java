/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.DefaultDependencyResolutionRequest;
import org.apache.maven.project.DependencyResolutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectDependenciesResolver;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.DependencySelector;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyFilter;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.util.FilterRepositorySystemSession;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.graph.selector.ScopeDependencySelector;
import org.sourcepit.common.maven.model.MavenArtifact;
import org.sourcepit.common.maven.model.util.MavenModelUtils;
import org.sourcepit.common.utils.lang.Exceptions;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

@Named
public class DefaultModuleArtifactResolver implements ModuleArtifactResolver
{
   @Inject
   private ProjectDependenciesResolver dependenciesResolver;

   public SetMultimap<MavenArtifact, String> resolve(MavenSession session, MavenProject project, String scope)
   {
      final SetMultimap<Artifact, String> artifactToClassifiers = resolveModuleDependencies(session, project, scope);

      final SetMultimap<MavenArtifact, String> moduleArtifactToClassifiers = LinkedHashMultimap.create();
      for (Entry<Artifact, Collection<String>> entry : artifactToClassifiers.asMap().entrySet())
      {
         Artifact artifact = entry.getKey();
         Collection<String> classifiers = entry.getValue();

         final MavenArtifact mavenArtifact = MavenModelUtils.toMavenArtifact(artifact);
         moduleArtifactToClassifiers.get(mavenArtifact).addAll(classifiers);
      }
      return moduleArtifactToClassifiers;
   }

   private SetMultimap<Artifact, String> resolveModuleDependencies(MavenSession session, MavenProject project,
      final String scope)
   {
      final Map<DependencyNode, String> moduleNodeToClassifiers = new LinkedHashMap<DependencyNode, String>();
      final DependencyFilter resolutionFilter = new DependencyFilter()
      {
         public boolean accept(DependencyNode node, List<DependencyNode> parents)
         {
            Artifact moduleArtifact = getModuleArtifact(node);
            if (moduleArtifact != null)
            {
               final String classifier = moduleArtifact.getClassifier();

               final Artifact newArtifact;
               newArtifact = new DefaultArtifact(moduleArtifact.getGroupId(), moduleArtifact.getArtifactId(), "",
                  moduleArtifact.getExtension(), moduleArtifact.getVersion(), moduleArtifact.getProperties(),
                  moduleArtifact.getFile());
               node.setArtifact(newArtifact);

               moduleNodeToClassifiers.put(node, classifier);
            }
            return true;
         }

         private Artifact getModuleArtifact(DependencyNode node)
         {
            if (node.getDependency() != null)
            {
               final Dependency dependency = node.getDependency();
               if (dependency.getArtifact() != null)
               {
                  final Artifact artifact = dependency.getArtifact();
                  if ("module".equals(artifact.getExtension()))
                  {
                     return artifact;
                  }
               }
            }
            return null;
         }
      };


      final FilterRepositorySystemSession systemSession = new FilterRepositorySystemSession(
         session.getRepositorySession())
      {
         @Override
         public DependencySelector getDependencySelector()
         {
            return new ScopeDependencySelector(null, negate(Collections.singleton(scope)));
         }
      };

      final DefaultDependencyResolutionRequest request;
      request = new DefaultDependencyResolutionRequest(project, systemSession);
      request.setResolutionFilter(resolutionFilter);

      try
      {
         dependenciesResolver.resolve(request);
      }
      catch (DependencyResolutionException e)
      {
         throw Exceptions.pipe(e);
      }

      final SetMultimap<Artifact, String> artifactToClassifiers = LinkedHashMultimap.create();
      for (Entry<DependencyNode, String> entry : moduleNodeToClassifiers.entrySet())
      {
         DependencyNode dependencyNode = entry.getKey();
         artifactToClassifiers.get(dependencyNode.getDependency().getArtifact()).add(entry.getValue());
      }
      return artifactToClassifiers;
   }

   private Collection<String> negate(Collection<String> scopes)
   {
      Collection<String> result = new HashSet<String>();
      Collections.addAll(result, "system", "compile", "provided", "runtime", "test");
      for (String scope : scopes)
      {
         if ("compile".equals(scope))
         {
            result.remove("compile");
            result.remove("system");
            result.remove("provided");
         }
         else if ("runtime".equals(scope))
         {
            result.remove("compile");
            result.remove("runtime");
         }
         else if ("compile+runtime".equals(scope))
         {
            result.remove("compile");
            result.remove("system");
            result.remove("provided");
            result.remove("runtime");
         }
         else if ("runtime+system".equals(scope))
         {
            result.remove("compile");
            result.remove("system");
            result.remove("runtime");
         }
         else if ("test".equals(scope))
         {
            result.clear();
         }
      }
      return result;
   }
}
