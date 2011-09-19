/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.beef.b2.model.module.FeatureProject;
import org.sourcepit.beef.b2.model.module.FeaturesFacet;
import org.sourcepit.beef.b2.model.module.ModuleModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Features Facet</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.FeaturesFacetImpl#getProjects <em>Projects</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class FeaturesFacetImpl extends ProjectFacetImpl<FeatureProject> implements FeaturesFacet
{
   /**
    * The cached value of the '{@link #getProjects() <em>Projects</em>}' containment reference list. <!-- begin-user-doc
    * --> <!-- end-user-doc -->
    * 
    * @see #getProjects()
    * @generated
    * @ordered
    */
   protected EList<FeatureProject> projects;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected FeaturesFacetImpl()
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
      return ModuleModelPackage.Literals.FEATURES_FACET;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<FeatureProject> getProjects()
   {
      if (projects == null)
      {
         projects = new EObjectContainmentWithInverseEList<FeatureProject>(FeatureProject.class, this,
            ModuleModelPackage.FEATURES_FACET__PROJECTS, ModuleModelPackage.FEATURE_PROJECT__PARENT);
      }
      return projects;
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
         case ModuleModelPackage.FEATURES_FACET__PROJECTS :
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
         case ModuleModelPackage.FEATURES_FACET__PROJECTS :
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
         case ModuleModelPackage.FEATURES_FACET__PROJECTS :
            return getProjects();
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
         case ModuleModelPackage.FEATURES_FACET__PROJECTS :
            getProjects().clear();
            getProjects().addAll((Collection<? extends FeatureProject>) newValue);
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
         case ModuleModelPackage.FEATURES_FACET__PROJECTS :
            getProjects().clear();
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
         case ModuleModelPackage.FEATURES_FACET__PROJECTS :
            return projects != null && !projects.isEmpty();
      }
      return super.eIsSet(featureID);
   }

} // FeaturesFacetImpl
