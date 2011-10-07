/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.b2.model.module.internal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Products Facet</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.internal.impl.ProductsFacetImpl#getProductDefinitions <em>Product
 * Definitions</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ProductsFacetImpl extends AbstractFacetImpl implements ProductsFacet
{
   /**
    * The cached value of the '{@link #getProductDefinitions() <em>Product Definitions</em>}' containment reference
    * list. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getProductDefinitions()
    * @generated
    * @ordered
    */
   protected EList<ProductDefinition> productDefinitions;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected ProductsFacetImpl()
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
      return ModuleModelPackage.Literals.PRODUCTS_FACET;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<ProductDefinition> getProductDefinitions()
   {
      if (productDefinitions == null)
      {
         productDefinitions = new EObjectContainmentWithInverseEList.Resolving<ProductDefinition>(
            ProductDefinition.class, this, ModuleModelPackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS,
            ModuleModelPackage.PRODUCT_DEFINITION__PARENT);
      }
      return productDefinitions;
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
         case ModuleModelPackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getProductDefinitions()).basicAdd(otherEnd,
               msgs);
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
         case ModuleModelPackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS :
            return ((InternalEList<?>) getProductDefinitions()).basicRemove(otherEnd, msgs);
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
         case ModuleModelPackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS :
            return getProductDefinitions();
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
         case ModuleModelPackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS :
            getProductDefinitions().clear();
            getProductDefinitions().addAll((Collection<? extends ProductDefinition>) newValue);
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
         case ModuleModelPackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS :
            getProductDefinitions().clear();
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
         case ModuleModelPackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS :
            return productDefinitions != null && !productDefinitions.isEmpty();
      }
      return super.eIsSet(featureID);
   }

} // ProductsFacetImpl
