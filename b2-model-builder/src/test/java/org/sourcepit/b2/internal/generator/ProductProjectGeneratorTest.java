/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.sonatype.guice.bean.containers.InjectedTest;
import org.sourcepit.b2.execution.B2;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Node;

import com.google.inject.Binder;

public class ProductProjectGeneratorTest extends InjectedTest
{
   protected Environment environment = Environment.get("env-test.properties");

   @Rule
   public Workspace workspace = new Workspace(new File(environment.getBuildDir(), "test-ws"), false);

   @Inject
   private LayoutManager layoutManager;

   @Inject
   private B2 b2;

   @Inject
   private B2SessionService sessionService;

   @Test
   public void testGetClassifier() throws Exception
   {
      assertEquals("bar", ProductProjectGenerator.getAssemblyClassifier("foo-bar.txt"));
      assertEquals("", ProductProjectGenerator.getAssemblyClassifier("foo.txt"));
      assertEquals("", ProductProjectGenerator.getAssemblyClassifier("foo-.txt"));
      assertEquals("bar", ProductProjectGenerator.getAssemblyClassifier("foo-bar"));
      assertEquals("", ProductProjectGenerator.getAssemblyClassifier("foo"));
   }

   @Override
   public void configure(Binder binder)
   {
      super.configure(binder);
      binder.bind(Logger.class).toInstance(new ConsoleLogger());
   }

   @Test
   public void testGenProduct_Bug37() throws Exception
   {
      final File moduleDir = getResource("ProductTest");

      // TODO automate init of b2 session in an abstract test class
      ModuleProject moduleProject = SessionModelFactory.eINSTANCE.createModuleProject();
      moduleProject.setDirectory(moduleDir);
      moduleProject.setGroupId("org.sourcepit.b2.its");
      moduleProject.setArtifactId("ProductTest");
      moduleProject.setVersion("1.0.0-SNAPSHOT");
      B2Session session = SessionModelFactory.eINSTANCE.createB2Session();
      session.getProjects().add(moduleProject);
      session.setCurrentProject(moduleProject);
      sessionService.setCurrentSession(session);
      sessionService.setCurrentResourceSet(new ResourceSetImpl());

      File productFile = new File(moduleDir, "bundle.a/bundle.a.product");
      Node features = XmlUtils.queryNode(XmlUtils.readXml(productFile), "/product/features");
      assertNull(features);

      B2Request request = new B2Request();
      request.setModuleDirectory(moduleDir);
      request.setInterpolate(true);
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setTemplates(new DefaultTemplateCopier());

      // build model and generate
      final AbstractModule module = b2.generate(request);
      assertNotNull(module);

      EList<ProductsFacet> productsFacets = module.getFacets(ProductsFacet.class);
      assertThat(productsFacets.size(), Is.is(1));

      ProductsFacet productsFacet = productsFacets.get(0);
      EList<ProductDefinition> productDefinitions = productsFacet.getProductDefinitions();
      assertThat(productDefinitions.size(), Is.is(1));

      ProductDefinition productDefinition = productDefinitions.get(0);
      final String uid = productDefinition.getAnnotationEntry("product", "uid");
      assertNotNull(uid);

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      final File projectDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
      assertTrue(projectDir.exists());

      productFile = new File(projectDir, "bundle.a.product");
      features = XmlUtils.queryNode(XmlUtils.readXml(productFile), "/product/features");
      assertNotNull(features);
   }

   protected File getResource(String path) throws IOException
   {
      final File resourcesDir = environment.getResourcesDir();
      assertTrue(resourcesDir.exists());
      final File resource = workspace.importDir(new File(resourcesDir, path));
      assertTrue(resource.exists());
      return resource;
   }
}
