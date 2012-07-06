/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.PluginInclude;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Plugin Include</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.PluginIncludeImpl#isUnpack <em>Unpack</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.PluginIncludeImpl#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class PluginIncludeImpl extends ReferenceImpl implements PluginInclude
{
   /**
    * The default value of the '{@link #isUnpack() <em>Unpack</em>}' attribute.
    * <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #isUnpack()
    * @generated
    * @ordered
    */
   protected static final boolean UNPACK_EDEFAULT = true;

   /**
    * The cached value of the '{@link #isUnpack() <em>Unpack</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @see #isUnpack()
    * @generated
    * @ordered
    */
   protected boolean unpack = UNPACK_EDEFAULT;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected PluginIncludeImpl()
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
      return ModuleModelPackage.Literals.PLUGIN_INCLUDE;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public boolean isUnpack()
   {
      return unpack;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setUnpack(boolean newUnpack)
   {
      boolean oldUnpack = unpack;
      unpack = newUnpack;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PLUGIN_INCLUDE__UNPACK, oldUnpack,
            unpack));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeatureProject getParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.PLUGIN_INCLUDE__PARENT)
         return null;
      return (FeatureProject) eContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeatureProject basicGetParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.PLUGIN_INCLUDE__PARENT)
         return null;
      return (FeatureProject) eInternalContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(FeatureProject newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, ModuleModelPackage.PLUGIN_INCLUDE__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setParent(FeatureProject newParent)
   {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != ModuleModelPackage.PLUGIN_INCLUDE__PARENT && newParent != null))
      {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this,
               ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS, FeatureProject.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PLUGIN_INCLUDE__PARENT, newParent,
            newParent));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
   {
      switch (featureID)
      {
         case ModuleModelPackage.PLUGIN_INCLUDE__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((FeatureProject) otherEnd, msgs);
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
         case ModuleModelPackage.PLUGIN_INCLUDE__PARENT :
            return basicSetParent(null, msgs);
      }
      return super.eInverseRemove(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs)
   {
      switch (eContainerFeatureID())
      {
         case ModuleModelPackage.PLUGIN_INCLUDE__PARENT :
            return eInternalContainer().eInverseRemove(this, ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS,
               FeatureProject.class, msgs);
      }
      return super.eBasicRemoveFromContainerFeature(msgs);
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
         case ModuleModelPackage.PLUGIN_INCLUDE__UNPACK :
            return isUnpack();
         case ModuleModelPackage.PLUGIN_INCLUDE__PARENT :
            if (resolve)
               return getParent();
            return basicGetParent();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public void eSet(int featureID, Object newValue)
   {
      switch (featureID)
      {
         case ModuleModelPackage.PLUGIN_INCLUDE__UNPACK :
            setUnpack((Boolean) newValue);
            return;
         case ModuleModelPackage.PLUGIN_INCLUDE__PARENT :
            setParent((FeatureProject) newValue);
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
         case ModuleModelPackage.PLUGIN_INCLUDE__UNPACK :
            setUnpack(UNPACK_EDEFAULT);
            return;
         case ModuleModelPackage.PLUGIN_INCLUDE__PARENT :
            setParent((FeatureProject) null);
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
         case ModuleModelPackage.PLUGIN_INCLUDE__UNPACK :
            return unpack != UNPACK_EDEFAULT;
         case ModuleModelPackage.PLUGIN_INCLUDE__PARENT :
            return basicGetParent() != null;
      }
      return super.eIsSet(featureID);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public String toString()
   {
      if (eIsProxy())
         return super.toString();

      StringBuffer result = new StringBuffer(super.toString());
      result.append(" (unpack: ");
      result.append(unpack);
      result.append(')');
      return result.toString();
   }

} // PluginIncludeImpl
