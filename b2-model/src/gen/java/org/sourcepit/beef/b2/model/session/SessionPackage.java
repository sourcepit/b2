/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session;

import org.eclipse.emf.ecore.EClass;
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
 * @see org.sourcepit.beef.b2.model.session.SessionFactory
 * @model kind="package"
 * @generated
 */
public interface SessionPackage extends EPackage
{
   /**
    * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNAME = "session";

   /**
    * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_URI = "http://www.sourcepit.org/b2/model/0.1/session";

   /**
    * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_PREFIX = "session";

   /**
    * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   SessionPackage eINSTANCE = org.sourcepit.beef.b2.model.session.internal.impl.SessionPackageImpl.init();

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl <em>Session</em>}
    * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl
    * @see org.sourcepit.beef.b2.model.session.internal.impl.SessionPackageImpl#getSession()
    * @generated
    */
   int SESSION = 0;

   /**
    * The number of structural features of the '<em>Session</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SESSION_FEATURE_COUNT = 0;


   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.model.session.Session <em>Session</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Session</em>'.
    * @see org.sourcepit.beef.b2.model.session.Session
    * @generated
    */
   EClass getSession();

   /**
    * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the factory that creates the instances of the model.
    * @generated
    */
   SessionFactory getSessionFactory();

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
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl
       * <em>Session</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl
       * @see org.sourcepit.beef.b2.model.session.internal.impl.SessionPackageImpl#getSession()
       * @generated
       */
      EClass SESSION = eINSTANCE.getSession();

   }

} // SessionPackage
