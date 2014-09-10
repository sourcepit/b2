/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.model.module.internal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Site Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.SiteProjectImpl#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.SiteProjectImpl#getCategories <em>Categories</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.SiteProjectImpl#getInstallableUnits <em>Installable Units
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class SiteProjectImpl extends ProjectImpl implements SiteProject
{
   /**
    * The cached value of the '{@link #getCategories() <em>Categories</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getCategories()
    * @generated
    * @ordered
    */
   protected EList<Category> categories;

   /**
    * The cached value of the '{@link #getInstallableUnits() <em>Installable Units</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getInstallableUnits()
    * @generated
    * @ordered
    */
   protected EList<AbstractStrictReference> installableUnits;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   protected SiteProjectImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   protected EClass eStaticClass()
   {
      return ModuleModelPackage.Literals.SITE_PROJECT;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public SitesFacet getParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.SITE_PROJECT__PARENT)
         return null;
      return (SitesFacet) eContainer();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public SitesFacet basicGetParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.SITE_PROJECT__PARENT)
         return null;
      return (SitesFacet) eInternalContainer();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(SitesFacet newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, ModuleModelPackage.SITE_PROJECT__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setParent(SitesFacet newParent)
   {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != ModuleModelPackage.SITE_PROJECT__PARENT && newParent != null))
      {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this, ModuleModelPackage.SITES_FACET__PROJECTS,
               SitesFacet.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.SITE_PROJECT__PARENT, newParent,
            newParent));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<Category> getCategories()
   {
      if (categories == null)
      {
         categories = new EObjectContainmentEList.Resolving<Category>(Category.class, this,
            ModuleModelPackage.SITE_PROJECT__CATEGORIES);
      }
      return categories;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<AbstractStrictReference> getInstallableUnits()
   {
      if (installableUnits == null)
      {
         installableUnits = new EObjectContainmentEList.Resolving<AbstractStrictReference>(
            AbstractStrictReference.class, this, ModuleModelPackage.SITE_PROJECT__INSTALLABLE_UNITS);
      }
      return installableUnits;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITE_PROJECT__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((SitesFacet) otherEnd, msgs);
      }
      return super.eInverseAdd(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITE_PROJECT__PARENT :
            return basicSetParent(null, msgs);
         case ModuleModelPackage.SITE_PROJECT__CATEGORIES :
            return ((InternalEList<?>) getCategories()).basicRemove(otherEnd, msgs);
         case ModuleModelPackage.SITE_PROJECT__INSTALLABLE_UNITS :
            return ((InternalEList<?>) getInstallableUnits()).basicRemove(otherEnd, msgs);
      }
      return super.eInverseRemove(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs)
   {
      switch (eContainerFeatureID())
      {
         case ModuleModelPackage.SITE_PROJECT__PARENT :
            return eInternalContainer().eInverseRemove(this, ModuleModelPackage.SITES_FACET__PROJECTS,
               SitesFacet.class, msgs);
      }
      return super.eBasicRemoveFromContainerFeature(msgs);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITE_PROJECT__PARENT :
            if (resolve)
               return getParent();
            return basicGetParent();
         case ModuleModelPackage.SITE_PROJECT__CATEGORIES :
            return getCategories();
         case ModuleModelPackage.SITE_PROJECT__INSTALLABLE_UNITS :
            return getInstallableUnits();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @SuppressWarnings("unchecked")
   @Override
   public void eSet(int featureID, Object newValue)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITE_PROJECT__PARENT :
            setParent((SitesFacet) newValue);
            return;
         case ModuleModelPackage.SITE_PROJECT__CATEGORIES :
            getCategories().clear();
            getCategories().addAll((Collection<? extends Category>) newValue);
            return;
         case ModuleModelPackage.SITE_PROJECT__INSTALLABLE_UNITS :
            getInstallableUnits().clear();
            getInstallableUnits().addAll((Collection<? extends AbstractStrictReference>) newValue);
            return;
      }
      super.eSet(featureID, newValue);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public void eUnset(int featureID)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITE_PROJECT__PARENT :
            setParent((SitesFacet) null);
            return;
         case ModuleModelPackage.SITE_PROJECT__CATEGORIES :
            getCategories().clear();
            return;
         case ModuleModelPackage.SITE_PROJECT__INSTALLABLE_UNITS :
            getInstallableUnits().clear();
            return;
      }
      super.eUnset(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public boolean eIsSet(int featureID)
   {
      switch (featureID)
      {
         case ModuleModelPackage.SITE_PROJECT__PARENT :
            return basicGetParent() != null;
         case ModuleModelPackage.SITE_PROJECT__CATEGORIES :
            return categories != null && !categories.isEmpty();
         case ModuleModelPackage.SITE_PROJECT__INSTALLABLE_UNITS :
            return installableUnits != null && !installableUnits.isEmpty();
      }
      return super.eIsSet(featureID);
   }

} // SiteProjectImpl
