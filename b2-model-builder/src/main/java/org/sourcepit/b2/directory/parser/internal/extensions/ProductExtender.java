/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.extensions;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

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

                  final String uid = elem.getAttribute("uid");
                  if (uid.length() == 0)
                  {
                     throw new IllegalStateException("Attribute uid not specified in product file "
                        + productDef.getFile());
                  }

                  String version = elem.getAttribute("version");
                  if (version.length() == 0)
                  {
                     version = null;
                  }

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

                  productDef.setAnnotationData("product", "uid", uid);
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

}
