/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.sourcepit.modeling.common.Annotatable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Reference</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.AbstractReference#getId <em>Id</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.AbstractReference#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractReference()
 * @model abstract="true"
 * @generated
 */
public interface AbstractReference extends Annotatable
{
   /**
    * Returns the value of the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Id</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Id</em>' attribute.
    * @see #setId(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractReference_Id()
    * @model required="true"
    * @generated
    */
   String getId();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.AbstractReference#getId <em>Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Id</em>' attribute.
    * @see #getId()
    * @generated
    */
   void setId(String value);

   /**
    * Returns the value of the '<em><b>Version</b></em>' attribute.
    * The default value is <code>"0.0.0"</code>.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Version</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Version</em>' attribute.
    * @see #isSetVersion()
    * @see #unsetVersion()
    * @see #setVersion(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractReference_Version()
    * @model default="0.0.0" unsettable="true"
    * @generated
    */
   String getVersion();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.AbstractReference#getVersion <em>Version</em>}'
    * attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Version</em>' attribute.
    * @see #isSetVersion()
    * @see #unsetVersion()
    * @see #getVersion()
    * @generated
    */
   void setVersion(String value);

   /**
    * Unsets the value of the '{@link org.sourcepit.b2.model.module.AbstractReference#getVersion <em>Version</em>}'
    * attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #isSetVersion()
    * @see #getVersion()
    * @see #setVersion(String)
    * @generated
    */
   void unsetVersion();

   /**
    * Returns whether the value of the '{@link org.sourcepit.b2.model.module.AbstractReference#getVersion
    * <em>Version</em>}' attribute is set.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return whether the value of the '<em>Version</em>' attribute is set.
    * @see #unsetVersion()
    * @see #getVersion()
    * @see #setVersion(String)
    * @generated
    */
   boolean isSetVersion();

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model required="true"
    * @generated
    */
   boolean isSatisfiableBy(Identifiable identifier);

} // AbstractReference
