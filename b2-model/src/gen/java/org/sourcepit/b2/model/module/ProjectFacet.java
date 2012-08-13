/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Project Facet</b></em>'. <!-- end-user-doc -->
 * 
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getProjectFacet()
 * @model abstract="true"
 * @generated
 */
public interface ProjectFacet<P extends Project> extends AbstractFacet
{
   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model kind="operation" required="true" many="false"
    * @generated
    */
   EList<P> getProjects();

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   P getProjectById(String name);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model referenceRequired="true"
    * @generated
    */
   P resolveReference(Reference reference);

} // ProjectFacet
