/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.maven.model.Extension;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Repository;
import org.apache.maven.model.Resource;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.sourcepit.common.utils.lang.Exceptions;

public class ModelCutter
{
   private final Collection<Class<?>> leafTypes;

   public ModelCutter()
   {
      leafTypes = new HashSet<Class<?>>();
      leafTypes.add(Repository.class);
      leafTypes.add(Plugin.class);
      leafTypes.add(Extension.class);
      leafTypes.add(Resource.class);
   }

   public void cut(Model target, Model source)
   {
      final String targetPackaging = target.getPackaging();
      cut(target, source, Model.class);
      if (!"jar".equals(targetPackaging) && target.getPackaging() == null)
      {
         target.setPackaging(targetPackaging);
      }
   }

   private Object cut(Map<Object, Object> targetMap, Map<Object, Object> sourceMap, Class<?> valueType)
   {
      final Map<Object, Object> newValues = new LinkedHashMap<Object, Object>();

      for (Entry<Object, Object> entry : targetMap.entrySet())
      {
         final Object targetKey = entry.getKey();
         final Object targetValue = entry.getValue();
         if (targetValue == null)
         {
            newValues.put(targetKey, null);
         }
         else
         {
            Object sourceValue = sourceMap.get(targetKey);
            Object result = cut(targetValue, sourceValue, targetValue.getClass());
            if (result != null)
            {
               newValues.put(targetKey, result);
            }
         }
      }

      targetMap.clear();
      targetMap.putAll(newValues);

      return targetMap;
   }

   private Object cut(Collection<Object> targetValues, Collection<Object> sourceValues, Class<?> valueType)
   {
      final List<Object> newValues = new ArrayList<Object>();
      for (Object targetValue : targetValues)
      {
         if (targetValue == null)
         {
            newValues.add(null);
         }
         else
         {
            Object sourceValue = findSourceValue(targetValue, sourceValues);
            Object result = cut(targetValue, sourceValue, targetValue.getClass());
            if (result != null)
            {
               newValues.add(result);
            }
         }
      }

      targetValues.clear();
      targetValues.addAll(newValues);

      return targetValues;
   }

   private Object cut(Object targetValue, Object sourceValue, Class<?> valueType)
   {
      if (targetValue == null || sourceValue == null)
      {
         return targetValue;
      }
      return cutNullSafe(targetValue, sourceValue, valueType);
   }

   @SuppressWarnings("unchecked")
   private Object cutNullSafe(Object targetValue, Object sourceValue, Class<?> valueType)
   {
      if (Collection.class.isAssignableFrom(valueType))
      {
         return cut((Collection<Object>) targetValue, (Collection<Object>) sourceValue, valueType);
      }

      if (Map.class.isAssignableFrom(valueType))
      {
         return cut((Map<Object, Object>) targetValue, (Map<Object, Object>) sourceValue, valueType);
      }

      final Set<JavaProperty> properties = JavaProperty.getProperties(valueType);

      if (properties.isEmpty() || isLeafType(valueType))
      {
         return equals(targetValue, sourceValue) ? null : targetValue;
      }

      boolean isEmpty = true;

      for (JavaProperty property : properties)
      {
         Object result = cut(property.doGetValue(targetValue), property.doGetValue(sourceValue), property.getType());
         property.doSetValue(targetValue, result);
         if (!isEmpty(result))
         {
            isEmpty = false;
         }
      }

      return isEmpty ? null : targetValue;
   }

   @SuppressWarnings("rawtypes")
   private boolean isEmpty(Object result)
   {
      if (result == null)
      {
         return true;
      }

      if (result instanceof Collection)
      {
         return ((Collection) result).isEmpty();
      }

      if (result instanceof Map)
      {
         return ((Map) result).isEmpty();
      }

      return false;
   }

   private boolean equals(Object targetValue, Object sourceValue)
   {
      if (targetValue == null)
      {
         return sourceValue == null;
      }
      return Arrays.equals(toByteArray(targetValue), toByteArray(sourceValue));
   }

   private byte[] toByteArray(Object object)
   {
      try
      {
         ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
         ObjectOutputStream outputStream = new ObjectOutputStream(byteOut);
         outputStream.writeObject(object);
         return byteOut.toByteArray();
      }
      catch (IOException e)
      {
         throw Exceptions.pipe(e);
      }
   }

   private Object findSourceValue(Object targetValue, Collection<?> sourceValues)
   {
      for (Object sourceValue : sourceValues)
      {
         if (equals(targetValue, sourceValue))
         {
            return sourceValue;
         }
      }
      return null;
   }

   private boolean isLeafType(Class<?> valueType)
   {
      for (Class<?> leafType : leafTypes)
      {
         if (leafType.isAssignableFrom(valueType))
         {
            return true;
         }
      }
      return false;
   }
}
