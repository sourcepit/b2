/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.execution;

import java.io.File;
import java.util.List;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;

public interface B2SessionLifecycleParticipant
{
   void prePrepareProjects(List<File> projectDirs);

   void prePrepareProject(File projectDir, B2Request request);

   void postPrepareProject(File projectDir, B2Request request, AbstractModule module, ThrowablePipe errors);

   void postPrepareProjects(List<File> projectDirs, ThrowablePipe errors);

   void preFinalizeProjects(List<File> projectDirs);

   void preFinalizeProject(File projectDir);

   void postFinalizeProject(File projectDir, ThrowablePipe errors);

   void postFinalizeProjects(List<File> projectDirs, ThrowablePipe errors);
}
