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

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.codehaus.plexus.interpolation.AbstractValueSource;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.tools.shared.resources.harness.SharedResourcesCopier;
import org.sourcepit.tools.shared.resources.harness.ValueSourceUtils;
import org.sourcepit.tools.shared.resources.internal.harness.IFilterStrategy;

import com.google.common.base.Optional;

public class DefaultTemplateCopier implements ITemplates
{
   private final PropertiesSource globalProperties;

   final PathMatcher filterStrategy;

   public DefaultTemplateCopier()
   {
      this(Optional.<PropertiesSource> absent());
   }

   public DefaultTemplateCopier(Optional<? extends PropertiesSource> globalProperties)
   {
      this.globalProperties = globalProperties.orNull();
      final String pattern = this.globalProperties == null ? "**" : this.globalProperties.get(
         "b2.templates.filteredResources", "**");
      filterStrategy = PathMatcher.parse(pattern, "/", ",");
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

      copier.setFilterStrategy(new IFilterStrategy()
      {
         public boolean filter(String fileName)
         {
            return filterStrategy.isMatch(fileName);
         }
      });

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
