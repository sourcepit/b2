/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.RepositoryUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.DefaultDependencyResolutionRequest;
import org.apache.maven.project.DependencyResolutionException;
import org.apache.maven.project.DependencyResolutionResult;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectDependenciesResolver;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyFilter;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.filter.AndDependencyFilter;
import org.sonatype.aether.util.filter.ScopeDependencyFilter;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.interpolation.internal.module.ResolutionContextResolver;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.common.utils.collections.LinkedMultiValuHashMap;
import org.sourcepit.common.utils.collections.MultiValueMap;
import org.sourcepit.common.utils.lang.Exceptions;

@Named
public class MavenResolutionContextResolver implements ResolutionContextResolver
{
   @Inject
   private B2SessionService sessionService;

   @Inject
   private LegacySupport buildContext;

   @Inject
   private ProjectDependenciesResolver dependenciesResolver;

   public void determineForeignResolutionContext(MultiValueMap<AbstractModule, String> moduleToAssemblies,
      AbstractModule module, FeatureProject resolutionTarget)
   {
      for (ContextEntry entry : resolveResolutionContext(module, resolutionTarget))
      {
         moduleToAssemblies.get(entry.getModule(), true).addAll(entry.getAssemblyNames());
      }
   }

   public List<ContextEntry> resolveResolutionContext(AbstractModule module, FeatureProject resolutionTarget)
   {
      final String scope = B2MetadataUtils.isTestFeature(resolutionTarget)
         ? Artifact.SCOPE_TEST
         : Artifact.SCOPE_COMPILE;

      final MavenSession session = buildContext.getSession();
      final MavenProject project = session.getCurrentProject();

      assertIsModuleProject(project, module);

      @SuppressWarnings("unchecked")
      List<ContextEntry> context = (List<ContextEntry>) project.getContextValue("b2.resolutionContext.scope=" + scope);
      if (context == null)
      {
         context = resolveResolutionContext(session, project, scope);
         project.setContextValue("b2.resolutionContext.scope=" + scope, Collections.unmodifiableList(context));
      }

      return context;
   }

   private static void assertIsModuleProject(final MavenProject project, AbstractModule module)
   {
      final String projectPath = project.getBasedir().getAbsolutePath();
      final String modelPath = module.getDirectory().getAbsolutePath();
      if (!modelPath.startsWith(projectPath))
      {
         throw new IllegalStateException();
      }
   }

   private List<ContextEntry> resolveResolutionContext(final MavenSession session, final MavenProject project,
      final String scope)
   {
      final Set<Artifact> artifacts = new LinkedHashSet<Artifact>();
      final MultiValueMap<String, String> artifactKeyToClassifiers = new LinkedMultiValuHashMap<String, String>(
         LinkedHashSet.class);

      resolveDependencies(artifacts, artifactKeyToClassifiers, session, project, scope);

      final GAVURIResolver oldResolver = GAVURIResolver.getCurrent();
      try
      {
         GAVURIResolver.setCurrent(new GAVURIResolver(session.getProjects(), artifacts));

         final ResourceSet resourceSet = getResourceSet(project);

         final List<ContextEntry> context = new ArrayList<ContextEntry>();
         for (Entry<String, Collection<String>> entry : artifactKeyToClassifiers.entrySet())
         {
            final URI uri = toGAVURI(entry.getKey());
            Resource resource = resourceSet.getResource(uri, true);
            AbstractModule m = (AbstractModule) resource.getContents().get(0);
            context.add(new ContextEntry(m, entry.getValue()));
         }

         EcoreUtil.resolveAll(resourceSet);

         return context;
      }
      finally
      {
         GAVURIResolver.setCurrent(oldResolver);
      }
   }

   private static ResourceSet getResourceSet(final MavenProject project)
   {
      ResourceSet resourceSet = (ResourceSet) project.getContextValue("b2.resolutionContext.resourceSet");
      if (resourceSet == null)
      {
         resourceSet = createResourceSet();
         project.setContextValue("b2.resolutionContext.resourceSet", resourceSet);
      }
      return resourceSet;
   }


   private static ResourceSet createResourceSet()
   {
      final ResourceSet resourceSet = new ResourceSetImpl();
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("gav", new XMIResourceFactoryImpl());

      final ExtensibleURIConverterImpl uriConverter = (ExtensibleURIConverterImpl) resourceSet.getURIConverter();
      uriConverter.getURIHandlers().add(0, new AbstractDelegatingURIHandler()
      {
         @Override
         protected URI toTargetURI(URI uri)
         {
            if ("gav".equals(uri.scheme()))
            {
               final GAVURIResolver uriResolver = GAVURIResolver.getCurrent();
               if (uriResolver == null)
               {
                  throw new IllegalStateException("Current uri resolver is not set");
               }
               return uriResolver.resolve(uri);
            }
            return null;
         }

         @Override
         protected URIHandler determineTargetURIHandler(URI targetURI)
         {
            return resourceSet.getURIConverter().getURIHandler(targetURI);
         }
      });

      return resourceSet;
   }

   private static URI toGAVURI(String artifactKey)
   {
      return URI.createURI("gav:/" + artifactKey.replace(':', '/'));
   }

