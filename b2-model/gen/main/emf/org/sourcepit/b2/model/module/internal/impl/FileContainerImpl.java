/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.internal.impl;

import java.io.File;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.sourcepit.b2.model.module.FileContainer;
import org.sourcepit.b2.model.module.ModuleModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.FileContainerImpl#getDirectory <em>Directory</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class FileContainerImpl extends EObjectImpl implements FileContainer
{
   /**
    * The default value of the '{@link #getDirectory() <em>Directory</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getDirectory()
    * @generated
    * @ordered
    */
   protected static final File DIRECTORY_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getDirectory() <em>Directory</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getDirectory()
    * @generated
    * @ordered
    */
   protected File directory = DIRECTORY_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   protected FileContainerImpl()
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
      return ModuleModelPackage.Literals.FILE_CONTAINER;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public File getDirectory()
   {
      return directory;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setDirectory(File newDirectory)
   {
      File oldDirectory = directory;
      directory = newDirectory;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.FILE_CONTAINER__DIRECTORY,
            oldDirectory, directory));
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
         case ModuleModelPackage.FILE_CONTAINER__DIRECTORY :
            return getDirectory();
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
         case ModuleModelPackage.FILE_CONTAINER__DIRECTORY :
            setDirectory((File) newValue);
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
         case ModuleModelPackage.FILE_CONTAINER__DIRECTORY :
            setDirectory(DIRECTORY_EDEFAULT);
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
         case ModuleModelPackage.FILE_CONTAINER__DIRECTORY :
            return DIRECTORY_EDEFAULT == null ? directory != null : !DIRECTORY_EDEFAULT.equals(directory);
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
      result.append(" (directory: ");
      result.append(directory);
      result.append(')');
      return result.toString();
   }

} // FileContainerImpl