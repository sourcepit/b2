/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.sourcepit.common.utils.io.IOResources.buffIn;
import static org.sourcepit.common.utils.io.IOResources.fileIn;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.manifest.Manifest;
import org.sourcepit.common.manifest.osgi.resource.GenericManifestResourceImpl;
import org.sourcepit.common.utils.io.IOOperation;

public final class B2ModelUtils
{
   private B2ModelUtils()
   {
      super();
   }

   public static FeatureInclude toFeatureInclude(FeatureProject featureProject)
   {
      FeatureInclude featureInclude = ModuleModelFactory.eINSTANCE.createFeatureInclude();
      featureInclude.setId(featureProject.getId());
      featureInclude.setVersion(featureProject.getVersion());

      Annotation b2Metadata = B2MetadataUtils.getB2Metadata(featureProject);
      if (b2Metadata != null)
      {
         featureInclude.getAnnotations().add(EcoreUtil.copy(b2Metadata));
      }

      return featureInclude;
   }

   public static PluginInclude toPluginInclude(PluginProject pluginProject)
   {
      final AbstractModule module =  pluginProject.getParent().getParent();
      return toPluginInclude(module, pluginProject);
   }

   public static PluginInclude toPluginInclude(AbstractModule module, PluginProject pluginProject)
   {
      PluginInclude featureInclude = ModuleModelFactory.eINSTANCE.createPluginInclude();
      featureInclude.setId(pluginProject.getId());
      featureInclude.setVersion(pluginProject.getVersion());

      Annotation b2Metadata = B2MetadataUtils.getB2Metadata(pluginProject);
      if (b2Metadata != null)
      {
         featureInclude.getAnnotations().add(EcoreUtil.copy(b2Metadata));
      }

      B2MetadataUtils.setModuleId(featureInclude, module.getId());
      B2MetadataUtils.setModuleVersion(featureInclude, module.getVersion());
      B2MetadataUtils.setTestPlugin(featureInclude, pluginProject.isTestPlugin());
      
      PluginsFacet facet = pluginProject.getParent();
      B2MetadataUtils.setFacetName(featureInclude, facet.getName());
      
      return featureInclude;
   }
   
   public static Manifest readManifest(File file)
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
