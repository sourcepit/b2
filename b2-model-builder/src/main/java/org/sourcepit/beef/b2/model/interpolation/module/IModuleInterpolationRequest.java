/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.model.interpolation.module;

import org.sourcepit.beef.b2.internal.model.AbstractModule;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.builder.util.IModelCache;


/**
 * @author Bernd
 */
public interface IModuleInterpolationRequest
{
   AbstractModule getModule();

   IConverter getConverter();

   IModelCache getModelCache();
}