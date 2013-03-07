/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Plugin Include</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.PluginInclude#isUnpack <em>Unpack</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginInclude()
 * @model
 * @generated
 */
public interface PluginInclude extends AbstractStrictReference
{
   /**
    * Returns the value of the '<em><b>Unpack</b></em>' attribute.
    * The default value is <code>"true"</code>.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Unpack</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Unpack</em>' attribute.
    * @see #setUnpack(boolean)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginInclude_Unpack()
    * @model default="true"
    * @generated
    */
   boolean isUnpack();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.PluginInclude#isUnpack <em>Unpack</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Unpack</em>' attribute.
    * @see #isUnpack()
    * @generated
    */
   void setUnpack(boolean value);

} // PluginInclude
