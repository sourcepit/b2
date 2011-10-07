/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import org.sourcepit.b2.model.module.PluginProject;


public interface ISourceService
{
   boolean isSourceBuildEnabled(PluginProject pluginProject, IConverter converter);

   boolean hasSource(PluginProject pluginProject);
}