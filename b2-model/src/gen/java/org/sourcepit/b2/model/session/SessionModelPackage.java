/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.b2.model.session;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sourcepit.b2.model.common.CommonModelPackage;

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
 * @see org.sourcepit.b2.model.session.SessionModelFactory
 * @model kind="package"
 * @generated
 */
public interface SessionModelPackage extends EPackage
{
   /**
    * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNAME = "session";

   /**
    * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_URI = "http://www.sourcepit.org/b2/model/0.1/session";

   /**
    * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eNS_PREFIX = "session";

   /**
    * The package content type ID. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   String eCONTENT_TYPE = "b2-session";

   /**
    * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   SessionModelPackage eINSTANCE = org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl.init();

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.session.internal.impl.B2SessionImpl
    * <em>B2 Session</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.session.internal.impl.B2SessionImpl
    * @see org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl#getB2Session()
    * @generated
    */
   int B2_SESSION = 0;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int B2_SESSION__ANNOTATIONS = CommonModelPackage.ANNOTATABLE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Projects</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int B2_SESSION__PROJECTS = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Current Project</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int B2_SESSION__CURRENT_PROJECT = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 1;

   /**
    * The number of structural features of the '<em>B2 Session</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int B2_SESSION_FEATURE_COUNT = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 2;

   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.session.internal.impl.ModuleProjectImpl
    * <em>Module Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.session.internal.impl.ModuleProjectImpl
    * @see org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl#getModuleProject()
    * @generated
    */
   int MODULE_PROJECT = 1;

   /**
    * The feature id for the '<em><b>Annotations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__ANNOTATIONS = CommonModelPackage.ANNOTATABLE__ANNOTATIONS;

   /**
    * The feature id for the '<em><b>Group Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__GROUP_ID = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 0;

   /**
    * The feature id for the '<em><b>Session</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__SESSION = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 1;

   /**
    * The feature id for the '<em><b>Artifact Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__ARTIFACT_ID = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 2;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__VERSION = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 3;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__DIRECTORY = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 4;

   /**
    * The feature id for the '<em><b>Dependencies</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__DEPENDENCIES = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 5;

   /**
    * The feature id for the '<em><b>Module Model</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__MODULE_MODEL = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 6;

   /**
    * The feature id for the '<em><b>Attachments</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__ATTACHMENTS = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 7;

   /**
    * The feature id for the '<em><b>Environements</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__ENVIRONEMENTS = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 8;

   /**
    * The number of structural features of the '<em>Module Project</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT_FEATURE_COUNT = CommonModelPackage.ANNOTATABLE_FEATURE_COUNT + 9;


   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.session.internal.impl.ModuleDependencyImpl
    * <em>Module Dependency</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.session.internal.impl.ModuleDependencyImpl
    * @see org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl#getModuleDependency()
    * @generated
    */
   int MODULE_DEPENDENCY = 2;

   /**
    * The feature id for the '<em><b>Group Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_DEPENDENCY__GROUP_ID = 0;

   /**
    * The feature id for the '<em><b>Artifact Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_DEPENDENCY__ARTIFACT_ID = 1;

   /**
    * The feature id for the '<em><b>Version Range</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_DEPENDENCY__VERSION_RANGE = 2;

   /**
    * The feature id for the '<em><b>Classifier</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_DEPENDENCY__CLASSIFIER = 3;

   /**
    * The number of structural features of the '<em>Module Dependency</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_DEPENDENCY_FEATURE_COUNT = 4;


   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.session.internal.impl.ModuleAttachmentImpl
    * <em>Module Attachment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.session.internal.impl.ModuleAttachmentImpl
    * @see org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl#getModuleAttachment()
    * @generated
    */
   int MODULE_ATTACHMENT = 3;

   /**
    * The feature id for the '<em><b>Classifier</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_ATTACHMENT__CLASSIFIER = 0;

   /**
    * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_ATTACHMENT__TYPE = 1;

   /**
    * The feature id for the '<em><b>File</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_ATTACHMENT__FILE = 2;

   /**
    * The feature id for the '<em><b>Parent</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_ATTACHMENT__PARENT = 3;

   /**
    * The number of structural features of the '<em>Module Attachment</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_ATTACHMENT_FEATURE_COUNT = 4;


   /**
    * The meta object id for the '{@link org.sourcepit.b2.model.session.internal.impl.EnvironmentImpl
    * <em>Environment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.b2.model.session.internal.impl.EnvironmentImpl
    * @see org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl#getEnvironment()
    * @generated
    */
   int ENVIRONMENT = 4;

