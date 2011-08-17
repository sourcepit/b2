/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.model.io.ModelReader;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.generator.AbstractGenerator;
import org.sourcepit.beef.b2.internal.model.Annotateable;
import org.sourcepit.beef.b2.model.builder.util.IConverter;

public abstract class AbstractPomGenerator extends AbstractGenerator
{
   public static final String SOURCE_MAVEN = "maven";
   public static final String KEY_POM_FILE = "pomFile";

   @Override
   public final void generate(EObject inputElement, IConverter converter, ITemplates templates)
   {
      generate((Annotateable) inputElement, converter, templates);
   }

   protected abstract void generate(Annotateable inputElement, IConverter converter, ITemplates templates);

   protected Model readMavenModel(File pomFile)
   {
      final Map<String, String> options = new HashMap<String, String>();
      options.put(ModelReader.IS_STRICT, Boolean.FALSE.toString());
      try
      {
         return new DefaultModelReader().read(pomFile, options);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   protected void writeMavenModel(File file, Model model)
   {
      try
      {
         new DefaultModelWriter().write(file, null, model);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }
}
