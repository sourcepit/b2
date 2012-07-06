/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Category</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.Category#getFeatureReferences <em>Feature References</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.Category#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getCategory()
 * @model
 * @generated
 */
public interface Category extends EObject
{
   /**
    * Returns the value of the '<em><b>Feature References</b></em>' containment reference list.
    * The list contents are of type {@link org.sourcepit.b2.model.module.Reference}.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Feature References</em>' containment reference list isn't clear, there really should be
    * more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Feature References</em>' containment reference list.
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getCategory_FeatureReferences()
    * @model containment="true" resolveProxies="true"
    * @generated
    */
   EList<Reference> getFeatureReferences();

   /**
    * Returns the value of the '<em><b>Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Name</em>' attribute.
    * @see #setName(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getCategory_Name()
    * @model required="true"
    * @generated
    */
   String getName();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.Category#getName <em>Name</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Name</em>' attribute.
    * @see #getName()
    * @generated
    */
   void setName(String value);

} // Category
