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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.sonatype.guice.bean.containers.InjectedTest;
import org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness;
import org.sourcepit.b2.execution.B2;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.internal.generator.p2.Action;
import org.sourcepit.b2.internal.generator.p2.Instruction;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Element;
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

   @Override
   public void configure(Binder binder)
   {
      super.configure(binder);
      // binder.bind(Logger.class).toInstance(new ConsoleLogger());
   }

   @Test
   public void testGenProduct_Bug37() throws Exception
   {
      final File moduleDir = getResource("ProductTest");

      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

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
      final String uid = productDefinition.getAnnotationData("product", "uid");
      assertNotNull(uid);

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      final File projectDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
      assertTrue(projectDir.exists());

      productFile = new File(projectDir, "bundle.a.product");
      features = XmlUtils.queryNode(XmlUtils.readXml(productFile), "/product/features");
      assertNotNull(features);
   }

   @Test
   public void testCopyProductResources_Feature86() throws Exception
   {
      final File moduleDir = getResource("ProductTest");
      
      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

      PropertiesMap props = B2ModelBuildingRequest.newDefaultProperties();
      props.put("b2.products.resources", "p2.inf,legal/**");

      B2Request request = new B2Request();
      request.setModuleDirectory(moduleDir);
      request.setInterpolate(true);
      request.setModuleProperties(props);
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
      final String uid = productDefinition.getAnnotationData("product", "uid");
      assertNotNull(uid);

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      final File projectDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
      assertTrue(projectDir.exists());

      assertTrue(new File(projectDir, "p2.inf").exists());
      assertTrue(new File(projectDir, "legal/license.txt").exists());
      assertTrue(new File(projectDir, "legal/about.txt").exists());
   }

   @Test
   public void testFeature87_AdditionalFeatureIncludes() throws Exception
   {
      final File moduleDir = getResource("ProductTest");

      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

      PropertiesMap props = ModelBuilderTestHarness.newProperties(moduleDir);
      props.put("b2.products.features", "foo:1.0.0,bar");

      B2Request request = new B2Request();
      request.setModuleDirectory(moduleDir);
      request.setInterpolate(true);
      request.setModuleProperties(props);
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
      final String uid = productDefinition.getAnnotationData("product", "uid");
      assertNotNull(uid);

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      final File projectDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
      assertTrue(projectDir.exists());

      File productFile = new File(projectDir, "bundle.a.product");
      @SuppressWarnings({ "unchecked", "rawtypes" })
      Iterator<Element> features = (Iterator) XmlUtils.queryNodes(XmlUtils.readXml(productFile),
         "/product/features/feature").iterator();

      Element feature = features.next();
      assertEquals("org.sourcepit.b2.its.ProductTest.plugins.feature", feature.getAttribute("id"));
      assertEquals("", feature.getAttribute("version"));
      feature = features.next();
      assertEquals("foo", feature.getAttribute("id"));
      assertEquals("1.0.0", feature.getAttribute("version"));
      feature = features.next();
      assertEquals("bar", feature.getAttribute("id"));
      assertEquals("", feature.getAttribute("version"));
   }

   @Test
   public void testFeature87_AdditionalPluginIncludes() throws Exception
   {
      final File moduleDir = getResource("ProductTest");

      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

      PropertiesMap props = B2ModelBuildingRequest.newDefaultProperties();
      props.put("b2.products.plugins", "foo:1.0.0,bar");

      B2Request request = new B2Request();
      request.setModuleDirectory(moduleDir);
      request.setInterpolate(true);
      request.setModuleProperties(props);
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
      final String uid = productDefinition.getAnnotationData("product", "uid");
      assertNotNull(uid);

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      final File projectDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
      assertTrue(projectDir.exists());

      File productFile = new File(projectDir, "bundle.a.product");
      @SuppressWarnings({ "unchecked", "rawtypes" })
      Iterator<Element> features = (Iterator) XmlUtils.queryNodes(XmlUtils.readXml(productFile),
         "/product/plugins/plugin").iterator();

      Element plugins = features.next();
      assertEquals("foo", plugins.getAttribute("id"));
      assertEquals("1.0.0", plugins.getAttribute("version"));
      plugins = features.next();
      assertEquals("bar", plugins.getAttribute("id"));
      assertEquals("", plugins.getAttribute("version"));
   }

   @Test
   public void testFeature89_ProductSites() throws Exception
   {
      final File moduleDir = getResource("ProductTest");

      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

      PropertiesMap props = B2ModelBuildingRequest.newDefaultProperties();
      props.put("b2.products.sites",
         "http://download.eclipse.org/eclipse/updates/4.2,http://download.eclipse.org/releases/juno/");

      B2Request request = new B2Request();
      request.setModuleDirectory(moduleDir);
      request.setInterpolate(true);
      request.setModuleProperties(props);
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
      final String uid = productDefinition.getAnnotationData("product", "uid");
      assertNotNull(uid);

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      final File projectDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
      assertTrue(projectDir.exists());

      File productFile = new File(projectDir, "bundle.a.p2.inf");
      assertTrue(productFile.exists());

      PropertiesMap p2Inf = new LinkedPropertiesMap();
      p2Inf.load(productFile);

      Instruction instruction = Instruction.parse("instructions.configure", p2Inf.get("instructions.configure"));
      List<Action> actions = instruction.getActions();
      assertEquals(4, actions.size());

      Action action = actions.get(0);
      assertEquals(0, action.getParameters().getInt("type", -1));
      assertEquals("http://download.eclipse.org/eclipse/updates/4.2", action.getParameters().get("location"));
      assertEquals(true, action.getParameters().getBoolean("enabled", false));

      action = actions.get(1);
      assertEquals(1, action.getParameters().getInt("type", -1));
      assertEquals("http://download.eclipse.org/eclipse/updates/4.2", action.getParameters().get("location"));
      assertEquals(true, action.getParameters().getBoolean("enabled", false));

      action = actions.get(2);
      assertEquals(0, action.getParameters().getInt("type", -1));
      assertEquals("http://download.eclipse.org/releases/juno/", action.getParameters().get("location"));
      assertEquals(true, action.getParameters().getBoolean("enabled", false));

      action = actions.get(3);
      assertEquals(1, action.getParameters().getInt("type", -1));
      assertEquals("http://download.eclipse.org/releases/juno/", action.getParameters().get("location"));
      assertEquals(true, action.getParameters().getBoolean("enabled", false));
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
