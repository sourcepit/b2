/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.generator;

import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;

public interface B2GeneratorLifecycleParticipant
{
   void preGenerate(AbstractModule module);

   void postGenerate(AbstractModule module, ThrowablePipe errors);
}
