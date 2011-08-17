/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.internal.model;

import java.io.File;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Product Definition</b></em>'. <!-- end-user-doc
 * -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.internal.model.ProductDefinition#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.internal.model.ProductDefinition#getFile <em>File</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.internal.model.ProductDefinition#getProductPlugin <em>Product Plugin</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getProductDefinition()
 * @model
 * @generated
 */
public interface ProductDefinition extends Annotateable, Derivable
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.internal.model.ProductsFacet#getProductDefinitions <em>Product Definitions</em>}'.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(ProductsFacet)
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getProductDefinition_Parent()
    * @see org.sourcepit.beef.b2.internal.model.ProductsFacet#getProductDefinitions
    * @model opposite="productDefinitions" required="true" transient="false"
    * @generated
    */
   ProductsFacet getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.internal.model.ProductDefinition#getParent <em>Parent</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(ProductsFacet value);

   /**
    * Returns the value of the '<em><b>File</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>File</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>File</em>' attribute.
    * @see #setFile(File)
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getProductDefinition_File()
    * @model dataType="org.sourcepit.beef.b2.internal.model.EJavaFile" required="true"
    * @generated
    */
   File getFile();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.internal.model.ProductDefinition#getFile <em>File</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>File</em>' attribute.
    * @see #getFile()
    * @generated
    */
   void setFile(File value);

   /**
    * Returns the value of the '<em><b>Product Plugin</b></em>' containment reference. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Product Plugin</em>' containment reference isn't clear, there really should be more of
    * a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Product Plugin</em>' containment reference.
    * @see #setProductPlugin(Reference)
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getProductDefinition_ProductPlugin()
    * @model containment="true" required="true"
    * @generated
    */
   Reference getProductPlugin();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.internal.model.ProductDefinition#getProductPlugin
    * <em>Product Plugin</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Product Plugin</em>' containment reference.
    * @see #getProductPlugin()
    * @generated
    */
   void setProductPlugin(Reference value);

} // ProductDefinition
