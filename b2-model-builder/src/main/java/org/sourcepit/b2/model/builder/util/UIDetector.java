/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.PropertiesSource;

public interface UIDetector
{
   boolean requiresUI(PluginProject pluginProject, PropertiesSource properties);
}
