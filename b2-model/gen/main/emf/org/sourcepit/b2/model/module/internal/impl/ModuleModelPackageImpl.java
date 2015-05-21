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

package org.sourcepit.b2.model.module.internal.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractIdentifiable;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.FileContainer;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.b2.model.module.util.Identifiable;
import org.sourcepit.b2.model.module.util.Identifier;
import org.sourcepit.common.manifest.ManifestPackage;
import org.sourcepit.common.manifest.osgi.BundleManifestPackage;
import org.sourcepit.common.modeling.CommonModelingPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ModuleModelPackageImpl extends EPackageImpl implements ModuleModelPackage {
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass abstractModuleEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass basicModuleEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass abstractFacetEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass compositeModuleEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass pluginsFacetEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass featuresFacetEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass sitesFacetEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass pluginProjectEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass featureProjectEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass siteProjectEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass projectEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass projectFacetEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass fileContainerEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass derivableEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass pluginIncludeEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass categoryEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass abstractIdentifiableEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass productsFacetEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass productDefinitionEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass abstractReferenceEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass ruledReferenceEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass abstractStrictReferenceEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass strictReferenceEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass featureIncludeEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EClass identifiableEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EEnum versionMatchRuleEEnum = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private EDataType identifierEDataType = null;

   /**
    * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
    * EPackage.Registry} by the package
    * package URI value.
    * <p>
    * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
    * performs initialization of the package, or returns the registered package, if one already exists. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.eclipse.emf.ecore.EPackage.Registry
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#eNS_URI
    * @see #init()
    * @generated
    */
   private ModuleModelPackageImpl() {
      super(eNS_URI, ModuleModelFactory.eINSTANCE);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private static boolean isInited = false;

   /**
    * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
    * 
    * <p>
    * This method is used to initialize {@link ModuleModelPackage#eINSTANCE} when that field is accessed. Clients should
    * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
    * --> <!-- end-user-doc -->
    * 
    * @see #eNS_URI
    * @see #createPackageContents()
    * @see #initializePackageContents()
    * @generated
    */
   public static ModuleModelPackage init() {
      if (isInited)
         return (ModuleModelPackage) EPackage.Registry.INSTANCE.getEPackage(ModuleModelPackage.eNS_URI);

      // Obtain or create and register package
      ModuleModelPackageImpl theModuleModelPackage = (ModuleModelPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModuleModelPackageImpl
         ? EPackage.Registry.INSTANCE.get(eNS_URI)
         : new ModuleModelPackageImpl());

      isInited = true;

      // Initialize simple dependencies
      CommonModelingPackage.eINSTANCE.eClass();
      ManifestPackage.eINSTANCE.eClass();

      // Create package meta-data objects
      theModuleModelPackage.createPackageContents();

      // Initialize created meta-data
      theModuleModelPackage.initializePackageContents();

      // Mark meta-data to indicate it can't be changed
      theModuleModelPackage.freeze();


      // Update the registry and return the package
      EPackage.Registry.INSTANCE.put(ModuleModelPackage.eNS_URI, theModuleModelPackage);
      return theModuleModelPackage;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAbstractModule() {
      return abstractModuleEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAbstractModule_LayoutId() {
      return (EAttribute) abstractModuleEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAbstractModule_Locales() {
      return (EAttribute) abstractModuleEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAbstractModule_Facets() {
      return (EReference) abstractModuleEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getBasicModule() {
      return basicModuleEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAbstractFacet() {
      return abstractFacetEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getAbstractFacet_Parent() {
      return (EReference) abstractFacetEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAbstractFacet_Name() {
      return (EAttribute) abstractFacetEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getCompositeModule() {
      return compositeModuleEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getCompositeModule_Modules() {
      return (EReference) compositeModuleEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getPluginsFacet() {
      return pluginsFacetEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getPluginsFacet_Projects() {
      return (EReference) pluginsFacetEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getFeaturesFacet() {
      return featuresFacetEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeaturesFacet_Projects() {
      return (EReference) featuresFacetEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getSitesFacet() {
      return sitesFacetEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getSitesFacet_Projects() {
      return (EReference) sitesFacetEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getPluginProject() {
      return pluginProjectEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getPluginProject_Parent() {
      return (EReference) pluginProjectEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getPluginProject_BundleVersion() {
      return (EAttribute) pluginProjectEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getPluginProject_TestPlugin() {
      return (EAttribute) pluginProjectEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getPluginProject_FragmentHostSymbolicName() {
      return (EAttribute) pluginProjectEClass.getEStructuralFeatures().get(3);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getPluginProject_FragmentHostVersion() {
      return (EAttribute) pluginProjectEClass.getEStructuralFeatures().get(4);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getPluginProject_BundleManifest() {
      return (EReference) pluginProjectEClass.getEStructuralFeatures().get(5);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getFeatureProject() {
      return featureProjectEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeatureProject_Parent() {
      return (EReference) featureProjectEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeatureProject_IncludedPlugins() {
      return (EReference) featureProjectEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeatureProject_IncludedFeatures() {
      return (EReference) featureProjectEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeatureProject_RequiredFeatures() {
      return (EReference) featureProjectEClass.getEStructuralFeatures().get(3);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getFeatureProject_RequiredPlugins() {
      return (EReference) featureProjectEClass.getEStructuralFeatures().get(4);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getSiteProject() {
      return siteProjectEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getSiteProject_Parent() {
      return (EReference) siteProjectEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getSiteProject_Categories() {
      return (EReference) siteProjectEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getSiteProject_InstallableUnits() {
      return (EReference) siteProjectEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getProject() {
      return projectEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getProjectFacet() {
      return projectFacetEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getFileContainer() {
      return fileContainerEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getFileContainer_Directory() {
      return (EAttribute) fileContainerEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getDerivable() {
      return derivableEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getDerivable_Derived() {
      return (EAttribute) derivableEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getPluginInclude() {
      return pluginIncludeEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getPluginInclude_Unpack() {
      return (EAttribute) pluginIncludeEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getCategory() {
      return categoryEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getCategory_InstallableUnits() {
      return (EReference) categoryEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getCategory_Name() {
      return (EAttribute) categoryEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAbstractIdentifiable() {
      return abstractIdentifiableEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAbstractIdentifiable_Id() {
      return (EAttribute) abstractIdentifiableEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAbstractIdentifiable_Version() {
      return (EAttribute) abstractIdentifiableEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getProductsFacet() {
      return productsFacetEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getProductsFacet_ProductDefinitions() {
      return (EReference) productsFacetEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getProductDefinition() {
      return productDefinitionEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getProductDefinition_Parent() {
      return (EReference) productDefinitionEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getProductDefinition_File() {
      return (EAttribute) productDefinitionEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EReference getProductDefinition_ProductPlugin() {
      return (EReference) productDefinitionEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAbstractReference() {
      return abstractReferenceEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAbstractReference_Id() {
      return (EAttribute) abstractReferenceEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getAbstractReference_Version() {
      return (EAttribute) abstractReferenceEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getRuledReference() {
      return ruledReferenceEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getRuledReference_VersionMatchRule() {
      return (EAttribute) ruledReferenceEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getAbstractStrictReference() {
      return abstractStrictReferenceEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getStrictReference() {
      return strictReferenceEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getFeatureInclude() {
      return featureIncludeEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EAttribute getFeatureInclude_Optional() {
      return (EAttribute) featureIncludeEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EClass getIdentifiable() {
      return identifiableEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EEnum getVersionMatchRule() {
      return versionMatchRuleEEnum;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EDataType getIdentifier() {
      return identifierEDataType;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleModelFactory getModuleModelFactory() {
      return (ModuleModelFactory) getEFactoryInstance();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private boolean isCreated = false;

   /**
    * Creates the meta-model objects for the package. This method is
    * guarded to have no affect on any invocation but its first.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void createPackageContents() {
      if (isCreated)
         return;
      isCreated = true;

      // Create classes and their features
      abstractModuleEClass = createEClass(ABSTRACT_MODULE);
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
      createEReference(pluginProjectEClass, PLUGIN_PROJECT__BUNDLE_MANIFEST);

      featureProjectEClass = createEClass(FEATURE_PROJECT);
      createEReference(featureProjectEClass, FEATURE_PROJECT__PARENT);
      createEReference(featureProjectEClass, FEATURE_PROJECT__INCLUDED_PLUGINS);
      createEReference(featureProjectEClass, FEATURE_PROJECT__INCLUDED_FEATURES);
      createEReference(featureProjectEClass, FEATURE_PROJECT__REQUIRED_FEATURES);
      createEReference(featureProjectEClass, FEATURE_PROJECT__REQUIRED_PLUGINS);

      siteProjectEClass = createEClass(SITE_PROJECT);
      createEReference(siteProjectEClass, SITE_PROJECT__PARENT);
      createEReference(siteProjectEClass, SITE_PROJECT__CATEGORIES);
      createEReference(siteProjectEClass, SITE_PROJECT__INSTALLABLE_UNITS);

      projectEClass = createEClass(PROJECT);

      projectFacetEClass = createEClass(PROJECT_FACET);

      fileContainerEClass = createEClass(FILE_CONTAINER);
      createEAttribute(fileContainerEClass, FILE_CONTAINER__DIRECTORY);

      derivableEClass = createEClass(DERIVABLE);
      createEAttribute(derivableEClass, DERIVABLE__DERIVED);

      pluginIncludeEClass = createEClass(PLUGIN_INCLUDE);
      createEAttribute(pluginIncludeEClass, PLUGIN_INCLUDE__UNPACK);

      categoryEClass = createEClass(CATEGORY);
      createEReference(categoryEClass, CATEGORY__INSTALLABLE_UNITS);
      createEAttribute(categoryEClass, CATEGORY__NAME);

      abstractIdentifiableEClass = createEClass(ABSTRACT_IDENTIFIABLE);
      createEAttribute(abstractIdentifiableEClass, ABSTRACT_IDENTIFIABLE__ID);
      createEAttribute(abstractIdentifiableEClass, ABSTRACT_IDENTIFIABLE__VERSION);

      productsFacetEClass = createEClass(PRODUCTS_FACET);
      createEReference(productsFacetEClass, PRODUCTS_FACET__PRODUCT_DEFINITIONS);

      productDefinitionEClass = createEClass(PRODUCT_DEFINITION);
      createEReference(productDefinitionEClass, PRODUCT_DEFINITION__PARENT);
      createEAttribute(productDefinitionEClass, PRODUCT_DEFINITION__FILE);
      createEReference(productDefinitionEClass, PRODUCT_DEFINITION__PRODUCT_PLUGIN);

      abstractReferenceEClass = createEClass(ABSTRACT_REFERENCE);
      createEAttribute(abstractReferenceEClass, ABSTRACT_REFERENCE__ID);
      createEAttribute(abstractReferenceEClass, ABSTRACT_REFERENCE__VERSION);

      ruledReferenceEClass = createEClass(RULED_REFERENCE);
      createEAttribute(ruledReferenceEClass, RULED_REFERENCE__VERSION_MATCH_RULE);

      abstractStrictReferenceEClass = createEClass(ABSTRACT_STRICT_REFERENCE);

      strictReferenceEClass = createEClass(STRICT_REFERENCE);

      featureIncludeEClass = createEClass(FEATURE_INCLUDE);
      createEAttribute(featureIncludeEClass, FEATURE_INCLUDE__OPTIONAL);

      identifiableEClass = createEClass(IDENTIFIABLE);

      // Create enums
      versionMatchRuleEEnum = createEEnum(VERSION_MATCH_RULE);

      // Create data types
      identifierEDataType = createEDataType(IDENTIFIER);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   private boolean isInitialized = false;

   /**
    * Complete the initialization of the package and its meta-model. This
    * method is guarded to have no affect on any invocation but its first.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void initializePackageContents() {
      if (isInitialized)
         return;
      isInitialized = true;

      // Initialize package
      setName(eNAME);
      setNsPrefix(eNS_PREFIX);
      setNsURI(eNS_URI);

      // Obtain other dependent packages
      CommonModelingPackage theCommonModelingPackage = (CommonModelingPackage) EPackage.Registry.INSTANCE.getEPackage(CommonModelingPackage.eNS_URI);
      BundleManifestPackage theBundleManifestPackage = (BundleManifestPackage) EPackage.Registry.INSTANCE.getEPackage(BundleManifestPackage.eNS_URI);

      // Create type parameters
      ETypeParameter projectFacetEClass_P = addETypeParameter(projectFacetEClass, "P");

      // Set bounds for type parameters
      EGenericType g1 = createEGenericType(this.getProject());
      projectFacetEClass_P.getEBounds().add(g1);

      // Add supertypes to classes
      abstractModuleEClass.getESuperTypes().add(this.getFileContainer());
      abstractModuleEClass.getESuperTypes().add(theCommonModelingPackage.getAnnotatable());
      abstractModuleEClass.getESuperTypes().add(this.getAbstractIdentifiable());
      basicModuleEClass.getESuperTypes().add(this.getAbstractModule());
      abstractFacetEClass.getESuperTypes().add(this.getDerivable());
      abstractFacetEClass.getESuperTypes().add(theCommonModelingPackage.getAnnotatable());
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
      siteProjectEClass.getESuperTypes().add(this.getProject());
      projectEClass.getESuperTypes().add(this.getFileContainer());
      projectEClass.getESuperTypes().add(this.getDerivable());
      projectEClass.getESuperTypes().add(theCommonModelingPackage.getAnnotatable());
      projectEClass.getESuperTypes().add(this.getAbstractIdentifiable());
      projectFacetEClass.getESuperTypes().add(this.getAbstractFacet());
      pluginIncludeEClass.getESuperTypes().add(this.getAbstractStrictReference());
      abstractIdentifiableEClass.getESuperTypes().add(this.getIdentifiable());
      productsFacetEClass.getESuperTypes().add(this.getAbstractFacet());
      productDefinitionEClass.getESuperTypes().add(theCommonModelingPackage.getAnnotatable());
      productDefinitionEClass.getESuperTypes().add(this.getDerivable());
      abstractReferenceEClass.getESuperTypes().add(theCommonModelingPackage.getAnnotatable());
      ruledReferenceEClass.getESuperTypes().add(this.getAbstractReference());
      abstractStrictReferenceEClass.getESuperTypes().add(this.getAbstractReference());
      strictReferenceEClass.getESuperTypes().add(this.getAbstractStrictReference());
      featureIncludeEClass.getESuperTypes().add(this.getAbstractStrictReference());

      // Initialize classes and features; add operations and parameters
      initEClass(abstractModuleEClass, AbstractModule.class, "AbstractModule", IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getAbstractModule_LayoutId(), ecorePackage.getEString(), "layoutId", null, 1, 1,
         AbstractModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getAbstractModule_Locales(), theCommonModelingPackage.getELocale(), "locales", null, 0, -1,
         AbstractModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEReference(getAbstractModule_Facets(), this.getAbstractFacet(), this.getAbstractFacet_Parent(), "facets",
         null, 0, -1, AbstractModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
         IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
      addEParameter(op, this.getAbstractReference(), "reference", 1, 1, IS_UNIQUE, IS_ORDERED);
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
         IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getAbstractFacet_Name(), ecorePackage.getEString(), "name", null, 1, 1, AbstractFacet.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(compositeModuleEClass, CompositeModule.class, "CompositeModule", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getCompositeModule_Modules(), this.getAbstractModule(), null, "modules", null, 0, -1,
         CompositeModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(pluginsFacetEClass, PluginsFacet.class, "PluginsFacet", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getPluginsFacet_Projects(), this.getPluginProject(), this.getPluginProject_Parent(), "projects",
         null, 0, -1, PluginsFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(featuresFacetEClass, FeaturesFacet.class, "FeaturesFacet", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getFeaturesFacet_Projects(), this.getFeatureProject(), this.getFeatureProject_Parent(),
         "projects", null, 0, -1, FeaturesFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
         IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(sitesFacetEClass, SitesFacet.class, "SitesFacet", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getSitesFacet_Projects(), this.getSiteProject(), this.getSiteProject_Parent(), "projects", null,
         0, -1, SitesFacet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(pluginProjectEClass, PluginProject.class, "PluginProject", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getPluginProject_Parent(), this.getPluginsFacet(), this.getPluginsFacet_Projects(), "parent",
         null, 0, 1, PluginProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
         IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
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
      initEReference(getPluginProject_BundleManifest(), theBundleManifestPackage.getBundleManifest(), null,
         "bundleManifest", null, 1, 1, PluginProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
         IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      addEOperation(pluginProjectEClass, ecorePackage.getEBoolean(), "isFragment", 1, 1, IS_UNIQUE, IS_ORDERED);

      initEClass(featureProjectEClass, FeatureProject.class, "FeatureProject", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getFeatureProject_Parent(), this.getFeaturesFacet(), this.getFeaturesFacet_Projects(), "parent",
         null, 0, 1, FeatureProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
         IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getFeatureProject_IncludedPlugins(), this.getPluginInclude(), null, "includedPlugins", null, 0,
         -1, FeatureProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getFeatureProject_IncludedFeatures(), this.getFeatureInclude(), null, "includedFeatures", null, 0,
         -1, FeatureProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getFeatureProject_RequiredFeatures(), this.getRuledReference(), null, "requiredFeatures", null, 0,
         -1, FeatureProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getFeatureProject_RequiredPlugins(), this.getRuledReference(), null, "requiredPlugins", null, 0,
         -1, FeatureProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(siteProjectEClass, SiteProject.class, "SiteProject", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getSiteProject_Parent(), this.getSitesFacet(), this.getSitesFacet_Projects(), "parent", null, 0,
         1, SiteProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getSiteProject_Categories(), this.getCategory(), null, "categories", null, 0, -1,
         SiteProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getSiteProject_InstallableUnits(), this.getAbstractStrictReference(), null, "installableUnits",
         null, 0, -1, SiteProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
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
      addEParameter(op, this.getAbstractReference(), "reference", 1, 1, IS_UNIQUE, IS_ORDERED);
      g1 = createEGenericType(projectFacetEClass_P);
      initEOperation(op, g1);

      initEClass(fileContainerEClass, FileContainer.class, "FileContainer", IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getFileContainer_Directory(), theCommonModelingPackage.getEFile(), "directory", null, 0, 1,
         FileContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      initEClass(derivableEClass, Derivable.class, "Derivable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getDerivable_Derived(), ecorePackage.getEBoolean(), "derived", null, 0, 1, Derivable.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(pluginIncludeEClass, PluginInclude.class, "PluginInclude", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getPluginInclude_Unpack(), ecorePackage.getEBoolean(), "unpack", "true", 0, 1,
         PluginInclude.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      initEClass(categoryEClass, Category.class, "Category", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEReference(getCategory_InstallableUnits(), this.getAbstractStrictReference(), null, "installableUnits", null,
         0, -1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getCategory_Name(), ecorePackage.getEString(), "name", null, 1, 1, Category.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(abstractIdentifiableEClass, AbstractIdentifiable.class, "AbstractIdentifiable", IS_ABSTRACT,
         IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getAbstractIdentifiable_Id(), ecorePackage.getEString(), "id", null, 0, 1,
         AbstractIdentifiable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getAbstractIdentifiable_Version(), ecorePackage.getEString(), "version", null, 0, 1,
         AbstractIdentifiable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      op = addEOperation(abstractIdentifiableEClass, ecorePackage.getEBoolean(), "isIdentifyableBy", 1, 1, IS_UNIQUE,
         IS_ORDERED);
      addEParameter(op, this.getIdentifier(), "identifier", 0, 1, IS_UNIQUE, IS_ORDERED);

      addEOperation(abstractIdentifiableEClass, this.getIdentifier(), "toIdentifier", 1, 1, IS_UNIQUE, IS_ORDERED);

      initEClass(productsFacetEClass, ProductsFacet.class, "ProductsFacet", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getProductsFacet_ProductDefinitions(), this.getProductDefinition(),
         this.getProductDefinition_Parent(), "productDefinitions", null, 0, -1, ProductsFacet.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
         IS_ORDERED);

      initEClass(productDefinitionEClass, ProductDefinition.class, "ProductDefinition", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getProductDefinition_Parent(), this.getProductsFacet(),
         this.getProductsFacet_ProductDefinitions(), "parent", null, 1, 1, ProductDefinition.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
         IS_ORDERED);
      initEAttribute(getProductDefinition_File(), theCommonModelingPackage.getEFile(), "file", null, 1, 1,
         ProductDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEReference(getProductDefinition_ProductPlugin(), this.getStrictReference(), null, "productPlugin", null, 1,
         1, ProductDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(abstractReferenceEClass, AbstractReference.class, "AbstractReference", IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getAbstractReference_Id(), ecorePackage.getEString(), "id", null, 1, 1, AbstractReference.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getAbstractReference_Version(), ecorePackage.getEString(), "version", "0.0.0", 0, 1,
         AbstractReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      op = addEOperation(abstractReferenceEClass, ecorePackage.getEBoolean(), "isSatisfiableBy", 1, 1, IS_UNIQUE,
         IS_ORDERED);
      addEParameter(op, this.getIdentifiable(), "identifier", 0, 1, IS_UNIQUE, IS_ORDERED);

      initEClass(ruledReferenceEClass, RuledReference.class, "RuledReference", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getRuledReference_VersionMatchRule(), this.getVersionMatchRule(), "versionMatchRule",
         "compatible", 0, 1, RuledReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID,
         IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(abstractStrictReferenceEClass, AbstractStrictReference.class, "AbstractStrictReference", IS_ABSTRACT,
         !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

      initEClass(strictReferenceEClass, StrictReference.class, "StrictReference", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);

      initEClass(featureIncludeEClass, FeatureInclude.class, "FeatureInclude", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getFeatureInclude_Optional(), ecorePackage.getEBoolean(), "optional", null, 0, 1,
         FeatureInclude.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      initEClass(identifiableEClass, Identifiable.class, "Identifiable", IS_ABSTRACT, IS_INTERFACE,
         !IS_GENERATED_INSTANCE_CLASS);

      // Initialize enums and add enum literals
      initEEnum(versionMatchRuleEEnum, VersionMatchRule.class, "VersionMatchRule");
      addEEnumLiteral(versionMatchRuleEEnum, VersionMatchRule.COMPATIBLE);
      addEEnumLiteral(versionMatchRuleEEnum, VersionMatchRule.PERFECT);
      addEEnumLiteral(versionMatchRuleEEnum, VersionMatchRule.EQUIVALENT);
      addEEnumLiteral(versionMatchRuleEEnum, VersionMatchRule.GREATER_OR_EQUAL);

      // Initialize data types
      initEDataType(identifierEDataType, Identifier.class, "Identifier", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

      // Create resource
      createResource(eNS_URI);
   }

} // ModuleModelPackageImpl
