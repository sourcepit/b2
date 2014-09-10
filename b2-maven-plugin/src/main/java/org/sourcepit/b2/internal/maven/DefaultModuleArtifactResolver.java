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
import org.eclipse.aether.AbstractForwardingRepositorySystemSession;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.ArtifactProperties;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.util.graph.selector.AndDependencySelector;
import org.eclipse.aether.util.graph.selector.ScopeDependencySelector;
import org.sourcepit.common.maven.artifact.MavenArtifactUtils;
import org.sourcepit.common.maven.model.MavenArtifact;
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

         final MavenArtifact mavenArtifact = MavenArtifactUtils.toMavenArtifact(artifact);
         moduleArtifactToClassifiers.get(mavenArtifact).addAll(classifiers);
      }
      return moduleArtifactToClassifiers;
   }

   private SetMultimap<Artifact, String> resolveModuleDependencies(final MavenSession session, MavenProject project,
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

      final DependencySelector dependencySelector = createDependencySelector(scope);

      final AbstractForwardingRepositorySystemSession systemSession = new AbstractForwardingRepositorySystemSession()
      {
         @Override
         public DependencySelector getDependencySelector()
         {
            return dependencySelector;
         }

         @Override
         protected RepositorySystemSession getSession()
         {
            return session.getRepositorySession();
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

   private static DependencySelector createDependencySelector(final String scope)
   {
      final DependencySelector moduleDependencySelector = new DependencySelector()
      {
         public boolean selectDependency(Dependency dependency)
         {
            final Artifact artifact = dependency.getArtifact();
            final String type = artifact.getProperty(ArtifactProperties.TYPE, artifact.getExtension());
            return "module".equals(type);
         }

         public DependencySelector deriveChildSelector(DependencyCollectionContext context)
         {
            return this;
         }
      };

      final ScopeDependencySelector scopeDependencySelector = new ScopeDependencySelector(null,
         negate(Collections.singleton(scope)));

      return AndDependencySelector.newInstance(moduleDependencySelector, scopeDependencySelector);
   }

   private static Collection<String> negate(Collection<String> scopes)
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
