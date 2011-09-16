/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.common;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.sourcepit.beef.b2.model.common.CommonFactory
 * @model kind="package"
 * @generated
 */
public interface CommonPackage extends EPackage
{
   /**
    * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNAME = "common";

   /**
    * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_URI = "http://www.sourcepit.org/b2/model/0.1/common";

   /**
    * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_PREFIX = "common";

   /**
    * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   CommonPackage eINSTANCE = org.sourcepit.beef.b2.model.common.internal.impl.CommonPackageImpl.init();

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.common.internal.impl.EStringMapEntryImpl
    * <em>EString Map Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.common.internal.impl.EStringMapEntryImpl
    * @see org.sourcepit.beef.b2.model.common.internal.impl.CommonPackageImpl#getEStringMapEntry()
    * @generated
    */
   int ESTRING_MAP_ENTRY = 0;

   /**
    * The feature id for the '<em><b>Key</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ESTRING_MAP_ENTRY__KEY = 0;

   /**
    * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ESTRING_MAP_ENTRY__VALUE = 1;

   /**
    * The number of structural features of the '<em>EString Map Entry</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ESTRING_MAP_ENTRY_FEATURE_COUNT = 2;

   /**
    * The meta object id for the '<em>EJava File</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see java.io.File
    * @see org.sourcepit.beef.b2.model.common.internal.impl.CommonPackageImpl#getEJavaFile()
    * @generated
    */
   int EJAVA_FILE = 1;

   /**
    * The meta object id for the '<em>ELocale</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see java.util.Locale
    * @see org.sourcepit.beef.b2.model.common.internal.impl.CommonPackageImpl#getELocale()
    * @generated
    */
   int ELOCALE = 2;


   /**
    * Returns the meta object for class '{@link java.util.Map.Entry <em>EString Map Entry</em>}'. <!-- begin-user-doc
    * --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>EString Map Entry</em>'.
    * @see java.util.Map.Entry
    * @model keyDataType="org.eclipse.emf.ecore.EString" valueDataType="org.eclipse.emf.ecore.EString"
    * @generated
    */
   EClass getEStringMapEntry();

   /**
    * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Key</em>'.
    * @see java.util.Map.Entry
    * @see #getEStringMapEntry()
    * @generated
    */
   EAttribute getEStringMapEntry_Key();

   /**
    * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Value</em>'.
    * @see java.util.Map.Entry
    * @see #getEStringMapEntry()
    * @generated
    */
   EAttribute getEStringMapEntry_Value();

   /**
    * Returns the meta object for data type '{@link java.io.File <em>EJava File</em>}'. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for data type '<em>EJava File</em>'.
    * @see java.io.File
    * @model instanceClass="java.io.File"
    * @generated
    */
   EDataType getEJavaFile();

   /**
    * Returns the meta object for data type '{@link java.util.Locale <em>ELocale</em>}'. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for data type '<em>ELocale</em>'.
    * @see java.util.Locale
    * @model instanceClass="java.util.Locale"
    * @generated
    */
   EDataType getELocale();

   /**
    * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the factory that creates the instances of the model.
    * @generated
    */
   CommonFactory getCommonFactory();

   /**
    * <!-- begin-user-doc --> Defines literals for the meta objects that represent
    * <ul>
    * <li>each class,</li>
    * <li>each feature of each class,</li>
    * <li>each enum,</li>
    * <li>and each data type</li>
    * </ul>
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   interface Literals
   {
      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.common.internal.impl.EStringMapEntryImpl
       * <em>EString Map Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.common.internal.impl.EStringMapEntryImpl
       * @see org.sourcepit.beef.b2.model.common.internal.impl.CommonPackageImpl#getEStringMapEntry()
       * @generated
       */
      EClass ESTRING_MAP_ENTRY = eINSTANCE.getEStringMapEntry();

      /**
       * The meta object literal for the '<em><b>Key</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ESTRING_MAP_ENTRY__KEY = eINSTANCE.getEStringMapEntry_Key();

      /**
       * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ESTRING_MAP_ENTRY__VALUE = eINSTANCE.getEStringMapEntry_Value();

      /**
       * The meta object literal for the '<em>EJava File</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see java.io.File
       * @see org.sourcepit.beef.b2.model.common.internal.impl.CommonPackageImpl#getEJavaFile()
       * @generated
       */
      EDataType EJAVA_FILE = eINSTANCE.getEJavaFile();

      /**
       * The meta object literal for the '<em>ELocale</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see java.util.Locale
       * @see org.sourcepit.beef.b2.model.common.internal.impl.CommonPackageImpl#getELocale()
       * @generated
       */
      EDataType ELOCALE = eINSTANCE.getELocale();

   }

} // CommonPackage
