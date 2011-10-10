/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Package</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.CompositeModule#getModules <em>Modules</em>}</li>
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
    * Returns the value of the '<em><b>Modules</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.b2.model.module.AbstractModule}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.b2.model.module.AbstractModule#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Modules</em>' containment reference list isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Modules</em>' containment reference list.
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getCompositeModule_Modules()
    * @see org.sourcepit.b2.model.module.AbstractModule#getParent
    * @model opposite="parent" containment="true" resolveProxies="true"
    * @generated
    */
   EList<AbstractModule> getModules();

} // ModulePackage
