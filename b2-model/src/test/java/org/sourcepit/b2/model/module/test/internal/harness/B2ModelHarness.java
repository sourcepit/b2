/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.test.internal.harness;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.Derivable;

public final class B2ModelHarness
{
   private B2ModelHarness()
   {
      super();
   }

   public static void assertHasNoDerivedElements(EObject container)
   {
      assertFalse(hasDerivedElements(container));
   }

   public static void assertHasDerivedElements(EObject container)
   {
      assertTrue(hasDerivedElements(container));
   }

   private static boolean hasDerivedElements(EObject container)
   {
      TreeIterator<EObject> it = container.eAllContents();
      while (it.hasNext())
      {
         EObject eObject = (EObject) it.next();
         if (eObject instanceof Derivable)
         {
            if (((Derivable) eObject).isDerived())
            {
               return true;
            }
         }
      }
      return false;
   }
}
