/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage
 * @generated
 */
public interface ModuleModelFactory extends EFactory
{
   /**
    * The singleton instance of the factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   ModuleModelFactory eINSTANCE = org.sourcepit.b2.model.module.internal.impl.ModuleModelFactoryImpl.init();

   /**
    * Returns a new object of class '<em>Basic Module</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Basic Module</em>'.
    * @generated
    */
   BasicModule createBasicModule();

   /**
    * Returns a new object of class '<em>Composite Module</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Composite Module</em>'.
    * @generated
    */
   CompositeModule createCompositeModule();

   /**
    * Returns a new object of class '<em>Plugins Facet</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Plugins Facet</em>'.
    * @generated
    */
   PluginsFacet createPluginsFacet();

   /**
    * Returns a new object of class '<em>Features Facet</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Features Facet</em>'.
    * @generated
    */
   FeaturesFacet createFeaturesFacet();

   /**
    * Returns a new object of class '<em>Sites Facet</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Sites Facet</em>'.
    * @generated
    */
   SitesFacet createSitesFacet();

   /**
    * Returns a new object of class '<em>Plugin Project</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Plugin Project</em>'.
    * @generated
    */
   PluginProject createPluginProject();

   /**
    * Returns a new object of class '<em>Feature Project</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Feature Project</em>'.
    * @generated
    */
   FeatureProject createFeatureProject();

   /**
    * Returns a new object of class '<em>Site Project</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Site Project</em>'.
    * @generated
    */
   SiteProject createSiteProject();

   /**
    * Returns a new object of class '<em>Plugin Include</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Plugin Include</em>'.
    * @generated
    */
   PluginInclude createPluginInclude();

   /**
    * Returns a new object of class '<em>Category</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Category</em>'.
    * @generated
    */
   Category createCategory();

   /**
    * Returns a new object of class '<em>Products Facet</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Products Facet</em>'.
    * @generated
    */
   ProductsFacet createProductsFacet();

   /**
    * Returns a new object of class '<em>Product Definition</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Product Definition</em>'.
    * @generated
    */
   ProductDefinition createProductDefinition();

   /**
    * Returns a new object of class '<em>Ruled Reference</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Ruled Reference</em>'.
    * @generated
    */
   RuledReference createRuledReference();

   /**
    * Returns a new object of class '<em>Strict Reference</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Strict Reference</em>'.
    * @generated
    */
   StrictReference createStrictReference();

   /**
    * Returns the package supported by this factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the package supported by this factory.
    * @generated
    */
   ModuleModelPackage getModuleModelPackage();

} // ModuleModelFactory
