/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;


/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Project</b></em>'. <!-- end-user-doc -->
 * 
 * 
 * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getProject()
 * @model abstract="true"
 * @generated
 */
public interface Project extends FileContainer, Derivable, Annotateable, Identifiable
{
   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model kind="operation"
    * @generated
    */
   ProjectFacet<? extends Project> getParent();

} // Project
