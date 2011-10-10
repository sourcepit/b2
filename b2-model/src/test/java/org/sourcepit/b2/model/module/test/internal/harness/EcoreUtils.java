/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
