/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session.internal.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.sourcepit.beef.b2.model.common.CommonPackage;
import org.sourcepit.beef.b2.model.common.internal.impl.CommonPackageImpl;
import org.sourcepit.beef.b2.model.module.ModulePackage;
import org.sourcepit.beef.b2.model.module.internal.impl.ModulePackageImpl;
import org.sourcepit.beef.b2.model.session.Session;
import org.sourcepit.beef.b2.model.session.SessionFactory;
import org.sourcepit.beef.b2.model.session.SessionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class SessionPackageImpl extends EPackageImpl implements SessionPackage
{
   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass sessionEClass = null;

   /**
    * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
    * EPackage.Registry} by the package package URI value.
    * <p>
    * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
    * performs initialization of the package, or returns the registered package, if one already exists. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.eclipse.emf.ecore.EPackage.Registry
    * @see org.sourcepit.beef.b2.model.session.SessionPackage#eNS_URI
    * @see #init()
    * @generated
    */
   private SessionPackageImpl()
   {
      super(eNS_URI, SessionFactory.eINSTANCE);
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
    * This method is used to initialize {@link SessionPackage#eINSTANCE} when that field is accessed. Clients should not
    * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #eNS_URI
    * @see #createPackageContents()
    * @see #initializePackageContents()
    * @generated
    */
   public static SessionPackage init()
   {
      if (isInited)
         return (SessionPackage) EPackage.Registry.INSTANCE.getEPackage(SessionPackage.eNS_URI);

      // Obtain or create and register package
      SessionPackageImpl theSessionPackage = (SessionPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SessionPackageImpl
         ? EPackage.Registry.INSTANCE.get(eNS_URI)
         : new SessionPackageImpl());

      isInited = true;

      // Obtain or create and register interdependencies
      CommonPackageImpl theCommonPackage = (CommonPackageImpl) (EPackage.Registry.INSTANCE
         .getEPackage(CommonPackage.eNS_URI) instanceof CommonPackageImpl ? EPackage.Registry.INSTANCE
         .getEPackage(CommonPackage.eNS_URI) : CommonPackage.eINSTANCE);
      ModulePackageImpl theModulePackage = (ModulePackageImpl) (EPackage.Registry.INSTANCE
         .getEPackage(ModulePackage.eNS_URI) instanceof ModulePackageImpl ? EPackage.Registry.INSTANCE
         .getEPackage(ModulePackage.eNS_URI) : ModulePackage.eINSTANCE);

      // Create package meta-data objects
      theSessionPackage.createPackageContents();
      theCommonPackage.createPackageContents();
      theModulePackage.createPackageContents();

      // Initialize created meta-data
      theSessionPackage.initializePackageContents();
      theCommonPackage.initializePackageContents();
      theModulePackage.initializePackageContents();

      // Mark meta-data to indicate it can't be changed
      theSessionPackage.freeze();


      // Update the registry and return the package
      EPackage.Registry.INSTANCE.put(SessionPackage.eNS_URI, theSessionPackage);
      return theSessionPackage;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getSession()
   {
      return sessionEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SessionFactory getSessionFactory()
   {
      return (SessionFactory) getEFactoryInstance();
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
      sessionEClass = createEClass(SESSION);
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
      initEClass(sessionEClass, Session.class, "Session", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

      // Create resource
      createResource(eNS_URI);
   }

} // SessionPackageImpl
