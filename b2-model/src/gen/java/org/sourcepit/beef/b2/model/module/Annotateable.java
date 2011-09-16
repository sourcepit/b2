/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Annotateable</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.Annotateable#getAnnotations <em>Annotations</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.ModulePackage#getAnnotateable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Annotateable extends EObject
{
   /**
    * Returns the value of the '<em><b>Annotations</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.beef.b2.model.module.Annotation}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.module.Annotation#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Annotations</em>' containment reference list isn't clear, there really should be more
    * of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Annotations</em>' containment reference list.
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getAnnotateable_Annotations()
    * @see org.sourcepit.beef.b2.model.module.Annotation#getParent
    * @model opposite="parent" containment="true"
    * @generated
    */
   EList<Annotation> getAnnotations();

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   Annotation getAnnotation(String source);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model
    * @generated
    */
   String getAnnotationEntry(String source, String key);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model required="true"
    * @generated
    */
   String putAnnotationEntry(String source, String key, String value);

} // Annotateable
