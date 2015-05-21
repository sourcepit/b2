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

import java.util.LinkedHashMap;
import java.util.Map;

import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.common.base.Strings;

public class SitePropertiesQueryFactory {
   public Map<String, PropertiesQuery> createPropertyQueries(PropertiesSource properties, String assemblyName,
      String assemblyClassifier, String categoryName) {
      final Map<String, PropertiesQuery> queries = new LinkedHashMap<String, PropertiesQuery>();
      putQueries(queries, assemblyName, assemblyClassifier, categoryName);
      return queries;
   }

   private void putQueries(final Map<String, PropertiesQuery> queries, String assemblyName, String assemblyClassifier,
      final String categoryName) {
      final boolean hasAssemblyClassifier = !Strings.isNullOrEmpty(assemblyClassifier);

      StringBuilder sb = new StringBuilder();
      sb.append("${categories");
      sb.append(createPropertySpacer(categoryName));
      sb.append("label}");
      if (hasAssemblyClassifier) {
         sb.append(" ${categories");
         sb.append(createPropertySpacer(categoryName));
         sb.append("classifierLabel}");
      }
      sb.append(" ${categories");
      sb.append(createPropertySpacer(categoryName));
      sb.append("labelAppendix}");

      PropertiesQuery query = createQuery(assemblyName, categoryName, false, "name");
      query.setDefaultValue(sb.toString());
      queries.put("categories" + createPropertySpacer(categoryName) + "name", query);

      query = createQuery(assemblyName, categoryName, false, "label");
      queries.put("categories" + createPropertySpacer(categoryName) + "label", query);
      query.getKeys().add("b2.module.name");
      query.getKeys().add("project.name");
      query.getKeys().add("project.artifactId");

      if (hasAssemblyClassifier) {
         query = createQuery(assemblyName, null, false, "classifierLabel");
         query.setDefaultValue(FeaturePropertiesQueryFactory.toClassifierLabel(assemblyClassifier));
         query.getKeys().add("b2.assemblies" + createPropertySpacer(assemblyClassifier) + "classifierLabel");
         queries.put("categories" + createPropertySpacer(categoryName) + "classifierLabel", query);
      }

      query = createQuery(assemblyName, categoryName, true, "labelAppendix");
      queries.put("categories" + createPropertySpacer(categoryName) + "labelAppendix", query);

      query = createQuery(assemblyName, categoryName, false, "description");
      query.getKeys().add("b2.module.description");
      query.getKeys().add("project.description");
      queries.put("categories" + createPropertySpacer(categoryName) + "description", query);
   }

   private PropertiesQuery createQuery(String assemblyName, String categoryName, boolean addDefaultKey, String property) {
      final String preamble = "b2.assemblies";

      final PropertiesQuery query = new PropertiesQuery();
      query.setRetryWithoutPrefix(true);

      String p = "categories." + (categoryName == null ? property : categoryName + "." + property);
      query.getKeys().add(preamble + createPropertySpacer(assemblyName) + p);
      if (addDefaultKey) {
         query.getKeys().add(preamble + "." + p);
         query.getKeys().add("b2.module." + p);
      }
      query.setDefaultValue("");
      return query;
   }

   private String createPropertySpacer(String stringInTheMiddle) {
      return stringInTheMiddle == null || stringInTheMiddle.length() == 0 ? "." : "." + stringInTheMiddle + ".";
   }
}
