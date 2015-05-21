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
 * A representation of the model object '<em><b>Products Facet</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.ProductsFacet#getProductDefinitions <em>Product Definitions</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getProductsFacet()
 * @model
 * @generated
 */
public interface ProductsFacet extends AbstractFacet {
   /**
    * Returns the value of the '<em><b>Product Definitions</b></em>' containment reference list.
    * The list contents are of type {@link org.sourcepit.b2.model.module.ProductDefinition}.
    * It is bidirectional and its opposite is '{@link org.sourcepit.b2.model.module.ProductDefinition#getParent
    * <em>Parent</em>}'.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Product Definitions</em>' containment reference list isn't clear, there really should
    * be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Product Definitions</em>' containment reference list.
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getProductsFacet_ProductDefinitions()
    * @see org.sourcepit.b2.model.module.ProductDefinition#getParent
    * @model opposite="parent" containment="true" resolveProxies="true"
    * @generated
    */
   EList<ProductDefinition> getProductDefinitions();

} // ProductsFacet
