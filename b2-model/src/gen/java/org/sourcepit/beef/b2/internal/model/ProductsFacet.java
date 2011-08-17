/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.internal.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Products Facet</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.internal.model.ProductsFacet#getProductDefinitions <em>Product Definitions</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getProductsFacet()
 * @model
 * @generated
 */
public interface ProductsFacet extends AbstractFacet
{
   /**
    * Returns the value of the '<em><b>Product Definitions</b></em>' containment reference list. The list contents are
    * of type {@link org.sourcepit.beef.b2.internal.model.ProductDefinition}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.internal.model.ProductDefinition#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Product Definitions</em>' containment reference list isn't clear, there really should
    * be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Product Definitions</em>' containment reference list.
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getProductsFacet_ProductDefinitions()
    * @see org.sourcepit.beef.b2.internal.model.ProductDefinition#getParent
    * @model opposite="parent" containment="true"
    * @generated
    */
   EList<ProductDefinition> getProductDefinitions();

} // ProductsFacet
