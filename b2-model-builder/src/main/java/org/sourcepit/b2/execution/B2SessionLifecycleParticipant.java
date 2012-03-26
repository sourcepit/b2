/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.execution;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.common.utils.lang.ThrowablePipe;

public interface B2SessionLifecycleParticipant
{
   void prePrepareProjects(B2Session session);

   void prePrepareProject(B2Session session, ModuleProject project, B2Request request);

   void postPrepareProject(B2Session session, ModuleProject project, B2Request request, AbstractModule module,
      ThrowablePipe errors);

   void postPrepareProjects(B2Session session, ThrowablePipe errors);

   void preFinalizeProjects(B2Session session);

   void preFinalizeProject(B2Session session, ModuleProject project);

   void postFinalizeProject(B2Session session, ModuleProject project, ThrowablePipe errors);

   void postFinalizeProjects(B2Session session, ThrowablePipe errors);
}
