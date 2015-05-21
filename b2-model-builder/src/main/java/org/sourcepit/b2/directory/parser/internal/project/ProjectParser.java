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

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.utils.props.PropertiesSource;


/**
 * @author Bernd
 */
@Named
public class ProjectParser {
   @Inject
   private ProjectDetector detector;

   @Inject
   private ProjectPreprocessor preprocessor;

   @Inject
   private ProjectModelInitializer modelInitializer;

   public Project parse(File directory, PropertiesSource properties) {
      final Project project = detector.detect(directory, properties);
      if (project != null) {
         preprocessor.preprocess(project, properties);
         modelInitializer.initialize(project, properties);
      }
      return project;
   }
}
