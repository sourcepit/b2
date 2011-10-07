/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;

import org.sourcepit.b2.model.module.AbstractModule;


/**
 * @author Bernd
 */
public interface IModelCache
{
   AbstractModule get(File moduleDir);
}