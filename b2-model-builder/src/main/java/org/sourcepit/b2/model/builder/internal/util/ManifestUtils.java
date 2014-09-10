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

package org.sourcepit.b2.model.builder.internal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.apache.commons.io.IOUtils;

public final class ManifestUtils
{
   private ManifestUtils()
   {
      super();
   }

   public static Manifest createOrderedManifest()
   {
      final Manifest mf = new Manifest();
      final Class<? extends Manifest> mfClass = mf.getClass();

      try
      {
         setField(mfClass, "attr", mf, new OrderedAttributes());
         setField(mfClass, "entries", mf, new LinkedHashMap<Object, Object>());
      }
      catch (NoSuchFieldException e1)
      { // hack the planet
      }
      catch (IllegalAccessException e1)
      { // hack the planet
      }

      return mf;
   }

   private static void setField(Class<?> clazz, String name, Object obj, Object value) throws NoSuchFieldException,
      IllegalAccessException
   {
      final Field field = clazz.getDeclaredField(name);
      boolean accessible = field.isAccessible();
      try
      {
         field.setAccessible(true);
      }
      catch (SecurityException e)
      { // swallow
      }
      try
      {
         field.set(obj, value);
      }
      finally
      {
         try
         {
            field.setAccessible(accessible);
         }
         catch (SecurityException e)
         { // swallow
         }
      }
   }

   private static class OrderedAttributes extends Attributes
   {
      public OrderedAttributes()
      {
         this(11);
      }

      public OrderedAttributes(int size)
      {
         map = new LinkedHashMap<Object, Object>(size);
      }

      public OrderedAttributes(Attributes attr)
      {
         map = new LinkedHashMap<Object, Object>(attr);
      }

      public Object clone()
      {
         return new OrderedAttributes(this);
      }
   }

   public static Manifest readManifest(File manifestFile) throws IllegalArgumentException
   {
      InputStream in = null;
      try
      {
         in = new FileInputStream(manifestFile);

         final Manifest manifest = createOrderedManifest();
         manifest.read(in);
         return manifest;
      }
      catch (IOException e)
      {
         throw new IllegalArgumentException(e);
      }
      finally
      {
         IOUtils.closeQuietly(in);
      }
   }

   public static String getBundleVersion(final Manifest manifest)
   {
      return getAttributeValue(manifest, "Bundle-Version");
   }

   public static String getBundleSymbolicName(final Manifest manifest)
   {
      return getAttributeValue(manifest, "Bundle-SymbolicName");
   }

   public static String getAttributeValue(Manifest manifest, String key)
   {
      String result = manifest.getMainAttributes().getValue(key);
      if (result != null)
      {
         int sepIdx = result.indexOf(";");
         if (sepIdx > -1)
         {
            result = result.substring(0, sepIdx);
         }
      }
      return result;
   }
}
