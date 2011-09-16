/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sourcepit.beef.b2.model.module.Annotation;
import org.sourcepit.beef.b2.model.module.ModuleFactory;
import org.sourcepit.beef.b2.model.module.ModulePackage;
import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.Category;
import org.sourcepit.beef.b2.model.module.CompositeModule;
import org.sourcepit.beef.b2.model.module.FeatureInclude;
import org.sourcepit.beef.b2.model.module.FeatureProject;
import org.sourcepit.beef.b2.model.module.FeaturesFacet;
import org.sourcepit.beef.b2.model.module.PluginInclude;
import org.sourcepit.beef.b2.model.module.PluginProject;
import org.sourcepit.beef.b2.model.module.PluginsFacet;
import org.sourcepit.beef.b2.model.module.ProductDefinition;
import org.sourcepit.beef.b2.model.module.ProductsFacet;
import org.sourcepit.beef.b2.model.module.Reference;
import org.sourcepit.beef.b2.model.module.SiteProject;
import org.sourcepit.beef.b2.model.module.SitesFacet;
import org.sourcepit.beef.b2.model.module.internal.impl.CModuleFactoryImpl;
import org.sourcepit.beef.b2.model.module.util.Identifier;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class ModuleFactoryImpl extends EFactoryImpl implements ModuleFactory
{
   /**
    * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated NOT
    */
   public static ModuleFactory init()
   {
      try
      {
         ModuleFactory theB2ModelFactory = (ModuleFactory) EPackage.Registry.INSTANCE
            .getEFactory(ModulePackage.eNS_URI);
         if (theB2ModelFactory != null)
         {
            return theB2ModelFactory;
         }
      }
      catch (Exception exception)
      {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new CModuleFactoryImpl();
   }

   /**
    * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleFactoryImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public EObject create(EClass eClass)
   {
      switch (eClass.getClassifierID())
      {
         case ModulePackage.BASIC_MODULE :
            return createBasicModule();
         case ModulePackage.COMPOSITE_MODULE :
            return createCompositeModule();
         case ModulePackage.PLUGINS_FACET :
            return createPluginsFacet();
         case ModulePackage.FEATURES_FACET :
            return createFeaturesFacet();
         case ModulePackage.SITES_FACET :
            return createSitesFacet();
         case ModulePackage.PLUGIN_PROJECT :
            return createPluginProject();
         case ModulePackage.FEATURE_PROJECT :
            return createFeatureProject();
         case ModulePackage.SITE_PROJECT :
            return createSiteProject();
         case ModulePackage.PLUGIN_INCLUDE :
            return createPluginInclude();
         case ModulePackage.CATEGORY :
            return createCategory();
         case ModulePackage.FEATURE_INCLUDE :
            return createFeatureInclude();
         case ModulePackage.ANNOTATION :
            return createAnnotation();
         case ModulePackage.ESTRING_MAP_ENTRY :
            return (EObject) createEStringMapEntry();
         case ModulePackage.PRODUCTS_FACET :
            return createProductsFacet();
         case ModulePackage.PRODUCT_DEFINITION :
            return createProductDefinition();
         case ModulePackage.REFERENCE :
            return createReference();
         default :
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public Object createFromString(EDataType eDataType, String initialValue)
   {
      switch (eDataType.getClassifierID())
      {
         case ModulePackage.EJAVA_FILE :
            return createEJavaFileFromString(eDataType, initialValue);
         case ModulePackage.ELOCALE :
            return createELocaleFromString(eDataType, initialValue);
         case ModulePackage.IDENTIFIER :
            return createIdentifierFromString(eDataType, initialValue);
         default :
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public String convertToString(EDataType eDataType, Object instanceValue)
   {
      switch (eDataType.getClassifierID())
      {
         case ModulePackage.EJAVA_FILE :
            return convertEJavaFileToString(eDataType, instanceValue);
         case ModulePackage.ELOCALE :
            return convertELocaleToString(eDataType, instanceValue);
         case ModulePackage.IDENTIFIER :
            return convertIdentifierToString(eDataType, instanceValue);
         default :
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public BasicModule createBasicModule()
   {
      BasicModuleImpl basicModule = new BasicModuleImpl();
      return basicModule;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public CompositeModule createCompositeModule()
   {
      CompositeModuleImpl compositeModule = new CompositeModuleImpl();
      return compositeModule;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginsFacet createPluginsFacet()
   {
      PluginsFacetImpl pluginsFacet = new PluginsFacetImpl();
      return pluginsFacet;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeaturesFacet createFeaturesFacet()
   {
      FeaturesFacetImpl featuresFacet = new FeaturesFacetImpl();
      return featuresFacet;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SitesFacet createSitesFacet()
   {
      SitesFacetImpl sitesFacet = new SitesFacetImpl();
      return sitesFacet;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginProject createPluginProject()
   {
      PluginProjectImpl pluginProject = new PluginProjectImpl();
      return pluginProject;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeatureProject createFeatureProject()
   {
      FeatureProjectImpl featureProject = new FeatureProjectImpl();
      return featureProject;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SiteProject createSiteProject()
   {
      SiteProjectImpl siteProject = new SiteProjectImpl();
      return siteProject;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginInclude createPluginInclude()
   {
      PluginIncludeImpl pluginInclude = new PluginIncludeImpl();
      return pluginInclude;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Category createCategory()
   {
      CategoryImpl category = new CategoryImpl();
      return category;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeatureInclude createFeatureInclude()
   {
      FeatureIncludeImpl featureInclude = new FeatureIncludeImpl();
      return featureInclude;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Annotation createAnnotation()
   {
      AnnotationImpl annotation = new AnnotationImpl();
      return annotation;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Map.Entry<String, String> createEStringMapEntry()
   {
      EStringMapEntryImpl eStringMapEntry = new EStringMapEntryImpl();
      return eStringMapEntry;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ProductsFacet createProductsFacet()
   {
      ProductsFacetImpl productsFacet = new ProductsFacetImpl();
      return productsFacet;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ProductDefinition createProductDefinition()
   {
      ProductDefinitionImpl productDefinition = new ProductDefinitionImpl();
      return productDefinition;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Reference createReference()
   {
      ReferenceImpl reference = new ReferenceImpl();
      return reference;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public File createEJavaFileFromString(EDataType eDataType, String initialValue)
   {
      return (File) super.createFromString(eDataType, initialValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String convertEJavaFileToString(EDataType eDataType, Object instanceValue)
   {
      return super.convertToString(eDataType, instanceValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Locale createELocaleFromString(EDataType eDataType, String initialValue)
   {
      return (Locale) super.createFromString(eDataType, initialValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String convertELocaleToString(EDataType eDataType, Object instanceValue)
   {
      return super.convertToString(eDataType, instanceValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Identifier createIdentifierFromString(EDataType eDataType, String initialValue)
   {
      return (Identifier) super.createFromString(eDataType, initialValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String convertIdentifierToString(EDataType eDataType, Object instanceValue)
   {
      return super.convertToString(eDataType, instanceValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModulePackage getB2ModelPackage()
   {
      return (ModulePackage) getEPackage();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @deprecated
    * @generated
    */
   @Deprecated
   public static ModulePackage getPackage()
   {
      return ModulePackage.eINSTANCE;
   }

} // B2ModelFactoryImpl
