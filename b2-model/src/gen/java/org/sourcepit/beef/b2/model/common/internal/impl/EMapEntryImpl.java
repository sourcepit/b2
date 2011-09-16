/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.common.internal.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.sourcepit.beef.b2.model.common.CommonPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>EMap Entry</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.common.internal.impl.EMapEntryImpl#getTypedKey <em>Key</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.common.internal.impl.EMapEntryImpl#getTypedValue <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class EMapEntryImpl<K, V> extends EObjectImpl implements BasicEMap.Entry<K, V>
{
   /**
    * The cached value of the '{@link #getTypedKey() <em>Key</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @see #getTypedKey()
    * @generated
    * @ordered
    */
   protected K key;

   /**
    * The cached value of the '{@link #getTypedValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getTypedValue()
    * @generated
    * @ordered
    */
   protected V value;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected EMapEntryImpl()
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
      return CommonPackage.Literals.EMAP_ENTRY;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public K getTypedKey()
   {
      return key;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setTypedKey(K newKey)
   {
      K oldKey = key;
      key = newKey;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, CommonPackage.EMAP_ENTRY__KEY, oldKey, key));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public V getTypedValue()
   {
      return value;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setTypedValue(V newValue)
   {
      V oldValue = value;
      value = newValue;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, CommonPackage.EMAP_ENTRY__VALUE, oldValue, value));
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
         case CommonPackage.EMAP_ENTRY__KEY :
            return getTypedKey();
         case CommonPackage.EMAP_ENTRY__VALUE :
            return getTypedValue();
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
         case CommonPackage.EMAP_ENTRY__KEY :
            setTypedKey((K) newValue);
            return;
         case CommonPackage.EMAP_ENTRY__VALUE :
            setTypedValue((V) newValue);
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
         case CommonPackage.EMAP_ENTRY__KEY :
            setTypedKey((K) null);
            return;
         case CommonPackage.EMAP_ENTRY__VALUE :
            setTypedValue((V) null);
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
         case CommonPackage.EMAP_ENTRY__KEY :
            return key != null;
         case CommonPackage.EMAP_ENTRY__VALUE :
            return value != null;
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
      result.append(" (key: ");
      result.append(key);
      result.append(", value: ");
      result.append(value);
      result.append(')');
      return result.toString();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected int hash = -1;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public int getHash()
   {
      if (hash == -1)
      {
         Object theKey = getKey();
         hash = (theKey == null ? 0 : theKey.hashCode());
      }
      return hash;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setHash(int hash)
   {
      this.hash = hash;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public K getKey()
   {
      return getTypedKey();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setKey(K key)
   {
      setTypedKey(key);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public V getValue()
   {
      return getTypedValue();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public V setValue(V value)
   {
      V oldValue = getValue();
      setTypedValue(value);
      return oldValue;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @SuppressWarnings("unchecked")
   public EMap<K, V> getEMap()
   {
      EObject container = eContainer();
      return container == null ? null : (EMap<K, V>) container.eGet(eContainmentFeature());
   }

} // EMapEntryImpl
