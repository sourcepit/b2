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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.codehaus.plexus.interpolation.AbstractValueSource;
import org.codehaus.plexus.util.xml.PrettyPrintXMLWriter;
import org.codehaus.plexus.util.xml.XMLWriter;
import org.sourcepit.b2.generator.AbstractGeneratorForDerivedElements;
import org.sourcepit.b2.generator.GeneratorType;
import org.sourcepit.b2.generator.IB2GenerationParticipant;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.AbstractReference;
import org.sourcepit.b2.model.module.AbstractStrictReference;
import org.sourcepit.b2.model.module.Category;
import org.sourcepit.b2.model.module.Derivable;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.props.PropertiesUtils;
import org.sourcepit.tools.shared.resources.harness.StringInterpolator;

import com.google.common.base.Strings;

@Named
public class SiteProjectGenerator extends AbstractGeneratorForDerivedElements implements IB2GenerationParticipant
{
   private final SitePropertiesQueryFactory queryFactory;

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

      final String assemblyName = getAssemblyName(siteProject);

      final String assemblyClassifier = getAssemblyClassifier(siteProject);

      final Properties properties = new Properties();
      properties.setProperty("site.id", siteProject.getId());
      properties.setProperty("site.version", siteProject.getVersion());
      properties.setProperty("site.classifier", assemblyClassifier == null ? "" : assemblyClassifier);
      insertCategoriesProperty(siteProject, properties);
      templates.copy("site-project", siteProject.getDirectory(), properties);
      generateProperties(source, siteProject, assemblyName, assemblyClassifier);
   }

   private static String getAssemblyName(SiteProject site)
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
      final Map<String, Set<String>> fullFeatureIdToCategoriesMap = new HashMap<String, Set<String>>();
      final Map<String, AbstractReference> fullFeatureIdToRefMap = new HashMap<String, AbstractReference>();

      for (Category category : siteProject.getCategories())
      {
         final String categoryName = category.getName();

         for (AbstractReference featureRef : category.getInstallableUnits())
         {
            put(fullFeatureIdToCategoriesMap, fullFeatureIdToRefMap, featureRef, categoryName);
         }
      }

      for (AbstractStrictReference featureRef : siteProject.getInstallableUnits())
      {
         put(fullFeatureIdToCategoriesMap, fullFeatureIdToRefMap, featureRef, "");
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
            if (!Strings.isNullOrEmpty(categoryName))
            {
               xml.startElement("category");
               xml.addAttribute("name", categoryName);
               xml.endElement();
            }
         }
         xml.endElement();
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
