/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import javax.inject.Named;

import org.sourcepit.b2.model.module.PluginProject;

@Named
public class SourceService implements ISourceService
{
   /**
    * {@inheritDoc}
    */
   public boolean isSourceBuildEnabled(PluginProject pluginProject, IConverter converter)
   {
      return Boolean.valueOf(converter.getProperties().get("build.sources", "true")).booleanValue()
         && hasSource(pluginProject);
   }

   /**
    * {@inheritDoc}
    */
   public boolean hasSource(PluginProject pluginProject)
   {
      return pluginProject.getAnnotationEntry("java", "source.paths") != null;
   }
}