   /**
    * The feature id for the '<em><b>Os</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ENVIRONMENT__OS = 0;

   /**
    * The feature id for the '<em><b>Ws</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ENVIRONMENT__WS = 1;

   /**
    * The feature id for the '<em><b>Arch</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int ENVIRONMENT__ARCH = 2;

   /**
    * The number of structural features of the '<em>Environment</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int ENVIRONMENT_FEATURE_COUNT = 3;


   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.session.B2Session <em>B2 Session</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>B2 Session</em>'.
    * @see org.sourcepit.b2.model.session.B2Session
    * @generated
    */
   EClass getB2Session();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.session.B2Session#getProjects <em>Projects</em>}'. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Projects</em>'.
    * @see org.sourcepit.b2.model.session.B2Session#getProjects()
    * @see #getB2Session()
    * @generated
    */
   EReference getB2Session_Projects();

   /**
    * Returns the meta object for the reference '{@link org.sourcepit.b2.model.session.B2Session#getCurrentProject
    * <em>Current Project</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the reference '<em>Current Project</em>'.
    * @see org.sourcepit.b2.model.session.B2Session#getCurrentProject()
    * @see #getB2Session()
    * @generated
    */
   EReference getB2Session_CurrentProject();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.session.ModuleProject
    * <em>Module Project</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Module Project</em>'.
    * @see org.sourcepit.b2.model.session.ModuleProject
    * @generated
    */
   EClass getModuleProject();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.session.ModuleProject#getGroupId
    * <em>Group Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Group Id</em>'.
    * @see org.sourcepit.b2.model.session.ModuleProject#getGroupId()
    * @see #getModuleProject()
    * @generated
    */
   EAttribute getModuleProject_GroupId();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.b2.model.session.ModuleProject#getSession <em>Session</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Session</em>'.
    * @see org.sourcepit.b2.model.session.ModuleProject#getSession()
    * @see #getModuleProject()
    * @generated
    */
   EReference getModuleProject_Session();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.session.ModuleProject#getArtifactId
    * <em>Artifact Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Artifact Id</em>'.
    * @see org.sourcepit.b2.model.session.ModuleProject#getArtifactId()
    * @see #getModuleProject()
    * @generated
    */
   EAttribute getModuleProject_ArtifactId();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.session.ModuleProject#getVersion
    * <em>Version</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Version</em>'.
    * @see org.sourcepit.b2.model.session.ModuleProject#getVersion()
    * @see #getModuleProject()
    * @generated
    */
   EAttribute getModuleProject_Version();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.session.ModuleProject#getDirectory
    * <em>Directory</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Directory</em>'.
    * @see org.sourcepit.b2.model.session.ModuleProject#getDirectory()
    * @see #getModuleProject()
    * @generated
    */
   EAttribute getModuleProject_Directory();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.session.ModuleProject#getDependencies <em>Dependencies</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Dependencies</em>'.
    * @see org.sourcepit.b2.model.session.ModuleProject#getDependencies()
    * @see #getModuleProject()
    * @generated
    */
   EReference getModuleProject_Dependencies();

   /**
    * Returns the meta object for the reference '
    * {@link org.sourcepit.b2.model.session.ModuleProject#getModuleModel <em>Module Model</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the reference '<em>Module Model</em>'.
    * @see org.sourcepit.b2.model.session.ModuleProject#getModuleModel()
    * @see #getModuleProject()
    * @generated
    */
   EReference getModuleProject_ModuleModel();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.session.ModuleProject#getAttachments <em>Attachments</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Attachments</em>'.
    * @see org.sourcepit.b2.model.session.ModuleProject#getAttachments()
    * @see #getModuleProject()
    * @generated
    */
   EReference getModuleProject_Attachments();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.b2.model.session.ModuleProject#getEnvironements <em>Environements</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Environements</em>'.
    * @see org.sourcepit.b2.model.session.ModuleProject#getEnvironements()
    * @see #getModuleProject()
    * @generated
    */
   EReference getModuleProject_Environements();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.session.ModuleDependency
    * <em>Module Dependency</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Module Dependency</em>'.
    * @see org.sourcepit.b2.model.session.ModuleDependency
    * @generated
    */
   EClass getModuleDependency();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.session.ModuleDependency#getGroupId
    * <em>Group Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Group Id</em>'.
    * @see org.sourcepit.b2.model.session.ModuleDependency#getGroupId()
    * @see #getModuleDependency()
    * @generated
    */
   EAttribute getModuleDependency_GroupId();

