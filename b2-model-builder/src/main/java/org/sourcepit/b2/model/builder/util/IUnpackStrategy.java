/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import java.util.List;

import org.sourcepit.b2.model.module.PluginProject;


public interface IUnpackStrategy
{
   boolean isUnpack(PluginProject pluginProject);

   List<String> getBuildJars(PluginProject pluginProject);
}