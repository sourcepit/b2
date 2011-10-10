/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Sites Facet</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.SitesFacetImpl#getProjects <em>Projects</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class SitesFacetImpl extends ProjectFacetImpl<SiteProject> implements SitesFacet
{
   /**
    * The cached value of the '{@link #getProjects() <em>Projects</em>}' containment reference list. <!-- begin-user-doc
    * --> <!-- end-user-doc -->
    * 
    * @see #getProjects()
    * @generated
    * @ordered
    */
   protected EList<SiteProject> projects;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected SitesFacetImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   protected EClass eStaticClass()
   {
      return ModuleModelPackage.Literals.SITES_FACET;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<SiteProject> getProjects()
   {
      if (projects == null)
      {
         projects = new EObjectContainmentWithInverseEList.Resolving<SiteProject>(SiteProject.class, this,
            ModuleModelPackage.SITES_FACET__PROJECTS, ModuleModelPackage.SITE_PROJECT__PARENT);
      }
      return projects;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @SuppressWarnings("unchecked")
   @Override
   public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITES_FACET__PROJECTS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getProjects()).basicAdd(otherEnd, msgs);
      }
      return super.eInverseAdd(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITES_FACET__PROJECTS :
            return ((InternalEList<?>) getProjects()).basicRemove(otherEnd, msgs);
      }
      return super.eInverseRemove(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITES_FACET__PROJECTS :
            return getProjects();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @SuppressWarnings("unchecked")
   @Override
   public void eSet(int featureID, Object newValue)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITES_FACET__PROJECTS :
            getProjects().clear();
            getProjects().addAll((Collection<? extends SiteProject>) newValue);
            return;
      }
      super.eSet(featureID, newValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public void eUnset(int featureID)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITES_FACET__PROJECTS :
            getProjects().clear();
            return;
      }
      super.eUnset(featureID);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public boolean eIsSet(int featureID)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITES_FACET__PROJECTS :
            return projects != null && !projects.isEmpty();
      }
      return super.eIsSet(featureID);
   }

} // SitesFacetImpl
