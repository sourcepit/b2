/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.common.internal.impl;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sourcepit.beef.b2.model.common.CommonFactory;
import org.sourcepit.beef.b2.model.common.CommonPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class CommonFactoryImpl extends EFactoryImpl implements CommonFactory
{
   /**
    * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated NOT
    */
   public static CommonFactory init()
   {
      try
      {
         CommonFactory theCommonFactory = (CommonFactory) EPackage.Registry.INSTANCE.getEFactory(CommonPackage.eNS_URI);
         if (theCommonFactory != null)
         {
            return theCommonFactory;
         }
      }
      catch (Exception exception)
      {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new CCommonFactoryImpl();
   }

   /**
    * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public CommonFactoryImpl()
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
         case CommonPackage.ESTRING_MAP_ENTRY :
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
         case CommonPackage.EJAVA_FILE :
            return createEJavaFileFromString(eDataType, initialValue);
         case CommonPackage.ELOCALE :
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
         case CommonPackage.EJAVA_FILE :
            return convertEJavaFileToString(eDataType, instanceValue);
         case CommonPackage.ELOCALE :
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
   public CommonPackage getCommonPackage()
   {
      return (CommonPackage) getEPackage();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @deprecated
    * @generated
    */
   @Deprecated
   public static CommonPackage getPackage()
   {
      return CommonPackage.eINSTANCE;
   }

} // CommonFactoryImpl
