/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Named;

import com.google.common.base.Strings;

@Named
public class FeaturePropertiesQueryFactory
{
   public Map<String, PropertiesQuery> createPropertyQueries(boolean isAssemblyFeature, boolean isSourceFeature,
      String facetOrAssemblyName, String facetOrAssemblyClassifier)
   {
      final boolean hasClassifier = !Strings.isNullOrEmpty(facetOrAssemblyClassifier);

      final Map<String, PropertiesQuery> queries = new LinkedHashMap<String, PropertiesQuery>();
      final PropertiesQuery nameQuery = createQuery(isAssemblyFeature, facetOrAssemblyName, false, isSourceFeature
         ? "sourceFeatureName"
         : "featureName");

      if (hasClassifier)
      {
         nameQuery.setDefaultValue("${feature.label} ${feature.classifierLabel} ${feature.labelAppendix}");
      }
      else
      {
         nameQuery.setDefaultValue("${feature.label} ${feature.labelAppendix}");
      }
      queries.put("feature.name", nameQuery);

      final PropertiesQuery labelQuery = createQuery(isAssemblyFeature, facetOrAssemblyName, false, isSourceFeature
         ? "sourceFeatureLabel"
         : "featureLabel");

      if (isSourceFeature)
      {
         labelQuery.getKeys().add(
            createQuery(isAssemblyFeature, facetOrAssemblyName, false, "featureLabel").getKeys().iterator().next());
      }

      labelQuery.getKeys().add("b2.module.name");
      labelQuery.getKeys().add("project.name");
      labelQuery.getKeys().add("project.artifactId");
      queries.put("feature.label", labelQuery);

      if (hasClassifier)
      {
         final PropertiesQuery clsQuery = createQuery(isAssemblyFeature, facetOrAssemblyName, false, "classifierLabel");
         clsQuery.setDefaultValue(toClassifierLabel(facetOrAssemblyClassifier));
         queries.put("feature.classifierLabel", clsQuery);
      }

      if (isSourceFeature)
      {
         PropertiesQuery query = createQuery(isAssemblyFeature, facetOrAssemblyName, true, "sourceFeatureLabelAppendix");
         query.setDefaultValue("(Sources)");
         queries.put("feature.labelAppendix", query);
      }
      else
      {
         PropertiesQuery query = createQuery(isAssemblyFeature, facetOrAssemblyName, true, "featureLabelAppendix");
         query.setDefaultValue("");
         queries.put("feature.labelAppendix", query);
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

      return sb.toString();
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
      query.getKeys().add(preamble + createPropertySpacer(facetOrAssemblyName) + property);
      if (addDefaultKey)
      {
         query.getKeys().add(preamble + "." + property);
         query.getKeys().add("b2.module." + property);
      }
      query.setDefaultValue("");
      return query;
   }

   private String createPropertySpacer(String stringInTheMiddle)
   {
      return stringInTheMiddle == null || stringInTheMiddle.length() == 0 ? "." : "." + stringInTheMiddle + ".";
   }

}
