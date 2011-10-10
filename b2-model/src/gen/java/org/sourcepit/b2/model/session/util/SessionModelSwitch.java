/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.Environment;
import org.sourcepit.b2.model.session.ModuleAttachment;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelPackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.sourcepit.b2.model.session.SessionModelPackage
 * @generated
 */
public class SessionModelSwitch<T> extends Switch<T>
{
   /**
    * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected static SessionModelPackage modelPackage;

   /**
    * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SessionModelSwitch()
   {
      if (modelPackage == null)
      {
         modelPackage = SessionModelPackage.eINSTANCE;
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
         case SessionModelPackage.B2_SESSION :
         {
            B2Session b2Session = (B2Session) theEObject;
            T result = caseB2Session(b2Session);
            if (result == null)
               result = caseAnnotatable(b2Session);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case SessionModelPackage.MODULE_PROJECT :
         {
            ModuleProject moduleProject = (ModuleProject) theEObject;
            T result = caseModuleProject(moduleProject);
            if (result == null)
               result = caseAnnotatable(moduleProject);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case SessionModelPackage.MODULE_DEPENDENCY :
         {
            ModuleDependency moduleDependency = (ModuleDependency) theEObject;
            T result = caseModuleDependency(moduleDependency);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case SessionModelPackage.MODULE_ATTACHMENT :
         {
            ModuleAttachment moduleAttachment = (ModuleAttachment) theEObject;
            T result = caseModuleAttachment(moduleAttachment);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case SessionModelPackage.ENVIRONMENT :
         {
            Environment environment = (Environment) theEObject;
            T result = caseEnvironment(environment);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         default :
            return defaultCase(theEObject);
      }
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>B2 Session</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>B2 Session</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseB2Session(B2Session object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Module Project</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Module Project</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseModuleProject(ModuleProject object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Module Dependency</em>'. <!-- begin-user-doc
    * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Module Dependency</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseModuleDependency(ModuleDependency object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Module Attachment</em>'. <!-- begin-user-doc
    * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Module Attachment</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseModuleAttachment(ModuleAttachment object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Environment</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Environment</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseEnvironment(Environment object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Annotatable</em>'. <!-- begin-user-doc -->
    * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
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

} // SessionModelSwitch
