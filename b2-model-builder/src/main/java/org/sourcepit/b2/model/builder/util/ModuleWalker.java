/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.internal.util.EWalkerImpl;

public abstract class ModuleWalker extends EWalkerImpl
{
   public ModuleWalker()
   {
      super();
   }

   public ModuleWalker(boolean isReverse, boolean isRecursive)
   {
      super(isReverse, isRecursive);
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
      return true;
   }

   protected abstract boolean doVisit(EObject eObject);

}
