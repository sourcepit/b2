/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sourcepit.b2.model.module.PluginProject;

@Named
@Singleton
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
