/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.b2.model.module.util.Identifier;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ModuleModelFactoryImpl extends EFactoryImpl implements ModuleModelFactory
{
   /**
    * Creates the default factory implementation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public static ModuleModelFactory init()
   {
      try
      {
         ModuleModelFactory theModuleModelFactory = (ModuleModelFactory) EPackage.Registry.INSTANCE
            .getEFactory("http://www.sourcepit.org/b2/model/0.1/module");
         if (theModuleModelFactory != null)
         {
            return theModuleModelFactory;
         }
      }
      catch (Exception exception)
      {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new ModuleModelFactoryImpl();
   }

   /**
    * Creates an instance of the factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleModelFactoryImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public EObject create(EClass eClass)
   {
      switch (eClass.getClassifierID())
      {
         case ModuleModelPackage.BASIC_MODULE :
            return createBasicModule();
         case ModuleModelPackage.COMPOSITE_MODULE :
            return createCompositeModule();
         case ModuleModelPackage.PLUGINS_FACET :
            return createPluginsFacet();
         case ModuleModelPackage.FEATURES_FACET :
            return createFeaturesFacet();
         case ModuleModelPackage.SITES_FACET :
            return createSitesFacet();
         case ModuleModelPackage.PLUGIN_PROJECT :
            return createPluginProject();
         case ModuleModelPackage.FEATURE_PROJECT :
            return createFeatureProject();
         case ModuleModelPackage.SITE_PROJECT :
            return createSiteProject();
         case ModuleModelPackage.PLUGIN_INCLUDE :
            return createPluginInclude();
         case ModuleModelPackage.CATEGORY :
            return createCategory();
         case ModuleModelPackage.PRODUCTS_FACET :
            return createProductsFacet();
         case ModuleModelPackage.PRODUCT_DEFINITION :
            return createProductDefinition();
         case ModuleModelPackage.RULED_REFERENCE :
            return createRuledReference();
         case ModuleModelPackage.STRICT_REFERENCE :
            return createStrictReference();
         default :
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public Object createFromString(EDataType eDataType, String initialValue)
   {
      switch (eDataType.getClassifierID())
      {
         case ModuleModelPackage.VERSION_MATCH_RULE :
            return createVersionMatchRuleFromString(eDataType, initialValue);
         case ModuleModelPackage.IDENTIFIER :
            return createIdentifierFromString(eDataType, initialValue);
         default :
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public String convertToString(EDataType eDataType, Object instanceValue)
   {
      switch (eDataType.getClassifierID())
      {
         case ModuleModelPackage.VERSION_MATCH_RULE :
            return convertVersionMatchRuleToString(eDataType, instanceValue);
         case ModuleModelPackage.IDENTIFIER :
            return convertIdentifierToString(eDataType, instanceValue);
         default :
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public BasicModule createBasicModule()
   {
      BasicModuleImpl basicModule = new BasicModuleImpl();
      return basicModule;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public CompositeModule createCompositeModule()
   {
      CompositeModuleImpl compositeModule = new CompositeModuleImpl();
      return compositeModule;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginsFacet createPluginsFacet()
   {
      PluginsFacetImpl pluginsFacet = new PluginsFacetImpl();
      return pluginsFacet;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeaturesFacet createFeaturesFacet()
   {
      FeaturesFacetImpl featuresFacet = new FeaturesFacetImpl();
      return featuresFacet;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public SitesFacet createSitesFacet()
   {
      SitesFacetImpl sitesFacet = new SitesFacetImpl();
      return sitesFacet;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginProject createPluginProject()
   {
      PluginProjectImpl pluginProject = new PluginProjectImpl();
      return pluginProject;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeatureProject createFeatureProject()
   {
      FeatureProjectImpl featureProject = new FeatureProjectImpl();
      return featureProject;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public SiteProject createSiteProject()
   {
      SiteProjectImpl siteProject = new SiteProjectImpl();
      return siteProject;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginInclude createPluginInclude()
   {
      PluginIncludeImpl pluginInclude = new PluginIncludeImpl();
      return pluginInclude;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public Category createCategory()
   {
      CategoryImpl category = new CategoryImpl();
      return category;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public ProductsFacet createProductsFacet()
   {
      ProductsFacetImpl productsFacet = new ProductsFacetImpl();
      return productsFacet;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public ProductDefinition createProductDefinition()
   {
      ProductDefinitionImpl productDefinition = new ProductDefinitionImpl();
      return productDefinition;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public RuledReference createRuledReference()
   {
      RuledReferenceImpl ruledReference = new RuledReferenceImpl();
      return ruledReference;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public StrictReference createStrictReference()
   {
      StrictReferenceImpl strictReference = new StrictReferenceImpl();
      return strictReference;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public VersionMatchRule createVersionMatchRuleFromString(EDataType eDataType, String initialValue)
   {
      VersionMatchRule result = VersionMatchRule.get(initialValue);
      if (result == null)
         throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '"
            + eDataType.getName() + "'");
      return result;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public String convertVersionMatchRuleToString(EDataType eDataType, Object instanceValue)
   {
      return instanceValue == null ? null : instanceValue.toString();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public Identifier createIdentifierFromString(EDataType eDataType, String initialValue)
   {
      return (Identifier) super.createFromString(eDataType, initialValue);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public String convertIdentifierToString(EDataType eDataType, Object instanceValue)
   {
      return super.convertToString(eDataType, instanceValue);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleModelPackage getModuleModelPackage()
   {
      return (ModuleModelPackage) getEPackage();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @deprecated
    * @generated
    */
   @Deprecated
   public static ModuleModelPackage getPackage()
   {
      return ModuleModelPackage.eINSTANCE;
   }

} // ModuleModelFactoryImpl
