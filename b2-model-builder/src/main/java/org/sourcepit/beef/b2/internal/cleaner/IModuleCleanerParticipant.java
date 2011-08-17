/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.cleaner;

import java.io.File;

public interface IModuleCleanerParticipant
{
   boolean isGarbage(File file);
}
