/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.DefaultDependencyResolutionRequest;
import org.apache.maven.project.DependencyResolutionException;
import org.apache.maven.project.DependencyResolutionResult;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectDependenciesResolver;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyFilter;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.filter.AndDependencyFilter;
import org.sonatype.aether.util.filter.ScopeDependencyFilter;
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
      final SetMultimap<DependencyNode, String> moduleNodeToClassifiers = LinkedHashMultimap.create();
      resolveModuleDependencies(moduleNodeToClassifiers, session, project, scope);

      final SetMultimap<MavenArtifact, String> moduleArtifactToClassifiers = LinkedHashMultimap.create();
      for (Entry<DependencyNode, Collection<String>> entry : moduleNodeToClassifiers.asMap().entrySet())
      {
         DependencyNode dependencyNode = entry.getKey();
         Collection<String> classifiers = entry.getValue();

         final MavenArtifact artifact = MavenModelUtils.toMavenArtifact(dependencyNode.getDependency().getArtifact());
         moduleArtifactToClassifiers.get(artifact).addAll(classifiers);
      }
      return moduleArtifactToClassifiers;
   }

   private DependencyResolutionResult resolveModuleDependencies(
      final SetMultimap<DependencyNode, String> moduleNodeToClassifiers, MavenSession session, MavenProject project,
      String scope)
   {
      DependencyFilter collectionFilter = new ScopeDependencyFilter(null, negate(Collections.singleton(scope)));
      DependencyFilter resolutionFilter;
      resolutionFilter = AndDependencyFilter.newInstance(collectionFilter, new DependencyFilter()
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

               moduleNodeToClassifiers.get(node).add(classifier);
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
      });

      final DefaultDependencyResolutionRequest request;
      request = new DefaultDependencyResolutionRequest(project, session.getRepositorySession());
      request.setResolutionFilter(resolutionFilter);

      try
      {
         return dependenciesResolver.resolve(request);
      }
      catch (DependencyResolutionException e)
      {
         throw Exceptions.pipe(e);
      }
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
