/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.common;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.sourcepit.b2.model.common.CommonModelFactory
 * @model kind="package"
 * @generated
 */
public interface CommonModelPackage extends EPackage
{
   /**
    * The package name.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNAME = "common";

   /**
    * The package namespace URI.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_URI = "http://www.sourcepit.org/b2/model/0.1/common";

   /**
    * The package namespace name.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_PREFIX = "common";

   /**
    * The singleton instance of the package.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   CommonModelPackage eINSTANCE = org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl.init();

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.common.Annotatable <em>Annotatable</em>}' class. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.common.Annotatable
    * @see org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl#getAnnotatable()
    * @generated
    */
   int ANNOTATABLE = 0;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATABLE__ANNOTATIONS = 0;

   /**
    * The number of structural features of the '<em>Annotatable</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATABLE_FEATURE_COUNT = 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.common.internal.impl.AnnotationImpl <em>Annotation</em>}
    * ' class.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.common.internal.impl.AnnotationImpl
    * @see org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl#getAnnotation()
    * @generated
    */
   int ANNOTATION = 1;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATION__PARENT = 0;

   /**
    * The feature id for the '<em><b>Source</b></em>' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATION__SOURCE = 1;

   /**
    * The feature id for the '<em><b>Entries</b></em>' map.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATION__ENTRIES = 2;

   /**
    * The number of structural features of the '<em>Annotation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATION_FEATURE_COUNT = 3;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.common.internal.impl.EStringMapEntryImpl
    * <em>EString Map Entry</em>}' class.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.common.internal.impl.EStringMapEntryImpl
    * @see org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl#getEStringMapEntry()
    * @generated
    */
   int ESTRING_MAP_ENTRY = 2;

   /**
    * The feature id for the '<em><b>Key</b></em>' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ESTRING_MAP_ENTRY__KEY = 0;

   /**
    * The feature id for the '<em><b>Value</b></em>' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ESTRING_MAP_ENTRY__VALUE = 1;

   /**
    * The number of structural features of the '<em>EString Map Entry</em>' class.
    * <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ESTRING_MAP_ENTRY_FEATURE_COUNT = 2;

   /**
    * The meta object id for the '<em>EJava File</em>' data type.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see java.io.File
    * @see org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl#getEJavaFile()
    * @generated
    */
   int EJAVA_FILE = 3;

   /**
    * The meta object id for the '<em>ELocale</em>' data type.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see java.util.Locale
    * @see org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl#getELocale()
    * @generated
    */
   int ELOCALE = 4;


   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.common.Annotatable <em>Annotatable</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Annotatable</em>'.
    * @see org.sourcepit.b2.model.common.Annotatable
    * @generated
    */
   EClass getAnnotatable();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.common.Annotatable#getAnnotations <em>Annotations</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Annotations</em>'.
    * @see org.sourcepit.b2.model.common.Annotatable#getAnnotations()
    * @see #getAnnotatable()
    * @generated
    */
   EReference getAnnotatable_Annotations();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.common.Annotation <em>Annotation</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Annotation</em>'.
    * @see org.sourcepit.b2.model.common.Annotation
    * @generated
    */
   EClass getAnnotation();

   /**
    * Returns the meta object for the container reference '{@link org.sourcepit.b2.model.common.Annotation#getParent
    * <em>Parent</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.b2.model.common.Annotation#getParent()
    * @see #getAnnotation()
    * @generated
    */
   EReference getAnnotation_Parent();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.common.Annotation#getSource
    * <em>Source</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Source</em>'.
    * @see org.sourcepit.b2.model.common.Annotation#getSource()
    * @see #getAnnotation()
    * @generated
    */
   EAttribute getAnnotation_Source();

   /**
    * Returns the meta object for the map '{@link org.sourcepit.b2.model.common.Annotation#getEntries <em>Entries</em>}
    * '.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the map '<em>Entries</em>'.
    * @see org.sourcepit.b2.model.common.Annotation#getEntries()
    * @see #getAnnotation()
    * @generated
    */
   EReference getAnnotation_Entries();

