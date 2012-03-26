/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.interpolation.module;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;

public interface ModuleInterpolatorLifecycleParticipant
{
   void preInterpolation(AbstractModule module);

   void postInterpolation(AbstractModule module, ThrowablePipe errors);
}
