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

import static com.google.common.base.Preconditions.checkState;
import static java.lang.Character.isWhitespace;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.addAssemblyClassifier;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.addAssemblyName;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
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

                  final String version = determineProductVersion(productFile, elem, productPlugin);
                  productDef.setAnnotationData("product", "version", version);

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

   private static String determineProductVersion(File productFile, Element productXML,
      final StrictReference productPlugin)
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
            throw new IllegalStateException("Attribute version not specified in product file " + productFile);
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
