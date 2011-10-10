/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Derivable</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.Derivable#isDerived <em>Derived</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getDerivable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Derivable extends EObject
{
   /**
    * Returns the value of the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Derived</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Derived</em>' attribute.
    * @see #setDerived(boolean)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getDerivable_Derived()
    * @model
    * @generated
    */
   boolean isDerived();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.Derivable#isDerived <em>Derived</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Derived</em>' attribute.
    * @see #isDerived()
    * @generated
    */
   void setDerived(boolean value);

} // Derivable
