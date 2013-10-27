/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.PropertiesSource;


public interface ISourceService
{
   boolean isSourceBuildEnabled(PropertiesSource moduleProperties);

   boolean isSourceBuildEnabled(PluginProject pluginProject, PropertiesSource moduleProperties);

   boolean hasSource(PluginProject pluginProject);
}
