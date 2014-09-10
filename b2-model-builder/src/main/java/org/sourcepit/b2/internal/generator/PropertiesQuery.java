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

import java.util.Collection;
import java.util.LinkedHashSet;

import org.sourcepit.common.utils.props.PropertiesSource;

public class PropertiesQuery
{
   private String prefix;

   private Collection<String> keys = new LinkedHashSet<String>();

   private String defaultValue;

   private boolean retryWithoutPrefix;

   public void setPrefix(String prefix)
   {
      this.prefix = prefix;
   }

   public String getPrefix()
   {
      return prefix;
   }

   public void setRetryWithoutPrefix(boolean retryWithoutPrefix)
   {
      this.retryWithoutPrefix = retryWithoutPrefix;
   }

   public Collection<String> getKeys()
   {
      return keys;
   }

   public void setDefaultValue(String defaultValue)
   {
      this.defaultValue = defaultValue;
   }

   public String getDefaultValue()
   {
      return defaultValue;
   }

   public String lookup(PropertiesSource properties)
   {
      for (String rawKey : keys)
      {
         final String key = prefix == null ? rawKey : prefix + rawKey;
         final String result = lookup(properties, key);
         if (result != null)
         {
            return result;
         }
      }
      if (prefix != null && retryWithoutPrefix)
      {
         for (String key : keys)
         {
            final String result = lookup(properties, key);
            if (result != null)
            {
               return result;
            }
         }
      }
      return defaultValue;
   }

   private String lookup(PropertiesSource properties, String key)
   {
      return properties.get(key);
   }
}
