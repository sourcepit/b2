/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.common;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotatable</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.common.Annotatable#getAnnotations <em>Annotations</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.common.CommonModelPackage#getAnnotatable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Annotatable extends EObject
{
   /**
    * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
    * The list contents are of type {@link org.sourcepit.b2.model.common.Annotation}.
    * It is bidirectional and its opposite is '{@link org.sourcepit.b2.model.common.Annotation#getParent
    * <em>Parent</em>}'.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Annotations</em>' containment reference list isn't clear, there really should be more
    * of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Annotations</em>' containment reference list.
    * @see org.sourcepit.b2.model.common.CommonModelPackage#getAnnotatable_Annotations()
    * @see org.sourcepit.b2.model.common.Annotation#getParent
    * @model opposite="parent" containment="true" resolveProxies="true"
    * @generated
    */
   EList<Annotation> getAnnotations();

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   Annotation getAnnotation(String source);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   String getAnnotationEntry(String source, String key);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model required="true"
    * @generated
    */
   String putAnnotationEntry(String source, String key, String value);

} // Annotatable
