/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module.util;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.model.module.AbstractFacet;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.Annotateable;
import org.sourcepit.beef.b2.model.module.Annotation;
import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.Category;
import org.sourcepit.beef.b2.model.module.Classified;
import org.sourcepit.beef.b2.model.module.CompositeModule;
import org.sourcepit.beef.b2.model.module.Derivable;
import org.sourcepit.beef.b2.model.module.FeatureInclude;
import org.sourcepit.beef.b2.model.module.FeatureProject;
import org.sourcepit.beef.b2.model.module.FeaturesFacet;
import org.sourcepit.beef.b2.model.module.FileContainer;
import org.sourcepit.beef.b2.model.module.Identifiable;
import org.sourcepit.beef.b2.model.module.ModulePackage;
import org.sourcepit.beef.b2.model.module.PluginInclude;
import org.sourcepit.beef.b2.model.module.PluginProject;
import org.sourcepit.beef.b2.model.module.PluginsFacet;
import org.sourcepit.beef.b2.model.module.ProductDefinition;
import org.sourcepit.beef.b2.model.module.ProductsFacet;
import org.sourcepit.beef.b2.model.module.Project;
import org.sourcepit.beef.b2.model.module.ProjectFacet;
import org.sourcepit.beef.b2.model.module.Reference;
import org.sourcepit.beef.b2.model.module.SiteProject;
import org.sourcepit.beef.b2.model.module.SitesFacet;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.sourcepit.beef.b2.model.module.ModulePackage
 * @generated
 */
public class ModuleAdapterFactory extends AdapterFactoryImpl
{
   /**
    * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected static ModulePackage modelPackage;

   /**
    * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ModuleAdapterFactory()
   {
      if (modelPackage == null)
      {
         modelPackage = ModulePackage.eINSTANCE;
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
   protected ModuleSwitch<Adapter> modelSwitch = new ModuleSwitch<Adapter>()
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
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.AbstractModule
    * <em>Abstract Module</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.AbstractModule
    * @generated
    */
   public Adapter createAbstractModuleAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.BasicModule
    * <em>Basic Module</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.BasicModule
    * @generated
    */
   public Adapter createBasicModuleAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.AbstractFacet
    * <em>Abstract Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.AbstractFacet
    * @generated
    */
   public Adapter createAbstractFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.CompositeModule
    * <em>Composite Module</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
    * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
    * end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.CompositeModule
    * @generated
    */
   public Adapter createCompositeModuleAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.PluginsFacet
    * <em>Plugins Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.PluginsFacet
    * @generated
    */
   public Adapter createPluginsFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.FeaturesFacet
    * <em>Features Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.FeaturesFacet
    * @generated
    */
   public Adapter createFeaturesFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.SitesFacet
    * <em>Sites Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.SitesFacet
    * @generated
    */
   public Adapter createSitesFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.PluginProject
    * <em>Plugin Project</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.PluginProject
    * @generated
    */
   public Adapter createPluginProjectAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.FeatureProject
    * <em>Feature Project</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.FeatureProject
    * @generated
    */
   public Adapter createFeatureProjectAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.SiteProject
    * <em>Site Project</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.SiteProject
    * @generated
    */
   public Adapter createSiteProjectAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.Project <em>Project</em>}
    * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
    * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.Project
    * @generated
    */
   public Adapter createProjectAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.ProjectFacet
    * <em>Project Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.ProjectFacet
    * @generated
    */
   public Adapter createProjectFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.FileContainer
    * <em>File Container</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.FileContainer
    * @generated
    */
   public Adapter createFileContainerAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.Derivable
    * <em>Derivable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.Derivable
    * @generated
    */
   public Adapter createDerivableAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.PluginInclude
    * <em>Plugin Include</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.PluginInclude
    * @generated
    */
   public Adapter createPluginIncludeAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.Category
    * <em>Category</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
    * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.Category
    * @generated
    */
   public Adapter createCategoryAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.FeatureInclude
    * <em>Feature Include</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.FeatureInclude
    * @generated
    */
   public Adapter createFeatureIncludeAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.Classified
    * <em>Classified</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.Classified
    * @generated
    */
   public Adapter createClassifiedAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.Annotateable
    * <em>Annotateable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.Annotateable
    * @generated
    */
   public Adapter createAnnotateableAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.Annotation
    * <em>Annotation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.Annotation
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
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.Identifiable
    * <em>Identifiable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.Identifiable
    * @generated
    */
   public Adapter createIdentifiableAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.ProductsFacet
    * <em>Products Facet</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.ProductsFacet
    * @generated
    */
   public Adapter createProductsFacetAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.ProductDefinition
    * <em>Product Definition</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
    * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
    * end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.ProductDefinition
    * @generated
    */
   public Adapter createProductDefinitionAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.module.Reference
    * <em>Reference</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.module.Reference
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
