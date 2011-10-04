/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.beef.b2.model.module.Classified;
import org.sourcepit.beef.b2.model.module.FeatureInclude;
import org.sourcepit.beef.b2.model.module.FeatureProject;
import org.sourcepit.beef.b2.model.module.FeaturesFacet;
import org.sourcepit.beef.b2.model.module.ModuleModelPackage;
import org.sourcepit.beef.b2.model.module.PluginInclude;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Feature Project</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.FeatureProjectImpl#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class FeatureProjectImpl extends ProjectImpl implements FeatureProject
{
   /**
    * The default value of the '{@link #getClassifier() <em>Classifier</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getClassifier()
    * @generated
    * @ordered
    */
   protected static final String CLASSIFIER_EDEFAULT = null;
   /**
    * The cached value of the '{@link #getClassifier() <em>Classifier</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getClassifier()
    * @generated
    * @ordered
    */
   protected String classifier = CLASSIFIER_EDEFAULT;
   /**
    * The cached value of the '{@link #getIncludedPlugins() <em>Included Plugins</em>}' containment reference list. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getIncludedPlugins()
    * @generated
    * @ordered
    */
   protected EList<PluginInclude> includedPlugins;
   /**
    * The cached value of the '{@link #getIncludedFeatures() <em>Included Features</em>}' containment reference list.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getIncludedFeatures()
    * @generated
    * @ordered
    */
   protected EList<FeatureInclude> includedFeatures;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected FeatureProjectImpl()
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
      return ModuleModelPackage.Literals.FEATURE_PROJECT;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
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
    * <!-- begin-user-doc --> <!-- end-user-doc -->
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
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(FeaturesFacet newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, ModuleModelPackage.FEATURE_PROJECT__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
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
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<PluginInclude> getIncludedPlugins()
   {
      if (includedPlugins == null)
      {
         includedPlugins = new EObjectContainmentWithInverseEList.Resolving<PluginInclude>(PluginInclude.class, this,
            ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS, ModuleModelPackage.PLUGIN_INCLUDE__PARENT);
      }
      return includedPlugins;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<FeatureInclude> getIncludedFeatures()
   {
      if (includedFeatures == null)
      {
         includedFeatures = new EObjectContainmentWithInverseEList.Resolving<FeatureInclude>(FeatureInclude.class,
            this, ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES, ModuleModelPackage.FEATURE_INCLUDE__PARENT);
      }
      return includedFeatures;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getClassifier()
   {
      return classifier;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setClassifier(String newClassifier)
   {
      String oldClassifier = classifier;
      classifier = newClassifier;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.FEATURE_PROJECT__CLASSIFIER,
            oldClassifier, classifier));
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
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((FeaturesFacet) otherEnd, msgs);
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncludedPlugins()).basicAdd(otherEnd, msgs);
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncludedFeatures()).basicAdd(otherEnd, msgs);
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
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            return basicSetParent(null, msgs);
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS :
            return ((InternalEList<?>) getIncludedPlugins()).basicRemove(otherEnd, msgs);
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES :
            return ((InternalEList<?>) getIncludedFeatures()).basicRemove(otherEnd, msgs);
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
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            return eInternalContainer().eInverseRemove(this, ModuleModelPackage.FEATURES_FACET__PROJECTS,
               FeaturesFacet.class, msgs);
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
         case ModuleModelPackage.FEATURE_PROJECT__CLASSIFIER :
            return getClassifier();
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            if (resolve)
               return getParent();
            return basicGetParent();
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS :
            return getIncludedPlugins();
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES :
            return getIncludedFeatures();
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
         case ModuleModelPackage.FEATURE_PROJECT__CLASSIFIER :
            setClassifier((String) newValue);
            return;
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
         case ModuleModelPackage.FEATURE_PROJECT__CLASSIFIER :
            setClassifier(CLASSIFIER_EDEFAULT);
            return;
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            setParent((FeaturesFacet) null);
            return;
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS :
            getIncludedPlugins().clear();
            return;
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES :
            getIncludedFeatures().clear();
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
         case ModuleModelPackage.FEATURE_PROJECT__CLASSIFIER :
            return CLASSIFIER_EDEFAULT == null ? classifier != null : !CLASSIFIER_EDEFAULT.equals(classifier);
         case ModuleModelPackage.FEATURE_PROJECT__PARENT :
            return basicGetParent() != null;
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_PLUGINS :
            return includedPlugins != null && !includedPlugins.isEmpty();
         case ModuleModelPackage.FEATURE_PROJECT__INCLUDED_FEATURES :
            return includedFeatures != null && !includedFeatures.isEmpty();
      }
      return super.eIsSet(featureID);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
   {
      if (baseClass == Classified.class)
      {
         switch (derivedFeatureID)
         {
            case ModuleModelPackage.FEATURE_PROJECT__CLASSIFIER :
               return ModuleModelPackage.CLASSIFIED__CLASSIFIER;
            default :
               return -1;
         }
      }
      return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
   {
      if (baseClass == Classified.class)
      {
         switch (baseFeatureID)
         {
            case ModuleModelPackage.CLASSIFIED__CLASSIFIER :
               return ModuleModelPackage.FEATURE_PROJECT__CLASSIFIER;
            default :
               return -1;
         }
      }
      return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
      result.append(" (classifier: ");
      result.append(classifier);
      result.append(')');
      return result.toString();
   }

} // FeatureProjectImpl
