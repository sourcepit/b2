/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.internal.model;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Feature Project</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.internal.model.FeatureProject#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.internal.model.FeatureProject#getIncludedPlugins <em>Included Plugins</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.internal.model.FeatureProject#getIncludedFeatures <em>Included Features</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getFeatureProject()
 * @model
 * @generated
 */
public interface FeatureProject extends Project, Classified
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.internal.model.FeaturesFacet#getProjects <em>Projects</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(FeaturesFacet)
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getFeatureProject_Parent()
    * @see org.sourcepit.beef.b2.internal.model.FeaturesFacet#getProjects
    * @model opposite="projects" transient="false"
    * @generated
    */
   FeaturesFacet getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.internal.model.FeatureProject#getParent <em>Parent</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(FeaturesFacet value);

   /**
    * Returns the value of the '<em><b>Included Plugins</b></em>' containment reference list. The list contents are of
    * type {@link org.sourcepit.beef.b2.internal.model.PluginInclude}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.internal.model.PluginInclude#getFeatureProject <em>Feature Project</em>}'. <!--
    * begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Included Plugins</em>' containment reference list isn't clear, there really should be
    * more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Included Plugins</em>' containment reference list.
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getFeatureProject_IncludedPlugins()
    * @see org.sourcepit.beef.b2.internal.model.PluginInclude#getFeatureProject
    * @model opposite="featureProject" containment="true"
    * @generated
    */
   EList<PluginInclude> getIncludedPlugins();

   /**
    * Returns the value of the '<em><b>Included Features</b></em>' containment reference list. The list contents are of
    * type {@link org.sourcepit.beef.b2.internal.model.FeatureInclude}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.internal.model.FeatureInclude#getFeatureProject <em>Feature Project</em>}'. <!--
    * begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Included Features</em>' containment reference list isn't clear, there really should be
    * more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Included Features</em>' containment reference list.
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getFeatureProject_IncludedFeatures()
    * @see org.sourcepit.beef.b2.internal.model.FeatureInclude#getFeatureProject
    * @model opposite="featureProject" containment="true"
    * @generated
    */
   EList<FeatureInclude> getIncludedFeatures();

} // FeatureProject
