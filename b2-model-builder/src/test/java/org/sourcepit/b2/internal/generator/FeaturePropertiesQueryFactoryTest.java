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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.sisu.launch.InjectedTest;
import org.junit.Test;

public class FeaturePropertiesQueryFactoryTest extends InjectedTest {
   @Test
   public void testFacet() {
      final FeaturePropertiesQueryFactory queryFactory = lookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(false, false, "plugins", "");
      assertEquals(17, queries.size());

      PropertiesQuery query = queries.get("feature.name");
      assertNotNull(query);
      assertEquals("${feature.label} ${feature.labelAppendix}", query.getDefaultValue());
      Iterator<String> it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.featureName", it.next());

      query = queries.get("feature.label");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.featureLabel", it.next());
      assertEquals("b2.module.name", it.next());
      assertEquals("project.name", it.next());
      assertEquals("project.artifactId", it.next());

      query = queries.get("feature.labelAppendix");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.featureLabelAppendix", it.next());

      query = queries.get("feature.providerName");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.providerName", it.next());
      assertEquals("b2.facets.providerName", it.next());
      assertEquals("b2.module.providerName", it.next());

      query = queries.get("feature.copyright");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.copyright", it.next());
      assertEquals("b2.facets.copyright", it.next());
      assertEquals("b2.module.copyright", it.next());

      query = queries.get("feature.copyrightURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.copyrightURL", it.next());
      assertEquals("b2.facets.copyrightURL", it.next());
      assertEquals("b2.module.copyrightURL", it.next());

      query = queries.get("feature.description");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.description", it.next());
      assertEquals("b2.facets.description", it.next());
      assertEquals("b2.module.description", it.next());

      query = queries.get("feature.descriptionURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.descriptionURL", it.next());
      assertEquals("b2.facets.descriptionURL", it.next());
      assertEquals("b2.module.descriptionURL", it.next());

      query = queries.get("feature.license");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.license", it.next());
      assertEquals("b2.facets.license", it.next());
      assertEquals("b2.module.license", it.next());

      query = queries.get("feature.licenseURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.licenseURL", it.next());
      assertEquals("b2.facets.licenseURL", it.next());
      assertEquals("b2.module.licenseURL", it.next());

      query = queries.get("feature.plugin");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.brandingPlugin", it.next());
      assertEquals("b2.facets.brandingPlugin", it.next());
      assertEquals("b2.module.brandingPlugin", it.next());

      query = queries.get("feature.aboutText");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.aboutText", it.next());
      assertEquals("b2.facets.aboutText", it.next());
      assertEquals("b2.module.aboutText", it.next());

      query = queries.get("feature.featureImage");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.featureImage", it.next());
      assertEquals("b2.facets.featureImage", it.next());
      assertEquals("b2.module.featureImage", it.next());

      query = queries.get("feature.tipsAndTricksHref");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.tipsAndTricksHref", it.next());
      assertEquals("b2.facets.tipsAndTricksHref", it.next());
      assertEquals("b2.module.tipsAndTricksHref", it.next());

      query = queries.get("feature.windowImage");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.windowImage", it.next());
      assertEquals("b2.facets.windowImage", it.next());
      assertEquals("b2.module.windowImage", it.next());

      query = queries.get("feature.appName");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.appName", it.next());
      assertEquals("b2.facets.appName", it.next());
      assertEquals("b2.module.appName", it.next());

      query = queries.get("feature.welcomePerspective");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.welcomePerspective", it.next());
      assertEquals("b2.facets.welcomePerspective", it.next());
      assertEquals("b2.module.welcomePerspective", it.next());
   }

   @Test
   public void testFacetWithClassifier() {
      final FeaturePropertiesQueryFactory queryFactory = lookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(false, false, "plugins",
         "plugins");
      assertEquals(18, queries.size());

      PropertiesQuery query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      assertEquals("Plugins", query.getDefaultValue());

      query = queries.get("feature.labelAppendix");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
   }

