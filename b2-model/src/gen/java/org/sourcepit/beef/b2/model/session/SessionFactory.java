/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 * 
 * @see org.sourcepit.beef.b2.model.session.SessionPackage
 * @generated
 */
public interface SessionFactory extends EFactory
{
   /**
    * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   SessionFactory eINSTANCE = org.sourcepit.beef.b2.model.session.internal.impl.SessionFactoryImpl.init();

   /**
    * Returns a new object of class '<em>Session</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Session</em>'.
    * @generated
    */
   Session createSession();

   /**
    * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the package supported by this factory.
    * @generated
    */
   SessionPackage getSessionPackage();

} // SessionFactory
