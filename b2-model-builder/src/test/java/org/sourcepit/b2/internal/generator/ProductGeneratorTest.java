/*
 * Copyright (C) 2013 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sourcepit.b2.internal.generator.p2.Require;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ProductGeneratorTest
{

   @Test
   public void testMoveStrictRequirementsToProductXML()
   {
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

   private static Require createRequire(String name, String range)
   {
      final Require require = new Require();
      require.setNamespace("org.eclipse.equinox.p2.iu");
      require.setName(name);
      require.setRange(range);
      return require;
   }
}
