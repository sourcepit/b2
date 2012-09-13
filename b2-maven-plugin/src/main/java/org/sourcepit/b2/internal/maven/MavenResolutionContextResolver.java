/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectDependenciesResolver;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.interpolation.internal.module.ResolutionContextResolver;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.common.utils.collections.MultiValueMap;

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
         final B2Project b2Project = B2ProjectAdapterFactory.getB2Project(project);

         final Map<URI, Collection<String>> dependencies;
         if (B2MetadataUtils.isTestFeature(resolutionTarget))
         {
            dependencies = b2Project.getTestDependencies();
         }
         else
         {
            dependencies = b2Project.getMainDependencies();
         }

         context = new ArrayList<ContextEntry>();

         final ResourceSet resourceSet = b2Project.getResourceSet();
         for (Entry<URI, Collection<String>> entry : dependencies.entrySet())
         {
            final URI uri = entry.getKey();
            AbstractModule eObject = (AbstractModule) resourceSet.getResource(uri, true).getContents().get(0);
            context.add(new ContextEntry(eObject, entry.getValue()));
         }

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

}
