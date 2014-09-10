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

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.ClassPathEntry;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named("avoidDotAndJarOnBundleCP")
public class AvoidDotAndJarOnBundleCPConstraint implements ModuleValidationConstraint
{
   private final UnpackStrategy unpackStrategy;

   private final Logger logger;

   @Inject
   public AvoidDotAndJarOnBundleCPConstraint(UnpackStrategy unpackStrategy, Logger logger)
   {
      this.unpackStrategy = unpackStrategy;
      this.logger = logger;
   }

   public void validate(EObject eObject, PropertiesSource properties, boolean quickFixesEnabled)
   {
      if (eObject instanceof PluginProject)
      {
         final PluginProject pluginProject = (PluginProject) eObject;
         if (!pluginProject.isDerived())
         {
            validate(pluginProject, quickFixesEnabled);
         }
      }
   }

   private void validate(PluginProject pluginProject, boolean quickFixesEnabled)
   {
      final boolean unpack = unpackStrategy.isUnpack(pluginProject);
      if (unpack)
      {
         final BundleManifest manifest = pluginProject.getBundleManifest();
         if (hasDotOnBundleCP(manifest))
         {
            final StringBuilder msg = new StringBuilder();
            msg.append(pluginProject.getId());
            msg.append(": Bundle will be unpacked on installation but contains \'.\' on the bundles classpath declaration. This may lead to naked class files after unpacking. It is recomended to build these classes in its own JAR via the bundles build.properties.");

            if (quickFixesEnabled)
            {
               msg.append(" (no quick fix available)");
            }

            logger.warn(msg.toString());
         }
      }
   }

   private boolean hasDotOnBundleCP(BundleManifest manifest)
   {
      return getDotBundleCPEntry(manifest) != null;
   }

   private ClassPathEntry getDotBundleCPEntry(BundleManifest manifest)
   {
      EList<ClassPathEntry> cp = manifest.getBundleClassPath();
      if (cp != null)
      {
         for (ClassPathEntry entry : cp)
         {
            for (String path : entry.getPaths())
            {
               if (".".equals(path))
               {
                  return entry;
               }
            }
         }
      }
      return null;
   }
}
