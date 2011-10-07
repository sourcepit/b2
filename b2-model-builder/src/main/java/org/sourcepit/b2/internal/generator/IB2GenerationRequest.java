/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.generator;

import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.builder.util.IModelCache;
import org.sourcepit.b2.model.module.AbstractModule;


public interface IB2GenerationRequest
{
   AbstractModule getModule();

   IModelCache getModelCache();

   IConverter getConverter();

   ITemplates getTemplates();
}