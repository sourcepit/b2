/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.maven.model.Extension;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Repository;
import org.apache.maven.model.Resource;

public class ModelCutter
{
   private final static class Property
   {
      public Class<?> type;
      public String name;
      public Method getter;
      public Method setter;

      public Object getValue(Object owner)
      {
         try
         {
            return getter.invoke(owner, (Object[]) null);
         }
         catch (InvocationTargetException e)
         {
            throw new IllegalStateException(e.getTargetException());
         }
         catch (Exception e)
         {
            throw new IllegalStateException(e);
         }
      }

      public void setValue(Object owner, Object value)
      {
         try
         {
            setter.invoke(owner, value);
         }
         catch (InvocationTargetException e)
         {
            throw new IllegalStateException(e.getTargetException());
         }
         catch (Exception e)
         {
            throw new IllegalStateException(e);
         }
      }

      @Override
      public String toString()
      {
         final StringBuilder sb = new StringBuilder();

         sb.append(name);
         sb.append(" : ");
         if (type == null)
         {
            sb.append(type);
         }
         else
         {
            sb.append(type.getName());
         }

         return sb.toString();
      }

      private static Set<Property> getProperties(Class<?> clazz)
      {
         final Map<String, Property> nameToPropertyMap = new HashMap<String, ModelCutter.Property>();
         for (Method method : clazz.getMethods())
         {
            final String methodName = method.getName();
            final boolean isGetter = isGetter(method);
            final boolean isSetter = isSetter(method);
            if (isGetter || isSetter)
            {
               final Property property = getProperty(nameToPropertyMap, toPropertyName(methodName));
               if (isSetter)
               {
                  property.setter = method;
               }
               if (isGetter)
               {
                  property.getter = method;
               }

               if (property.getter != null && property.setter != null)
               {
                  property.type = determinePropertyType(property.getter, property.setter);
               }
            }
         }

         final Set<Property> properties = new HashSet<ModelCutter.Property>();
         for (Property property : nameToPropertyMap.values())
         {
            if (property.type != null)
            {
               properties.add(property);
            }
         }
         return properties;
      }

      private static Class<?> determinePropertyType(Method getter, Method setter)
      {
         final Class<?> returnType = getter.getReturnType();
         final Class<?> parameterType = setter.getParameterTypes()[0];
         if (returnType != parameterType)
         {
            return null;
         }
         return returnType;
      }

      private static Property getProperty(Map<String, Property> nameToPropertyMap, final String propertyName)
      {
         Property property = nameToPropertyMap.get(propertyName);
         if (property == null)
         {
            property = new Property();
            property.name = propertyName;
            nameToPropertyMap.put(propertyName, property);
         }
         return property;
      }

      private static String toPropertyName(String methodName)
      {
         if (methodName.startsWith("is"))
         {
            return methodName.substring(2, methodName.length());
         }
         return methodName.substring(3, methodName.length());
      }

      private static boolean isSetter(final Method method)
      {
         final String methodName = method.getName();
         if (!isSetterName(methodName))
         {
            return false;
         }

         Class<?> returnType = method.getReturnType();
         if (returnType != void.class)
         {
            return false;
         }

         if (method.getParameterTypes().length != 1)
         {
            return false;
         }

         return true;
      }

      private static boolean isGetter(final Method method)
      {
         final String methodName = method.getName();
         if (!isGetterName(methodName))
         {
            return false;
         }

         if (method.getReturnType() == null)
         {
            return false;
         }

         if (method.getParameterTypes().length != 0)
         {
            return false;
         }

         return true;
      }

      private static boolean isSetterName(final String methodName)
      {
         if (methodName.length() > 3)
         {
            return methodName.startsWith("set");
         }
         return false;
      }

      private static boolean isGetterName(final String methodName)
      {
         if (methodName.length() > 3)
         {
            return methodName.startsWith("get");
         }
         else if (methodName.length() > 2)
         {
            return methodName.startsWith("is");
         }
         else
         {
            return false;
         }
      }
   }

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
      cut(target, source, Model.class);
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

   @SuppressWarnings("unchecked")
   private Object cut(Object targetValue, Object sourceValue, Class<?> valueType)
   {
      if (targetValue == null || sourceValue == null)
      {
         return targetValue;
      }

      if (Collection.class.isAssignableFrom(valueType))
      {
         return cut((Collection<Object>) targetValue, (Collection<Object>) sourceValue, valueType);
      }

      if (Map.class.isAssignableFrom(valueType))
      {
         return cut((Map<Object, Object>) targetValue, (Map<Object, Object>) sourceValue, valueType);
      }

      final Set<Property> properties = Property.getProperties(valueType);

      if (properties.isEmpty() || isLeafType(valueType))
      {
         return equals(targetValue, sourceValue) ? null : targetValue;
      }

      boolean isEmpty = true;

      for (Property property : properties)
      {
         Object result = cut(property.getValue(targetValue), property.getValue(sourceValue), property.type);
         property.setValue(targetValue, result);
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
      if (targetValue instanceof Resource && sourceValue instanceof Resource)
      {
         return equalsDeluxe(((Resource) targetValue).getDirectory(), ((Resource) sourceValue).getDirectory());
      }
      return equalsDeluxe(targetValue, sourceValue);
   }

   private boolean equalsDeluxe(Object targetValue, Object sourceValue)
   {
      if (targetValue == null)
      {
         return sourceValue == null;
      }
      return targetValue.equals(sourceValue);
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
