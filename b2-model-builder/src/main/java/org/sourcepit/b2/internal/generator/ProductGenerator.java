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

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.getAssemblyClassifiers;
import static org.sourcepit.b2.model.module.internal.util.ReferenceUtils.toVersionRange;
import static org.sourcepit.common.utils.io.IO.buffIn;
import static org.sourcepit.common.utils.io.IO.buffOut;
import static org.sourcepit.common.utils.io.IO.fileIn;
import static org.sourcepit.common.utils.io.IO.fileOut;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.interpolation.AbstractValueSource;
import org.osgi.framework.VersionRange;
import org.sourcepit.b2.internal.generator.p2.Action;
import org.sourcepit.b2.internal.generator.p2.Instruction;
import org.sourcepit.b2.internal.generator.p2.Require;
import org.sourcepit.b2.model.builder.util.BasicConverter.AggregatorMode;
import org.sourcepit.b2.model.builder.util.ProductsConverter;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.common.utils.file.FileVisitor;
import org.sourcepit.common.utils.io.IOOperation;
import org.sourcepit.common.utils.lang.Exceptions;
import org.sourcepit.common.utils.path.Path;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.path.PathUtils;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.sourcepit.tools.shared.resources.harness.AbstractPropertyInterpolator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Optional;

@Named
public class ProductGenerator {
   final static class ProductFileCopier extends AbstractPropertyInterpolator {
      public void copy(File srcFile, final File destFile) {
         new IOOperation<InputStream>(buffIn(fileIn(srcFile))) {
            @Override
            protected void run(final InputStream inputStream) throws IOException {
               inputStream.mark(8192);
               final String encoding = XmlUtils.getEncoding(inputStream);
               inputStream.reset();

               new IOOperation<OutputStream>(buffOut(fileOut(destFile))) {
                  @Override
                  @SuppressWarnings("synthetic-access")
                  protected void run(OutputStream outputStream) throws IOException {
                     final InputStreamReader reader = new InputStreamReader(inputStream, encoding);
                     final OutputStreamWriter writer = new OutputStreamWriter(outputStream, encoding);
                     newCopier().copy(reader, writer, null);
                  }
               }.run();
            }
         }.run();
      }
   }

   private final ProductsConverter converter;

   @Inject
   public ProductGenerator(ProductsConverter converter) {
      this.converter = converter;
   }

   public void processProduct(File projectDir, ProductDefinition productDefinition, final PropertiesSource properties) {
      final AbstractModule module = productDefinition.getParent().getParent();

      final String uid = productDefinition.getAnnotationData("product", "uid");

      File srcFile = productDefinition.getFile();
      File productFile = new File(projectDir, srcFile.getName());
      copyAndFilterProductFile(srcFile, productFile, properties);

      injectUIdAndVersion(productFile, uid, productDefinition.getAnnotationData("product", "version"));

      for (final String classifier : getAssemblyClassifiers(productDefinition)) {
         final Document productDoc = XmlUtils.readXml(productFile);

         final List<Require> requires = new ArrayList<Require>();

         final Element features = getFeaturesNode(productDoc);
         convertProductIncludesFromXML(requires, uid, features, true, properties);

         final Element plugins = getPluginsNode(productDoc);
         convertProductIncludesFromXML(requires, uid, plugins, false, properties);

         collectProductIncludes(requires, module, uid, classifier, properties);

         // move strict dependencies back to XML
         moveStrictRequirementsToProductXML(requires, features, plugins);

         XmlUtils.writeXml(productDoc, productFile);

         final PluginProject productPlugin = resolveProductPlugin(module, productDefinition);
         if (productPlugin != null) {
            copyProductResources(productPlugin.getDirectory(), projectDir, properties, uid);
            copyIcons(productDoc, productDefinition, productPlugin);
         }

         final File p2InfFile = new File(projectDir, getProductName(productFile.getName()) + ".p2.inf");

         final PropertiesMap p2Inf = new LinkedPropertiesMap();
         if (p2InfFile.exists()) {
            p2Inf.load(p2InfFile);
         }

         int i = 0;
         for (Require require : requires) {
            require.put(p2Inf, i);
            i++;
         }

         addUpdateSites(p2Inf, uid, properties);

         p2Inf.store(p2InfFile);
      }
   }

