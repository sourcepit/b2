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

package org.sourcepit.b2.directory.parser.internal.project;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.constraints.NotNull;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class ProjectPreprocessor {
   private final List<ProjectPreprocessingParticipant> participants;

   @Inject
   public ProjectPreprocessor(@NotNull List<ProjectPreprocessingParticipant> participants) {
      this.participants = participants;
   }

   public void preprocess(Project project, PropertiesSource properties) {
      for (ProjectPreprocessingParticipant participant : participants) {
         participant.preprocess(project, properties);
      }
   }
}
