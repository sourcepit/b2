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
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Plugin Project</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.PluginProjectImpl#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.PluginProjectImpl#getBundleVersion <em>Bundle Version</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.PluginProjectImpl#isTestPlugin <em>Test Plugin</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.PluginProjectImpl#getFragmentHostSymbolicName <em>Fragment
 * Host Symbolic Name</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.PluginProjectImpl#getFragmentHostVersion <em>Fragment Host
 * Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class PluginProjectImpl extends ProjectImpl implements PluginProject
{
   /**
    * The default value of the '{@link #getBundleVersion() <em>Bundle Version</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getBundleVersion()
    * @generated
    * @ordered
    */
   protected static final String BUNDLE_VERSION_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getBundleVersion() <em>Bundle Version</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getBundleVersion()
    * @generated
    * @ordered
    */
   protected String bundleVersion = BUNDLE_VERSION_EDEFAULT;

   /**
    * The default value of the '{@link #isTestPlugin() <em>Test Plugin</em>}' attribute.
    * <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #isTestPlugin()
    * @generated
    * @ordered
    */
   protected static final boolean TEST_PLUGIN_EDEFAULT = false;

   /**
    * The cached value of the '{@link #isTestPlugin() <em>Test Plugin</em>}' attribute.
    * <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #isTestPlugin()
    * @generated
    * @ordered
    */
   protected boolean testPlugin = TEST_PLUGIN_EDEFAULT;

   /**
    * The default value of the '{@link #getFragmentHostSymbolicName() <em>Fragment Host Symbolic Name</em>}' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getFragmentHostSymbolicName()
    * @generated
    * @ordered
    */
   protected static final String FRAGMENT_HOST_SYMBOLIC_NAME_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getFragmentHostSymbolicName() <em>Fragment Host Symbolic Name</em>}' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getFragmentHostSymbolicName()
    * @generated
    * @ordered
    */
   protected String fragmentHostSymbolicName = FRAGMENT_HOST_SYMBOLIC_NAME_EDEFAULT;

   /**
    * The default value of the '{@link #getFragmentHostVersion() <em>Fragment Host Version</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getFragmentHostVersion()
    * @generated
    * @ordered
    */
   protected static final String FRAGMENT_HOST_VERSION_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getFragmentHostVersion() <em>Fragment Host Version</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getFragmentHostVersion()
    * @generated
    * @ordered
    */
   protected String fragmentHostVersion = FRAGMENT_HOST_VERSION_EDEFAULT;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected PluginProjectImpl()
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
      return ModuleModelPackage.Literals.PLUGIN_PROJECT;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginsFacet getParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.PLUGIN_PROJECT__PARENT)
         return null;
      return (PluginsFacet) eContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginsFacet basicGetParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.PLUGIN_PROJECT__PARENT)
         return null;
      return (PluginsFacet) eInternalContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(PluginsFacet newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, ModuleModelPackage.PLUGIN_PROJECT__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setParent(PluginsFacet newParent)
   {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != ModuleModelPackage.PLUGIN_PROJECT__PARENT && newParent != null))
      {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this, ModuleModelPackage.PLUGINS_FACET__PROJECTS,
               PluginsFacet.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PLUGIN_PROJECT__PARENT, newParent,
            newParent));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getBundleVersion()
   {
      return bundleVersion;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setBundleVersion(String newBundleVersion)
   {
      String oldBundleVersion = bundleVersion;
      bundleVersion = newBundleVersion;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PLUGIN_PROJECT__BUNDLE_VERSION,
            oldBundleVersion, bundleVersion));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public boolean isTestPlugin()
   {
      return testPlugin;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setTestPlugin(boolean newTestPlugin)
   {
      boolean oldTestPlugin = testPlugin;
      testPlugin = newTestPlugin;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PLUGIN_PROJECT__TEST_PLUGIN,
            oldTestPlugin, testPlugin));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getFragmentHostSymbolicName()
   {
      return fragmentHostSymbolicName;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setFragmentHostSymbolicName(String newFragmentHostSymbolicName)
   {
      String oldFragmentHostSymbolicName = fragmentHostSymbolicName;
      fragmentHostSymbolicName = newFragmentHostSymbolicName;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET,
            ModuleModelPackage.PLUGIN_PROJECT__FRAGMENT_HOST_SYMBOLIC_NAME, oldFragmentHostSymbolicName,
            fragmentHostSymbolicName));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getFragmentHostVersion()
   {
      return fragmentHostVersion;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setFragmentHostVersion(String newFragmentHostVersion)
   {
      String oldFragmentHostVersion = fragmentHostVersion;
      fragmentHostVersion = newFragmentHostVersion;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET,
            ModuleModelPackage.PLUGIN_PROJECT__FRAGMENT_HOST_VERSION, oldFragmentHostVersion, fragmentHostVersion));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public boolean isFragment()
   {
      // TODO: implement this method
      // Ensure that you remove @generated or mark it @generated NOT
      throw new UnsupportedOperationException();
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
         case ModuleModelPackage.PLUGIN_PROJECT__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((PluginsFacet) otherEnd, msgs);
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
         case ModuleModelPackage.PLUGIN_PROJECT__PARENT :
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
         case ModuleModelPackage.PLUGIN_PROJECT__PARENT :
            return eInternalContainer().eInverseRemove(this, ModuleModelPackage.PLUGINS_FACET__PROJECTS,
               PluginsFacet.class, msgs);
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
         case ModuleModelPackage.PLUGIN_PROJECT__PARENT :
            if (resolve)
               return getParent();
            return basicGetParent();
         case ModuleModelPackage.PLUGIN_PROJECT__BUNDLE_VERSION :
            return getBundleVersion();
         case ModuleModelPackage.PLUGIN_PROJECT__TEST_PLUGIN :
            return isTestPlugin();
         case ModuleModelPackage.PLUGIN_PROJECT__FRAGMENT_HOST_SYMBOLIC_NAME :
            return getFragmentHostSymbolicName();
         case ModuleModelPackage.PLUGIN_PROJECT__FRAGMENT_HOST_VERSION :
            return getFragmentHostVersion();
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
         case ModuleModelPackage.PLUGIN_PROJECT__PARENT :
            setParent((PluginsFacet) newValue);
            return;
         case ModuleModelPackage.PLUGIN_PROJECT__BUNDLE_VERSION :
            setBundleVersion((String) newValue);
            return;
         case ModuleModelPackage.PLUGIN_PROJECT__TEST_PLUGIN :
            setTestPlugin((Boolean) newValue);
            return;
         case ModuleModelPackage.PLUGIN_PROJECT__FRAGMENT_HOST_SYMBOLIC_NAME :
            setFragmentHostSymbolicName((String) newValue);
            return;
         case ModuleModelPackage.PLUGIN_PROJECT__FRAGMENT_HOST_VERSION :
            setFragmentHostVersion((String) newValue);
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
         case ModuleModelPackage.PLUGIN_PROJECT__PARENT :
            setParent((PluginsFacet) null);
            return;
         case ModuleModelPackage.PLUGIN_PROJECT__BUNDLE_VERSION :
            setBundleVersion(BUNDLE_VERSION_EDEFAULT);
            return;
         case ModuleModelPackage.PLUGIN_PROJECT__TEST_PLUGIN :
            setTestPlugin(TEST_PLUGIN_EDEFAULT);
            return;
         case ModuleModelPackage.PLUGIN_PROJECT__FRAGMENT_HOST_SYMBOLIC_NAME :
            setFragmentHostSymbolicName(FRAGMENT_HOST_SYMBOLIC_NAME_EDEFAULT);
            return;
         case ModuleModelPackage.PLUGIN_PROJECT__FRAGMENT_HOST_VERSION :
            setFragmentHostVersion(FRAGMENT_HOST_VERSION_EDEFAULT);
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
         case ModuleModelPackage.PLUGIN_PROJECT__PARENT :
            return basicGetParent() != null;
         case ModuleModelPackage.PLUGIN_PROJECT__BUNDLE_VERSION :
            return BUNDLE_VERSION_EDEFAULT == null ? bundleVersion != null : !BUNDLE_VERSION_EDEFAULT
               .equals(bundleVersion);
         case ModuleModelPackage.PLUGIN_PROJECT__TEST_PLUGIN :
            return testPlugin != TEST_PLUGIN_EDEFAULT;
         case ModuleModelPackage.PLUGIN_PROJECT__FRAGMENT_HOST_SYMBOLIC_NAME :
            return FRAGMENT_HOST_SYMBOLIC_NAME_EDEFAULT == null
               ? fragmentHostSymbolicName != null
               : !FRAGMENT_HOST_SYMBOLIC_NAME_EDEFAULT.equals(fragmentHostSymbolicName);
         case ModuleModelPackage.PLUGIN_PROJECT__FRAGMENT_HOST_VERSION :
            return FRAGMENT_HOST_VERSION_EDEFAULT == null
               ? fragmentHostVersion != null
               : !FRAGMENT_HOST_VERSION_EDEFAULT.equals(fragmentHostVersion);
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
      result.append(" (bundleVersion: ");
      result.append(bundleVersion);
      result.append(", testPlugin: ");
      result.append(testPlugin);
      result.append(", fragmentHostSymbolicName: ");
      result.append(fragmentHostSymbolicName);
      result.append(", fragmentHostVersion: ");
      result.append(fragmentHostVersion);
      result.append(')');
      return result.toString();
   }

} // PluginProjectImpl
