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

package org.sourcepit.b2.model.module;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.test.internal.harness.EcoreUtils;
import org.sourcepit.b2.model.module.test.internal.harness.EcoreUtils.RunnableWithEObject;

public class IdentifiableTest extends TestCase
{
   public void testToIdentifier() throws Exception
   {
      EcoreUtils.foreachSupertype(ModuleModelPackage.eINSTANCE.getAbstractIdentifiable(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            AbstractIdentifiable identifiable = (AbstractIdentifiable) eObject;
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
