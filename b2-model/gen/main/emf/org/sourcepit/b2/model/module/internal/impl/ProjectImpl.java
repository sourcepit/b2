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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.b2.model.module.AbstractIdentifiable;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.b2.model.module.util.Identifiable;
import org.sourcepit.b2.model.module.util.Identifier;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.modeling.Annotation;
import org.sourcepit.common.modeling.CommonModelingPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.ProjectImpl#isDerived <em>Derived</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.ProjectImpl#getAnnotations <em>Annotations</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.ProjectImpl#getId <em>Id</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.ProjectImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class ProjectImpl extends FileContainerImpl implements Project
{
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
    * The default value of the '{@link #getId() <em>Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getId()
    * @generated
    * @ordered
    */
   protected static final String ID_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getId()
    * @generated
    * @ordered
    */
   protected String id = ID_EDEFAULT;

   /**
    * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getVersion()
    * @generated
    * @ordered
    */
   protected static final String VERSION_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getVersion()
    * @generated
    * @ordered
    */
   protected String version = VERSION_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   protected ProjectImpl()
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
      return ModuleModelPackage.Literals.PROJECT;
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
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PROJECT__DERIVED, oldDerived, derived));
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
            ModuleModelPackage.PROJECT__ANNOTATIONS, CommonModelingPackage.ANNOTATION__TARGET);
      }
      return annotations;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getId()
   {
      return id;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setId(String newId)
   {
      String oldId = id;
      id = newId;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PROJECT__ID, oldId, id));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getVersion()
   {
      return version;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setVersion(String newVersion)
   {
      String oldVersion = version;
      version = newVersion;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.PROJECT__VERSION, oldVersion, version));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public ProjectFacet<? extends Project> getParent()
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
   public boolean isIdentifyableBy(Identifier identifier)
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
   public Identifier toIdentifier()
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
         case ModuleModelPackage.PROJECT__ANNOTATIONS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getAnnotations()).basicAdd(otherEnd, msgs);
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
         case ModuleModelPackage.PROJECT__ANNOTATIONS :
            return ((InternalEList<?>) getAnnotations()).basicRemove(otherEnd, msgs);
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
   public Object eGet(int featureID, boolean resolve, boolean coreType)
   {
      switch (featureID)
      {
         case ModuleModelPackage.PROJECT__DERIVED :
            return isDerived();
         case ModuleModelPackage.PROJECT__ANNOTATIONS :
            return getAnnotations();
         case ModuleModelPackage.PROJECT__ID :
            return getId();
         case ModuleModelPackage.PROJECT__VERSION :
            return getVersion();
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
         case ModuleModelPackage.PROJECT__DERIVED :
            setDerived((Boolean) newValue);
            return;
         case ModuleModelPackage.PROJECT__ANNOTATIONS :
            getAnnotations().clear();
            getAnnotations().addAll((Collection<? extends Annotation>) newValue);
            return;
         case ModuleModelPackage.PROJECT__ID :
            setId((String) newValue);
            return;
         case ModuleModelPackage.PROJECT__VERSION :
            setVersion((String) newValue);
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
         case ModuleModelPackage.PROJECT__DERIVED :
            setDerived(DERIVED_EDEFAULT);
            return;
         case ModuleModelPackage.PROJECT__ANNOTATIONS :
            getAnnotations().clear();
            return;
         case ModuleModelPackage.PROJECT__ID :
            setId(ID_EDEFAULT);
            return;
         case ModuleModelPackage.PROJECT__VERSION :
            setVersion(VERSION_EDEFAULT);
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
         case ModuleModelPackage.PROJECT__DERIVED :
            return derived != DERIVED_EDEFAULT;
         case ModuleModelPackage.PROJECT__ANNOTATIONS :
            return annotations != null && !annotations.isEmpty();
         case ModuleModelPackage.PROJECT__ID :
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
         case ModuleModelPackage.PROJECT__VERSION :
            return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
            case ModuleModelPackage.PROJECT__DERIVED :
               return ModuleModelPackage.DERIVABLE__DERIVED;
            default :
               return -1;
         }
      }
      if (baseClass == Annotatable.class)
      {
         switch (derivedFeatureID)
         {
            case ModuleModelPackage.PROJECT__ANNOTATIONS :
               return CommonModelingPackage.ANNOTATABLE__ANNOTATIONS;
            default :
               return -1;
         }
      }
      if (baseClass == Identifiable.class)
      {
         switch (derivedFeatureID)
         {
            default :
               return -1;
         }
      }
      if (baseClass == AbstractIdentifiable.class)
      {
         switch (derivedFeatureID)
         {
            case ModuleModelPackage.PROJECT__ID :
               return ModuleModelPackage.ABSTRACT_IDENTIFIABLE__ID;
            case ModuleModelPackage.PROJECT__VERSION :
               return ModuleModelPackage.ABSTRACT_IDENTIFIABLE__VERSION;
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
               return ModuleModelPackage.PROJECT__DERIVED;
            default :
               return -1;
         }
      }
      if (baseClass == Annotatable.class)
      {
         switch (baseFeatureID)
         {
            case CommonModelingPackage.ANNOTATABLE__ANNOTATIONS :
               return ModuleModelPackage.PROJECT__ANNOTATIONS;
            default :
               return -1;
         }
      }
      if (baseClass == Identifiable.class)
      {
         switch (baseFeatureID)
         {
            default :
               return -1;
         }
      }
      if (baseClass == AbstractIdentifiable.class)
      {
         switch (baseFeatureID)
         {
            case ModuleModelPackage.ABSTRACT_IDENTIFIABLE__ID :
               return ModuleModelPackage.PROJECT__ID;
            case ModuleModelPackage.ABSTRACT_IDENTIFIABLE__VERSION :
               return ModuleModelPackage.PROJECT__VERSION;
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
      result.append(", id: ");
      result.append(id);
      result.append(", version: ");
      result.append(version);
      result.append(')');
      return result.toString();
   }

} // ProjectImpl
