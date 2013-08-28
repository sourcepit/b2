/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.extensions;

import static java.lang.Character.isWhitespace;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Named;

import org.sourcepit.b2.directory.parser.internal.module.AbstractModuleParserExtender;
import org.sourcepit.b2.directory.parser.internal.module.IModuleParserExtender;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.modeling.Annotatable;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Bernd
 */
@Named
public class ProductExtender extends AbstractModuleParserExtender implements IModuleParserExtender
{
   @Override
   protected void addInputTypes(Collection<Class<? extends Annotatable>> inputTypes)
   {
      inputTypes.add(BasicModule.class);
   }

   @Override
   protected void doExtend(Annotatable modelElement, PropertiesSource properties)
   {
      BasicModule module = (BasicModule) modelElement;

      final ProductsFacet productsFacet = ModuleModelFactory.eINSTANCE.createProductsFacet();
      productsFacet.setDerived(true);
      productsFacet.setName("products");

      for (PluginsFacet pluginsFacet : module.getFacets(PluginsFacet.class))
      {
         for (PluginProject pluginProject : pluginsFacet.getProjects())
         {
            File projectDir = pluginProject.getDirectory();
            final File[] productFiles = projectDir.listFiles(new FileFilter()
            {
               public boolean accept(File file)
               {
                  return file.isFile() && file.getName().endsWith(".product");
               }
            });

            if (productFiles != null && productFiles.length > 0)
            {
               for (File productFile : productFiles)
               {
                  final ProductDefinition productDef = ModuleModelFactory.eINSTANCE.createProductDefinition();
                  productDef.setFile(productFile);

                  productDef.setAnnotationData("product", "file", productFile.getAbsolutePath());

                  Document productDoc = XmlUtils.readXml(productFile);
                  Element elem = productDoc.getDocumentElement();

                  final String id = elem.getAttribute("id");
                  if (id.startsWith(pluginProject.getId() + "."))
                  {
                     final StrictReference productPlugin = ModuleModelFactory.eINSTANCE.createStrictReference();
                     productPlugin.setId(pluginProject.getId());
                     productPlugin.setVersion(pluginProject.getVersion());
                     productDef.setProductPlugin(productPlugin);
                  }
                  else
                  {
                     int idx = id.lastIndexOf('.');
                     if (idx > -1 && id.length() > idx + 1)
                     {
                        final String pluginId = id.substring(0, idx);
                        final StrictReference productPlugin = ModuleModelFactory.eINSTANCE.createStrictReference();
                        productPlugin.setId(pluginId);
                        productPlugin.setVersion("0.0.0"); // set default version as we can't determine it here...
                        productDef.setProductPlugin(productPlugin);
                     }
                  }

                  final StrictReference productPlugin = productDef.getProductPlugin();

                  final String uid = determineProductUniqueId(productFile, elem, productPlugin);
                  productDef.setAnnotationData("product", "uid", uid);

                  final String version = determineProductVersion(elem, productPlugin);
                  if (version != null)
                  {
                     productDef.setAnnotationData("product", "version", version);
                  }

                  productDef.setAnnotationData("product", "id", id);
                  productDef.setAnnotationData("product", "application", elem.getAttribute("application"));

                  final Iterable<Node> nodes = XmlUtils.queryNodes(productDoc, "product/launcher/*/ico");
                  for (Node node : nodes)
                  {
                     Element ico = (Element) node;
                     String path = ico.getAttribute("path");
                     if (path.length() > 0)
                     {
                        String osName = ico.getParentNode().getNodeName();
                        productDef.setAnnotationData("product", osName + ".icon", path);
                     }
                  }

                  productsFacet.getProductDefinitions().add(productDef);
               }
            }
         }
      }

      if (!productsFacet.getProductDefinitions().isEmpty())
      {
         module.getFacets().add(productsFacet);
      }
   }

   private static String determineProductVersion(Element productXML, final StrictReference productPlugin)
   {
      String version = productXML.getAttribute("version");
      if (version.length() == 0)
      {
         if (productPlugin != null)
         {
            version = productPlugin.getVersion();
         }
         else
         {
            version = null;
         }
      }
      return version;
   }

   private static String determineProductUniqueId(File productFile, Element productXML,
      final StrictReference productPlugin)
   {
      String uid = productXML.getAttribute("uid");
      if (uid.length() == 0)
      {
         if (productPlugin == null)
         {
            throw new IllegalStateException("Attribute uid not specified in product file " + productFile);
         }
         else
         {
            uid = deriveProductUniqueId(productPlugin.getId(), productFile.getName());
         }
      }
      return uid;
   }

   static String deriveProductUniqueId(String productPluginId, String productFileName)
   {
      final String[] nameSegments = normalizeAndSplitProductFileName(productFileName);
      final String[] pluginSegments = productPluginId.split("\\.");

      final StringBuilder sb = new StringBuilder();

      int i = 0;
      int j = 0;

      final String firstNameSegment = nameSegments[0];
      for (; i < pluginSegments.length; i++)
      {
         final String pluginSegment = pluginSegments[i];
         sb.append(pluginSegment);
         sb.append('.');

         if (firstNameSegment.equals(pluginSegment))
         {
            i++;
            j++;
            break;
         }
      }

      for (; i < pluginSegments.length && j < nameSegments.length; i++, j++)
      {
         final String pluginSegment = pluginSegments[i];
         final String nameSegment = nameSegments[j];
         if (pluginSegment.equals(nameSegment))
         {
            sb.append(pluginSegment);
            sb.append('.');
         }
         else
         {
            j = 0;
            break;
         }
      }

      if (i != pluginSegments.length)
      {
         j = 0;
      }

      for (; i < pluginSegments.length; i++)
      {
         sb.append(pluginSegments[i]);
         sb.append('.');
      }

      for (; j < nameSegments.length; j++)
      {
         sb.append(nameSegments[j]);
         sb.append('.');
      }

      sb.append("product");

      return sb.toString();
   }

   private static String[] normalizeAndSplitProductFileName(String productFileName)
   {
      int idx = productFileName.toLowerCase().lastIndexOf('.');
      if (idx > -1)
      {
         productFileName = productFileName.substring(0, idx);
      }

      char[] chars = productFileName.toCharArray();

      final List<String> segments = new ArrayList<String>();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < chars.length; i++)
      {
         final char c = chars[i];
         if (c == '-' || c == '.')
         {
            final String segment = sb.toString();
            if (!segment.isEmpty())
            {
               segments.add(segment);
            }
            sb = new StringBuilder();
         }
         else if (isWhitespace(c))
         {
            sb.append('_');
         }
         else
         {
            sb.append(c);
         }
      }

      final String segment = sb.toString();
      if (!segment.isEmpty())
      {
         segments.add(segment);
      }

      return segments.toArray(new String[segments.size()]);
   }

}
