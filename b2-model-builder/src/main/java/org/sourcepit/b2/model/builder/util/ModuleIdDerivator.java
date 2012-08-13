/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;

public interface ModuleIdDerivator
{
   String deriveModuleId(File moduleDir);
}
