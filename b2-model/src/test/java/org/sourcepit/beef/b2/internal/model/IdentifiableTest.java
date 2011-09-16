/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.model;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.internal.model.harness.EcoreUtils;
import org.sourcepit.beef.b2.internal.model.harness.EcoreUtils.RunnableWithEObject;
import org.sourcepit.beef.b2.model.module.B2ModelPackage;
import org.sourcepit.beef.b2.model.module.Identifiable;

public class IdentifiableTest extends TestCase
{
   public void testToIdentifier() throws Exception
   {
      EcoreUtils.foreachSupertype(B2ModelPackage.eINSTANCE.getIdentifiable(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            Identifiable identifiable = (Identifiable) eObject;
            try
            {
               identifiable.toIdentifier();
               fail();
            }
            catch (IllegalArgumentException e)
            {
            }

            identifiable.setId("foo");
            assertNotNull(identifiable);

            assertTrue(identifiable.isIdentifyableBy(identifiable.toIdentifier()));
         }
      });
   }
}
