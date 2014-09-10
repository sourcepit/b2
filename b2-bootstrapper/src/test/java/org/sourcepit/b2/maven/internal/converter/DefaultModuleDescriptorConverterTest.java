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

package org.sourcepit.b2.maven.internal.converter;

import java.io.File;
import java.util.Properties;

import org.apache.maven.model.Model;
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
