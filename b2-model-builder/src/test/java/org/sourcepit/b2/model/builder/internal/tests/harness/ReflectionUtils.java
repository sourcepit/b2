/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Bernd
 */
public final class ReflectionUtils
{
   private interface IRunnableWithResult<T>
   {
      T run() throws InvocationTargetException;
   }

   private abstract static class AccessRunnable<T> implements IRunnableWithResult<T>
   {
      private final AccessibleObject accessible;

      public AccessRunnable(AccessibleObject accessible)
      {
         this.accessible = accessible;
      }

      public T run() throws InvocationTargetException
      {
         final boolean isAccessible = accessible.isAccessible();
         try
         {
            if (!isAccessible)
            {
               accessible.setAccessible(true);
            }
            return runAccessible();
         }
         finally
         {
            if (!isAccessible)
            {
               accessible.setAccessible(isAccessible);
            }
         }
      }

      protected abstract T runAccessible() throws InvocationTargetException;
   }

   private ReflectionUtils()
   {
      super();
   }


   @SuppressWarnings("unchecked")
   public static <T> T getFieldValue(final Object target, String name, Class<T> type)
   {
      try
      {
         final Field field = getFieldByNameIncludingSuperclasses(name, target.getClass());
         return new AccessRunnable<T>(field)
         {
            @Override
            protected T runAccessible() throws InvocationTargetException
            {
               try
               {
                  return (T) field.get(target);
               }
               catch (Exception e)
               {
                  throw new InvocationTargetException(e);
               }
            }
         }.run();
      }
      catch (InvocationTargetException e)
      {
         throw new IllegalArgumentException(e.getCause());
      }
      catch (Exception e)
      {
         throw new IllegalArgumentException(e);
      }
   }

   @SuppressWarnings("unchecked")
   public static <T> List<T> getFieldListValue(final Object target, String name, Class<T> type)
   {
      return (List<T>) getFieldValue(target, name, List.class);
   }

   private static Field getFieldByNameIncludingSuperclasses(String fieldName, Class<?> clazz)
   {
      Field retValue = null;

      try
      {
         retValue = clazz.getDeclaredField(fieldName);
      }
      catch (NoSuchFieldException e)
      {
         Class<?> superclass = clazz.getSuperclass();

         if (superclass != null)
         {
            retValue = getFieldByNameIncludingSuperclasses(fieldName, superclass);
         }
      }

      return retValue;
   }
}
