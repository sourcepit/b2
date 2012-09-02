/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.VersionMatchRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ruled Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.RuledReferenceImpl#getVersionMatchRule <em>Version Match Rule
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class RuledReferenceImpl extends AbstractReferenceImpl implements RuledReference
{
   /**
    * The default value of the '{@link #getVersionMatchRule() <em>Version Match Rule</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getVersionMatchRule()
    * @generated
    * @ordered
    */
   protected static final VersionMatchRule VERSION_MATCH_RULE_EDEFAULT = VersionMatchRule.COMPATIBLE;

   /**
    * The cached value of the '{@link #getVersionMatchRule() <em>Version Match Rule</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getVersionMatchRule()
    * @generated
    * @ordered
    */
   protected VersionMatchRule versionMatchRule = VERSION_MATCH_RULE_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   protected RuledReferenceImpl()
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
      return ModuleModelPackage.Literals.RULED_REFERENCE;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public VersionMatchRule getVersionMatchRule()
   {
      return versionMatchRule;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setVersionMatchRule(VersionMatchRule newVersionMatchRule)
   {
      VersionMatchRule oldVersionMatchRule = versionMatchRule;
      versionMatchRule = newVersionMatchRule == null ? VERSION_MATCH_RULE_EDEFAULT : newVersionMatchRule;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.RULED_REFERENCE__VERSION_MATCH_RULE,
            oldVersionMatchRule, versionMatchRule));
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
         case ModuleModelPackage.RULED_REFERENCE__VERSION_MATCH_RULE :
            return getVersionMatchRule();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public void eSet(int featureID, Object newValue)
   {
      switch (featureID)
      {
         case ModuleModelPackage.RULED_REFERENCE__VERSION_MATCH_RULE :
            setVersionMatchRule((VersionMatchRule) newValue);
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
         case ModuleModelPackage.RULED_REFERENCE__VERSION_MATCH_RULE :
            setVersionMatchRule(VERSION_MATCH_RULE_EDEFAULT);
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
         case ModuleModelPackage.RULED_REFERENCE__VERSION_MATCH_RULE :
            return versionMatchRule != VERSION_MATCH_RULE_EDEFAULT;
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
   public String toString()
   {
      if (eIsProxy())
         return super.toString();

      StringBuffer result = new StringBuffer(super.toString());
      result.append(" (versionMatchRule: ");
      result.append(versionMatchRule);
      result.append(')');
      return result.toString();
   }

} // RuledReferenceImpl
