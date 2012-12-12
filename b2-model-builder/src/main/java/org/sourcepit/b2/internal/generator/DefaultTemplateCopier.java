/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.codehaus.plexus.interpolation.AbstractValueSource;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.tools.shared.resources.harness.SharedResourcesCopier;
import org.sourcepit.tools.shared.resources.harness.ValueSourceUtils;

import com.google.common.base.Optional;

public class DefaultTemplateCopier implements ITemplates
{
   private final PropertiesSource globalProperties;

   public DefaultTemplateCopier()
   {
      this(Optional.<PropertiesSource> absent());
   }

   public DefaultTemplateCopier(Optional<? extends PropertiesSource> globalProperties)
   {
      this.globalProperties = globalProperties.orNull();
   }

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
      copy(resourcePath, targetDir, properties, true);
   }

   /**
    * {@inheritDoc}
    */
   public void copy(String resourcePath, File targetDir, Properties properties, boolean includeDefaults)
   {
      final SharedResourcesCopier copier = createCopier(properties);
      try
      {
         copier.setManifestHeader("B2-Templates");
         copier.copy(resourcePath, targetDir);
      }
      catch (IOException ex)
      {
         if (includeDefaults)
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
         else
         {
            throw new IllegalStateException(ex);
         }
      }
   }

   protected SharedResourcesCopier createCopier(Properties properties)
   {
      final SharedResourcesCopier copier = new SharedResourcesCopier();
      copier.setClassLoader(getClass().getClassLoader());
      copier.setEscapeString("\\");
      addValueSources(copier, properties);

      if (globalProperties != null)
      {
         copier.getValueSources().add(new AbstractValueSource(false)
         {
            public Object getValue(String expression)
            {
               return globalProperties.get(expression);
            }
         });
      }

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
