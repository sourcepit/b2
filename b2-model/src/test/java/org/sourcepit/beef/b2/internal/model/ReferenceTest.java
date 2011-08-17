/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.model;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.internal.model.harness.EcoreUtils;
import org.sourcepit.beef.b2.internal.model.harness.EcoreUtils.RunnableWithEObject;

public class ReferenceTest extends TestCase
{
   public void testIsSatisfying() throws Exception
   {
      EcoreUtils.foreachSupertype(B2ModelPackage.eINSTANCE.getReference(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            Reference module = (Reference) eObject;
            try
            {
               module.isSatisfiableBy(null);
               fail();
            }
            catch (IllegalArgumentException e)
            {
            }
         }
      });
   }

   public void testSetStrictVersion() throws Exception
   {
      EcoreUtils.foreachSupertype(B2ModelPackage.eINSTANCE.getReference(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            Reference reference = (Reference) eObject;
            try
            {
               reference.setStrictVersion(null);
               fail();
            }
            catch (NullPointerException e)
            {
            }

            try
            {
               reference.setStrictVersion("[1.0.0,2.0.0)");
               fail();
            }
            catch (IllegalArgumentException e)
            {
            }

            reference.setStrictVersion("1.0.0");
            assertEquals("[1.0.0,1.0.0]", reference.getVersionRange());
         }
      });
   }
}
