/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.b2.model.module;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.util.Identifier;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Reference</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.Reference#getId <em>Id</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.Reference#getVersionRange <em>Version Range</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getReference()
 * @model
 * @generated
 */
public interface Reference extends EObject
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
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getReference_Id()
    * @model required="true"
    * @generated
    */
   String getId();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.Reference#getId <em>Id</em>}' attribute. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Id</em>' attribute.
    * @see #getId()
    * @generated
    */
   void setId(String value);

   /**
    * Returns the value of the '<em><b>Version Range</b></em>' attribute. The default value is <code>"0.0.0"</code>.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Version</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Version Range</em>' attribute.
    * @see #setVersionRange(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getReference_VersionRange()
    * @model default="0.0.0"
    * @generated
    */
   String getVersionRange();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.Reference#getVersionRange <em>Version Range</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Version Range</em>' attribute.
    * @see #getVersionRange()
    * @generated
    */
   void setVersionRange(String value);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model required="true" identifierDataType="org.sourcepit.b2.model.module.Identifier"
    * @generated
    */
   boolean isSatisfiableBy(Identifier identifier);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   void setStrictVersion(String version);

} // Reference
