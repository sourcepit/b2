/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Named;

import org.sourcepit.beef.b2.model.common.Annotation;
import org.sourcepit.beef.b2.model.module.PluginProject;

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
