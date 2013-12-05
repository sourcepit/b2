/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven.util;

import static org.apache.maven.model.Plugin.constructKey;

import org.apache.maven.model.Build;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;

public final class MavenModelUtils
{
   private MavenModelUtils()
   {
      super();
   }

   public static PluginExecution getPluginExecution(Plugin plugin, boolean createOnDemand, final String id)
   {
      PluginExecution pluginExecution = plugin.getExecutionsAsMap().get(id);
      if (pluginExecution == null && createOnDemand)
      {
         pluginExecution = new PluginExecution();
         pluginExecution.setId(id);
         plugin.getExecutions().add(pluginExecution);
         plugin.flushExecutionMap();
      }
      return pluginExecution;
   }

   public static Plugin getPlugin(Build build, boolean createOnDemand, final String groupId, final String artifactId,
      final String version)
   {
      Plugin plugin = build.getPluginsAsMap().get(constructKey(groupId, artifactId));
      if (plugin == null && createOnDemand)
      {
         plugin = new Plugin();
         plugin.setGroupId(groupId);
         plugin.setArtifactId(artifactId);
         plugin.setVersion(version);
         build.getPlugins().add(plugin);
         build.flushPluginMap();
      }
      return plugin;
   }

}
