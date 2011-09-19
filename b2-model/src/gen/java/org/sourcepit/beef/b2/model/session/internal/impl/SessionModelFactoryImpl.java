/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session.internal.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.b2.model.session.Session;
import org.sourcepit.beef.b2.model.session.SessionModelFactory;
import org.sourcepit.beef.b2.model.session.SessionModelPackage;

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
         case SessionModelPackage.SESSION :
            return createSession();
         case SessionModelPackage.MODULE_PROJECT :
            return createModuleProject();
         default :
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Session createSession()
   {
      SessionImpl session = new SessionImpl();
      return session;
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
