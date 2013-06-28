/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import static org.sourcepit.common.utils.lang.Exceptions.pipe;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.utils.lang.PipedError;
import org.sourcepit.common.utils.props.PropertiesSource;


/**
 * @author Bernd
 */
@Named
public class ProjectParser
{
   @Inject
   private List<AbstractProjectParserRule<? extends Project>> rules;

   private static Method initialize = getMethod();

   private static Method getMethod()
   {
      try
      {
         return AbstractProjectParserRule.class.getMethod("initialize", Project.class, PropertiesSource.class);
      }
      catch (NoSuchMethodException e)
      {
         throw pipe(e);
      }
   }

   public Project parse(File directory, PropertiesSource properties)
   {
      for (AbstractProjectParserRule<? extends Project> rule : rules)
      {
         final Project project = rule.parse(directory, properties);
         if (project != null)
         {
            initialize(rule, project, properties);
            return project;
         }
      }
      return null;
   }

   private static void initialize(AbstractProjectParserRule<? extends Project> rule, final Project project,
      PropertiesSource properties) throws PipedError
   {
      try
      {
         initialize.invoke(rule, project, properties);
      }
      catch (InvocationTargetException e)
      {
         Throwable cause = e.getTargetException();
         if (cause instanceof Exception)
         {
            throw pipe((Exception) cause);
         }
         else
         {
            throw pipe((Error) cause);
         }
      }
      catch (Exception e)
      {
         throw pipe(e);
      }
   }
}
