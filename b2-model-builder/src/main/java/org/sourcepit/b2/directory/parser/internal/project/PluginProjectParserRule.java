/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import static java.util.jar.JarFile.MANIFEST_NAME;
import static org.sourcepit.b2.directory.parser.internal.project.ProjectResourcesUtils.getResourcesDir;

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
      File manifestFile = new File(getResourcesDir(directory, properties), MANIFEST_NAME);
      if (!manifestFile.exists())
      {
         manifestFile = new File(directory, MANIFEST_NAME);
      }

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
