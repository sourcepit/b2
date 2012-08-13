/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.cleaner;

import java.io.File;

import org.sourcepit.common.utils.lang.ThrowablePipe;

public interface ModuleCleanerLifecycleParticipant
{
   void preClean(File moduleDir);

   void postClean(File moduleDir, ThrowablePipe errors);
}
