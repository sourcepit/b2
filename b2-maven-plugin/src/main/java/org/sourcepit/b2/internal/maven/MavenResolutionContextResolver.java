/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.sourcepit.b2.model.interpolation.internal.module.ResolutionContextResolver;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;

import com.google.common.collect.SetMultimap;

@Named
public class MavenResolutionContextResolver implements ResolutionContextResolver
{
   @Inject
   private LegacySupport buildContext;

   public SetMultimap<AbstractModule, FeatureProject> resolveResolutionContext(AbstractModule module, boolean scopeTest)
   {
      final MavenSession session = buildContext.getSession();
      final MavenProject project = session.getCurrentProject();

      assertIsModuleProject(project, module);

      final ModelContext modelContext = ModelContextAdapterFactory.get(project);
      if (scopeTest)
      {
         return modelContext.getTestScope();
      }
      else
      {
         return modelContext.getMainScope();
      }
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
