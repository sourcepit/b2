/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.directory.parser.internal.project;

import static java.util.jar.JarFile.MANIFEST_NAME;

import java.io.File;

import javax.inject.Named;

import org.sourcepit.b2.model.interpolation.internal.module.B2ModelUtils;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.manifest.Manifest;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.FragmentHost;
import org.sourcepit.common.manifest.osgi.VersionRange;
import org.sourcepit.common.utils.props.PropertiesSource;

/**
 * @author Bernd
 */
@Named("plugin")
public class PluginProjectParserRule extends AbstractProjectParserRule<PluginProject>
   implements
      ProjectDetectionRule<PluginProject>,
      ProjectModelInitializationParticipant
{


   @Override
   public PluginProject detect(File directory, PropertiesSource properties)
   {
      return parse(directory, properties);
   }

   @Override
   public PluginProject parse(File directory, PropertiesSource properties)
   {
      final File manifestFile = new File(directory, MANIFEST_NAME);
      if (!manifestFile.exists())
      {
         return null;
      }

      final Manifest manifest = B2ModelUtils.readManifest(manifestFile, false);
      if (manifest instanceof BundleManifest)
      {
         final PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
         project.setDirectory(directory);
         return project;
      }
      return null;
   }

   @Override
   public void initialize(Project project, PropertiesSource properties)
   {
      if (project instanceof PluginProject)
      {
         initializeeee((PluginProject) project, properties);
      }
   }

   @Override
   public void initializeeee(PluginProject project, PropertiesSource properties)
   {
      final File projectDir = project.getDirectory();

      final File manifestFile = new File(projectDir, "META-INF/MANIFEST.MF");

      final BundleManifest bundleManifest = (BundleManifest) B2ModelUtils.readManifest(manifestFile, true);

      project.setId(bundleManifest.getBundleSymbolicName().getSymbolicName());
      project.setVersion(bundleManifest.getBundleVersion().toString());
      project.setBundleManifest(bundleManifest);

      // TODO replace with configurable filter pattern
      project.setTestPlugin(project.getId().endsWith(".tests"));

      final FragmentHost fragmentHost = bundleManifest.getFragmentHost();
      if (fragmentHost != null)
      {
         project.setFragmentHostSymbolicName(fragmentHost.getSymbolicName());
         final VersionRange hostVersion = fragmentHost.getBundleVersion();
         if (hostVersion != null)
         {
            project.setFragmentHostVersion(hostVersion.toString());
         }
      }
   }
}
