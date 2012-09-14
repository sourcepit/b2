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
import org.sourcepit.common.utils.lang.Exceptions;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

@Named
public class ModuleModelContextAdapterFactory
{
   @Inject
   private LayoutManager layoutManager;

   @Inject
   private ProjectDependenciesResolver dependenciesResolver;

   public ModuleModelContext adapt(MavenSession session, MavenProject project)
   {
      synchronized (project)
      {
         ModuleModelContext context = (ModuleModelContext) project.getContextValue(ModuleModelContext.class.getName());
         if (context == null)
         {
            context = createModuleModelContext(session, project);
            project.setContextValue(ModuleModelContext.class.getName(), context);
         }
         return context;
      }
   }

   public static ModuleModelContext get(MavenProject project)
   {
      synchronized (project)
      {
         return (ModuleModelContext) project.getContextValue(ModuleModelContext.class.getName());
      }
   }

   private ModuleModelContext createModuleModelContext(MavenSession session, MavenProject project)
   {
      final ResourceSet resourceSet = createResourceSet();

      final SetMultimap<URI, String> scopeTest = LinkedHashMultimap.create();
      initURIMapping(scopeTest, session, project, resourceSet, "test");

      final SetMultimap<URI, String> scopeCompile = LinkedHashMultimap.create();
      initURIMapping(scopeCompile, session, project, resourceSet, "compile");

      final URI artifactURI = toArtifactURI(project, "module", null);
      final URI fileURI = URI.createFileURI(pathOfMetaDataFile(project.getBasedir(), "b2.module"));
      resourceSet.getURIConverter().getURIMap().put(artifactURI, fileURI);

      return new ModuleModelContext(resourceSet, artifactURI, scopeCompile, scopeTest);
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

   private void initURIMapping(SetMultimap<URI, String> artifactURIs, MavenSession session, MavenProject project,
      ResourceSet resourceSet, String scope)
   {
      // resolve module artifacts
      final SetMultimap<DependencyNode, String> moduleNodeToClassifiers = LinkedHashMultimap.create();
      resolveModuleDependencies(moduleNodeToClassifiers, session, project, scope);

      // create artifact to file uri mapping
      for (Entry<DependencyNode, Collection<String>> entry : moduleNodeToClassifiers.asMap().entrySet())
      {
         final Artifact artifact = entry.getKey().getDependency().getArtifact();
         final URI artifactURI = toArtifactURI(artifact, null);
         final URI fileURI = URI.createFileURI(artifact.getFile().getAbsolutePath());
         artifactURIs.get(artifactURI).addAll(entry.getValue());
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

               moduleNodeToClassifiers.get(node).add(classifier.length() > 0 ? classifier : null);
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
