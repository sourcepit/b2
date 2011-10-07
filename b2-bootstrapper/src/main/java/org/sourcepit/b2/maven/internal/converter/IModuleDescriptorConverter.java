/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.maven.internal.converter;

import java.io.File;
import java.io.IOException;

import org.apache.maven.model.Model;

public interface IModuleDescriptorConverter
{
   Model convert(File moduleDescriptor) throws IOException;
}
