/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.Project;


/**
 * @author Bernd
 */
@Named
public class ProjectParser
{
   @Inject
   private List<AbstractProjectParserRule<? extends Project>> rules;

   public Project parse(File directory, IConverter converter)
   {
      for (AbstractProjectParserRule<? extends Project> rule : rules)
      {
         final Project project = rule.parse(directory, converter);
         if (project != null)
         {
            return project;
         }
      }
      return null;
   }
}
