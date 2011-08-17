/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import org.sourcepit.beef.b2.internal.model.AbstractModule;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.builder.util.IModelCache;


public interface IB2GenerationRequest
{
   AbstractModule getModule();

   IModelCache getModelCache();

   IConverter getConverter();

   ITemplates getTemplates();
}