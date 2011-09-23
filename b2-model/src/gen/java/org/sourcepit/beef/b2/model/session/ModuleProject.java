/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.beef.b2.model.common.Annotatable;
import org.sourcepit.beef.b2.model.module.AbstractModule;

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
 * <li>{@link org.sourcepit.beef.b2.model.session.ModuleProject#getDependencies <em>Dependencies</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.ModuleProject#getModuleModel <em>Module Model</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getModuleProject()
 * @model
 * @generated
 */
public interface ModuleProject extends Annotatable
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
    * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getModuleProject_GroupId()
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
    * {@link org.sourcepit.beef.b2.model.session.B2Session#getProjects <em>Projects</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Session</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Session</em>' container reference.
    * @see #setSession(B2Session)
    * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getModuleProject_Session()
    * @see org.sourcepit.beef.b2.model.session.B2Session#getProjects
    * @model opposite="projects" required="true" transient="false"
    * @generated
    */
   B2Session getSession();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getSession <em>Session</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Session</em>' container reference.
    * @see #getSession()
    * @generated
    */
   void setSession(B2Session value);

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
    * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getModuleProject_ArtifactId()
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
    * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getModuleProject_Version()
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
    * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getModuleProject_Directory()
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
    * Returns the value of the '<em><b>Dependencies</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.beef.b2.model.session.ModuleDependency}. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Dependencies</em>' containment reference list isn't clear, there really should be more
    * of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Dependencies</em>' containment reference list.
    * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getModuleProject_Dependencies()
    * @model containment="true"
    * @generated
    */
   EList<ModuleDependency> getDependencies();

   /**
    * Returns the value of the '<em><b>Module Model</b></em>' reference. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Module Model</em>' reference isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Module Model</em>' reference.
    * @see #setModuleModel(AbstractModule)
    * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getModuleProject_ModuleModel()
    * @model
    * @generated
    */
   AbstractModule getModuleModel();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.session.ModuleProject#getModuleModel
    * <em>Module Model</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Module Model</em>' reference.
    * @see #getModuleModel()
    * @generated
    */
   void setModuleModel(AbstractModule value);

} // ModuleProject
