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

package org.sourcepit.b2.directory.parser.internal.extensions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.sourcepit.b2.directory.parser.internal.extensions.ProductExtender.deriveProductUniqueId;
import static org.sourcepit.common.utils.io.IO.buffOut;
import static org.sourcepit.common.utils.io.IO.fileOut;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.builder.util.DefaultConverter;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.common.utils.io.IO;
import org.sourcepit.common.utils.io.Write.ToStream;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;


public class ProductExtenderTest extends AbstractTestEnvironmentTest {

   @Test
   public void testEmptyUIdAndVersion() throws IOException {
      final StringBuilder sb = new StringBuilder();
      sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      sb.append("<?pde version=\"3.5\"?>\n");
      sb.append("<product id=\"org.sourcepit.foo.product\">\n");
      sb.append("</product>\n");

      final File productFile = ws.newFile("foo-sdk.product");
      write(productFile, sb.toString().getBytes("UTF-8"));

      PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setId("org.sourcepit.foo");
      pluginProject.setVersion("1.0.0.qualifier");
      pluginProject.setDirectory(ws.getRoot());

      PluginsFacet pluginsFacet = ModuleModelFactory.eINSTANCE.createPluginsFacet();
      pluginsFacet.getProjects().add(pluginProject);

      BasicModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.getFacets().add(pluginsFacet);

      LinkedPropertiesMap properties = new LinkedPropertiesMap();
      properties.put("b2.assemblies", "sdk");

      ProductExtender productExtender = new ProductExtender(new DefaultConverter());
      productExtender.extend(module, properties);

      ProductsFacet productsFacet = module.getFacets(ProductsFacet.class).get(0);
      assertEquals(1, productsFacet.getProductDefinitions().size());

      ProductDefinition productDefinition = productsFacet.getProductDefinitions().get(0);
      assertEquals("org.sourcepit.foo.sdk.product", productDefinition.getAnnotationData("product", "uid"));
      assertEquals("1.0.0.qualifier", productDefinition.getAnnotationData("product", "version"));
   }

   @Test
   public void testDeriveProductUniqueId() throws Exception {
      assertEquals("org.sourcepit.foo.bar.product", deriveProductUniqueId("org.sourcepit.foo", "bar.product"));
      assertEquals("org.sourcepit.foo.product", deriveProductUniqueId("org.sourcepit.foo", "foo.product"));
      assertEquals("org.sourcepit.foo.sdk.product", deriveProductUniqueId("org.sourcepit.foo", "foo-sdk.product"));
      assertEquals("org.sourcepit.foo.sdk.product", deriveProductUniqueId("org.sourcepit.foo.sdk", "foo-sdk.product"));
      assertEquals("org.sourcepit.foo.sdk.murks.product",
         deriveProductUniqueId("org.sourcepit.foo.sdk", "foo-sdk-murks.product"));
      assertEquals("org.sourcepit.foo.sdk.murks.foo.sdk.product",
         deriveProductUniqueId("org.sourcepit.foo.sdk.murks", "foo-sdk.product"));
      assertEquals("org.sourcepit.foo.product", deriveProductUniqueId("org.sourcepit.foo", "sourcepit-foo.product"));
      assertEquals("foo.sdk.product", deriveProductUniqueId("foo", "foo-sdk.product"));
      assertEquals("foo.murks.foo.sdk.murks.product", deriveProductUniqueId("foo.murks", "foo-sdk-murks.product"));
   }

   private static void write(final File productFile, final byte[] content) {
      IO.write(new ToStream<byte[]>() {
         @Override
         public void write(OutputStream writer, byte[] content) throws Exception {
            writer.write(content);
         }
      }, buffOut(fileOut(productFile)), content);
   }

   @Test
   public void testGetAssemblyClassifier() throws Exception {
      assertEquals("bar", ProductExtender.getAssemblyClassifier("foo-bar.txt"));
      assertEquals("", ProductExtender.getAssemblyClassifier("foo.txt"));
      assertEquals("", ProductExtender.getAssemblyClassifier("foo-.txt"));
      assertEquals("bar", ProductExtender.getAssemblyClassifier("foo-bar"));
      assertEquals("", ProductExtender.getAssemblyClassifier("foo"));
   }

   @Test
   public void testAddAssemblyMetadata() {
      BasicConverter converter = new DefaultConverter();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<ProductDefinition> productDefinitions = new ArrayList<ProductDefinition>();
      ProductDefinition fooMainProduct = addProductDefinition(productDefinitions, "foo.product");
      ProductDefinition fooTestProduct = addProductDefinition(productDefinitions, "foo-test.product");

      try {
         ProductExtender.addAssemblyMetadata(productDefinitions, converter, properties);
         fail();
      }
      catch (IllegalStateException e) { // as expected
      }

      List<String> assemblyNames;
      List<String> assemblyClassifiers;

      assemblyNames = B2MetadataUtils.getAssemblyNames(fooMainProduct);
      assertEquals(0, assemblyNames.size());

      assemblyClassifiers = B2MetadataUtils.getAssemblyClassifiers(fooMainProduct);
      assertEquals(0, assemblyClassifiers.size());

      assemblyNames = B2MetadataUtils.getAssemblyNames(fooTestProduct);
      assertEquals(0, assemblyNames.size());

      assemblyClassifiers = B2MetadataUtils.getAssemblyClassifiers(fooTestProduct);
      assertEquals(0, assemblyClassifiers.size());

      properties.put("b2.assemblies", "main, test");
      properties.put("b2.assemblies.main.classifier", "");

      ProductExtender.addAssemblyMetadata(productDefinitions, converter, properties);

      assemblyNames = B2MetadataUtils.getAssemblyNames(fooMainProduct);
      assertEquals(1, assemblyNames.size());
      assertEquals("main", assemblyNames.get(0));

      assemblyClassifiers = B2MetadataUtils.getAssemblyClassifiers(fooMainProduct);
      assertEquals(1, assemblyClassifiers.size());
      assertEquals("", assemblyClassifiers.get(0));

      assemblyNames = B2MetadataUtils.getAssemblyNames(fooTestProduct);
      assertEquals(1, assemblyNames.size());
      assertEquals("test", assemblyNames.get(0));

      assemblyClassifiers = B2MetadataUtils.getAssemblyClassifiers(fooTestProduct);
      assertEquals(1, assemblyClassifiers.size());
      assertEquals("test", assemblyClassifiers.get(0));
   }

   private static ProductDefinition addProductDefinition(List<ProductDefinition> productDefinitions, String fileName) {
      ProductDefinition productDefinition = ModuleModelFactory.eINSTANCE.createProductDefinition();
      productDefinition.setFile(new File(fileName));
      productDefinitions.add(productDefinition);
      return productDefinition;
   }
}
