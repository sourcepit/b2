/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.util;

import java.util.List;

import org.sourcepit.b2.model.module.Project;

/**
 * @author Bernd
 */
public final class ProjectUtils
{
   private ProjectUtils()
   {
      super();
   }

   public static <P extends Project> P getProjectById(List<P> projects, String id)
   {
      if (projects == null || id == null)
      {
         return null;
      }
      for (P project : projects)
      {
         if (id != null && id.equals(project.getId()))
         {
            return project;
         }
      }
      return null;
   }
}
