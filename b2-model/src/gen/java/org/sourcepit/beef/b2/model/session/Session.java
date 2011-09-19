/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.beef.b2.model.common.Annotateable;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Session</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.session.Session#getProjects <em>Projects</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.Session#getCurrentProject <em>Current Project</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getSession()
 * @model
 * @generated
 */
public interface Session extends Annotateable
{

   /**
    * Returns the value of the '<em><b>Projects</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.beef.b2.model.session.ModuleProject}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.session.ModuleProject#getSession <em>Session</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Projects</em>' containment reference list isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Projects</em>' containment reference list.
    * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getSession_Projects()
    * @see org.sourcepit.beef.b2.model.session.ModuleProject#getSession
    * @model opposite="session" containment="true"
    * @generated
    */
   EList<ModuleProject> getProjects();

   /**
    * Returns the value of the '<em><b>Current Project</b></em>' reference. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Current Project</em>' reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Current Project</em>' reference.
    * @see #setCurrentProject(ModuleProject)
    * @see org.sourcepit.beef.b2.model.session.SessionModelPackage#getSession_CurrentProject()
    * @model required="true"
    * @generated
    */
   ModuleProject getCurrentProject();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.session.Session#getCurrentProject
    * <em>Current Project</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Current Project</em>' reference.
    * @see #getCurrentProject()
    * @generated
    */
   void setCurrentProject(ModuleProject value);
} // Session
