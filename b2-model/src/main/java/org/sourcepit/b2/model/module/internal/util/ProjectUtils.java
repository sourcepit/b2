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
