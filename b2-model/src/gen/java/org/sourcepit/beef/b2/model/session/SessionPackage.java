/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.sourcepit.beef.b2.model.session.SessionFactory
 * @model kind="package"
 * @generated
 */
public interface SessionPackage extends EPackage
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
    * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   SessionPackage eINSTANCE = org.sourcepit.beef.b2.model.session.internal.impl.SessionPackageImpl.init();

   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl <em>Session</em>}
    * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl
    * @see org.sourcepit.beef.b2.model.session.internal.impl.SessionPackageImpl#getSession()
    * @generated
    */
   int SESSION = 0;

   /**
    * The feature id for the '<em><b>Projects</b></em>' containment reference list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SESSION__PROJECTS = 0;

   /**
    * The feature id for the '<em><b>Current Project</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SESSION__CURRENT_PROJECT = 1;

   /**
    * The feature id for the '<em><b>Data</b></em>' map. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SESSION__DATA = 2;

   /**
    * The number of structural features of the '<em>Session</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int SESSION_FEATURE_COUNT = 3;


   /**
    * The meta object id for the '{@link org.sourcepit.beef.b2.model.session.internal.impl.ModuleProjectImpl
    * <em>Module Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see org.sourcepit.beef.b2.model.session.internal.impl.ModuleProjectImpl
    * @see org.sourcepit.beef.b2.model.session.internal.impl.SessionPackageImpl#getModuleProject()
    * @generated
    */
   int MODULE_PROJECT = 1;

   /**
    * The feature id for the '<em><b>Group Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__GROUP_ID = 0;

   /**
    * The feature id for the '<em><b>Session</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__SESSION = 1;

   /**
    * The feature id for the '<em><b>Artifact Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__ARTIFACT_ID = 2;

   /**
    * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__VERSION = 3;

   /**
    * The feature id for the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__DIRECTORY = 4;

   /**
    * The feature id for the '<em><b>Skipped</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__SKIPPED = 5;

   /**
    * The feature id for the '<em><b>Data</b></em>' map. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT__DATA = 6;

   /**
    * The number of structural features of the '<em>Module Project</em>' class. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @generated
    * @ordered
    */
   int MODULE_PROJECT_FEATURE_COUNT = 7;


   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.model.session.Session <em>Session</em>}'. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Session</em>'.
    * @see org.sourcepit.beef.b2.model.session.Session
    * @generated
    */
   EClass getSession();

   /**
    * Returns the meta object for the containment reference list '
    * {@link org.sourcepit.beef.b2.model.session.Session#getProjects <em>Projects</em>}'. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @return the meta object for the containment reference list '<em>Projects</em>'.
    * @see org.sourcepit.beef.b2.model.session.Session#getProjects()
    * @see #getSession()
    * @generated
    */
   EReference getSession_Projects();

   /**
    * Returns the meta object for the reference '{@link org.sourcepit.beef.b2.model.session.Session#getCurrentProject
    * <em>Current Project</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the reference '<em>Current Project</em>'.
    * @see org.sourcepit.beef.b2.model.session.Session#getCurrentProject()
    * @see #getSession()
    * @generated
    */
   EReference getSession_CurrentProject();

   /**
    * Returns the meta object for the map '{@link org.sourcepit.beef.b2.model.session.Session#getData <em>Data</em>}'.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the map '<em>Data</em>'.
    * @see org.sourcepit.beef.b2.model.session.Session#getData()
    * @see #getSession()
    * @generated
    */
   EReference getSession_Data();

   /**
    * Returns the meta object for class '{@link org.sourcepit.beef.b2.model.session.ModuleProject
    * <em>Module Project</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for class '<em>Module Project</em>'.
    * @see org.sourcepit.beef.b2.model.session.ModuleProject
    * @generated
    */
   EClass getModuleProject();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getGroupId
    * <em>Group Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Group Id</em>'.
    * @see org.sourcepit.beef.b2.model.session.ModuleProject#getGroupId()
    * @see #getModuleProject()
    * @generated
    */
   EAttribute getModuleProject_GroupId();

   /**
    * Returns the meta object for the container reference '
    * {@link org.sourcepit.beef.b2.model.session.ModuleProject#getSession <em>Session</em>}'. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @return the meta object for the container reference '<em>Session</em>'.
    * @see org.sourcepit.beef.b2.model.session.ModuleProject#getSession()
    * @see #getModuleProject()
    * @generated
    */
   EReference getModuleProject_Session();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getArtifactId
    * <em>Artifact Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Artifact Id</em>'.
    * @see org.sourcepit.beef.b2.model.session.ModuleProject#getArtifactId()
    * @see #getModuleProject()
    * @generated
    */
   EAttribute getModuleProject_ArtifactId();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getVersion
    * <em>Version</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Version</em>'.
    * @see org.sourcepit.beef.b2.model.session.ModuleProject#getVersion()
    * @see #getModuleProject()
    * @generated
    */
   EAttribute getModuleProject_Version();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getDirectory
    * <em>Directory</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Directory</em>'.
    * @see org.sourcepit.beef.b2.model.session.ModuleProject#getDirectory()
    * @see #getModuleProject()
    * @generated
    */
   EAttribute getModuleProject_Directory();

   /**
    * Returns the meta object for the attribute '{@link org.sourcepit.beef.b2.model.session.ModuleProject#isSkipped
    * <em>Skipped</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the attribute '<em>Skipped</em>'.
    * @see org.sourcepit.beef.b2.model.session.ModuleProject#isSkipped()
    * @see #getModuleProject()
    * @generated
    */
   EAttribute getModuleProject_Skipped();

   /**
    * Returns the meta object for the map '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getData
    * <em>Data</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the meta object for the map '<em>Data</em>'.
    * @see org.sourcepit.beef.b2.model.session.ModuleProject#getData()
    * @see #getModuleProject()
    * @generated
    */
   EReference getModuleProject_Data();

   /**
    * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the factory that creates the instances of the model.
    * @generated
    */
   SessionFactory getSessionFactory();

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
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl
       * <em>Session</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl
       * @see org.sourcepit.beef.b2.model.session.internal.impl.SessionPackageImpl#getSession()
       * @generated
       */
      EClass SESSION = eINSTANCE.getSession();
      /**
       * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature. <!--
       * begin-user-doc --> <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference SESSION__PROJECTS = eINSTANCE.getSession_Projects();
      /**
       * The meta object literal for the '<em><b>Current Project</b></em>' reference feature. <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * 
       * @generated
       */
      EReference SESSION__CURRENT_PROJECT = eINSTANCE.getSession_CurrentProject();
      /**
       * The meta object literal for the '<em><b>Data</b></em>' map feature. <!-- begin-user-doc --> <!-- end-user-doc
       * -->
       * 
       * @generated
       */
      EReference SESSION__DATA = eINSTANCE.getSession_Data();
      /**
       * The meta object literal for the '{@link org.sourcepit.beef.b2.model.session.internal.impl.ModuleProjectImpl
       * <em>Module Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
       * 
       * @see org.sourcepit.beef.b2.model.session.internal.impl.ModuleProjectImpl
       * @see org.sourcepit.beef.b2.model.session.internal.impl.SessionPackageImpl#getModuleProject()
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
       * The meta object literal for the '<em><b>Skipped</b></em>' attribute feature. <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * 
       * @generated
       */
      EAttribute MODULE_PROJECT__SKIPPED = eINSTANCE.getModuleProject_Skipped();
      /**
       * The meta object literal for the '<em><b>Data</b></em>' map feature. <!-- begin-user-doc --> <!-- end-user-doc
       * -->
       * 
       * @generated
       */
      EReference MODULE_PROJECT__DATA = eINSTANCE.getModuleProject_Data();

   }

} // SessionPackage
