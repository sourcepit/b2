/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.maven.internal.converter;

import java.io.File;
import java.util.Properties;

import org.apache.maven.model.Model;
import org.sourcepit.b2.maven.internal.converter.DefaultModuleDescriptorConverter;
import org.sourcepit.b2.maven.internal.converter.IModuleDescriptorConverter;
import org.sourcepit.b2.test.resources.internal.harness.AbstractPlexusWorkspaceTest;


public class DefaultModuleDescriptorConverterTest extends AbstractPlexusWorkspaceTest
{
   public void testProperties() throws Exception
   {
      File moduleDir = workspace.importResources("component-properties");

      File moduleDescriptor = new File(moduleDir, "module.xml");
      assertTrue(moduleDescriptor.exists());

      IModuleDescriptorConverter converter = lookup(IModuleDescriptorConverter.class, moduleDescriptor.getName());
      assertNotNull(converter);
      assertTrue(converter instanceof DefaultModuleDescriptorConverter);

      Model mavenModel = converter.convert(moduleDescriptor);
      assertNotNull(mavenModel);
      assertEquals("org.sourcepit.b2", mavenModel.getGroupId());
      assertEquals("component-properties", mavenModel.getArtifactId());
      assertEquals("0.1.0-SNAPSHOT", mavenModel.getVersion());

      Properties properties = mavenModel.getProperties();
      assertEquals(6, properties.size());
      assertEquals("Hello", properties.getProperty("name"));
      assertEquals("Hallo", properties.getProperty("nls_de.name"));
      assertEquals("This is text belongs to ${name}", properties.getProperty("description"));
      assertEquals("Dieser Text gehört zu ${nls_de.name}", properties.getProperty("nls_de.description"));
      assertEquals("This key is missing in the de properties file", properties.getProperty("defaultOnly"));
      assertEquals("This key is missing in the de properties file", properties.getProperty("nls_de.defaultOnly"));
   }
}
