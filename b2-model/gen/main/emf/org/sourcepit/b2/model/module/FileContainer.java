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

import java.io.File;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Container</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.FileContainer#getDirectory <em>Directory</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getFileContainer()
 * @model abstract="true"
 * @generated
 */
public interface FileContainer extends EObject {
   /**
    * Returns the value of the '<em><b>Directory</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Directory</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Directory</em>' attribute.
    * @see #setDirectory(File)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getFileContainer_Directory()
    * @model dataType="org.sourcepit.common.modeling.EFile"
    * @generated
    */
   File getDirectory();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.FileContainer#getDirectory <em>Directory</em>}'
    * attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Directory</em>' attribute.
    * @see #getDirectory()
    * @generated
    */
   void setDirectory(File value);

} // FileContainer
