/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;


/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Plugin Project</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.PluginProject#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.PluginProject#getBundleVersion <em>Bundle Version</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.PluginProject#isTestPlugin <em>Test Plugin</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.PluginProject#getFragmentHostSymbolicName <em>Fragment Host Symbolic
 * Name</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.module.PluginProject#getFragmentHostVersion <em>Fragment Host Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getPluginProject()
 * @model
 * @generated
 */
public interface PluginProject extends Project
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.module.PluginsFacet#getProjects <em>Projects</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(PluginsFacet)
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getPluginProject_Parent()
    * @see org.sourcepit.beef.b2.model.module.PluginsFacet#getProjects
    * @model opposite="projects" transient="false"
    * @generated
    */
   PluginsFacet getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.PluginProject#getParent <em>Parent</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(PluginsFacet value);

   /**
    * Returns the value of the '<em><b>Bundle Version</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Bundle Version</em>' attribute isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Bundle Version</em>' attribute.
    * @see #setBundleVersion(String)
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getPluginProject_BundleVersion()
    * @model required="true"
    * @generated
    */
   String getBundleVersion();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.PluginProject#getBundleVersion
    * <em>Bundle Version</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Bundle Version</em>' attribute.
    * @see #getBundleVersion()
    * @generated
    */
   void setBundleVersion(String value);

   /**
    * Returns the value of the '<em><b>Test Plugin</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Test Plugin</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Test Plugin</em>' attribute.
    * @see #setTestPlugin(boolean)
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getPluginProject_TestPlugin()
    * @model
    * @generated
    */
   boolean isTestPlugin();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.PluginProject#isTestPlugin
    * <em>Test Plugin</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Test Plugin</em>' attribute.
    * @see #isTestPlugin()
    * @generated
    */
   void setTestPlugin(boolean value);

   /**
    * Returns the value of the '<em><b>Fragment Host Symbolic Name</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Fragment Host Symbolic Name</em>' attribute isn't clear, there really should be more of
    * a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Fragment Host Symbolic Name</em>' attribute.
    * @see #setFragmentHostSymbolicName(String)
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getPluginProject_FragmentHostSymbolicName()
    * @model
    * @generated
    */
   String getFragmentHostSymbolicName();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.PluginProject#getFragmentHostSymbolicName
    * <em>Fragment Host Symbolic Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Fragment Host Symbolic Name</em>' attribute.
    * @see #getFragmentHostSymbolicName()
    * @generated
    */
   void setFragmentHostSymbolicName(String value);

   /**
    * Returns the value of the '<em><b>Fragment Host Version</b></em>' attribute. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Fragment Host Version</em>' attribute isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Fragment Host Version</em>' attribute.
    * @see #setFragmentHostVersion(String)
    * @see org.sourcepit.beef.b2.model.module.B2ModelPackage#getPluginProject_FragmentHostVersion()
    * @model
    * @generated
    */
   String getFragmentHostVersion();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.PluginProject#getFragmentHostVersion
    * <em>Fragment Host Version</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Fragment Host Version</em>' attribute.
    * @see #getFragmentHostVersion()
    * @generated
    */
   void setFragmentHostVersion(String value);

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @model kind="operation" required="true"
    * @generated
    */
   boolean isFragment();

} // PluginProject
