/*
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session.internal.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.sourcepit.b2.model.session.Environment;
import org.sourcepit.b2.model.session.SessionModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Environment</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.session.internal.impl.EnvironmentImpl#getOs <em>Os</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.internal.impl.EnvironmentImpl#getWs <em>Ws</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.internal.impl.EnvironmentImpl#getArch <em>Arch</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class EnvironmentImpl extends EObjectImpl implements Environment
{
   /**
    * The default value of the '{@link #getOs() <em>Os</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getOs()
    * @generated
    * @ordered
    */
   protected static final String OS_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getOs() <em>Os</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getOs()
    * @generated
    * @ordered
    */
   protected String os = OS_EDEFAULT;

   /**
    * The default value of the '{@link #getWs() <em>Ws</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getWs()
    * @generated
    * @ordered
    */
   protected static final String WS_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getWs() <em>Ws</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getWs()
    * @generated
    * @ordered
    */
   protected String ws = WS_EDEFAULT;

   /**
    * The default value of the '{@link #getArch() <em>Arch</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @see #getArch()
    * @generated
    * @ordered
    */
   protected static final String ARCH_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getArch() <em>Arch</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @see #getArch()
    * @generated
    * @ordered
    */
   protected String arch = ARCH_EDEFAULT;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected EnvironmentImpl()
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
      return SessionModelPackage.Literals.ENVIRONMENT;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getOs()
   {
      return os;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setOs(String newOs)
   {
      String oldOs = os;
      os = newOs;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.ENVIRONMENT__OS, oldOs, os));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getWs()
   {
      return ws;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setWs(String newWs)
   {
      String oldWs = ws;
      ws = newWs;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.ENVIRONMENT__WS, oldWs, ws));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getArch()
   {
      return arch;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setArch(String newArch)
   {
      String oldArch = arch;
      arch = newArch;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.ENVIRONMENT__ARCH, oldArch, arch));
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
         case SessionModelPackage.ENVIRONMENT__OS :
            return getOs();
         case SessionModelPackage.ENVIRONMENT__WS :
            return getWs();
         case SessionModelPackage.ENVIRONMENT__ARCH :
            return getArch();
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
         case SessionModelPackage.ENVIRONMENT__OS :
            setOs((String) newValue);
            return;
         case SessionModelPackage.ENVIRONMENT__WS :
            setWs((String) newValue);
            return;
         case SessionModelPackage.ENVIRONMENT__ARCH :
            setArch((String) newValue);
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
         case SessionModelPackage.ENVIRONMENT__OS :
            setOs(OS_EDEFAULT);
            return;
         case SessionModelPackage.ENVIRONMENT__WS :
            setWs(WS_EDEFAULT);
            return;
         case SessionModelPackage.ENVIRONMENT__ARCH :
            setArch(ARCH_EDEFAULT);
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
         case SessionModelPackage.ENVIRONMENT__OS :
            return OS_EDEFAULT == null ? os != null : !OS_EDEFAULT.equals(os);
         case SessionModelPackage.ENVIRONMENT__WS :
            return WS_EDEFAULT == null ? ws != null : !WS_EDEFAULT.equals(ws);
         case SessionModelPackage.ENVIRONMENT__ARCH :
            return ARCH_EDEFAULT == null ? arch != null : !ARCH_EDEFAULT.equals(arch);
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
      result.append(" (os: ");
      result.append(os);
      result.append(", ws: ");
      result.append(ws);
      result.append(", arch: ");
      result.append(arch);
      result.append(')');
      return result.toString();
   }

} // EnvironmentImpl
