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

package org.sourcepit.b2.model.module.internal.util;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;


public abstract class EWalkerImpl {
   private boolean isReverse;

   private boolean isRecursive;

   public EWalkerImpl() {
      this(false, true);
   }

   public EWalkerImpl(boolean isReverse, boolean isRecursive) {
      this.isRecursive = isRecursive;
      this.isReverse = isReverse;
   }

   public boolean isReverse() {
      return isReverse;
   }

   public void setReverse(boolean isReverse) {
      this.isReverse = isReverse;
   }

   public boolean isRecursive() {
      return isRecursive;
   }

   public void setRecursive(boolean isRecursive) {
      this.isRecursive = isRecursive;
   }

   public void walk(EObject eObject) {
      if (!isReverse) {
         if (!visit(eObject)) {
            return;
         }
      }

      if (isRecursive) {
         walk(getChildren(eObject));
      }

      if (isReverse) {
         if (!visit(eObject)) {
            return;
         }
      }
   }

   public void walk(final List<? extends EObject> eContents) {
      final int size = eContents.size();
      for (int i = 0; i < size; i++) {
         final int j = isReverse ? size - i - 1 : i;
         walk(eContents.get(j));
      }
   }

   protected EList<? extends EObject> getChildren(EObject eObject) {
      return eObject.eContents();
   }

   protected abstract boolean visit(EObject eObject);
}
