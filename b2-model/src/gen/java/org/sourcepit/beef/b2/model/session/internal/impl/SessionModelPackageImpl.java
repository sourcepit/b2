/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session.internal.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.sourcepit.beef.b2.model.common.CommonModelPackage;
import org.sourcepit.beef.b2.model.common.internal.impl.CommonModelPackageImpl;
import org.sourcepit.beef.b2.model.module.ModuleModelPackage;
import org.sourcepit.beef.b2.model.module.internal.impl.ModuleModelPackageImpl;
import org.sourcepit.beef.b2.model.session.B2Session;
import org.sourcepit.beef.b2.model.session.ModuleDependency;
import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.b2.model.session.SessionModelFactory;
import org.sourcepit.beef.b2.model.session.SessionModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class SessionModelPackageImpl extends EPackageImpl implements SessionModelPackage
{
   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass b2SessionEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass moduleProjectEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass moduleDependencyEClass = null;

   /**
    * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
    * EPackage.Registry} by the package package URI value.
    * <p>
    * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
    * performs initialization of the package, or returns the registered package, if one already exists. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.eclipse.emf.ecore.EPackage.Registry
    * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#eNS_URI
    * @see #init()
    * @generated
    */
   private SessionModelPackageImpl()
   {
      super(eNS_URI, SessionModelFactory.eINSTANCE);
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
    * This method is used to initialize {@link SessionModelPackage#eINSTANCE} when that field is accessed. Clients
    * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #eNS_URI
    * @see #createPackageContents()
    * @see #initializePackageContents()
    * @generated
    */
   public static SessionModelPackage init()
   {
      if (isInited)
         return (SessionModelPackage) EPackage.Registry.INSTANCE.getEPackage(SessionModelPackage.eNS_URI);

      // Obtain or create and register package
      SessionModelPackageImpl theSessionModelPackage = (SessionModelPackageImpl) (EPackage.Registry.INSTANCE
         .get(eNS_URI) instanceof SessionModelPackageImpl
         ? EPackage.Registry.INSTANCE.get(eNS_URI)
         : new SessionModelPackageImpl());

      isInited = true;

      // Obtain or create and register interdependencies
      CommonModelPackageImpl theCommonModelPackage = (CommonModelPackageImpl) (EPackage.Registry.INSTANCE
         .getEPackage(CommonModelPackage.eNS_URI) instanceof CommonModelPackageImpl ? EPackage.Registry.INSTANCE
         .getEPackage(CommonModelPackage.eNS_URI) : CommonModelPackage.eINSTANCE);
      ModuleModelPackageImpl theModuleModelPackage = (ModuleModelPackageImpl) (EPackage.Registry.INSTANCE
         .getEPackage(ModuleModelPackage.eNS_URI) instanceof ModuleModelPackageImpl ? EPackage.Registry.INSTANCE
         .getEPackage(ModuleModelPackage.eNS_URI) : ModuleModelPackage.eINSTANCE);

      // Create package meta-data objects
      theSessionModelPackage.createPackageContents();
      theCommonModelPackage.createPackageContents();
      theModuleModelPackage.createPackageContents();

      // Initialize created meta-data
      theSessionModelPackage.initializePackageContents();
      theCommonModelPackage.initializePackageContents();
      theModuleModelPackage.initializePackageContents();

      // Mark meta-data to indicate it can't be changed
      theSessionModelPackage.freeze();


      // Update the registry and return the package
      EPackage.Registry.INSTANCE.put(SessionModelPackage.eNS_URI, theSessionModelPackage);
      return theSessionModelPackage;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getB2Session()
   {
      return b2SessionEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getB2Session_Projects()
   {
      return (EReference) b2SessionEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getB2Session_CurrentProject()
   {
      return (EReference) b2SessionEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getModuleProject()
   {
      return moduleProjectEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getModuleProject_GroupId()
   {
      return (EAttribute) moduleProjectEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getModuleProject_Session()
   {
      return (EReference) moduleProjectEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getModuleProject_ArtifactId()
   {
      return (EAttribute) moduleProjectEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getModuleProject_Version()
   {
      return (EAttribute) moduleProjectEClass.getEStructuralFeatures().get(3);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getModuleProject_Directory()
   {
      return (EAttribute) moduleProjectEClass.getEStructuralFeatures().get(4);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getModuleProject_Dependencies()
   {
      return (EReference) moduleProjectEClass.getEStructuralFeatures().get(5);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getModuleProject_ModuleModel()
   {
      return (EReference) moduleProjectEClass.getEStructuralFeatures().get(6);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getModuleDependency()
   {
      return moduleDependencyEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getModuleDependency_GroupId()
   {
      return (EAttribute) moduleDependencyEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getModuleDependency_ArtifactId()
   {
      return (EAttribute) moduleDependencyEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getModuleDependency_VersionRange()
   {
      return (EAttribute) moduleDependencyEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SessionModelFactory getSessionModelFactory()
   {
      return (SessionModelFactory) getEFactoryInstance();
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
      b2SessionEClass = createEClass(B2_SESSION);
      createEReference(b2SessionEClass, B2_SESSION__PROJECTS);
      createEReference(b2SessionEClass, B2_SESSION__CURRENT_PROJECT);

      moduleProjectEClass = createEClass(MODULE_PROJECT);
      createEAttribute(moduleProjectEClass, MODULE_PROJECT__GROUP_ID);
      createEReference(moduleProjectEClass, MODULE_PROJECT__SESSION);
      createEAttribute(moduleProjectEClass, MODULE_PROJECT__ARTIFACT_ID);
      createEAttribute(moduleProjectEClass, MODULE_PROJECT__VERSION);
      createEAttribute(moduleProjectEClass, MODULE_PROJECT__DIRECTORY);
      createEReference(moduleProjectEClass, MODULE_PROJECT__DEPENDENCIES);
      createEReference(moduleProjectEClass, MODULE_PROJECT__MODULE_MODEL);

      moduleDependencyEClass = createEClass(MODULE_DEPENDENCY);
      createEAttribute(moduleDependencyEClass, MODULE_DEPENDENCY__GROUP_ID);
      createEAttribute(moduleDependencyEClass, MODULE_DEPENDENCY__ARTIFACT_ID);
      createEAttribute(moduleDependencyEClass, MODULE_DEPENDENCY__VERSION_RANGE);
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

      // Obtain other dependent packages
      CommonModelPackage theCommonModelPackage = (CommonModelPackage) EPackage.Registry.INSTANCE
         .getEPackage(CommonModelPackage.eNS_URI);
      ModuleModelPackage theModuleModelPackage = (ModuleModelPackage) EPackage.Registry.INSTANCE
         .getEPackage(ModuleModelPackage.eNS_URI);

      // Create type parameters

      // Set bounds for type parameters

      // Add supertypes to classes
      b2SessionEClass.getESuperTypes().add(theCommonModelPackage.getAnnotateable());
      moduleProjectEClass.getESuperTypes().add(theCommonModelPackage.getAnnotateable());

      // Initialize classes and features; add operations and parameters
      initEClass(b2SessionEClass, B2Session.class, "B2Session", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getB2Session_Projects(), this.getModuleProject(), this.getModuleProject_Session(), "projects",
         null, 0, -1, B2Session.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getB2Session_CurrentProject(), this.getModuleProject(), null, "currentProject", null, 1, 1,
         B2Session.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(moduleProjectEClass, ModuleProject.class, "ModuleProject", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getModuleProject_GroupId(), ecorePackage.getEString(), "groupId", null, 1, 1, ModuleProject.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getModuleProject_Session(), this.getB2Session(), this.getB2Session_Projects(), "session", null, 1,
         1, ModuleProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getModuleProject_ArtifactId(), ecorePackage.getEString(), "artifactId", null, 1, 1,
         ModuleProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getModuleProject_Version(), ecorePackage.getEString(), "version", null, 1, 1, ModuleProject.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getModuleProject_Directory(), theCommonModelPackage.getEJavaFile(), "directory", null, 1, 1,
         ModuleProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEReference(getModuleProject_Dependencies(), this.getModuleDependency(), null, "dependencies", null, 0, -1,
         ModuleProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getModuleProject_ModuleModel(), theModuleModelPackage.getAbstractModule(), null, "moduleModel",
         null, 0, 1, ModuleProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
         IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(moduleDependencyEClass, ModuleDependency.class, "ModuleDependency", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getModuleDependency_GroupId(), ecorePackage.getEString(), "groupId", null, 1, 1,
         ModuleDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getModuleDependency_ArtifactId(), ecorePackage.getEString(), "artifactId", null, 1, 1,
         ModuleDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getModuleDependency_VersionRange(), ecorePackage.getEString(), "versionRange", null, 1, 1,
         ModuleDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      EOperation op = addEOperation(moduleDependencyEClass, ecorePackage.getEBoolean(), "isSatisfiableBy", 0, 1,
         IS_UNIQUE, IS_ORDERED);
      addEParameter(op, this.getModuleProject(), "moduleProject", 0, 1, IS_UNIQUE, IS_ORDERED);

      // Create resource
      createResource(eNS_URI);
   }

} // SessionModelPackageImpl
