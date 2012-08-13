/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import org.sourcepit.b2.model.module.PluginProject;

public interface UIDetector
{
   boolean requiresUI(PluginProject pluginProject, IConverter converter);
}