   /**
    * Returns the meta object for class '{@link java.util.Map.Entry <em>EString Map Entry</em>}'.
    * <!-- begin-user-doc
    * --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>EString Map Entry</em>'.
    * @see java.util.Map.Entry
    * @model keyDataType="org.eclipse.emf.ecore.EString"
    *        valueDataType="org.eclipse.emf.ecore.EString"
    * @generated
    */
   EClass getEStringMapEntry();

   /**
    * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
    * <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Key</em>'.
    * @see java.util.Map.Entry
    * @see #getEStringMapEntry()
    * @generated
    */
   EAttribute getEStringMapEntry_Key();

   /**
    * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Value</em>'.
    * @see java.util.Map.Entry
    * @see #getEStringMapEntry()
    * @generated
    */
   EAttribute getEStringMapEntry_Value();

   /**
    * Returns the meta object for data type '{@link java.io.File <em>EJava File</em>}'.
    * <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for data type '<em>EJava File</em>'.
    * @see java.io.File
    * @model instanceClass="java.io.File"
    * @generated
    */
   EDataType getEJavaFile();

   /**
    * Returns the meta object for data type '{@link java.util.Locale <em>ELocale</em>}'.
    * <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for data type '<em>ELocale</em>'.
    * @see java.util.Locale
    * @model instanceClass="java.util.Locale"
    * @generated
    */
   EDataType getELocale();

   /**
    * Returns the factory that creates the instances of the model.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the factory that creates the instances of the model.
    * @generated
    */
   CommonModelFactory getCommonModelFactory();

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
       * The meta object literal for the '{@link org.sourcepit.b2.model.common.Annotatable <em>Annotatable</em>}' class.
       * <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.common.Annotatable
       * @see org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl#getAnnotatable()
       * @generated
       */
      EClass ANNOTATABLE = eINSTANCE.getAnnotatable();

      /**
       * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference ANNOTATABLE__ANNOTATIONS = eINSTANCE.getAnnotatable_Annotations();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.common.internal.impl.AnnotationImpl
       * <em>Annotation</em>}' class.
       * <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.common.internal.impl.AnnotationImpl
       * @see org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl#getAnnotation()
       * @generated
       */
      EClass ANNOTATION = eINSTANCE.getAnnotation();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference ANNOTATION__PARENT = eINSTANCE.getAnnotation_Parent();

      /**
       * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
       * <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ANNOTATION__SOURCE = eINSTANCE.getAnnotation_Source();

      /**
       * The meta object literal for the '<em><b>Entries</b></em>' map feature.
       * <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EReference ANNOTATION__ENTRIES = eINSTANCE.getAnnotation_Entries();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.common.internal.impl.EStringMapEntryImpl
       * <em>EString Map Entry</em>}' class.
       * <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.common.internal.impl.EStringMapEntryImpl
       * @see org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl#getEStringMapEntry()
       * @generated
       */
      EClass ESTRING_MAP_ENTRY = eINSTANCE.getEStringMapEntry();

      /**
       * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
       * <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ESTRING_MAP_ENTRY__KEY = eINSTANCE.getEStringMapEntry_Key();

      /**
       * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
       * <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ESTRING_MAP_ENTRY__VALUE = eINSTANCE.getEStringMapEntry_Value();

      /**
       * The meta object literal for the '<em>EJava File</em>' data type.
       * <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see java.io.File
       * @see org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl#getEJavaFile()
       * @generated
       */
      EDataType EJAVA_FILE = eINSTANCE.getEJavaFile();

      /**
       * The meta object literal for the '<em>ELocale</em>' data type.
       * <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see java.util.Locale
       * @see org.sourcepit.b2.model.common.internal.impl.CommonModelPackageImpl#getELocale()
       * @generated
       */
      EDataType ELOCALE = eINSTANCE.getELocale();

   }

} // CommonModelPackage
