/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.module;

import java.io.File;

import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.builder.util.IModelCache;

public interface IModuleParsingRequest
{
   File getModuleDirectory();

   IConverter getConverter();

   IModelCache getModelCache();

   IModuleFilter getModuleFilter();
}
