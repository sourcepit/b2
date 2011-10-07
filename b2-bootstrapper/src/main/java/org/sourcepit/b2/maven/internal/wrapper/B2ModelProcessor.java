/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
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