   /**
    * Returns the meta object for the attribute '
    * {@link org.sourcepit.b2.model.session.ModuleDependency#getArtifactId <em>Artifact Id</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Artifact Id</em>'.
    * @see org.sourcepit.b2.model.session.ModuleDependency#getArtifactId()
    * @see #getModuleDependency()
    * @generated
    */
   EAttribute getModuleDependency_ArtifactId();

   /**
    * Returns the meta object for the attribute '
    * {@link org.sourcepit.b2.model.session.ModuleDependency#getVersionRange <em>Version Range</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Version Range</em>'.
    * @see org.sourcepit.b2.model.session.ModuleDependency#getVersionRange()
    * @see #getModuleDependency()
    * @generated
    */
   EAttribute getModuleDependency_VersionRange();

   /**
    * Returns the meta object for the attribute '
    * {@link org.sourcepit.b2.model.session.ModuleDependency#getClassifier <em>Classifier</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Classifier</em>'.
    * @see org.sourcepit.b2.model.session.ModuleDependency#getClassifier()
    * @see #getModuleDependency()
    * @generated
    */
   EAttribute getModuleDependency_Classifier();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.session.ModuleAttachment
    * <em>Module Attachment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Module Attachment</em>'.
    * @see org.sourcepit.b2.model.session.ModuleAttachment
    * @generated
    */
   EClass getModuleAttachment();

   /**
    * Returns the meta object for the attribute '
    * {@link org.sourcepit.b2.model.session.ModuleAttachment#getClassifier <em>Classifier</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Classifier</em>'.
    * @see org.sourcepit.b2.model.session.ModuleAttachment#getClassifier()
    * @see #getModuleAttachment()
    * @generated
    */
   EAttribute getModuleAttachment_Classifier();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.session.ModuleAttachment#getType
    * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Type</em>'.
    * @see org.sourcepit.b2.model.session.ModuleAttachment#getType()
    * @see #getModuleAttachment()
    * @generated
    */
   EAttribute getModuleAttachment_Type();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.session.ModuleAttachment#getFile
    * <em>File</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>File</em>'.
    * @see org.sourcepit.b2.model.session.ModuleAttachment#getFile()
    * @see #getModuleAttachment()
    * @generated
    */
   EAttribute getModuleAttachment_File();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.b2.model.session.ModuleAttachment#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Parent</em>'.
    * @see org.sourcepit.b2.model.session.ModuleAttachment#getParent()
    * @see #getModuleAttachment()
    * @generated
    */
   EReference getModuleAttachment_Parent();

   /**
    * Returns the meta object for class '{@link org.sourcepit.b2.model.session.Environment <em>Environment</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Environment</em>'.
    * @see org.sourcepit.b2.model.session.Environment
    * @generated
    */
   EClass getEnvironment();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.session.Environment#getOs
    * <em>Os</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Os</em>'.
    * @see org.sourcepit.b2.model.session.Environment#getOs()
    * @see #getEnvironment()
    * @generated
    */
   EAttribute getEnvironment_Os();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.session.Environment#getWs
    * <em>Ws</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Ws</em>'.
    * @see org.sourcepit.b2.model.session.Environment#getWs()
    * @see #getEnvironment()
    * @generated
    */
   EAttribute getEnvironment_Ws();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.b2.model.session.Environment#getArch
    * <em>Arch</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Arch</em>'.
    * @see org.sourcepit.b2.model.session.Environment#getArch()
    * @see #getEnvironment()
    * @generated
    */
   EAttribute getEnvironment_Arch();

   /**
    * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the factory that creates the instances of the model.
    * @generated
    */
   SessionModelFactory getSessionModelFactory();

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
       * The meta object literal for the '{@link org.sourcepit.b2.model.session.internal.impl.B2SessionImpl
       * <em>B2 Session</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.session.internal.impl.B2SessionImpl
       * @see org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl#getB2Session()
       * @generated
       */
      EClass B2_SESSION = eINSTANCE.getB2Session();

      /**
       * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference B2_SESSION__PROJECTS = eINSTANCE.getB2Session_Projects();

      /**
       * The meta object literal for the '<em><b>Current Project</b></em>' reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference B2_SESSION__CURRENT_PROJECT = eINSTANCE.getB2Session_CurrentProject();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.session.internal.impl.ModuleProjectImpl
       * <em>Module Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.session.internal.impl.ModuleProjectImpl
       * @see org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl#getModuleProject()
       * @generated
       */
      EClass MODULE_PROJECT = eINSTANCE.getModuleProject();

