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

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.ClassPathEntry;
import org.sourcepit.modeling.common.Annotation;

@Named
public class DefaultUnpackStrategy implements UnpackStrategy
{
   public boolean isUnpack(PluginProject pluginProject)
   {
      final BundleManifest manifest = pluginProject.getBundleManifest();
      return hasDirBundleShape(manifest) || hasJarOnBundleCP(manifest) || hasJarBinIncludes(pluginProject);
   }

   private boolean hasDirBundleShape(BundleManifest manifest)
   {
      return "dir".equals(manifest.getHeaderValue("Eclipse-BundleShape"));
   }

   private boolean hasJarOnBundleCP(BundleManifest manifest)
   {
      EList<ClassPathEntry> cp = manifest.getBundleClassPath();
      if (cp != null)
      {
         for (ClassPathEntry entry : cp)
         {
            for (String path : entry.getPaths())
            {
               if (path.endsWith(".jar"))
               {
                  return true;
               }
            }
         }
      }
      return false;
   }

   private boolean hasJarBinIncludes(final PluginProject pluginProject)
   {
      final Annotation build = pluginProject.getAnnotation("build");
      if (build != null)
      {
         final String binIncs = build.getData().get("bin.includes");
         if (binIncs != null)
         {
            final String[] split = binIncs.split(",");
            for (String binInclude : split)
            {
               if (binInclude.trim().endsWith(".jar"))
               {
                  return true;
               }
            }
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
         for (Entry<String, String> entry : build.getData())
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
