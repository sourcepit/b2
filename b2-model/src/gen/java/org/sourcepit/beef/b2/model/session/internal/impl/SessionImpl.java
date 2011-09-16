/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session.internal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.beef.b2.model.common.CommonPackage;
import org.sourcepit.beef.b2.model.common.internal.impl.EMapEntryImpl;
import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.b2.model.session.Session;
import org.sourcepit.beef.b2.model.session.SessionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Session</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl#getProjects <em>Projects</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl#getCurrentProject <em>Current Project</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.internal.impl.SessionImpl#getData <em>Data</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class SessionImpl extends EObjectImpl implements Session
{
   /**
    * The cached value of the '{@link #getProjects() <em>Projects</em>}' containment reference list. <!-- begin-user-doc
    * --> <!-- end-user-doc -->
    * 
    * @see #getProjects()
    * @generated
    * @ordered
    */
   protected EList<ModuleProject> projects;
   /**
    * The cached value of the '{@link #getCurrentProject() <em>Current Project</em>}' reference. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getCurrentProject()
    * @generated
    * @ordered
    */
   protected ModuleProject currentProject;

   /**
    * The cached value of the '{@link #getData() <em>Data</em>}' map. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getData()
    * @generated
    * @ordered
    */
   protected EMap<String, Object> data;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected SessionImpl()
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
      return SessionPackage.Literals.SESSION;
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
         projects = new EObjectContainmentWithInverseEList<ModuleProject>(ModuleProject.class, this,
            SessionPackage.SESSION__PROJECTS, SessionPackage.MODULE_PROJECT__SESSION);
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
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, SessionPackage.SESSION__CURRENT_PROJECT,
                  oldCurrentProject, currentProject));
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
         eNotify(new ENotificationImpl(this, Notification.SET, SessionPackage.SESSION__CURRENT_PROJECT,
            oldCurrentProject, currentProject));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EMap<String, Object> getData()
   {
      if (data == null)
      {
         data = new EcoreEMap<String, Object>(CommonPackage.Literals.EMAP_ENTRY, EMapEntryImpl.class, this,
            SessionPackage.SESSION__DATA);
      }
      return data;
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
         case SessionPackage.SESSION__PROJECTS :
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
         case SessionPackage.SESSION__PROJECTS :
            return ((InternalEList<?>) getProjects()).basicRemove(otherEnd, msgs);
         case SessionPackage.SESSION__DATA :
            return ((InternalEList<?>) getData()).basicRemove(otherEnd, msgs);
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
         case SessionPackage.SESSION__PROJECTS :
            return getProjects();
         case SessionPackage.SESSION__CURRENT_PROJECT :
            if (resolve)
               return getCurrentProject();
            return basicGetCurrentProject();
         case SessionPackage.SESSION__DATA :
            if (coreType)
               return getData();
            else
               return getData().map();
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
         case SessionPackage.SESSION__PROJECTS :
            getProjects().clear();
            getProjects().addAll((Collection<? extends ModuleProject>) newValue);
            return;
         case SessionPackage.SESSION__CURRENT_PROJECT :
            setCurrentProject((ModuleProject) newValue);
            return;
         case SessionPackage.SESSION__DATA :
            ((EStructuralFeature.Setting) getData()).set(newValue);
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
         case SessionPackage.SESSION__PROJECTS :
            getProjects().clear();
            return;
         case SessionPackage.SESSION__CURRENT_PROJECT :
            setCurrentProject((ModuleProject) null);
            return;
         case SessionPackage.SESSION__DATA :
            getData().clear();
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
         case SessionPackage.SESSION__PROJECTS :
            return projects != null && !projects.isEmpty();
         case SessionPackage.SESSION__CURRENT_PROJECT :
            return currentProject != null;
         case SessionPackage.SESSION__DATA :
            return data != null && !data.isEmpty();
      }
      return super.eIsSet(featureID);
   }

} // SessionImpl
