/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Named;

@Named
public class FeaturePropertiesQueryFactory
{
   public Map<String, PropertiesQuery> createPropertyQueries(boolean isAssemblyFeature, boolean isSourceFeature,
      String facetOrAssemblyName, String facetOrAssemblyClassifier)
   {
      final PropertiesQuery nameQuery = createQuery(isAssemblyFeature, facetOrAssemblyName, false, "featureName");
      nameQuery.setDefaultValue("${feature.label} ${feature.classifierLabel} ${feature.classifierLabelAppendix}");

      final PropertiesQuery labelQuery = createQuery(isAssemblyFeature, facetOrAssemblyName, false, "featureLabel");
      labelQuery.addKey("b2.module.name");
      labelQuery.addKey("project.name");
      labelQuery.addKey("project.artifactId");

      final PropertiesQuery clsQuery = createQuery(isAssemblyFeature, facetOrAssemblyName, false, "classifierLabel");
      clsQuery.setDefaultValue(toClassifierLabel(facetOrAssemblyClassifier));

      final Map<String, PropertiesQuery> queries = new LinkedHashMap<String, PropertiesQuery>();
      queries.put("feature.name", nameQuery);
      queries.put("feature.label", labelQuery);
      queries.put("feature.classifierLabel", clsQuery);

      if (isSourceFeature)
      {
         PropertiesQuery query = createQuery(isAssemblyFeature, facetOrAssemblyName, true, "sourceClassifierLabel");
         query.setDefaultValue("Sources");
         queries.put("feature.classifierLabelAppendix", query);
      }
      else
      {
         PropertiesQuery query = createQuery(isAssemblyFeature, facetOrAssemblyName, false, "classifierLabelAppendix");
         query.setDefaultValue("");
         queries.put("feature.classifierLabelAppendix", query);
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
      return stringInTheMiddle == null || stringInTheMiddle.length() == 0 ? "." : "." + stringInTheMiddle + ".";
   }

}
