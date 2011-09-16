/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.sourcepit.beef.b2.model.module.AbstractFacet;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.Annotateable;
import org.sourcepit.beef.b2.model.module.Annotation;
import org.sourcepit.beef.b2.model.module.B2ModelFactory;
import org.sourcepit.beef.b2.model.module.B2ModelPackage;
import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.Category;
import org.sourcepit.beef.b2.model.module.Classified;
import org.sourcepit.beef.b2.model.module.CompositeModule;
import org.sourcepit.beef.b2.model.module.Derivable;
import org.sourcepit.beef.b2.model.module.FeatureInclude;
import org.sourcepit.beef.b2.model.module.FeatureProject;
import org.sourcepit.beef.b2.model.module.FeaturesFacet;
import org.sourcepit.beef.b2.model.module.FileContainer;
import org.sourcepit.beef.b2.model.module.Identifiable;
import org.sourcepit.beef.b2.model.module.PluginInclude;
import org.sourcepit.beef.b2.model.module.PluginProject;
import org.sourcepit.beef.b2.model.module.PluginsFacet;
import org.sourcepit.beef.b2.model.module.ProductDefinition;
import org.sourcepit.beef.b2.model.module.ProductsFacet;
import org.sourcepit.beef.b2.model.module.Project;
import org.sourcepit.beef.b2.model.module.ProjectFacet;
import org.sourcepit.beef.b2.model.module.Reference;
import org.sourcepit.beef.b2.model.module.SiteProject;
import org.sourcepit.beef.b2.model.module.SitesFacet;
import org.sourcepit.beef.b2.model.module.util.Identifier;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class B2ModelPackageImpl extends EPackageImpl implements B2ModelPackage
{
   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass abstractModuleEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass basicModuleEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass abstractFacetEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass compositeModuleEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass pluginsFacetEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass featuresFacetEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass sitesFacetEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass pluginProjectEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass featureProjectEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass siteProjectEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass projectEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass projectFacetEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass fileContainerEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass derivableEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass pluginIncludeEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass categoryEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass featureIncludeEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass classifiedEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass annotateableEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass annotationEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass eStringMapEntryEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass identifiableEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass productsFacetEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass productDefinitionEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass referenceEClass = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EDataType eJavaFileEDataType = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EDataType eLocaleEDataType = null;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private EDataType identifierEDataType = null;

   /**
    * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
    * EPackage.Registry} by the package package URI value.
    * <p>
    * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
    * performs initialization of the package, or returns the registered package, if one already exists. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.eclipse.emf.ecore.EPackage.Registry
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#eNS_URI
    * @see #init()
    * @generated
    */
   private B2ModelPackageImpl()
   {
      super(eNS_URI, B2ModelFactory.eINSTANCE);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private static boolean isInited = false;

   /**
    * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
    * 
    * <p>
    * This method is used to initialize {@link B2ModelPackage#eINSTANCE} when that field is accessed. Clients should not
    * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #eNS_URI
    * @see #createPackageContents()
    * @see #initializePackageContents()
    * @generated
    */
   public static B2ModelPackage init()
   {
      if (isInited)
         return (B2ModelPackage) EPackage.Registry.INSTANCE.getEPackage(B2ModelPackage.eNS_URI);

      // Obtain or create and register package
      B2ModelPackageImpl theB2ModelPackage = (B2ModelPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof B2ModelPackageImpl
         ? EPackage.Registry.INSTANCE.get(eNS_URI)
         : new B2ModelPackageImpl());

      isInited = true;

      // Create package meta-data objects
      theB2ModelPackage.createPackageContents();

      // Initialize created meta-data
      theB2ModelPackage.initializePackageContents();

      // Mark meta-data to indicate it can't be changed
      theB2ModelPackage.freeze();


      // Update the registry and return the package
      EPackage.Registry.INSTANCE.put(B2ModelPackage.eNS_URI, theB2ModelPackage);
      return theB2ModelPackage;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAbstractModule()
   {
      return abstractModuleEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAbstractModule_Parent()
   {
      return (EReference) abstractModuleEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAbstractModule_LayoutId()
   {
      return (EAttribute) abstractModuleEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAbstractModule_Locales()
   {
      return (EAttribute) abstractModuleEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAbstractModule_Facets()
   {
      return (EReference) abstractModuleEClass.getEStructuralFeatures().get(3);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getBasicModule()
   {
      return basicModuleEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAbstractFacet()
   {
      return abstractFacetEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAbstractFacet_Parent()
   {
      return (EReference) abstractFacetEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAbstractFacet_Name()
   {
      return (EAttribute) abstractFacetEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getCompositeModule()
   {
      return compositeModuleEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getCompositeModule_Modules()
   {
      return (EReference) compositeModuleEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getPluginsFacet()
   {
      return pluginsFacetEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getPluginsFacet_Projects()
   {
      return (EReference) pluginsFacetEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getFeaturesFacet()
   {
      return featuresFacetEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeaturesFacet_Projects()
   {
      return (EReference) featuresFacetEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getSitesFacet()
   {
      return sitesFacetEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getSitesFacet_Projects()
   {
      return (EReference) sitesFacetEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getPluginProject()
   {
      return pluginProjectEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getPluginProject_Parent()
   {
      return (EReference) pluginProjectEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getPluginProject_BundleVersion()
   {
      return (EAttribute) pluginProjectEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getPluginProject_TestPlugin()
   {
      return (EAttribute) pluginProjectEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getPluginProject_FragmentHostSymbolicName()
   {
      return (EAttribute) pluginProjectEClass.getEStructuralFeatures().get(3);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getPluginProject_FragmentHostVersion()
   {
      return (EAttribute) pluginProjectEClass.getEStructuralFeatures().get(4);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getFeatureProject()
   {
      return featureProjectEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeatureProject_Parent()
   {
      return (EReference) featureProjectEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeatureProject_IncludedPlugins()
   {
      return (EReference) featureProjectEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeatureProject_IncludedFeatures()
   {
      return (EReference) featureProjectEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getSiteProject()
   {
      return siteProjectEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getSiteProject_Parent()
   {
      return (EReference) siteProjectEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getSiteProject_Categories()
   {
      return (EReference) siteProjectEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getProject()
   {
      return projectEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getProjectFacet()
   {
      return projectFacetEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getFileContainer()
   {
      return fileContainerEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getFileContainer_Directory()
   {
      return (EAttribute) fileContainerEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getDerivable()
   {
      return derivableEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getDerivable_Derived()
   {
      return (EAttribute) derivableEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getPluginInclude()
   {
      return pluginIncludeEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getPluginInclude_Unpack()
   {
      return (EAttribute) pluginIncludeEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getPluginInclude_Parent()
   {
      return (EReference) pluginIncludeEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getCategory()
   {
      return categoryEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getCategory_FeatureReferences()
   {
      return (EReference) categoryEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getCategory_Name()
   {
      return (EAttribute) categoryEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getFeatureInclude()
   {
      return featureIncludeEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeatureInclude_Parent()
   {
      return (EReference) featureIncludeEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getClassified()
   {
      return classifiedEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getClassified_Classifier()
   {
      return (EAttribute) classifiedEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAnnotateable()
   {
      return annotateableEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAnnotateable_Annotations()
   {
      return (EReference) annotateableEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAnnotation()
   {
      return annotationEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAnnotation_Parent()
   {
      return (EReference) annotationEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAnnotation_Source()
   {
      return (EAttribute) annotationEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAnnotation_Entries()
   {
      return (EReference) annotationEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getEStringMapEntry()
   {
      return eStringMapEntryEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getEStringMapEntry_Key()
   {
      return (EAttribute) eStringMapEntryEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getEStringMapEntry_Value()
   {
      return (EAttribute) eStringMapEntryEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getIdentifiable()
   {
      return identifiableEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getIdentifiable_Id()
   {
      return (EAttribute) identifiableEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getIdentifiable_Version()
   {
      return (EAttribute) identifiableEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getProductsFacet()
   {
      return productsFacetEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getProductsFacet_ProductDefinitions()
   {
      return (EReference) productsFacetEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getProductDefinition()
   {
      return productDefinitionEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getProductDefinition_Parent()
   {
      return (EReference) productDefinitionEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getProductDefinition_File()
   {
      return (EAttribute) productDefinitionEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getProductDefinition_ProductPlugin()
   {
      return (EReference) productDefinitionEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getReference()
   {
      return referenceEClass;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getReference_Id()
   {
      return (EAttribute) referenceEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getReference_VersionRange()
   {
      return (EAttribute) referenceEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EDataType getIdentifier()
   {
      return identifierEDataType;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EDataType getEJavaFile()
   {
      return eJavaFileEDataType;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EDataType getELocale()
   {
      return eLocaleEDataType;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public B2ModelFactory getB2ModelFactory()
   {
      return (B2ModelFactory) getEFactoryInstance();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private boolean isCreated = false;

   /**
    * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but its
    * first. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void createPackageContents()
   {
      if (isCreated)
         return;
      isCreated = true;

      // Create classes and their features
      abstractModuleEClass = createEClass(ABSTRACT_MODULE);
      createEReference(abstractModuleEClass, ABSTRACT_MODULE__PARENT);
      createEAttribute(abstractModuleEClass, ABSTRACT_MODULE__LAYOUT_ID);
      createEAttribute(abstractModuleEClass, ABSTRACT_MODULE__LOCALES);
      createEReference(abstractModuleEClass, ABSTRACT_MODULE__FACETS);

      basicModuleEClass = createEClass(BASIC_MODULE);

      abstractFacetEClass = createEClass(ABSTRACT_FACET);
      createEReference(abstractFacetEClass, ABSTRACT_FACET__PARENT);
      createEAttribute(abstractFacetEClass, ABSTRACT_FACET__NAME);

      compositeModuleEClass = createEClass(COMPOSITE_MODULE);
      createEReference(compositeModuleEClass, COMPOSITE_MODULE__MODULES);

      pluginsFacetEClass = createEClass(PLUGINS_FACET);
      createEReference(pluginsFacetEClass, PLUGINS_FACET__PROJECTS);

      featuresFacetEClass = createEClass(FEATURES_FACET);
      createEReference(featuresFacetEClass, FEATURES_FACET__PROJECTS);

      sitesFacetEClass = createEClass(SITES_FACET);
      createEReference(sitesFacetEClass, SITES_FACET__PROJECTS);

      pluginProjectEClass = createEClass(PLUGIN_PROJECT);
      createEReference(pluginProjectEClass, PLUGIN_PROJECT__PARENT);
      createEAttribute(pluginProjectEClass, PLUGIN_PROJECT__BUNDLE_VERSION);
      createEAttribute(pluginProjectEClass, PLUGIN_PROJECT__TEST_PLUGIN);
      createEAttribute(pluginProjectEClass, PLUGIN_PROJECT__FRAGMENT_HOST_SYMBOLIC_NAME);
      createEAttribute(pluginProjectEClass, PLUGIN_PROJECT__FRAGMENT_HOST_VERSION);

      featureProjectEClass = createEClass(FEATURE_PROJECT);
      createEReference(featureProjectEClass, FEATURE_PROJECT__PARENT);
      createEReference(featureProjectEClass, FEATURE_PROJECT__INCLUDED_PLUGINS);
      createEReference(featureProjectEClass, FEATURE_PROJECT__INCLUDED_FEATURES);

      siteProjectEClass = createEClass(SITE_PROJECT);
      createEReference(siteProjectEClass, SITE_PROJECT__PARENT);
      createEReference(siteProjectEClass, SITE_PROJECT__CATEGORIES);

      projectEClass = createEClass(PROJECT);

      projectFacetEClass = createEClass(PROJECT_FACET);

      fileContainerEClass = createEClass(FILE_CONTAINER);
      createEAttribute(fileContainerEClass, FILE_CONTAINER__DIRECTORY);

      derivableEClass = createEClass(DERIVABLE);
      createEAttribute(derivableEClass, DERIVABLE__DERIVED);

      pluginIncludeEClass = createEClass(PLUGIN_INCLUDE);
      createEAttribute(pluginIncludeEClass, PLUGIN_INCLUDE__UNPACK);
      createEReference(pluginIncludeEClass, PLUGIN_INCLUDE__PARENT);

      categoryEClass = createEClass(CATEGORY);
      createEReference(categoryEClass, CATEGORY__FEATURE_REFERENCES);
      createEAttribute(categoryEClass, CATEGORY__NAME);

      featureIncludeEClass = createEClass(FEATURE_INCLUDE);
      createEReference(featureIncludeEClass, FEATURE_INCLUDE__PARENT);

      classifiedEClass = createEClass(CLASSIFIED);
      createEAttribute(classifiedEClass, CLASSIFIED__CLASSIFIER);

      annotateableEClass = createEClass(ANNOTATEABLE);
      createEReference(annotateableEClass, ANNOTATEABLE__ANNOTATIONS);

      annotationEClass = createEClass(ANNOTATION);
      createEReference(annotationEClass, ANNOTATION__PARENT);
      createEAttribute(annotationEClass, ANNOTATION__SOURCE);
      createEReference(annotationEClass, ANNOTATION__ENTRIES);

      eStringMapEntryEClass = createEClass(ESTRING_MAP_ENTRY);
      createEAttribute(eStringMapEntryEClass, ESTRING_MAP_ENTRY__KEY);
      createEAttribute(eStringMapEntryEClass, ESTRING_MAP_ENTRY__VALUE);

      identifiableEClass = createEClass(IDENTIFIABLE);
      createEAttribute(identifiableEClass, IDENTIFIABLE__ID);
      createEAttribute(identifiableEClass, IDENTIFIABLE__VERSION);

      productsFacetEClass = createEClass(PRODUCTS_FACET);
      createEReference(productsFacetEClass, PRODUCTS_FACET__PRODUCT_DEFINITIONS);

      productDefinitionEClass = createEClass(PRODUCT_DEFINITION);
      createEReference(productDefinitionEClass, PRODUCT_DEFINITION__PARENT);
      createEAttribute(productDefinitionEClass, PRODUCT_DEFINITION__FILE);
      createEReference(productDefinitionEClass, PRODUCT_DEFINITION__PRODUCT_PLUGIN);

      referenceEClass = createEClass(REFERENCE);
      createEAttribute(referenceEClass, REFERENCE__ID);
      createEAttribute(referenceEClass, REFERENCE__VERSION_RANGE);

      // Create data types
      eJavaFileEDataType = createEDataType(EJAVA_FILE);
      eLocaleEDataType = createEDataType(ELOCALE);
      identifierEDataType = createEDataType(IDENTIFIER);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   private boolean isInitialized = false;

   /**
    * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
    * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void initializePackageContents()
   {
      if (isInitialized)
         return;
      isInitialized = true;

      // Initialize package
      setName(eNAME);
      setNsPrefix(eNS_PREFIX);
      setNsURI(eNS_URI);

      // Create type parameters
      ETypeParameter projectFacetEClass_P = addETypeParameter(projectFacetEClass, "P");

      // Set bounds for type parameters
      EGenericType g1 = createEGenericType(this.getProject());
      projectFacetEClass_P.getEBounds().add(g1);

      // Add supertypes to classes
      abstractModuleEClass.getESuperTypes().add(this.getFileContainer());
      abstractModuleEClass.getESuperTypes().add(this.getAnnotateable());
      abstractModuleEClass.getESuperTypes().add(this.getIdentifiable());
      basicModuleEClass.getESuperTypes().add(this.getAbstractModule());
      abstractFacetEClass.getESuperTypes().add(this.getDerivable());
      abstractFacetEClass.getESuperTypes().add(this.getAnnotateable());
      compositeModuleEClass.getESuperTypes().add(this.getAbstractModule());
      g1 = createEGenericType(this.getProjectFacet());
      EGenericType g2 = createEGenericType(this.getPluginProject());
      g1.getETypeArguments().add(g2);
      pluginsFacetEClass.getEGenericSuperTypes().add(g1);
      g1 = createEGenericType(this.getProjectFacet());
      g2 = createEGenericType(this.getFeatureProject());
      g1.getETypeArguments().add(g2);
      featuresFacetEClass.getEGenericSuperTypes().add(g1);
      g1 = createEGenericType(this.getProjectFacet());
      g2 = createEGenericType(this.getSiteProject());
      g1.getETypeArguments().add(g2);
      sitesFacetEClass.getEGenericSuperTypes().add(g1);
      pluginProjectEClass.getESuperTypes().add(this.getProject());
      featureProjectEClass.getESuperTypes().add(this.getProject());
      featureProjectEClass.getESuperTypes().add(this.getClassified());
      siteProjectEClass.getESuperTypes().add(this.getProject());
      siteProjectEClass.getESuperTypes().add(this.getClassified());
      projectEClass.getESuperTypes().add(this.getFileContainer());
      projectEClass.getESuperTypes().add(this.getDerivable());
      projectEClass.getESuperTypes().add(this.getAnnotateable());
      projectEClass.getESuperTypes().add(this.getIdentifiable());
      projectFacetEClass.getESuperTypes().add(this.getAbstractFacet());
      pluginIncludeEClass.getESuperTypes().add(this.getReference());
      featureIncludeEClass.getESuperTypes().add(this.getReference());
      productsFacetEClass.getESuperTypes().add(this.getAbstractFacet());
      productDefinitionEClass.getESuperTypes().add(this.getAnnotateable());
      productDefinitionEClass.getESuperTypes().add(this.getDerivable());

      // Initialize classes and features; add operations and parameters
      initEClass(abstractModuleEClass, AbstractModule.class, "AbstractModule", IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getAbstractModule_Parent(), this.getCompositeModule(), this.getCompositeModule_Modules(),
         "parent", null, 0, 1, AbstractModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getAbstractModule_LayoutId(), ecorePackage.getEString(), "layoutId", null, 1, 1,
         AbstractModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getAbstractModule_Locales(), this.getELocale(), "locales", null, 0, -1, AbstractModule.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getAbstractModule_Facets(), this.getAbstractFacet(), this.getAbstractFacet_Parent(), "facets",
         null, 0, -1, AbstractModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      EOperation op = addEOperation(abstractModuleEClass, null, "getFacets", 1, -1, IS_UNIQUE, IS_ORDERED);
      ETypeParameter t1 = addETypeParameter(op, "T");
      g1 = createEGenericType(this.getAbstractFacet());
      t1.getEBounds().add(g1);
      g1 = createEGenericType(ecorePackage.getEJavaClass());
      g2 = createEGenericType(t1);
      g1.getETypeArguments().add(g2);
      addEParameter(op, g1, "facetType", 1, 1, IS_UNIQUE, IS_ORDERED);
      g1 = createEGenericType(t1);
      initEOperation(op, g1);

      op = addEOperation(abstractModuleEClass, ecorePackage.getEBoolean(), "hasFacets", 1, 1, IS_UNIQUE, IS_ORDERED);
      g1 = createEGenericType(ecorePackage.getEJavaClass());
      g2 = createEGenericType();
      g1.getETypeArguments().add(g2);
      EGenericType g3 = createEGenericType(this.getAbstractFacet());
      g2.setEUpperBound(g3);
      addEParameter(op, g1, "facetType", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = addEOperation(abstractModuleEClass, null, "getFacetByName", 0, 1, IS_UNIQUE, IS_ORDERED);
      t1 = addETypeParameter(op, "F");
      g1 = createEGenericType(this.getAbstractFacet());
      t1.getEBounds().add(g1);
      addEParameter(op, ecorePackage.getEString(), "type", 0, 1, IS_UNIQUE, IS_ORDERED);
      g1 = createEGenericType(t1);
      initEOperation(op, g1);

      op = addEOperation(abstractModuleEClass, null, "resolveReference", 0, 1, IS_UNIQUE, IS_ORDERED);
      t1 = addETypeParameter(op, "P");
      g1 = createEGenericType(this.getProject());
      t1.getEBounds().add(g1);
      ETypeParameter t2 = addETypeParameter(op, "F");
      g1 = createEGenericType(this.getProjectFacet());
      g2 = createEGenericType(t1);
      g1.getETypeArguments().add(g2);
      t2.getEBounds().add(g1);
      addEParameter(op, this.getReference(), "reference", 1, 1, IS_UNIQUE, IS_ORDERED);
      g1 = createEGenericType(ecorePackage.getEJavaClass());
      g2 = createEGenericType(t2);
      g1.getETypeArguments().add(g2);
      addEParameter(op, g1, "facetType", 1, 1, IS_UNIQUE, IS_ORDERED);
      g1 = createEGenericType(t1);
      initEOperation(op, g1);

      initEClass(basicModuleEClass, BasicModule.class, "BasicModule", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);

      initEClass(abstractFacetEClass, AbstractFacet.class, "AbstractFacet", IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getAbstractFacet_Parent(), this.getAbstractModule(), this.getAbstractModule_Facets(), "parent",
         null, 1, 1, AbstractFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getAbstractFacet_Name(), ecorePackage.getEString(), "name", null, 1, 1, AbstractFacet.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(compositeModuleEClass, CompositeModule.class, "CompositeModule", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getCompositeModule_Modules(), this.getAbstractModule(), this.getAbstractModule_Parent(),
         "modules", null, 0, -1, CompositeModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(pluginsFacetEClass, PluginsFacet.class, "PluginsFacet", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getPluginsFacet_Projects(), this.getPluginProject(), this.getPluginProject_Parent(), "projects",
         null, 0, -1, PluginsFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(featuresFacetEClass, FeaturesFacet.class, "FeaturesFacet", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getFeaturesFacet_Projects(), this.getFeatureProject(), this.getFeatureProject_Parent(),
         "projects", null, 0, -1, FeaturesFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(sitesFacetEClass, SitesFacet.class, "SitesFacet", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getSitesFacet_Projects(), this.getSiteProject(), this.getSiteProject_Parent(), "projects", null,
         0, -1, SitesFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(pluginProjectEClass, PluginProject.class, "PluginProject", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getPluginProject_Parent(), this.getPluginsFacet(), this.getPluginsFacet_Projects(), "parent",
         null, 0, 1, PluginProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getPluginProject_BundleVersion(), ecorePackage.getEString(), "bundleVersion", null, 1, 1,
         PluginProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getPluginProject_TestPlugin(), ecorePackage.getEBoolean(), "testPlugin", null, 0, 1,
         PluginProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getPluginProject_FragmentHostSymbolicName(), ecorePackage.getEString(),
         "fragmentHostSymbolicName", null, 0, 1, PluginProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
         !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getPluginProject_FragmentHostVersion(), ecorePackage.getEString(), "fragmentHostVersion", null, 0,
         1, PluginProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      addEOperation(pluginProjectEClass, ecorePackage.getEBoolean(), "isFragment", 1, 1, IS_UNIQUE, IS_ORDERED);

      initEClass(featureProjectEClass, FeatureProject.class, "FeatureProject", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getFeatureProject_Parent(), this.getFeaturesFacet(), this.getFeaturesFacet_Projects(), "parent",
         null, 0, 1, FeatureProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getFeatureProject_IncludedPlugins(), this.getPluginInclude(), this.getPluginInclude_Parent(),
         "includedPlugins", null, 0, -1, FeatureProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
         IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getFeatureProject_IncludedFeatures(), this.getFeatureInclude(), this.getFeatureInclude_Parent(),
         "includedFeatures", null, 0, -1, FeatureProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
         IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(siteProjectEClass, SiteProject.class, "SiteProject", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getSiteProject_Parent(), this.getSitesFacet(), this.getSitesFacet_Projects(), "parent", null, 0,
         1, SiteProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getSiteProject_Categories(), this.getCategory(), null, "categories", null, 0, -1,
         SiteProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(projectEClass, Project.class, "Project", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

      op = addEOperation(projectEClass, null, "getParent", 0, 1, IS_UNIQUE, IS_ORDERED);
      g1 = createEGenericType(this.getProjectFacet());
      g2 = createEGenericType();
      g1.getETypeArguments().add(g2);
      g3 = createEGenericType(this.getProject());
      g2.setEUpperBound(g3);
      initEOperation(op, g1);

      initEClass(projectFacetEClass, ProjectFacet.class, "ProjectFacet", IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);

      op = addEOperation(projectFacetEClass, null, "getProjects", 1, 1, IS_UNIQUE, IS_ORDERED);
      g1 = createEGenericType(ecorePackage.getEEList());
      g2 = createEGenericType(projectFacetEClass_P);
      g1.getETypeArguments().add(g2);
      initEOperation(op, g1);

      op = addEOperation(projectFacetEClass, null, "getProjectById", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
      g1 = createEGenericType(projectFacetEClass_P);
      initEOperation(op, g1);

      op = addEOperation(projectFacetEClass, null, "resolveReference", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, this.getReference(), "reference", 1, 1, IS_UNIQUE, IS_ORDERED);
      g1 = createEGenericType(projectFacetEClass_P);
      initEOperation(op, g1);

      initEClass(fileContainerEClass, FileContainer.class, "FileContainer", IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getFileContainer_Directory(), this.getEJavaFile(), "directory", null, 0, 1, FileContainer.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(derivableEClass, Derivable.class, "Derivable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getDerivable_Derived(), ecorePackage.getEBoolean(), "derived", null, 0, 1, Derivable.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(pluginIncludeEClass, PluginInclude.class, "PluginInclude", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getPluginInclude_Unpack(), ecorePackage.getEBoolean(), "unpack", "true", 0, 1,
         PluginInclude.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEReference(getPluginInclude_Parent(), this.getFeatureProject(), this.getFeatureProject_IncludedPlugins(),
         "parent", null, 1, 1, PluginInclude.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(categoryEClass, Category.class, "Category", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEReference(getCategory_FeatureReferences(), this.getReference(), null, "featureReferences", null, 0, -1,
         Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
         IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getCategory_Name(), ecorePackage.getEString(), "name", null, 1, 1, Category.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(featureIncludeEClass, FeatureInclude.class, "FeatureInclude", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getFeatureInclude_Parent(), this.getFeatureProject(), this.getFeatureProject_IncludedFeatures(),
         "parent", null, 1, 1, FeatureInclude.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(classifiedEClass, Classified.class, "Classified", IS_ABSTRACT, IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getClassified_Classifier(), ecorePackage.getEString(), "classifier", null, 0, 1, Classified.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(annotateableEClass, Annotateable.class, "Annotateable", IS_ABSTRACT, IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getAnnotateable_Annotations(), this.getAnnotation(), this.getAnnotation_Parent(), "annotations",
         null, 0, -1, Annotateable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      op = addEOperation(annotateableEClass, this.getAnnotation(), "getAnnotation", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "source", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = addEOperation(annotateableEClass, ecorePackage.getEString(), "getAnnotationEntry", 0, 1, IS_UNIQUE,
         IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "source", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "key", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = addEOperation(annotateableEClass, ecorePackage.getEString(), "putAnnotationEntry", 1, 1, IS_UNIQUE,
         IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "source", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "key", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);

      initEClass(annotationEClass, Annotation.class, "Annotation", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getAnnotation_Parent(), this.getAnnotateable(), this.getAnnotateable_Annotations(), "parent",
         null, 1, 1, Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getAnnotation_Source(), ecorePackage.getEString(), "source", null, 1, 1, Annotation.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getAnnotation_Entries(), this.getEStringMapEntry(), null, "entries", null, 0, -1,
         Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(eStringMapEntryEClass, Map.Entry.class, "EStringMapEntry", !IS_ABSTRACT, !IS_INTERFACE,
         !IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getEStringMapEntry_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getEStringMapEntry_Value(), ecorePackage.getEString(), "value", null, 0, 1, Map.Entry.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(identifiableEClass, Identifiable.class, "Identifiable", IS_ABSTRACT, IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getIdentifiable_Id(), ecorePackage.getEString(), "id", null, 0, 1, Identifiable.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getIdentifiable_Version(), ecorePackage.getEString(), "version", null, 0, 1, Identifiable.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      op = addEOperation(identifiableEClass, ecorePackage.getEBoolean(), "isIdentifyableBy", 1, 1, IS_UNIQUE,
         IS_ORDERED);
      addEParameter(op, this.getIdentifier(), "identifier", 0, 1, IS_UNIQUE, IS_ORDERED);

      addEOperation(identifiableEClass, this.getIdentifier(), "toIdentifier", 1, 1, IS_UNIQUE, IS_ORDERED);

      initEClass(productsFacetEClass, ProductsFacet.class, "ProductsFacet", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getProductsFacet_ProductDefinitions(), this.getProductDefinition(),
         this.getProductDefinition_Parent(), "productDefinitions", null, 0, -1, ProductsFacet.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
         IS_ORDERED);

      initEClass(productDefinitionEClass, ProductDefinition.class, "ProductDefinition", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getProductDefinition_Parent(), this.getProductsFacet(),
         this.getProductsFacet_ProductDefinitions(), "parent", null, 1, 1, ProductDefinition.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
         IS_ORDERED);
      initEAttribute(getProductDefinition_File(), this.getEJavaFile(), "file", null, 1, 1, ProductDefinition.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getProductDefinition_ProductPlugin(), this.getReference(), null, "productPlugin", null, 1, 1,
         ProductDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(referenceEClass, Reference.class, "Reference", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getReference_Id(), ecorePackage.getEString(), "id", null, 1, 1, Reference.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getReference_VersionRange(), ecorePackage.getEString(), "versionRange", "0.0.0", 0, 1,
         Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
         IS_ORDERED);

      op = addEOperation(referenceEClass, ecorePackage.getEBoolean(), "isSatisfiableBy", 1, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, this.getIdentifier(), "identifier", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = addEOperation(referenceEClass, null, "setStrictVersion", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "version", 0, 1, IS_UNIQUE, IS_ORDERED);

      // Initialize data types
      initEDataType(eJavaFileEDataType, File.class, "EJavaFile", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
      initEDataType(eLocaleEDataType, Locale.class, "ELocale", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
      initEDataType(identifierEDataType, Identifier.class, "Identifier", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

      // Create resource
      createResource(eNS_URI);
   }

} // B2ModelPackageImpl
