/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.extensions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.builder.util.DefaultConverter;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class ProductExtenderTest
{

   @Test
   public void testGetAssemblyClassifier() throws Exception
   {
      assertEquals("bar", ProductExtender.getAssemblyClassifier("foo-bar.txt"));
      assertEquals("", ProductExtender.getAssemblyClassifier("foo.txt"));
      assertEquals("", ProductExtender.getAssemblyClassifier("foo-.txt"));
      assertEquals("bar", ProductExtender.getAssemblyClassifier("foo-bar"));
      assertEquals("", ProductExtender.getAssemblyClassifier("foo"));
   }
   
   @Test
   public void testAddAssemblyMetadata()
   {
      BasicConverter converter = new DefaultConverter();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<ProductDefinition> productDefinitions = new ArrayList<ProductDefinition>();
      ProductDefinition fooMainProduct = addProductDefinition(productDefinitions, "foo.product");
      ProductDefinition fooTestProduct = addProductDefinition(productDefinitions, "foo-test.product");

      try
      {
         ProductExtender.addAssemblyMetadata(productDefinitions, converter, properties);
         fail();
      }
      catch (IllegalStateException e)
      { // as expected
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

   private static ProductDefinition addProductDefinition(List<ProductDefinition> productDefinitions, String fileName)
   {
      ProductDefinition productDefinition = ModuleModelFactory.eINSTANCE.createProductDefinition();
      productDefinition.setFile(new File(fileName));
      productDefinitions.add(productDefinition);
      return productDefinition;
   }

}
