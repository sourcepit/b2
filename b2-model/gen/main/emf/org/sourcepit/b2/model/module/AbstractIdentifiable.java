/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.util.Identifiable;
import org.sourcepit.b2.model.module.util.Identifier;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Identifiable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sourcepit.b2.model.module.AbstractIdentifiable#getId <em>Id</em>}</li>
 *   <li>{@link org.sourcepit.b2.model.module.AbstractIdentifiable#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractIdentifiable()
 * @model interface="true" abstract="true" superTypes="org.sourcepit.b2.model.module.Identifiable"
 * @generated
 */
public interface AbstractIdentifiable extends EObject, Identifiable
{
   /**
    * Returns the value of the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Id</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * @return the value of the '<em>Id</em>' attribute.
    * @see #setId(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractIdentifiable_Id()
    * @model
    * @generated
    */
   String getId();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.AbstractIdentifiable#getId <em>Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Id</em>' attribute.
    * @see #getId()
    * @generated
    */
   void setId(String value);

   /**
    * Returns the value of the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Version</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * @return the value of the '<em>Version</em>' attribute.
    * @see #setVersion(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractIdentifiable_Version()
    * @model
    * @generated
    */
   String getVersion();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.AbstractIdentifiable#getVersion <em>Version</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Version</em>' attribute.
    * @see #getVersion()
    * @generated
    */
   void setVersion(String value);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model required="true" identifierDataType="org.sourcepit.b2.model.module.Identifier"
    * @generated
    */
   boolean isIdentifyableBy(Identifier identifier);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model dataType="org.sourcepit.b2.model.module.Identifier" required="true"
    * @generated
    */
   Identifier toIdentifier();

} // AbstractIdentifiable
