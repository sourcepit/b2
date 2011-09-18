/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.execution;

import org.sourcepit.beef.b2.model.module.AbstractModule;

/**
 * @author Bernd
 */
public interface IB2Listener
{
   void startGeneration(AbstractModule module);
}
