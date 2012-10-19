/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.manifest.osgi.BundleManifest;
import org.sourcepit.common.manifest.osgi.BundleManifestFactory;
import org.sourcepit.common.manifest.osgi.resource.BundleManifestResourceImpl;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ExecutionEnvironmentConstraintTest
{
   private Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = new Workspace(new File(env.getBuildDir(), "test-ws"), false);

   private RecordingLogger logger;

   private ExecutionEnvironmentConstraint constraint;

   @Before
   public void setUp()
   {
      logger = new RecordingLogger();
      constraint = new ExecutionEnvironmentConstraint(logger);
   }

   @Test
   public void testEmptyB2Properties() throws Exception
   {
      final PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.setDirectory(ws.getRoot());
      project.setId("org.sourcepit.foo.plugin");
      project.setBundleManifest(BundleManifestFactory.eINSTANCE.createBundleManifest());
      addJavaNature(project.getDirectory());

      final PropertiesMap properties = new LinkedPropertiesMap();

      constraint.validate(project, properties, false);

      assertTrue(logger.getMessages().isEmpty());
      assertEquals(1, project.getDirectory().list().length);
   }

   @Test
   public void testDefaultEE_NoJavaNature() throws Exception
   {
      final PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.setDirectory(ws.getRoot());
      project.setId("org.sourcepit.foo.plugin");
      project.setBundleManifest(BundleManifestFactory.eINSTANCE.createBundleManifest());

      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("b2.executionEnvironment", "JavaSE-1.6");

      constraint.validate(project, properties, false);

      assertEquals(0, logger.getMessages().size());
      assertEquals(0, project.getDirectory().list().length);

      logger.getMessages().clear();

      constraint.validate(project, properties, true);

      assertEquals(0, logger.getMessages().size());
      assertEquals(0, project.getDirectory().list().length);
   }

   @Test
   public void testDefaultEE_NoJDTSettings() throws Exception
   {
      final PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.setDirectory(ws.getRoot());
      project.setId("org.sourcepit.foo.plugin");
      project.setBundleManifest(BundleManifestFactory.eINSTANCE.createBundleManifest());
      addJavaNature(project.getDirectory());

      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("b2.executionEnvironment", "JavaSE-1.6");

      constraint.validate(project, properties, false);

      assertEquals(5, logger.getMessages().size());
      assertEquals(1, project.getDirectory().list().length);

      logger.getMessages().clear();

      constraint.validate(project, properties, true);

      assertEquals(5, logger.getMessages().size());
      assertEquals(4, project.getDirectory().list().length);

      final File cpFile = new File(project.getDirectory(), ".classpath");
      assertTrue(cpFile.exists());

      final Document cpDoc = XmlUtils.readXml(cpFile);

      Element eeNode = (Element) XmlUtils.queryNode(cpDoc,
         "/classpath/classpathentry[@kind='con' and starts-with(@path,'org.eclipse.jdt.launching.JRE_CONTAINER/')]");
      assertEquals(
         "org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.6",
         eeNode.getAttribute("path"));

      Resource eResource = new BundleManifestResourceImpl(URI.createFileURI(new File(project.getDirectory(),
         "META-INF/MANIFEST.MF").getAbsolutePath()));
      eResource.load(null);

      BundleManifest mf = (BundleManifest) eResource.getContents().get(0);
      assertEquals("JavaSE-1.6", mf.getBundleRequiredExecutionEnvironment().get(0));
   }

   @Test
   public void testDefaultEE_WithJDTSettings() throws Exception
   {
      final PluginProject project = ModuleModelFactory.eINSTANCE.createPluginProject();
      project.setDirectory(ws.getRoot());
      project.setId("org.sourcepit.foo.plugin");
      project.setBundleManifest(BundleManifestFactory.eINSTANCE.createBundleManifest());
      addJavaNature(project.getDirectory());

      final File cpFile = new File(project.getDirectory(), ".classpath");

      Document cpDoc = XmlUtils.newDocument();
      Node cpNode = cpDoc.createElement("classpath");
      cpDoc.appendChild(cpNode);
      Element eeNode = cpDoc.createElement("classpathentry");
      eeNode.setAttribute("kind", "con");
      eeNode.setAttribute("path",
         "org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/"
            + "J2SE-1.6");
      cpNode.appendChild(eeNode);
      XmlUtils.writeXml(cpDoc, cpFile);

      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("b2.executionEnvironment", "JavaSE-1.6");
      properties.put("b2.executionEnvironment." + project.getId(), "J2SE-1.4");

      constraint.validate(project, properties, false);

      assertEquals(5, logger.getMessages().size());
      assertEquals(2, project.getDirectory().list().length);

      logger.getMessages().clear();

      constraint.validate(project, properties, true);

      assertEquals(5, logger.getMessages().size());
      assertEquals(4, project.getDirectory().list().length);

      cpDoc = XmlUtils.readXml(cpFile);

      eeNode = (Element) XmlUtils
         .queryNode(
            cpDoc,
            "/classpath/classpathentry[@kind='con' and starts-with(@path,'org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/')]");
      assertEquals(
         "org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/J2SE-1.4",
         eeNode.getAttribute("path"));

      Resource eResource = new BundleManifestResourceImpl(URI.createFileURI(new File(project.getDirectory(),
         "META-INF/MANIFEST.MF").getAbsolutePath()));
      eResource.load(null);

      BundleManifest mf = (BundleManifest) eResource.getContents().get(0);
      assertEquals("J2SE-1.4", mf.getBundleRequiredExecutionEnvironment().get(0));
   }

   private static void addJavaNature(File directory)
   {
      Document doc = XmlUtils.newDocument();
      Node projectDescription = doc.createElement("projectDescription");
      doc.appendChild(projectDescription);

      Element natures = doc.createElement("natures");
      projectDescription.appendChild(natures);

      Element nature = doc.createElement("nature");
      nature.setTextContent("org.eclipse.jdt.core.javanature");
      natures.appendChild(nature);

      XmlUtils.writeXml(doc, new File(directory, ".project"));
   }
}
