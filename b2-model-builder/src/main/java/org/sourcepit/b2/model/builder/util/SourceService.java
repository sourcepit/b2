/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
@Singleton
public class SourceService implements ISourceService
{
   public boolean isSourceBuildEnabled(PluginProject pluginProject, PropertiesSource buildProperties)
   {
      return isSourceBuildEnabled(buildProperties) && hasSource(pluginProject);
   }

   public boolean isSourceBuildEnabled(PropertiesSource buildProperties)
   {
      return Boolean.valueOf(buildProperties.get("build.sources", "true")).booleanValue();
   }

   public boolean hasSource(PluginProject pluginProject)
   {
      return pluginProject.getAnnotationEntry("java", "source.paths") != null;
   }
}
