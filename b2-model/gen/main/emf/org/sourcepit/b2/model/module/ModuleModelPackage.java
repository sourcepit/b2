/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sourcepit.common.modeling.CommonModelingPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModuleModelPackage extends EPackage
{
   /**
    * The package name.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNAME = "module";

   /**
    * The package namespace URI.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_URI = "http://www.sourcepit.org/b2/module/0.1/";

   /**
    * The package namespace name.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_PREFIX = "b2-module";

   /**
    * The package content type ID.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   String eCONTENT_TYPE = "b2-module";

   /**
    * The singleton instance of the package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   ModuleModelPackage eINSTANCE = org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl.init();

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.FileContainerImpl
    * <em>File Container</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.FileContainerImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getFileContainer()
    * @generated
    */
   int FILE_CONTAINER = 12;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FILE_CONTAINER__DIRECTORY = 0;

   /**
    * The number of structural features of the '<em>File Container</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FILE_CONTAINER_FEATURE_COUNT = 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.AbstractModuleImpl
    * <em>Abstract Module</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.AbstractModuleImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getAbstractModule()
    * @generated
    */
   int ABSTRACT_MODULE = 0;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__DIRECTORY = FILE_CONTAINER__DIRECTORY;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__ANNOTATIONS = FILE_CONTAINER_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__ID = FILE_CONTAINER_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__VERSION = FILE_CONTAINER_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Layout Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__LAYOUT_ID = FILE_CONTAINER_FEATURE_COUNT + 3;

   /**
    * The feature id for the '<em><b>Locales</b></em>' attribute list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__LOCALES = FILE_CONTAINER_FEATURE_COUNT + 4;

   /**
    * The feature id for the '<em><b>Facets</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__FACETS = FILE_CONTAINER_FEATURE_COUNT + 5;

   /**
    * The number of structural features of the '<em>Abstract Module</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE_FEATURE_COUNT = FILE_CONTAINER_FEATURE_COUNT + 6;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.BasicModuleImpl
    * <em>Basic Module</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.BasicModuleImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getBasicModule()
    * @generated
    */
   int BASIC_MODULE = 1;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__DIRECTORY = ABSTRACT_MODULE__DIRECTORY;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__ANNOTATIONS = ABSTRACT_MODULE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__ID = ABSTRACT_MODULE__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__VERSION = ABSTRACT_MODULE__VERSION;

   /**
    * The feature id for the '<em><b>Layout Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__LAYOUT_ID = ABSTRACT_MODULE__LAYOUT_ID;

   /**
    * The feature id for the '<em><b>Locales</b></em>' attribute list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__LOCALES = ABSTRACT_MODULE__LOCALES;

   /**
    * The feature id for the '<em><b>Facets</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__FACETS = ABSTRACT_MODULE__FACETS;

   /**
    * The number of structural features of the '<em>Basic Module</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE_FEATURE_COUNT = ABSTRACT_MODULE_FEATURE_COUNT + 0;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.Derivable <em>Derivable</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.Derivable
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getDerivable()
    * @generated
    */
   int DERIVABLE = 13;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int DERIVABLE__DERIVED = 0;

   /**
    * The number of structural features of the '<em>Derivable</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int DERIVABLE_FEATURE_COUNT = 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.AbstractFacetImpl
    * <em>Abstract Facet</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.AbstractFacetImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getAbstractFacet()
    * @generated
    */
   int ABSTRACT_FACET = 2;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_FACET__DERIVED = DERIVABLE__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_FACET__ANNOTATIONS = DERIVABLE_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_FACET__PARENT = DERIVABLE_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_FACET__NAME = DERIVABLE_FEATURE_COUNT + 2;

   /**
    * The number of structural features of the '<em>Abstract Facet</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_FACET_FEATURE_COUNT = DERIVABLE_FEATURE_COUNT + 3;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.CompositeModuleImpl
    * <em>Composite Module</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.CompositeModuleImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getCompositeModule()
    * @generated
    */
   int COMPOSITE_MODULE = 3;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__DIRECTORY = ABSTRACT_MODULE__DIRECTORY;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__ANNOTATIONS = ABSTRACT_MODULE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__ID = ABSTRACT_MODULE__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__VERSION = ABSTRACT_MODULE__VERSION;

   /**
    * The feature id for the '<em><b>Layout Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__LAYOUT_ID = ABSTRACT_MODULE__LAYOUT_ID;

   /**
    * The feature id for the '<em><b>Locales</b></em>' attribute list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__LOCALES = ABSTRACT_MODULE__LOCALES;

   /**
    * The feature id for the '<em><b>Facets</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__FACETS = ABSTRACT_MODULE__FACETS;

   /**
    * The feature id for the '<em><b>Modules</b></em>' reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__MODULES = ABSTRACT_MODULE_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Composite Module</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE_FEATURE_COUNT = ABSTRACT_MODULE_FEATURE_COUNT + 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.ProjectFacetImpl
    * <em>Project Facet</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.ProjectFacetImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getProjectFacet()
    * @generated
    */
   int PROJECT_FACET = 11;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FACET__DERIVED = ABSTRACT_FACET__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FACET__ANNOTATIONS = ABSTRACT_FACET__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FACET__PARENT = ABSTRACT_FACET__PARENT;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FACET__NAME = ABSTRACT_FACET__NAME;

   /**
    * The number of structural features of the '<em>Project Facet</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FACET_FEATURE_COUNT = ABSTRACT_FACET_FEATURE_COUNT + 0;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.PluginsFacetImpl
    * <em>Plugins Facet</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.PluginsFacetImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getPluginsFacet()
    * @generated
    */
   int PLUGINS_FACET = 4;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET__DERIVED = PROJECT_FACET__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET__ANNOTATIONS = PROJECT_FACET__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET__PARENT = PROJECT_FACET__PARENT;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET__NAME = PROJECT_FACET__NAME;

   /**
    * The feature id for the '<em><b>Projects</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET__PROJECTS = PROJECT_FACET_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Plugins Facet</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET_FEATURE_COUNT = PROJECT_FACET_FEATURE_COUNT + 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.FeaturesFacetImpl
    * <em>Features Facet</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.FeaturesFacetImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getFeaturesFacet()
    * @generated
    */
   int FEATURES_FACET = 5;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET__DERIVED = PROJECT_FACET__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET__ANNOTATIONS = PROJECT_FACET__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET__PARENT = PROJECT_FACET__PARENT;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET__NAME = PROJECT_FACET__NAME;

   /**
    * The feature id for the '<em><b>Projects</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET__PROJECTS = PROJECT_FACET_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Features Facet</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET_FEATURE_COUNT = PROJECT_FACET_FEATURE_COUNT + 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.SitesFacetImpl
    * <em>Sites Facet</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.SitesFacetImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getSitesFacet()
    * @generated
    */
   int SITES_FACET = 6;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET__DERIVED = PROJECT_FACET__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET__ANNOTATIONS = PROJECT_FACET__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET__PARENT = PROJECT_FACET__PARENT;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET__NAME = PROJECT_FACET__NAME;

   /**
    * The feature id for the '<em><b>Projects</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET__PROJECTS = PROJECT_FACET_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Sites Facet</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET_FEATURE_COUNT = PROJECT_FACET_FEATURE_COUNT + 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.ProjectImpl <em>Project</em>}'
    * class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.ProjectImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getProject()
    * @generated
    */
   int PROJECT = 10;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT__DIRECTORY = FILE_CONTAINER__DIRECTORY;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT__DERIVED = FILE_CONTAINER_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT__ANNOTATIONS = FILE_CONTAINER_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT__ID = FILE_CONTAINER_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT__VERSION = FILE_CONTAINER_FEATURE_COUNT + 3;

   /**
    * The number of structural features of the '<em>Project</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FEATURE_COUNT = FILE_CONTAINER_FEATURE_COUNT + 4;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.PluginProjectImpl
    * <em>Plugin Project</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.PluginProjectImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getPluginProject()
    * @generated
    */
   int PLUGIN_PROJECT = 7;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__DIRECTORY = PROJECT__DIRECTORY;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__DERIVED = PROJECT__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__ANNOTATIONS = PROJECT__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__ID = PROJECT__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__VERSION = PROJECT__VERSION;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__PARENT = PROJECT_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Bundle Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__BUNDLE_VERSION = PROJECT_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Test Plugin</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__TEST_PLUGIN = PROJECT_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Fragment Host Symbolic Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__FRAGMENT_HOST_SYMBOLIC_NAME = PROJECT_FEATURE_COUNT + 3;

   /**
    * The feature id for the '<em><b>Fragment Host Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__FRAGMENT_HOST_VERSION = PROJECT_FEATURE_COUNT + 4;

   /**
    * The feature id for the '<em><b>Bundle Manifest</b></em>' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__BUNDLE_MANIFEST = PROJECT_FEATURE_COUNT + 5;

   /**
    * The number of structural features of the '<em>Plugin Project</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT_FEATURE_COUNT = PROJECT_FEATURE_COUNT + 6;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.FeatureProjectImpl
    * <em>Feature Project</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.FeatureProjectImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getFeatureProject()
    * @generated
    */
   int FEATURE_PROJECT = 8;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__DIRECTORY = PROJECT__DIRECTORY;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__DERIVED = PROJECT__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__ANNOTATIONS = PROJECT__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__ID = PROJECT__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__VERSION = PROJECT__VERSION;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__PARENT = PROJECT_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Included Plugins</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__INCLUDED_PLUGINS = PROJECT_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Included Features</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__INCLUDED_FEATURES = PROJECT_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Required Features</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__REQUIRED_FEATURES = PROJECT_FEATURE_COUNT + 3;

   /**
    * The feature id for the '<em><b>Required Plugins</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__REQUIRED_PLUGINS = PROJECT_FEATURE_COUNT + 4;

   /**
    * The number of structural features of the '<em>Feature Project</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT_FEATURE_COUNT = PROJECT_FEATURE_COUNT + 5;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.SiteProjectImpl
    * <em>Site Project</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.SiteProjectImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getSiteProject()
    * @generated
    */
   int SITE_PROJECT = 9;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__DIRECTORY = PROJECT__DIRECTORY;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__DERIVED = PROJECT__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__ANNOTATIONS = PROJECT__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__ID = PROJECT__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__VERSION = PROJECT__VERSION;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__PARENT = PROJECT_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Categories</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__CATEGORIES = PROJECT_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Installable Units</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__INSTALLABLE_UNITS = PROJECT_FEATURE_COUNT + 2;

   /**
    * The number of structural features of the '<em>Site Project</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT_FEATURE_COUNT = PROJECT_FEATURE_COUNT + 3;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.AbstractReferenceImpl
    * <em>Abstract Reference</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.AbstractReferenceImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getAbstractReference()
    * @generated
    */
   int ABSTRACT_REFERENCE = 19;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_REFERENCE__ANNOTATIONS = CommonModelingPackage.ANNOTATABLE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_REFERENCE__ID = CommonModelingPackage.ANNOTATABLE_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_REFERENCE__VERSION = CommonModelingPackage.ANNOTATABLE_FEATURE_COUNT + 1;

   /**
    * The number of structural features of the '<em>Abstract Reference</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_REFERENCE_FEATURE_COUNT = CommonModelingPackage.ANNOTATABLE_FEATURE_COUNT + 2;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.AbstractStrictReferenceImpl
    * <em>Abstract Strict Reference</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.AbstractStrictReferenceImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getAbstractStrictReference()
    * @generated
    */
   int ABSTRACT_STRICT_REFERENCE = 21;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_STRICT_REFERENCE__ANNOTATIONS = ABSTRACT_REFERENCE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_STRICT_REFERENCE__ID = ABSTRACT_REFERENCE__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_STRICT_REFERENCE__VERSION = ABSTRACT_REFERENCE__VERSION;

   /**
    * The number of structural features of the '<em>Abstract Strict Reference</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_STRICT_REFERENCE_FEATURE_COUNT = ABSTRACT_REFERENCE_FEATURE_COUNT + 0;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.PluginIncludeImpl
    * <em>Plugin Include</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.PluginIncludeImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getPluginInclude()
    * @generated
    */
   int PLUGIN_INCLUDE = 14;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_INCLUDE__ANNOTATIONS = ABSTRACT_STRICT_REFERENCE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_INCLUDE__ID = ABSTRACT_STRICT_REFERENCE__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_INCLUDE__VERSION = ABSTRACT_STRICT_REFERENCE__VERSION;

   /**
    * The feature id for the '<em><b>Unpack</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_INCLUDE__UNPACK = ABSTRACT_STRICT_REFERENCE_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Plugin Include</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_INCLUDE_FEATURE_COUNT = ABSTRACT_STRICT_REFERENCE_FEATURE_COUNT + 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.CategoryImpl <em>Category</em>}'
    * class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.CategoryImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getCategory()
    * @generated
    */
   int CATEGORY = 15;

   /**
    * The feature id for the '<em><b>Installable Units</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int CATEGORY__INSTALLABLE_UNITS = 0;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int CATEGORY__NAME = 1;

   /**
    * The number of structural features of the '<em>Category</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int CATEGORY_FEATURE_COUNT = 2;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.util.Identifiable <em>Identifiable</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.util.Identifiable
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getIdentifiable()
    * @generated
    */
   int IDENTIFIABLE = 24;

   /**
    * The number of structural features of the '<em>Identifiable</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int IDENTIFIABLE_FEATURE_COUNT = 0;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.AbstractIdentifiable
    * <em>Abstract Identifiable</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.AbstractIdentifiable
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getAbstractIdentifiable()
    * @generated
    */
   int ABSTRACT_IDENTIFIABLE = 16;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_IDENTIFIABLE__ID = IDENTIFIABLE_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_IDENTIFIABLE__VERSION = IDENTIFIABLE_FEATURE_COUNT + 1;

   /**
    * The number of structural features of the '<em>Abstract Identifiable</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_IDENTIFIABLE_FEATURE_COUNT = IDENTIFIABLE_FEATURE_COUNT + 2;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.ProductsFacetImpl
    * <em>Products Facet</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.ProductsFacetImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getProductsFacet()
    * @generated
    */
   int PRODUCTS_FACET = 17;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET__DERIVED = ABSTRACT_FACET__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET__ANNOTATIONS = ABSTRACT_FACET__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET__PARENT = ABSTRACT_FACET__PARENT;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET__NAME = ABSTRACT_FACET__NAME;

   /**
    * The feature id for the '<em><b>Product Definitions</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET__PRODUCT_DEFINITIONS = ABSTRACT_FACET_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Products Facet</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET_FEATURE_COUNT = ABSTRACT_FACET_FEATURE_COUNT + 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.ProductDefinitionImpl
    * <em>Product Definition</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.ProductDefinitionImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getProductDefinition()
    * @generated
    */
   int PRODUCT_DEFINITION = 18;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION__ANNOTATIONS = CommonModelingPackage.ANNOTATABLE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION__DERIVED = CommonModelingPackage.ANNOTATABLE_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION__PARENT = CommonModelingPackage.ANNOTATABLE_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>File</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION__FILE = CommonModelingPackage.ANNOTATABLE_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Product Plugin</b></em>' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION__PRODUCT_PLUGIN = CommonModelingPackage.ANNOTATABLE_FEATURE_COUNT + 3;

   /**
    * The number of structural features of the '<em>Product Definition</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION_FEATURE_COUNT = CommonModelingPackage.ANNOTATABLE_FEATURE_COUNT + 4;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.RuledReferenceImpl
    * <em>Ruled Reference</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.RuledReferenceImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getRuledReference()
    * @generated
    */
   int RULED_REFERENCE = 20;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int RULED_REFERENCE__ANNOTATIONS = ABSTRACT_REFERENCE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int RULED_REFERENCE__ID = ABSTRACT_REFERENCE__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int RULED_REFERENCE__VERSION = ABSTRACT_REFERENCE__VERSION;

   /**
    * The feature id for the '<em><b>Version Match Rule</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int RULED_REFERENCE__VERSION_MATCH_RULE = ABSTRACT_REFERENCE_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Ruled Reference</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int RULED_REFERENCE_FEATURE_COUNT = ABSTRACT_REFERENCE_FEATURE_COUNT + 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.StrictReferenceImpl
    * <em>Strict Reference</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.StrictReferenceImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getStrictReference()
    * @generated
    */
   int STRICT_REFERENCE = 22;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int STRICT_REFERENCE__ANNOTATIONS = ABSTRACT_STRICT_REFERENCE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int STRICT_REFERENCE__ID = ABSTRACT_STRICT_REFERENCE__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int STRICT_REFERENCE__VERSION = ABSTRACT_STRICT_REFERENCE__VERSION;

   /**
    * The number of structural features of the '<em>Strict Reference</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int STRICT_REFERENCE_FEATURE_COUNT = ABSTRACT_STRICT_REFERENCE_FEATURE_COUNT + 0;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.internal.impl.FeatureIncludeImpl
    * <em>Feature Include</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.internal.impl.FeatureIncludeImpl
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getFeatureInclude()
    * @generated
    */
   int FEATURE_INCLUDE = 23;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_INCLUDE__ANNOTATIONS = ABSTRACT_STRICT_REFERENCE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_INCLUDE__ID = ABSTRACT_STRICT_REFERENCE__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_INCLUDE__VERSION = ABSTRACT_STRICT_REFERENCE__VERSION;

   /**
    * The feature id for the '<em><b>Optional</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_INCLUDE__OPTIONAL = ABSTRACT_STRICT_REFERENCE_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Feature Include</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_INCLUDE_FEATURE_COUNT = ABSTRACT_STRICT_REFERENCE_FEATURE_COUNT + 1;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.module.VersionMatchRule <em>Version Match Rule</em>}'
    * enum.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.VersionMatchRule
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getVersionMatchRule()
    * @generated
    */
   int VERSION_MATCH_RULE = 25;

   /**
    * The meta object id for the '<em>Identifier</em>' data type.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.module.util.Identifier
    * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getIdentifier()
    * @generated
    */
   int IDENTIFIER = 26;


   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.AbstractModule <em>Abstract Module</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Abstract Module</em>'.
    * @see org.sourcepit.b2.model.module.AbstractModule
    * @generated
    */
   EClass getAbstractModule();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.AbstractModule#getLayoutId
    * <em>Layout Id</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Layout Id</em>'.
    * @see org.sourcepit.b2.model.module.AbstractModule#getLayoutId()
    * @see #getAbstractModule()
    * @generated
    */
   EAttribute getAbstractModule_LayoutId();

   /**
    * Returns the meta object for the attribute list '{@link org.sourcepit.b2.model.module.AbstractModule#getLocales
    * <em>Locales</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute list '<em>Locales</em>'.
    * @see org.sourcepit.b2.model.module.AbstractModule#getLocales()
    * @see #getAbstractModule()
    * @generated
    */
   EAttribute getAbstractModule_Locales();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.AbstractModule#getFacets <em>Facets</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Facets</em>'.
    * @see org.sourcepit.b2.model.module.AbstractModule#getFacets()
    * @see #getAbstractModule()
    * @generated
    */
   EReference getAbstractModule_Facets();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.BasicModule <em>Basic Module</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Basic Module</em>'.
    * @see org.sourcepit.b2.model.module.BasicModule
    * @generated
    */
   EClass getBasicModule();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.AbstractFacet <em>Abstract Facet</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Abstract Facet</em>'.
    * @see org.sourcepit.b2.model.module.AbstractFacet
    * @generated
    */
   EClass getAbstractFacet();

   /**
    * Returns the meta object for the container reference '{@link org.sourcepit.b2.model.module.AbstractFacet#getParent
    * <em>Parent</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.b2.model.module.AbstractFacet#getParent()
    * @see #getAbstractFacet()
    * @generated
    */
   EReference getAbstractFacet_Parent();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.AbstractFacet#getName
    * <em>Name</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Name</em>'.
    * @see org.sourcepit.b2.model.module.AbstractFacet#getName()
    * @see #getAbstractFacet()
    * @generated
    */
   EAttribute getAbstractFacet_Name();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.CompositeModule <em>Composite Module</em>}
    * '.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Composite Module</em>'.
    * @see org.sourcepit.b2.model.module.CompositeModule
    * @generated
    */
   EClass getCompositeModule();

   /**
    * Returns the meta object for the reference list '{@link org.sourcepit.b2.model.module.CompositeModule#getModules
    * <em>Modules</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the reference list '<em>Modules</em>'.
    * @see org.sourcepit.b2.model.module.CompositeModule#getModules()
    * @see #getCompositeModule()
    * @generated
    */
   EReference getCompositeModule_Modules();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.PluginsFacet <em>Plugins Facet</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Plugins Facet</em>'.
    * @see org.sourcepit.b2.model.module.PluginsFacet
    * @generated
    */
   EClass getPluginsFacet();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.PluginsFacet#getProjects <em>Projects</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Projects</em>'.
    * @see org.sourcepit.b2.model.module.PluginsFacet#getProjects()
    * @see #getPluginsFacet()
    * @generated
    */
   EReference getPluginsFacet_Projects();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.FeaturesFacet <em>Features Facet</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Features Facet</em>'.
    * @see org.sourcepit.b2.model.module.FeaturesFacet
    * @generated
    */
   EClass getFeaturesFacet();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.FeaturesFacet#getProjects <em>Projects</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Projects</em>'.
    * @see org.sourcepit.b2.model.module.FeaturesFacet#getProjects()
    * @see #getFeaturesFacet()
    * @generated
    */
   EReference getFeaturesFacet_Projects();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.SitesFacet <em>Sites Facet</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Sites Facet</em>'.
    * @see org.sourcepit.b2.model.module.SitesFacet
    * @generated
    */
   EClass getSitesFacet();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.SitesFacet#getProjects <em>Projects</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Projects</em>'.
    * @see org.sourcepit.b2.model.module.SitesFacet#getProjects()
    * @see #getSitesFacet()
    * @generated
    */
   EReference getSitesFacet_Projects();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.PluginProject <em>Plugin Project</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Plugin Project</em>'.
    * @see org.sourcepit.b2.model.module.PluginProject
    * @generated
    */
   EClass getPluginProject();

   /**
    * Returns the meta object for the container reference '{@link org.sourcepit.b2.model.module.PluginProject#getParent
    * <em>Parent</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.b2.model.module.PluginProject#getParent()
    * @see #getPluginProject()
    * @generated
    */
   EReference getPluginProject_Parent();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.PluginProject#getBundleVersion
    * <em>Bundle Version</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Bundle Version</em>'.
    * @see org.sourcepit.b2.model.module.PluginProject#getBundleVersion()
    * @see #getPluginProject()
    * @generated
    */
   EAttribute getPluginProject_BundleVersion();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.PluginProject#isTestPlugin
    * <em>Test Plugin</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Test Plugin</em>'.
    * @see org.sourcepit.b2.model.module.PluginProject#isTestPlugin()
    * @see #getPluginProject()
    * @generated
    */
   EAttribute getPluginProject_TestPlugin();

   /**
    * Returns the meta object for the attribute '
    * {@link org.sourcepit.b2.model.module.PluginProject#getFragmentHostSymbolicName
    * <em>Fragment Host Symbolic Name</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Fragment Host Symbolic Name</em>'.
    * @see org.sourcepit.b2.model.module.PluginProject#getFragmentHostSymbolicName()
    * @see #getPluginProject()
    * @generated
    */
   EAttribute getPluginProject_FragmentHostSymbolicName();

   /**
    * Returns the meta object for the attribute '
    * {@link org.sourcepit.b2.model.module.PluginProject#getFragmentHostVersion <em>Fragment Host Version</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Fragment Host Version</em>'.
    * @see org.sourcepit.b2.model.module.PluginProject#getFragmentHostVersion()
    * @see #getPluginProject()
    * @generated
    */
   EAttribute getPluginProject_FragmentHostVersion();

   /**
    * Returns the meta object for the containment reference '
    * {@link org.sourcepit.b2.model.module.PluginProject#getBundleManifest <em>Bundle Manifest</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference '<em>Bundle Manifest</em>'.
    * @see org.sourcepit.b2.model.module.PluginProject#getBundleManifest()
    * @see #getPluginProject()
    * @generated
    */
   EReference getPluginProject_BundleManifest();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.FeatureProject <em>Feature Project</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Feature Project</em>'.
    * @see org.sourcepit.b2.model.module.FeatureProject
    * @generated
    */
   EClass getFeatureProject();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.b2.model.module.FeatureProject#getParent <em>Parent</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.b2.model.module.FeatureProject#getParent()
    * @see #getFeatureProject()
    * @generated
    */
   EReference getFeatureProject_Parent();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.FeatureProject#getIncludedPlugins <em>Included Plugins</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Included Plugins</em>'.
    * @see org.sourcepit.b2.model.module.FeatureProject#getIncludedPlugins()
    * @see #getFeatureProject()
    * @generated
    */
   EReference getFeatureProject_IncludedPlugins();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.FeatureProject#getIncludedFeatures <em>Included Features</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Included Features</em>'.
    * @see org.sourcepit.b2.model.module.FeatureProject#getIncludedFeatures()
    * @see #getFeatureProject()
    * @generated
    */
   EReference getFeatureProject_IncludedFeatures();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.FeatureProject#getRequiredFeatures <em>Required Features</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Required Features</em>'.
    * @see org.sourcepit.b2.model.module.FeatureProject#getRequiredFeatures()
    * @see #getFeatureProject()
    * @generated
    */
   EReference getFeatureProject_RequiredFeatures();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.FeatureProject#getRequiredPlugins <em>Required Plugins</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Required Plugins</em>'.
    * @see org.sourcepit.b2.model.module.FeatureProject#getRequiredPlugins()
    * @see #getFeatureProject()
    * @generated
    */
   EReference getFeatureProject_RequiredPlugins();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.SiteProject <em>Site Project</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Site Project</em>'.
    * @see org.sourcepit.b2.model.module.SiteProject
    * @generated
    */
   EClass getSiteProject();

   /**
    * Returns the meta object for the container reference '{@link org.sourcepit.b2.model.module.SiteProject#getParent
    * <em>Parent</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.b2.model.module.SiteProject#getParent()
    * @see #getSiteProject()
    * @generated
    */
   EReference getSiteProject_Parent();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.SiteProject#getCategories <em>Categories</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Categories</em>'.
    * @see org.sourcepit.b2.model.module.SiteProject#getCategories()
    * @see #getSiteProject()
    * @generated
    */
   EReference getSiteProject_Categories();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.SiteProject#getInstallableUnits <em>Installable Units</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Installable Units</em>'.
    * @see org.sourcepit.b2.model.module.SiteProject#getInstallableUnits()
    * @see #getSiteProject()
    * @generated
    */
   EReference getSiteProject_InstallableUnits();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.Project <em>Project</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Project</em>'.
    * @see org.sourcepit.b2.model.module.Project
    * @generated
    */
   EClass getProject();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.ProjectFacet <em>Project Facet</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Project Facet</em>'.
    * @see org.sourcepit.b2.model.module.ProjectFacet
    * @generated
    */
   EClass getProjectFacet();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.FileContainer <em>File Container</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>File Container</em>'.
    * @see org.sourcepit.b2.model.module.FileContainer
    * @generated
    */
   EClass getFileContainer();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.FileContainer#getDirectory
    * <em>Directory</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Directory</em>'.
    * @see org.sourcepit.b2.model.module.FileContainer#getDirectory()
    * @see #getFileContainer()
    * @generated
    */
   EAttribute getFileContainer_Directory();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.Derivable <em>Derivable</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Derivable</em>'.
    * @see org.sourcepit.b2.model.module.Derivable
    * @generated
    */
   EClass getDerivable();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.Derivable#isDerived
    * <em>Derived</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Derived</em>'.
    * @see org.sourcepit.b2.model.module.Derivable#isDerived()
    * @see #getDerivable()
    * @generated
    */
   EAttribute getDerivable_Derived();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.PluginInclude <em>Plugin Include</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Plugin Include</em>'.
    * @see org.sourcepit.b2.model.module.PluginInclude
    * @generated
    */
   EClass getPluginInclude();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.PluginInclude#isUnpack
    * <em>Unpack</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Unpack</em>'.
    * @see org.sourcepit.b2.model.module.PluginInclude#isUnpack()
    * @see #getPluginInclude()
    * @generated
    */
   EAttribute getPluginInclude_Unpack();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.Category <em>Category</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Category</em>'.
    * @see org.sourcepit.b2.model.module.Category
    * @generated
    */
   EClass getCategory();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.Category#getInstallableUnits <em>Installable Units</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Installable Units</em>'.
    * @see org.sourcepit.b2.model.module.Category#getInstallableUnits()
    * @see #getCategory()
    * @generated
    */
   EReference getCategory_InstallableUnits();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.Category#getName <em>Name</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Name</em>'.
    * @see org.sourcepit.b2.model.module.Category#getName()
    * @see #getCategory()
    * @generated
    */
   EAttribute getCategory_Name();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.AbstractIdentifiable
    * <em>Abstract Identifiable</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Abstract Identifiable</em>'.
    * @see org.sourcepit.b2.model.module.AbstractIdentifiable
    * @generated
    */
   EClass getAbstractIdentifiable();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.AbstractIdentifiable#getId
    * <em>Id</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Id</em>'.
    * @see org.sourcepit.b2.model.module.AbstractIdentifiable#getId()
    * @see #getAbstractIdentifiable()
    * @generated
    */
   EAttribute getAbstractIdentifiable_Id();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.AbstractIdentifiable#getVersion
    * <em>Version</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Version</em>'.
    * @see org.sourcepit.b2.model.module.AbstractIdentifiable#getVersion()
    * @see #getAbstractIdentifiable()
    * @generated
    */
   EAttribute getAbstractIdentifiable_Version();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.ProductsFacet <em>Products Facet</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Products Facet</em>'.
    * @see org.sourcepit.b2.model.module.ProductsFacet
    * @generated
    */
   EClass getProductsFacet();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.module.ProductsFacet#getProductDefinitions <em>Product Definitions</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Product Definitions</em>'.
    * @see org.sourcepit.b2.model.module.ProductsFacet#getProductDefinitions()
    * @see #getProductsFacet()
    * @generated
    */
   EReference getProductsFacet_ProductDefinitions();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.ProductDefinition
    * <em>Product Definition</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Product Definition</em>'.
    * @see org.sourcepit.b2.model.module.ProductDefinition
    * @generated
    */
   EClass getProductDefinition();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.b2.model.module.ProductDefinition#getParent <em>Parent</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.b2.model.module.ProductDefinition#getParent()
    * @see #getProductDefinition()
    * @generated
    */
   EReference getProductDefinition_Parent();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.ProductDefinition#getFile
    * <em>File</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>File</em>'.
    * @see org.sourcepit.b2.model.module.ProductDefinition#getFile()
    * @see #getProductDefinition()
    * @generated
    */
   EAttribute getProductDefinition_File();

   /**
    * Returns the meta object for the containment reference '
    * {@link org.sourcepit.b2.model.module.ProductDefinition#getProductPlugin <em>Product Plugin</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference '<em>Product Plugin</em>'.
    * @see org.sourcepit.b2.model.module.ProductDefinition#getProductPlugin()
    * @see #getProductDefinition()
    * @generated
    */
   EReference getProductDefinition_ProductPlugin();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.AbstractReference
    * <em>Abstract Reference</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Abstract Reference</em>'.
    * @see org.sourcepit.b2.model.module.AbstractReference
    * @generated
    */
   EClass getAbstractReference();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.AbstractReference#getId
    * <em>Id</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Id</em>'.
    * @see org.sourcepit.b2.model.module.AbstractReference#getId()
    * @see #getAbstractReference()
    * @generated
    */
   EAttribute getAbstractReference_Id();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.AbstractReference#getVersion
    * <em>Version</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Version</em>'.
    * @see org.sourcepit.b2.model.module.AbstractReference#getVersion()
    * @see #getAbstractReference()
    * @generated
    */
   EAttribute getAbstractReference_Version();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.RuledReference <em>Ruled Reference</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Ruled Reference</em>'.
    * @see org.sourcepit.b2.model.module.RuledReference
    * @generated
    */
   EClass getRuledReference();

   /**
    * Returns the meta object for the attribute '
    * {@link org.sourcepit.b2.model.module.RuledReference#getVersionMatchRule <em>Version Match Rule</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Version Match Rule</em>'.
    * @see org.sourcepit.b2.model.module.RuledReference#getVersionMatchRule()
    * @see #getRuledReference()
    * @generated
    */
   EAttribute getRuledReference_VersionMatchRule();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.AbstractStrictReference
    * <em>Abstract Strict Reference</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Abstract Strict Reference</em>'.
    * @see org.sourcepit.b2.model.module.AbstractStrictReference
    * @generated
    */
   EClass getAbstractStrictReference();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.StrictReference <em>Strict Reference</em>}
    * '.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Strict Reference</em>'.
    * @see org.sourcepit.b2.model.module.StrictReference
    * @generated
    */
   EClass getStrictReference();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.FeatureInclude <em>Feature Include</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Feature Include</em>'.
    * @see org.sourcepit.b2.model.module.FeatureInclude
    * @generated
    */
   EClass getFeatureInclude();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.module.FeatureInclude#isOptional
    * <em>Optional</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Optional</em>'.
    * @see org.sourcepit.b2.model.module.FeatureInclude#isOptional()
    * @see #getFeatureInclude()
    * @generated
    */
   EAttribute getFeatureInclude_Optional();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.module.util.Identifiable <em>Identifiable</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Identifiable</em>'.
    * @see org.sourcepit.b2.model.module.util.Identifiable
    * @model instanceClass="org.sourcepit.b2.model.module.util.Identifiable"
    * @generated
    */
   EClass getIdentifiable();

   /**
    * Returns the meta object for enum '{@link org.sourcepit.b2.model.module.VersionMatchRule
    * <em>Version Match Rule</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for enum '<em>Version Match Rule</em>'.
    * @see org.sourcepit.b2.model.module.VersionMatchRule
    * @generated
    */
   EEnum getVersionMatchRule();

   /**
    * Returns the meta object for data type '{@link org.sourcepit.b2.model.module.util.Identifier <em>Identifier</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for data type '<em>Identifier</em>'.
    * @see org.sourcepit.b2.model.module.util.Identifier
    * @model instanceClass="org.sourcepit.b2.model.module.util.Identifier"
    * @generated
    */
   EDataType getIdentifier();

   /**
    * Returns the factory that creates the instances of the model.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the factory that creates the instances of the model.
    * @generated
    */
   ModuleModelFactory getModuleModelFactory();

   /**
    * <!-- begin-user-doc -->
    * Defines literals for the meta objects that represent
    * <ul>
    * <li>each class,</li>
    * <li>each feature of each class,</li>
    * <li>each enum,</li>
    * <li>and each data type</li>
    * </ul>
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   interface Literals
   {
      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.AbstractModuleImpl
       * <em>Abstract Module</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.AbstractModuleImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getAbstractModule()
       * @generated
       */
      EClass ABSTRACT_MODULE = eINSTANCE.getAbstractModule();

      /**
       * The meta object literal for the '<em><b>Layout Id</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute ABSTRACT_MODULE__LAYOUT_ID = eINSTANCE.getAbstractModule_LayoutId();

      /**
       * The meta object literal for the '<em><b>Locales</b></em>' attribute list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute ABSTRACT_MODULE__LOCALES = eINSTANCE.getAbstractModule_Locales();

      /**
       * The meta object literal for the '<em><b>Facets</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference ABSTRACT_MODULE__FACETS = eINSTANCE.getAbstractModule_Facets();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.BasicModuleImpl
       * <em>Basic Module</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.BasicModuleImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getBasicModule()
       * @generated
       */
      EClass BASIC_MODULE = eINSTANCE.getBasicModule();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.AbstractFacetImpl
       * <em>Abstract Facet</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.AbstractFacetImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getAbstractFacet()
       * @generated
       */
      EClass ABSTRACT_FACET = eINSTANCE.getAbstractFacet();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference ABSTRACT_FACET__PARENT = eINSTANCE.getAbstractFacet_Parent();

      /**
       * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute ABSTRACT_FACET__NAME = eINSTANCE.getAbstractFacet_Name();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.CompositeModuleImpl
       * <em>Composite Module</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.CompositeModuleImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getCompositeModule()
       * @generated
       */
      EClass COMPOSITE_MODULE = eINSTANCE.getCompositeModule();

      /**
       * The meta object literal for the '<em><b>Modules</b></em>' reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference COMPOSITE_MODULE__MODULES = eINSTANCE.getCompositeModule_Modules();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.PluginsFacetImpl
       * <em>Plugins Facet</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.PluginsFacetImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getPluginsFacet()
       * @generated
       */
      EClass PLUGINS_FACET = eINSTANCE.getPluginsFacet();

      /**
       * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PLUGINS_FACET__PROJECTS = eINSTANCE.getPluginsFacet_Projects();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.FeaturesFacetImpl
       * <em>Features Facet</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.FeaturesFacetImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getFeaturesFacet()
       * @generated
       */
      EClass FEATURES_FACET = eINSTANCE.getFeaturesFacet();

      /**
       * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURES_FACET__PROJECTS = eINSTANCE.getFeaturesFacet_Projects();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.SitesFacetImpl
       * <em>Sites Facet</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.SitesFacetImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getSitesFacet()
       * @generated
       */
      EClass SITES_FACET = eINSTANCE.getSitesFacet();

      /**
       * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference SITES_FACET__PROJECTS = eINSTANCE.getSitesFacet_Projects();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.PluginProjectImpl
       * <em>Plugin Project</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.PluginProjectImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getPluginProject()
       * @generated
       */
      EClass PLUGIN_PROJECT = eINSTANCE.getPluginProject();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PLUGIN_PROJECT__PARENT = eINSTANCE.getPluginProject_Parent();

      /**
       * The meta object literal for the '<em><b>Bundle Version</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute PLUGIN_PROJECT__BUNDLE_VERSION = eINSTANCE.getPluginProject_BundleVersion();

      /**
       * The meta object literal for the '<em><b>Test Plugin</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute PLUGIN_PROJECT__TEST_PLUGIN = eINSTANCE.getPluginProject_TestPlugin();

      /**
       * The meta object literal for the '<em><b>Fragment Host Symbolic Name</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute PLUGIN_PROJECT__FRAGMENT_HOST_SYMBOLIC_NAME = eINSTANCE.getPluginProject_FragmentHostSymbolicName();

      /**
       * The meta object literal for the '<em><b>Fragment Host Version</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute PLUGIN_PROJECT__FRAGMENT_HOST_VERSION = eINSTANCE.getPluginProject_FragmentHostVersion();

      /**
       * The meta object literal for the '<em><b>Bundle Manifest</b></em>' containment reference feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PLUGIN_PROJECT__BUNDLE_MANIFEST = eINSTANCE.getPluginProject_BundleManifest();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.FeatureProjectImpl
       * <em>Feature Project</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.FeatureProjectImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getFeatureProject()
       * @generated
       */
      EClass FEATURE_PROJECT = eINSTANCE.getFeatureProject();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURE_PROJECT__PARENT = eINSTANCE.getFeatureProject_Parent();

      /**
       * The meta object literal for the '<em><b>Included Plugins</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURE_PROJECT__INCLUDED_PLUGINS = eINSTANCE.getFeatureProject_IncludedPlugins();

      /**
       * The meta object literal for the '<em><b>Included Features</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURE_PROJECT__INCLUDED_FEATURES = eINSTANCE.getFeatureProject_IncludedFeatures();

      /**
       * The meta object literal for the '<em><b>Required Features</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURE_PROJECT__REQUIRED_FEATURES = eINSTANCE.getFeatureProject_RequiredFeatures();

      /**
       * The meta object literal for the '<em><b>Required Plugins</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURE_PROJECT__REQUIRED_PLUGINS = eINSTANCE.getFeatureProject_RequiredPlugins();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.SiteProjectImpl
       * <em>Site Project</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.SiteProjectImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getSiteProject()
       * @generated
       */
      EClass SITE_PROJECT = eINSTANCE.getSiteProject();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference SITE_PROJECT__PARENT = eINSTANCE.getSiteProject_Parent();

      /**
       * The meta object literal for the '<em><b>Categories</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference SITE_PROJECT__CATEGORIES = eINSTANCE.getSiteProject_Categories();

      /**
       * The meta object literal for the '<em><b>Installable Units</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference SITE_PROJECT__INSTALLABLE_UNITS = eINSTANCE.getSiteProject_InstallableUnits();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.ProjectImpl
       * <em>Project</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.ProjectImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getProject()
       * @generated
       */
      EClass PROJECT = eINSTANCE.getProject();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.ProjectFacetImpl
       * <em>Project Facet</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.ProjectFacetImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getProjectFacet()
       * @generated
       */
      EClass PROJECT_FACET = eINSTANCE.getProjectFacet();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.FileContainerImpl
       * <em>File Container</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.FileContainerImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getFileContainer()
       * @generated
       */
      EClass FILE_CONTAINER = eINSTANCE.getFileContainer();

      /**
       * The meta object literal for the '<em><b>Directory</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute FILE_CONTAINER__DIRECTORY = eINSTANCE.getFileContainer_Directory();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.Derivable <em>Derivable</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.Derivable
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getDerivable()
       * @generated
       */
      EClass DERIVABLE = eINSTANCE.getDerivable();

      /**
       * The meta object literal for the '<em><b>Derived</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute DERIVABLE__DERIVED = eINSTANCE.getDerivable_Derived();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.PluginIncludeImpl
       * <em>Plugin Include</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.PluginIncludeImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getPluginInclude()
       * @generated
       */
      EClass PLUGIN_INCLUDE = eINSTANCE.getPluginInclude();

      /**
       * The meta object literal for the '<em><b>Unpack</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute PLUGIN_INCLUDE__UNPACK = eINSTANCE.getPluginInclude_Unpack();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.CategoryImpl
       * <em>Category</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.CategoryImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getCategory()
       * @generated
       */
      EClass CATEGORY = eINSTANCE.getCategory();

      /**
       * The meta object literal for the '<em><b>Installable Units</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference CATEGORY__INSTALLABLE_UNITS = eINSTANCE.getCategory_InstallableUnits();

      /**
       * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute CATEGORY__NAME = eINSTANCE.getCategory_Name();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.AbstractIdentifiable
       * <em>Abstract Identifiable</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.AbstractIdentifiable
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getAbstractIdentifiable()
       * @generated
       */
      EClass ABSTRACT_IDENTIFIABLE = eINSTANCE.getAbstractIdentifiable();

      /**
       * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute ABSTRACT_IDENTIFIABLE__ID = eINSTANCE.getAbstractIdentifiable_Id();

      /**
       * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute ABSTRACT_IDENTIFIABLE__VERSION = eINSTANCE.getAbstractIdentifiable_Version();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.ProductsFacetImpl
       * <em>Products Facet</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.ProductsFacetImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getProductsFacet()
       * @generated
       */
      EClass PRODUCTS_FACET = eINSTANCE.getProductsFacet();

      /**
       * The meta object literal for the '<em><b>Product Definitions</b></em>' containment reference list feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PRODUCTS_FACET__PRODUCT_DEFINITIONS = eINSTANCE.getProductsFacet_ProductDefinitions();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.ProductDefinitionImpl
       * <em>Product Definition</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.ProductDefinitionImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getProductDefinition()
       * @generated
       */
      EClass PRODUCT_DEFINITION = eINSTANCE.getProductDefinition();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PRODUCT_DEFINITION__PARENT = eINSTANCE.getProductDefinition_Parent();

      /**
       * The meta object literal for the '<em><b>File</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute PRODUCT_DEFINITION__FILE = eINSTANCE.getProductDefinition_File();

      /**
       * The meta object literal for the '<em><b>Product Plugin</b></em>' containment reference feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PRODUCT_DEFINITION__PRODUCT_PLUGIN = eINSTANCE.getProductDefinition_ProductPlugin();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.AbstractReferenceImpl
       * <em>Abstract Reference</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.AbstractReferenceImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getAbstractReference()
       * @generated
       */
      EClass ABSTRACT_REFERENCE = eINSTANCE.getAbstractReference();

      /**
       * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute ABSTRACT_REFERENCE__ID = eINSTANCE.getAbstractReference_Id();

      /**
       * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute ABSTRACT_REFERENCE__VERSION = eINSTANCE.getAbstractReference_Version();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.RuledReferenceImpl
       * <em>Ruled Reference</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.RuledReferenceImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getRuledReference()
       * @generated
       */
      EClass RULED_REFERENCE = eINSTANCE.getRuledReference();

      /**
       * The meta object literal for the '<em><b>Version Match Rule</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute RULED_REFERENCE__VERSION_MATCH_RULE = eINSTANCE.getRuledReference_VersionMatchRule();

      /**
       * The meta object literal for the '
       * {@link org.sourcepit.b2.model.module.internal.impl.AbstractStrictReferenceImpl
       * <em>Abstract Strict Reference</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.AbstractStrictReferenceImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getAbstractStrictReference()
       * @generated
       */
      EClass ABSTRACT_STRICT_REFERENCE = eINSTANCE.getAbstractStrictReference();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.StrictReferenceImpl
       * <em>Strict Reference</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.StrictReferenceImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getStrictReference()
       * @generated
       */
      EClass STRICT_REFERENCE = eINSTANCE.getStrictReference();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.internal.impl.FeatureIncludeImpl
       * <em>Feature Include</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.internal.impl.FeatureIncludeImpl
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getFeatureInclude()
       * @generated
       */
      EClass FEATURE_INCLUDE = eINSTANCE.getFeatureInclude();

      /**
       * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute FEATURE_INCLUDE__OPTIONAL = eINSTANCE.getFeatureInclude_Optional();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.util.Identifiable <em>Identifiable</em>}'
       * class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.util.Identifiable
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getIdentifiable()
       * @generated
       */
      EClass IDENTIFIABLE = eINSTANCE.getIdentifiable();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.module.VersionMatchRule
       * <em>Version Match Rule</em>}' enum.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.VersionMatchRule
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getVersionMatchRule()
       * @generated
       */
      EEnum VERSION_MATCH_RULE = eINSTANCE.getVersionMatchRule();

      /**
       * The meta object literal for the '<em>Identifier</em>' data type.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.module.util.Identifier
       * @see org.sourcepit.b2.model.module.internal.impl.ModuleModelPackageImpl#getIdentifier()
       * @generated
       */
      EDataType IDENTIFIER = eINSTANCE.getIdentifier();

   }

} // ModuleModelPackage