   static void copyAndFilterProductFile(File srcFile, File destFile, final PropertiesSource properties) {
      try {
         final File parentFile = destFile.getParentFile();
         if (!parentFile.exists()) {
            parentFile.mkdirs();
         }
         destFile.createNewFile();

         final ProductFileCopier copier = new ProductFileCopier();
         copier.getValueSources().add(new AbstractValueSource(false) {
            @Override
            public Object getValue(String expression) {
               return properties.get(expression);
            }
         });
         copier.copy(srcFile, destFile);
      }
      catch (IOException e) {
         throw new IllegalStateException(e);
      }
   }

   private static void injectUIdAndVersion(File productFile, String uid, String version) {
      final Document productXml = XmlUtils.readXml(productFile);
      final Element productElem = productXml.getDocumentElement();
      productElem.setAttribute("uid", uid);
      productElem.setAttribute("version", version);
      XmlUtils.writeXml(productXml, productFile);
   }

   private Element getFeaturesNode(final Document productDoc) {
      Element features = (Element) XmlUtils.queryNode(productDoc, "/product/features");
      if (features == null) {
         features = productDoc.createElement("features");
         Element product = (Element) productDoc.getElementsByTagName("product").item(0);
         product.appendChild(features);
      }
      return features;
   }

   private Element getPluginsNode(final Document productDoc) {
      Element plugins = (Element) XmlUtils.queryNode(productDoc, "/product/plugins");
      if (plugins == null) {
         plugins = productDoc.createElement("plugins");
         Element product = (Element) productDoc.getElementsByTagName("product").item(0);
         product.appendChild(plugins);
      }
      return plugins;
   }


   private void convertProductIncludesFromXML(final List<Require> requires, final String productId,
      final Element featuresOrPlugins, boolean features, PropertiesSource properties) {
      final VersionMatchRule defaultVersionMatchRule = converter.getDefaultVersionMatchRuleForProduct(properties,
         productId);
      for (Node node : getChildNodes(featuresOrPlugins)) {
         featuresOrPlugins.removeChild(node);

         if (node instanceof Element) {
            final Element feature = (Element) node;

            final String featureId = feature.getAttribute("id");
            checkState(featureId.length() > 0);

            String version = feature.getAttribute("version");
            if (isNullOrEmpty(version)) {
               version = "0.0.0";
            }

            VersionMatchRule matchRule = converter.getVersionMatchRuleForProductInclude(properties, productId,
               featureId, defaultVersionMatchRule);

            requires.add(toRequire(featureId, features, version, matchRule));
         }
      }
   }

