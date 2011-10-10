/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;

import org.sourcepit.b2.model.module.AbstractModule;

public final class CacheUtils
{
   public static final IModelCache NULL_CACHE = new IModelCache()
   {
      public AbstractModule get(File moduleDir)
      {
         return null;
      }
   };

   private CacheUtils()
   {
      super();
   }

   public static IModelCache getNullSafeCache(IModelCache cache)
   {
      return cache == null ? NULL_CACHE : cache;
   }

   public static AbstractModule get(IModelCache cache, File moduleDir)
   {
      return getNullSafeCache(cache).get(moduleDir);
   }
}
