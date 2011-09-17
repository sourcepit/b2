/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.model.common;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.sourcepit.beef.b2.model.module.ModulePackage;
import org.sourcepit.beef.b2.model.session.SessionPackage;

public class AnnotateableTest extends TestCase
{
   public void testGetAnnotation() throws Exception
   {
      List<EClass> annotateableTypes = getAnnotateableTypes();
      for (EClass eClass : annotateableTypes)
      {
         Annotateable annotateable = createAnnotateable(eClass);

         try
         {
            annotateable.getAnnotation(null);
            fail("Must throw IllegalArgumentException");
         }
         catch (IllegalArgumentException e)
         {
         }
         catch (UnsupportedOperationException e)
         {
            fail("Operation getAnnotation(source) not supported by type " + annotateable.getClass().getSimpleName());
         }

         assertNull(annotateable.getAnnotation("foo"));

         Annotation a = CommonFactory.eINSTANCE.createAnnotation();
         a.setSource(annotateable.getClass().getName());

         annotateable.getAnnotations().add(a);

         Annotation aa = annotateable.getAnnotation(annotateable.getClass().getName());
         assertNotNull(aa);
         assertSame(a, aa);

         Annotation aaa = CommonFactory.eINSTANCE.createAnnotation();
         aaa.setSource(annotateable.getClass().getName() + "2");

         annotateable.getAnnotations().add(aaa);

         aa = annotateable.getAnnotation(annotateable.getClass().getName());
         assertNotNull(aa);
         assertSame(a, aa);
         assertNotSame(aa, aaa);

         Annotation aaaa = annotateable.getAnnotation(annotateable.getClass().getName() + "2");
         assertNotNull(aaaa);
         assertSame(aaa, aaaa);
         assertNotSame(aa, aaaa);

         assertNull(annotateable.getAnnotation("foo"));
      }
   }

   private Annotateable createAnnotateable(EClass eClass)
   {
      Annotateable annotateable = (Annotateable) eClass.getEPackage().getEFactoryInstance().create(eClass);
      assertNotNull(annotateable);
      return annotateable;
   }

   public void testGetAnnotationEntry() throws Exception
   {
      List<EClass> annotateableTypes = getAnnotateableTypes();
      for (EClass eClass : annotateableTypes)
      {
         Annotateable annotateable = createAnnotateable(eClass);
         try
         {
            annotateable.getAnnotationEntry(null, null);
            fail("Must throw IllegalArgumentException");
         }
         catch (IllegalArgumentException e)
         {
         }
         catch (UnsupportedOperationException e)
         {
            fail("Operation getAnnotation(source) not supported by type " + annotateable.getClass().getSimpleName());
         }

         try
         {
            annotateable.getAnnotationEntry("source", null);
            fail("Must throw IllegalArgumentException");
         }
         catch (IllegalArgumentException e)
         {
         }

         assertNull(annotateable.getAnnotationEntry("source", "key"));

         Annotation a = CommonFactory.eINSTANCE.createAnnotation();
         a.setSource("source");
         annotateable.getAnnotations().add(a);

         assertNull(annotateable.getAnnotationEntry("source", "key"));

         a.getEntries().put("key", "value");

         assertEquals("value", annotateable.getAnnotationEntry("source", "key"));
      }
   }

   public void testPutAnnptationEntry() throws Exception
   {
      List<EClass> annotateableTypes = getAnnotateableTypes();
      for (EClass eClass : annotateableTypes)
      {
         Annotateable annotateable = createAnnotateable(eClass);
         try
         {
            annotateable.putAnnotationEntry(null, null, null);
            fail("Must throw IllegalArgumentException");
         }
         catch (IllegalArgumentException e)
         {
         }
         catch (UnsupportedOperationException e)
         {
            fail("Operation getAnnotation(source) not supported by type " + annotateable.getClass().getSimpleName());
         }

         assertNull(annotateable.putAnnotationEntry("source", "key", "value"));
         assertEquals("value", annotateable.putAnnotationEntry("source", "key", "newValue"));
      }
   }

   private List<EClass> getAnnotateableTypes()
   {
      EClass annotateableType = CommonPackage.eINSTANCE.getAnnotateable();
      List<EClass> annotateableTypes = new ArrayList<EClass>();
      for (EClassifier eClassifier : getAllEClassifiers())
      {
         if (eClassifier instanceof EClass)
         {
            EClass eClass = (EClass) eClassifier;
            if (!eClass.isAbstract() && !eClass.isInterface() && annotateableType.isSuperTypeOf(eClass))
            {
               annotateableTypes.add(eClass);
            }
         }
      }
      assertFalse(annotateableTypes.isEmpty());
      return annotateableTypes;
   }

   private EList<EClassifier> getAllEClassifiers()
   {
      EList<EClassifier> classifiers = new BasicEList<EClassifier>();
      classifiers.addAll(CommonPackage.eINSTANCE.getEClassifiers());
      classifiers.addAll(ModulePackage.eINSTANCE.getEClassifiers());
      classifiers.addAll(SessionPackage.eINSTANCE.getEClassifiers());
      return classifiers;
   }
}
