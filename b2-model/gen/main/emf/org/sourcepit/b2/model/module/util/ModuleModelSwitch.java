/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.model.module.AbstractIdentifiable;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.FileContainer;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.modeling.Annotatable;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each
 * class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.sourcepit.b2.model.module.ModuleModelPackage
 * @generated
 */
public class ModuleModelSwitch<T> extends Switch<T>
{
   /**
    * The cached model package
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected static ModuleModelPackage modelPackage;

   /**
    * Creates an instance of the switch.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public ModuleModelSwitch()
   {
      if (modelPackage == null)
      {
         modelPackage = ModuleModelPackage.eINSTANCE;
      }
   }

   /**
    * Checks whether this is a switch for the given package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
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
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the first non-null result returned by a <code>caseXXX</code> call.
    * @generated
    */
   @Override
   protected T doSwitch(int classifierID, EObject theEObject)
   {
      switch (classifierID)
      {
         case ModuleModelPackage.ABSTRACT_MODULE:
         {
            AbstractModule abstractModule = (AbstractModule)theEObject;
            T result = caseAbstractModule(abstractModule);
            if (result == null) result = caseFileContainer(abstractModule);
            if (result == null) result = caseAnnotatable(abstractModule);
            if (result == null) result = caseAbstractIdentifiable(abstractModule);
            if (result == null) result = caseIdentifiable(abstractModule);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.BASIC_MODULE:
         {
            BasicModule basicModule = (BasicModule)theEObject;
            T result = caseBasicModule(basicModule);
            if (result == null) result = caseAbstractModule(basicModule);
            if (result == null) result = caseFileContainer(basicModule);
            if (result == null) result = caseAnnotatable(basicModule);
            if (result == null) result = caseAbstractIdentifiable(basicModule);
            if (result == null) result = caseIdentifiable(basicModule);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.ABSTRACT_FACET:
         {
            AbstractFacet abstractFacet = (AbstractFacet)theEObject;
            T result = caseAbstractFacet(abstractFacet);
            if (result == null) result = caseDerivable(abstractFacet);
            if (result == null) result = caseAnnotatable(abstractFacet);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.COMPOSITE_MODULE:
         {
            CompositeModule compositeModule = (CompositeModule)theEObject;
            T result = caseCompositeModule(compositeModule);
            if (result == null) result = caseAbstractModule(compositeModule);
            if (result == null) result = caseFileContainer(compositeModule);
            if (result == null) result = caseAnnotatable(compositeModule);
            if (result == null) result = caseAbstractIdentifiable(compositeModule);
            if (result == null) result = caseIdentifiable(compositeModule);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.PLUGINS_FACET:
         {
            PluginsFacet pluginsFacet = (PluginsFacet)theEObject;
            T result = casePluginsFacet(pluginsFacet);
            if (result == null) result = caseProjectFacet(pluginsFacet);
            if (result == null) result = caseAbstractFacet(pluginsFacet);
            if (result == null) result = caseDerivable(pluginsFacet);
            if (result == null) result = caseAnnotatable(pluginsFacet);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.FEATURES_FACET:
         {
            FeaturesFacet featuresFacet = (FeaturesFacet)theEObject;
            T result = caseFeaturesFacet(featuresFacet);
            if (result == null) result = caseProjectFacet(featuresFacet);
            if (result == null) result = caseAbstractFacet(featuresFacet);
            if (result == null) result = caseDerivable(featuresFacet);
            if (result == null) result = caseAnnotatable(featuresFacet);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.SITES_FACET:
         {
            SitesFacet sitesFacet = (SitesFacet)theEObject;
            T result = caseSitesFacet(sitesFacet);
            if (result == null) result = caseProjectFacet(sitesFacet);
            if (result == null) result = caseAbstractFacet(sitesFacet);
            if (result == null) result = caseDerivable(sitesFacet);
            if (result == null) result = caseAnnotatable(sitesFacet);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.PLUGIN_PROJECT:
         {
            PluginProject pluginProject = (PluginProject)theEObject;
            T result = casePluginProject(pluginProject);
            if (result == null) result = caseProject(pluginProject);
            if (result == null) result = caseFileContainer(pluginProject);
            if (result == null) result = caseDerivable(pluginProject);
            if (result == null) result = caseAnnotatable(pluginProject);
            if (result == null) result = caseAbstractIdentifiable(pluginProject);
            if (result == null) result = caseIdentifiable(pluginProject);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.FEATURE_PROJECT:
         {
            FeatureProject featureProject = (FeatureProject)theEObject;
            T result = caseFeatureProject(featureProject);
            if (result == null) result = caseProject(featureProject);
            if (result == null) result = caseFileContainer(featureProject);
            if (result == null) result = caseDerivable(featureProject);
            if (result == null) result = caseAnnotatable(featureProject);
            if (result == null) result = caseAbstractIdentifiable(featureProject);
            if (result == null) result = caseIdentifiable(featureProject);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.SITE_PROJECT:
         {
            SiteProject siteProject = (SiteProject)theEObject;
            T result = caseSiteProject(siteProject);
            if (result == null) result = caseProject(siteProject);
            if (result == null) result = caseFileContainer(siteProject);
            if (result == null) result = caseDerivable(siteProject);
            if (result == null) result = caseAnnotatable(siteProject);
            if (result == null) result = caseAbstractIdentifiable(siteProject);
            if (result == null) result = caseIdentifiable(siteProject);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.PROJECT:
         {
            Project project = (Project)theEObject;
            T result = caseProject(project);
            if (result == null) result = caseFileContainer(project);
            if (result == null) result = caseDerivable(project);
            if (result == null) result = caseAnnotatable(project);
            if (result == null) result = caseAbstractIdentifiable(project);
            if (result == null) result = caseIdentifiable(project);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.PROJECT_FACET:
         {
            ProjectFacet<?> projectFacet = (ProjectFacet<?>)theEObject;
            T result = caseProjectFacet(projectFacet);
            if (result == null) result = caseAbstractFacet(projectFacet);
            if (result == null) result = caseDerivable(projectFacet);
            if (result == null) result = caseAnnotatable(projectFacet);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.FILE_CONTAINER:
         {
            FileContainer fileContainer = (FileContainer)theEObject;
            T result = caseFileContainer(fileContainer);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.DERIVABLE:
         {
            Derivable derivable = (Derivable)theEObject;
            T result = caseDerivable(derivable);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.PLUGIN_INCLUDE:
         {
            PluginInclude pluginInclude = (PluginInclude)theEObject;
            T result = casePluginInclude(pluginInclude);
            if (result == null) result = caseAbstractStrictReference(pluginInclude);
            if (result == null) result = caseAbstractReference(pluginInclude);
            if (result == null) result = caseAnnotatable(pluginInclude);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.CATEGORY:
         {
            Category category = (Category)theEObject;
            T result = caseCategory(category);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.ABSTRACT_IDENTIFIABLE:
         {
            AbstractIdentifiable abstractIdentifiable = (AbstractIdentifiable)theEObject;
            T result = caseAbstractIdentifiable(abstractIdentifiable);
            if (result == null) result = caseIdentifiable(abstractIdentifiable);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.PRODUCTS_FACET:
         {
            ProductsFacet productsFacet = (ProductsFacet)theEObject;
            T result = caseProductsFacet(productsFacet);
            if (result == null) result = caseAbstractFacet(productsFacet);
            if (result == null) result = caseDerivable(productsFacet);
            if (result == null) result = caseAnnotatable(productsFacet);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.PRODUCT_DEFINITION:
         {
            ProductDefinition productDefinition = (ProductDefinition)theEObject;
            T result = caseProductDefinition(productDefinition);
            if (result == null) result = caseAnnotatable(productDefinition);
            if (result == null) result = caseDerivable(productDefinition);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.ABSTRACT_REFERENCE:
         {
            AbstractReference abstractReference = (AbstractReference)theEObject;
            T result = caseAbstractReference(abstractReference);
            if (result == null) result = caseAnnotatable(abstractReference);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.RULED_REFERENCE:
         {
            RuledReference ruledReference = (RuledReference)theEObject;
            T result = caseRuledReference(ruledReference);
            if (result == null) result = caseAbstractReference(ruledReference);
            if (result == null) result = caseAnnotatable(ruledReference);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.ABSTRACT_STRICT_REFERENCE:
         {
            AbstractStrictReference abstractStrictReference = (AbstractStrictReference)theEObject;
            T result = caseAbstractStrictReference(abstractStrictReference);
            if (result == null) result = caseAbstractReference(abstractStrictReference);
            if (result == null) result = caseAnnotatable(abstractStrictReference);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.STRICT_REFERENCE:
         {
            StrictReference strictReference = (StrictReference)theEObject;
            T result = caseStrictReference(strictReference);
            if (result == null) result = caseAbstractStrictReference(strictReference);
            if (result == null) result = caseAbstractReference(strictReference);
            if (result == null) result = caseAnnotatable(strictReference);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.FEATURE_INCLUDE:
         {
            FeatureInclude featureInclude = (FeatureInclude)theEObject;
            T result = caseFeatureInclude(featureInclude);
            if (result == null) result = caseAbstractStrictReference(featureInclude);
            if (result == null) result = caseAbstractReference(featureInclude);
            if (result == null) result = caseAnnotatable(featureInclude);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         case ModuleModelPackage.IDENTIFIABLE:
         {
            Identifiable identifiable = (Identifiable)theEObject;
            T result = caseIdentifiable(identifiable);
            if (result == null) result = defaultCase(theEObject);
            return result;
         }
         default: return defaultCase(theEObject);
      }
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Abstract Module</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Basic Module</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Abstract Facet</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Composite Module</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Plugins Facet</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Features Facet</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Sites Facet</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Plugin Project</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Feature Project</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Site Project</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Project</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Project Facet</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>File Container</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Derivable</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Plugin Include</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Category</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Abstract Identifiable</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Abstract Identifiable</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseAbstractIdentifiable(AbstractIdentifiable object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Products Facet</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Product Definition</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Abstract Reference</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Abstract Reference</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseAbstractReference(AbstractReference object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Ruled Reference</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Ruled Reference</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseRuledReference(RuledReference object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Abstract Strict Reference</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Abstract Strict Reference</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseAbstractStrictReference(AbstractStrictReference object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Strict Reference</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Strict Reference</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseStrictReference(StrictReference object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Feature Include</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Identifiable</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
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
    * Returns the result of interpreting the object as an instance of '<em>Annotatable</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Annotatable</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseAnnotatable(Annotatable object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch, but this is the last case anyway.
    * <!-- end-user-doc -->
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

} // ModuleModelSwitch
