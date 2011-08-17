/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.internal.test.harness;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.internal.model.Derivable;

public final class B2ModelHarness
{
   private B2ModelHarness()
   {
      super();
   }

   public static void assertHasDerivedElements(EObject container)
   {
      try
      {
         assertHasNoDerivedElements(container);
         Assert.fail();
      }
      catch (AssertionFailedError e)
      {
      }
   }

   public static void assertHasNoDerivedElements(EObject container)
   {
      TreeIterator<EObject> it = container.eAllContents();
      while (it.hasNext())
      {
         EObject eObject = (EObject) it.next();
         if (eObject instanceof Derivable)
         {
            Assert.assertFalse(((Derivable) eObject).isDerived());
         }
      }
   }
}
