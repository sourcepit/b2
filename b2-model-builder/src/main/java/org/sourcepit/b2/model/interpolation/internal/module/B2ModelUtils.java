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

package org.sourcepit.b2.model.interpolation.internal.module;

import static org.sourcepit.common.utils.io.IO.buffIn;
import static org.sourcepit.common.utils.io.IO.fileIn;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.manifest.Manifest;
import org.sourcepit.common.manifest.osgi.resource.GenericManifestResourceImpl;
import org.sourcepit.common.modeling.Annotation;
import org.sourcepit.common.utils.io.IOOperation;

public final class B2ModelUtils {
   private B2ModelUtils() {
      super();
   }

   public static FeatureInclude toFeatureInclude(FeatureProject featureProject) {
      FeatureInclude featureInclude = ModuleModelFactory.eINSTANCE.createFeatureInclude();
      featureInclude.setId(featureProject.getId());
      featureInclude.setVersion(featureProject.getVersion());

      Annotation b2Metadata = B2MetadataUtils.getB2Metadata(featureProject);
      if (b2Metadata != null) {
         featureInclude.getAnnotations().add(EcoreUtil.copy(b2Metadata));
      }

      return featureInclude;
   }

   public static PluginInclude toPluginInclude(PluginProject pluginProject) {
      final AbstractModule module = pluginProject.getParent().getParent();
      return toPluginInclude(module, pluginProject);
   }

   public static PluginInclude toPluginInclude(AbstractModule module, PluginProject pluginProject) {
      PluginInclude featureInclude = ModuleModelFactory.eINSTANCE.createPluginInclude();
      featureInclude.setId(pluginProject.getId());
      featureInclude.setVersion(pluginProject.getVersion());

      Annotation b2Metadata = B2MetadataUtils.getB2Metadata(pluginProject);
      if (b2Metadata != null) {
         featureInclude.getAnnotations().add(EcoreUtil.copy(b2Metadata));
      }

      B2MetadataUtils.setModuleId(featureInclude, module.getId());
      B2MetadataUtils.setModuleVersion(featureInclude, module.getVersion());
      B2MetadataUtils.setTestPlugin(featureInclude, pluginProject.isTestPlugin());

      PluginsFacet facet = pluginProject.getParent();
      B2MetadataUtils.setFacetName(featureInclude, facet.getName());

      return featureInclude;
   }

   public static Manifest readManifest(File file, boolean strict) {
      final Map<String, Boolean> options = Collections.singletonMap("strict", Boolean.valueOf(strict));

      final Resource eResource = new GenericManifestResourceImpl();
      new IOOperation<InputStream>(buffIn(fileIn(file))) {
         @Override
         protected void run(InputStream openResource) throws IOException {
            eResource.load(openResource, options);
         }
      }.run();

      final Manifest bundleManifest = (Manifest) eResource.getContents().get(0);
      eResource.getContents().clear(); // disconnect from resource
      return bundleManifest;
   }
}
