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

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.sourcepit.b2.model.harness.ModelTestHarness.addSiteProject;
import static org.sourcepit.b2.model.harness.ModelTestHarness.createBasicModule;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.addAssemblyClassifier;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.addAssemblyName;
import static org.sourcepit.common.utils.io.IO.buffOut;
import static org.sourcepit.common.utils.io.IO.fileOut;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;
import org.sourcepit.b2.directory.parser.internal.module.ModelBuilderTestHarness;
import org.sourcepit.b2.execution.B2;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.internal.generator.p2.Action;
import org.sourcepit.b2.internal.generator.p2.Instruction;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.io.IO;
import org.sourcepit.common.utils.io.Write.ToStream;
import org.sourcepit.common.utils.nls.NlsUtils;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Optional;

public class SiteProjectGeneratorTest extends AbstractTestEnvironmentTest {
   @Inject
   private B2 b2;

   @Inject
   private LayoutManager layoutManager;

   @Test
   public void testGenProduct_Bug37() throws Exception {
      final File moduleDir = getResource("ProductTest");

      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

      File productFile = new File(moduleDir, "bundle.a/bundle.a.product");
      Node features = XmlUtils.queryNode(XmlUtils.readXml(productFile), "/product/features");
      assertNull(features);

      B2Request request = new B2Request();
      request.setModuleDirectory(new ModuleDirectory(moduleDir, null));
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
      final File projectDir = new File(layout.pathOfSiteProject(module, ""));
      assertTrue(projectDir.exists());

      productFile = new File(projectDir, "bundle.a.product");
      features = XmlUtils.queryNode(XmlUtils.readXml(productFile), "/product/features");
      assertNotNull(features);
   }

   @Test
   public void testCopyProductResources_Feature86() throws Exception {
      final File moduleDir = getResource("ProductTest");

      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

      PropertiesMap props = B2ModelBuildingRequest.newDefaultProperties();
      props.put("b2.products.resources", "p2.inf,legal/**");

      B2Request request = new B2Request();
      request.setModuleDirectory(new ModuleDirectory(moduleDir, null));
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
      final File projectDir = new File(layout.pathOfSiteProject(module, ""));
      assertTrue(projectDir.exists());

      assertTrue(new File(projectDir, "p2.inf").exists());
      assertTrue(new File(projectDir, "legal/license.txt").exists());
      assertTrue(new File(projectDir, "legal/about.txt").exists());
   }

