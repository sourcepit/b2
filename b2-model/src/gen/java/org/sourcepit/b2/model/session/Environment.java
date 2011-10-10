/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Environment</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.session.Environment#getOs <em>Os</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.Environment#getWs <em>Ws</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.Environment#getArch <em>Arch</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.session.SessionModelPackage#getEnvironment()
 * @model
 * @generated
 */
public interface Environment extends EObject
{
   /**
    * Returns the value of the '<em><b>Os</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Os</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Os</em>' attribute.
    * @see #setOs(String)
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getEnvironment_Os()
    * @model
    * @generated
    */
   String getOs();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.Environment#getOs <em>Os</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Os</em>' attribute.
    * @see #getOs()
    * @generated
    */
   void setOs(String value);

   /**
    * Returns the value of the '<em><b>Ws</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Ws</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Ws</em>' attribute.
    * @see #setWs(String)
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getEnvironment_Ws()
    * @model
    * @generated
    */
   String getWs();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.Environment#getWs <em>Ws</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Ws</em>' attribute.
    * @see #getWs()
    * @generated
    */
   void setWs(String value);

   /**
    * Returns the value of the '<em><b>Arch</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Arch</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Arch</em>' attribute.
    * @see #setArch(String)
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getEnvironment_Arch()
    * @model
    * @generated
    */
   String getArch();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.Environment#getArch <em>Arch</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Arch</em>' attribute.
    * @see #getArch()
    * @generated
    */
   void setArch(String value);

} // Environment
