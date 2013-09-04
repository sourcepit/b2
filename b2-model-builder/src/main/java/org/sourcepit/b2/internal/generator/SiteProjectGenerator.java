/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;
import static org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils.getAssemblyClassifiers;
import static org.sourcepit.b2.model.module.internal.util.ReferenceUtils.toVersionRange;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.interpolation.AbstractValueSource;
import org.codehaus.plexus.util.xml.PrettyPrintXMLWriter;
import org.codehaus.plexus.util.xml.XMLWriter;
import org.osgi.framework.VersionRange;
import org.sourcepit.b2.generator.AbstractGeneratorForDerivedElements;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.internal.generator.p2.Action;
import org.sourcepit.b2.internal.generator.p2.Instruction;
import org.sourcepit.b2.internal.generator.p2.Require;
import org.sourcepit.b2.model.builder.util.BasicConverter.AggregatorMode;
import org.sourcepit.b2.model.builder.util.ProductsConverter;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.interpolation.internal.module.SitesInterpolator;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.StrictReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.common.utils.file.FileVisitor;
import org.sourcepit.common.utils.lang.Exceptions;
import org.sourcepit.common.utils.path.Path;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.path.PathUtils;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.props.PropertiesUtils;
import org.sourcepit.common.utils.xml.XmlUtils;
import org.sourcepit.tools.shared.resources.harness.StringInterpolator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

@Named
public class SiteProjectGenerator extends AbstractGeneratorForDerivedElements implements IB2GenerationParticipant
{
   private final SitePropertiesQueryFactory queryFactory;

   @Inject
   private ProductsConverter converter;

