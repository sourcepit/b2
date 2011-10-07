/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder;

import org.sourcepit.b2.model.module.AbstractModule;


public interface IB2ModelBuilder
{
   AbstractModule build(IB2ModelBuildingRequest request);
}