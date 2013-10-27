/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.directory.parser.internal.project.ProjectDetector;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class PluginFileFlagsProvider extends AbstractFileFlagsProvider implements FileFlagsProvider
{
   private final ProjectDetector projectDetector;

   @Inject
   public PluginFileFlagsProvider(ProjectDetector projectDetector)
   {
      this.projectDetector = projectDetector;
   }

   @Override
   public FileFlagsInvestigator createFileFlagsInvestigator(File moduleDir, final PropertiesSource properties)
   {
      return new FileFlagsInvestigator()
      {
         private final Map<File, Integer> flags = new HashMap<File, Integer>();

         @Override
         public Map<File, Integer> getAdditionallyFoundFileFlags()
         {
            return flags;
         }

         @Override
         public int determineFileFlags(File file)
         {
            if (file.isDirectory() && isPluginProject(file, properties))
            {
               processPluginDir(file);
            }
            return 0;
         }

         private void processPluginDir(File pluginDir)
         {
            final File buildPropsFile = new File(pluginDir, "build.properties");
            if (buildPropsFile.exists())
            {
               final PropertiesMap buildProps = new LinkedPropertiesMap();
               buildProps.load(buildPropsFile);

               for (Entry<String, String> entry : buildProps.entrySet())
               {
                  final String key = entry.getKey();
                  if (key.startsWith("source.") && key.length() > 7)
                  {
                     final String jarName = key.substring(7, key.length());
                     if (!".".equals(jarName))
                     {
                        flags.put(new File(pluginDir, jarName), Integer.valueOf(FLAG_DERIVED));
                     }
                  }
               }
            }
         }

         private boolean isPluginProject(File file, final PropertiesSource properties)
         {
            return projectDetector.detect(file, properties) instanceof PluginProject;
         }
      };
   }
}
