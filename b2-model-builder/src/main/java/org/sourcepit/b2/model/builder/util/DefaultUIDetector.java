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

package org.sourcepit.b2.model.builder.util;

import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleRequirement;
import org.sourcepit.common.manifest.osgi.PackageImport;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
@Singleton
public class DefaultUIDetector implements UIDetector
{
   public boolean requiresUI(PluginProject pluginProject, PropertiesSource properties)
   {
      final PathMatcher matcher = PathMatcher.parsePackagePatterns(properties.get("b2.uiDetector.filter"));

      final String id = pluginProject.getId();
      if (matcher.isMatch(id))
      {
         return true;
      }

      final BundleManifest manifest = pluginProject.getBundleManifest();

      final EList<BundleRequirement> bundleRequirements = manifest.getRequireBundle();
      if (bundleRequirements != null)
      {
         for (BundleRequirement bundleRequirement : bundleRequirements)
         {
            for (String symbolicName : bundleRequirement.getSymbolicNames())
            {
               if (matcher.isMatch(symbolicName))
               {
                  return true;
               }
            }
         }
      }

      final EList<PackageImport> packageImports = manifest.getImportPackage();
      if (packageImports != null)
      {
         for (PackageImport packageImport : packageImports)
         {
            for (String packageName : packageImport.getPackageNames())
            {
               if (matcher.isMatch(packageName))
               {
                  return true;
               }
            }
         }
      }

      return false;
   }
}