/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.common.internal.utils.PathMatcher;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleRequirement;
import org.sourcepit.common.manifest.osgi.PackageImport;

@Named
@Singleton
public class DefaultUIDetector implements UIDetector
{
   private final BundleManifestReader manifestReader;

   @Inject
   public DefaultUIDetector(BundleManifestReader manifestReader)
   {
      this.manifestReader = manifestReader;
   }

   public boolean requiresUI(PluginProject pluginProject, IConverter converter)
   {
      final PathMatcher matcher = PathMatcher.parsePackagePatterns(converter.getProperties()
         .get("b2.uiDetector.filter"));

      final String id = pluginProject.getId();
      if (matcher.isMatch(id))
      {
         return true;
      }

      final BundleManifest manifest = manifestReader.readManifest(pluginProject);

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