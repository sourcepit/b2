/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * <li>{@link org.sourcepit.b2.model.module.AbstractIdentifiable#getId <em>Id</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.AbstractIdentifiable#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractIdentifiable()
 * @model interface="true" abstract="true" superTypes="org.sourcepit.b2.model.module.Identifiable"
 * @generated
 */
public interface AbstractIdentifiable extends EObject, Identifiable {
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
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractIdentifiable_Id()
    * @model
    * @generated
    */
   String getId();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.AbstractIdentifiable#getId <em>Id</em>}' attribute.
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
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Version</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Version</em>' attribute.
    * @see #setVersion(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractIdentifiable_Version()
    * @model
    * @generated
    */
   String getVersion();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.AbstractIdentifiable#getVersion <em>Version</em>}'
    * attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Version</em>' attribute.
    * @see #getVersion()
    * @generated
    */
   void setVersion(String value);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model required="true" identifierDataType="org.sourcepit.b2.model.module.Identifier"
    * @generated
    */
   boolean isIdentifyableBy(Identifier identifier);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model dataType="org.sourcepit.b2.model.module.Identifier" required="true"
    * @generated
    */
   Identifier toIdentifier();

} // AbstractIdentifiable
