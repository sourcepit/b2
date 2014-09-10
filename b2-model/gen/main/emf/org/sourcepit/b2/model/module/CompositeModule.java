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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Module</b></em>'.
 * <!-- end-user-doc -->
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
    * Returns the value of the '<em><b>Modules</b></em>' reference list.
    * The list contents are of type {@link org.sourcepit.b2.model.module.AbstractModule}.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Modules</em>' reference list isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Modules</em>' reference list.
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getCompositeModule_Modules()
    * @model
    * @generated
    */
   EList<AbstractModule> getModules();

} // CompositeModule
