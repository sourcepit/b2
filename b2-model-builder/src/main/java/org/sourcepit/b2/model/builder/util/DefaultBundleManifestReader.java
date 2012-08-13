/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;
import java.io.IOException;

import javax.inject.Named;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;

@Named
public class DefaultBundleManifestReader implements BundleManifestReader
{
   public BundleManifest readManifest(PluginProject pluginProject)
   {
      File manifestFile = new File(pluginProject.getDirectory(), "META-INF/MANIFEST.MF");

      URI uri = URI.createFileURI(manifestFile.getAbsolutePath());

      Resource resource = new BundleManifestResourceImpl();
      resource.setURI(uri);
      try
      {
         resource.load(null);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      return (BundleManifest) resource.getContents().get(0);
   }
}
