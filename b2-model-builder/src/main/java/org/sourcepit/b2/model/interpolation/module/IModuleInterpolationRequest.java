/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.b2.model.interpolation.module;

import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.builder.util.IModelCache;
import org.sourcepit.b2.model.module.AbstractModule;


/**
 * @author Bernd
 */
public interface IModuleInterpolationRequest
{
   AbstractModule getModule();

   IConverter getConverter();

   IModelCache getModelCache();
}