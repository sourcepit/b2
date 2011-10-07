/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.b2.model.session;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Dependency</b></em>'. <!-- end-user-doc
 * -->
 * 
 * 
 * @see org.sourcepit.b2.model.session.SessionModelPackage#getModuleDependency()
 * @model
 * @generated
 */
public interface ModuleDependency extends EObject
{

   /**
    * Returns the value of the '<em><b>Group Id</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Group Id</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Group Id</em>' attribute.
    * @see #setGroupId(String)
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getModuleDependency_GroupId()
    * @model required="true"
    * @generated
    */
   String getGroupId();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.ModuleDependency#getGroupId <em>Group Id</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Group Id</em>' attribute.
    * @see #getGroupId()
    * @generated
    */
   void setGroupId(String value);

   /**
    * Returns the value of the '<em><b>Artifact Id</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Artifact Id</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Artifact Id</em>' attribute.
    * @see #setArtifactId(String)
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getModuleDependency_ArtifactId()
    * @model required="true"
    * @generated
    */
   String getArtifactId();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.ModuleDependency#getArtifactId <em>Artifact Id</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Artifact Id</em>' attribute.
    * @see #getArtifactId()
    * @generated
    */
   void setArtifactId(String value);

   /**
    * Returns the value of the '<em><b>Version Range</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Version Range</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Version Range</em>' attribute.
    * @see #setVersionRange(String)
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getModuleDependency_VersionRange()
    * @model required="true"
    * @generated
    */
   String getVersionRange();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.ModuleDependency#getVersionRange
    * <em>Version Range</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Version Range</em>' attribute.
    * @see #getVersionRange()
    * @generated
    */
   void setVersionRange(String value);

   /**
    * Returns the value of the '<em><b>Classifier</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Classifier</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Classifier</em>' attribute.
    * @see #setClassifier(String)
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getModuleDependency_Classifier()
    * @model
    * @generated
    */
   String getClassifier();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.ModuleDependency#getClassifier <em>Classifier</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Classifier</em>' attribute.
    * @see #getClassifier()
    * @generated
    */
   void setClassifier(String value);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   boolean isSatisfiableBy(ModuleProject moduleProject);
} // ModuleDependency
