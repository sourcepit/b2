/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import static java.lang.Integer.valueOf;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_HIDDEN;
import static org.sourcepit.common.utils.file.FileUtils.isParentOf;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.sourcepit.b2.files.FileFlagsProvider;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class NestedModuleDirsFileFlagsProvider implements FileFlagsProvider
{
   private final LegacySupport buildContext;

   @Inject
   public NestedModuleDirsFileFlagsProvider(LegacySupport buildContext)
   {
      this.buildContext = buildContext;
   }

   @Override
   public Map<File, Integer> getFileFlags(File moduleDir, PropertiesSource properties)
   {
      final Map<File, Integer> fileToFlagsMap = new HashMap<File, Integer>();
      final MavenSession session = buildContext.getSession();
      for (MavenProject project : session.getProjects())
      {
         final File projectDir = project.getBasedir();
         if (isParentOf(moduleDir, projectDir))
         {
            fileToFlagsMap.put(projectDir, valueOf(FLAG_FORBIDDEN | FLAG_HIDDEN));
         }
      }
      return fileToFlagsMap;
   }

}
