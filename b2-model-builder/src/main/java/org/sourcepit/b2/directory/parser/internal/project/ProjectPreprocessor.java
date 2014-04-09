/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import org.sourcepit.common.constraints.NotNull;

import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class ProjectPreprocessor
{
   private final List<ProjectPreprocessingParticipant> participants;

   @Inject
   public ProjectPreprocessor(@NotNull List<ProjectPreprocessingParticipant> participants)
   {
      this.participants = participants;
   }

   public void preprocess(Project project, PropertiesSource properties)
   {
      for (ProjectPreprocessingParticipant participant : participants)
      {
         participant.preprocess(project, properties);
      }
   }
}
