/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.builder.util;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.internal.util.EWalkerImpl;

public abstract class DecouplingB2ModelWalker extends EWalkerImpl
{
   protected IModelCache cache;

   public DecouplingB2ModelWalker(IModelCache cache)
   {
      super();
      this.cache = CacheUtils.getNullSafeCache(cache);
   }

   public DecouplingB2ModelWalker(IModelCache cache, boolean isReverse, boolean isRecursive)
   {
      super(isReverse, isRecursive);
      this.cache = CacheUtils.getNullSafeCache(cache);
   }

   @Override
   protected final boolean visit(EObject eObject)
   {
      if (acceptVisit(eObject))
      {
         return doVisit(eObject);
      }
      return false;
   }

   protected boolean acceptVisit(EObject eObject)
   {
      if (eObject instanceof AbstractModule)
      {
         AbstractModule module = (AbstractModule) eObject;
         return cache.get(module.getDirectory()) == null;
      }
      return true;
   }

   protected abstract boolean doVisit(EObject eObject);

}
