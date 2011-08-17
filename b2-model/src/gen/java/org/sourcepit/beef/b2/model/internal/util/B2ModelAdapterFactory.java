/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.internal.util;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.internal.model.AbstractFacet;
import org.sourcepit.beef.b2.internal.model.AbstractModule;
import org.sourcepit.beef.b2.internal.model.Annotateable;
import org.sourcepit.beef.b2.internal.model.Annotation;
import org.sourcepit.beef.b2.internal.model.B2ModelPackage;
import org.sourcepit.beef.b2.internal.model.BasicModule;
import org.sourcepit.beef.b2.internal.model.Category;
import org.sourcepit.beef.b2.internal.model.Classified;
import org.sourcepit.beef.b2.internal.model.CompositeModule;
import org.sourcepit.beef.b2.internal.model.Derivable;
import org.sourcepit.beef.b2.internal.model.FeatureInclude;
import org.sourcepit.beef.b2.internal.model.FeatureProject;
import org.sourcepit.beef.b2.internal.model.FeaturesFacet;
import org.sourcepit.beef.b2.internal.model.FileContainer;
import org.sourcepit.beef.b2.internal.model.Identifiable;
import org.sourcepit.beef.b2.internal.model.PluginInclude;
import org.sourcepit.beef.b2.internal.model.PluginProject;
import org.sourcepit.beef.b2.internal.model.PluginsFacet;
import org.sourcepit.beef.b2.internal.model.ProductDefinition;
import org.sourcepit.beef.b2.internal.model.ProductsFacet;
import org.sourcepit.beef.b2.internal.model.Project;
import org.sourcepit.beef.b2.internal.model.ProjectFacet;
import org.sourcepit.beef.b2.internal.model.Reference;
import org.sourcepit.beef.b2.internal.model.SiteProject;
import org.sourcepit.beef.b2.internal.model.SitesFacet;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage
 * @generated
 */
public class B2ModelAdapterFactory extends AdapterFactoryImpl
{
   /**
    * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected static B2ModelPackage modelPackage;

   /**
    * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public B2ModelAdapterFactory()
   {
      if (modelPackage == null)
      {
         modelPackage = B2ModelPackage.eINSTANCE;
      }
   }

   /**
    * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This implementation
    * returns <code>true</code> if the object is either the model's package or is an instance object of the model. <!--
    * end-user-doc -->
    * 
    * @return whether this factory is applicable for the type of the object.
    * @generated
    */
   @Override
   public boolean isFactoryForType(Object object)
   {
      if (object == modelPackage)
      {
         return true;
      }
      if (object instanceof EObject)
      {
         return ((EObject) object).eClass().getEPackage() == modelPackage;
      }
      return false;
   }

