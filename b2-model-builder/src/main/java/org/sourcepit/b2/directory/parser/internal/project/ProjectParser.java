/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
public class ProjectParser
{
   @Inject
   private ProjectDetector detector;

   @Inject
   private ProjectPreprocessor preprocessor;

   @Inject
   private ProjectModelInitializer modelInitializer;

   public Project parse(File directory, PropertiesSource properties)
   {
      final Project project = detector.detect(directory, properties);
      if (project != null)
      {
         preprocessor.preprocess(project, properties);
         modelInitializer.initialize(project, properties);
      }
      return project;
   }
}
