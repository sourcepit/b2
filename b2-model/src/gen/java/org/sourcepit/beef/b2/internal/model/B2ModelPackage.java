/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.internal.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.sourcepit.beef.b2.internal.model.B2ModelFactory
 * @model kind="package"
 * @generated
 */
public interface B2ModelPackage extends EPackage
{
   /**
    * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNAME = "b2";

   /**
    * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_URI = "http://www.sourcepit.org/beef/b2/0.1";

   /**
    * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_PREFIX = "b2";

   /**
    * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   B2ModelPackage eINSTANCE = org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl.init();

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.AbstractModuleImpl
    * <em>Abstract Module</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.AbstractModuleImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getAbstractModule()
    * @generated
    */
   int ABSTRACT_MODULE = 0;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.BasicModuleImpl
    * <em>Basic Module</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.BasicModuleImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getBasicModule()
    * @generated
    */
   int BASIC_MODULE = 1;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.AbstractFacetImpl
    * <em>Abstract Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.AbstractFacetImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getAbstractFacet()
    * @generated
    */
   int ABSTRACT_FACET = 2;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.CompositeModuleImpl
    * <em>Composite Module</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.CompositeModuleImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getCompositeModule()
    * @generated
    */
   int COMPOSITE_MODULE = 3;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.ProjectFacetImpl
    * <em>Project Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.ProjectFacetImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getProjectFacet()
    * @generated
    */
   int PROJECT_FACET = 11;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.PluginsFacetImpl
    * <em>Plugins Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.PluginsFacetImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getPluginsFacet()
    * @generated
    */
   int PLUGINS_FACET = 4;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.FeaturesFacetImpl
    * <em>Features Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.FeaturesFacetImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getFeaturesFacet()
    * @generated
    */
   int FEATURES_FACET = 5;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.SitesFacetImpl <em>Sites Facet</em>}'
    * class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.SitesFacetImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getSitesFacet()
    * @generated
    */
   int SITES_FACET = 6;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.ProjectImpl <em>Project</em>}' class.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.ProjectImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getProject()
    * @generated
    */
   int PROJECT = 10;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.PluginProjectImpl
    * <em>Plugin Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.PluginProjectImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getPluginProject()
    * @generated
    */
   int PLUGIN_PROJECT = 7;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.FeatureProjectImpl
    * <em>Feature Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.FeatureProjectImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getFeatureProject()
    * @generated
    */
   int FEATURE_PROJECT = 8;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.SiteProjectImpl
    * <em>Site Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.SiteProjectImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getSiteProject()
    * @generated
    */
   int SITE_PROJECT = 9;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.FileContainerImpl
    * <em>File Container</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.FileContainerImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getFileContainer()
    * @generated
    */
   int FILE_CONTAINER = 12;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FILE_CONTAINER__DIRECTORY = 0;

   /**
    * The number of structural features of the '<em>File Container</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FILE_CONTAINER_FEATURE_COUNT = 1;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__DIRECTORY = FILE_CONTAINER__DIRECTORY;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__ANNOTATIONS = FILE_CONTAINER_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__ID = FILE_CONTAINER_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__VERSION = FILE_CONTAINER_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__PARENT = FILE_CONTAINER_FEATURE_COUNT + 3;

   /**
    * The feature id for the '<em><b>Layout Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__LAYOUT_ID = FILE_CONTAINER_FEATURE_COUNT + 4;

   /**
    * The feature id for the '<em><b>Locales</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__LOCALES = FILE_CONTAINER_FEATURE_COUNT + 5;

   /**
    * The feature id for the '<em><b>Facets</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE__FACETS = FILE_CONTAINER_FEATURE_COUNT + 6;

   /**
    * The number of structural features of the '<em>Abstract Module</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_MODULE_FEATURE_COUNT = FILE_CONTAINER_FEATURE_COUNT + 7;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__DIRECTORY = ABSTRACT_MODULE__DIRECTORY;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__ANNOTATIONS = ABSTRACT_MODULE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__ID = ABSTRACT_MODULE__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__VERSION = ABSTRACT_MODULE__VERSION;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__PARENT = ABSTRACT_MODULE__PARENT;

   /**
    * The feature id for the '<em><b>Layout Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__LAYOUT_ID = ABSTRACT_MODULE__LAYOUT_ID;

   /**
    * The feature id for the '<em><b>Locales</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__LOCALES = ABSTRACT_MODULE__LOCALES;

   /**
    * The feature id for the '<em><b>Facets</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE__FACETS = ABSTRACT_MODULE__FACETS;

   /**
    * The number of structural features of the '<em>Basic Module</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int BASIC_MODULE_FEATURE_COUNT = ABSTRACT_MODULE_FEATURE_COUNT + 0;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.internal.model.Derivable <em>Derivable</em>}' class. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.internal.model.Derivable
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getDerivable()
    * @generated
    */
   int DERIVABLE = 13;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int DERIVABLE__DERIVED = 0;

   /**
    * The number of structural features of the '<em>Derivable</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int DERIVABLE_FEATURE_COUNT = 1;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_FACET__DERIVED = DERIVABLE__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_FACET__ANNOTATIONS = DERIVABLE_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_FACET__PARENT = DERIVABLE_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_FACET__NAME = DERIVABLE_FEATURE_COUNT + 2;

   /**
    * The number of structural features of the '<em>Abstract Facet</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ABSTRACT_FACET_FEATURE_COUNT = DERIVABLE_FEATURE_COUNT + 3;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__DIRECTORY = ABSTRACT_MODULE__DIRECTORY;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__ANNOTATIONS = ABSTRACT_MODULE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__ID = ABSTRACT_MODULE__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__VERSION = ABSTRACT_MODULE__VERSION;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__PARENT = ABSTRACT_MODULE__PARENT;

   /**
    * The feature id for the '<em><b>Layout Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__LAYOUT_ID = ABSTRACT_MODULE__LAYOUT_ID;

   /**
    * The feature id for the '<em><b>Locales</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__LOCALES = ABSTRACT_MODULE__LOCALES;

   /**
    * The feature id for the '<em><b>Facets</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__FACETS = ABSTRACT_MODULE__FACETS;

   /**
    * The feature id for the '<em><b>Modules</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE__MODULES = ABSTRACT_MODULE_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Composite Module</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int COMPOSITE_MODULE_FEATURE_COUNT = ABSTRACT_MODULE_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FACET__DERIVED = ABSTRACT_FACET__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FACET__ANNOTATIONS = ABSTRACT_FACET__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FACET__PARENT = ABSTRACT_FACET__PARENT;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FACET__NAME = ABSTRACT_FACET__NAME;

   /**
    * The number of structural features of the '<em>Project Facet</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FACET_FEATURE_COUNT = ABSTRACT_FACET_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET__DERIVED = PROJECT_FACET__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET__ANNOTATIONS = PROJECT_FACET__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET__PARENT = PROJECT_FACET__PARENT;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET__NAME = PROJECT_FACET__NAME;

   /**
    * The feature id for the '<em><b>Projects</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET__PROJECTS = PROJECT_FACET_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Plugins Facet</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int PLUGINS_FACET_FEATURE_COUNT = PROJECT_FACET_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET__DERIVED = PROJECT_FACET__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET__ANNOTATIONS = PROJECT_FACET__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET__PARENT = PROJECT_FACET__PARENT;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET__NAME = PROJECT_FACET__NAME;

   /**
    * The feature id for the '<em><b>Projects</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET__PROJECTS = PROJECT_FACET_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Features Facet</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURES_FACET_FEATURE_COUNT = PROJECT_FACET_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET__DERIVED = PROJECT_FACET__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET__ANNOTATIONS = PROJECT_FACET__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET__PARENT = PROJECT_FACET__PARENT;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET__NAME = PROJECT_FACET__NAME;

   /**
    * The feature id for the '<em><b>Projects</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET__PROJECTS = PROJECT_FACET_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Sites Facet</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int SITES_FACET_FEATURE_COUNT = PROJECT_FACET_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT__DIRECTORY = FILE_CONTAINER__DIRECTORY;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT__DERIVED = FILE_CONTAINER_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT__ANNOTATIONS = FILE_CONTAINER_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT__ID = FILE_CONTAINER_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT__VERSION = FILE_CONTAINER_FEATURE_COUNT + 3;

   /**
    * The number of structural features of the '<em>Project</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PROJECT_FEATURE_COUNT = FILE_CONTAINER_FEATURE_COUNT + 4;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__DIRECTORY = PROJECT__DIRECTORY;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__DERIVED = PROJECT__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__ANNOTATIONS = PROJECT__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__ID = PROJECT__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__VERSION = PROJECT__VERSION;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__PARENT = PROJECT_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Bundle Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__BUNDLE_VERSION = PROJECT_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Test Plugin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__TEST_PLUGIN = PROJECT_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Fragment Host Symbolic Name</b></em>' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__FRAGMENT_HOST_SYMBOLIC_NAME = PROJECT_FEATURE_COUNT + 3;

   /**
    * The feature id for the '<em><b>Fragment Host Version</b></em>' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT__FRAGMENT_HOST_VERSION = PROJECT_FEATURE_COUNT + 4;

   /**
    * The number of structural features of the '<em>Plugin Project</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_PROJECT_FEATURE_COUNT = PROJECT_FEATURE_COUNT + 5;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__DIRECTORY = PROJECT__DIRECTORY;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__DERIVED = PROJECT__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__ANNOTATIONS = PROJECT__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__ID = PROJECT__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__VERSION = PROJECT__VERSION;

   /**
    * The feature id for the '<em><b>Classifier</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__CLASSIFIER = PROJECT_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__PARENT = PROJECT_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Included Plugins</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__INCLUDED_PLUGINS = PROJECT_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Included Features</b></em>' containment reference list. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT__INCLUDED_FEATURES = PROJECT_FEATURE_COUNT + 3;

   /**
    * The number of structural features of the '<em>Feature Project</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_PROJECT_FEATURE_COUNT = PROJECT_FEATURE_COUNT + 4;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__DIRECTORY = PROJECT__DIRECTORY;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__DERIVED = PROJECT__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__ANNOTATIONS = PROJECT__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__ID = PROJECT__ID;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__VERSION = PROJECT__VERSION;

   /**
    * The feature id for the '<em><b>Classifier</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__CLASSIFIER = PROJECT_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__PARENT = PROJECT_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Categories</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT__CATEGORIES = PROJECT_FEATURE_COUNT + 2;

   /**
    * The number of structural features of the '<em>Site Project</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int SITE_PROJECT_FEATURE_COUNT = PROJECT_FEATURE_COUNT + 3;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.internal.model.Reference <em>Reference</em>}' class. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.internal.model.Reference
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getReference()
    * @generated
    */
   int REFERENCE = 24;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int REFERENCE__ID = 0;

   /**
    * The feature id for the '<em><b>Version Range</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int REFERENCE__VERSION_RANGE = 1;

   /**
    * The number of structural features of the '<em>Reference</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int REFERENCE_FEATURE_COUNT = 2;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.PluginIncludeImpl
    * <em>Plugin Include</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.PluginIncludeImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getPluginInclude()
    * @generated
    */
   int PLUGIN_INCLUDE = 14;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_INCLUDE__ID = REFERENCE__ID;

   /**
    * The feature id for the '<em><b>Version Range</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_INCLUDE__VERSION_RANGE = REFERENCE__VERSION_RANGE;

   /**
    * The feature id for the '<em><b>Unpack</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_INCLUDE__UNPACK = REFERENCE_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_INCLUDE__PARENT = REFERENCE_FEATURE_COUNT + 1;

   /**
    * The number of structural features of the '<em>Plugin Include</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PLUGIN_INCLUDE_FEATURE_COUNT = REFERENCE_FEATURE_COUNT + 2;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.CategoryImpl <em>Category</em>}'
    * class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.CategoryImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getCategory()
    * @generated
    */
   int CATEGORY = 15;

   /**
    * The feature id for the '<em><b>Feature References</b></em>' containment reference list. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int CATEGORY__FEATURE_REFERENCES = 0;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int CATEGORY__NAME = 1;

   /**
    * The number of structural features of the '<em>Category</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int CATEGORY_FEATURE_COUNT = 2;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.FeatureIncludeImpl
    * <em>Feature Include</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.FeatureIncludeImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getFeatureInclude()
    * @generated
    */
   int FEATURE_INCLUDE = 16;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_INCLUDE__ID = REFERENCE__ID;

   /**
    * The feature id for the '<em><b>Version Range</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_INCLUDE__VERSION_RANGE = REFERENCE__VERSION_RANGE;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_INCLUDE__PARENT = REFERENCE_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Feature Include</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int FEATURE_INCLUDE_FEATURE_COUNT = REFERENCE_FEATURE_COUNT + 1;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.internal.model.Classified <em>Classified</em>}' class.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.internal.model.Classified
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getClassified()
    * @generated
    */
   int CLASSIFIED = 17;

   /**
    * The feature id for the '<em><b>Classifier</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int CLASSIFIED__CLASSIFIER = 0;

   /**
    * The number of structural features of the '<em>Classified</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int CLASSIFIED_FEATURE_COUNT = 1;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.internal.model.Annotateable <em>Annotateable</em>}'
    * class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.internal.model.Annotateable
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getAnnotateable()
    * @generated
    */
   int ANNOTATEABLE = 18;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATEABLE__ANNOTATIONS = 0;

   /**
    * The number of structural features of the '<em>Annotateable</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATEABLE_FEATURE_COUNT = 1;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.AnnotationImpl <em>Annotation</em>}'
    * class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.AnnotationImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getAnnotation()
    * @generated
    */
   int ANNOTATION = 19;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATION__PARENT = 0;

   /**
    * The feature id for the '<em><b>Source</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATION__SOURCE = 1;

   /**
    * The feature id for the '<em><b>Entries</b></em>' map. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATION__ENTRIES = 2;

   /**
    * The number of structural features of the '<em>Annotation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int ANNOTATION_FEATURE_COUNT = 3;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.EStringMapEntryImpl
    * <em>EString Map Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.EStringMapEntryImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getEStringMapEntry()
    * @generated
    */
   int ESTRING_MAP_ENTRY = 20;

   /**
    * The feature id for the '<em><b>Key</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ESTRING_MAP_ENTRY__KEY = 0;

   /**
    * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ESTRING_MAP_ENTRY__VALUE = 1;

   /**
    * The number of structural features of the '<em>EString Map Entry</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ESTRING_MAP_ENTRY_FEATURE_COUNT = 2;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.internal.model.Identifiable <em>Identifiable</em>}'
    * class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.internal.model.Identifiable
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getIdentifiable()
    * @generated
    */
   int IDENTIFIABLE = 21;

   /**
    * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int IDENTIFIABLE__ID = 0;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int IDENTIFIABLE__VERSION = 1;

   /**
    * The number of structural features of the '<em>Identifiable</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int IDENTIFIABLE_FEATURE_COUNT = 2;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.ProductsFacetImpl
    * <em>Products Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.ProductsFacetImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getProductsFacet()
    * @generated
    */
   int PRODUCTS_FACET = 22;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET__DERIVED = ABSTRACT_FACET__DERIVED;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET__ANNOTATIONS = ABSTRACT_FACET__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET__PARENT = ABSTRACT_FACET__PARENT;

   /**
    * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET__NAME = ABSTRACT_FACET__NAME;

   /**
    * The feature id for the '<em><b>Product Definitions</b></em>' containment reference list. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET__PRODUCT_DEFINITIONS = ABSTRACT_FACET_FEATURE_COUNT + 0;

   /**
    * The number of structural features of the '<em>Products Facet</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCTS_FACET_FEATURE_COUNT = ABSTRACT_FACET_FEATURE_COUNT + 1;

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.internal.impl.ProductDefinitionImpl
    * <em>Product Definition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.internal.impl.ProductDefinitionImpl
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getProductDefinition()
    * @generated
    */
   int PRODUCT_DEFINITION = 23;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION__ANNOTATIONS = ANNOTATEABLE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Derived</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION__DERIVED = ANNOTATEABLE_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION__PARENT = ANNOTATEABLE_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>File</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION__FILE = ANNOTATEABLE_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Product Plugin</b></em>' containment reference. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION__PRODUCT_PLUGIN = ANNOTATEABLE_FEATURE_COUNT + 3;

   /**
    * The number of structural features of the '<em>Product Definition</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int PRODUCT_DEFINITION_FEATURE_COUNT = ANNOTATEABLE_FEATURE_COUNT + 4;

   /**
    * The meta object id for the '<em>Identifier</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.util.Identifier
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getIdentifier()
    * @generated
    */
   int IDENTIFIER = 27;

   /**
    * The meta object id for the '<em>EJava File</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see java.io.File
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getEJavaFile()
    * @generated
    */
   int EJAVA_FILE = 25;


   /**
    * The meta object id for the '<em>ELocale</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see java.util.Locale
    * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getELocale()
    * @generated
    */
   int ELOCALE = 26;


   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.AbstractModule
    * <em>Abstract Module</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Abstract Module</em>'.
    * @see org.sourcepit.beef.b2.internal.model.AbstractModule
    * @generated
    */
   EClass getAbstractModule();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.beef.b2.internal.model.AbstractModule#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.beef.b2.internal.model.AbstractModule#getParent()
    * @see #getAbstractModule()
    * @generated
    */
   EReference getAbstractModule_Parent();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.AbstractModule#getLayoutId
    * <em>Layout Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Layout Id</em>'.
    * @see org.sourcepit.beef.b2.internal.model.AbstractModule#getLayoutId()
    * @see #getAbstractModule()
    * @generated
    */
   EAttribute getAbstractModule_LayoutId();

   /**
    * Returns the meta object for the attribute list '
    * {@link org.sourcepit.beef.b2.internal.model.AbstractModule#getLocales <em>Locales</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute list '<em>Locales</em>'.
    * @see org.sourcepit.beef.b2.internal.model.AbstractModule#getLocales()
    * @see #getAbstractModule()
    * @generated
    */
   EAttribute getAbstractModule_Locales();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.AbstractModule#getFacets <em>Facets</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Facets</em>'.
    * @see org.sourcepit.beef.b2.internal.model.AbstractModule#getFacets()
    * @see #getAbstractModule()
    * @generated
    */
   EReference getAbstractModule_Facets();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.BasicModule <em>Basic Module</em>}
    * '. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Basic Module</em>'.
    * @see org.sourcepit.beef.b2.internal.model.BasicModule
    * @generated
    */
   EClass getBasicModule();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.AbstractFacet
    * <em>Abstract Facet</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Abstract Facet</em>'.
    * @see org.sourcepit.beef.b2.internal.model.AbstractFacet
    * @generated
    */
   EClass getAbstractFacet();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.beef.b2.internal.model.AbstractFacet#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.beef.b2.internal.model.AbstractFacet#getParent()
    * @see #getAbstractFacet()
    * @generated
    */
   EReference getAbstractFacet_Parent();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.AbstractFacet#getName
    * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Name</em>'.
    * @see org.sourcepit.beef.b2.internal.model.AbstractFacet#getName()
    * @see #getAbstractFacet()
    * @generated
    */
   EAttribute getAbstractFacet_Name();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.CompositeModule
    * <em>Composite Module</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Composite Module</em>'.
    * @see org.sourcepit.beef.b2.internal.model.CompositeModule
    * @generated
    */
   EClass getCompositeModule();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.CompositeModule#getModules <em>Modules</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Modules</em>'.
    * @see org.sourcepit.beef.b2.internal.model.CompositeModule#getModules()
    * @see #getCompositeModule()
    * @generated
    */
   EReference getCompositeModule_Modules();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.PluginsFacet
    * <em>Plugins Facet</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Plugins Facet</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginsFacet
    * @generated
    */
   EClass getPluginsFacet();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.PluginsFacet#getProjects <em>Projects</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Projects</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginsFacet#getProjects()
    * @see #getPluginsFacet()
    * @generated
    */
   EReference getPluginsFacet_Projects();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.FeaturesFacet
    * <em>Features Facet</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Features Facet</em>'.
    * @see org.sourcepit.beef.b2.internal.model.FeaturesFacet
    * @generated
    */
   EClass getFeaturesFacet();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.FeaturesFacet#getProjects <em>Projects</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Projects</em>'.
    * @see org.sourcepit.beef.b2.internal.model.FeaturesFacet#getProjects()
    * @see #getFeaturesFacet()
    * @generated
    */
   EReference getFeaturesFacet_Projects();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.SitesFacet <em>Sites Facet</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Sites Facet</em>'.
    * @see org.sourcepit.beef.b2.internal.model.SitesFacet
    * @generated
    */
   EClass getSitesFacet();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.SitesFacet#getProjects <em>Projects</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Projects</em>'.
    * @see org.sourcepit.beef.b2.internal.model.SitesFacet#getProjects()
    * @see #getSitesFacet()
    * @generated
    */
   EReference getSitesFacet_Projects();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.PluginProject
    * <em>Plugin Project</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Plugin Project</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginProject
    * @generated
    */
   EClass getPluginProject();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.beef.b2.internal.model.PluginProject#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginProject#getParent()
    * @see #getPluginProject()
    * @generated
    */
   EReference getPluginProject_Parent();

   /**
    * Returns the meta object for the attribute '
    * {@link org.sourcepit.beef.b2.internal.model.PluginProject#getBundleVersion <em>Bundle Version</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Bundle Version</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginProject#getBundleVersion()
    * @see #getPluginProject()
    * @generated
    */
   EAttribute getPluginProject_BundleVersion();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.PluginProject#isTestPlugin
    * <em>Test Plugin</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Test Plugin</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginProject#isTestPlugin()
    * @see #getPluginProject()
    * @generated
    */
   EAttribute getPluginProject_TestPlugin();

   /**
    * Returns the meta object for the attribute '
    * {@link org.sourcepit.beef.b2.internal.model.PluginProject#getFragmentHostSymbolicName
    * <em>Fragment Host Symbolic Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Fragment Host Symbolic Name</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginProject#getFragmentHostSymbolicName()
    * @see #getPluginProject()
    * @generated
    */
   EAttribute getPluginProject_FragmentHostSymbolicName();

   /**
    * Returns the meta object for the attribute '
    * {@link org.sourcepit.beef.b2.internal.model.PluginProject#getFragmentHostVersion <em>Fragment Host Version</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Fragment Host Version</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginProject#getFragmentHostVersion()
    * @see #getPluginProject()
    * @generated
    */
   EAttribute getPluginProject_FragmentHostVersion();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.FeatureProject
    * <em>Feature Project</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Feature Project</em>'.
    * @see org.sourcepit.beef.b2.internal.model.FeatureProject
    * @generated
    */
   EClass getFeatureProject();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.beef.b2.internal.model.FeatureProject#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.beef.b2.internal.model.FeatureProject#getParent()
    * @see #getFeatureProject()
    * @generated
    */
   EReference getFeatureProject_Parent();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.FeatureProject#getIncludedPlugins <em>Included Plugins</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Included Plugins</em>'.
    * @see org.sourcepit.beef.b2.internal.model.FeatureProject#getIncludedPlugins()
    * @see #getFeatureProject()
    * @generated
    */
   EReference getFeatureProject_IncludedPlugins();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.FeatureProject#getIncludedFeatures <em>Included Features</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Included Features</em>'.
    * @see org.sourcepit.beef.b2.internal.model.FeatureProject#getIncludedFeatures()
    * @see #getFeatureProject()
    * @generated
    */
   EReference getFeatureProject_IncludedFeatures();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.SiteProject <em>Site Project</em>}
    * '. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Site Project</em>'.
    * @see org.sourcepit.beef.b2.internal.model.SiteProject
    * @generated
    */
   EClass getSiteProject();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.beef.b2.internal.model.SiteProject#getParent <em>Parent</em>}'. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.beef.b2.internal.model.SiteProject#getParent()
    * @see #getSiteProject()
    * @generated
    */
   EReference getSiteProject_Parent();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.SiteProject#getCategories <em>Categories</em>}'. <!-- begin-user-doc
    * --> <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Categories</em>'.
    * @see org.sourcepit.beef.b2.internal.model.SiteProject#getCategories()
    * @see #getSiteProject()
    * @generated
    */
   EReference getSiteProject_Categories();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.Project <em>Project</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Project</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Project
    * @generated
    */
   EClass getProject();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.ProjectFacet
    * <em>Project Facet</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Project Facet</em>'.
    * @see org.sourcepit.beef.b2.internal.model.ProjectFacet
    * @generated
    */
   EClass getProjectFacet();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.FileContainer
    * <em>File Container</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>File Container</em>'.
    * @see org.sourcepit.beef.b2.internal.model.FileContainer
    * @generated
    */
   EClass getFileContainer();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.FileContainer#getDirectory
    * <em>Directory</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Directory</em>'.
    * @see org.sourcepit.beef.b2.internal.model.FileContainer#getDirectory()
    * @see #getFileContainer()
    * @generated
    */
   EAttribute getFileContainer_Directory();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.Derivable <em>Derivable</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Derivable</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Derivable
    * @generated
    */
   EClass getDerivable();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.Derivable#isDerived
    * <em>Derived</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Derived</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Derivable#isDerived()
    * @see #getDerivable()
    * @generated
    */
   EAttribute getDerivable_Derived();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.PluginInclude
    * <em>Plugin Include</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Plugin Include</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginInclude
    * @generated
    */
   EClass getPluginInclude();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.PluginInclude#isUnpack
    * <em>Unpack</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Unpack</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginInclude#isUnpack()
    * @see #getPluginInclude()
    * @generated
    */
   EAttribute getPluginInclude_Unpack();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.beef.b2.internal.model.PluginInclude#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.beef.b2.internal.model.PluginInclude#getParent()
    * @see #getPluginInclude()
    * @generated
    */
   EReference getPluginInclude_Parent();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.Category <em>Category</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Category</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Category
    * @generated
    */
   EClass getCategory();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.Category#getFeatureReferences <em>Feature References</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Feature References</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Category#getFeatureReferences()
    * @see #getCategory()
    * @generated
    */
   EReference getCategory_FeatureReferences();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.Category#getName
    * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Name</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Category#getName()
    * @see #getCategory()
    * @generated
    */
   EAttribute getCategory_Name();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.FeatureInclude
    * <em>Feature Include</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Feature Include</em>'.
    * @see org.sourcepit.beef.b2.internal.model.FeatureInclude
    * @generated
    */
   EClass getFeatureInclude();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.beef.b2.internal.model.FeatureInclude#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.beef.b2.internal.model.FeatureInclude#getParent()
    * @see #getFeatureInclude()
    * @generated
    */
   EReference getFeatureInclude_Parent();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.Classified <em>Classified</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Classified</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Classified
    * @generated
    */
   EClass getClassified();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.Classified#getClassifier
    * <em>Classifier</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Classifier</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Classified#getClassifier()
    * @see #getClassified()
    * @generated
    */
   EAttribute getClassified_Classifier();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.Annotateable <em>Annotateable</em>}
    * '. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Annotateable</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Annotateable
    * @generated
    */
   EClass getAnnotateable();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.Annotateable#getAnnotations <em>Annotations</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Annotations</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Annotateable#getAnnotations()
    * @see #getAnnotateable()
    * @generated
    */
   EReference getAnnotateable_Annotations();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.Annotation <em>Annotation</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Annotation</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Annotation
    * @generated
    */
   EClass getAnnotation();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.beef.b2.internal.model.Annotation#getParent <em>Parent</em>}'. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Annotation#getParent()
    * @see #getAnnotation()
    * @generated
    */
   EReference getAnnotation_Parent();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.Annotation#getSource
    * <em>Source</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Source</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Annotation#getSource()
    * @see #getAnnotation()
    * @generated
    */
   EAttribute getAnnotation_Source();

   /**
    * Returns the meta object for the map '{@link org.sourcepit.beef.b2.internal.model.Annotation#getEntries
    * <em>Entries</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the map '<em>Entries</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Annotation#getEntries()
    * @see #getAnnotation()
    * @generated
    */
   EReference getAnnotation_Entries();

   /**
    * Returns the meta object for class '{@link java.util.Map.Entry <em>EString Map Entry</em>}'. <!-- begin-user-doc
    * --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>EString Map Entry</em>'.
    * @see java.util.Map.Entry
    * @model keyDataType="org.eclipse.emf.ecore.EString" valueDataType="org.eclipse.emf.ecore.EString"
    * @generated
    */
   EClass getEStringMapEntry();

   /**
    * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Key</em>'.
    * @see java.util.Map.Entry
    * @see #getEStringMapEntry()
    * @generated
    */
   EAttribute getEStringMapEntry_Key();

   /**
    * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Value</em>'.
    * @see java.util.Map.Entry
    * @see #getEStringMapEntry()
    * @generated
    */
   EAttribute getEStringMapEntry_Value();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.Identifiable <em>Identifiable</em>}
    * '. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Identifiable</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Identifiable
    * @generated
    */
   EClass getIdentifiable();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.Identifiable#getId
    * <em>Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Id</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Identifiable#getId()
    * @see #getIdentifiable()
    * @generated
    */
   EAttribute getIdentifiable_Id();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.Identifiable#getVersion
    * <em>Version</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Version</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Identifiable#getVersion()
    * @see #getIdentifiable()
    * @generated
    */
   EAttribute getIdentifiable_Version();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.ProductsFacet
    * <em>Products Facet</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Products Facet</em>'.
    * @see org.sourcepit.beef.b2.internal.model.ProductsFacet
    * @generated
    */
   EClass getProductsFacet();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.internal.model.ProductsFacet#getProductDefinitions <em>Product Definitions</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Product Definitions</em>'.
    * @see org.sourcepit.beef.b2.internal.model.ProductsFacet#getProductDefinitions()
    * @see #getProductsFacet()
    * @generated
    */
   EReference getProductsFacet_ProductDefinitions();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.ProductDefinition
    * <em>Product Definition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Product Definition</em>'.
    * @see org.sourcepit.beef.b2.internal.model.ProductDefinition
    * @generated
    */
   EClass getProductDefinition();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.beef.b2.internal.model.ProductDefinition#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.beef.b2.internal.model.ProductDefinition#getParent()
    * @see #getProductDefinition()
    * @generated
    */
   EReference getProductDefinition_Parent();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.ProductDefinition#getFile
    * <em>File</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>File</em>'.
    * @see org.sourcepit.beef.b2.internal.model.ProductDefinition#getFile()
    * @see #getProductDefinition()
    * @generated
    */
   EAttribute getProductDefinition_File();

   /**
    * Returns the meta object for the containment reference '
    * {@link org.sourcepit.beef.b2.internal.model.ProductDefinition#getProductPlugin <em>Product Plugin</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference '<em>Product Plugin</em>'.
    * @see org.sourcepit.beef.b2.internal.model.ProductDefinition#getProductPlugin()
    * @see #getProductDefinition()
    * @generated
    */
   EReference getProductDefinition_ProductPlugin();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.internal.model.Reference <em>Reference</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Reference</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Reference
    * @generated
    */
   EClass getReference();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.Reference#getId
    * <em>Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Id</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Reference#getId()
    * @see #getReference()
    * @generated
    */
   EAttribute getReference_Id();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.internal.model.Reference#getVersionRange
    * <em>Version Range</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Version Range</em>'.
    * @see org.sourcepit.beef.b2.internal.model.Reference#getVersionRange()
    * @see #getReference()
    * @generated
    */
   EAttribute getReference_VersionRange();

   /**
    * Returns the meta object for data type '{@link org.sourcepit.beef.b2.model.util.Identifier <em>Identifier</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for data type '<em>Identifier</em>'.
    * @see org.sourcepit.beef.b2.model.util.Identifier
    * @model instanceClass="org.sourcepit.beef.b2.model.util.Identifier"
    * @generated
    */
   EDataType getIdentifier();

   /**
    * Returns the meta object for data type '{@link java.io.File <em>EJava File</em>}'. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for data type '<em>EJava File</em>'.
    * @see java.io.File
    * @model instanceClass="java.io.File"
    * @generated
    */
   EDataType getEJavaFile();

   /**
    * Returns the meta object for data type '{@link java.util.Locale <em>ELocale</em>}'. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for data type '<em>ELocale</em>'.
    * @see java.util.Locale
    * @model instanceClass="java.util.Locale"
    * @generated
    */
   EDataType getELocale();

   /**
    * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the factory that creates the instances of the model.
    * @generated
    */
   B2ModelFactory getB2ModelFactory();

   /**
    * <!-- begin-user-doc --> Defines literals for the meta objects that represent
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
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.AbstractModuleImpl
       * <em>Abstract Module</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.AbstractModuleImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getAbstractModule()
       * @generated
       */
      EClass ABSTRACT_MODULE = eINSTANCE.getAbstractModule();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference ABSTRACT_MODULE__PARENT = eINSTANCE.getAbstractModule_Parent();

      /**
       * The meta object literal for the '<em><b>Layout Id</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ABSTRACT_MODULE__LAYOUT_ID = eINSTANCE.getAbstractModule_LayoutId();

      /**
       * The meta object literal for the '<em><b>Locales</b></em>' attribute list feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ABSTRACT_MODULE__LOCALES = eINSTANCE.getAbstractModule_Locales();

      /**
       * The meta object literal for the '<em><b>Facets</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference ABSTRACT_MODULE__FACETS = eINSTANCE.getAbstractModule_Facets();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.BasicModuleImpl
       * <em>Basic Module</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.BasicModuleImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getBasicModule()
       * @generated
       */
      EClass BASIC_MODULE = eINSTANCE.getBasicModule();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.AbstractFacetImpl
       * <em>Abstract Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.AbstractFacetImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getAbstractFacet()
       * @generated
       */
      EClass ABSTRACT_FACET = eINSTANCE.getAbstractFacet();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference ABSTRACT_FACET__PARENT = eINSTANCE.getAbstractFacet_Parent();

      /**
       * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ABSTRACT_FACET__NAME = eINSTANCE.getAbstractFacet_Name();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.CompositeModuleImpl
       * <em>Composite Module</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.CompositeModuleImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getCompositeModule()
       * @generated
       */
      EClass COMPOSITE_MODULE = eINSTANCE.getCompositeModule();

      /**
       * The meta object literal for the '<em><b>Modules</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference COMPOSITE_MODULE__MODULES = eINSTANCE.getCompositeModule_Modules();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.PluginsFacetImpl
       * <em>Plugins Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.PluginsFacetImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getPluginsFacet()
       * @generated
       */
      EClass PLUGINS_FACET = eINSTANCE.getPluginsFacet();

      /**
       * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PLUGINS_FACET__PROJECTS = eINSTANCE.getPluginsFacet_Projects();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.FeaturesFacetImpl
       * <em>Features Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.FeaturesFacetImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getFeaturesFacet()
       * @generated
       */
      EClass FEATURES_FACET = eINSTANCE.getFeaturesFacet();

      /**
       * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURES_FACET__PROJECTS = eINSTANCE.getFeaturesFacet_Projects();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.SitesFacetImpl
       * <em>Sites Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.SitesFacetImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getSitesFacet()
       * @generated
       */
      EClass SITES_FACET = eINSTANCE.getSitesFacet();

      /**
       * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference SITES_FACET__PROJECTS = eINSTANCE.getSitesFacet_Projects();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.PluginProjectImpl
       * <em>Plugin Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.PluginProjectImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getPluginProject()
       * @generated
       */
      EClass PLUGIN_PROJECT = eINSTANCE.getPluginProject();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PLUGIN_PROJECT__PARENT = eINSTANCE.getPluginProject_Parent();

      /**
       * The meta object literal for the '<em><b>Bundle Version</b></em>' attribute feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute PLUGIN_PROJECT__BUNDLE_VERSION = eINSTANCE.getPluginProject_BundleVersion();

      /**
       * The meta object literal for the '<em><b>Test Plugin</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute PLUGIN_PROJECT__TEST_PLUGIN = eINSTANCE.getPluginProject_TestPlugin();

      /**
       * The meta object literal for the '<em><b>Fragment Host Symbolic Name</b></em>' attribute feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute PLUGIN_PROJECT__FRAGMENT_HOST_SYMBOLIC_NAME = eINSTANCE.getPluginProject_FragmentHostSymbolicName();

      /**
       * The meta object literal for the '<em><b>Fragment Host Version</b></em>' attribute feature. <!-- begin-user-doc
       * --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EAttribute PLUGIN_PROJECT__FRAGMENT_HOST_VERSION = eINSTANCE.getPluginProject_FragmentHostVersion();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.FeatureProjectImpl
       * <em>Feature Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.FeatureProjectImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getFeatureProject()
       * @generated
       */
      EClass FEATURE_PROJECT = eINSTANCE.getFeatureProject();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURE_PROJECT__PARENT = eINSTANCE.getFeatureProject_Parent();

      /**
       * The meta object literal for the '<em><b>Included Plugins</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURE_PROJECT__INCLUDED_PLUGINS = eINSTANCE.getFeatureProject_IncludedPlugins();

      /**
       * The meta object literal for the '<em><b>Included Features</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURE_PROJECT__INCLUDED_FEATURES = eINSTANCE.getFeatureProject_IncludedFeatures();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.SiteProjectImpl
       * <em>Site Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.SiteProjectImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getSiteProject()
       * @generated
       */
      EClass SITE_PROJECT = eINSTANCE.getSiteProject();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference SITE_PROJECT__PARENT = eINSTANCE.getSiteProject_Parent();

      /**
       * The meta object literal for the '<em><b>Categories</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference SITE_PROJECT__CATEGORIES = eINSTANCE.getSiteProject_Categories();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.ProjectImpl <em>Project</em>}
       * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.ProjectImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getProject()
       * @generated
       */
      EClass PROJECT = eINSTANCE.getProject();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.ProjectFacetImpl
       * <em>Project Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.ProjectFacetImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getProjectFacet()
       * @generated
       */
      EClass PROJECT_FACET = eINSTANCE.getProjectFacet();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.FileContainerImpl
       * <em>File Container</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.FileContainerImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getFileContainer()
       * @generated
       */
      EClass FILE_CONTAINER = eINSTANCE.getFileContainer();

      /**
       * The meta object literal for the '<em><b>Directory</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute FILE_CONTAINER__DIRECTORY = eINSTANCE.getFileContainer_Directory();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.internal.model.Derivable <em>Derivable</em>}'
       * class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.internal.model.Derivable
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getDerivable()
       * @generated
       */
      EClass DERIVABLE = eINSTANCE.getDerivable();

      /**
       * The meta object literal for the '<em><b>Derived</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute DERIVABLE__DERIVED = eINSTANCE.getDerivable_Derived();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.PluginIncludeImpl
       * <em>Plugin Include</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.PluginIncludeImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getPluginInclude()
       * @generated
       */
      EClass PLUGIN_INCLUDE = eINSTANCE.getPluginInclude();

      /**
       * The meta object literal for the '<em><b>Unpack</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute PLUGIN_INCLUDE__UNPACK = eINSTANCE.getPluginInclude_Unpack();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PLUGIN_INCLUDE__PARENT = eINSTANCE.getPluginInclude_Parent();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.CategoryImpl
       * <em>Category</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.CategoryImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getCategory()
       * @generated
       */
      EClass CATEGORY = eINSTANCE.getCategory();

      /**
       * The meta object literal for the '<em><b>Feature References</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference CATEGORY__FEATURE_REFERENCES = eINSTANCE.getCategory_FeatureReferences();

      /**
       * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute CATEGORY__NAME = eINSTANCE.getCategory_Name();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.FeatureIncludeImpl
       * <em>Feature Include</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.FeatureIncludeImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getFeatureInclude()
       * @generated
       */
      EClass FEATURE_INCLUDE = eINSTANCE.getFeatureInclude();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference FEATURE_INCLUDE__PARENT = eINSTANCE.getFeatureInclude_Parent();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.internal.model.Classified <em>Classified</em>}'
       * class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.internal.model.Classified
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getClassified()
       * @generated
       */
      EClass CLASSIFIED = eINSTANCE.getClassified();

      /**
       * The meta object literal for the '<em><b>Classifier</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute CLASSIFIED__CLASSIFIER = eINSTANCE.getClassified_Classifier();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.internal.model.Annotateable
       * <em>Annotateable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.internal.model.Annotateable
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getAnnotateable()
       * @generated
       */
      EClass ANNOTATEABLE = eINSTANCE.getAnnotateable();

      /**
       * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference ANNOTATEABLE__ANNOTATIONS = eINSTANCE.getAnnotateable_Annotations();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.AnnotationImpl
       * <em>Annotation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.AnnotationImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getAnnotation()
       * @generated
       */
      EClass ANNOTATION = eINSTANCE.getAnnotation();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference ANNOTATION__PARENT = eINSTANCE.getAnnotation_Parent();

      /**
       * The meta object literal for the '<em><b>Source</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ANNOTATION__SOURCE = eINSTANCE.getAnnotation_Source();

      /**
       * The meta object literal for the '<em><b>Entries</b></em>' map feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EReference ANNOTATION__ENTRIES = eINSTANCE.getAnnotation_Entries();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.EStringMapEntryImpl
       * <em>EString Map Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.EStringMapEntryImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getEStringMapEntry()
       * @generated
       */
      EClass ESTRING_MAP_ENTRY = eINSTANCE.getEStringMapEntry();

      /**
       * The meta object literal for the '<em><b>Key</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ESTRING_MAP_ENTRY__KEY = eINSTANCE.getEStringMapEntry_Key();

      /**
       * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ESTRING_MAP_ENTRY__VALUE = eINSTANCE.getEStringMapEntry_Value();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.internal.model.Identifiable
       * <em>Identifiable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.internal.model.Identifiable
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getIdentifiable()
       * @generated
       */
      EClass IDENTIFIABLE = eINSTANCE.getIdentifiable();

      /**
       * The meta object literal for the '<em><b>Id</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute IDENTIFIABLE__ID = eINSTANCE.getIdentifiable_Id();

      /**
       * The meta object literal for the '<em><b>Version</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute IDENTIFIABLE__VERSION = eINSTANCE.getIdentifiable_Version();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.ProductsFacetImpl
       * <em>Products Facet</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.ProductsFacetImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getProductsFacet()
       * @generated
       */
      EClass PRODUCTS_FACET = eINSTANCE.getProductsFacet();

      /**
       * The meta object literal for the '<em><b>Product Definitions</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PRODUCTS_FACET__PRODUCT_DEFINITIONS = eINSTANCE.getProductsFacet_ProductDefinitions();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.ProductDefinitionImpl
       * <em>Product Definition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.ProductDefinitionImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getProductDefinition()
       * @generated
       */
      EClass PRODUCT_DEFINITION = eINSTANCE.getProductDefinition();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PRODUCT_DEFINITION__PARENT = eINSTANCE.getProductDefinition_Parent();

      /**
       * The meta object literal for the '<em><b>File</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute PRODUCT_DEFINITION__FILE = eINSTANCE.getProductDefinition_File();

      /**
       * The meta object literal for the '<em><b>Product Plugin</b></em>' containment reference feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference PRODUCT_DEFINITION__PRODUCT_PLUGIN = eINSTANCE.getProductDefinition_ProductPlugin();

      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.internal.impl.ReferenceImpl
       * <em>Reference</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.internal.impl.ReferenceImpl
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getReference()
       * @generated
       */
      EClass REFERENCE = eINSTANCE.getReference();

      /**
       * The meta object literal for the '<em><b>Id</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute REFERENCE__ID = eINSTANCE.getReference_Id();

      /**
       * The meta object literal for the '<em><b>Version Range</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute REFERENCE__VERSION_RANGE = eINSTANCE.getReference_VersionRange();

      /**
       * The meta object literal for the '<em>Identifier</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.util.Identifier
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getIdentifier()
       * @generated
       */
      EDataType IDENTIFIER = eINSTANCE.getIdentifier();

      /**
       * The meta object literal for the '<em>EJava File</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see java.io.File
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getEJavaFile()
       * @generated
       */
      EDataType EJAVA_FILE = eINSTANCE.getEJavaFile();

      /**
       * The meta object literal for the '<em>ELocale</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see java.util.Locale
       * @see org.sourcepit.beef.b2.model.internal.impl.B2ModelPackageImpl#getELocale()
       * @generated
       */
      EDataType ELOCALE = eINSTANCE.getELocale();

   }

} // B2ModelPackage
