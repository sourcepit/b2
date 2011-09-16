/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Site Project</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.SiteProject#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.SiteProject#getCategories <em>Categories</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.ModulePackage#getSiteProject()
 * @model
 * @generated
 */
public interface SiteProject extends Project, Classified
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.module.SitesFacet#getProjects <em>Projects</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(SitesFacet)
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getSiteProject_Parent()
    * @see org.sourcepit.beef.b2.model.module.SitesFacet#getProjects
    * @model opposite="projects" transient="false"
    * @generated
    */
   SitesFacet getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.SiteProject#getParent <em>Parent</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(SitesFacet value);

   /**
    * Returns the value of the '<em><b>Categories</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.beef.b2.model.module.Category}. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Categories</em>' containment reference list isn't clear, there really should be more of
    * a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Categories</em>' containment reference list.
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getSiteProject_Categories()
    * @model containment="true"
    * @generated
    */
   EList<Category> getCategories();

} // SiteProject
