/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session.internal.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Module Dependency</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.session.internal.impl.ModuleDependencyImpl#getGroupId <em>Group Id</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.internal.impl.ModuleDependencyImpl#getArtifactId <em>Artifact Id</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.internal.impl.ModuleDependencyImpl#getVersionRange <em>Version Range</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.internal.impl.ModuleDependencyImpl#getClassifier <em>Classifier</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ModuleDependencyImpl extends EObjectImpl implements ModuleDependency
{
   /**
    * The default value of the '{@link #getGroupId() <em>Group Id</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getGroupId()
    * @generated
    * @ordered
    */
   protected static final String GROUP_ID_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getGroupId() <em>Group Id</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getGroupId()
    * @generated
    * @ordered
    */
   protected String groupId = GROUP_ID_EDEFAULT;

   /**
    * The default value of the '{@link #getArtifactId() <em>Artifact Id</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getArtifactId()
    * @generated
    * @ordered
    */
   protected static final String ARTIFACT_ID_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getArtifactId() <em>Artifact Id</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getArtifactId()
    * @generated
    * @ordered
    */
   protected String artifactId = ARTIFACT_ID_EDEFAULT;

   /**
    * The default value of the '{@link #getVersionRange() <em>Version Range</em>}' attribute. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getVersionRange()
    * @generated
    * @ordered
    */
   protected static final String VERSION_RANGE_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getVersionRange() <em>Version Range</em>}' attribute. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getVersionRange()
    * @generated
    * @ordered
    */
   protected String versionRange = VERSION_RANGE_EDEFAULT;

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
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected ModuleDependencyImpl()
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
      return SessionModelPackage.Literals.MODULE_DEPENDENCY;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getGroupId()
   {
      return groupId;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setGroupId(String newGroupId)
   {
      String oldGroupId = groupId;
      groupId = newGroupId;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.MODULE_DEPENDENCY__GROUP_ID,
            oldGroupId, groupId));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getArtifactId()
   {
      return artifactId;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setArtifactId(String newArtifactId)
   {
      String oldArtifactId = artifactId;
      artifactId = newArtifactId;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.MODULE_DEPENDENCY__ARTIFACT_ID,
            oldArtifactId, artifactId));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getVersionRange()
   {
      return versionRange;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setVersionRange(String newVersionRange)
   {
      String oldVersionRange = versionRange;
      versionRange = newVersionRange;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.MODULE_DEPENDENCY__VERSION_RANGE,
            oldVersionRange, versionRange));
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
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.MODULE_DEPENDENCY__CLASSIFIER,
            oldClassifier, classifier));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public boolean isSatisfiableBy(ModuleProject moduleProject)
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
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType)
   {
      switch (featureID)
      {
         case SessionModelPackage.MODULE_DEPENDENCY__GROUP_ID :
            return getGroupId();
         case SessionModelPackage.MODULE_DEPENDENCY__ARTIFACT_ID :
            return getArtifactId();
         case SessionModelPackage.MODULE_DEPENDENCY__VERSION_RANGE :
            return getVersionRange();
         case SessionModelPackage.MODULE_DEPENDENCY__CLASSIFIER :
            return getClassifier();
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
         case SessionModelPackage.MODULE_DEPENDENCY__GROUP_ID :
            setGroupId((String) newValue);
            return;
         case SessionModelPackage.MODULE_DEPENDENCY__ARTIFACT_ID :
            setArtifactId((String) newValue);
            return;
         case SessionModelPackage.MODULE_DEPENDENCY__VERSION_RANGE :
            setVersionRange((String) newValue);
            return;
         case SessionModelPackage.MODULE_DEPENDENCY__CLASSIFIER :
            setClassifier((String) newValue);
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
         case SessionModelPackage.MODULE_DEPENDENCY__GROUP_ID :
            setGroupId(GROUP_ID_EDEFAULT);
            return;
         case SessionModelPackage.MODULE_DEPENDENCY__ARTIFACT_ID :
            setArtifactId(ARTIFACT_ID_EDEFAULT);
            return;
         case SessionModelPackage.MODULE_DEPENDENCY__VERSION_RANGE :
            setVersionRange(VERSION_RANGE_EDEFAULT);
            return;
         case SessionModelPackage.MODULE_DEPENDENCY__CLASSIFIER :
            setClassifier(CLASSIFIER_EDEFAULT);
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
         case SessionModelPackage.MODULE_DEPENDENCY__GROUP_ID :
            return GROUP_ID_EDEFAULT == null ? groupId != null : !GROUP_ID_EDEFAULT.equals(groupId);
         case SessionModelPackage.MODULE_DEPENDENCY__ARTIFACT_ID :
            return ARTIFACT_ID_EDEFAULT == null ? artifactId != null : !ARTIFACT_ID_EDEFAULT.equals(artifactId);
         case SessionModelPackage.MODULE_DEPENDENCY__VERSION_RANGE :
            return VERSION_RANGE_EDEFAULT == null ? versionRange != null : !VERSION_RANGE_EDEFAULT.equals(versionRange);
         case SessionModelPackage.MODULE_DEPENDENCY__CLASSIFIER :
            return CLASSIFIER_EDEFAULT == null ? classifier != null : !CLASSIFIER_EDEFAULT.equals(classifier);
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
      result.append(" (groupId: ");
      result.append(groupId);
      result.append(", artifactId: ");
      result.append(artifactId);
      result.append(", versionRange: ");
      result.append(versionRange);
      result.append(", classifier: ");
      result.append(classifier);
      result.append(')');
      return result.toString();
   }

} // ModuleDependencyImpl
