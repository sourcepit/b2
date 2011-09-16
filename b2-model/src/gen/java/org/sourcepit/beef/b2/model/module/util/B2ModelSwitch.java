/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.sourcepit.beef.b2.model.module.AbstractFacet;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.Annotateable;
import org.sourcepit.beef.b2.model.module.Annotation;
import org.sourcepit.beef.b2.model.module.B2ModelPackage;
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
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.sourcepit.beef.b2.model.module.B2ModelPackage
 * @generated
 */
public class B2ModelSwitch<T> extends Switch<T>
{
   /**
    * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected static B2ModelPackage modelPackage;

   /**
    * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public B2ModelSwitch()
   {
      if (modelPackage == null)
      {
         modelPackage = B2ModelPackage.eINSTANCE;
      }
   }

   /**
    * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @parameter ePackage the package in question.
    * @return whether this is a switch for the given package.
    * @generated
    */
   @Override
   protected boolean isSwitchFor(EPackage ePackage)
   {
      return ePackage == modelPackage;
   }

   /**
    * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the first non-null result returned by a <code>caseXXX</code> call.
    * @generated
    */
   @Override
   protected T doSwitch(int classifierID, EObject theEObject)
   {
      switch (classifierID)
      {
         case B2ModelPackage.ABSTRACT_MODULE :
         {
            AbstractModule abstractModule = (AbstractModule) theEObject;
            T result = caseAbstractModule(abstractModule);
            if (result == null)
               result = caseFileContainer(abstractModule);
            if (result == null)
               result = caseAnnotateable(abstractModule);
            if (result == null)
               result = caseIdentifiable(abstractModule);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.BASIC_MODULE :
         {
            BasicModule basicModule = (BasicModule) theEObject;
            T result = caseBasicModule(basicModule);
            if (result == null)
               result = caseAbstractModule(basicModule);
            if (result == null)
               result = caseFileContainer(basicModule);
            if (result == null)
               result = caseAnnotateable(basicModule);
            if (result == null)
               result = caseIdentifiable(basicModule);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.ABSTRACT_FACET :
         {
            AbstractFacet abstractFacet = (AbstractFacet) theEObject;
            T result = caseAbstractFacet(abstractFacet);
            if (result == null)
               result = caseDerivable(abstractFacet);
            if (result == null)
               result = caseAnnotateable(abstractFacet);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.COMPOSITE_MODULE :
         {
            CompositeModule compositeModule = (CompositeModule) theEObject;
            T result = caseCompositeModule(compositeModule);
            if (result == null)
               result = caseAbstractModule(compositeModule);
            if (result == null)
               result = caseFileContainer(compositeModule);
            if (result == null)
               result = caseAnnotateable(compositeModule);
            if (result == null)
               result = caseIdentifiable(compositeModule);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.PLUGINS_FACET :
         {
            PluginsFacet pluginsFacet = (PluginsFacet) theEObject;
            T result = casePluginsFacet(pluginsFacet);
            if (result == null)
               result = caseProjectFacet(pluginsFacet);
            if (result == null)
               result = caseAbstractFacet(pluginsFacet);
            if (result == null)
               result = caseDerivable(pluginsFacet);
            if (result == null)
               result = caseAnnotateable(pluginsFacet);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.FEATURES_FACET :
         {
            FeaturesFacet featuresFacet = (FeaturesFacet) theEObject;
            T result = caseFeaturesFacet(featuresFacet);
            if (result == null)
               result = caseProjectFacet(featuresFacet);
            if (result == null)
               result = caseAbstractFacet(featuresFacet);
            if (result == null)
               result = caseDerivable(featuresFacet);
            if (result == null)
               result = caseAnnotateable(featuresFacet);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.SITES_FACET :
         {
            SitesFacet sitesFacet = (SitesFacet) theEObject;
            T result = caseSitesFacet(sitesFacet);
            if (result == null)
               result = caseProjectFacet(sitesFacet);
            if (result == null)
               result = caseAbstractFacet(sitesFacet);
            if (result == null)
               result = caseDerivable(sitesFacet);
            if (result == null)
               result = caseAnnotateable(sitesFacet);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.PLUGIN_PROJECT :
         {
            PluginProject pluginProject = (PluginProject) theEObject;
            T result = casePluginProject(pluginProject);
            if (result == null)
               result = caseProject(pluginProject);
            if (result == null)
               result = caseFileContainer(pluginProject);
            if (result == null)
               result = caseDerivable(pluginProject);
            if (result == null)
               result = caseAnnotateable(pluginProject);
            if (result == null)
               result = caseIdentifiable(pluginProject);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.FEATURE_PROJECT :
         {
            FeatureProject featureProject = (FeatureProject) theEObject;
            T result = caseFeatureProject(featureProject);
            if (result == null)
               result = caseProject(featureProject);
            if (result == null)
               result = caseClassified(featureProject);
            if (result == null)
               result = caseFileContainer(featureProject);
            if (result == null)
               result = caseDerivable(featureProject);
            if (result == null)
               result = caseAnnotateable(featureProject);
            if (result == null)
               result = caseIdentifiable(featureProject);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.SITE_PROJECT :
         {
            SiteProject siteProject = (SiteProject) theEObject;
            T result = caseSiteProject(siteProject);
            if (result == null)
               result = caseProject(siteProject);
            if (result == null)
               result = caseClassified(siteProject);
            if (result == null)
               result = caseFileContainer(siteProject);
            if (result == null)
               result = caseDerivable(siteProject);
            if (result == null)
               result = caseAnnotateable(siteProject);
            if (result == null)
               result = caseIdentifiable(siteProject);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.PROJECT :
         {
            Project project = (Project) theEObject;
            T result = caseProject(project);
            if (result == null)
               result = caseFileContainer(project);
            if (result == null)
               result = caseDerivable(project);
            if (result == null)
               result = caseAnnotateable(project);
            if (result == null)
               result = caseIdentifiable(project);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.PROJECT_FACET :
         {
            ProjectFacet<?> projectFacet = (ProjectFacet<?>) theEObject;
            T result = caseProjectFacet(projectFacet);
            if (result == null)
               result = caseAbstractFacet(projectFacet);
            if (result == null)
               result = caseDerivable(projectFacet);
            if (result == null)
               result = caseAnnotateable(projectFacet);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.FILE_CONTAINER :
         {
            FileContainer fileContainer = (FileContainer) theEObject;
            T result = caseFileContainer(fileContainer);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.DERIVABLE :
         {
            Derivable derivable = (Derivable) theEObject;
            T result = caseDerivable(derivable);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.PLUGIN_INCLUDE :
         {
            PluginInclude pluginInclude = (PluginInclude) theEObject;
            T result = casePluginInclude(pluginInclude);
            if (result == null)
               result = caseReference(pluginInclude);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.CATEGORY :
         {
            Category category = (Category) theEObject;
            T result = caseCategory(category);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.FEATURE_INCLUDE :
         {
            FeatureInclude featureInclude = (FeatureInclude) theEObject;
            T result = caseFeatureInclude(featureInclude);
            if (result == null)
               result = caseReference(featureInclude);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.CLASSIFIED :
         {
            Classified classified = (Classified) theEObject;
            T result = caseClassified(classified);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.ANNOTATEABLE :
         {
            Annotateable annotateable = (Annotateable) theEObject;
            T result = caseAnnotateable(annotateable);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.ANNOTATION :
         {
            Annotation annotation = (Annotation) theEObject;
            T result = caseAnnotation(annotation);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.ESTRING_MAP_ENTRY :
         {
            @SuppressWarnings("unchecked")
            Map.Entry<String, String> eStringMapEntry = (Map.Entry<String, String>) theEObject;
            T result = caseEStringMapEntry(eStringMapEntry);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.IDENTIFIABLE :
         {
            Identifiable identifiable = (Identifiable) theEObject;
            T result = caseIdentifiable(identifiable);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.PRODUCTS_FACET :
         {
            ProductsFacet productsFacet = (ProductsFacet) theEObject;
            T result = caseProductsFacet(productsFacet);
            if (result == null)
               result = caseAbstractFacet(productsFacet);
            if (result == null)
               result = caseDerivable(productsFacet);
            if (result == null)
               result = caseAnnotateable(productsFacet);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.PRODUCT_DEFINITION :
         {
            ProductDefinition productDefinition = (ProductDefinition) theEObject;
            T result = caseProductDefinition(productDefinition);
            if (result == null)
               result = caseAnnotateable(productDefinition);
            if (result == null)
               result = caseDerivable(productDefinition);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case B2ModelPackage.REFERENCE :
         {
            Reference reference = (Reference) theEObject;
            T result = caseReference(reference);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         default :
            return defaultCase(theEObject);
      }
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Abstract Module</em>'. <!-- begin-user-doc
    * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Abstract Module</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseAbstractModule(AbstractModule object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Basic Module</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Basic Module</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseBasicModule(BasicModule object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Abstract Facet</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Abstract Facet</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseAbstractFacet(AbstractFacet object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Composite Module</em>'. <!-- begin-user-doc
    * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Composite Module</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseCompositeModule(CompositeModule object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Plugins Facet</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Plugins Facet</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T casePluginsFacet(PluginsFacet object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Features Facet</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Features Facet</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseFeaturesFacet(FeaturesFacet object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Sites Facet</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Sites Facet</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseSitesFacet(SitesFacet object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Plugin Project</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Plugin Project</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T casePluginProject(PluginProject object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Feature Project</em>'. <!-- begin-user-doc
    * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Feature Project</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseFeatureProject(FeatureProject object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Site Project</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Site Project</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseSiteProject(SiteProject object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Project</em>'. <!-- begin-user-doc --> This
    * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Project</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseProject(Project object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Project Facet</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Project Facet</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public <P extends Project> T caseProjectFacet(ProjectFacet<P> object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>File Container</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>File Container</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseFileContainer(FileContainer object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Derivable</em>'. <!-- begin-user-doc --> This
    * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Derivable</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseDerivable(Derivable object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Plugin Include</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Plugin Include</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T casePluginInclude(PluginInclude object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Category</em>'. <!-- begin-user-doc --> This
    * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Category</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseCategory(Category object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Feature Include</em>'. <!-- begin-user-doc
    * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Feature Include</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseFeatureInclude(FeatureInclude object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Classified</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Classified</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseClassified(Classified object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Annotateable</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Annotateable</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseAnnotateable(Annotateable object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Annotation</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Annotation</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseAnnotation(Annotation object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>EString Map Entry</em>'. <!-- begin-user-doc
    * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>EString Map Entry</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseEStringMapEntry(Map.Entry<String, String> object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Identifiable</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Identifiable</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseIdentifiable(Identifiable object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Products Facet</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Products Facet</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseProductsFacet(ProductsFacet object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Product Definition</em>'. <!-- begin-user-doc
    * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Product Definition</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseProductDefinition(ProductDefinition object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Reference</em>'. <!-- begin-user-doc --> This
    * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Reference</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseReference(Reference object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
    * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
    * anyway. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject)
    * @generated
    */
   @Override
   public T defaultCase(EObject object)
   {
      return null;
   }

} // B2ModelSwitch
