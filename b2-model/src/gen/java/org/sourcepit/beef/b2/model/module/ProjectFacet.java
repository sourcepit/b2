/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Project Facet</b></em>'. <!-- end-user-doc -->
 * 
 * 
 * @see org.sourcepit.beef.b2.model.module.ModulePackage#getProjectFacet()
 * @model abstract="true"
 * @generated
 */
public interface ProjectFacet<P extends Project> extends AbstractFacet
{
   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model kind="operation" required="true" many="false"
    * @generated
    */
   EList<P> getProjects();

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   P getProjectById(String name);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model referenceRequired="true"
    * @generated
    */
   P resolveReference(Reference reference);

} // ProjectFacet
