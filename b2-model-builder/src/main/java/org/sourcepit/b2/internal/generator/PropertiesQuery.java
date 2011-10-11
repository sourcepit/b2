/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.codehaus.plexus.interpolation.ValueSource;

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

   public void setRetryWithoutPrefix(boolean retryWithoutPrefix)
   {
      this.retryWithoutPrefix = retryWithoutPrefix;
   }

   public void addKey(String key)
   {
      keys.add(key);
   }

   public void setDefault(String defaultValue)
   {
      this.defaultValue = defaultValue;
   }

   public String lookup(Collection<ValueSource> valueSources)
   {
      for (String rawKey : keys)
      {
         final String key = prefix == null ? rawKey : prefix + rawKey;
         final String result = lookup(valueSources, key);
         if (result != null)
         {
            return result;
         }
      }
      if (prefix != null && retryWithoutPrefix)
      {
         for (String key : keys)
         {
            final String result = lookup(valueSources, key);
            if (result != null)
            {
               return result;
            }
         }
      }
      return defaultValue;
   }

   private String lookup(Collection<ValueSource> valueSources, String key)
   {
      for (ValueSource valueSource : valueSources)
      {
         Object value = valueSource.getValue(key);
         if (value != null)
         {
            return value.toString();
         }
      }
      return null;
   }
}
