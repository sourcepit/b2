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

import org.sourcepit.b2.model.module.util.Identifiable;
import org.sourcepit.common.modeling.Annotatable;

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
public interface AbstractReference extends Annotatable {
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
    * @model required="true" identifierType="org.sourcepit.b2.model.module.Identifiable"
    * @generated
    */
   boolean isSatisfiableBy(Identifiable identifier);

} // AbstractReference
