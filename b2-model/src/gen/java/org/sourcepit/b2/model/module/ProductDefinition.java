/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import java.io.File;

import org.sourcepit.common.modeling.Annotatable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Product Definition</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.ProductDefinition#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.ProductDefinition#getFile <em>File</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.ProductDefinition#getProductPlugin <em>Product Plugin</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getProductDefinition()
 * @model
 * @generated
 */
public interface ProductDefinition extends Annotatable, Derivable
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference.
    * It is bidirectional and its opposite is '{@link org.sourcepit.b2.model.module.ProductsFacet#getProductDefinitions
    * <em>Product Definitions</em>}'.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(ProductsFacet)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getProductDefinition_Parent()
    * @see org.sourcepit.b2.model.module.ProductsFacet#getProductDefinitions
    * @model opposite="productDefinitions" required="true" transient="false"
    * @generated
    */
   ProductsFacet getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.ProductDefinition#getParent <em>Parent</em>}'
    * container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(ProductsFacet value);

   /**
    * Returns the value of the '<em><b>File</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>File</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>File</em>' attribute.
    * @see #setFile(File)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getProductDefinition_File()
    * @model dataType="org.sourcepit.modeling.common.EFile" required="true"
    * @generated
    */
   File getFile();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.ProductDefinition#getFile <em>File</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>File</em>' attribute.
    * @see #getFile()
    * @generated
    */
   void setFile(File value);

   /**
    * Returns the value of the '<em><b>Product Plugin</b></em>' containment reference.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Product Plugin</em>' containment reference isn't clear, there really should be more of
    * a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Product Plugin</em>' containment reference.
    * @see #setProductPlugin(StrictReference)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getProductDefinition_ProductPlugin()
    * @model containment="true" resolveProxies="true" required="true"
    * @generated
    */
   StrictReference getProductPlugin();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.ProductDefinition#getProductPlugin
    * <em>Product Plugin</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Product Plugin</em>' containment reference.
    * @see #getProductPlugin()
    * @generated
    */
   void setProductPlugin(StrictReference value);

} // ProductDefinition
