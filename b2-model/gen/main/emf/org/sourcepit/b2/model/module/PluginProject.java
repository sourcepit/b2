/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.model.module;

import org.sourcepit.common.manifest.osgi.BundleManifest;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Plugin Project</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.PluginProject#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.PluginProject#getBundleVersion <em>Bundle Version</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.PluginProject#isTestPlugin <em>Test Plugin</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.PluginProject#getFragmentHostSymbolicName <em>Fragment Host Symbolic Name
 * </em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.PluginProject#getFragmentHostVersion <em>Fragment Host Version</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.PluginProject#getBundleManifest <em>Bundle Manifest</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginProject()
 * @model
 * @generated
 */
public interface PluginProject extends Project
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference.
    * It is bidirectional and its opposite is '{@link org.sourcepit.b2.model.module.PluginsFacet#getProjects
    * <em>Projects</em>}'.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(PluginsFacet)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginProject_Parent()
    * @see org.sourcepit.b2.model.module.PluginsFacet#getProjects
    * @model opposite="projects" transient="false"
    * @generated
    */
   PluginsFacet getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.PluginProject#getParent <em>Parent</em>}' container
    * reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(PluginsFacet value);

   /**
    * Returns the value of the '<em><b>Bundle Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Bundle Version</em>' attribute isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Bundle Version</em>' attribute.
    * @see #setBundleVersion(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginProject_BundleVersion()
    * @model required="true"
    * @generated
    */
   String getBundleVersion();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.PluginProject#getBundleVersion
    * <em>Bundle Version</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Bundle Version</em>' attribute.
    * @see #getBundleVersion()
    * @generated
    */
   void setBundleVersion(String value);

   /**
    * Returns the value of the '<em><b>Test Plugin</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Test Plugin</em>' attribute isn't clear, there really should be more of a description
    * here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Test Plugin</em>' attribute.
    * @see #setTestPlugin(boolean)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginProject_TestPlugin()
    * @model
    * @generated
    */
   boolean isTestPlugin();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.PluginProject#isTestPlugin <em>Test Plugin</em>}'
    * attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Test Plugin</em>' attribute.
    * @see #isTestPlugin()
    * @generated
    */
   void setTestPlugin(boolean value);

   /**
    * Returns the value of the '<em><b>Fragment Host Symbolic Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Fragment Host Symbolic Name</em>' attribute isn't clear, there really should be more of
    * a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Fragment Host Symbolic Name</em>' attribute.
    * @see #setFragmentHostSymbolicName(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginProject_FragmentHostSymbolicName()
    * @model
    * @generated
    */
   String getFragmentHostSymbolicName();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.PluginProject#getFragmentHostSymbolicName
    * <em>Fragment Host Symbolic Name</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Fragment Host Symbolic Name</em>' attribute.
    * @see #getFragmentHostSymbolicName()
    * @generated
    */
   void setFragmentHostSymbolicName(String value);

   /**
    * Returns the value of the '<em><b>Fragment Host Version</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Fragment Host Version</em>' attribute isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Fragment Host Version</em>' attribute.
    * @see #setFragmentHostVersion(String)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginProject_FragmentHostVersion()
    * @model
    * @generated
    */
   String getFragmentHostVersion();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.PluginProject#getFragmentHostVersion
    * <em>Fragment Host Version</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Fragment Host Version</em>' attribute.
    * @see #getFragmentHostVersion()
    * @generated
    */
   void setFragmentHostVersion(String value);

   /**
    * Returns the value of the '<em><b>Bundle Manifest</b></em>' containment reference.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Bundle Manifest</em>' containment reference isn't clear, there really should be more of
    * a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Bundle Manifest</em>' containment reference.
    * @see #setBundleManifest(BundleManifest)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getPluginProject_BundleManifest()
    * @model containment="true" resolveProxies="true" required="true"
    * @generated
    */
   BundleManifest getBundleManifest();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.PluginProject#getBundleManifest
    * <em>Bundle Manifest</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Bundle Manifest</em>' containment reference.
    * @see #getBundleManifest()
    * @generated
    */
   void setBundleManifest(BundleManifest value);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @model kind="operation" required="true"
    * @generated
    */
   boolean isFragment();

} // PluginProject
