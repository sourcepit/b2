/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import org.sourcepit.common.constraints.NotNull;

import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class ProjectDetector
{
   private final List<ProjectDetectionRule<? extends Project>> detectionRules;

   @Inject
   public ProjectDetector(@NotNull List<ProjectDetectionRule<? extends Project>> detectionRules)
   {
      this.detectionRules = detectionRules;
   }

   public Project detect(File directory, PropertiesSource properties)
   {
      for (ProjectDetectionRule<? extends Project> detectionRule : detectionRules)
      {
         final Project project = detectionRule.detect(directory, properties);
         if (project != null)
         {
            if (project.getDirectory() == null)
            {
               project.setDirectory(directory);
            }
            return project;
         }
      }
      return null;
   }
}