   private void resolveDependencies(Set<Artifact> artifacts,
      final MultiValueMap<String, String> artifactKeyToClassifiers, MavenSession session, MavenProject project,
      String scope)
   {
      DependencyFilter collectionFilter = new ScopeDependencyFilter(null, negate(Collections.singleton(scope)));
      DependencyFilter resolutionFilter;// = new ScopeDependencyFilter(null, negate(Collections.singleton(scope)));
      resolutionFilter = AndDependencyFilter.newInstance(collectionFilter, new DependencyFilter()
      {
         public boolean accept(DependencyNode node, List<DependencyNode> parents)
         {
            if (node.getDependency() != null)
            {
               final Dependency dependency = node.getDependency();
               if (dependency.getArtifact() != null)
               {
                  final org.sonatype.aether.artifact.Artifact artifact = dependency.getArtifact();
                  if ("module".equals(artifact.getExtension()))
                  {
                     final String classifier = artifact.getClassifier();

                     final org.sonatype.aether.artifact.Artifact newArtifact;
                     newArtifact = new DefaultArtifact(artifact.getGroupId(), artifact.getArtifactId(), "", artifact
                        .getExtension(), artifact.getVersion(), artifact.getProperties(), artifact.getFile());
                     node.setArtifact(newArtifact);

                     artifactKeyToClassifiers.get(newArtifact.toString(), true).add(
                        classifier.length() > 0 ? classifier : null);
                  }
               }
            }
            return true;
         }
      });


      DefaultDependencyResolutionRequest request = new DefaultDependencyResolutionRequest(project,
         session.getRepositorySession());
      request.setResolutionFilter(resolutionFilter);

      DependencyResolutionResult result;
      try
      {
         result = dependenciesResolver.resolve(request);
      }
      catch (DependencyResolutionException e)
      {
         throw Exceptions.pipe(e);
      }

      if (result.getDependencyGraph() != null && !result.getDependencyGraph().getChildren().isEmpty())
      {
         RepositoryUtils.toArtifacts(artifacts, result.getDependencyGraph().getChildren(),
            Collections.singletonList(project.getArtifact().getId()), collectionFilter);
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


   private static class GAVURIResolver
   {
      private final static ThreadLocal<GAVURIResolver> URI_RESOLVER = new ThreadLocal<GAVURIResolver>();
      private final Collection<MavenProject> knownProjects;
      private final Collection<Artifact> knownArtifacts;

      public GAVURIResolver(Collection<MavenProject> knownProjects, Collection<Artifact> knownArtifacts)
      {
         this.knownProjects = knownProjects;
         this.knownArtifacts = knownArtifacts;
      }

      public static GAVURIResolver getCurrent()
      {
         return URI_RESOLVER.get();
      }

      public static void setCurrent(GAVURIResolver resolver)
      {
         URI_RESOLVER.set(resolver);
      }

      public URI resolve(URI gavURI)
      {
         final String artifactKey = toArtifactKey(gavURI);
         final File file = resolveFile(artifactKey);
         return file == null ? null : URI.createFileURI(file.getAbsolutePath());
      }

      private File resolveFile(final String artifactKey)
      {
         for (MavenProject project : knownProjects)
         {
            final File file = resolveFile(project, artifactKey);
            if (file != null)
            {
               return file;
            }
         }
         for (Artifact artifact : knownArtifacts)
         {
            final File file = resolveFile(artifact, artifactKey);
            if (file != null)
            {
               return file;
            }
         }
         return null;
      }

      private File resolveFile(MavenProject project, final String artifactKey)
      {
         if (isContainedInProject(project, artifactKey))
         {
            // is pom
            if (toArtifactKey(project, null, "pom").equals(artifactKey))
            {
               return project.getFile();
            }

            // is project artifact
            if (project.getArtifact() != null)
            {
               final File file = resolveFile(project.getArtifact(), artifactKey);
               if (file != null)
               {
                  return file;
               }
            }

            // is attached artifact
            for (Artifact artifact : project.getAttachedArtifacts())
            {
               final File file = resolveFile(artifact, artifactKey);
               if (file != null)
               {
                  return file;
               }
            }
         }
         return null;
      }

      private File resolveFile(Artifact artifact, String artifactKey)
      {
         if (artifact.getFile() != null && artifact.getId().equals(artifactKey))
         {
            return artifact.getFile();
         }
         return null;
      }

      private boolean isContainedInProject(MavenProject project, String artifactKey)
      {
         final String projectPrefix = project.getGroupId() + ":" + project.getArtifactId() + ":";
         final String projectSuffix = ":" + project.getVersion();
         return artifactKey.startsWith(projectPrefix) && artifactKey.endsWith(projectSuffix);
      }

      private static String toArtifactKey(MavenProject project, String classifier, String type)
      {
         final StringBuilder sb = new StringBuilder();
         sb.append(project.getGroupId());
         sb.append(":");
         sb.append(project.getArtifactId());
         sb.append(":");
         sb.append(type);
         if (classifier != null)
         {
            sb.append(":");
            sb.append(classifier);
         }
         sb.append(":");
         sb.append(project.getVersion());
         return sb.toString();
      }

      private static String toArtifactKey(URI gavURI)
      {
         final String[] segments = gavURI.segments();
         final boolean hasClassifier = segments.length > 4;

         final StringBuilder sb = new StringBuilder();
         sb.append(segments[0]);
         sb.append(":");
         sb.append(segments[1]);
         sb.append(":");
         sb.append(segments[2]);
         if (hasClassifier)
         {
            sb.append(":");
            sb.append(segments[3]);
         }
         sb.append(":");
         sb.append(segments[hasClassifier ? 4 : 3]);
         return sb.toString();
      }
   }

}