   private static List<Node> getChildNodes(final Element node) {
      final List<Node> childNodes = new ArrayList<Node>();
      final NodeList nodeList = node.getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
         childNodes.add(nodeList.item(i));
      }
      return childNodes;
   }

   private static Require toRequire(RuledReference reference, boolean feature) {
      return toRequire(reference.getId(), feature, reference.getVersion(), reference.getVersionMatchRule());
   }

   private static Require toRequire(final String pluginOrFeatureId, boolean feature, String version,
      VersionMatchRule versionMatchRule) {
      final Require require = new Require();
      require.setNamespace("org.eclipse.equinox.p2.iu");
      if (feature) {
         require.setName(pluginOrFeatureId + ".feature.group");
      }
      else {
         require.setName(pluginOrFeatureId);
      }
      require.setRange(toVersionRange(version, versionMatchRule).toString());
      return require;
   }

   private void collectProductIncludes(final List<Require> requires, final AbstractModule module,
      final String productId, final String classifier, PropertiesSource properties) {
      final VersionMatchRule defaultVersionMatchRule = converter.getDefaultVersionMatchRuleForProduct(properties,
         productId);

      appendProductIncludesFromAssembly(requires, module, productId, classifier, properties, defaultVersionMatchRule);

      collectProductIncludesfromProperties(requires, productId, properties, defaultVersionMatchRule);
   }


   private void appendProductIncludesFromAssembly(final List<Require> requires, final AbstractModule module,
      final String productId, final String classifier, PropertiesSource properties,
      final VersionMatchRule defaultVersionMatchRule) {
      final FeatureProject assemblyFeature = findAssemblyFeatureForClassifier(module, classifier);
      if (assemblyFeature == null) {
         throw new IllegalStateException("Cannot determine assembly feature for classifier '" + classifier + "'");
      }

      final Optional<String> oAssemblyName = getAssemblyNameByClassifier(properties, classifier,
         B2MetadataUtils.getAssemblyNames(assemblyFeature));
      if (!oAssemblyName.isPresent()) {
         throw new IllegalStateException("Cannot determine assembly name for classifier '" + classifier + "'");
      }
      final String assemblyName = oAssemblyName.get();

      final AggregatorMode mode = converter.getAggregatorMode(properties, assemblyName);
      switch (mode) {
         case UNWRAP :
            appendUnwrapped(requires, productId, assemblyFeature, properties, defaultVersionMatchRule);
            break;
         case OFF :
         case AGGREGATE :
            appendAggregated(requires, productId, assemblyFeature, properties, defaultVersionMatchRule);
            break;
         default :
            throw new IllegalStateException("Unknown aggregator mode: " + mode);
      }
   }

   private static FeatureProject findAssemblyFeatureForClassifier(final AbstractModule module, String classifier) {
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class)) {
         for (FeatureProject featureProject : featuresFacet.getProjects()) {
            final int idx = B2MetadataUtils.getAssemblyClassifiers(featureProject).indexOf(classifier);
            if (idx > -1) {
               return featureProject;
            }
         }
      }
      return null;
   }

   private Optional<String> getAssemblyNameByClassifier(PropertiesSource properties, final String classifier,
      List<String> assemblyNames) {
      for (String assemblyName : assemblyNames) {
         if (classifier.equals(converter.getAssemblyClassifier(properties, assemblyName))) {
            return Optional.of(assemblyName);
         }
      }
      return Optional.absent();
   }

   private void appendUnwrapped(List<Require> requires, String productId, FeatureProject assemblyFeature,
      PropertiesSource properties, VersionMatchRule defaultVersionMatchRule) {
      for (FeatureInclude featureInclude : assemblyFeature.getIncludedFeatures()) {
         appendFeature(requires, productId, featureInclude.getId(), featureInclude.getVersion(), properties,
            defaultVersionMatchRule);
      }

      for (RuledReference ruledReference : assemblyFeature.getRequiredFeatures()) {
         requires.add(toRequire(ruledReference, true));
      }
   }

   private void appendAggregated(List<Require> requires, String productId, FeatureProject assemblyFeature,
      PropertiesSource properties, VersionMatchRule defaultVersionMatchRule) {
      appendFeature(requires, productId, assemblyFeature.getId(), assemblyFeature.getVersion(), properties,
         defaultVersionMatchRule);
   }

   private void appendFeature(List<Require> requires, String productId, String featureId, String version,
      PropertiesSource properties, VersionMatchRule defaultVersionMatchRule) {
      final VersionMatchRule rule = converter.getVersionMatchRuleForProductInclude(properties, productId, featureId,
         defaultVersionMatchRule);
      requires.add(toRequire(featureId, true, version, rule));
   }

   private void collectProductIncludesfromProperties(final List<Require> requires, final String productId,
      PropertiesSource properties, final VersionMatchRule defaultVersionMatchRule) {
      for (RuledReference feature : converter.getIncludedFeaturesForProduct(properties, productId,
         defaultVersionMatchRule)) {
         requires.add(toRequire(feature, true));
      }

      final List<RuledReference> additionalPlugins = converter.getIncludedPluginsForProduct(properties, productId,
         defaultVersionMatchRule);
      if (!additionalPlugins.isEmpty()) {
         for (RuledReference plugin : additionalPlugins) {
            requires.add(toRequire(plugin, false));
         }
      }
   }

   static void moveStrictRequirementsToProductXML(final List<Require> requires, final Element features,
      final Element plugins) {
      for (Iterator<Require> it = requires.iterator(); it.hasNext();) {
         final Require require = it.next();

         final VersionRange range = new VersionRange(require.getRange());
         if (range.isExact()) {
            final String name = require.getName();

            final boolean feature = name.endsWith(".feature.group");

            final String id = feature ? name.substring(0, name.length() - 14) : name;

            String version = range.getLeft().toString();
            if ("0.0.0".equals(version) || version.endsWith(".qualifier")) {
               version = null;
            }

            Element parent;
            Element element;

            if (feature) {
               parent = features;
               element = features.getOwnerDocument().createElement("feature");
            }
            else {
               parent = plugins;
               element = plugins.getOwnerDocument().createElement("plugin");
            }

            element.setAttribute("id", id);
            if (version != null) {
               element.setAttribute("version", version);
            }

            parent.appendChild(element);

            it.remove();
         }
      }
   }

   private PluginProject resolveProductPlugin(final AbstractModule module, final ProductDefinition project) {
      final StrictReference reference = project.getProductPlugin();
      if (reference != null) {
         return module.resolveReference(reference, PluginsFacet.class);
      }
      return null;
   }

   private void copyProductResources(final File srcDir, final File destDir, PropertiesSource properties,
      String productId) {
      final PathMatcher matcher = converter.getResourceMatcherForProduct(properties, productId);

      org.sourcepit.common.utils.file.FileUtils.accept(srcDir, new FileVisitor() {
         @Override
         public boolean visit(final File file) {
            if (file.equals(srcDir)) {
               return true;
            }

            final boolean isDir = file.isDirectory();

            String path = PathUtils.getRelativePath(file, srcDir, "/");
            if (isDir) {
               path += "/";
            }
            if (matcher.isMatch(path)) {
               try {
                  final File destFile = new File(destDir, path);
                  if (isDir) {
                     FileUtils.copyDirectory(file, destFile);
                  }
                  else {
                     FileUtils.copyFile(file, destFile);
                  }
               }
               catch (IOException e) {
                  throw Exceptions.pipe(e);
               }

               return false;
            }
            return true;
         }
      });
   }

   private void copyIcons(final Document productDoc, ProductDefinition productDefinition,
      final PluginProject productPlugin) {
      // tycho icon bug fix
      for (Entry<String, String> entry : productDefinition.getAnnotation("product").getData()) {
         String key = entry.getKey();
         if (key.endsWith(".icon")) {
            final String os = key.substring(0, key.length() - ".icon".length());
            String path = entry.getValue();
            if (path.startsWith("/")) {
               path = path.substring(1);
            }

            final File iconFile = new File(productPlugin.getDirectory().getParentFile(), path);
            if (iconFile.exists()) {
               for (Node node : XmlUtils.queryNodes(productDoc, "product/launcher/" + os + "/ico")) {
                  Element element = (Element) node;
                  element.setAttribute("path", iconFile.getAbsolutePath());
               }
            }
         }
      }
   }

   private void addUpdateSites(final PropertiesMap p2Inf, String uid, PropertiesSource properties) {
      final List<String> sites = converter.getUpdateSitesForProduct(properties, uid);
      if (!sites.isEmpty()) {
         final Instruction instruction = new Instruction();
         instruction.setPhase("configure");
         for (String site : sites) {
            instruction.getActions().add(createAddRepoAction("0", site));
            instruction.getActions().add(createAddRepoAction("1", site));
         }
         p2Inf.put(instruction.getHeader(), instruction.getBody());
      }
   }

   private static Action createAddRepoAction(String type, String url) {
      final Action action = new Action();
      action.setName("addRepository");
      final PropertiesMap params = action.getParameters();
      params.put("type", type);
      params.put("location", url);
      params.put("enabled", "true");
      return action;
   }

   private static String getProductName(String productFileName) {
      return new Path(productFileName).getFileName();
   }
}
