/*******************************************************************************
 * Copyright (c) 2011 Bernd and others. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Bernd - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.sourcepit.b2.directory.parser.internal.project;

import java.io.File;
import java.util.jar.Manifest;

import javax.inject.Named;

import org.sourcepit.b2.model.builder.internal.util.ManifestUtils;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;

import com.springsource.util.osgi.manifest.parse.DummyParserLogger;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParser;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;

/**
 * @author Bernd
 */
@Named("plugin")
public class PluginProjectParserRule extends AbstractProjectParserRule<PluginProject>
{
   @Override
   public PluginProject parse(File directory, IConverter converter)
   {
      final Manifest manifest;
      try
      {
         manifest = ManifestUtils.readManifest(new File(directory, "META-INF/MANIFEST.MF"));
      }
      catch (IllegalArgumentException e)
      {
         return null;
      }

      final String symbolicName = ManifestUtils.getBundleSymbolicName(manifest);
      if (symbolicName == null)
      {
         return null;
      }

      final PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.setId(symbolicName);
      project.setVersion(ManifestUtils.getBundleVersion(manifest));
      project.setDirectory(directory);
      project.setTestPlugin(project.getId().endsWith(".tests"));

      final String fragmentHost = manifest.getMainAttributes().getValue("Fragment-Host");
      if (fragmentHost != null)
      {
         final HeaderParser headerParser = HeaderParserFactory.newHeaderParser(new DummyParserLogger());
         final HeaderDeclaration hostHeader = headerParser.parseFragmentHostHeader(fragmentHost);
         if (!hostHeader.getNames().isEmpty())
         {
            String hostName = hostHeader.getNames().get(0);
            project.setFragmentHostSymbolicName(hostName);
            project.setFragmentHostVersion(hostHeader.getAttributes().get("bundle-version"));
         }
      }

      return project;
   }
}
