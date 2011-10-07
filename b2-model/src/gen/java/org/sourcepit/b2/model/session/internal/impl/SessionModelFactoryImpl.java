/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.b2.model.session.internal.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.Environment;
import org.sourcepit.b2.model.session.ModuleAttachment;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.b2.model.session.SessionModelPackage;
import org.sourcepit.b2.model.session.internal.impl.CSessionModelFactoryImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class SessionModelFactoryImpl extends EFactoryImpl implements SessionModelFactory
{
   /**
    * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated NOT
    */
   public static SessionModelFactory init()
   {
      try
      {
         SessionModelFactory theSessionFactory = (SessionModelFactory) EPackage.Registry.INSTANCE
            .getEFactory(SessionModelPackage.eNS_URI);
         if (theSessionFactory != null)
         {
            return theSessionFactory;
         }
      }
      catch (Exception exception)
      {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new CSessionModelFactoryImpl();
   }

   /**
    * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SessionModelFactoryImpl()
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
         case SessionModelPackage.B2_SESSION :
            return createB2Session();
         case SessionModelPackage.MODULE_PROJECT :
            return createModuleProject();
         case SessionModelPackage.MODULE_DEPENDENCY :
            return createModuleDependency();
         case SessionModelPackage.MODULE_ATTACHMENT :
            return createModuleAttachment();
         case SessionModelPackage.ENVIRONMENT :
            return createEnvironment();
         default :
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public B2Session createB2Session()
   {
      B2SessionImpl b2Session = new B2SessionImpl();
      return b2Session;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleProject createModuleProject()
   {
      ModuleProjectImpl moduleProject = new ModuleProjectImpl();
      return moduleProject;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleDependency createModuleDependency()
   {
      ModuleDependencyImpl moduleDependency = new ModuleDependencyImpl();
      return moduleDependency;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleAttachment createModuleAttachment()
   {
      ModuleAttachmentImpl moduleAttachment = new ModuleAttachmentImpl();
      return moduleAttachment;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Environment createEnvironment()
   {
      EnvironmentImpl environment = new EnvironmentImpl();
      return environment;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SessionModelPackage getSessionModelPackage()
   {
      return (SessionModelPackage) getEPackage();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @deprecated
    * @generated
    */
   @Deprecated
   public static SessionModelPackage getPackage()
   {
      return SessionModelPackage.eINSTANCE;
   }

} // SessionFactoryImpl