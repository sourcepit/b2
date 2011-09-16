/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;

import java.util.Locale;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.AbstractModule#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.AbstractModule#getLayoutId <em>Layout Id</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.AbstractModule#getLocales <em>Locales</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.AbstractModule#getFacets <em>Facets</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.ModulePackage#getAbstractModule()
 * @model abstract="true"
 * @generated
 */
public interface AbstractModule extends FileContainer, Annotateable, Identifiable
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.module.CompositeModule#getModules <em>Modules</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(CompositeModule)
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getAbstractModule_Parent()
    * @see org.sourcepit.beef.b2.model.module.CompositeModule#getModules
    * @model opposite="modules" transient="false"
    * @generated
    */
   CompositeModule getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.AbstractModule#getParent <em>Parent</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(CompositeModule value);

   /**
    * Returns the value of the '<em><b>Layout Id</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Layout</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Layout Id</em>' attribute.
    * @see #setLayoutId(String)
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getAbstractModule_LayoutId()
    * @model required="true"
    * @generated
    */
   String getLayoutId();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.AbstractModule#getLayoutId <em>Layout Id</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Layout Id</em>' attribute.
    * @see #getLayoutId()
    * @generated
    */
   void setLayoutId(String value);

   /**
    * Returns the value of the '<em><b>Locales</b></em>' attribute list. The list contents are of type
    * {@link java.util.Locale}. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Locales</em>' attribute list isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Locales</em>' attribute list.
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getAbstractModule_Locales()
    * @model dataType="org.sourcepit.beef.b2.model.common.ELocale"
    * @generated
    */
   EList<Locale> getLocales();

   /**
    * Returns the value of the '<em><b>Facets</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.beef.b2.model.module.AbstractFacet}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.module.AbstractFacet#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Facets</em>' containment reference list isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Facets</em>' containment reference list.
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getAbstractModule_Facets()
    * @see org.sourcepit.beef.b2.model.module.AbstractFacet#getParent
    * @model opposite="parent" containment="true"
    * @generated
    */
   EList<AbstractFacet> getFacets();

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model required="true" facetTypeRequired="true"
    * @generated
    */
   <T extends AbstractFacet> EList<T> getFacets(Class<T> facetType);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model required="true"
    * @generated
    */
   boolean hasFacets(Class<? extends AbstractFacet> facetType);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   <F extends AbstractFacet> F getFacetByName(String type);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model referenceRequired="true" facetTypeRequired="true"
    * @generated
    */
   <P extends Project, F extends ProjectFacet<P>> P resolveReference(Reference reference, Class<F> facetType);


} // Module
