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

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.junit.Assert;

/**
 * @author Bernd
 */
public final class EcoreUtils
{
   private EcoreUtils()
   {
      super();
   }

   public static void assertEEquals(EObject expected, EObject actual)
   {
      Assert.assertEquals(expected.eClass(), actual.eClass());

      EList<EAttribute> eAttributes = expected.eClass().getEAllAttributes();
      for (EAttribute eAttribute : eAttributes)
      {
         Assert.assertEquals(expected.eGet(eAttribute), actual.eGet(eAttribute));
      }

      EList<EReference> eAllContainments = expected.eClass().getEAllContainments();
      for (EReference eReference : eAllContainments)
      {
         final Object expectedValue = expected.eGet(eReference);
         final Object actualValue = actual.eGet(eReference);
         if (eReference.isMany())
         {
            @SuppressWarnings("unchecked")
            List<EObject> expectedList = (List<EObject>) expectedValue;
            @SuppressWarnings("unchecked")
            List<EObject> actualList = (List<EObject>) actualValue;
            for (int i = 0; i < expectedList.size(); i++)
            {
               assertEEquals(expectedList.get(i), actualList.get(i));
            }
         }
         else if (expectedValue instanceof EObject)
         {
            assertEEquals((EObject) expectedValue, (EObject) actualValue);
         }
         else
         {
            Assert.assertEquals(expectedValue, actualValue);
         }
      }
   }
}
