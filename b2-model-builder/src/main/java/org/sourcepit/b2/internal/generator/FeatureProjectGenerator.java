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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.interpolation.ValueSource;
import org.codehaus.plexus.util.xml.PrettyPrintXMLWriter;
import org.codehaus.plexus.util.xml.XMLWriter;
import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.common.internal.utils.NlsUtils;
import org.sourcepit.b2.common.internal.utils.PropertiesUtils;
import org.sourcepit.b2.generator.AbstractGeneratorForDerivedElements;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.PluginInclude;

@Named
public class FeatureProjectGenerator extends AbstractGeneratorForDerivedElements implements IB2GenerationParticipant
{
   @Override
   public GeneratorType getGeneratorType()
   {
      return GeneratorType.PROJECT_GENERATOR;
   }

   @Override
   protected void addTypesOfInputs(Collection<Class<? extends Derivable>> inputTypes)
   {
      inputTypes.add(FeatureProject.class);
   }

   @Override
   protected void generate(Derivable inputElement, IConverter converter, ITemplates templates)
   {
      final Collection<ValueSource> valueSources = converter.getValueSources();
      final FeatureProject feature = (FeatureProject) inputElement;

      final String classifier = feature.getClassifier();

      final Properties featureProperties = new Properties();
      featureProperties.setProperty("feature.id", feature.getId());
      featureProperties.setProperty("feature.version", feature.getVersion());
      featureProperties.setProperty("feature.classifier", classifier == null ? "" : classifier);

      final Map<String, PropertiesQuery> queries = createNlsPropertyQueries(converter, classifier);

      insertNlsProperties(queries, NlsUtils.DEFAULT_LOCALE, valueSources, featureProperties);
      insertIncludesProperty(feature, featureProperties);
      insertPluginsProperty(feature, featureProperties);
      templates.copy("feature-project", feature.getDirectory(), featureProperties);

      generateNlsPropertyFiles(feature, valueSources, templates, queries);
   }

