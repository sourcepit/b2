/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.codehaus.plexus.interpolation.ValueSource;
import org.codehaus.plexus.util.xml.PrettyPrintXMLWriter;
import org.codehaus.plexus.util.xml.XMLWriter;
import org.sourcepit.b2.generator.AbstractGeneratorForDerivedElements;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.props.PropertiesUtils;

@Named
public class SiteProjectGenerator extends AbstractGeneratorForDerivedElements implements IB2GenerationParticipant
{
   @Inject
   private BasicConverter converter;

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
   protected void generate(Derivable inputElement, IConverter converter, ITemplates templates)
   {
      final SiteProject siteProject = (SiteProject) inputElement;

      final String classifier = getClassifier(this.converter, converter.getProperties(), siteProject);

      final Properties properties = new Properties();
      properties.setProperty("site.id", siteProject.getId());
      properties.setProperty("site.version", siteProject.getVersion());
      properties.setProperty("site.classifier", classifier == null ? "" : classifier);
      insertCategoriesProperty(siteProject, properties);
      templates.copy("site-project", siteProject.getDirectory(), properties);
      generateProperties(converter, siteProject);
   }

   // TODO legacy compatibility
   public static String getClassifier(BasicConverter converter, PropertiesSource properties, SiteProject site)
   {
      Set<String> assemblyNames = B2MetadataUtils.getAssemblyNames(site);
      String assemblyName = assemblyNames.isEmpty() ? null : assemblyNames.iterator().next();
      return assemblyName == null ? null : converter.getAssemblyClassifier(properties, assemblyName);
   }

   private void insertCategoriesProperty(final SiteProject siteProject, final Properties properties)
   {
      final Map<String, Set<String>> fullFeatureIdToCategoriesMap = new HashMap<String, Set<String>>();
      final Map<String, AbstractReference> fullFeatureIdToRefMap = new HashMap<String, AbstractReference>();

      for (Category category : siteProject.getCategories())
      {
         final String categoryName = category.getName();

         for (AbstractReference featureRef : category.getFeatureReferences())
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
      }

      final StringWriter includes = new StringWriter();
      XMLWriter xml = new PrettyPrintXMLWriter(includes);
      for (Entry<String, Set<String>> entry : fullFeatureIdToCategoriesMap.entrySet())
      {
         final AbstractReference featureRef = fullFeatureIdToRefMap.get(entry.getKey());
         xml.startElement("feature");
         xml.addAttribute("url", "features/" + entry.getKey() + ".jar");
         xml.addAttribute("id", featureRef.getId());
         xml.addAttribute("version", featureRef.getVersion());
         for (String categoryName : entry.getValue())
         {
            xml.startElement("category");
            xml.addAttribute("name", categoryName);
            xml.endElement();
         }
         xml.endElement();
      }

      for (Category category : siteProject.getCategories())
      {
         final String categoryName = category.getName() == null ? "public" : category.getName();
         String propertySpacer = createPropertySpacer(category.getName());
         xml.startElement("category-def");
         xml.addAttribute("name", categoryName);
         xml.addAttribute("label", "%category" + propertySpacer + "label");
         xml.startElement("description");
         xml.writeText("%category" + propertySpacer + "description");
         xml.endElement();
         xml.endElement();
      }

      includes.flush();
      properties.setProperty("site.categories", includes.toString());
   }

   private void generateProperties(IConverter converter, final SiteProject siteProject)
   {
      final AbstractModule module = siteProject.getParent().getParent();
      final File projectDir = siteProject.getDirectory();
      for (Category category : siteProject.getCategories())
      {
         final String categoryId = category.getName();

         final Map<String, PropertiesQuery> propertyQueries = createNlsPropertyQueries(converter, categoryId);

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

            final Properties tmp = new Properties();
            insertNlsProperties(propertyQueries, locale, converter.getValueSources(), tmp);

            final Properties siteProperties = PropertiesUtils.load(propertiesFile);
            siteProperties.setProperty("category" + createPropertySpacer(categoryId) + "label",
               tmp.getProperty("category.label", ""));
            siteProperties.setProperty("category" + createPropertySpacer(categoryId) + "description",
               tmp.getProperty("category.description", ""));
            PropertiesUtils.store(siteProperties, propertiesFile);
         }
      }
   }

   private void insertNlsProperties(final Map<String, PropertiesQuery> queries, Locale locale,
      final Collection<ValueSource> valueSources, final Properties properties)
   {
      final String nlsPrefix = createNlsPrefix(locale);

      for (Entry<String, PropertiesQuery> entry : queries.entrySet())
      {
         final String key = entry.getKey();
         final PropertiesQuery query = entry.getValue();

         query.setPrefix(nlsPrefix);

         if (key.equals("category.label"))
         {
            final PropertiesQuery clsLabelQuery = queries.get("category.classifier.label");
            clsLabelQuery.setPrefix(nlsPrefix);

            String label = query.lookup(valueSources);
            String clsLabel = clsLabelQuery.lookup(valueSources);

            if (clsLabel.length() > 0)
            {
               label += " " + clsLabel;
            }
            properties.setProperty("category.label", label);
         }
         else
         {
            properties.setProperty(key, query.lookup(valueSources));
         }
      }
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
      queries.put("category.label", labelQuery);
      queries.put("category.classifier.label", clsQuery);
      putQuery(queries, classifier, true, "description");
      return queries;
   }

   private PropertiesQuery putQuery(Map<String, PropertiesQuery> queries, String classifier, boolean addDefaultKey,
      String property)
   {
      final PropertiesQuery query = createQuery(classifier, addDefaultKey, property);
      queries.put("category" + createPropertySpacer(classifier) + property, query);
      return query;
   }

   private PropertiesQuery createQuery(String classifier, boolean addDefaultKey, String property)
   {
      final PropertiesQuery query = new PropertiesQuery();
      query.setRetryWithoutPrefix(true);
      query.addKey("category" + createPropertySpacer(classifier) + property);
      if (addDefaultKey)
      {
         query.addKey("category." + property);
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
