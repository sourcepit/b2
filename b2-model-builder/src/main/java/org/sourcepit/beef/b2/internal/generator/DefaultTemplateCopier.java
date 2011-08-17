/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.sourcepit.tools.shared.resources.harness.SharedResourcesCopier;
import org.sourcepit.tools.shared.resources.harness.ValueSourceUtils;

public class DefaultTemplateCopier implements ITemplates
{
   /**
    * {@inheritDoc}
    */
   public void copy(String resourcePath, File targetDir) throws IllegalArgumentException, IllegalStateException
   {
      copy(resourcePath, targetDir, null);
   }

   /**
    * {@inheritDoc}
    */
   public void copy(String resourcePath, File targetDir, Properties properties) throws IllegalArgumentException,
      IllegalStateException
   {
      final SharedResourcesCopier copier = createCopier(properties);
      try
      {
         copier.setManifestHeader("B2-Templates");
         copier.copy(resourcePath, targetDir);
      }
      catch (IOException ex)
      {
         try
         {
            copier.setManifestHeader("B2-Default-Templates");
            copier.copy(resourcePath, targetDir);
         }
         catch (FileNotFoundException e)
         {
            throw new IllegalArgumentException(e);
         }
         catch (IOException e)
         {
            throw new IllegalStateException(e);
         }
      }
   }

   protected SharedResourcesCopier createCopier(Properties properties)
   {
      final SharedResourcesCopier copier = new SharedResourcesCopier();
      copier.setClassLoader(getClass().getClassLoader());
      copier.setEscapeString("\\");
      addValueSources(copier, properties);
      copier.setFilter(!copier.getValueSources().isEmpty());
      return copier;
   }

   protected void addValueSources(final SharedResourcesCopier copier, Properties properties)
   {
      if (properties != null)
      {
         copier.getValueSources().add(ValueSourceUtils.newPropertyValueSource(properties));
      }
   }
}
