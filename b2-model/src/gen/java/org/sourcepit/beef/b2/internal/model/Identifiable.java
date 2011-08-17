/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.internal.model;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.model.util.Identifier;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Identifiably</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.internal.model.Identifiable#getId <em>Id</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.internal.model.Identifiable#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getIdentifiable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Identifiable extends EObject
{
   /**
    * Returns the value of the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Id</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Id</em>' attribute.
    * @see #setId(String)
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getIdentifiable_Id()
    * @model
    * @generated
    */
   String getId();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.internal.model.Identifiable#getId <em>Id</em>}' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Id</em>' attribute.
    * @see #getId()
    * @generated
    */
   void setId(String value);

   /**
    * Returns the value of the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Version</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Version</em>' attribute.
    * @see #setVersion(String)
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getIdentifiable_Version()
    * @model
    * @generated
    */
   String getVersion();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.internal.model.Identifiable#getVersion <em>Version</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Version</em>' attribute.
    * @see #getVersion()
    * @generated
    */
   void setVersion(String value);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model required="true" identifierDataType="org.sourcepit.beef.b2.internal.model.Identifier"
    * @generated
    */
   boolean isIdentifyableBy(Identifier identifier);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model dataType="org.sourcepit.beef.b2.internal.model.Identifier" required="true"
    * @generated
    */
   Identifier toIdentifier();

} // Identifiably