   /**
    * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected B2ModelSwitch<Adapter> modelSwitch = new B2ModelSwitch<Adapter>()
   {
      @Override
      public Adapter caseAbstractModule(AbstractModule object)
      {
         return createAbstractModuleAdapter();
      }

      @Override
      public Adapter caseBasicModule(BasicModule object)
      {
         return createBasicModuleAdapter();
      }

      @Override
      public Adapter caseAbstractFacet(AbstractFacet object)
      {
         return createAbstractFacetAdapter();
      }

      @Override
      public Adapter caseCompositeModule(CompositeModule object)
      {
         return createCompositeModuleAdapter();
      }

      @Override
      public Adapter casePluginsFacet(PluginsFacet object)
      {
         return createPluginsFacetAdapter();
      }

      @Override
      public Adapter caseFeaturesFacet(FeaturesFacet object)
      {
         return createFeaturesFacetAdapter();
      }

      @Override
      public Adapter caseSitesFacet(SitesFacet object)
      {
         return createSitesFacetAdapter();
      }

      @Override
      public Adapter casePluginProject(PluginProject object)
      {
         return createPluginProjectAdapter();
      }

      @Override
      public Adapter caseFeatureProject(FeatureProject object)
      {
         return createFeatureProjectAdapter();
      }

      @Override
      public Adapter caseSiteProject(SiteProject object)
      {
         return createSiteProjectAdapter();
      }

      @Override
      public Adapter caseProject(Project object)
      {
         return createProjectAdapter();
      }

      @Override
      public <P extends Project> Adapter caseProjectFacet(ProjectFacet<P> object)
      {
         return createProjectFacetAdapter();
      }

      @Override
      public Adapter caseFileContainer(FileContainer object)
      {
         return createFileContainerAdapter();
      }

      @Override
      public Adapter caseDerivable(Derivable object)
      {
         return createDerivableAdapter();
      }

      @Override
      public Adapter casePluginInclude(PluginInclude object)
      {
         return createPluginIncludeAdapter();
      }

      @Override
      public Adapter caseCategory(Category object)
      {
         return createCategoryAdapter();
      }

      @Override
      public Adapter caseFeatureInclude(FeatureInclude object)
      {
         return createFeatureIncludeAdapter();
      }

      @Override
      public Adapter caseClassified(Classified object)
      {
         return createClassifiedAdapter();
      }

      @Override
      public Adapter caseAnnotateable(Annotateable object)
      {
         return createAnnotateableAdapter();
      }

      @Override
      public Adapter caseAnnotation(Annotation object)
      {
         return createAnnotationAdapter();
      }

      @Override
      public Adapter caseEStringMapEntry(Map.Entry<String, String> object)
      {
         return createEStringMapEntryAdapter();
      }

      @Override
      public Adapter caseIdentifiable(Identifiable object)
      {
         return createIdentifiableAdapter();
      }

      @Override
      public Adapter caseProductsFacet(ProductsFacet object)
      {
         return createProductsFacetAdapter();
      }

      @Override
      public Adapter caseProductDefinition(ProductDefinition object)
      {
         return createProductDefinitionAdapter();
      }

      @Override
      public Adapter caseReference(Reference object)
      {
         return createReferenceAdapter();
      }

      @Override
      public Adapter defaultCase(EObject object)
      {
         return createEObjectAdapter();
      }
   };

   /**
    * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param target the object to adapt.
    * @return the adapter for the <code>target</code>.
    * @generated
    */
   @Override
   public Adapter createAdapter(Notifier target)
   {
      return modelSwitch.doSwitch((EObject) target);
   }


   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.AbstractModule
    * <em>Abstract Module</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.AbstractModule
    * @generated
    */
   public Adapter createAbstractModuleAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.BasicModule
    * <em>Basic Module</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.BasicModule
    * @generated
    */
   public Adapter createBasicModuleAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.AbstractFacet
    * <em>Abstract Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.AbstractFacet
    * @generated
    */
   public Adapter createAbstractFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.CompositeModule
    * <em>Composite Module</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
    * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
    * end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.CompositeModule
    * @generated
    */
   public Adapter createCompositeModuleAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.PluginsFacet
    * <em>Plugins Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.PluginsFacet
    * @generated
    */
   public Adapter createPluginsFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.FeaturesFacet
    * <em>Features Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.FeaturesFacet
    * @generated
    */
   public Adapter createFeaturesFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.SitesFacet
    * <em>Sites Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.SitesFacet
    * @generated
    */
   public Adapter createSitesFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.PluginProject
    * <em>Plugin Project</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.PluginProject
    * @generated
    */
   public Adapter createPluginProjectAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.FeatureProject
    * <em>Feature Project</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.FeatureProject
    * @generated
    */
   public Adapter createFeatureProjectAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.SiteProject
    * <em>Site Project</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.SiteProject
    * @generated
    */
   public Adapter createSiteProjectAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.Project
    * <em>Project</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
    * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.Project
    * @generated
    */
   public Adapter createProjectAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.ProjectFacet
    * <em>Project Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.ProjectFacet
    * @generated
    */
   public Adapter createProjectFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.FileContainer
    * <em>File Container</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.FileContainer
    * @generated
    */
   public Adapter createFileContainerAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.Derivable
    * <em>Derivable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.Derivable
    * @generated
    */
   public Adapter createDerivableAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.PluginInclude
    * <em>Plugin Include</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.PluginInclude
    * @generated
    */
   public Adapter createPluginIncludeAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.Category
    * <em>Category</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
    * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.Category
    * @generated
    */
   public Adapter createCategoryAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.FeatureInclude
    * <em>Feature Include</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.FeatureInclude
    * @generated
    */
   public Adapter createFeatureIncludeAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.Classified
    * <em>Classified</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.Classified
    * @generated
    */
   public Adapter createClassifiedAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.Annotateable
    * <em>Annotateable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.Annotateable
    * @generated
    */
   public Adapter createAnnotateableAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.Annotation
    * <em>Annotation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.Annotation
    * @generated
    */
   public Adapter createAnnotationAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>EString Map Entry</em>}'. <!--
    * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
    * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see java.util.Map.Entry
    * @generated
    */
   public Adapter createEStringMapEntryAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.Identifiable
    * <em>Identifiable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.Identifiable
    * @generated
    */
   public Adapter createIdentifiableAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.ProductsFacet
    * <em>Products Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.ProductsFacet
    * @generated
    */
   public Adapter createProductsFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.ProductDefinition
    * <em>Product Definition</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
    * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
    * end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.ProductDefinition
    * @generated
    */
   public Adapter createProductDefinitionAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.internal.model.Reference
    * <em>Reference</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.internal.model.Reference
    * @generated
    */
   public Adapter createReferenceAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null. <!--
    * end-user-doc -->
    * 
    * @return the new adapter.
    * @generated
    */
   public Adapter createEObjectAdapter()
   {
      return null;
   }

} // B2ModelAdapterFactory
