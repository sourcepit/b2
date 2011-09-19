/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.sourcepit.beef.b2.model.common.Annotateable;
import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.b2.model.session.Session;
import org.sourcepit.beef.b2.model.session.SessionModelPackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.sourcepit.beef.b2.model.session.SessionModelPackage
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
         case SessionModelPackage.SESSION :
         {
            Session session = (Session) theEObject;
            T result = caseSession(session);
            if (result == null)
               result = caseAnnotateable(session);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case SessionModelPackage.MODULE_PROJECT :
         {
            ModuleProject moduleProject = (ModuleProject) theEObject;
            T result = caseModuleProject(moduleProject);
            if (result == null)
               result = caseAnnotateable(moduleProject);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         default :
            return defaultCase(theEObject);
      }
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Session</em>'. <!-- begin-user-doc --> This
    * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
    * 
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Session</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseSession(Session object)
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
