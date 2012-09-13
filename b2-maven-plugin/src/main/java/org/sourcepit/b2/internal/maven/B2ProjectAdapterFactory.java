/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.DefaultDependencyResolutionRequest;
import org.apache.maven.project.DependencyResolutionException;
import org.apache.maven.project.DependencyResolutionResult;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectDependenciesResolver;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyFilter;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.filter.AndDependencyFilter;
import org.sonatype.aether.util.filter.ScopeDependencyFilter;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.common.utils.collections.LinkedMultiValuHashMap;
import org.sourcepit.common.utils.collections.MultiValueMap;
import org.sourcepit.common.utils.lang.Exceptions;

@Named
public class B2ProjectAdapterFactory
{
   @Inject
   private LayoutManager layoutManager;

   @Inject
   private ProjectDependenciesResolver dependenciesResolver;

   public B2Project adapt(MavenSession session, MavenProject project)
   {
      synchronized (project)
      {
         B2Project b2Project = (B2Project) project.getContextValue(B2Project.class.getName());
         if (b2Project == null)
         {
            b2Project = createB2Project(session, project);
            project.setContextValue(B2Project.class.getName(), b2Project);
         }
         return b2Project;
      }
   }

   public static B2Project getB2Project(MavenProject project)
   {
      synchronized (project)
      {
         return (B2Project) project.getContextValue(B2Project.class.getName());
      }
   }

   private B2Project createB2Project(MavenSession session, MavenProject project)
   {
      final ResourceSet resourceSet = createResourceSet();

      final Map<URI, Collection<String>> scopeTest = new LinkedHashMap<URI, Collection<String>>();
      initURIMapping(scopeTest, session, project, resourceSet, "test");

      final Map<URI, Collection<String>> scopeCompile = new LinkedHashMap<URI, Collection<String>>();
      initURIMapping(scopeCompile, session, project, resourceSet, "compile");

      final URI artifactURI = toArtifactURI(project, "module", null);
      final URI fileURI = URI.createFileURI(pathOfMetaDataFile(project.getBasedir(), "b2.module"));
      resourceSet.getURIConverter().getURIMap().put(artifactURI, fileURI);

      return new B2Project(artifactURI, project.getBasedir(), resourceSet, scopeCompile, scopeTest);
   }

   private static String pathOfMetaDataFile(File directory, String name)
   {
      final StringBuilder sb = new StringBuilder();
      final String modulePath = directory.getAbsolutePath();
      if (modulePath.length() != 0)
      {
         sb.append(modulePath);
         sb.append(File.separatorChar);
      }
      sb.append(".b2");
      sb.append(File.separatorChar);
      sb.append(name);
      return sb.toString();
   }

   private static ResourceSet createResourceSet()
   {
      final ResourceSet resourceSet = new ResourceSetImpl();
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("gav", new XMIResourceFactoryImpl());
      return resourceSet;
   }

   private void initURIMapping(Map<URI, Collection<String>> artifactURIs, MavenSession session, MavenProject project,
      ResourceSet resourceSet, String scope)
   {
      // resolve module artifacts
      final MultiValueMap<DependencyNode, String> moduleNodeToClassifiers;
      moduleNodeToClassifiers = new LinkedMultiValuHashMap<DependencyNode, String>(LinkedHashSet.class);
      resolveModuleDependencies(moduleNodeToClassifiers, session, project, scope);

      // create artifact to file uri mapping
      for (Entry<DependencyNode, Collection<String>> entry : moduleNodeToClassifiers.entrySet())
      {
         final Artifact artifact = entry.getKey().getDependency().getArtifact();
         final URI artifactURI = toArtifactURI(artifact, null);
         final URI fileURI = URI.createFileURI(artifact.getFile().getAbsolutePath());
         artifactURIs.put(artifactURI, entry.getValue());
         resourceSet.getURIConverter().getURIMap().put(artifactURI, fileURI);
      }
   }

   private static URI toArtifactURI(MavenProject project, String type, String classifier)
   {
      final StringBuilder sb = new StringBuilder();
      sb.append(project.getGroupId());
      sb.append("/");
      sb.append(project.getArtifactId());
      sb.append("/");
      sb.append(type);
      if (classifier != null && classifier.length() > 0)
      {
         sb.append("/");
         sb.append(classifier);
      }
      sb.append("/");
      sb.append(project.getVersion());
      return URI.createURI("gav:/" + sb.toString());
   }

   private static URI toArtifactURI(Artifact artifact, String classifier)
   {
      final StringBuilder sb = new StringBuilder();
      sb.append(artifact.getGroupId());
      sb.append("/");
      sb.append(artifact.getArtifactId());
      sb.append("/");
      sb.append(artifact.getExtension());
      if (classifier != null && classifier.length() > 0)
      {
         sb.append("/");
         sb.append(classifier);
      }
      sb.append("/");
      sb.append(artifact.getBaseVersion());
      return URI.createURI("gav:/" + sb.toString());
   }

   private DependencyResolutionResult resolveModuleDependencies(
      final MultiValueMap<DependencyNode, String> moduleNodeToClassifiers, MavenSession session, MavenProject project,
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

               moduleNodeToClassifiers.get(node, true).add(classifier.length() > 0 ? classifier : null);
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
