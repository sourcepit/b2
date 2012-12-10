/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.common.base.Strings;

public class SitePropertiesQueryFactory
{
   public Map<String, PropertiesQuery> createPropertyQueries(PropertiesSource properties, String assemblyName,
      String assemblyClassifier, String categoryName)
   {
      final Map<String, PropertiesQuery> queries = new LinkedHashMap<String, PropertiesQuery>();
      putQueries(queries, assemblyName, assemblyClassifier, categoryName);
      return queries;
   }

   private void putQueries(final Map<String, PropertiesQuery> queries, String assemblyName, String assemblyClassifier,
      final String categoryName)
   {
      final boolean hasAssemblyClassifier = !Strings.isNullOrEmpty(assemblyClassifier);

      StringBuilder sb = new StringBuilder();
      sb.append("${categories");
      sb.append(createPropertySpacer(categoryName));
      sb.append("label}");
      if (hasAssemblyClassifier)
      {
         sb.append(" ${categories");
         sb.append(createPropertySpacer(categoryName));
         sb.append("classifierLabel}");
      }
      sb.append(" ${categories");
      sb.append(createPropertySpacer(categoryName));
      sb.append("labelAppendix}");

      PropertiesQuery query = createQuery(assemblyName, categoryName, false, "categoryName");
      query.setDefaultValue(sb.toString());
      queries.put("categories" + createPropertySpacer(categoryName) + "name", query);

      query = createQuery(assemblyName, categoryName, false, "categoryLabel");
      queries.put("categories" + createPropertySpacer(categoryName) + "label", query);
      query.getKeys().add("b2.module.name");
      query.getKeys().add("project.name");
      query.getKeys().add("project.artifactId");

      if (hasAssemblyClassifier)
      {
         query = createQuery(assemblyName, null, false, "classifierLabel");
         query.setDefaultValue(FeaturePropertiesQueryFactory.toClassifierLabel(assemblyClassifier));
         queries.put("categories" + createPropertySpacer(categoryName) + "classifierLabel", query);
      }

      query = createQuery(assemblyName, categoryName, true, "categoryLabelAppendix");
      queries.put("categories" + createPropertySpacer(categoryName) + "labelAppendix", query);

      query = createQuery(assemblyName, null, true, "description");
      queries.put("categories" + createPropertySpacer(categoryName) + "description", query);
   }

   private PropertiesQuery createQuery(String assemblyName, String categoryName, boolean addDefaultKey, String property)
   {
      final String preamble = "b2.assemblies";

      final PropertiesQuery query = new PropertiesQuery();
      query.setRetryWithoutPrefix(true);

      String p = categoryName == null ? property : categoryName + firstToUpper(property);
      query.getKeys().add(preamble + createPropertySpacer(assemblyName) + p);
      if (addDefaultKey)
      {
         query.getKeys().add(preamble + "." + p);
         query.getKeys().add("b2.module." + p);
      }
      query.setDefaultValue("");
      return query;
   }

   private static String firstToUpper(String string)
   {
      char[] stringArray = string.toCharArray();
      stringArray[0] = Character.toUpperCase(stringArray[0]);
      return new String(stringArray);
   }

   private String createPropertySpacer(String stringInTheMiddle)
   {
      return stringInTheMiddle == null || stringInTheMiddle.length() == 0 ? "." : "." + stringInTheMiddle + ".";
   }
}
