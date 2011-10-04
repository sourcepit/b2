/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import java.util.Collection;
import java.util.Locale;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.beef.b2.model.common.Annotatable;
import org.sourcepit.beef.b2.model.common.Annotation;
import org.sourcepit.beef.b2.model.common.CommonModelPackage;
import org.sourcepit.beef.b2.model.module.AbstractFacet;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.CompositeModule;
import org.sourcepit.beef.b2.model.module.Identifiable;
import org.sourcepit.beef.b2.model.module.ModuleModelPackage;
import org.sourcepit.beef.b2.model.module.Project;
import org.sourcepit.beef.b2.model.module.ProjectFacet;
import org.sourcepit.beef.b2.model.module.Reference;
import org.sourcepit.beef.b2.model.module.util.Identifier;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Abstract Module</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.AbstractModuleImpl#getName <em>Name</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.AbstractModuleImpl#getDirectory <em>Directory</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.AbstractModuleImpl#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.AbstractModuleImpl#getLayoutId <em>Layout Id</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class AbstractModuleImpl extends FileContainerImpl implements AbstractModule
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
    * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getId()
    * @generated
    * @ordered
    */
   protected static final String ID_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getId()
    * @generated
    * @ordered
    */
   protected String id = ID_EDEFAULT;

   /**
    * The default value of the '{@link #getVersion() <em>Version</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getVersion()
    * @generated
    * @ordered
    */
   protected static final String VERSION_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getVersion()
    * @generated
    * @ordered
    */
   protected String version = VERSION_EDEFAULT;

   /**
    * The default value of the '{@link #getLayoutId() <em>Layout Id</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getLayoutId()
    * @generated
    * @ordered
    */
   protected static final String LAYOUT_ID_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getLayoutId() <em>Layout Id</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getLayoutId()
    * @generated
    * @ordered
    */
   protected String layoutId = LAYOUT_ID_EDEFAULT;

   /**
    * The cached value of the '{@link #getLocales() <em>Locales</em>}' attribute list. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getLocales()
    * @generated
    * @ordered
    */
   protected EList<Locale> locales;

   /**
    * The cached value of the '{@link #getFacets() <em>Facets</em>}' containment reference list. <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @see #getFacets()
    * @generated
    * @ordered
    */
   protected EList<AbstractFacet> facets;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected AbstractModuleImpl()
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
      return ModuleModelPackage.Literals.ABSTRACT_MODULE;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getId()
   {
      return id;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setId(String newId)
   {
      String oldId = id;
      id = newId;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.ABSTRACT_MODULE__ID, oldId, id));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getVersion()
   {
      return version;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setVersion(String newVersion)
   {
      String oldVersion = version;
      version = newVersion;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.ABSTRACT_MODULE__VERSION, oldVersion,
            version));
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
            ModuleModelPackage.ABSTRACT_MODULE__ANNOTATIONS, CommonModelPackage.ANNOTATION__PARENT);
      }
      return annotations;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public CompositeModule getParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.ABSTRACT_MODULE__PARENT)
         return null;
      return (CompositeModule) eContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public CompositeModule basicGetParent()
   {
      if (eContainerFeatureID() != ModuleModelPackage.ABSTRACT_MODULE__PARENT)
         return null;
      return (CompositeModule) eInternalContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetParent(CompositeModule newParent, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newParent, ModuleModelPackage.ABSTRACT_MODULE__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setParent(CompositeModule newParent)
   {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != ModuleModelPackage.ABSTRACT_MODULE__PARENT && newParent != null))
      {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this, ModuleModelPackage.COMPOSITE_MODULE__MODULES,
               CompositeModule.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.ABSTRACT_MODULE__PARENT, newParent,
            newParent));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getLayoutId()
   {
      return layoutId;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setLayoutId(String newLayoutId)
   {
      String oldLayoutId = layoutId;
      layoutId = newLayoutId;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModuleModelPackage.ABSTRACT_MODULE__LAYOUT_ID,
            oldLayoutId, layoutId));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<Locale> getLocales()
   {
      if (locales == null)
      {
         locales = new EDataTypeUniqueEList<Locale>(Locale.class, this, ModuleModelPackage.ABSTRACT_MODULE__LOCALES);
      }
      return locales;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<AbstractFacet> getFacets()
   {
      if (facets == null)
      {
         facets = new EObjectContainmentWithInverseEList.Resolving<AbstractFacet>(AbstractFacet.class, this,
            ModuleModelPackage.ABSTRACT_MODULE__FACETS, ModuleModelPackage.ABSTRACT_FACET__PARENT);
      }
      return facets;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public <T extends AbstractFacet> EList<T> getFacets(Class<T> facetType)
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
   public boolean hasFacets(Class<? extends AbstractFacet> facetType)
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
   public <F extends AbstractFacet> F getFacetByName(String type)
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
   public <P extends Project, F extends ProjectFacet<P>> P resolveReference(Reference reference, Class<F> facetType)
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
   public boolean isIdentifyableBy(Identifier identifier)
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
   public Identifier toIdentifier()
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
         case ModuleModelPackage.ABSTRACT_MODULE__ANNOTATIONS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getAnnotations()).basicAdd(otherEnd, msgs);
         case ModuleModelPackage.ABSTRACT_MODULE__PARENT :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((CompositeModule) otherEnd, msgs);
         case ModuleModelPackage.ABSTRACT_MODULE__FACETS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getFacets()).basicAdd(otherEnd, msgs);
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
         case ModuleModelPackage.ABSTRACT_MODULE__ANNOTATIONS :
            return ((InternalEList<?>) getAnnotations()).basicRemove(otherEnd, msgs);
         case ModuleModelPackage.ABSTRACT_MODULE__PARENT :
            return basicSetParent(null, msgs);
         case ModuleModelPackage.ABSTRACT_MODULE__FACETS :
            return ((InternalEList<?>) getFacets()).basicRemove(otherEnd, msgs);
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
         case ModuleModelPackage.ABSTRACT_MODULE__PARENT :
            return eInternalContainer().eInverseRemove(this, ModuleModelPackage.COMPOSITE_MODULE__MODULES,
               CompositeModule.class, msgs);
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
         case ModuleModelPackage.ABSTRACT_MODULE__ANNOTATIONS :
            return getAnnotations();
         case ModuleModelPackage.ABSTRACT_MODULE__ID :
            return getId();
         case ModuleModelPackage.ABSTRACT_MODULE__VERSION :
            return getVersion();
         case ModuleModelPackage.ABSTRACT_MODULE__PARENT :
            if (resolve)
               return getParent();
            return basicGetParent();
         case ModuleModelPackage.ABSTRACT_MODULE__LAYOUT_ID :
            return getLayoutId();
         case ModuleModelPackage.ABSTRACT_MODULE__LOCALES :
            return getLocales();
         case ModuleModelPackage.ABSTRACT_MODULE__FACETS :
            return getFacets();
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
         case ModuleModelPackage.ABSTRACT_MODULE__ANNOTATIONS :
            getAnnotations().clear();
            getAnnotations().addAll((Collection<? extends Annotation>) newValue);
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__ID :
            setId((String) newValue);
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__VERSION :
            setVersion((String) newValue);
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__PARENT :
            setParent((CompositeModule) newValue);
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__LAYOUT_ID :
            setLayoutId((String) newValue);
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__LOCALES :
            getLocales().clear();
            getLocales().addAll((Collection<? extends Locale>) newValue);
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__FACETS :
            getFacets().clear();
            getFacets().addAll((Collection<? extends AbstractFacet>) newValue);
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
         case ModuleModelPackage.ABSTRACT_MODULE__ANNOTATIONS :
            getAnnotations().clear();
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__ID :
            setId(ID_EDEFAULT);
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__VERSION :
            setVersion(VERSION_EDEFAULT);
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__PARENT :
            setParent((CompositeModule) null);
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__LAYOUT_ID :
            setLayoutId(LAYOUT_ID_EDEFAULT);
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__LOCALES :
            getLocales().clear();
            return;
         case ModuleModelPackage.ABSTRACT_MODULE__FACETS :
            getFacets().clear();
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
         case ModuleModelPackage.ABSTRACT_MODULE__ANNOTATIONS :
            return annotations != null && !annotations.isEmpty();
         case ModuleModelPackage.ABSTRACT_MODULE__ID :
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
         case ModuleModelPackage.ABSTRACT_MODULE__VERSION :
            return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
         case ModuleModelPackage.ABSTRACT_MODULE__PARENT :
            return basicGetParent() != null;
         case ModuleModelPackage.ABSTRACT_MODULE__LAYOUT_ID :
            return LAYOUT_ID_EDEFAULT == null ? layoutId != null : !LAYOUT_ID_EDEFAULT.equals(layoutId);
         case ModuleModelPackage.ABSTRACT_MODULE__LOCALES :
            return locales != null && !locales.isEmpty();
         case ModuleModelPackage.ABSTRACT_MODULE__FACETS :
            return facets != null && !facets.isEmpty();
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
      if (baseClass == Annotatable.class)
      {
         switch (derivedFeatureID)
         {
            case ModuleModelPackage.ABSTRACT_MODULE__ANNOTATIONS :
               return CommonModelPackage.ANNOTATABLE__ANNOTATIONS;
            default :
               return -1;
         }
      }
      if (baseClass == Identifiable.class)
      {
         switch (derivedFeatureID)
         {
            case ModuleModelPackage.ABSTRACT_MODULE__ID :
               return ModuleModelPackage.IDENTIFIABLE__ID;
            case ModuleModelPackage.ABSTRACT_MODULE__VERSION :
               return ModuleModelPackage.IDENTIFIABLE__VERSION;
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
      if (baseClass == Annotatable.class)
      {
         switch (baseFeatureID)
         {
            case CommonModelPackage.ANNOTATABLE__ANNOTATIONS :
               return ModuleModelPackage.ABSTRACT_MODULE__ANNOTATIONS;
            default :
               return -1;
         }
      }
      if (baseClass == Identifiable.class)
      {
         switch (baseFeatureID)
         {
            case ModuleModelPackage.IDENTIFIABLE__ID :
               return ModuleModelPackage.ABSTRACT_MODULE__ID;
            case ModuleModelPackage.IDENTIFIABLE__VERSION :
               return ModuleModelPackage.ABSTRACT_MODULE__VERSION;
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
      result.append(" (id: ");
      result.append(id);
      result.append(", version: ");
      result.append(version);
      result.append(", layoutId: ");
      result.append(layoutId);
      result.append(", locales: ");
      result.append(locales);
      result.append(')');
      return result.toString();
   }

} // AbstractModuleImpl
