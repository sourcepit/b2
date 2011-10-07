/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.module.internal.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;


public abstract class EWalkerImpl
{
   private boolean isReverse;

   private boolean isRecursive;

   public EWalkerImpl()
   {
      this(false, true);
   }

   public EWalkerImpl(boolean isReverse, boolean isRecursive)
   {
      this.isRecursive = isRecursive;
      this.isReverse = isReverse;
   }

   public boolean isReverse()
   {
      return isReverse;
   }

   public void setReverse(boolean isReverse)
   {
      this.isReverse = isReverse;
   }

   public boolean isRecursive()
   {
      return isRecursive;
   }

   public void setRecursive(boolean isRecursive)
   {
      this.isRecursive = isRecursive;
   }

   public void walk(EObject eObject)
   {
      if (!isReverse)
      {
         if (!visit(eObject))
         {
            return;
         }
      }

      if (isRecursive)
      {
         final EList<EObject> eContents = eObject.eContents();
         final int size = eContents.size();
         for (int i = 0; i < size; i++)
         {
            final int j = isReverse ? size - i - 1 : i;
            walk(eContents.get(j));
         }
      }

      if (isReverse)
      {
         if (!visit(eObject))
         {
            return;
         }
      }
   }

   protected abstract boolean visit(EObject eObject);
}
