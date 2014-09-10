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

package org.sourcepit.b2.maven.internal.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelProcessor;
import org.apache.maven.model.building.ModelProcessor;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.io.ModelParseException;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

/**
 * @author Bernd
 */
@Component(role = ModelProcessor.class)
public class B2ModelProcessor extends DefaultModelProcessor implements ModelProcessor
{
   @Requirement
   private ModuleDescriptorProcessor moduleDescriptorProcessor;

   @Override
   public Model read(File input, Map<String, ?> options) throws IOException
   {
      final Model model = moduleDescriptorProcessor.convert(input);
      if (model != null)
      {
         return model;
      }
      return super.read(input, options);
   }

   @Override
   public Model read(InputStream input, Map<String, ?> options) throws IOException
   {
      final Model model = convert(options);
      if (model != null)
      {
         return model;
      }
      return super.read(input, options);
   }

   @Override
   public Model read(Reader input, Map<String, ?> options) throws IOException
   {
      final Model model = convert(options);
      if (model != null)
      {
         return model;
      }
      return super.read(input, options);
   }

   @Override
   public File locatePom(File projectDirectory)
   {
      return super.locatePom(projectDirectory);
   }

   private Model convert(Map<String, ?> options) throws IOException, ModelParseException
   {
      final File descriptor = resolveFile(options);
      if (descriptor != null)
      {
         return moduleDescriptorProcessor.convert(descriptor);
      }
      return null;
   }

   private File resolveFile(Map<String, ?> options)
   {
      if (options != null)
      {
         final Object oSource = options.get(SOURCE);
         if (oSource instanceof ModelSource)
         {
            final String location = ((ModelSource) oSource).getLocation();
            if (location != null)
            {
               File descriptor = new File(location);
               if (descriptor.exists())
               {
                  return descriptor;
               }
            }
         }
      }
      return null;
   }
}
