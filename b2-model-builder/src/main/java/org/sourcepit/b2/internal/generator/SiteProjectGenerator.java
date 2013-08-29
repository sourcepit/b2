/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import org.eclipse.emf.common.util.EMap;
import org.sourcepit.b2.generator.AbstractGeneratorForDerivedElements;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.internal.generator.p2.Action;
import org.sourcepit.b2.internal.generator.p2.Instruction;
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

      EMap<String, String> data = productDefinition.getAnnotation("product").getData();

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

      for (final String classifier : B2MetadataUtils.getAssemblyClassifiers(productDefinition))
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

         final PluginProject productPlugin = resolveProductPlugin(module, productDefinition);
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
