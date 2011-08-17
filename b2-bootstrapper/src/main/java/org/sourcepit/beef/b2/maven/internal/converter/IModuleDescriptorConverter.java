/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.maven.internal.converter;

import java.io.File;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.ModelParseException;

public interface IModuleDescriptorConverter
{
   Model convert(File moduleDescriptor) throws IOException, ModelParseException;
}
