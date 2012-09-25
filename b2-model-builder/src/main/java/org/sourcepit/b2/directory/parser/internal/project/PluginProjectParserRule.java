/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import static org.sourcepit.common.utils.io.IOResources.buffIn;
import static org.sourcepit.common.utils.io.IOResources.fileIn;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Named;

import org.eclipse.emf.ecore.resource.Resource;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.Manifest;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.FragmentHost;
import org.sourcepit.common.manifest.osgi.VersionRange;
import org.sourcepit.common.manifest.osgi.resource.GenericManifestResourceImpl;
import org.sourcepit.common.utils.io.IOOperation;
import org.sourcepit.common.utils.props.PropertiesSource;

/**
 * @author Bernd
 */
@Named("plugin")
public class PluginProjectParserRule extends AbstractProjectParserRule<PluginProject>
{
   @Override
   public PluginProject parse(File directory, PropertiesSource properties)
   {
      final File manifestFile = new File(directory, "META-INF/MANIFEST.MF");
      if (!manifestFile.exists())
      {
         return null;
      }

      final Manifest manifest = readManifest(manifestFile);
      if (manifest instanceof BundleManifest)
      {
         final BundleManifest bundleManifest = (BundleManifest) manifest;

         final PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
         project.setId(bundleManifest.getBundleSymbolicName().getSymbolicName());
         project.setVersion(bundleManifest.getBundleVersion().toString());
         project.setBundleManifest(bundleManifest);
         project.setDirectory(directory);
         
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
         return project;
      }
      return null;
   }

   private Manifest readManifest(File file)
   {
      final Resource eResource = new GenericManifestResourceImpl();
      new IOOperation<InputStream>(buffIn(fileIn(file)))
      {
         @Override
         protected void run(InputStream openResource) throws IOException
         {
            eResource.load(openResource, null);
         }
      }.run();

      final Manifest bundleManifest = (Manifest) eResource.getContents().get(0);
      eResource.getContents().clear(); // disconnect from resource
      return bundleManifest;
   }
}
