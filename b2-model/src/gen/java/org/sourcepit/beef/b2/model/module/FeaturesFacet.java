/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Features Facet</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.FeaturesFacet#getProjects <em>Projects</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.ModulePackage#getFeaturesFacet()
 * @model
 * @generated
 */
public interface FeaturesFacet extends ProjectFacet<FeatureProject>
{
   /**
    * Returns the value of the '<em><b>Projects</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.beef.b2.model.module.FeatureProject}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.module.FeatureProject#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Projects</em>' containment reference list isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Projects</em>' containment reference list.
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getFeaturesFacet_Projects()
    * @see org.sourcepit.beef.b2.model.module.FeatureProject#getParent
    * @model opposite="parent" containment="true"
    * @generated
    */
   EList<FeatureProject> getProjects();

} // FeaturesFacet
