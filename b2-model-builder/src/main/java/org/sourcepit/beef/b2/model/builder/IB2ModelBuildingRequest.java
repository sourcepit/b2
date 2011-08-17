/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder;

import org.sourcepit.beef.b2.directory.parser.module.IModuleParsingRequest;


public interface IB2ModelBuildingRequest extends IModuleParsingRequest
{
   boolean isInterpolate();
}