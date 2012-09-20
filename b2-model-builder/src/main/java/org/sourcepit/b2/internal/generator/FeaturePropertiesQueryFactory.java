/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class FeaturePropertiesQueryFactory
{
   private final BasicConverter converter;

   @Inject
   public FeaturePropertiesQueryFactory(BasicConverter converter)
   {
      this.converter = converter;
   }

   public Map<String, PropertiesQuery> createPropertyQueries(boolean isAssemblyFeature, boolean isSourceFeature,
      String facetOrAssemblyName, String facetOrAssemblyClassifier)
   {
      final PropertiesQuery labelQuery = createQuery(isAssemblyFeature, facetOrAssemblyName, false, "name");
      labelQuery.addKey("b2.module.name");
      labelQuery.addKey("project.name");
      labelQuery.addKey("project.artifactId");

      final PropertiesQuery clsQuery = createQuery(isAssemblyFeature, facetOrAssemblyName, false, "classifierLabel");
      clsQuery.setDefaultValue(toClassifierLabel(facetOrAssemblyClassifier));

      final Map<String, PropertiesQuery> queries = new LinkedHashMap<String, PropertiesQuery>();
      queries.put("feature.name", labelQuery);
      queries.put("feature.classifierLabel", clsQuery);

      if (isSourceFeature)
      {
         PropertiesQuery query = createQuery(isAssemblyFeature, facetOrAssemblyName, false,
            "sourceClassifierLabelAppendix");
         query.setDefaultValue("Sources");
         queries.put("feature.classifierLabelAppendix", query);

         // query = createQuery(isAssemblyFeature, facetOrAssemblyName, false, "sourceClassifierLabel");
         // queries.put("feature.customClassifierLabel", query);
      }

      putQuery(queries, isAssemblyFeature, facetOrAssemblyName, true, "providerName");
      putQuery(queries, isAssemblyFeature, facetOrAssemblyName, true, "copyright");
      putQuery(queries, isAssemblyFeature, facetOrAssemblyName, true, "copyrightURL");
      putQuery(queries, isAssemblyFeature, facetOrAssemblyName, true, "description");
      putQuery(queries, isAssemblyFeature, facetOrAssemblyName, true, "descriptionURL");
      putQuery(queries, isAssemblyFeature, facetOrAssemblyName, true, "license");
      putQuery(queries, isAssemblyFeature, facetOrAssemblyName, true, "licenseURL");
      return queries;
   }

   public static String toClassifierLabel(String classifier)
   {
      final StringBuilder sb = new StringBuilder();
      boolean nextUp = true;

      for (char c : classifier.toCharArray())
      {
         if (c == '.')
         {
            c = ' ';
         }
         sb.append(nextUp ? Character.toUpperCase(c) : c);
         nextUp = Character.isWhitespace(c);
      }

      //
      // if (classifier.length() > 1)
      // {
      // return classifier.substring(0, 1).toUpperCase() + classifier.substring(1).toLowerCase();
      // }
      return sb.toString();
   }

   private void insertNlsProperties(final Map<String, PropertiesQuery> queries, Locale locale,
      final PropertiesSource properties, boolean isSourceFeature)
   {
      final String nlsPrefix = createNlsPrefix(locale);

      for (Entry<String, PropertiesQuery> entry : queries.entrySet())
      {
         final String key = entry.getKey();
         final PropertiesQuery query = entry.getValue();

         query.setPrefix(nlsPrefix);

         if (key.equals("feature.name"))
         {
            final PropertiesQuery clsLabelQuery = queries.get("feature.classifierLabel");
            clsLabelQuery.setPrefix(nlsPrefix);

            String label = query.lookup(properties);
            String clsLabel = clsLabelQuery.lookup(properties);

            if (clsLabel.length() > 0)
            {
               label += " " + clsLabel;
            }
            // featureProperties.setProperty("feature.name", label);
         }
         else
         {
            // featureProperties.setProperty(key, query.lookup(properties));
         }
      }
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

   private PropertiesQuery putQuery(Map<String, PropertiesQuery> queries, boolean isAssemblyFeature,
      String facetOrAssemblyName, boolean addDefaultKey, String property)
   {
      final PropertiesQuery query = createQuery(isAssemblyFeature, facetOrAssemblyName, addDefaultKey, property);
      queries.put("feature." + property, query);
      return query;
   }

   private PropertiesQuery createQuery(boolean isAssemblyFeature, String facetOrAssemblyName, boolean addDefaultKey,
      String property)
   {
      final String preamble = "b2." + (isAssemblyFeature ? "assemblies" : "facets");

      final PropertiesQuery query = new PropertiesQuery();
      query.setRetryWithoutPrefix(true);
      query.addKey(preamble + createPropertySpacer(facetOrAssemblyName) + property);
      if (addDefaultKey)
      {
         query.addKey(preamble + "." + property);
         query.addKey("b2." + property);
      }
      query.setDefaultValue("");
      return query;
   }

   private String createPropertySpacer(String stringInTheMiddle)
   {
      return stringInTheMiddle == null || stringInTheMiddle.length() == 0 ? "." : "[\"" + stringInTheMiddle + "\"].";
   }

}