   @Test
   public void testSourceFacet() {
      final FeaturePropertiesQueryFactory queryFactory = lookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(false, true, "plugins", "");
      assertEquals(17, queries.size());

      PropertiesQuery query = queries.get("feature.name");
      assertNotNull(query);

      Iterator<String> it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.sourceFeatureName", it.next());

      query = queries.get("feature.label");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.sourceFeatureLabel", it.next());
      assertEquals("b2.facets.plugins.featureLabel", it.next());
      assertEquals("b2.module.name", it.next());
      assertEquals("project.name", it.next());
      assertEquals("project.artifactId", it.next());

      query = queries.get("feature.labelAppendix");
      assertNotNull(query);
      assertEquals("(Sources)", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.sourceFeatureLabelAppendix", it.next());
      assertEquals("b2.facets.sourceFeatureLabelAppendix", it.next());
      assertEquals("b2.module.sourceFeatureLabelAppendix", it.next());

      query = queries.get("feature.providerName");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.providerName", it.next());
      assertEquals("b2.facets.providerName", it.next());
      assertEquals("b2.module.providerName", it.next());

      query = queries.get("feature.copyright");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.copyright", it.next());
      assertEquals("b2.facets.copyright", it.next());
      assertEquals("b2.module.copyright", it.next());

      query = queries.get("feature.copyrightURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.copyrightURL", it.next());
      assertEquals("b2.facets.copyrightURL", it.next());
      assertEquals("b2.module.copyrightURL", it.next());

      query = queries.get("feature.description");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.description", it.next());
      assertEquals("b2.facets.description", it.next());
      assertEquals("b2.module.description", it.next());

      query = queries.get("feature.descriptionURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.descriptionURL", it.next());
      assertEquals("b2.facets.descriptionURL", it.next());
      assertEquals("b2.module.descriptionURL", it.next());

      query = queries.get("feature.license");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.license", it.next());
      assertEquals("b2.facets.license", it.next());
      assertEquals("b2.module.license", it.next());

      query = queries.get("feature.licenseURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.licenseURL", it.next());
      assertEquals("b2.facets.licenseURL", it.next());
      assertEquals("b2.module.licenseURL", it.next());

      query = queries.get("feature.plugin");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.brandingPlugin", it.next());
      assertEquals("b2.facets.brandingPlugin", it.next());
      assertEquals("b2.module.brandingPlugin", it.next());

      query = queries.get("feature.aboutText");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.aboutText", it.next());
      assertEquals("b2.facets.aboutText", it.next());
      assertEquals("b2.module.aboutText", it.next());

      query = queries.get("feature.featureImage");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.featureImage", it.next());
      assertEquals("b2.facets.featureImage", it.next());
      assertEquals("b2.module.featureImage", it.next());

      query = queries.get("feature.tipsAndTricksHref");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.tipsAndTricksHref", it.next());
      assertEquals("b2.facets.tipsAndTricksHref", it.next());
      assertEquals("b2.module.tipsAndTricksHref", it.next());

      query = queries.get("feature.windowImage");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.windowImage", it.next());
      assertEquals("b2.facets.windowImage", it.next());
      assertEquals("b2.module.windowImage", it.next());

      query = queries.get("feature.appName");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.appName", it.next());
      assertEquals("b2.facets.appName", it.next());
      assertEquals("b2.module.appName", it.next());

      query = queries.get("feature.welcomePerspective");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.welcomePerspective", it.next());
      assertEquals("b2.facets.welcomePerspective", it.next());
      assertEquals("b2.module.welcomePerspective", it.next());
   }

   @Test
   public void testSourceFacetWithClassifier() {
      final FeaturePropertiesQueryFactory queryFactory = lookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(false, true, "plugins", "plugins");
      assertEquals(18, queries.size());

      PropertiesQuery query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      assertEquals("Plugins", query.getDefaultValue());

      query = queries.get("feature.labelAppendix");
      assertNotNull(query);
      assertEquals("(Sources)", query.getDefaultValue());
   }

   @Test
   public void testAssembly() {
      final FeaturePropertiesQueryFactory queryFactory = lookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(true, false, "plugins", "");
      assertEquals(17, queries.size());

      PropertiesQuery query = queries.get("feature.name");
      assertNotNull(query);

      Iterator<String> it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.featureName", it.next());

      query = queries.get("feature.label");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.featureLabel", it.next());
      assertEquals("b2.module.name", it.next());
      assertEquals("project.name", it.next());
      assertEquals("project.artifactId", it.next());

      query = queries.get("feature.labelAppendix");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.featureLabelAppendix", it.next());

      query = queries.get("feature.providerName");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.providerName", it.next());
      assertEquals("b2.assemblies.providerName", it.next());
      assertEquals("b2.module.providerName", it.next());

      query = queries.get("feature.copyright");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.copyright", it.next());
      assertEquals("b2.assemblies.copyright", it.next());
      assertEquals("b2.module.copyright", it.next());

      query = queries.get("feature.copyrightURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.copyrightURL", it.next());
      assertEquals("b2.assemblies.copyrightURL", it.next());
      assertEquals("b2.module.copyrightURL", it.next());

      query = queries.get("feature.description");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.description", it.next());
      assertEquals("b2.assemblies.description", it.next());
      assertEquals("b2.module.description", it.next());

      query = queries.get("feature.descriptionURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.descriptionURL", it.next());
      assertEquals("b2.assemblies.descriptionURL", it.next());
      assertEquals("b2.module.descriptionURL", it.next());

      query = queries.get("feature.license");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.license", it.next());
      assertEquals("b2.assemblies.license", it.next());
      assertEquals("b2.module.license", it.next());

      query = queries.get("feature.licenseURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.licenseURL", it.next());
      assertEquals("b2.assemblies.licenseURL", it.next());
      assertEquals("b2.module.licenseURL", it.next());

      query = queries.get("feature.plugin");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.brandingPlugin", it.next());
      assertEquals("b2.assemblies.brandingPlugin", it.next());
      assertEquals("b2.module.brandingPlugin", it.next());

      query = queries.get("feature.aboutText");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.aboutText", it.next());
      assertEquals("b2.assemblies.aboutText", it.next());
      assertEquals("b2.module.aboutText", it.next());

      query = queries.get("feature.featureImage");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.featureImage", it.next());
      assertEquals("b2.assemblies.featureImage", it.next());
      assertEquals("b2.module.featureImage", it.next());

      query = queries.get("feature.tipsAndTricksHref");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.tipsAndTricksHref", it.next());
      assertEquals("b2.assemblies.tipsAndTricksHref", it.next());
      assertEquals("b2.module.tipsAndTricksHref", it.next());

      query = queries.get("feature.windowImage");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.windowImage", it.next());
      assertEquals("b2.assemblies.windowImage", it.next());
      assertEquals("b2.module.windowImage", it.next());

      query = queries.get("feature.appName");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.appName", it.next());
      assertEquals("b2.assemblies.appName", it.next());
      assertEquals("b2.module.appName", it.next());

      query = queries.get("feature.welcomePerspective");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.welcomePerspective", it.next());
      assertEquals("b2.assemblies.welcomePerspective", it.next());
      assertEquals("b2.module.welcomePerspective", it.next());
   }

   @Test
   public void testAssemblyWithClassifier() {
      final FeaturePropertiesQueryFactory queryFactory = lookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(true, false, "plugins", "plugins");
      assertEquals(18, queries.size());

      PropertiesQuery query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      assertEquals("Plugins", query.getDefaultValue());

      query = queries.get("feature.labelAppendix");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
   }

   @Test
   public void testSourceAssembly() {
      final FeaturePropertiesQueryFactory queryFactory = lookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(true, true, "plugins", "");
      assertEquals(17, queries.size());

      PropertiesQuery query = queries.get("feature.name");
      assertNotNull(query);
      assertEquals("${feature.label} ${feature.labelAppendix}", query.getDefaultValue());
      Iterator<String> it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.sourceFeatureName", it.next());

      query = queries.get("feature.label");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.sourceFeatureLabel", it.next());
      assertEquals("b2.assemblies.plugins.featureLabel", it.next());
      assertEquals("b2.module.name", it.next());
      assertEquals("project.name", it.next());
      assertEquals("project.artifactId", it.next());

      query = queries.get("feature.labelAppendix");
      assertNotNull(query);
      assertEquals("(Sources)", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.sourceFeatureLabelAppendix", it.next());
      assertEquals("b2.assemblies.sourceFeatureLabelAppendix", it.next());
      assertEquals("b2.module.sourceFeatureLabelAppendix", it.next());

      query = queries.get("feature.providerName");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.providerName", it.next());
      assertEquals("b2.assemblies.providerName", it.next());
      assertEquals("b2.module.providerName", it.next());

      query = queries.get("feature.copyright");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.copyright", it.next());
      assertEquals("b2.assemblies.copyright", it.next());
      assertEquals("b2.module.copyright", it.next());

      query = queries.get("feature.copyrightURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.copyrightURL", it.next());
      assertEquals("b2.assemblies.copyrightURL", it.next());
      assertEquals("b2.module.copyrightURL", it.next());

      query = queries.get("feature.description");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.description", it.next());
      assertEquals("b2.assemblies.description", it.next());
      assertEquals("b2.module.description", it.next());

      query = queries.get("feature.descriptionURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.descriptionURL", it.next());
      assertEquals("b2.assemblies.descriptionURL", it.next());
      assertEquals("b2.module.descriptionURL", it.next());

      query = queries.get("feature.license");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.license", it.next());
      assertEquals("b2.assemblies.license", it.next());
      assertEquals("b2.module.license", it.next());

      query = queries.get("feature.licenseURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.licenseURL", it.next());
      assertEquals("b2.assemblies.licenseURL", it.next());
      assertEquals("b2.module.licenseURL", it.next());

      query = queries.get("feature.plugin");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.brandingPlugin", it.next());
      assertEquals("b2.assemblies.brandingPlugin", it.next());
      assertEquals("b2.module.brandingPlugin", it.next());

      query = queries.get("feature.aboutText");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.aboutText", it.next());
      assertEquals("b2.assemblies.aboutText", it.next());
      assertEquals("b2.module.aboutText", it.next());

      query = queries.get("feature.featureImage");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.featureImage", it.next());
      assertEquals("b2.assemblies.featureImage", it.next());
      assertEquals("b2.module.featureImage", it.next());

      query = queries.get("feature.tipsAndTricksHref");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.tipsAndTricksHref", it.next());
      assertEquals("b2.assemblies.tipsAndTricksHref", it.next());
      assertEquals("b2.module.tipsAndTricksHref", it.next());

      query = queries.get("feature.windowImage");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.windowImage", it.next());
      assertEquals("b2.assemblies.windowImage", it.next());
      assertEquals("b2.module.windowImage", it.next());

      query = queries.get("feature.appName");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.appName", it.next());
      assertEquals("b2.assemblies.appName", it.next());
      assertEquals("b2.module.appName", it.next());

      query = queries.get("feature.welcomePerspective");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.welcomePerspective", it.next());
      assertEquals("b2.assemblies.welcomePerspective", it.next());
      assertEquals("b2.module.welcomePerspective", it.next());
   }

   @Test
   public void testSourceAssemblyWithClassifier() {
      final FeaturePropertiesQueryFactory queryFactory = lookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(true, true, "plugins", "plugins");
      assertEquals(18, queries.size());

      PropertiesQuery query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      assertEquals("Plugins", query.getDefaultValue());

      query = queries.get("feature.labelAppendix");
      assertNotNull(query);
      assertEquals("(Sources)", query.getDefaultValue());
   }
}
