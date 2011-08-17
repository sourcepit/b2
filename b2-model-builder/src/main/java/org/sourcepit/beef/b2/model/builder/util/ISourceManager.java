/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder.util;

import org.sourcepit.beef.b2.internal.model.PluginProject;


public interface ISourceManager
{
   boolean isSourceBuildEnabled(PluginProject pluginProject, IConverter converter);

   boolean hasSource(PluginProject pluginProject);
}