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

package org.sourcepit.b2.maven.internal.converter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.ModelParseException;
import org.apache.maven.model.io.ModelReader;
import org.sourcepit.common.utils.nls.NlsUtils;
import org.sourcepit.common.utils.path.PathUtils;

@Named("module.xml")
public class DefaultModuleDescriptorConverter implements IModuleDescriptorConverter
{
   @Inject
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
