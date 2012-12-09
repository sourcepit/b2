/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
public interface RuledReference extends AbstractReference
{
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
