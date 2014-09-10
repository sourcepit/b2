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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;


/**
 * @author Bernd
 */
public final class EcoreUtils
{
   private EcoreUtils()
   {
      super();
   }

   public interface RunnableWithEObject
   {
      void run(EObject eObject);
   }

   public static void foreachSupertype(EClass type, RunnableWithEObject runnable)
   {
      List<EClass> superTypes = getConcreteSuperTypes(type);
      for (EClass superType : superTypes)
      {
         EObject instance = superType.getEPackage().getEFactoryInstance().create(superType);
         runnable.run(instance);
      }
   }

   public static List<EClass> getConcreteSuperTypes(EClass type)
   {
      List<EClass> moduleTypes = new ArrayList<EClass>();
      for (EClassifier eClassifier : type.getEPackage().getEClassifiers())
      {
         if (eClassifier instanceof EClass)
         {
            EClass eClass = (EClass) eClassifier;
            if (!eClass.isAbstract() && !eClass.isInterface() && type.isSuperTypeOf(eClass))
            {
               moduleTypes.add(eClass);
            }
         }
      }
      return moduleTypes;
   }

}
