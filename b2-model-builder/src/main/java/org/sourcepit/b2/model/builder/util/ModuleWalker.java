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

package org.sourcepit.b2.model.builder.util;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.internal.util.EWalkerImpl;

public abstract class ModuleWalker extends EWalkerImpl {
   public ModuleWalker() {
      super();
   }

   public ModuleWalker(boolean isReverse, boolean isRecursive) {
      super(isReverse, isRecursive);
   }

   @Override
   protected final boolean visit(EObject eObject) {
      if (acceptVisit(eObject)) {
         return doVisit(eObject);
      }
      return false;
   }

   protected boolean acceptVisit(EObject eObject) {
      return true;
   }

   protected abstract boolean doVisit(EObject eObject);

}
