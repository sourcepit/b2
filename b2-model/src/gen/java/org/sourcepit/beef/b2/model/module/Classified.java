/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Classified</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.Classified#getClassifier <em>Classifier</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getClassified()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Classified extends EObject
{
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
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getClassified_Classifier()
    * @model
    * @generated
    */
   String getClassifier();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.Classified#getClassifier <em>Classifier</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Classifier</em>' attribute.
    * @see #getClassifier()
    * @generated
    */
   void setClassifier(String value);

} // Classified
