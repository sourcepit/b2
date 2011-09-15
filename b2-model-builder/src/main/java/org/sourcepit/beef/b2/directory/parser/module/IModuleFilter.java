/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.directory.parser.module;

import java.io.File;

public interface IModuleFilter
{
   boolean accept(File potentialModuleDir);
}
