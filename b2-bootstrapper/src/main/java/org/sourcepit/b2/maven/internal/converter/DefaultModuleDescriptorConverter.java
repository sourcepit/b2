/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.maven.internal.converter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.ModelParseException;
import org.apache.maven.model.io.ModelReader;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sourcepit.b2.common.internal.utils.NlsUtils;
import org.sourcepit.b2.common.internal.utils.PathUtils;

@Component(role = IModuleDescriptorConverter.class, hint = "module.xml")
public class DefaultModuleDescriptorConverter implements IModuleDescriptorConverter
{
   @Requirement
   private ModelReader modelReader;

   public Model convert(File moduleDescriptor) throws IOException, ModelParseException
   {
      final Map<String, String> options = new HashMap<String, String>();
      options.put(ModelReader.IS_STRICT, Boolean.FALSE.toString());

      final Model model = modelReader.read(moduleDescriptor, options);
      injectProjectProperties(model.getProperties(), moduleDescriptor);
      return model;
   }

   private void injectProjectProperties(Properties target, File modelFile)
   {
      final File directory = modelFile.getParentFile();
      final String fileName = PathUtils.trimFileExtension(modelFile.getName());
      final String fileExtension = ".properties";

      final Set<Locale> locles = NlsUtils.injectNlsProperties(target, directory, fileName, fileExtension);

      // remove default locale
      locles.remove(null);
   }
}
