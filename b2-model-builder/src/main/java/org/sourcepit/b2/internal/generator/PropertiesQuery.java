/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
