/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.module;

import org.sourcepit.beef.b2.internal.model.AbstractModule;


/**
 * IModuleParser
 * 
 * @author Bernd
 */
public interface IModuleParser
{

   public abstract AbstractModule parse(IModuleParsingRequest request);

}