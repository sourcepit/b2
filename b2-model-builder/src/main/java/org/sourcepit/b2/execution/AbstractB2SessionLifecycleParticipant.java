/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.execution;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.common.utils.lang.ThrowablePipe;

public class AbstractB2SessionLifecycleParticipant implements B2SessionLifecycleParticipant
{
   public void prePrepareProjects(B2Session session)
   {
   }

   public void prePrepareProject(B2Session session, ModuleProject project, B2Request request)
   {
   }

   public void postPrepareProject(B2Session session, ModuleProject project, B2Request request, AbstractModule module,
      ThrowablePipe errors)
   {
   }

   public void postPrepareProjects(B2Session session, ThrowablePipe errors)
   {
   }

   public void preFinalizeProjects(B2Session session)
   {
   }

   public void preFinalizeProject(B2Session session, ModuleProject project)
   {
   }

   public void postFinalizeProject(B2Session session, ModuleProject project, ThrowablePipe errors)
   {
   }

   public void postFinalizeProjects(B2Session session, ThrowablePipe errors)
   {
   }
}
