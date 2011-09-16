/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.common;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 * 
 * @see org.sourcepit.beef.b2.model.common.CommonPackage
 * @generated
 */
public interface CommonFactory extends EFactory
{
   /**
    * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   CommonFactory eINSTANCE = org.sourcepit.beef.b2.model.common.internal.impl.CommonFactoryImpl.init();

   /**
    * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the package supported by this factory.
    * @generated
    */
   CommonPackage getCommonPackage();

} // CommonFactory
