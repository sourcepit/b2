/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Package</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.CompositeModule#getModules <em>Modules</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.ModulePackage#getCompositeModule()
 * @model
 * @generated
 */
public interface CompositeModule extends AbstractModule
{
   /**
    * Returns the value of the '<em><b>Modules</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.beef.b2.model.module.AbstractModule}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.module.AbstractModule#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Modules</em>' containment reference list isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Modules</em>' containment reference list.
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getCompositeModule_Modules()
    * @see org.sourcepit.beef.b2.model.module.AbstractModule#getParent
    * @model opposite="parent" containment="true"
    * @generated
    */
   EList<AbstractModule> getModules();

} // ModulePackage
