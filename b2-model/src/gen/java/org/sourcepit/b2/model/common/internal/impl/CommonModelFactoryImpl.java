/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.b2.model.common.internal.impl;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.common.CommonModelFactory;
import org.sourcepit.b2.model.common.CommonModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class CommonModelFactoryImpl extends EFactoryImpl implements CommonModelFactory
{
   /**
    * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated NOT
    */
   public static CommonModelFactory init()
   {
      try
      {
         CommonModelFactory theCommonFactory = (CommonModelFactory) EPackage.Registry.INSTANCE
            .getEFactory(CommonModelPackage.eNS_URI);
         if (theCommonFactory != null)
         {
            return theCommonFactory;
         }
      }
      catch (Exception exception)
      {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new CCommonModelFactoryImpl();
   }

   /**
    * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public CommonModelFactoryImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public EObject create(EClass eClass)
   {
      switch (eClass.getClassifierID())
      {
         case CommonModelPackage.ANNOTATION :
            return createAnnotation();
         case CommonModelPackage.ESTRING_MAP_ENTRY :
            return (EObject) createEStringMapEntry();
         default :
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public Object createFromString(EDataType eDataType, String initialValue)
   {
      switch (eDataType.getClassifierID())
      {
         case CommonModelPackage.EJAVA_FILE :
            return createEJavaFileFromString(eDataType, initialValue);
         case CommonModelPackage.ELOCALE :
            return createELocaleFromString(eDataType, initialValue);
         default :
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public String convertToString(EDataType eDataType, Object instanceValue)
   {
      switch (eDataType.getClassifierID())
      {
         case CommonModelPackage.EJAVA_FILE :
            return convertEJavaFileToString(eDataType, instanceValue);
         case CommonModelPackage.ELOCALE :
            return convertELocaleToString(eDataType, instanceValue);
         default :
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Annotation createAnnotation()
   {
      AnnotationImpl annotation = new AnnotationImpl();
      return annotation;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Map.Entry<String, String> createEStringMapEntry()
   {
      EStringMapEntryImpl eStringMapEntry = new EStringMapEntryImpl();
      return eStringMapEntry;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public File createEJavaFileFromString(EDataType eDataType, String initialValue)
   {
      return (File) super.createFromString(eDataType, initialValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String convertEJavaFileToString(EDataType eDataType, Object instanceValue)
   {
      return super.convertToString(eDataType, instanceValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Locale createELocaleFromString(EDataType eDataType, String initialValue)
   {
      return (Locale) super.createFromString(eDataType, initialValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String convertELocaleToString(EDataType eDataType, Object instanceValue)
   {
      return super.convertToString(eDataType, instanceValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public CommonModelPackage getCommonModelPackage()
   {
      return (CommonModelPackage) getEPackage();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @deprecated
    * @generated
    */
   @Deprecated
   public static CommonModelPackage getPackage()
   {
      return CommonModelPackage.eINSTANCE;
   }

} // CommonFactoryImpl
