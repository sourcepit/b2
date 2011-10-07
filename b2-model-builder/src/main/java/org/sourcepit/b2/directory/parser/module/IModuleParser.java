/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.b2.directory.parser.module;

import org.sourcepit.b2.model.module.AbstractModule;


/**
 * @author Bernd
 */
public interface IModuleParser
{
   AbstractModule parse(IModuleParsingRequest request);
}