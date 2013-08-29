/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.extensions;

import static com.google.common.base.Preconditions.checkState;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.addAssemblyClassifier;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.addAssemblyName;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.directory.parser.internal.module.AbstractModuleParserExtender;
import org.sourcepit.b2.directory.parser.internal.module.IModuleParserExtender;
import org.sourcepit.b2.model.builder.util.BasicConverter;
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

@Named
public class ProductExtender extends AbstractModuleParserExtender implements IModuleParserExtender
{
   private BasicConverter converter;

   @Inject
   public ProductExtender(BasicConverter converter)
   {
      this.converter = converter;
   }

   @Override
   protected void addInputTypes(Collection<Class<? extends Annotatable>> inputTypes)
   {
      inputTypes.add(BasicModule.class);
   }

   @Override
   protected void doExtend(Annotatable modelElement, PropertiesSource properties)
   {
      final BasicModule module = (BasicModule) modelElement;

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
                     throw new IllegalStateException("Attribute version not specified in product file "
                        + productDef.getFile());
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

      final EList<ProductDefinition> productDefinitions = productsFacet.getProductDefinitions();
      if (!productDefinitions.isEmpty())
      {
         addAssemblyMetadata(productDefinitions, converter, properties);
         module.getFacets().add(productsFacet);
      }
   }

   static void addAssemblyMetadata(List<ProductDefinition> productDefinitions, BasicConverter converter,
      PropertiesSource properties)
   {
      final Map<String, String> assemblyClassifierToNameMap = createAssemblyClassifierToNameMap(converter, properties);
      for (ProductDefinition productDefinition : productDefinitions)
      {
         final String productFileName = productDefinition.getFile().getName();
         final String assemblyClassifier = getAssemblyClassifier(productFileName);

         final String assemblyName = assemblyClassifierToNameMap.get(assemblyClassifier);
         checkState(assemblyName != null, "Unable to determine assembly for product '%s' with classifier '%s'",
            productFileName, assemblyClassifier);

         addAssemblyName(productDefinition, assemblyName);
         addAssemblyClassifier(productDefinition, assemblyClassifier);
      }
   }

   private static Map<String, String> createAssemblyClassifierToNameMap(BasicConverter converter,
      PropertiesSource properties)
   {
      final Map<String, String> assemblyClassifierToNameMap = new HashMap<String, String>();
      for (String assemblyName : converter.getAssemblyNames(properties))
      {
         assemblyClassifierToNameMap.put(converter.getAssemblyClassifier(properties, assemblyName), assemblyName);
      }
      return assemblyClassifierToNameMap;
   }

   static String getAssemblyClassifier(String productFileName)
   {
      String classifier = productFileName;
      int idx = classifier.lastIndexOf('.');
      if (idx > -1)
      {
         classifier = classifier.substring(0, idx);
      }
      idx = classifier.lastIndexOf('-');
      if (idx > -1)
      {
         classifier = classifier.substring(idx + 1, classifier.length());
      }
      else
      {
         classifier = "";
      }
      return classifier;
   }

}
