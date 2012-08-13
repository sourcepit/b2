/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class JavaProperty
{
   private Class<?> type;
   private String name;
   private Method getter;
   private Method setter;

   public Class<?> getType()
   {
      return type;
   }

   public String getName()
   {
      return name;
   }

   public Method getGetter()
   {
      return getter;
   }

   public Method getSetter()
   {
      return setter;
   }

   public Object doGetValue(Object owner)
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

   public void doSetValue(Object owner, Object value)
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

   static Set<JavaProperty> getProperties(Class<?> clazz)
   {
      final Map<String, JavaProperty> nameToPropertyMap = new HashMap<String, JavaProperty>();
      for (Method method : clazz.getMethods())
      {
         final String methodName = method.getName();
         final boolean isGetter = isGetter(method);
         final boolean isSetter = isSetter(method);
         if (isGetter || isSetter)
         {
            final JavaProperty property = getProperty(nameToPropertyMap, toPropertyName(methodName));
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

      final Set<JavaProperty> properties = new HashSet<JavaProperty>();
      for (JavaProperty property : nameToPropertyMap.values())
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

   private static JavaProperty getProperty(Map<String, JavaProperty> nameToPropertyMap, final String propertyName)
   {
      JavaProperty property = nameToPropertyMap.get(propertyName);
      if (property == null)
      {
         property = new JavaProperty();
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