   @Test
   public void testFeature87_AdditionalProductFeatureIncludes() throws Exception {
      final File moduleDir = getResource("ProductTest");

      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

      PropertiesMap props = ModelBuilderTestHarness.newProperties(moduleDir);
      props.put("b2.products.features", "foo:1.0.0,bar");

      B2Request request = new B2Request();
      request.setModuleDirectory(new ModuleDirectory(moduleDir, null));
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
      final File projectDir = new File(layout.pathOfSiteProject(module, ""));
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
   public void testFeature87_AdditionalProductPluginIncludes() throws Exception {
      final File moduleDir = getResource("ProductTest");

      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

      PropertiesMap props = B2ModelBuildingRequest.newDefaultProperties();
      props.put("b2.products.plugins", "foo:1.0.0,bar");

      B2Request request = new B2Request();
      request.setModuleDirectory(new ModuleDirectory(moduleDir, null));
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
      final File projectDir = new File(layout.pathOfSiteProject(module, ""));
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
   public void testFeature89_AddUpdateSitesToProduct() throws Exception {
      final File moduleDir = getResource("ProductTest");

      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

      PropertiesMap props = B2ModelBuildingRequest.newDefaultProperties();
      props.put("b2.products.sites",
         "http://download.eclipse.org/eclipse/updates/4.2,http://download.eclipse.org/releases/juno/");

      B2Request request = new B2Request();
      request.setModuleDirectory(new ModuleDirectory(moduleDir, null));
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
      final File projectDir = new File(layout.pathOfSiteProject(module, ""));
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

   @Test
   public void testFeature14_EmptyProductUIdAndVersion() throws Exception {
      final File moduleDir = getResource("ProductTest");

      final List<File> projectDirs = new ArrayList<File>();
      projectDirs.add(moduleDir);

      final StringBuilder sb = new StringBuilder();
      sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      sb.append("<?pde version=\"3.5\"?>\n");
      sb.append("<product id=\"bundle.a.product\">\n");
      sb.append("</product>\n");

      final File productFile = new File(moduleDir, "bundle.a/bundle.a.product");
      assertTrue(productFile.exists());
      write(productFile, sb.toString().getBytes("UTF-8"));

      B2Request request = new B2Request();
      request.setModuleDirectory(new ModuleDirectory(moduleDir, null));
      request.setInterpolate(true);
      request.setModuleProperties(B2ModelBuildingRequest.newDefaultProperties());
      request.setTemplates(new DefaultTemplateCopier());

      // build model and generate
      final AbstractModule module = b2.generate(request);
      assertNotNull(module);

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      final File projectDir = new File(layout.pathOfSiteProject(module, ""));
      assertTrue(projectDir.exists());

      File newProductFile = new File(projectDir, "bundle.a.product");
      assertTrue(newProductFile.exists());

      Element productElem = XmlUtils.readXml(newProductFile).getDocumentElement();
      assertNotNull(productElem);
      assertEquals("bundle.a.product", productElem.getAttribute("uid"));
      assertEquals("1.0.0.qualifier", productElem.getAttribute("version"));
   }

   private static void write(final File productFile, final byte[] content) {
      IO.write(new ToStream<byte[]>() {
         @Override
         public void write(OutputStream writer, byte[] content) throws Exception {
            writer.write(content);
         }
      }, buffOut(fileOut(productFile)), content);
   }

   protected File getResource(String path) throws IOException {
      final File resourcesDir = env.getResourcesDir();
      assertTrue(resourcesDir.exists());
      final File resource = ws.importDir(new File(resourcesDir, path));
      assertTrue(resource.exists());
      return resource;
   }

   @Test
   public void testGenDifferentIUTypes() throws Exception {
      final PropertiesMap properties = new LinkedPropertiesMap();

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(properties));

      final BasicModule module = createBasicModule("foo");

      SiteProject siteProject = addSiteProject(module, "sites", "foo.site");
      siteProject.setDirectory(ws.getRoot());

      addFeatureReference(siteProject, "category", "foo.plugins.feature");
      addPluginReference(siteProject, "category", "foo.plugin");
      addProductReference(siteProject, "category", "foo.product");

      final SiteProjectGenerator generator = lookup(SiteProjectGenerator.class);
      generator.generate(siteProject, properties, templates);

      File siteDir = siteProject.getDirectory();
      assertTrue(siteDir.exists());
      System.out.println(siteDir);

      Document categoryXml = XmlUtils.readXml(new File(siteDir, "category.xml"));

      List<Element> elements = new ArrayList<Element>();

      NodeList childNodes = categoryXml.getDocumentElement().getChildNodes();
      for (int i = 0; i < childNodes.getLength(); i++) {
         Node node = childNodes.item(i);
         if (node instanceof Element) {
            elements.add((Element) node);
         }
      }

      assertEquals(4, elements.size());

      Element pluginElem = elements.get(0);
      assertEquals("bundle", pluginElem.getTagName());
      assertEquals("foo.plugin", pluginElem.getAttribute("id"));
      assertEquals("1.0.0.qualifier", pluginElem.getAttribute("version"));

      Element featureElem = elements.get(1);
      assertEquals("feature", featureElem.getTagName());
      assertEquals("foo.plugins.feature", featureElem.getAttribute("id"));
      assertEquals("1.0.0.qualifier", featureElem.getAttribute("version"));

      Element productElem = elements.get(2);
      assertEquals("iu", productElem.getTagName());
      assertEquals("foo.product", productElem.getAttribute("id"));
      assertEquals("1.0.0.qualifier", productElem.getAttribute("version"));

      Element categoryElem = elements.get(3);
      assertEquals("category-def", categoryElem.getTagName());
      assertEquals("category", categoryElem.getAttribute("name"));
      assertEquals("%categories.category.name", categoryElem.getAttribute("label"));
   }

   @Test
   public void testSiteProperties() {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("b2.module.categories.included.labelAppendix", "(included Features)");
      properties.put("nls_de.b2.module.categories.included.labelAppendix", "(enthaltene Features)");
      properties.put("b2.assemblies.sdk.classifierLabel", "SDK");

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(properties));

      final BasicModule module = createBasicModule("foo");
      module.getLocales().add(NlsUtils.DEFAULT_LOCALE);
      module.getLocales().add(Locale.GERMAN);

      SiteProject siteProject = addSiteProject(module, "sites", "foo.site");
      siteProject.setDirectory(ws.getRoot());

      addFeatureReference(siteProject, "assembled", "foo.sdk.feature");
      addFeatureReference(siteProject, "included", "foo.plugins.feature");
      addFeatureReference(siteProject, "included", "foo.plugins.sources.feature");

      addAssemblyName(siteProject, "sdk");
      addAssemblyClassifier(siteProject, "");

      final SiteProjectGenerator generator = lookup(SiteProjectGenerator.class);
      generator.generate(siteProject, properties, templates);

      File siteDir = siteProject.getDirectory();
      assertTrue(siteDir.exists());

      PropertiesMap featureProperties;

      // default locale
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(siteDir, "site.properties"));
      assertEquals("Core Plug-ins", featureProperties.get("categories.assembled.name"));
      assertEquals("Core Plug-ins (included Features)", featureProperties.get("categories.included.name"));

      // de
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(siteDir, "site_de.properties"));
      assertEquals("Kern Plug-ins", featureProperties.get("categories.assembled.name"));
      assertEquals("Kern Plug-ins (enthaltene Features)", featureProperties.get("categories.included.name"));
   }

   @Test
   public void testSiteProperties_WithClassifier() {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("b2.module.categories.included.labelAppendix", "(included Features)");
      properties.put("nls_de.b2.module.categories.included.labelAppendix", "(enthaltene Features)");
      properties.put("b2.assemblies.sdk.classifierLabel", "SDK");

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(properties));

      final BasicModule module = createBasicModule("foo");
      module.getLocales().add(NlsUtils.DEFAULT_LOCALE);
      module.getLocales().add(Locale.GERMAN);

      SiteProject siteProject = addSiteProject(module, "sites", "foo.site");
      siteProject.setDirectory(ws.getRoot());

      addFeatureReference(siteProject, "assembled", "foo.sdk.feature");
      addFeatureReference(siteProject, "included", "foo.plugins.feature");
      addFeatureReference(siteProject, "included", "foo.plugins.sources.feature");

      addAssemblyName(siteProject, "sdk");
      addAssemblyClassifier(siteProject, "sdk");

      final SiteProjectGenerator generator = lookup(SiteProjectGenerator.class);
      generator.generate(siteProject, properties, templates);

      File siteDir = siteProject.getDirectory();
      assertTrue(siteDir.exists());

      PropertiesMap featureProperties;

      // default locale
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(siteDir, "site.properties"));
      assertEquals("Core Plug-ins SDK", featureProperties.get("categories.assembled.name"));
      assertEquals("Core Plug-ins SDK (included Features)", featureProperties.get("categories.included.name"));

      // de
      featureProperties = new LinkedPropertiesMap();
      featureProperties.load(new File(siteDir, "site_de.properties"));
      assertEquals("Kern Plug-ins SDK", featureProperties.get("categories.assembled.name"));
      assertEquals("Kern Plug-ins SDK (enthaltene Features)", featureProperties.get("categories.included.name"));
   }

   private FeatureInclude addFeatureReference(SiteProject siteProject, String categoryName, String featureId) {
      Category category = getCategory(siteProject, categoryName);
      if (category == null) {
         category = createCategory(siteProject, categoryName);
         siteProject.getCategories().add(category);
      }

      FeatureInclude strictReference = ModuleModelFactory.eINSTANCE.createFeatureInclude();
      strictReference.setId(featureId);
      strictReference.setVersion(siteProject.getParent().getParent().getVersion());
      category.getInstallableUnits().add(strictReference);

      return strictReference;
   }

   private PluginInclude addPluginReference(SiteProject siteProject, String categoryName, String pluginId) {
      Category category = getCategory(siteProject, categoryName);
      if (category == null) {
         category = createCategory(siteProject, categoryName);
         siteProject.getCategories().add(category);
      }

      PluginInclude strictReference = ModuleModelFactory.eINSTANCE.createPluginInclude();
      strictReference.setId(pluginId);
      strictReference.setVersion(siteProject.getParent().getParent().getVersion());
      category.getInstallableUnits().add(strictReference);

      return strictReference;
   }

   private StrictReference addProductReference(SiteProject siteProject, String categoryName, String productId) {
      Category category = getCategory(siteProject, categoryName);
      if (category == null) {
         category = createCategory(siteProject, categoryName);
         siteProject.getCategories().add(category);
      }

      StrictReference strictReference = ModuleModelFactory.eINSTANCE.createStrictReference();
      strictReference.setId(productId);
      strictReference.setVersion(siteProject.getParent().getParent().getVersion());
      category.getInstallableUnits().add(strictReference);

      return strictReference;
   }

   private Category getCategory(SiteProject siteProject, String categoryName) {
      for (Category category : siteProject.getCategories()) {
         if (categoryName.equals(category.getName())) {
            return category;
         }
      }
      return null;
   }

   private Category createCategory(SiteProject siteProject, String categoryName) {
      Category category = ModuleModelFactory.eINSTANCE.createCategory();
      category.setName(categoryName);
      return category;
   }

}
