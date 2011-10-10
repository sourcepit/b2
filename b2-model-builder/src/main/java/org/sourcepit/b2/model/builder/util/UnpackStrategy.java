/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Named;

import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.module.PluginProject;

@Named
public class UnpackStrategy implements IUnpackStrategy
{
   public boolean isUnpack(PluginProject pluginProject)
   {
      final Annotation build = pluginProject.getAnnotation("build");
      if (build != null)
      {
         final String binIncs = build.getEntries().get("bin.includes");
         if (binIncs != null)
         {
            final String[] split = binIncs.split(",");
            for (String binInclude : split)
            {
               if (".".equals(binInclude.trim()))
               {
                  return false;
               }
            }
            return true;
         }
      }
      return false;
   }

   public List<String> getBuildJars(PluginProject pluginProject)
   {
      final List<String> buildJars = new ArrayList<String>();
      final Annotation build = pluginProject.getAnnotation("build");
      if (build != null)
      {
         for (Entry<String, String> entry : build.getEntries())
         {
            String key = entry.getKey();
            if (key.startsWith("source.") && key.endsWith(".jar"))
            {
               buildJars.add(key.substring("source.".length()));
            }
         }
      }
      return buildJars;
   }
}
