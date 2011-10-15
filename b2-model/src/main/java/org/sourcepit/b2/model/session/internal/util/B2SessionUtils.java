/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session.internal.util;

import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;

public final class B2SessionUtils
{
   private B2SessionUtils()
   {
      super();
   }

   public static ModuleProject getProject(B2Session session, String groupId, String artifactId, String version)
   {
      for (ModuleProject project : session.getProjects())
      {
         if (!groupId.equals(project.getGroupId()))
         {
            continue;
         }

         if (!artifactId.equals(project.getArtifactId()))
         {
            continue;
         }

         if (!version.equals(project.getVersion()))
         {
            continue;
         }

         return project;
      }
      return null;
   }
}
