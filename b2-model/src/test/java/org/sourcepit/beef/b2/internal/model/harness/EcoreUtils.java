/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.model.harness;

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
