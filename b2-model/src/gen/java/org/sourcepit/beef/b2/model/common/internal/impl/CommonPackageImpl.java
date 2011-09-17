/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.common.internal.impl;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.sourcepit.beef.b2.model.common.Annotateable;
import org.sourcepit.beef.b2.model.common.Annotation;
import org.sourcepit.beef.b2.model.common.CommonFactory;
import org.sourcepit.beef.b2.model.common.CommonPackage;
import org.sourcepit.beef.b2.model.module.ModulePackage;
import org.sourcepit.beef.b2.model.module.internal.impl.ModulePackageImpl;
import org.sourcepit.beef.b2.model.session.SessionPackage;
import org.sourcepit.beef.b2.model.session.internal.impl.SessionPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class CommonPackageImpl extends EPackageImpl implements CommonPackage
{
   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass annotateableEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass annotationEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass eStringMapEntryEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EDataType eJavaFileEDataType = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EDataType eLocaleEDataType = null;

   /**
    * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
    * EPackage.Registry} by the package package URI value.
    * <p>
    * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
    * performs initialization of the package, or returns the registered package, if one already exists. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.eclipse.emf.ecore.EPackage.Registry
    * @see org.sourcepit.beef.b2.model.common.CommonPackage#eNS_URI
    * @see #init()
    * @generated
    */
   private CommonPackageImpl()
   {
      super(eNS_URI, CommonFactory.eINSTANCE);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private static boolean isInited = false;

   /**
    * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
    * 
    * <p>
    * This method is used to initialize {@link CommonPackage#eINSTANCE} when that field is accessed. Clients should not
    * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #eNS_URI
    * @see #createPackageContents()
    * @see #initializePackageContents()
    * @generated
    */
   public static CommonPackage init()
   {
      if (isInited)
         return (CommonPackage) EPackage.Registry.INSTANCE.getEPackage(CommonPackage.eNS_URI);

      // Obtain or create and register package
      CommonPackageImpl theCommonPackage = (CommonPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CommonPackageImpl
         ? EPackage.Registry.INSTANCE.get(eNS_URI)
         : new CommonPackageImpl());

      isInited = true;

      // Obtain or create and register interdependencies
      ModulePackageImpl theModulePackage = (ModulePackageImpl) (EPackage.Registry.INSTANCE
         .getEPackage(ModulePackage.eNS_URI) instanceof ModulePackageImpl ? EPackage.Registry.INSTANCE
         .getEPackage(ModulePackage.eNS_URI) : ModulePackage.eINSTANCE);
      SessionPackageImpl theSessionPackage = (SessionPackageImpl) (EPackage.Registry.INSTANCE
         .getEPackage(SessionPackage.eNS_URI) instanceof SessionPackageImpl ? EPackage.Registry.INSTANCE
         .getEPackage(SessionPackage.eNS_URI) : SessionPackage.eINSTANCE);

      // Create package meta-data objects
      theCommonPackage.createPackageContents();
      theModulePackage.createPackageContents();
      theSessionPackage.createPackageContents();

      // Initialize created meta-data
      theCommonPackage.initializePackageContents();
      theModulePackage.initializePackageContents();
      theSessionPackage.initializePackageContents();

      // Mark meta-data to indicate it can't be changed
      theCommonPackage.freeze();


      // Update the registry and return the package
      EPackage.Registry.INSTANCE.put(CommonPackage.eNS_URI, theCommonPackage);
      return theCommonPackage;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAnnotateable()
   {
      return annotateableEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAnnotateable_Annotations()
   {
      return (EReference) annotateableEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAnnotation()
   {
      return annotationEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAnnotation_Parent()
   {
      return (EReference) annotationEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAnnotation_Source()
   {
      return (EAttribute) annotationEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAnnotation_Entries()
   {
      return (EReference) annotationEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getEStringMapEntry()
   {
      return eStringMapEntryEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getEStringMapEntry_Key()
   {
      return (EAttribute) eStringMapEntryEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getEStringMapEntry_Value()
   {
      return (EAttribute) eStringMapEntryEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EDataType getEJavaFile()
   {
      return eJavaFileEDataType;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EDataType getELocale()
   {
      return eLocaleEDataType;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public CommonFactory getCommonFactory()
   {
      return (CommonFactory) getEFactoryInstance();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private boolean isCreated = false;

   /**
    * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but its
    * first. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void createPackageContents()
   {
      if (isCreated)
         return;
      isCreated = true;

      // Create classes and their features
      annotateableEClass = createEClass(ANNOTATEABLE);
      createEReference(annotateableEClass, ANNOTATEABLE__ANNOTATIONS);

      annotationEClass = createEClass(ANNOTATION);
      createEReference(annotationEClass, ANNOTATION__PARENT);
      createEAttribute(annotationEClass, ANNOTATION__SOURCE);
      createEReference(annotationEClass, ANNOTATION__ENTRIES);

      eStringMapEntryEClass = createEClass(ESTRING_MAP_ENTRY);
      createEAttribute(eStringMapEntryEClass, ESTRING_MAP_ENTRY__KEY);
      createEAttribute(eStringMapEntryEClass, ESTRING_MAP_ENTRY__VALUE);

      // Create data types
      eJavaFileEDataType = createEDataType(EJAVA_FILE);
      eLocaleEDataType = createEDataType(ELOCALE);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private boolean isInitialized = false;

   /**
    * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
    * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void initializePackageContents()
   {
      if (isInitialized)
         return;
      isInitialized = true;

      // Initialize package
      setName(eNAME);
      setNsPrefix(eNS_PREFIX);
      setNsURI(eNS_URI);

      // Create type parameters

      // Set bounds for type parameters

      // Add supertypes to classes

      // Initialize classes and features; add operations and parameters
      initEClass(annotateableEClass, Annotateable.class, "Annotateable", IS_ABSTRACT, IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getAnnotateable_Annotations(), this.getAnnotation(), this.getAnnotation_Parent(), "annotations",
         null, 0, -1, Annotateable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      EOperation op = addEOperation(annotateableEClass, this.getAnnotation(), "getAnnotation", 0, 1, IS_UNIQUE,
         IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "source", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = addEOperation(annotateableEClass, ecorePackage.getEString(), "getAnnotationEntry", 0, 1, IS_UNIQUE,
         IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "source", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "key", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = addEOperation(annotateableEClass, ecorePackage.getEString(), "putAnnotationEntry", 1, 1, IS_UNIQUE,
         IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "source", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "key", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);

      initEClass(annotationEClass, Annotation.class, "Annotation", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getAnnotation_Parent(), this.getAnnotateable(), this.getAnnotateable_Annotations(), "parent",
         null, 1, 1, Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getAnnotation_Source(), ecorePackage.getEString(), "source", null, 1, 1, Annotation.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getAnnotation_Entries(), this.getEStringMapEntry(), null, "entries", null, 0, -1,
         Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(eStringMapEntryEClass, Map.Entry.class, "EStringMapEntry", !IS_ABSTRACT, !IS_INTERFACE,
         !IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getEStringMapEntry_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getEStringMapEntry_Value(), ecorePackage.getEString(), "value", null, 0, 1, Map.Entry.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      // Initialize data types
      initEDataType(eJavaFileEDataType, File.class, "EJavaFile", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
      initEDataType(eLocaleEDataType, Locale.class, "ELocale", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

      // Create resource
      createResource(eNS_URI);
   }

} // CommonPackageImpl
