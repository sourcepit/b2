/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session.internal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.b2.model.common.Annotation;
import org.sourcepit.b2.model.common.CommonModelPackage;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>B2 Session</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.session.internal.impl.B2SessionImpl#getAnnotations <em>Annotations</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.internal.impl.B2SessionImpl#getProjects <em>Projects</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.internal.impl.B2SessionImpl#getCurrentProject <em>Current Project</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class B2SessionImpl extends EObjectImpl implements B2Session
{
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
    * The cached value of the '{@link #getProjects() <em>Projects</em>}' containment reference list.
    * <!-- begin-user-doc
    * --> <!-- end-user-doc -->
    * 
    * @see #getProjects()
    * @generated
    * @ordered
    */
   protected EList<ModuleProject> projects;

   /**
    * The cached value of the '{@link #getCurrentProject() <em>Current Project</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getCurrentProject()
    * @generated
    * @ordered
    */
   protected ModuleProject currentProject;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected B2SessionImpl()
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
      return SessionModelPackage.Literals.B2_SESSION;
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
         annotations = new EObjectContainmentWithInverseEList.Resolving<Annotation>(Annotation.class, this,
            SessionModelPackage.B2_SESSION__ANNOTATIONS, CommonModelPackage.ANNOTATION__PARENT);
      }
      return annotations;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<ModuleProject> getProjects()
   {
      if (projects == null)
      {
         projects = new EObjectContainmentWithInverseEList.Resolving<ModuleProject>(ModuleProject.class, this,
            SessionModelPackage.B2_SESSION__PROJECTS, SessionModelPackage.MODULE_PROJECT__SESSION);
      }
      return projects;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleProject getCurrentProject()
   {
      if (currentProject != null && currentProject.eIsProxy())
      {
         InternalEObject oldCurrentProject = (InternalEObject) currentProject;
         currentProject = (ModuleProject) eResolveProxy(oldCurrentProject);
         if (currentProject != oldCurrentProject)
         {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                  SessionModelPackage.B2_SESSION__CURRENT_PROJECT, oldCurrentProject, currentProject));
         }
      }
      return currentProject;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleProject basicGetCurrentProject()
   {
      return currentProject;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setCurrentProject(ModuleProject newCurrentProject)
   {
      ModuleProject oldCurrentProject = currentProject;
      currentProject = newCurrentProject;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.B2_SESSION__CURRENT_PROJECT,
            oldCurrentProject, currentProject));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleProject getProject(String groupId, String artifactId, String version)
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
         case SessionModelPackage.B2_SESSION__ANNOTATIONS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getAnnotations()).basicAdd(otherEnd, msgs);
         case SessionModelPackage.B2_SESSION__PROJECTS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getProjects()).basicAdd(otherEnd, msgs);
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
         case SessionModelPackage.B2_SESSION__ANNOTATIONS :
            return ((InternalEList<?>) getAnnotations()).basicRemove(otherEnd, msgs);
         case SessionModelPackage.B2_SESSION__PROJECTS :
            return ((InternalEList<?>) getProjects()).basicRemove(otherEnd, msgs);
      }
      return super.eInverseRemove(otherEnd, featureID, msgs);
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
         case SessionModelPackage.B2_SESSION__ANNOTATIONS :
            return getAnnotations();
         case SessionModelPackage.B2_SESSION__PROJECTS :
            return getProjects();
         case SessionModelPackage.B2_SESSION__CURRENT_PROJECT :
            if (resolve)
               return getCurrentProject();
            return basicGetCurrentProject();
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
         case SessionModelPackage.B2_SESSION__ANNOTATIONS :
            getAnnotations().clear();
            getAnnotations().addAll((Collection<? extends Annotation>) newValue);
            return;
         case SessionModelPackage.B2_SESSION__PROJECTS :
            getProjects().clear();
            getProjects().addAll((Collection<? extends ModuleProject>) newValue);
            return;
         case SessionModelPackage.B2_SESSION__CURRENT_PROJECT :
            setCurrentProject((ModuleProject) newValue);
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
         case SessionModelPackage.B2_SESSION__ANNOTATIONS :
            getAnnotations().clear();
            return;
         case SessionModelPackage.B2_SESSION__PROJECTS :
            getProjects().clear();
            return;
         case SessionModelPackage.B2_SESSION__CURRENT_PROJECT :
            setCurrentProject((ModuleProject) null);
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
         case SessionModelPackage.B2_SESSION__ANNOTATIONS :
            return annotations != null && !annotations.isEmpty();
         case SessionModelPackage.B2_SESSION__PROJECTS :
            return projects != null && !projects.isEmpty();
         case SessionModelPackage.B2_SESSION__CURRENT_PROJECT :
            return currentProject != null;
      }
      return super.eIsSet(featureID);
   }

} // B2SessionImpl
