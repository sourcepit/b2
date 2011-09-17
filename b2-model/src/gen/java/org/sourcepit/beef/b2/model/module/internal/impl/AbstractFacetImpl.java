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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.beef.b2.model.common.Annotateable;
import org.sourcepit.beef.b2.model.common.Annotation;
import org.sourcepit.beef.b2.model.common.CommonPackage;
import org.sourcepit.beef.b2.model.module.AbstractFacet;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.ModulePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Module Facet</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.AbstractFacetImpl#isDerived <em>Derived</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.AbstractFacetImpl#getAnnotations <em>Annotations</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.AbstractFacetImpl#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.AbstractFacetImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class AbstractFacetImpl extends EObjectImpl implements AbstractFacet
{
   /**
    * The default value of the '{@link #isDerived() <em>Derived</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #isDerived()
    * @generated
    * @ordered
    */
   protected static final boolean DERIVED_EDEFAULT = false;

   /**
    * The cached value of the '{@link #isDerived() <em>Derived</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #isDerived()
    * @generated
    * @ordered
    */
   protected boolean derived = DERIVED_EDEFAULT;

   /**
    * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getAnnotations()
    * @generated
    * @ordered
    */
   protected EList<Annotation> annotations;

   /**
    * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @see #getName()
    * @generated
    * @ordered
    */
   protected static final String NAME_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @see #getName()
    * @generated
    * @ordered
    */
   protected String name = NAME_EDEFAULT;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected AbstractFacetImpl()
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
      return ModulePackage.Literals.ABSTRACT_FACET;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getName()
   {
      return name;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setName(String newName)
   {
      String oldName = name;
      name = newName;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModulePackage.ABSTRACT_FACET__NAME, oldName, name));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public boolean isDerived()
   {
      return derived;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setDerived(boolean newDerived)
   {
      boolean oldDerived = derived;
      derived = newDerived;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModulePackage.ABSTRACT_FACET__DERIVED, oldDerived,
            derived));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<Annotation> getAnnotations()
   {
      if (annotations == null)
      {
         annotations = new EObjectContainmentWithInverseEList<Annotation>(Annotation.class, this,
            ModulePackage.ABSTRACT_FACET__ANNOTATIONS, CommonPackage.ANNOTATION__PARENT);
      }
      return annotations;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public AbstractModule getParent()
   {
      if (eContainerFeatureID() != ModulePackage.ABSTRACT_FACET__PARENT)
         return null;
      return (AbstractModule) eContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(AbstractModule newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, ModulePackage.ABSTRACT_FACET__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setParent(AbstractModule newParent)
   {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != ModulePackage.ABSTRACT_FACET__PARENT && newParent != null))
      {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this, ModulePackage.ABSTRACT_MODULE__FACETS,
               AbstractModule.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModulePackage.ABSTRACT_FACET__PARENT, newParent,
            newParent));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
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
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getAnnotationEntry(String source, String key)
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
   public String putAnnotationEntry(String source, String key, String value)
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
   @SuppressWarnings("unchecked")
   @Override
   public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
   {
      switch (featureID)
      {
         case ModulePackage.ABSTRACT_FACET__ANNOTATIONS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getAnnotations()).basicAdd(otherEnd, msgs);
         case ModulePackage.ABSTRACT_FACET__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((AbstractModule) otherEnd, msgs);
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
         case ModulePackage.ABSTRACT_FACET__ANNOTATIONS :
            return ((InternalEList<?>) getAnnotations()).basicRemove(otherEnd, msgs);
         case ModulePackage.ABSTRACT_FACET__PARENT :
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
         case ModulePackage.ABSTRACT_FACET__PARENT :
            return eInternalContainer().eInverseRemove(this, ModulePackage.ABSTRACT_MODULE__FACETS,
               AbstractModule.class, msgs);
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
         case ModulePackage.ABSTRACT_FACET__DERIVED :
            return isDerived();
         case ModulePackage.ABSTRACT_FACET__ANNOTATIONS :
            return getAnnotations();
         case ModulePackage.ABSTRACT_FACET__PARENT :
            return getParent();
         case ModulePackage.ABSTRACT_FACET__NAME :
            return getName();
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
         case ModulePackage.ABSTRACT_FACET__DERIVED :
            setDerived((Boolean) newValue);
            return;
         case ModulePackage.ABSTRACT_FACET__ANNOTATIONS :
            getAnnotations().clear();
            getAnnotations().addAll((Collection<? extends Annotation>) newValue);
            return;
         case ModulePackage.ABSTRACT_FACET__PARENT :
            setParent((AbstractModule) newValue);
            return;
         case ModulePackage.ABSTRACT_FACET__NAME :
            setName((String) newValue);
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
         case ModulePackage.ABSTRACT_FACET__DERIVED :
            setDerived(DERIVED_EDEFAULT);
            return;
         case ModulePackage.ABSTRACT_FACET__ANNOTATIONS :
            getAnnotations().clear();
            return;
         case ModulePackage.ABSTRACT_FACET__PARENT :
            setParent((AbstractModule) null);
            return;
         case ModulePackage.ABSTRACT_FACET__NAME :
            setName(NAME_EDEFAULT);
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
         case ModulePackage.ABSTRACT_FACET__DERIVED :
            return derived != DERIVED_EDEFAULT;
         case ModulePackage.ABSTRACT_FACET__ANNOTATIONS :
            return annotations != null && !annotations.isEmpty();
         case ModulePackage.ABSTRACT_FACET__PARENT :
            return getParent() != null;
         case ModulePackage.ABSTRACT_FACET__NAME :
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
      if (baseClass == Annotateable.class)
      {
         switch (derivedFeatureID)
         {
            case ModulePackage.ABSTRACT_FACET__ANNOTATIONS :
               return CommonPackage.ANNOTATEABLE__ANNOTATIONS;
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
      if (baseClass == Annotateable.class)
      {
         switch (baseFeatureID)
         {
            case CommonPackage.ANNOTATEABLE__ANNOTATIONS :
               return ModulePackage.ABSTRACT_FACET__ANNOTATIONS;
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
      result.append(" (derived: ");
      result.append(derived);
      result.append(", name: ");
      result.append(name);
      result.append(')');
      return result.toString();
   }

} // ModuleFacetImpl
