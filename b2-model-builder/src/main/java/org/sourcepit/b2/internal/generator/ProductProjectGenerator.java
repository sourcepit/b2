/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.generator.AbstractGenerator;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.internal.generator.p2.Action;
import org.sourcepit.b2.internal.generator.p2.Instruction;
import org.sourcepit.b2.model.builder.util.BasicConverter.AggregatorMode;
import org.sourcepit.b2.model.builder.util.ProductsConverter;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.common.utils.file.FileVisitor;
import org.sourcepit.common.utils.lang.Exceptions;
import org.sourcepit.common.utils.path.Path;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.path.PathUtils;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

@Named
public class ProductProjectGenerator extends AbstractGenerator implements IB2GenerationParticipant
{
   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private ProductsConverter converter;

   @Override
   protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
   {
      inputTypes.add(ProductDefinition.class);
   }

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.PROJECT_GENERATOR;
   }

   @Override
   public void generate(EObject inputElement, PropertiesSource properties, ITemplates templates)
   {
      final ProductDefinition product = (ProductDefinition) inputElement;
      final AbstractModule module = product.getParent().getParent();
      final IInterpolationLayout layout = layoutMap.get(module.getLayoutId());

      final String uid = product.getAnnotationData("product", "uid");

      final File projectDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
      projectDir.mkdirs();

      EMap<String, String> data = product.getAnnotation("product").getData();

      File srcFile = product.getFile();
      File productFile = new File(projectDir, srcFile.getName());
      try
      {
         productFile.createNewFile();
         FileUtils.copyFile(srcFile, productFile);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      final String classifier = getAssemblyClassifier(productFile.getName());

      final Optional<FeatureProject> oAssemblyFeature = findAssemblyFeatureForClassifier(module, classifier);
      if (!oAssemblyFeature.isPresent())
      {
         throw new IllegalStateException("Cannot determine assembly feature for classifier '" + classifier + "'");
      }

      final FeatureProject assemblyFeature = oAssemblyFeature.get();

      final Optional<String> oAssemblyName = getAssemblyNameByClassifier(properties, classifier,
         B2MetadataUtils.getAssemblyNames(assemblyFeature));
      if (!oAssemblyName.isPresent())
      {
         throw new IllegalStateException("Cannot determine assembly name for classifier '" + classifier + "'");
      }

      final Document productDoc = XmlUtils.readXml(productFile);
      final Element features = getFeaturesNode(productDoc);

      final List<Node> featuresFromProductFile = getChildNodes(features);
      for (Node node : featuresFromProductFile)
      {
         features.removeChild(node);
      }

      final AggregatorMode mode = converter.getAggregatorMode(properties, oAssemblyName.get());
      switch (mode)
      {
         case UNWRAP :
            appendUnwrapped(productDoc, features, assemblyFeature);
            break;
         case OFF :
         case AGGREGATE :
            appendAggregated(productDoc, features, assemblyFeature);
            break;
         default :
            throw new IllegalStateException("Unknown aggregator mode: " + mode);
      }

      for (Node node : featuresFromProductFile)
      {
         features.appendChild(node);
      }

      Element element;
      for (StrictReference feature : converter.getIncludedFeaturesForProduct(properties, uid))
      {
         element = productDoc.createElement("feature");
         element.setAttribute("id", feature.getId());
         final String version = feature.getVersion();
         if (!Strings.isNullOrEmpty(version) && feature.isSetVersion())
         {
            element.setAttribute("version", version);
         }
         features.appendChild(element);
      }

      final List<StrictReference> additionalPlugins = converter.getIncludedPluginsForProduct(properties, uid);
      if (!additionalPlugins.isEmpty())
      {
         final Element plugins = getPluginsNode(productDoc);
         for (StrictReference plugin : additionalPlugins)
         {
            element = productDoc.createElement("plugin");
            element.setAttribute("id", plugin.getId());
            final String version = plugin.getVersion();
            if (!Strings.isNullOrEmpty(version) && plugin.isSetVersion())
            {
               element.setAttribute("version", version);
            }
            plugins.appendChild(element);
         }
      }

      final PluginProject productPlugin = resolveProductPlugin(module, product);
      if (productPlugin != null)
      {
         copyProductResources(productPlugin.getDirectory(), projectDir, properties, uid);

         // tycho icon bug fix
         for (Entry<String, String> entry : data)
         {
            String key = entry.getKey();
            if (key.endsWith(".icon"))
            {
               final String os = key.substring(0, key.length() - ".icon".length());
               String path = entry.getValue();
               if (path.startsWith("/"))
               {
                  path = path.substring(1);
               }

               final File iconFile = new File(productPlugin.getDirectory().getParentFile(), path);
               if (iconFile.exists())
               {
                  for (Node node : XmlUtils.queryNodes(productDoc, "product/launcher/" + os + "/ico"))
                  {
                     element = (Element) node;
                     element.setAttribute("path", iconFile.getAbsolutePath());
                  }
               }
            }
         }
      }

      XmlUtils.writeXml(productDoc, productFile);

      generateP2Inf(properties, uid, projectDir, getProductName(productFile.getName()));
   }

   private static List<Node> getChildNodes(final Element node)
   {
      final List<Node> childNodes = new ArrayList<Node>();
      final NodeList nodeList = node.getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++)
      {
         childNodes.add(nodeList.item(i));
      }
      return childNodes;
   }

   private void appendAggregated(Document doc, Element features, FeatureProject assemblyFeature)
   {
      appendFeature(doc, features, assemblyFeature.getId());
   }

   private void appendFeature(Document doc, Element features, String featureId)
   {
      Element element = doc.createElement("feature");
      element.setAttribute("id", featureId);
      features.appendChild(element);
   }

   private void appendUnwrapped(Document doc, Element features, FeatureProject assemblyFeature)
   {
      for (FeatureInclude featureInclude : assemblyFeature.getIncludedFeatures())
      {
         appendFeature(doc, features, featureInclude.getId());
      }

      for (RuledReference ruledReference : assemblyFeature.getRequiredFeatures())
      {
         appendFeature(doc, features, ruledReference.getId());
      }
   }

   private void generateP2Inf(PropertiesSource properties, String uid, File projectDir, String productName)
   {
      final List<String> sites = converter.getUpdateSitesForProduct(properties, uid);
      if (!sites.isEmpty())
      {
         final Instruction instruction = new Instruction();
         instruction.setPhase("configure");
         for (String site : sites)
         {
            instruction.getActions().add(createAddRepoAction("0", site));
            instruction.getActions().add(createAddRepoAction("1", site));
         }

         final PropertiesMap instructions = new LinkedPropertiesMap();
         instructions.put(instruction.getHeader(), instruction.getBody());
         instructions.store(new File(projectDir, productName + ".p2.inf"));
      }
   }

   private static Action createAddRepoAction(String type, String url)
   {
      final Action action = new Action();
      action.setName("addRepository");
      final PropertiesMap params = action.getParameters();
      params.put("type", type);
      params.put("location", url);
      params.put("enabled", "true");
      return action;
   }

   private void copyProductResources(final File srcDir, final File destDir, PropertiesSource properties,
      String productId)
   {
      final PathMatcher matcher = converter.getResourceMatcherForProduct(properties, productId);

      org.sourcepit.common.utils.file.FileUtils.accept(srcDir, new FileVisitor()
      {
         public boolean visit(File srcFile)
         {
            if (srcFile.equals(srcDir))
            {
               return true;
            }

            final boolean isDir = srcFile.isDirectory();

            String path = PathUtils.getRelativePath(srcFile, srcDir, "/");
            if (isDir)
            {
               path += "/";
            }
            if (matcher.isMatch(path))
            {
               try
               {
                  final File destFile = new File(destDir, path);
                  if (isDir)
                  {
                     FileUtils.copyDirectory(srcFile, destFile);
                  }
                  else
                  {
                     FileUtils.copyFile(srcFile, destFile);
                  }
               }
               catch (IOException e)
               {
                  throw Exceptions.pipe(e);
               }

               return false;
            }
            return true;
         }
      });
   }

   private static Optional<FeatureProject> findAssemblyFeatureForClassifier(final AbstractModule module,
      String classifier)
   {
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         for (FeatureProject featureProject : featuresFacet.getProjects())
         {
            final int idx = B2MetadataUtils.getAssemblyClassifiers(featureProject).indexOf(classifier);
            if (idx > -1)
            {
               return Optional.of(featureProject);
            }
         }
      }
      return Optional.absent();
   }

   private Element getFeaturesNode(final Document productDoc)
   {
      Element features = (Element) XmlUtils.queryNode(productDoc, "/product/features");
      if (features == null)
      {
         features = productDoc.createElement("features");
         Element product = (Element) productDoc.getElementsByTagName("product").item(0);
         product.appendChild(features);
      }
      return features;
   }

   private Element getPluginsNode(final Document productDoc)
   {
      Element plugins = (Element) XmlUtils.queryNode(productDoc, "/product/plugins");
      if (plugins == null)
      {
         plugins = productDoc.createElement("plugins");
         Element product = (Element) productDoc.getElementsByTagName("product").item(0);
         product.appendChild(plugins);
      }
      return plugins;
   }

   public static String getProductName(String productFileName)
   {
      return new Path(productFileName).getFileName();
   }

   public static String getAssemblyClassifier(String productFileName)
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

   private Optional<String> getAssemblyNameByClassifier(PropertiesSource properties, final String classifier,
      List<String> assemblyNames)
   {
      for (String assemblyName : assemblyNames)
      {
         if (classifier.equals(converter.getAssemblyClassifier(properties, assemblyName)))
         {
            return Optional.of(assemblyName);
         }
      }
      return Optional.absent();
   }

   private PluginProject resolveProductPlugin(final AbstractModule module, final ProductDefinition project)
   {
      final StrictReference reference = project.getProductPlugin();
      if (reference != null)
      {
         return module.resolveReference(reference, PluginsFacet.class);
      }
      return null;
   }
}
