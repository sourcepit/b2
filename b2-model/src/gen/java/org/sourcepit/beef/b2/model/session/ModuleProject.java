/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session;

import java.io.File;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Project</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.session.ModuleProject#getGroupId <em>Group Id</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.ModuleProject#getSession <em>Session</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.ModuleProject#getArtifactId <em>Artifact Id</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.ModuleProject#getVersion <em>Version</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.ModuleProject#getDirectory <em>Directory</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.ModuleProject#isSkipped <em>Skipped</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.ModuleProject#getData <em>Data</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.session.SessionPackage#getModuleProject()
 * @model
 * @generated
 */
public interface ModuleProject extends EObject
{
   /**
    * Returns the value of the '<em><b>Group Id</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Group Id</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Group Id</em>' attribute.
    * @see #setGroupId(String)
    * @see org.sourcepit.beef.b2.model.session.SessionPackage#getModuleProject_GroupId()
    * @model required="true"
    * @generated
    */
   String getGroupId();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getGroupId <em>Group Id</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Group Id</em>' attribute.
    * @see #getGroupId()
    * @generated
    */
   void setGroupId(String value);

   /**
    * Returns the value of the '<em><b>Session</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.session.Session#getProjects <em>Projects</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Session</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Session</em>' container reference.
    * @see #setSession(Session)
    * @see org.sourcepit.beef.b2.model.session.SessionPackage#getModuleProject_Session()
    * @see org.sourcepit.beef.b2.model.session.Session#getProjects
    * @model opposite="projects" required="true" transient="false"
    * @generated
    */
   Session getSession();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getSession <em>Session</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Session</em>' container reference.
    * @see #getSession()
    * @generated
    */
   void setSession(Session value);

   /**
    * Returns the value of the '<em><b>Artifact Id</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Artifact Id</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Artifact Id</em>' attribute.
    * @see #setArtifactId(String)
    * @see org.sourcepit.beef.b2.model.session.SessionPackage#getModuleProject_ArtifactId()
    * @model required="true"
    * @generated
    */
   String getArtifactId();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getArtifactId
    * <em>Artifact Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Artifact Id</em>' attribute.
    * @see #getArtifactId()
    * @generated
    */
   void setArtifactId(String value);

   /**
    * Returns the value of the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Version</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Version</em>' attribute.
    * @see #setVersion(String)
    * @see org.sourcepit.beef.b2.model.session.SessionPackage#getModuleProject_Version()
    * @model required="true"
    * @generated
    */
   String getVersion();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getVersion <em>Version</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Version</em>' attribute.
    * @see #getVersion()
    * @generated
    */
   void setVersion(String value);

   /**
    * Returns the value of the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Directory</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Directory</em>' attribute.
    * @see #setDirectory(File)
    * @see org.sourcepit.beef.b2.model.session.SessionPackage#getModuleProject_Directory()
    * @model dataType="org.sourcepit.beef.b2.model.common.EJavaFile" required="true"
    * @generated
    */
   File getDirectory();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getDirectory <em>Directory</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Directory</em>' attribute.
    * @see #getDirectory()
    * @generated
    */
   void setDirectory(File value);

   /**
    * Returns the value of the '<em><b>Skipped</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Skipped</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Skipped</em>' attribute.
    * @see #setSkipped(boolean)
    * @see org.sourcepit.beef.b2.model.session.SessionPackage#getModuleProject_Skipped()
    * @model required="true"
    * @generated
    */
   boolean isSkipped();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.session.ModuleProject#isSkipped <em>Skipped</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Skipped</em>' attribute.
    * @see #isSkipped()
    * @generated
    */
   void setSkipped(boolean value);

   /**
    * Returns the value of the '<em><b>Data</b></em>' map. The key is of type {@link K}, and the value is of type
    * {@link V}, <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Data</em>' map isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Data</em>' map.
    * @see org.sourcepit.beef.b2.model.session.SessionPackage#getModuleProject_Data()
    * @model mapType="org.sourcepit.beef.b2.model.common.EMapEntry<K, V>"
    * @generated
    */
   EMap<String, Object> getData();

} // ModuleProject
