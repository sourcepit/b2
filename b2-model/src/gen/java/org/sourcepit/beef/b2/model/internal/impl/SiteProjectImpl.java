/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.internal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.beef.b2.internal.model.B2ModelPackage;
import org.sourcepit.beef.b2.internal.model.Category;
import org.sourcepit.beef.b2.internal.model.Classified;
import org.sourcepit.beef.b2.internal.model.SiteProject;
import org.sourcepit.beef.b2.internal.model.SitesFacet;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Site Project</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.internal.impl.SiteProjectImpl#getClassifier <em>Classifier</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.internal.impl.SiteProjectImpl#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.internal.impl.SiteProjectImpl#getCategories <em>Categories</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class SiteProjectImpl extends ProjectImpl implements SiteProject
{
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
    * The cached value of the '{@link #getCategories() <em>Categories</em>}' containment reference list. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getCategories()
    * @generated
    * @ordered
    */
   protected EList<Category> categories;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected SiteProjectImpl()
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
      return B2ModelPackage.Literals.SITE_PROJECT;
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
         eNotify(new ENotificationImpl(this, Notification.SET, B2ModelPackage.SITE_PROJECT__CLASSIFIER, oldClassifier,
            classifier));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SitesFacet getParent()
   {
      if (eContainerFeatureID() != B2ModelPackage.SITE_PROJECT__PARENT)
         return null;
      return (SitesFacet) eContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(SitesFacet newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, B2ModelPackage.SITE_PROJECT__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setParent(SitesFacet newParent)
   {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != B2ModelPackage.SITE_PROJECT__PARENT && newParent != null))
      {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this, B2ModelPackage.SITES_FACET__PROJECTS,
               SitesFacet.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, B2ModelPackage.SITE_PROJECT__PARENT, newParent,
            newParent));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<Category> getCategories()
   {
      if (categories == null)
      {
         categories = new EObjectContainmentEList<Category>(Category.class, this,
            B2ModelPackage.SITE_PROJECT__CATEGORIES);
      }
      return categories;
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
         case B2ModelPackage.SITE_PROJECT__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((SitesFacet) otherEnd, msgs);
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
         case B2ModelPackage.SITE_PROJECT__PARENT :
            return basicSetParent(null, msgs);
         case B2ModelPackage.SITE_PROJECT__CATEGORIES :
            return ((InternalEList<?>) getCategories()).basicRemove(otherEnd, msgs);
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
         case B2ModelPackage.SITE_PROJECT__PARENT :
            return eInternalContainer().eInverseRemove(this, B2ModelPackage.SITES_FACET__PROJECTS, SitesFacet.class,
               msgs);
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
         case B2ModelPackage.SITE_PROJECT__CLASSIFIER :
            return getClassifier();
         case B2ModelPackage.SITE_PROJECT__PARENT :
            return getParent();
         case B2ModelPackage.SITE_PROJECT__CATEGORIES :
            return getCategories();
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
         case B2ModelPackage.SITE_PROJECT__CLASSIFIER :
            setClassifier((String) newValue);
            return;
         case B2ModelPackage.SITE_PROJECT__PARENT :
            setParent((SitesFacet) newValue);
            return;
         case B2ModelPackage.SITE_PROJECT__CATEGORIES :
            getCategories().clear();
            getCategories().addAll((Collection<? extends Category>) newValue);
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
         case B2ModelPackage.SITE_PROJECT__CLASSIFIER :
            setClassifier(CLASSIFIER_EDEFAULT);
            return;
         case B2ModelPackage.SITE_PROJECT__PARENT :
            setParent((SitesFacet) null);
            return;
         case B2ModelPackage.SITE_PROJECT__CATEGORIES :
            getCategories().clear();
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
         case B2ModelPackage.SITE_PROJECT__CLASSIFIER :
            return CLASSIFIER_EDEFAULT == null ? classifier != null : !CLASSIFIER_EDEFAULT.equals(classifier);
         case B2ModelPackage.SITE_PROJECT__PARENT :
            return getParent() != null;
         case B2ModelPackage.SITE_PROJECT__CATEGORIES :
            return categories != null && !categories.isEmpty();
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
      if (baseClass == Classified.class)
      {
         switch (derivedFeatureID)
         {
            case B2ModelPackage.SITE_PROJECT__CLASSIFIER :
               return B2ModelPackage.CLASSIFIED__CLASSIFIER;
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
      if (baseClass == Classified.class)
      {
         switch (baseFeatureID)
         {
            case B2ModelPackage.CLASSIFIED__CLASSIFIER :
               return B2ModelPackage.SITE_PROJECT__CLASSIFIER;
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
      result.append(" (classifier: ");
      result.append(classifier);
      result.append(')');
      return result.toString();
   }

} // SiteProjectImpl
