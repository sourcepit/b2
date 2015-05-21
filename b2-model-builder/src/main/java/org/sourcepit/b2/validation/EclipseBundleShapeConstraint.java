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

package org.sourcepit.b2.validation;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.slf4j.Logger;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.utils.lang.Exceptions;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named("eclipseBundleShape")
public class EclipseBundleShapeConstraint implements ModuleValidationConstraint {
   private final UnpackStrategy unpackStrategy;

   private final Logger logger;

   @Inject
   public EclipseBundleShapeConstraint(UnpackStrategy unpackStrategy, Logger logger) {
      this.unpackStrategy = unpackStrategy;
      this.logger = logger;
   }

   public void validate(EObject eObject, PropertiesSource properties, boolean quickFixesEnabled) {
      if (eObject instanceof PluginProject) {
         final PluginProject pluginProject = (PluginProject) eObject;
         if (!pluginProject.isDerived()) {
            validate(pluginProject, quickFixesEnabled);
         }
      }
   }

   private void validate(PluginProject pluginProject, boolean quickFixesEnabled) {
      final boolean unpack = unpackStrategy.isUnpack(pluginProject);
      if (unpack) {
         final BundleManifest manifest = pluginProject.getBundleManifest();

         final String id = pluginProject.getId();

         final String bundleShape = manifest.getHeaderValue("Eclipse-BundleShape");
         if (!"dir".equals(bundleShape)) {
            if (quickFixesEnabled) {
               manifest.setHeader("Eclipse-BundleShape", "dir");
               save(pluginProject.getDirectory(), manifest);
            }

            final StringBuilder msg = new StringBuilder();
            msg.append(id);
            msg.append(": Missing manifest entry: \'Eclipse-BundleShape: dir\'.");
            if (quickFixesEnabled) {
               msg.append(" (applied quick fix)");
            }
            else {
               msg.append(" (quick fix available)");
            }

            logger.warn(msg.toString());
         }
      }
   }

   static void save(File pluginDir, final BundleManifest manifest) {
      Resource eResource = manifest.eResource();
      if (eResource == null) {
         final URI uri = URI.createFileURI(new File(pluginDir, "META-INF/MANIFEST.MF").getAbsolutePath());
         eResource = new BundleManifestResourceImpl(uri);
         eResource.getContents().add(manifest);
         try {
            eResource.save(null);
         }
         catch (IOException e) {
            throw Exceptions.pipe(e);
         }
         finally {
            eResource.getContents().clear();
         }
      }
      else {
         try {
            eResource.save(null);
         }
         catch (IOException e) {
            throw Exceptions.pipe(e);
         }
      }
   }
}
