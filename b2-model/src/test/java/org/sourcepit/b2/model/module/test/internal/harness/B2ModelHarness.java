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

package org.sourcepit.b2.model.module.test.internal.harness;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.Derivable;

public final class B2ModelHarness {
   private B2ModelHarness() {
      super();
   }

   public static void assertHasNoDerivedElements(EObject container) {
      assertFalse(hasDerivedElements(container));
   }

   public static void assertHasDerivedElements(EObject container) {
      assertTrue(hasDerivedElements(container));
   }

   private static boolean hasDerivedElements(EObject container) {
      TreeIterator<EObject> it = container.eAllContents();
      while (it.hasNext()) {
         EObject eObject = (EObject) it.next();
         if (eObject instanceof Derivable) {
            if (((Derivable) eObject).isDerived()) {
               return true;
            }
         }
      }
      return false;
   }
}
