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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.ModuleModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composite Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.CompositeModuleImpl#getModules <em>Modules</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CompositeModuleImpl extends AbstractModuleImpl implements CompositeModule {
   /**
    * The cached value of the '{@link #getModules() <em>Modules</em>}' reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getModules()
    * @generated
    * @ordered
    */
   protected EList<AbstractModule> modules;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   protected CompositeModuleImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return ModuleModelPackage.Literals.COMPOSITE_MODULE;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<AbstractModule> getModules() {
      if (modules == null) {
         modules = new EObjectResolvingEList<AbstractModule>(AbstractModule.class, this,
            ModuleModelPackage.COMPOSITE_MODULE__MODULES);
      }
      return modules;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case ModuleModelPackage.COMPOSITE_MODULE__MODULES :
            return getModules();
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
   public void eSet(int featureID, Object newValue) {
      switch (featureID) {
         case ModuleModelPackage.COMPOSITE_MODULE__MODULES :
            getModules().clear();
            getModules().addAll((Collection<? extends AbstractModule>) newValue);
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
   public void eUnset(int featureID) {
      switch (featureID) {
         case ModuleModelPackage.COMPOSITE_MODULE__MODULES :
            getModules().clear();
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
   public boolean eIsSet(int featureID) {
      switch (featureID) {
         case ModuleModelPackage.COMPOSITE_MODULE__MODULES :
            return modules != null && !modules.isEmpty();
      }
      return super.eIsSet(featureID);
   }

} // CompositeModuleImpl