   private void generateNlsPropertyFiles(FeatureProject feature, Collection<ValueSource> valueSources,
      ITemplates templates, Map<String, PropertiesQuery> queries)
   {
      try
      {
         File projectDir = feature.getDirectory();
         final File workDir = new File(projectDir, ".b2");
         workDir.mkdirs();

         final List<File> nlsPropertyFiles = new ArrayList<File>();
         final EList<Locale> locales = feature.getParent().getParent().getLocales();
         for (Locale locale : locales)
         {
            if (locale.equals(NlsUtils.DEFAULT_LOCALE))
            {
               continue;
            }

            final Properties nlsProperties = new Properties();
            insertNlsProperties(queries, locale, valueSources, nlsProperties);

            templates.copy("feature-project/feature.properties", workDir, nlsProperties);

            String nlsSuffix = locale.toString();
            if (nlsSuffix.length() > 0)
            {
               nlsSuffix = "_" + nlsSuffix;
            }

            final File destFile = new File(projectDir, "feature" + nlsSuffix + ".properties");
            final File srcFile = new File(workDir, "feature.properties");
            FileUtils.copyFile(srcFile, destFile);
            FileUtils.forceDelete(srcFile);

            nlsPropertyFiles.add(destFile);
         }
         FileUtils.deleteDirectory(workDir);

         addToBinIncludes(projectDir, nlsPropertyFiles);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   public static void addToBinIncludes(File projectDir, final List<File> nlsPropertyFiles)
   {
      if (nlsPropertyFiles.isEmpty())
      {
         return;
      }
      final File propertiesFile = new File(projectDir, "build.properties");
      if (!propertiesFile.exists())
      {
         try
         {
            propertiesFile.createNewFile();
         }
         catch (IOException e)
         {
            throw new IllegalStateException(e);
         }
      }
      final Properties buildProperties = PropertiesUtils.load(propertiesFile);
      final Set<String> binIncludes = new LinkedHashSet<String>();
      final String rawIncludes = buildProperties.getProperty("bin.includes");
      if (rawIncludes != null)
      {
         for (String rawInclude : rawIncludes.split(","))
         {
            binIncludes.add(rawInclude.trim());
         }
      }
      for (File file : nlsPropertyFiles)
      {
         binIncludes.add(file.getName());
      }
      final StringBuilder sb = new StringBuilder();
      for (String include : binIncludes)
      {
         sb.append(include);
         sb.append(',');
      }
      sb.deleteCharAt(sb.length() - 1);
      buildProperties.setProperty("bin.includes", sb.toString());
      PropertiesUtils.store(buildProperties, propertiesFile);
   }

   private Map<String, PropertiesQuery> createNlsPropertyQueries(IConverter converter, final String classifier)
   {
      final PropertiesQuery labelQuery = createQuery(classifier, true, "label");
      labelQuery.addKey("module.name");
      labelQuery.addKey("project.name");
      labelQuery.addKey("project.artifactId");

      final PropertiesQuery clsQuery = createQuery(classifier, false, "classifier.label");
      clsQuery.setDefault(classifier == null ? "" : converter.toClassifierLabel(classifier));

      final Map<String, PropertiesQuery> queries = new LinkedHashMap<String, PropertiesQuery>();
      queries.put("feature.label", labelQuery);
      queries.put("feature.classifier.label", clsQuery);
      putQuery(queries, classifier, true, "providerName");
      putQuery(queries, classifier, true, "copyright");
      putQuery(queries, classifier, true, "copyrightURL");
      putQuery(queries, classifier, true, "description");
      putQuery(queries, classifier, true, "descriptionURL");
      putQuery(queries, classifier, true, "license");
      putQuery(queries, classifier, true, "licenseURL");
      return queries;
   }

   private void insertPluginsProperty(final FeatureProject feature, final Properties featureProperties)
   {
      final StringWriter plugins = new StringWriter();
      XMLWriter xml = new PrettyPrintXMLWriter(plugins);
      for (PluginInclude plugin : feature.getIncludedPlugins())
      {
         xml.startElement("plugin");
         xml.addAttribute("id", plugin.getId());
         xml.addAttribute("download-size", "0");
         xml.addAttribute("install-size", "0");
         xml.addAttribute("version", "0.0.0");
         xml.addAttribute("unpack", String.valueOf(plugin.isUnpack()));
         xml.endElement();
      }
      plugins.flush();
      featureProperties.setProperty("feature.plugins", plugins.toString());
   }

   private void insertIncludesProperty(final FeatureProject feature, final Properties featureProperties)
   {
      final StringWriter includes = new StringWriter();
      XMLWriter xml = new PrettyPrintXMLWriter(includes);
      for (FeatureInclude include : feature.getIncludedFeatures())
      {
         xml.startElement("includes");
         xml.addAttribute("id", include.getId());
         xml.addAttribute("version", "0.0.0");
         xml.endElement();
      }
      includes.flush();
      featureProperties.setProperty("feature.includes", includes.toString());
   }

   private void insertNlsProperties(final Map<String, PropertiesQuery> queries, Locale locale,
      final Collection<ValueSource> valueSources, final Properties featureProperties)
   {
      final String nlsPrefix = createNlsPrefix(locale);

      for (Entry<String, PropertiesQuery> entry : queries.entrySet())
      {
         final String key = entry.getKey();
         final PropertiesQuery query = entry.getValue();

         query.setPrefix(nlsPrefix);

         if (key.equals("feature.label"))
         {
            final PropertiesQuery clsLabelQuery = queries.get("feature.classifier.label");
            clsLabelQuery.setPrefix(nlsPrefix);

            String label = query.lookup(valueSources);
            String clsLabel = clsLabelQuery.lookup(valueSources);

            if (clsLabel.length() > 0)
            {
               label += " " + clsLabel;
            }
            featureProperties.setProperty("feature.label", label);
         }
         else
         {
            featureProperties.setProperty(key, query.lookup(valueSources));
         }
      }
   }

   private PropertiesQuery putQuery(Map<String, PropertiesQuery> queries, String classifier, boolean addDefaultKey,
      String property)
   {
      final PropertiesQuery query = createQuery(classifier, addDefaultKey, property);
      queries.put("feature." + property, query);
      return query;
   }

   private PropertiesQuery createQuery(String classifier, boolean addDefaultKey, String property)
   {
      final PropertiesQuery query = new PropertiesQuery();
      query.setRetryWithoutPrefix(true);
      query.addKey("feature" + createPropertySpacer(classifier) + property);
      if (addDefaultKey)
      {
         query.addKey("feature." + property);
      }
      query.setDefault("");
      return query;
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
