/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.common.Annotatable;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Session</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.session.B2Session#getProjects <em>Projects</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.B2Session#getCurrentProject <em>Current Project</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.session.SessionModelPackage#getB2Session()
 * @model
 * @generated
 */
public interface B2Session extends Annotatable
{

   /**
    * Returns the value of the '<em><b>Projects</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.b2.model.session.ModuleProject}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.b2.model.session.ModuleProject#getSession <em>Session</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Projects</em>' containment reference list isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Projects</em>' containment reference list.
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getB2Session_Projects()
    * @see org.sourcepit.b2.model.session.ModuleProject#getSession
    * @model opposite="session" containment="true" resolveProxies="true"
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
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getB2Session_CurrentProject()
    * @model required="true"
    * @generated
    */
   ModuleProject getCurrentProject();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.B2Session#getCurrentProject <em>Current Project</em>}
    * ' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Current Project</em>' reference.
    * @see #getCurrentProject()
    * @generated
    */
   void setCurrentProject(ModuleProject value);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   ModuleProject getProject(String groupId, String artifactId, String version);
} // Session
