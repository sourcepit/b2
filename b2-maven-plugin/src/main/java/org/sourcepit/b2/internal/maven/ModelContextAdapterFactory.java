/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sourcepit.b2.maven.core.B2MavenBridge;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.common.maven.model.MavenArtifact;

import com.google.common.base.Optional;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

@Named
public class ModelContextAdapterFactory
{
   private final ModuleArtifactResolver artifactResolver;

   @Inject
   public ModelContextAdapterFactory(ModuleArtifactResolver artifactResolver)
   {
      this.artifactResolver = artifactResolver;
   }

   public ModelContext adapt(MavenSession session, MavenProject project)
   {
      synchronized (project)
      {
         ModelContext context = (ModelContext) project.getContextValue(ModelContext.class.getName());
         if (context == null)
         {
            context = createModuleModelContext(session, project);
            project.setContextValue(ModelContext.class.getName(), context);
         }
         return context;
      }
   }

   public static ModelContext get(MavenProject project)
   {
      synchronized (project)
      {
         return (ModelContext) project.getContextValue(ModelContext.class.getName());
      }
   }

   private ModelContext createModuleModelContext(MavenSession session, MavenProject project)
   {
      final ResourceSet resourceSet = createResourceSet();

      final SetMultimap<URI, String> scopeTest = LinkedHashMultimap.create();
      initURIMapping(resourceSet, scopeTest, session, project, "test");

      final SetMultimap<URI, String> scopeCompile = LinkedHashMultimap.create();
      initURIMapping(resourceSet, scopeCompile, session, project, "compile");

      initURIMapping(resourceSet, session, project);

      final URI artifactURI = B2MavenBridge.toArtifactURI(project, "module", null);
      final URI fileURI = URI.createFileURI(pathOfMetaDataFile(project.getBasedir(), "b2.module"));
      resourceSet.getURIConverter().getURIMap().put(artifactURI, fileURI);

      return new ModelContext(resourceSet, artifactURI.appendFragment("/"), resolve(resourceSet, scopeCompile),
         resolve(resourceSet, scopeTest));
   }

   private void initURIMapping(ResourceSet resourceSet, MavenSession session, MavenProject currentProject)
   {
      final Map<URI, URI> uriMap = resourceSet.getURIConverter().getURIMap();

      final List<MavenProject> projects = session.getProjects();
      for (int i = 0; i < projects.indexOf(currentProject); i++)
      {
         final ModelContext foreignModelContext = ModelContextAdapterFactory.get(projects.get(i));
         if (foreignModelContext != null)
         {
            final Map<URI, URI> foreignUriMap = foreignModelContext.getResourceSet().getURIConverter().getURIMap();
            for (URI uri : foreignUriMap.keySet())
            {
               if (!uriMap.containsKey(uri))
               {
                  uriMap.put(uri, foreignUriMap.get(uri));
               }
            }
         }
      }
   }

   private static SetMultimap<AbstractModule, FeatureProject> resolve(ResourceSet resourceSet,
      SetMultimap<URI, String> unresolved)
   {
      final SetMultimap<AbstractModule, FeatureProject> resolved = LinkedHashMultimap.create(unresolved.size(), 4);
      for (Entry<URI, Collection<String>> entry : unresolved.asMap().entrySet())
      {
         final URI uri = entry.getKey();
         final AbstractModule module = (AbstractModule) resourceSet.getResource(uri, true).getContents().get(0);

         for (String classifier : entry.getValue())
         {
            final Optional<FeatureProject> result = findAssemblyFeatureForClassifier(module, classifier);
            if (!result.isPresent())
            {
               throw new IllegalStateException("Cannot determine assembly feature for classifier '" + classifier + "'");
            }
            resolved.get(module).add(result.get());
         }
      }
      return resolved;
   }

   private static Optional<FeatureProject> findAssemblyFeatureForClassifier(final AbstractModule module,
      String classifier)
   {
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         for (FeatureProject featureProject : featuresFacet.getProjects())
         {
            final int idx = B2MetadataUtils.getAssemblyClassifiers(featureProject).indexOf(classifier);
            if (idx > -1)
            {
               return Optional.of(featureProject);
            }
         }
      }
      return Optional.absent();
   }

   // TODO use interpolation layout
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
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("file", new XMIResourceFactoryImpl());
      return resourceSet;
   }

   private void initURIMapping(ResourceSet resourceSet, SetMultimap<URI, String> artifactURIs, MavenSession session,
      MavenProject project, String scope)
   {
      // resolve module artifacts
      final SetMultimap<MavenArtifact, String> moduleArtifactToClassifiers = artifactResolver.resolve(session, project,
         scope);

      // create artifact to file uri mapping
      for (Entry<MavenArtifact, Collection<String>> entry : moduleArtifactToClassifiers.asMap().entrySet())
      {
         final MavenArtifact artifact = entry.getKey();
         final URI artifactURI = toArtifactURI(artifact, null);
         final URI fileURI = URI.createFileURI(artifact.getFile().getAbsolutePath());
         artifactURIs.get(artifactURI).addAll(entry.getValue());
         resourceSet.getURIConverter().getURIMap().put(artifactURI, fileURI);
      }
   }

   private static URI toArtifactURI(MavenArtifact artifact, String classifier)
   {
      final StringBuilder sb = new StringBuilder();
      sb.append(artifact.getGroupId());
      sb.append("/");
      sb.append(artifact.getArtifactId());
      sb.append("/");
      sb.append(artifact.getType());
      if (classifier != null && classifier.length() > 0)
      {
         sb.append("/");
         sb.append(classifier);
      }
      sb.append("/");
      sb.append(artifact.getVersion());
      return URI.createURI("gav:/" + sb.toString());
   }

}
