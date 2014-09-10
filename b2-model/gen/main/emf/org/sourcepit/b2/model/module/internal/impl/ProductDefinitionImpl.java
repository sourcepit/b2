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

import java.io.File;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.modeling.Annotation;
import org.sourcepit.common.modeling.CommonModelingPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.ProductDefinitionImpl#getAnnotations <em>Annotations</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.ProductDefinitionImpl#isDerived <em>Derived</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.ProductDefinitionImpl#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.ProductDefinitionImpl#getFile <em>File</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.ProductDefinitionImpl#getProductPlugin <em>Product Plugin
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ProductDefinitionImpl extends EObjectImpl implements ProductDefinition
{
   /**
    * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getAnnotations()
    * @generated
    * @ordered
    */
   protected EList<Annotation> annotations;

   /**
    * The default value of the '{@link #isDerived() <em>Derived</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #isDerived()
    * @generated
    * @ordered
    */
   protected static final boolean DERIVED_EDEFAULT = false;

   /**
    * The cached value of the '{@link #isDerived() <em>Derived</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #isDerived()
    * @generated
    * @ordered
    */
   protected boolean derived = DERIVED_EDEFAULT;

   /**
    * The default value of the '{@link #getFile() <em>File</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getFile()
    * @generated
    * @ordered
    */
   protected static final File FILE_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getFile() <em>File</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getFile()
    * @generated
    * @ordered
    */
   protected File file = FILE_EDEFAULT;

   /**
    * The cached value of the '{@link #getProductPlugin() <em>Product Plugin</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getProductPlugin()
    * @generated
    * @ordered
    */
   protected StrictReference productPlugin;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   protected ProductDefinitionImpl()
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
      return ModuleModelPackage.Literals.PRODUCT_DEFINITION;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<Annotation> getAnnotations()
   {
      if (annotations == null)
      {
         annotations = new EObjectContainmentWithInverseEList<Annotation>(Annotation.class, this,
            ModuleModelPackage.PRODUCT_DEFINITION__ANNOTATIONS, CommonModelingPackage.ANNOTATION__TARGET);
      }
      return annotations;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public boolean isDerived()
   {
      return derived;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setDerived(boolean newDerived)
   {
      boolean oldDerived = derived;
      derived = newDerived;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PRODUCT_DEFINITION__DERIVED,
            oldDerived, derived));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public ProductsFacet getParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.PRODUCT_DEFINITION__PARENT)
         return null;
      return (ProductsFacet) eContainer();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public ProductsFacet basicGetParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.PRODUCT_DEFINITION__PARENT)
         return null;
      return (ProductsFacet) eInternalContainer();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(ProductsFacet newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, ModuleModelPackage.PRODUCT_DEFINITION__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setParent(ProductsFacet newParent)
   {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != ModuleModelPackage.PRODUCT_DEFINITION__PARENT && newParent != null))
      {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this,
               ModuleModelPackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS, ProductsFacet.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PRODUCT_DEFINITION__PARENT,
            newParent, newParent));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public File getFile()
   {
      return file;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setFile(File newFile)
   {
      File oldFile = file;
      file = newFile;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PRODUCT_DEFINITION__FILE, oldFile,
            file));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public StrictReference getProductPlugin()
   {
      if (productPlugin != null && productPlugin.eIsProxy())
      {
         InternalEObject oldProductPlugin = (InternalEObject) productPlugin;
         productPlugin = (StrictReference) eResolveProxy(oldProductPlugin);
         if (productPlugin != oldProductPlugin)
         {
            InternalEObject newProductPlugin = (InternalEObject) productPlugin;
            NotificationChain msgs = oldProductPlugin.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
               - ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN, null, null);
            if (newProductPlugin.eInternalContainer() == null)
            {
               msgs = newProductPlugin.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
                  - ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN, null, msgs);
            }
            if (msgs != null)
               msgs.dispatch();
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                  ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN, oldProductPlugin, productPlugin));
         }
      }
      return productPlugin;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public StrictReference basicGetProductPlugin()
   {
      return productPlugin;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetProductPlugin(StrictReference newProductPlugin, NotificationChain msgs)
   {
      StrictReference oldProductPlugin = productPlugin;
      productPlugin = newProductPlugin;
      if (eNotificationRequired())
      {
         ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
            ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN, oldProductPlugin, newProductPlugin);
         if (msgs == null)
            msgs = notification;
         else
            msgs.add(notification);
      }
      return msgs;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setProductPlugin(StrictReference newProductPlugin)
   {
      if (newProductPlugin != productPlugin)
      {
         NotificationChain msgs = null;
         if (productPlugin != null)
            msgs = ((InternalEObject) productPlugin).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
               - ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN, null, msgs);
         if (newProductPlugin != null)
            msgs = ((InternalEObject) newProductPlugin).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
               - ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN, null, msgs);
         msgs = basicSetProductPlugin(newProductPlugin, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN,
            newProductPlugin, newProductPlugin));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public Annotation getAnnotation(String source)
   {
      // TODO: implement this method
      // Ensure that you remove @generated or mark it @generated NOT
      throw new UnsupportedOperationException();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public Annotation getAnnotation(String source, boolean createOnDemand)
   {
      // TODO: implement this method
      // Ensure that you remove @generated or mark it @generated NOT
      throw new UnsupportedOperationException();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getAnnotationData(String source, String key)
   {
      // TODO: implement this method
      // Ensure that you remove @generated or mark it @generated NOT
      throw new UnsupportedOperationException();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public String setAnnotationData(String source, String key, String value)
   {
      // TODO: implement this method
      // Ensure that you remove @generated or mark it @generated NOT
      throw new UnsupportedOperationException();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @SuppressWarnings("unchecked")
   @Override
   public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
   {
      switch (featureID)
      {
         case ModuleModelPackage.PRODUCT_DEFINITION__ANNOTATIONS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getAnnotations()).basicAdd(otherEnd, msgs);
         case ModuleModelPackage.PRODUCT_DEFINITION__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((ProductsFacet) otherEnd, msgs);
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
         case ModuleModelPackage.PRODUCT_DEFINITION__ANNOTATIONS :
            return ((InternalEList<?>) getAnnotations()).basicRemove(otherEnd, msgs);
         case ModuleModelPackage.PRODUCT_DEFINITION__PARENT :
            return basicSetParent(null, msgs);
         case ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN :
            return basicSetProductPlugin(null, msgs);
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
         case ModuleModelPackage.PRODUCT_DEFINITION__PARENT :
            return eInternalContainer().eInverseRemove(this, ModuleModelPackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS,
               ProductsFacet.class, msgs);
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
         case ModuleModelPackage.PRODUCT_DEFINITION__ANNOTATIONS :
            return getAnnotations();
         case ModuleModelPackage.PRODUCT_DEFINITION__DERIVED :
            return isDerived();
         case ModuleModelPackage.PRODUCT_DEFINITION__PARENT :
            if (resolve)
               return getParent();
            return basicGetParent();
         case ModuleModelPackage.PRODUCT_DEFINITION__FILE :
            return getFile();
         case ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN :
            if (resolve)
               return getProductPlugin();
            return basicGetProductPlugin();
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
         case ModuleModelPackage.PRODUCT_DEFINITION__ANNOTATIONS :
            getAnnotations().clear();
            getAnnotations().addAll((Collection<? extends Annotation>) newValue);
            return;
         case ModuleModelPackage.PRODUCT_DEFINITION__DERIVED :
            setDerived((Boolean) newValue);
            return;
         case ModuleModelPackage.PRODUCT_DEFINITION__PARENT :
            setParent((ProductsFacet) newValue);
            return;
         case ModuleModelPackage.PRODUCT_DEFINITION__FILE :
            setFile((File) newValue);
            return;
         case ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN :
            setProductPlugin((StrictReference) newValue);
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
         case ModuleModelPackage.PRODUCT_DEFINITION__ANNOTATIONS :
            getAnnotations().clear();
            return;
         case ModuleModelPackage.PRODUCT_DEFINITION__DERIVED :
            setDerived(DERIVED_EDEFAULT);
            return;
         case ModuleModelPackage.PRODUCT_DEFINITION__PARENT :
            setParent((ProductsFacet) null);
            return;
         case ModuleModelPackage.PRODUCT_DEFINITION__FILE :
            setFile(FILE_EDEFAULT);
            return;
         case ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN :
            setProductPlugin((StrictReference) null);
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
         case ModuleModelPackage.PRODUCT_DEFINITION__ANNOTATIONS :
            return annotations != null && !annotations.isEmpty();
         case ModuleModelPackage.PRODUCT_DEFINITION__DERIVED :
            return derived != DERIVED_EDEFAULT;
         case ModuleModelPackage.PRODUCT_DEFINITION__PARENT :
            return basicGetParent() != null;
         case ModuleModelPackage.PRODUCT_DEFINITION__FILE :
            return FILE_EDEFAULT == null ? file != null : !FILE_EDEFAULT.equals(file);
         case ModuleModelPackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN :
            return productPlugin != null;
      }
      return super.eIsSet(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
   {
      if (baseClass == Derivable.class)
      {
         switch (derivedFeatureID)
         {
            case ModuleModelPackage.PRODUCT_DEFINITION__DERIVED :
               return ModuleModelPackage.DERIVABLE__DERIVED;
            default :
               return -1;
         }
      }
      return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
   {
      if (baseClass == Derivable.class)
      {
         switch (baseFeatureID)
         {
            case ModuleModelPackage.DERIVABLE__DERIVED :
               return ModuleModelPackage.PRODUCT_DEFINITION__DERIVED;
            default :
               return -1;
         }
      }
      return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public String toString()
   {
      if (eIsProxy())
         return super.toString();

      StringBuffer result = new StringBuffer(super.toString());
      result.append(" (derived: ");
      result.append(derived);
      result.append(", file: ");
      result.append(file);
      result.append(')');
      return result.toString();
   }

} // ProductDefinitionImpl
