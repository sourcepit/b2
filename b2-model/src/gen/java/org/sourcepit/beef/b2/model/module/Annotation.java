/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Annotation</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.Annotation#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.Annotation#getSource <em>Source</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.Annotation#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getAnnotation()
 * @model
 * @generated
 */
public interface Annotation extends EObject
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.module.Annotateable#getAnnotations <em>Annotations</em>}'. <!--
    * begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(Annotateable)
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getAnnotation_Parent()
    * @see org.sourcepit.beef.b2.model.module.Annotateable#getAnnotations
    * @model opposite="annotations" required="true" transient="false"
    * @generated
    */
   Annotateable getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.Annotation#getParent <em>Parent</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(Annotateable value);

   /**
    * Returns the value of the '<em><b>Source</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Source</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Source</em>' attribute.
    * @see #setSource(String)
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getAnnotation_Source()
    * @model required="true"
    * @generated
    */
   String getSource();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.Annotation#getSource <em>Source</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Source</em>' attribute.
    * @see #getSource()
    * @generated
    */
   void setSource(String value);

   /**
    * Returns the value of the '<em><b>Entries</b></em>' map. The key is of type {@link java.lang.String}, and the value
    * is of type {@link java.lang.String}, <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Entries</em>' map isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Entries</em>' map.
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getAnnotation_Entries()
    * @model mapType=
    *        "org.sourcepit.beef.b2.internal.model.EStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
    * @generated
    */
   EMap<String, String> getEntries();

} // Annotation
