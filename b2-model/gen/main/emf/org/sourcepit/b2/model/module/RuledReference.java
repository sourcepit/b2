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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ruled Reference</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.RuledReference#getVersionMatchRule <em>Version Match Rule</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getRuledReference()
 * @model
 * @generated
 */
public interface RuledReference extends AbstractReference {
   /**
    * Returns the value of the '<em><b>Version Match Rule</b></em>' attribute.
    * The default value is <code>"compatible"</code>.
    * The literals are from the enumeration {@link org.sourcepit.b2.model.module.VersionMatchRule}.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Version Match Rule</em>' attribute isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Version Match Rule</em>' attribute.
    * @see org.sourcepit.b2.model.module.VersionMatchRule
    * @see #isSetVersionMatchRule()
    * @see #unsetVersionMatchRule()
    * @see #setVersionMatchRule(VersionMatchRule)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getRuledReference_VersionMatchRule()
    * @model default="compatible" unsettable="true"
    * @generated
    */
   VersionMatchRule getVersionMatchRule();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.RuledReference#getVersionMatchRule
    * <em>Version Match Rule</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Version Match Rule</em>' attribute.
    * @see org.sourcepit.b2.model.module.VersionMatchRule
    * @see #isSetVersionMatchRule()
    * @see #unsetVersionMatchRule()
    * @see #getVersionMatchRule()
    * @generated
    */
   void setVersionMatchRule(VersionMatchRule value);

   /**
    * Unsets the value of the '{@link org.sourcepit.b2.model.module.RuledReference#getVersionMatchRule
    * <em>Version Match Rule</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #isSetVersionMatchRule()
    * @see #getVersionMatchRule()
    * @see #setVersionMatchRule(VersionMatchRule)
    * @generated
    */
   void unsetVersionMatchRule();

   /**
    * Returns whether the value of the '{@link org.sourcepit.b2.model.module.RuledReference#getVersionMatchRule
    * <em>Version Match Rule</em>}' attribute is set.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return whether the value of the '<em>Version Match Rule</em>' attribute is set.
    * @see #unsetVersionMatchRule()
    * @see #getVersionMatchRule()
    * @see #setVersionMatchRule(VersionMatchRule)
    * @generated
    */
   boolean isSetVersionMatchRule();

} // RuledReference
