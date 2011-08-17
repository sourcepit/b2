/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.internal.model;


/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Component Facet</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.internal.model.AbstractFacet#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.internal.model.AbstractFacet#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getAbstractFacet()
 * @model abstract="true"
 * @generated
 */
public interface AbstractFacet extends Derivable, Annotateable
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.internal.model.AbstractModule#getFacets <em>Facets</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(AbstractModule)
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getAbstractFacet_Parent()
    * @see org.sourcepit.beef.b2.internal.model.AbstractModule#getFacets
    * @model opposite="facets" required="true" transient="false"
    * @generated
    */
   AbstractModule getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.internal.model.AbstractFacet#getParent <em>Parent</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(AbstractModule value);

   /**
    * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Type</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Name</em>' attribute.
    * @see #setName(String)
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getAbstractFacet_Name()
    * @model required="true"
    * @generated
    */
   String getName();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.internal.model.AbstractFacet#getName <em>Name</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Name</em>' attribute.
    * @see #getName()
    * @generated
    */
   void setName(String value);

} // ComponentFacet
