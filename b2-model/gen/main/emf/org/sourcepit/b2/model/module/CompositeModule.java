/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sourcepit.b2.model.module.CompositeModule#getModules <em>Modules</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getCompositeModule()
 * @model
 * @generated
 */
public interface CompositeModule extends AbstractModule
{
   /**
    * Returns the value of the '<em><b>Modules</b></em>' reference list.
    * The list contents are of type {@link org.sourcepit.b2.model.module.AbstractModule}.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Modules</em>' reference list isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * @return the value of the '<em>Modules</em>' reference list.
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getCompositeModule_Modules()
    * @model
    * @generated
    */
   EList<AbstractModule> getModules();

} // CompositeModule
