/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.extensions;

import static org.junit.Assert.assertEquals;
import static org.sourcepit.b2.directory.parser.internal.extensions.ProductExtender.deriveProductUniqueId;
import static org.sourcepit.common.utils.io.IO.buffOut;
import static org.sourcepit.common.utils.io.IO.fileOut;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.common.utils.io.IO;
import org.sourcepit.common.utils.io.Write.ToStream;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;

public class ProductExtenderTest extends AbstractTestEnvironmentTest
{

   @Test
   public void testEmptyUIdAndVersion() throws IOException
   {
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

      ProductExtender productExtender = new ProductExtender();
      productExtender.extend(module, new LinkedPropertiesMap());

      ProductsFacet productsFacet = module.getFacets(ProductsFacet.class).get(0);
      assertEquals(1, productsFacet.getProductDefinitions().size());

      ProductDefinition productDefinition = productsFacet.getProductDefinitions().get(0);
      assertEquals("org.sourcepit.foo.sdk.product", productDefinition.getAnnotationData("product", "uid"));
      assertEquals("1.0.0.qualifier", productDefinition.getAnnotationData("product", "version"));
   }

   @Test
   public void testDeriveProductUniqueId() throws Exception
   {
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

   private static void write(final File productFile, final byte[] content)
   {
      IO.write(new ToStream<byte[]>()
      {
         @Override
         public void write(OutputStream writer, byte[] content) throws Exception
         {
            writer.write(content);
         }
      }, buffOut(fileOut(productFile)), content);
   }
}
