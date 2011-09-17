/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import java.io.File;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.beef.b2.model.common.Annotation;
import org.sourcepit.beef.b2.model.common.CommonPackage;
import org.sourcepit.beef.b2.model.module.Derivable;
import org.sourcepit.beef.b2.model.module.ModulePackage;
import org.sourcepit.beef.b2.model.module.ProductDefinition;
import org.sourcepit.beef.b2.model.module.ProductsFacet;
import org.sourcepit.beef.b2.model.module.Reference;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Product Definition</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.ProductDefinitionImpl#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.ProductDefinitionImpl#getFile <em>File</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.ProductDefinitionImpl#getProductPlugin <em>Product Plugin
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ProductDefinitionImpl extends EObjectImpl implements ProductDefinition
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
    * The default value of the '{@link #isDerived() <em>Derived</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #isDerived()
    * @generated
    * @ordered
    */
   protected static final boolean DERIVED_EDEFAULT = false;

   /**
    * The cached value of the '{@link #isDerived() <em>Derived</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #isDerived()
    * @generated
    * @ordered
    */
   protected boolean derived = DERIVED_EDEFAULT;

   /**
    * The default value of the '{@link #getFile() <em>File</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @see #getFile()
    * @generated
    * @ordered
    */
   protected static final File FILE_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getFile() <em>File</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
    * -->
    * 
    * @see #getFile()
    * @generated
    * @ordered
    */
   protected File file = FILE_EDEFAULT;

   /**
    * The cached value of the '{@link #getProductPlugin() <em>Product Plugin</em>}' containment reference. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getProductPlugin()
    * @generated
    * @ordered
    */
   protected Reference productPlugin;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected ProductDefinitionImpl()
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
      return ModulePackage.Literals.PRODUCT_DEFINITION;
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
         annotations = new EObjectContainmentWithInverseEList<Annotation>(Annotation.class, this,
            ModulePackage.PRODUCT_DEFINITION__ANNOTATIONS, CommonPackage.ANNOTATION__PARENT);
      }
      return annotations;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public boolean isDerived()
   {
      return derived;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setDerived(boolean newDerived)
   {
      boolean oldDerived = derived;
      derived = newDerived;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModulePackage.PRODUCT_DEFINITION__DERIVED, oldDerived,
            derived));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ProductsFacet getParent()
   {
      if (eContainerFeatureID() != ModulePackage.PRODUCT_DEFINITION__PARENT)
         return null;
      return (ProductsFacet) eContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(ProductsFacet newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, ModulePackage.PRODUCT_DEFINITION__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setParent(ProductsFacet newParent)
   {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != ModulePackage.PRODUCT_DEFINITION__PARENT && newParent != null))
      {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this, ModulePackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS,
               ProductsFacet.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModulePackage.PRODUCT_DEFINITION__PARENT, newParent,
            newParent));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public File getFile()
   {
      return file;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setFile(File newFile)
   {
      File oldFile = file;
      file = newFile;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModulePackage.PRODUCT_DEFINITION__FILE, oldFile, file));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Reference getProductPlugin()
   {
      return productPlugin;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetProductPlugin(Reference newProductPlugin, NotificationChain msgs)
   {
      Reference oldProductPlugin = productPlugin;
      productPlugin = newProductPlugin;
      if (eNotificationRequired())
      {
         ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
            ModulePackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN, oldProductPlugin, newProductPlugin);
         if (msgs == null)
            msgs = notification;
         else
            msgs.add(notification);
      }
      return msgs;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setProductPlugin(Reference newProductPlugin)
   {
      if (newProductPlugin != productPlugin)
      {
         NotificationChain msgs = null;
         if (productPlugin != null)
            msgs = ((InternalEObject) productPlugin).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
               - ModulePackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN, null, msgs);
         if (newProductPlugin != null)
            msgs = ((InternalEObject) newProductPlugin).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
               - ModulePackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN, null, msgs);
         msgs = basicSetProductPlugin(newProductPlugin, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModulePackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN,
            newProductPlugin, newProductPlugin));
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
         case ModulePackage.PRODUCT_DEFINITION__ANNOTATIONS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getAnnotations()).basicAdd(otherEnd, msgs);
         case ModulePackage.PRODUCT_DEFINITION__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((ProductsFacet) otherEnd, msgs);
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
         case ModulePackage.PRODUCT_DEFINITION__ANNOTATIONS :
            return ((InternalEList<?>) getAnnotations()).basicRemove(otherEnd, msgs);
         case ModulePackage.PRODUCT_DEFINITION__PARENT :
            return basicSetParent(null, msgs);
         case ModulePackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN :
            return basicSetProductPlugin(null, msgs);
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
         case ModulePackage.PRODUCT_DEFINITION__PARENT :
            return eInternalContainer().eInverseRemove(this, ModulePackage.PRODUCTS_FACET__PRODUCT_DEFINITIONS,
               ProductsFacet.class, msgs);
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
         case ModulePackage.PRODUCT_DEFINITION__ANNOTATIONS :
            return getAnnotations();
         case ModulePackage.PRODUCT_DEFINITION__DERIVED :
            return isDerived();
         case ModulePackage.PRODUCT_DEFINITION__PARENT :
            return getParent();
         case ModulePackage.PRODUCT_DEFINITION__FILE :
            return getFile();
         case ModulePackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN :
            return getProductPlugin();
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
         case ModulePackage.PRODUCT_DEFINITION__ANNOTATIONS :
            getAnnotations().clear();
            getAnnotations().addAll((Collection<? extends Annotation>) newValue);
            return;
         case ModulePackage.PRODUCT_DEFINITION__DERIVED :
            setDerived((Boolean) newValue);
            return;
         case ModulePackage.PRODUCT_DEFINITION__PARENT :
            setParent((ProductsFacet) newValue);
            return;
         case ModulePackage.PRODUCT_DEFINITION__FILE :
            setFile((File) newValue);
            return;
         case ModulePackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN :
            setProductPlugin((Reference) newValue);
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
         case ModulePackage.PRODUCT_DEFINITION__ANNOTATIONS :
            getAnnotations().clear();
            return;
         case ModulePackage.PRODUCT_DEFINITION__DERIVED :
            setDerived(DERIVED_EDEFAULT);
            return;
         case ModulePackage.PRODUCT_DEFINITION__PARENT :
            setParent((ProductsFacet) null);
            return;
         case ModulePackage.PRODUCT_DEFINITION__FILE :
            setFile(FILE_EDEFAULT);
            return;
         case ModulePackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN :
            setProductPlugin((Reference) null);
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
         case ModulePackage.PRODUCT_DEFINITION__ANNOTATIONS :
            return annotations != null && !annotations.isEmpty();
         case ModulePackage.PRODUCT_DEFINITION__DERIVED :
            return derived != DERIVED_EDEFAULT;
         case ModulePackage.PRODUCT_DEFINITION__PARENT :
            return getParent() != null;
         case ModulePackage.PRODUCT_DEFINITION__FILE :
            return FILE_EDEFAULT == null ? file != null : !FILE_EDEFAULT.equals(file);
         case ModulePackage.PRODUCT_DEFINITION__PRODUCT_PLUGIN :
            return productPlugin != null;
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
      if (baseClass == Derivable.class)
      {
         switch (derivedFeatureID)
         {
            case ModulePackage.PRODUCT_DEFINITION__DERIVED :
               return ModulePackage.DERIVABLE__DERIVED;
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
      if (baseClass == Derivable.class)
      {
         switch (baseFeatureID)
         {
            case ModulePackage.DERIVABLE__DERIVED :
               return ModulePackage.PRODUCT_DEFINITION__DERIVED;
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
      result.append(" (derived: ");
      result.append(derived);
      result.append(", file: ");
      result.append(file);
      result.append(')');
      return result.toString();
   }

} // ProductDefinitionImpl
