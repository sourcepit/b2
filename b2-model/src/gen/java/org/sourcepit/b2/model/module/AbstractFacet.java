/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.sourcepit.b2.model.common.Annotatable;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Abstract Facet</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.AbstractFacet#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.AbstractFacet#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractFacet()
 * @model abstract="true"
 * @generated
 */
public interface AbstractFacet extends Derivable, Annotatable
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.b2.model.module.AbstractModule#getFacets <em>Facets</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(AbstractModule)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractFacet_Parent()
    * @see org.sourcepit.b2.model.module.AbstractModule#getFacets
    * @model opposite="facets" required="true" transient="false"
    * @generated
    */
   AbstractModule getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.AbstractFacet#getParent <em>Parent</em>}' container
    * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(AbstractModule value);

   /**
    * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Name</em>' attribute.
    * @see #setName(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractFacet_Name()
    * @model required="true"
    * @generated
    */
   String getName();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.AbstractFacet#getName <em>Name</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Name</em>' attribute.
    * @see #getName()
    * @generated
    */
   void setName(String value);

} // AbstractFacet