   @Inject
   public SiteProjectGenerator(SitePropertiesQueryFactory queryFactory)
   {
      this.queryFactory = queryFactory;
   }

   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.PROJECT_GENERATOR;
   }

   @Override
   protected void addTypesOfInputs(Collection<Class<? extends Derivable>> inputTypes)
   {
      inputTypes.add(SiteProject.class);
   }

   @Override
   protected void generate(Derivable inputElement, PropertiesSource source, ITemplates templates)
   {
      final SiteProject siteProject = (SiteProject) inputElement;

      final AbstractModule module = siteProject.getParent().getParent();

      final String assemblyName = getAssemblyName(siteProject);

      final List<ProductDefinition> productDefinitions = SitesInterpolator.getProductDefinitionsForAssembly(module,
         assemblyName);
      for (ProductDefinition productDefinition : productDefinitions)
      {
         processProduct(siteProject.getDirectory(), productDefinition, source);
      }

      final String assemblyClassifier = getAssemblyClassifier(siteProject);

      final Properties properties = new Properties();
      properties.setProperty("site.id", siteProject.getId());
      properties.setProperty("site.version", siteProject.getVersion());
      properties.setProperty("site.classifier", assemblyClassifier == null ? "" : assemblyClassifier);
      insertCategoriesProperty(siteProject, properties);
      templates.copy("site-project", siteProject.getDirectory(), properties);
      generateProperties(source, siteProject, assemblyName, assemblyClassifier);
   }

   private void processProduct(File projectDir, ProductDefinition productDefinition, PropertiesSource properties)
   {
      final AbstractModule module = productDefinition.getParent().getParent();

      final String uid = productDefinition.getAnnotationData("product", "uid");

      File srcFile = productDefinition.getFile();
      File productFile = new File(projectDir, srcFile.getName());
      try
      {
         projectDir.mkdirs();
         productFile.createNewFile();
         FileUtils.copyFile(srcFile, productFile);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      injectUIdAndVersion(productFile, uid, productDefinition.getAnnotationData("product", "version"));

      for (final String classifier : getAssemblyClassifiers(productDefinition))
      {
         final Document productDoc = XmlUtils.readXml(productFile);

         final List<Require> requires = new ArrayList<Require>();

         final Element features = getFeaturesNode(productDoc);
         convertProductIncludesFromXML(requires, uid, features, true, properties);

         final Element plugins = getPluginsNode(productDoc);
         convertProductIncludesFromXML(requires, uid, plugins, false, properties);

         collectProductIncludes(requires, module, uid, classifier, properties);

         // move strict dependencies back to XML
         for (Iterator<Require> it = requires.iterator(); it.hasNext();)
         {
            final Require require = it.next();

            final VersionRange range = new VersionRange(require.getRange());
            if (range.isExact())
            {
               final String name = require.getName();

               final boolean feature = name.endsWith(".feature.group");

               final String id = feature ? name.substring(0, name.length() - 14) : name;

               String version = range.getLeft().toString();
               if ("0.0.0".equals(version) || version.endsWith(".qualifier"))
               {
                  version = null;
               }

               Element parent;
               Element element;

               if (feature)
               {
                  parent = features;
                  element = features.getOwnerDocument().createElement("feature");
               }
               else
               {
                  parent = plugins;
                  element = plugins.getOwnerDocument().createElement("plugin");
               }

               element.setAttribute("id", id);
               if (version != null)
               {
                  element.setAttribute("version", version);
               }

               parent.appendChild(element);

               it.remove();
            }
         }

         XmlUtils.writeXml(productDoc, productFile);

         final PluginProject productPlugin = resolveProductPlugin(module, productDefinition);
         if (productPlugin != null)
         {
            copyProductResources(productPlugin.getDirectory(), projectDir, properties, uid);
            copyIcons(productDoc, productDefinition, productPlugin);
         }

         final File p2InfFile = new File(projectDir, getProductName(productFile.getName()) + ".p2.inf");

         final PropertiesMap p2Inf = new LinkedPropertiesMap();
         if (p2InfFile.exists())
         {
            p2Inf.load(p2InfFile);
         }
         
         int i = 0;
         for (Require require : requires)
         {
            require.put(p2Inf, i);
            i++;
         }

         addUpdateSites(p2Inf, uid, properties);
         
         p2Inf.store(p2InfFile);
      }
   }

   void convertProductIncludesFromXML(final List<Require> requires, final String productId,
      final Element featuresOrPlugins, boolean features, PropertiesSource properties)
   {
      final VersionMatchRule defaultVersionMatchRule = converter.getDefaultVersionMatchRuleForProduct(properties,
         productId);
      for (Node node : getChildNodes(featuresOrPlugins))
      {
         featuresOrPlugins.removeChild(node);

         if (node instanceof Element)
         {
            final Element feature = (Element) node;

            final String featureId = feature.getAttribute("id");
            checkState(featureId.length() > 0);

            String version = feature.getAttribute("version");
            if (isNullOrEmpty(version))
            {
               version = "0.0.0";
            }

            VersionMatchRule matchRule = converter.getVersionMatchRuleForProductInclude(properties, productId,
               featureId, defaultVersionMatchRule);

            requires.add(toRequire(featureId, features, version, matchRule));
         }
      }
   }

   private void collectProductIncludes(final List<Require> requires, final AbstractModule module,
      final String productId, final String classifier, PropertiesSource properties)
   {
      final VersionMatchRule defaultVersionMatchRule = converter.getDefaultVersionMatchRuleForProduct(properties,
         productId);

      appendProductIncludesFromAssembly(requires, module, productId, classifier, properties, defaultVersionMatchRule);

      collectProductIncludesfromProperties(requires, productId, properties, defaultVersionMatchRule);
   }

   void collectProductIncludesfromProperties(final List<Require> requires, final String productId,
      PropertiesSource properties, final VersionMatchRule defaultVersionMatchRule)
   {
      for (RuledReference feature : converter.getIncludedFeaturesForProduct(properties, productId,
         defaultVersionMatchRule))
      {
         requires.add(toRequire(feature, true));
      }

      final List<RuledReference> additionalPlugins = converter.getIncludedPluginsForProduct(properties, productId,
         defaultVersionMatchRule);
      if (!additionalPlugins.isEmpty())
      {
         for (RuledReference plugin : additionalPlugins)
         {
            requires.add(toRequire(plugin, false));
         }
      }
   }

   private void appendProductIncludesFromAssembly(final List<Require> requires, final AbstractModule module,
      final String productId, final String classifier, PropertiesSource properties,
      final VersionMatchRule defaultVersionMatchRule)
   {
      final FeatureProject assemblyFeature = findAssemblyFeatureForClassifier(module, classifier);
      if (assemblyFeature == null)
      {
         throw new IllegalStateException("Cannot determine assembly feature for classifier '" + classifier + "'");
      }

      final Optional<String> oAssemblyName = getAssemblyNameByClassifier(properties, classifier,
         B2MetadataUtils.getAssemblyNames(assemblyFeature));
      if (!oAssemblyName.isPresent())
      {
         throw new IllegalStateException("Cannot determine assembly name for classifier '" + classifier + "'");
      }
      final String assemblyName = oAssemblyName.get();

      final AggregatorMode mode = converter.getAggregatorMode(properties, assemblyName);
      switch (mode)
      {
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

   void copyIcons(final Document productDoc, ProductDefinition productDefinition, final PluginProject productPlugin)
   {
      // tycho icon bug fix
      for (Entry<String, String> entry : productDefinition.getAnnotation("product").getData())
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
                  Element element = (Element) node;
                  element.setAttribute("path", iconFile.getAbsolutePath());
               }
            }
         }
      }
   }

   private static Require toRequire(RuledReference reference, boolean feature)
   {
      return toRequire(reference.getId(), feature, reference.getVersion(), reference.getVersionMatchRule());
   }

   private static Require toRequire(final String pluginOrFeatureId, boolean feature, String version,
      VersionMatchRule versionMatchRule)
   {
      final Require require = new Require();
      require.setNamespace("org.eclipse.equinox.p2.iu");
      if (feature)
      {
         require.setName(pluginOrFeatureId + ".feature.group");
      }
      else
      {
         require.setName(pluginOrFeatureId);
      }
      require.setRange(toVersionRange(version, versionMatchRule).toString());
      return require;
   }

   private static void injectUIdAndVersion(File productFile, String uid, String version)
   {
      final Document productXml = XmlUtils.readXml(productFile);
      final Element productElem = productXml.getDocumentElement();
      productElem.setAttribute("uid", uid);
      productElem.setAttribute("version", version);
      XmlUtils.writeXml(productXml, productFile);
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

   private void appendAggregated(List<Require> requires, String productId, FeatureProject assemblyFeature,
      PropertiesSource properties, VersionMatchRule defaultVersionMatchRule)
   {
      appendFeature(requires, productId, assemblyFeature.getId(), assemblyFeature.getVersion(), properties,
         defaultVersionMatchRule);
   }

   private void appendUnwrapped(List<Require> requires, String productId, FeatureProject assemblyFeature,
      PropertiesSource properties, VersionMatchRule defaultVersionMatchRule)
   {
      for (FeatureInclude featureInclude : assemblyFeature.getIncludedFeatures())
      {
         appendFeature(requires, productId, featureInclude.getId(), featureInclude.getVersion(), properties,
            defaultVersionMatchRule);
      }

      for (RuledReference ruledReference : assemblyFeature.getRequiredFeatures())
      {
         requires.add(toRequire(ruledReference, true));
      }
   }

   private void appendFeature(List<Require> requires, String productId, String featureId, String version,
      PropertiesSource properties, VersionMatchRule defaultVersionMatchRule)
   {
      final VersionMatchRule rule = converter.getVersionMatchRuleForProductInclude(properties, productId, featureId,
         defaultVersionMatchRule);
      requires.add(toRequire(featureId, true, version, rule));
   }

   private void addUpdateSites(final PropertiesMap p2Inf, String uid, PropertiesSource properties)
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
         p2Inf.put(instruction.getHeader(), instruction.getBody());
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

   private static FeatureProject findAssemblyFeatureForClassifier(final AbstractModule module, String classifier)
   {
      for (FeaturesFacet featuresFacet : module.getFacets(FeaturesFacet.class))
      {
         for (FeatureProject featureProject : featuresFacet.getProjects())
         {
            final int idx = B2MetadataUtils.getAssemblyClassifiers(featureProject).indexOf(classifier);
            if (idx > -1)
            {
               return featureProject;
            }
         }
      }
      return null;
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

   public static String getAssemblyName(SiteProject site)
   {
      List<String> assemblyNames = B2MetadataUtils.getAssemblyNames(site);
      String assemblyName = assemblyNames.isEmpty() ? null : assemblyNames.get(0);
      return assemblyName == null ? null : assemblyName;
   }

   public static String getAssemblyClassifier(SiteProject site)
   {
      List<String> assemblyClassifiers = B2MetadataUtils.getAssemblyClassifiers(site);
      String assemblyClassifier = assemblyClassifiers.isEmpty() ? null : assemblyClassifiers.get(0);
      return assemblyClassifier == null ? null : assemblyClassifier;
   }

   private void insertCategoriesProperty(final SiteProject siteProject, final Properties properties)
   {
      final Map<String, Set<String>> fullIUIdToCategoriesMap = new HashMap<String, Set<String>>();
      final Map<String, AbstractReference> fullIUIdToRefMap = new HashMap<String, AbstractReference>();

      for (Category category : siteProject.getCategories())
      {
         final String categoryName = category.getName();

         for (AbstractReference iu : category.getInstallableUnits())
         {
            put(fullIUIdToCategoriesMap, fullIUIdToRefMap, iu, categoryName);
         }
      }

      for (AbstractStrictReference iu : siteProject.getInstallableUnits())
      {
         put(fullIUIdToCategoriesMap, fullIUIdToRefMap, iu, "");
      }

      final StringWriter includes = new StringWriter();
      XMLWriter xml = new PrettyPrintXMLWriter(includes);
      for (Entry<String, Set<String>> entry : fullIUIdToCategoriesMap.entrySet())
      {
         Set<String> categoryNames = entry.getValue();
         final AbstractReference iu = fullIUIdToRefMap.get(entry.getKey());
         addInstallableUnit(xml, iu, categoryNames);
      }

      for (Category category : siteProject.getCategories())
      {
         final String categoryName = category.getName() == null ? "public" : category.getName();
         String propertySpacer = createPropertySpacer(category.getName());
         xml.startElement("category-def");
         xml.addAttribute("name", categoryName);
         xml.addAttribute("label", "%categories" + propertySpacer + "name");
         xml.startElement("description");
         xml.writeText("%categories" + propertySpacer + "description");
         xml.endElement();
         xml.endElement();
      }

      includes.flush();
      properties.setProperty("site.categories", includes.toString());
   }

   private void addInstallableUnit(XMLWriter xml, final AbstractReference installableUnit, Set<String> categoryNames)
   {
      if (installableUnit instanceof FeatureInclude)
      {
         xml.startElement("feature");
         xml.addAttribute("url", "features/" + installableUnit.getId() + ".jar");
      }
      else if (installableUnit instanceof PluginInclude)
      {
         xml.startElement("bundle");
      }
      else if (installableUnit instanceof StrictReference)
      {
         xml.startElement("iu");
      }
      else
      {
         throw new IllegalStateException(installableUnit.getClass() + " currently not supported in update sites");
      }

      xml.addAttribute("id", installableUnit.getId());
      xml.addAttribute("version", installableUnit.getVersion());
      for (String categoryName : categoryNames)
      {
         if (!Strings.isNullOrEmpty(categoryName))
         {
            xml.startElement("category");
            xml.addAttribute("name", categoryName);
            xml.endElement();
         }
      }

      xml.endElement();
   }

   private void put(final Map<String, Set<String>> fullFeatureIdToCategoriesMap,
      final Map<String, AbstractReference> fullFeatureIdToRefMap, AbstractReference featureRef,
      final String categoryName)
   {
      final String featureId = featureRef.getId();
      final String version = featureRef.getVersion();

      final String fully = featureId + "_" + version;
      Set<String> set = fullFeatureIdToCategoriesMap.get(fully);
      if (set == null)
      {
         set = new LinkedHashSet<String>();
         fullFeatureIdToCategoriesMap.put(fully, set);
      }
      set.add(categoryName);
      fullFeatureIdToRefMap.put(fully, featureRef);
   }

   private void generateProperties(PropertiesSource properties, final SiteProject siteProject, String assemblyName,
      String assemblyClassifier)
   {
      final AbstractModule module = siteProject.getParent().getParent();
      final File projectDir = siteProject.getDirectory();
      for (Category category : siteProject.getCategories())
      {
         final String categoryName = category.getName();

         final Map<String, PropertiesQuery> propertyQueries = queryFactory.createPropertyQueries(properties,
            assemblyName, assemblyClassifier, categoryName);

         for (Locale locale : module.getLocales())
         {
            String nlsSuffix = locale.toString();
            if (nlsSuffix.length() > 0)
            {
               nlsSuffix = "_" + nlsSuffix;
            }

            final File propertiesFile = new File(projectDir, "site" + nlsSuffix + ".properties");
            try
            {
               propertiesFile.createNewFile();
            }
            catch (IOException e)
            {
               throw new IllegalStateException(e);
            }
            Properties tmp = new Properties();
            insertNlsProperties(propertyQueries, locale, properties, tmp);
            final Properties siteProperties = PropertiesUtils.load(propertiesFile);

            String keyLabel = "categories" + createPropertySpacer(categoryName) + "name";
            siteProperties.setProperty(keyLabel, tmp.getProperty(keyLabel, ""));

            String keyDescription = "categories" + createPropertySpacer(categoryName) + "description";
            siteProperties.setProperty(keyDescription, tmp.getProperty(keyDescription, ""));
            PropertiesUtils.store(siteProperties, propertiesFile);
         }
      }
   }

   private void insertNlsProperties(final Map<String, PropertiesQuery> queries, Locale locale,
      final PropertiesSource properties, final Properties siteProperties)
   {
      final String nlsPrefix = createNlsPrefix(locale);

      StringInterpolator s = new StringInterpolator();
      s.setEscapeString("\\");
      s.getValueSources().add(new AbstractValueSource(false)
      {
         public Object getValue(String expression)
         {
            return properties.get(expression);
         }
      });
      s.getValueSources().add(new AbstractValueSource(false)
      {
         public Object getValue(String expression)
         {
            PropertiesQuery query = queries.get(expression);
            if (query != null)
            {
               query.setPrefix(nlsPrefix);
               return query.lookup(properties);
            }
            return null;
         }
      });


      for (Entry<String, PropertiesQuery> entry : queries.entrySet())
      {
         final String key = entry.getKey();
         final PropertiesQuery query = entry.getValue();

         query.setPrefix(nlsPrefix);

         String value = s.interpolate(query.lookup(properties));

         // HACK
         if (key.startsWith("categories.") && key.endsWith(".name"))
         {
            value = removeRedundantWS(value);
         }

         siteProperties.setProperty(key, value);
      }
   }

   private static String removeRedundantWS(String value)
   {
      final StringBuilder sb = new StringBuilder(value.length());
      final char[] chars = value.toCharArray();
      int ws = 0;
      for (char c : chars)
      {
         boolean isWs = Character.isWhitespace(c);
         if (isWs)
         {
            if (ws == 0)
            {
               sb.append(c);
            }
            ws++;
         }
         else
         {
            ws = 0;
            sb.append(c);
         }
      }
      return sb.toString().trim();
   }

   private String createPropertySpacer(String stringInTheMiddle)
   {
      return stringInTheMiddle == null || stringInTheMiddle.length() == 0 ? "." : "." + stringInTheMiddle + ".";
   }

   private String createNlsPrefix(Locale locale)
   {
      String nlsPrefix = locale.toString();
      if (nlsPrefix.length() > 0)
      {
         nlsPrefix = "nls_" + nlsPrefix + ".";
      }
      return nlsPrefix;
   }
}
