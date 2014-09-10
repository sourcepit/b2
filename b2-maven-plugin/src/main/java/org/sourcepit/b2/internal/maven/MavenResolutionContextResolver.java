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
