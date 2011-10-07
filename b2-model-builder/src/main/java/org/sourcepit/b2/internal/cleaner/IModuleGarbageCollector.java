/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.cleaner;

import java.io.File;

public interface IModuleGarbageCollector
{
   boolean isGarbage(File file);
}
