/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;


/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Category</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.Category#getFeatureReferences <em>Feature References</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.Category#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getCategory()
 * @model
 * @generated
 */
public interface Category extends EObject
{

   /**
    * Returns the value of the '<em><b>Feature References</b></em>' containment reference list. The list contents are of
    * type {@link org.sourcepit.beef.b2.model.module.Reference}. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Feature References</em>' containment reference list isn't clear, there really should be
    * more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Feature References</em>' containment reference list.
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getCategory_FeatureReferences()
    * @model containment="true"
    * @generated
    */
   EList<Reference> getFeatureReferences();

   /**
    * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Name</em>' attribute.
    * @see #setName(String)
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getCategory_Name()
    * @model required="true"
    * @generated
    */
   String getName();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.Category#getName <em>Name</em>}' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Name</em>' attribute.
    * @see #getName()
    * @generated
    */
   void setName(String value);
} // Category