      /**
       * The meta object literal for the '<em><b>Group Id</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_PROJECT__GROUP_ID = eINSTANCE.getModuleProject_GroupId();

      /**
       * The meta object literal for the '<em><b>Session</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference MODULE_PROJECT__SESSION = eINSTANCE.getModuleProject_Session();

      /**
       * The meta object literal for the '<em><b>Artifact Id</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_PROJECT__ARTIFACT_ID = eINSTANCE.getModuleProject_ArtifactId();

      /**
       * The meta object literal for the '<em><b>Version</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_PROJECT__VERSION = eINSTANCE.getModuleProject_Version();

      /**
       * The meta object literal for the '<em><b>Directory</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_PROJECT__DIRECTORY = eINSTANCE.getModuleProject_Directory();

      /**
       * The meta object literal for the '<em><b>Dependencies</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference MODULE_PROJECT__DEPENDENCIES = eINSTANCE.getModuleProject_Dependencies();

      /**
       * The meta object literal for the '<em><b>Module Model</b></em>' reference feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EReference MODULE_PROJECT__MODULE_MODEL = eINSTANCE.getModuleProject_ModuleModel();

      /**
       * The meta object literal for the '<em><b>Attachments</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference MODULE_PROJECT__ATTACHMENTS = eINSTANCE.getModuleProject_Attachments();

      /**
       * The meta object literal for the '<em><b>Environements</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference MODULE_PROJECT__ENVIRONEMENTS = eINSTANCE.getModuleProject_Environements();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.session.internal.impl.ModuleDependencyImpl
       * <em>Module Dependency</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.session.internal.impl.ModuleDependencyImpl
       * @see org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl#getModuleDependency()
       * @generated
       */
      EClass MODULE_DEPENDENCY = eINSTANCE.getModuleDependency();

      /**
       * The meta object literal for the '<em><b>Group Id</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_DEPENDENCY__GROUP_ID = eINSTANCE.getModuleDependency_GroupId();

      /**
       * The meta object literal for the '<em><b>Artifact Id</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_DEPENDENCY__ARTIFACT_ID = eINSTANCE.getModuleDependency_ArtifactId();

      /**
       * The meta object literal for the '<em><b>Version Range</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_DEPENDENCY__VERSION_RANGE = eINSTANCE.getModuleDependency_VersionRange();

      /**
       * The meta object literal for the '<em><b>Classifier</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_DEPENDENCY__CLASSIFIER = eINSTANCE.getModuleDependency_Classifier();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.session.internal.impl.ModuleAttachmentImpl
       * <em>Module Attachment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.session.internal.impl.ModuleAttachmentImpl
       * @see org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl#getModuleAttachment()
       * @generated
       */
      EClass MODULE_ATTACHMENT = eINSTANCE.getModuleAttachment();

      /**
       * The meta object literal for the '<em><b>Classifier</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_ATTACHMENT__CLASSIFIER = eINSTANCE.getModuleAttachment_Classifier();

      /**
       * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_ATTACHMENT__TYPE = eINSTANCE.getModuleAttachment_Type();

      /**
       * The meta object literal for the '<em><b>File</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_ATTACHMENT__FILE = eINSTANCE.getModuleAttachment_File();

      /**
       * The meta object literal for the '<em><b>Parent</b></em>' container reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference MODULE_ATTACHMENT__PARENT = eINSTANCE.getModuleAttachment_Parent();

      /**
       * The meta object literal for the '{@link org.sourcepit.b2.model.session.internal.impl.EnvironmentImpl
       * <em>Environment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.b2.model.session.internal.impl.EnvironmentImpl
       * @see org.sourcepit.b2.model.session.internal.impl.SessionModelPackageImpl#getEnvironment()
       * @generated
       */
      EClass ENVIRONMENT = eINSTANCE.getEnvironment();

      /**
       * The meta object literal for the '<em><b>Os</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ENVIRONMENT__OS = eINSTANCE.getEnvironment_Os();

      /**
       * The meta object literal for the '<em><b>Ws</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ENVIRONMENT__WS = eINSTANCE.getEnvironment_Ws();

      /**
       * The meta object literal for the '<em><b>Arch</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute ENVIRONMENT__ARCH = eINSTANCE.getEnvironment_Arch();

   }

} // SessionModelPackage
