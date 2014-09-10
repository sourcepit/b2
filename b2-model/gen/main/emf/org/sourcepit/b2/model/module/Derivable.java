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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Derivable</b></em>'.
 * <!-- end-user-doc -->
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
    * Returns the value of the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
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
    * Sets the value of the '{@link org.sourcepit.b2.model.module.Derivable#isDerived <em>Derived</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Derived</em>' attribute.
    * @see #isDerived()
    * @generated
    */
   void setDerived(boolean value);

} // Derivable
