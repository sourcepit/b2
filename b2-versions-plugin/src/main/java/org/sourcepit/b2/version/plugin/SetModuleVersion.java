/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.sourcepit.b2.version.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.model.Model;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.mojo.versions.api.PomHelper;
import org.codehaus.mojo.versions.rewriting.ModifiedPomXMLEventReader;
import org.eclipse.emf.common.util.URI;
import org.sourcepit.b2.files.FileVisitor;
import org.sourcepit.b2.version.plugin.internal.ModuleVersionsMojo;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.Version;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.manifest.resource.ManifestResource;
import org.sourcepit.common.utils.lang.Exceptions;

/**
 * @author eddi-weiss
 */
@Mojo(requiresProject = true, name = "set", defaultPhase = LifecyclePhase.POST_CLEAN)
public class SetModuleVersion extends ModuleVersionsMojo
{

   @Parameter(name = "newVersion", required = true, readonly = true, defaultValue = "${newVersion}")
   private String newVersion = null;

   @Override
   protected void updateModule(ModifiedPomXMLEventReader reader, Model projectModel) throws MojoExecutionException,
      MojoFailureException, XMLStreamException
   {
      String version = projectModel.getVersion();
      if (version == null)
      {
         getLog().info("Skip, version is not present");
      }
      else if (StringUtils.equalsIgnoreCase(newVersion, version))
      {
         getLog().info("Skip, version is up-to-date");
      }
      else
      {
         getLog().info("Updating version " + version + ">" + newVersion);
         PomHelper.setProjectVersion(reader, newVersion);
      }
      bridge.getModuleDirectory(project).accept(new FileVisitor<MojoExecutionException>()
      {
         @Override
         public boolean visit(File file, int flags) throws MojoExecutionException
         {
            if (StringUtils.equalsIgnoreCase("MANIFEST.MF", file.getName()))
            {
               rewriteBundleVersion(file, newVersion);
            }
            return true;
         }
      });
   }

   private void rewriteBundleVersion(final File manifestFile, String bundleVersion) throws MojoExecutionException
   {
      final Version osgiVersion = Version.parse(toOSGiVersion(bundleVersion));

      if (!manifestFile.canRead())
      {
         throw new MojoExecutionException("", new FileNotFoundException(manifestFile.getPath()));
      }
      final BundleManifest manifest = readBundleManifest(manifestFile);
      final Version current = manifest.getBundleVersion();
      if (!current.equals(osgiVersion))
      {
         getLog().info(
            "bundle:" + manifest.getBundleSymbolicName().getSymbolicName() + "  updating version "
               + current.toFullString() + ">" + osgiVersion.toFullString());
         manifest.setBundleVersion(osgiVersion);
         try
         {
            manifest.eResource().save(null);
         }
         catch (IOException e)
         {
            throw new MojoExecutionException("", e);
         }
      }
   }


   private String toOSGiVersion(final String mavenVersion)
   {
      final boolean isSnapshot = ArtifactUtils.isSnapshot(mavenVersion);
      int qualiIdx = mavenVersion.indexOf('-');
      if (qualiIdx > -1)
      {
         if (isSnapshot)
         {
            return mavenVersion.substring(0, qualiIdx) + ".qualifier";
         }
         else
         {
            char[] chars = mavenVersion.toCharArray();
            chars[qualiIdx] = '.';
            return new String(chars);
         }
      }
      return mavenVersion;
   }

   private BundleManifest readBundleManifest(final File manifestFile)
   {
      final ManifestResource resource =
         new BundleManifestResourceImpl(URI.createFileURI(manifestFile.getAbsolutePath()));
      try
      {
         resource.load(null);
      }
      catch (IOException e)
      {
         throw Exceptions.pipe(e);
      }
      return (BundleManifest) resource.getContents().get(0);
   }
}
