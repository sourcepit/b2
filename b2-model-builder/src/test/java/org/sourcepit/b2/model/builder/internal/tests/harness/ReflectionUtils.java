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

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Bernd
 */
public final class ReflectionUtils {
   private interface IRunnableWithResult<T> {
      T run() throws InvocationTargetException;
   }

   private abstract static class AccessRunnable<T> implements IRunnableWithResult<T> {
      private final AccessibleObject accessible;

      public AccessRunnable(AccessibleObject accessible) {
         this.accessible = accessible;
      }

      public T run() throws InvocationTargetException {
         final boolean isAccessible = accessible.isAccessible();
         try {
            if (!isAccessible) {
               accessible.setAccessible(true);
            }
            return runAccessible();
         }
         finally {
            if (!isAccessible) {
               accessible.setAccessible(isAccessible);
            }
         }
      }

      protected abstract T runAccessible() throws InvocationTargetException;
   }

   private ReflectionUtils() {
      super();
   }


   @SuppressWarnings("unchecked")
   public static <T> T getFieldValue(final Object target, String name, Class<T> type) {
      try {
         final Field field = getFieldByNameIncludingSuperclasses(name, target.getClass());
         return new AccessRunnable<T>(field) {
            @Override
            protected T runAccessible() throws InvocationTargetException {
               try {
                  return (T) field.get(target);
               }
               catch (Exception e) {
                  throw new InvocationTargetException(e);
               }
            }
         }.run();
      }
      catch (InvocationTargetException e) {
         throw new IllegalArgumentException(e.getCause());
      }
      catch (Exception e) {
         throw new IllegalArgumentException(e);
      }
   }

   @SuppressWarnings("unchecked")
   public static <T> List<T> getFieldListValue(final Object target, String name, Class<T> type) {
      return (List<T>) getFieldValue(target, name, List.class);
   }

   private static Field getFieldByNameIncludingSuperclasses(String fieldName, Class<?> clazz) {
      Field retValue = null;

      try {
         retValue = clazz.getDeclaredField(fieldName);
      }
      catch (NoSuchFieldException e) {
         Class<?> superclass = clazz.getSuperclass();

         if (superclass != null) {
            retValue = getFieldByNameIncludingSuperclasses(fieldName, superclass);
         }
      }

      return retValue;
   }
}
