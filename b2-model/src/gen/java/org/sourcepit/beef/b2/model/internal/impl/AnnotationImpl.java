/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.internal.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.beef.b2.internal.model.Annotateable;
import org.sourcepit.beef.b2.internal.model.Annotation;
import org.sourcepit.beef.b2.internal.model.B2ModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Annotation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.internal.impl.AnnotationImpl#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.internal.impl.AnnotationImpl#getSource <em>Source</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.internal.impl.AnnotationImpl#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class AnnotationImpl extends EObjectImpl implements Annotation
{
   /**
    * The default value of the '{@link #getSource() <em>Source</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getSource()
    * @generated
    * @ordered
    */
   protected static final String SOURCE_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getSource() <em>Source</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getSource()
    * @generated
    * @ordered
    */
   protected String source = SOURCE_EDEFAULT;

   /**
    * The cached value of the '{@link #getEntries() <em>Entries</em>}' map. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @see #getEntries()
    * @generated
    * @ordered
    */
   protected EMap<String, String> entries;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected AnnotationImpl()
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
      return B2ModelPackage.Literals.ANNOTATION;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Annotateable getParent()
   {
      if (eContainerFeatureID() != B2ModelPackage.ANNOTATION__PARENT)
         return null;
      return (Annotateable) eContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(Annotateable newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, B2ModelPackage.ANNOTATION__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setParent(Annotateable newParent)
   {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != B2ModelPackage.ANNOTATION__PARENT && newParent != null))
      {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this, B2ModelPackage.ANNOTATEABLE__ANNOTATIONS,
               Annotateable.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, B2ModelPackage.ANNOTATION__PARENT, newParent, newParent));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getSource()
   {
      return source;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setSource(String newSource)
   {
      String oldSource = source;
      source = newSource;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, B2ModelPackage.ANNOTATION__SOURCE, oldSource, source));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EMap<String, String> getEntries()
   {
      if (entries == null)
      {
         entries = new EcoreEMap<String, String>(B2ModelPackage.Literals.ESTRING_MAP_ENTRY, EStringMapEntryImpl.class,
            this, B2ModelPackage.ANNOTATION__ENTRIES);
      }
      return entries;
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
         case B2ModelPackage.ANNOTATION__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((Annotateable) otherEnd, msgs);
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
         case B2ModelPackage.ANNOTATION__PARENT :
            return basicSetParent(null, msgs);
         case B2ModelPackage.ANNOTATION__ENTRIES :
            return ((InternalEList<?>) getEntries()).basicRemove(otherEnd, msgs);
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
         case B2ModelPackage.ANNOTATION__PARENT :
            return eInternalContainer().eInverseRemove(this, B2ModelPackage.ANNOTATEABLE__ANNOTATIONS,
               Annotateable.class, msgs);
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
         case B2ModelPackage.ANNOTATION__PARENT :
            return getParent();
         case B2ModelPackage.ANNOTATION__SOURCE :
            return getSource();
         case B2ModelPackage.ANNOTATION__ENTRIES :
            if (coreType)
               return getEntries();
            else
               return getEntries().map();
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
         case B2ModelPackage.ANNOTATION__PARENT :
            setParent((Annotateable) newValue);
            return;
         case B2ModelPackage.ANNOTATION__SOURCE :
            setSource((String) newValue);
            return;
         case B2ModelPackage.ANNOTATION__ENTRIES :
            ((EStructuralFeature.Setting) getEntries()).set(newValue);
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
         case B2ModelPackage.ANNOTATION__PARENT :
            setParent((Annotateable) null);
            return;
         case B2ModelPackage.ANNOTATION__SOURCE :
            setSource(SOURCE_EDEFAULT);
            return;
         case B2ModelPackage.ANNOTATION__ENTRIES :
            getEntries().clear();
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
         case B2ModelPackage.ANNOTATION__PARENT :
            return getParent() != null;
         case B2ModelPackage.ANNOTATION__SOURCE :
            return SOURCE_EDEFAULT == null ? source != null : !SOURCE_EDEFAULT.equals(source);
         case B2ModelPackage.ANNOTATION__ENTRIES :
            return entries != null && !entries.isEmpty();
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
      result.append(" (source: ");
      result.append(source);
      result.append(')');
      return result.toString();
   }

} // AnnotationImpl
