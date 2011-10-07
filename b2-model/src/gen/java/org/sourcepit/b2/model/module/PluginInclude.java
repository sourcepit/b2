/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.b2.model.module;


/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Plugin Include</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.PluginInclude#isUnpack <em>Unpack</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.PluginInclude#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginInclude()
 * @model
 * @generated
 */
public interface PluginInclude extends Reference
{
   /**
    * Returns the value of the '<em><b>Unpack</b></em>' attribute. The default value is <code>"false"</code>. <!--
    * begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Unpack</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Unpack</em>' attribute.
    * @see #setUnpack(boolean)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginInclude_Unpack()
    * @model default="false"
    * @generated
    */
   boolean isUnpack();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.PluginInclude#isUnpack <em>Unpack</em>}'
    * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Unpack</em>' attribute.
    * @see #isUnpack()
    * @generated
    */
   void setUnpack(boolean value);

   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.b2.model.module.FeatureProject#getIncludedPlugins <em>Included Plugins</em>}'. <!--
    * begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(FeatureProject)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginInclude_Parent()
    * @see org.sourcepit.b2.model.module.FeatureProject#getIncludedPlugins
    * @model opposite="includedPlugins" required="true" transient="false"
    * @generated
    */
   FeatureProject getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.PluginInclude#getParent <em>Parent</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(FeatureProject value);

} // PluginInclude
