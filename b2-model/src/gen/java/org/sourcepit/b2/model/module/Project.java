/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.b2.model.module;

import org.sourcepit.b2.model.common.Annotatable;


/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Project</b></em>'. <!-- end-user-doc -->
 * 
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getProject()
 * @model abstract="true"
 * @generated
 */
public interface Project extends FileContainer, Derivable, Annotatable, Identifiable
{
   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model kind="operation"
    * @generated
    */
   ProjectFacet<? extends Project> getParent();

} // Project