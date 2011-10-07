/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.b2.model.session;

import java.io.File;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Attachment</b></em>'. <!-- end-user-doc
 * -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.session.ModuleAttachment#getClassifier <em>Classifier</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.ModuleAttachment#getType <em>Type</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.ModuleAttachment#getFile <em>File</em>}</li>
 * <li>{@link org.sourcepit.b2.model.session.ModuleAttachment#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.session.SessionModelPackage#getModuleAttachment()
 * @model
 * @generated
 */
public interface ModuleAttachment extends EObject
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
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getModuleAttachment_Classifier()
    * @model
    * @generated
    */
   String getClassifier();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.ModuleAttachment#getClassifier <em>Classifier</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Classifier</em>' attribute.
    * @see #getClassifier()
    * @generated
    */
   void setClassifier(String value);

   /**
    * Returns the value of the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Type</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Type</em>' attribute.
    * @see #setType(String)
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getModuleAttachment_Type()
    * @model required="true"
    * @generated
    */
   String getType();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.ModuleAttachment#getType <em>Type</em>}' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Type</em>' attribute.
    * @see #getType()
    * @generated
    */
   void setType(String value);

   /**
    * Returns the value of the '<em><b>File</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>File</em>' attribute isn't clear, there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>File</em>' attribute.
    * @see #setFile(File)
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getModuleAttachment_File()
    * @model dataType="org.sourcepit.b2.model.common.EJavaFile" required="true"
    * @generated
    */
   File getFile();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.ModuleAttachment#getFile <em>File</em>}' attribute.
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>File</em>' attribute.
    * @see #getFile()
    * @generated
    */
   void setFile(File value);

   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.b2.model.session.ModuleProject#getAttachments <em>Attachments</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(ModuleProject)
    * @see org.sourcepit.b2.model.session.SessionModelPackage#getModuleAttachment_Parent()
    * @see org.sourcepit.b2.model.session.ModuleProject#getAttachments
    * @model opposite="attachments" required="true" transient="false"
    * @generated
    */
   ModuleProject getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.session.ModuleAttachment#getParent <em>Parent</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(ModuleProject value);

} // ModuleAttachment
