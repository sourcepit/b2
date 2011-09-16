/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;

import java.io.File;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>File Container</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.FileContainer#getDirectory <em>Directory</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.ModulePackage#getFileContainer()
 * @model abstract="true"
 * @generated
 */
public interface FileContainer extends EObject
{
   /**
    * Returns the value of the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Directory</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Directory</em>' attribute.
    * @see #setDirectory(File)
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getFileContainer_Directory()
    * @model dataType="org.sourcepit.beef.b2.model.common.EJavaFile"
    * @generated
    */
   File getDirectory();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.FileContainer#getDirectory <em>Directory</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Directory</em>' attribute.
    * @see #getDirectory()
    * @generated
    */
   void setDirectory(File value);

} // FileContainer
