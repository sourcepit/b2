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
import static org.junit.Assert.assertTrue;
import static org.sourcepit.common.utils.io.IO.buffIn;
import static org.sourcepit.common.utils.io.IO.buffOut;
import static org.sourcepit.common.utils.io.IO.fileIn;
import static org.sourcepit.common.utils.io.IO.fileOut;
import static org.sourcepit.common.utils.io.IO.read;
import static org.sourcepit.common.utils.io.IO.write;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.sourcepit.b2.directory.parser.internal.module.AbstractTestEnvironmentTest;
import org.sourcepit.b2.internal.generator.p2.Require;
import org.sourcepit.common.utils.io.Read.FromStream;
import org.sourcepit.common.utils.io.Write.ToStream;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ProductGeneratorTest extends AbstractTestEnvironmentTest {

   @Test
   public void testCopyAndFilterProductFile() throws Exception {
      final StringBuilder sb = new StringBuilder();
      sb.append("<product name=\"${name}\" uid=\"${uid}\" version=\"${version}\">\n");
      sb.append("${content}\n");
      sb.append("</product>\n");

      final File srcFile = ws.newFile("src.product");
      final File destFile = ws.newFile("dest.product");
      write(new ToStream<String>() {
         @Override
         public void write(OutputStream output, String content) throws Exception {
            IOUtils.copy(new ByteArrayInputStream(content.getBytes("UTF-8")), output);
         }
      }, buffOut(fileOut(srcFile)), sb.toString());
      assertTrue(srcFile.exists());

      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("uid", "foo.product");
      properties.put("version", "1.0.0.qualifier");
      properties.put("content", "hui");

      ProductGenerator.copyAndFilterProductFile(srcFile, destFile, properties);
      assertTrue(destFile.exists());

      String filteredContent = read(new FromStream<String>() {
         @Override
         public String read(InputStream input) throws Exception {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(input, output);
            return new String(output.toByteArray(), "UTF-8");
         }
      }, buffIn(fileIn(destFile)));

      assertEquals("<product name=\"${name}\" uid=\"foo.product\" version=\"1.0.0.qualifier\">\nhui\n</product>\n",
         filteredContent);
   }

   @Test
   public void testMoveStrictRequirementsToProductXML() {
      List<Require> requires = new ArrayList<Require>();
      requires.add(createRequire("1.feature.group", "0.0.0"));
      requires.add(createRequire("2.feature.group", "[1.0.0,2.0.0]"));
      requires.add(createRequire("3.feature.group", "[2.0.0,2.0.0]"));
      requires.add(createRequire("4", "0.0.0"));
      requires.add(createRequire("5", "[1.0.0,2.0.0]"));
      requires.add(createRequire("6", "[2.0.0,2.0.0]"));

      Document doc = XmlUtils.newDocument();
      Element features = doc.createElement("features");
      Element plugins = doc.createElement("plugins");

      ProductGenerator.moveStrictRequirementsToProductXML(requires, features, plugins);

      assertEquals(4, requires.size());

      NodeList childNodes;
      Element childNode;

      childNodes = features.getChildNodes();
      assertEquals(1, childNodes.getLength());
      childNode = (Element) childNodes.item(0);
      assertEquals("feature", childNode.getNodeName());
      assertEquals("3", childNode.getAttribute("id"));
      assertEquals("2.0.0", childNode.getAttribute("version"));

      childNodes = plugins.getChildNodes();
      assertEquals(1, childNodes.getLength());
      childNode = (Element) childNodes.item(0);
      assertEquals("plugin", childNode.getNodeName());
      assertEquals("6", childNode.getAttribute("id"));
      assertEquals("2.0.0", childNode.getAttribute("version"));
   }

   private static Require createRequire(String name, String range) {
      final Require require = new Require();
      require.setNamespace("org.eclipse.equinox.p2.iu");
      require.setName(name);
      require.setRange(range);
      return require;
   }
}
