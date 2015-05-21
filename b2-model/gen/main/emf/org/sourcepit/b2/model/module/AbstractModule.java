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

import java.util.Locale;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.common.modeling.Annotatable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Module</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.AbstractModule#getLayoutId <em>Layout Id</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.AbstractModule#getLocales <em>Locales</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.AbstractModule#getFacets <em>Facets</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractModule()
 * @model abstract="true"
 * @generated
 */
public interface AbstractModule extends FileContainer, Annotatable, AbstractIdentifiable {
   /**
    * Returns the value of the '<em><b>Layout Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Layout Id</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Layout Id</em>' attribute.
    * @see #setLayoutId(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractModule_LayoutId()
    * @model required="true"
    * @generated
    */
   String getLayoutId();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.AbstractModule#getLayoutId <em>Layout Id</em>}'
    * attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Layout Id</em>' attribute.
    * @see #getLayoutId()
    * @generated
    */
   void setLayoutId(String value);

   /**
    * Returns the value of the '<em><b>Locales</b></em>' attribute list.
    * The list contents are of type {@link java.util.Locale}.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Locales</em>' attribute list isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Locales</em>' attribute list.
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractModule_Locales()
    * @model dataType="org.sourcepit.common.modeling.ELocale"
    * @generated
    */
   EList<Locale> getLocales();

   /**
    * Returns the value of the '<em><b>Facets</b></em>' containment reference list.
    * The list contents are of type {@link org.sourcepit.b2.model.module.AbstractFacet}.
    * It is bidirectional and its opposite is '{@link org.sourcepit.b2.model.module.AbstractFacet#getParent
    * <em>Parent</em>}'.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Facets</em>' containment reference list isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Facets</em>' containment reference list.
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getAbstractModule_Facets()
    * @see org.sourcepit.b2.model.module.AbstractFacet#getParent
    * @model opposite="parent" containment="true" resolveProxies="true"
    * @generated
    */
   EList<AbstractFacet> getFacets();

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model required="true" facetTypeRequired="true"
    * @generated
    */
   <T extends AbstractFacet> EList<T> getFacets(Class<T> facetType);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model required="true"
    * @generated
    */
   boolean hasFacets(Class<? extends AbstractFacet> facetType);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   <F extends AbstractFacet> F getFacetByName(String type);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model referenceRequired="true" facetTypeRequired="true"
    * @generated
    */
   <P extends Project, F extends ProjectFacet<P>> P resolveReference(AbstractReference reference, Class<F> facetType);

} // AbstractModule
