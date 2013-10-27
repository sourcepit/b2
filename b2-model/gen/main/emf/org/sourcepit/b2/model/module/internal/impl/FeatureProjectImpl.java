/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.RuledReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.FeatureProjectImpl#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.FeatureProjectImpl#getIncludedPlugins <em>Included Plugins
 * </em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.FeatureProjectImpl#getIncludedFeatures <em>Included Features
 * </em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.FeatureProjectImpl#getRequiredFeatures <em>Required Features
 * </em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.FeatureProjectImpl#getRequiredPlugins <em>Required Plugins
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class FeatureProjectImpl extends ProjectImpl implements FeatureProject
{
   /**
    * The cached value of the '{@link #getIncludedPlugins() <em>Included Plugins</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getIncludedPlugins()
    * @generated
    * @ordered
    */
   protected EList<PluginInclude> includedPlugins;

   /**
    * The cached value of the '{@link #getIncludedFeatures() <em>Included Features</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getIncludedFeatures()
    * @generated
    * @ordered
    */
   protected EList<FeatureInclude> includedFeatures;

   /**
    * The cached value of the '{@link #getRequiredFeatures() <em>Required Features</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getRequiredFeatures()
    * @generated
    * @ordered
    */
   protected EList<RuledReference> requiredFeatures;

   /**
    * The cached value of the '{@link #getRequiredPlugins() <em>Required Plugins</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getRequiredPlugins()
    * @generated
    * @ordered
    */
   protected EList<RuledReference> requiredPlugins;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   protected FeatureProjectImpl()
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
      return ModuleModelPackage.Literals.FEATURE_PROJECT;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeaturesFacet getParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.FEATURE_PROJECT__PARENT)
         return null;
      return (FeaturesFacet) eContainer();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeaturesFacet basicGetParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.FEATURE_PROJECT__PARENT)
         return null;
      return (FeaturesFacet) eInternalContainer();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(FeaturesFacet newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, ModuleModelPackage.FEATURE_PROJECT__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setParent(FeaturesFacet newParent)
   {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != ModuleModelPackage.FEATURE_PROJECT__PARENT && newParent != null))
      {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this, ModuleModelPackage.FEATURES_FACET__PROJECTS,
               FeaturesFacet.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.FEATURE_PROJECT__PARENT, newParent,
            newParent));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<PluginInclude> getIncludedPlugins()
   {
      if (includedPlugins == null)
      {
         includedPlugins = new EObjectContainmentEList.Resolving<PluginInclude>(PluginInclude.class, this,
            ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS);
      }
      return includedPlugins;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<FeatureInclude> getIncludedFeatures()
   {
      if (includedFeatures == null)
      {
         includedFeatures = new EObjectContainmentEList.Resolving<FeatureInclude>(FeatureInclude.class, this,
            ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES);
      }
      return includedFeatures;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<RuledReference> getRequiredFeatures()
   {
      if (requiredFeatures == null)
      {
         requiredFeatures = new EObjectContainmentEList.Resolving<RuledReference>(RuledReference.class, this,
            ModuleModelPackage.FEATURE_PROJECT__REQUIRED_FEATURES);
      }
      return requiredFeatures;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<RuledReference> getRequiredPlugins()
   {
      if (requiredPlugins == null)
      {
         requiredPlugins = new EObjectContainmentEList.Resolving<RuledReference>(RuledReference.class, this,
            ModuleModelPackage.FEATURE_PROJECT__REQUIRED_PLUGINS);
      }
      return requiredPlugins;
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
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((FeaturesFacet) otherEnd, msgs);
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
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            return basicSetParent(null, msgs);
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS :
            return ((InternalEList<?>) getIncludedPlugins()).basicRemove(otherEnd, msgs);
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES :
            return ((InternalEList<?>) getIncludedFeatures()).basicRemove(otherEnd, msgs);
         case ModuleModelPackage.FEATURE_PROJECT__REQUIRED_FEATURES :
            return ((InternalEList<?>) getRequiredFeatures()).basicRemove(otherEnd, msgs);
         case ModuleModelPackage.FEATURE_PROJECT__REQUIRED_PLUGINS :
            return ((InternalEList<?>) getRequiredPlugins()).basicRemove(otherEnd, msgs);
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
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            return eInternalContainer().eInverseRemove(this, ModuleModelPackage.FEATURES_FACET__PROJECTS,
               FeaturesFacet.class, msgs);
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
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            if (resolve)
               return getParent();
            return basicGetParent();
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS :
            return getIncludedPlugins();
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES :
            return getIncludedFeatures();
         case ModuleModelPackage.FEATURE_PROJECT__REQUIRED_FEATURES :
            return getRequiredFeatures();
         case ModuleModelPackage.FEATURE_PROJECT__REQUIRED_PLUGINS :
            return getRequiredPlugins();
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
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            setParent((FeaturesFacet) newValue);
            return;
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS :
            getIncludedPlugins().clear();
            getIncludedPlugins().addAll((Collection<? extends PluginInclude>) newValue);
            return;
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES :
            getIncludedFeatures().clear();
            getIncludedFeatures().addAll((Collection<? extends FeatureInclude>) newValue);
            return;
         case ModuleModelPackage.FEATURE_PROJECT__REQUIRED_FEATURES :
            getRequiredFeatures().clear();
            getRequiredFeatures().addAll((Collection<? extends RuledReference>) newValue);
            return;
         case ModuleModelPackage.FEATURE_PROJECT__REQUIRED_PLUGINS :
            getRequiredPlugins().clear();
            getRequiredPlugins().addAll((Collection<? extends RuledReference>) newValue);
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
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            setParent((FeaturesFacet) null);
            return;
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS :
            getIncludedPlugins().clear();
            return;
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES :
            getIncludedFeatures().clear();
            return;
         case ModuleModelPackage.FEATURE_PROJECT__REQUIRED_FEATURES :
            getRequiredFeatures().clear();
            return;
         case ModuleModelPackage.FEATURE_PROJECT__REQUIRED_PLUGINS :
            getRequiredPlugins().clear();
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
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            return basicGetParent() != null;
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS :
            return includedPlugins != null && !includedPlugins.isEmpty();
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES :
            return includedFeatures != null && !includedFeatures.isEmpty();
         case ModuleModelPackage.FEATURE_PROJECT__REQUIRED_FEATURES :
            return requiredFeatures != null && !requiredFeatures.isEmpty();
         case ModuleModelPackage.FEATURE_PROJECT__REQUIRED_PLUGINS :
            return requiredPlugins != null && !requiredPlugins.isEmpty();
      }
      return super.eIsSet(featureID);
   }

} // FeatureProjectImpl
