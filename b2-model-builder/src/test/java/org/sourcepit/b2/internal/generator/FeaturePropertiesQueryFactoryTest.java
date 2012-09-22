/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.sourcepit.guplex.test.GuplexTest;

public class FeaturePropertiesQueryFactoryTest extends GuplexTest
{
   @Override
   protected boolean isUseIndex()
   {
      return true;
   }

   @Test
   public void testFacet()
   {
      final FeaturePropertiesQueryFactory queryFactory = gLookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(false, false, "plugins", "");
      assertEquals(11, queries.size());

      PropertiesQuery query = queries.get("feature.name");
      assertNotNull(query);
      assertEquals("${feature.label} ${feature.classifierLabel} ${feature.classifierLabelAppendix}",
         query.getDefaultValue());
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

      query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.classifierLabel", it.next());

      query = queries.get("feature.classifierLabelAppendix");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.classifierLabelAppendix", it.next());

      query = queries.get("feature.providerName");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.providerName", it.next());
      assertEquals("b2.facets.providerName", it.next());
      assertEquals("b2.providerName", it.next());

      query = queries.get("feature.copyright");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.copyright", it.next());
      assertEquals("b2.facets.copyright", it.next());
      assertEquals("b2.copyright", it.next());

      query = queries.get("feature.copyrightURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.copyrightURL", it.next());
      assertEquals("b2.facets.copyrightURL", it.next());
      assertEquals("b2.copyrightURL", it.next());

      query = queries.get("feature.description");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.description", it.next());
      assertEquals("b2.facets.description", it.next());
      assertEquals("b2.description", it.next());

      query = queries.get("feature.descriptionURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.descriptionURL", it.next());
      assertEquals("b2.facets.descriptionURL", it.next());
      assertEquals("b2.descriptionURL", it.next());

      query = queries.get("feature.license");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.license", it.next());
      assertEquals("b2.facets.license", it.next());
      assertEquals("b2.license", it.next());

      query = queries.get("feature.licenseURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.licenseURL", it.next());
      assertEquals("b2.facets.licenseURL", it.next());
      assertEquals("b2.licenseURL", it.next());
   }

   @Test
   public void testFacetWithClassifier()
   {
      final FeaturePropertiesQueryFactory queryFactory = gLookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(false, false, "plugins",
         "plugins");
      assertEquals(11, queries.size());

      PropertiesQuery query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      assertEquals("Plugins", query.getDefaultValue());

      query = queries.get("feature.classifierLabelAppendix");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
   }

   @Test
   public void testSourceFacet()
   {
      final FeaturePropertiesQueryFactory queryFactory = gLookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(false, true, "plugins", "");
      assertEquals(11, queries.size());

      PropertiesQuery query = queries.get("feature.name");
      assertNotNull(query);

      Iterator<String> it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.featureName", it.next());

      query = queries.get("feature.label");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.featureLabel", it.next());
      assertEquals("b2.module.name", it.next());
      assertEquals("project.name", it.next());
      assertEquals("project.artifactId", it.next());

      query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.classifierLabel", it.next());

      query = queries.get("feature.classifierLabelAppendix");
      assertNotNull(query);
      assertEquals("(Sources)", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.sourceClassifierLabel", it.next());
      assertEquals("b2.facets.sourceClassifierLabel", it.next());
      assertEquals("b2.sourceClassifierLabel", it.next());

      query = queries.get("feature.providerName");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.providerName", it.next());
      assertEquals("b2.facets.providerName", it.next());
      assertEquals("b2.providerName", it.next());

      query = queries.get("feature.copyright");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.copyright", it.next());
      assertEquals("b2.facets.copyright", it.next());
      assertEquals("b2.copyright", it.next());

      query = queries.get("feature.copyrightURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.copyrightURL", it.next());
      assertEquals("b2.facets.copyrightURL", it.next());
      assertEquals("b2.copyrightURL", it.next());

      query = queries.get("feature.description");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.description", it.next());
      assertEquals("b2.facets.description", it.next());
      assertEquals("b2.description", it.next());

      query = queries.get("feature.descriptionURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.descriptionURL", it.next());
      assertEquals("b2.facets.descriptionURL", it.next());
      assertEquals("b2.descriptionURL", it.next());

      query = queries.get("feature.license");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.license", it.next());
      assertEquals("b2.facets.license", it.next());
      assertEquals("b2.license", it.next());

      query = queries.get("feature.licenseURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.facets.plugins.licenseURL", it.next());
      assertEquals("b2.facets.licenseURL", it.next());
      assertEquals("b2.licenseURL", it.next());
   }

   @Test
   public void testSourceFacetWithClassifier()
   {
      final FeaturePropertiesQueryFactory queryFactory = gLookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory
         .createPropertyQueries(false, true, "plugins", "plugins");
      assertEquals(11, queries.size());

      PropertiesQuery query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      assertEquals("Plugins", query.getDefaultValue());

      query = queries.get("feature.classifierLabelAppendix");
      assertNotNull(query);
      assertEquals("(Sources)", query.getDefaultValue());
   }

   @Test
   public void testAssembly()
   {
      final FeaturePropertiesQueryFactory queryFactory = gLookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(true, false, "plugins", "");
      assertEquals(11, queries.size());

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

      query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.classifierLabel", it.next());

      query = queries.get("feature.classifierLabelAppendix");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.classifierLabelAppendix", it.next());

      query = queries.get("feature.providerName");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.providerName", it.next());
      assertEquals("b2.assemblies.providerName", it.next());
      assertEquals("b2.providerName", it.next());

      query = queries.get("feature.copyright");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.copyright", it.next());
      assertEquals("b2.assemblies.copyright", it.next());
      assertEquals("b2.copyright", it.next());

      query = queries.get("feature.copyrightURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.copyrightURL", it.next());
      assertEquals("b2.assemblies.copyrightURL", it.next());
      assertEquals("b2.copyrightURL", it.next());

      query = queries.get("feature.description");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.description", it.next());
      assertEquals("b2.assemblies.description", it.next());
      assertEquals("b2.description", it.next());

      query = queries.get("feature.descriptionURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.descriptionURL", it.next());
      assertEquals("b2.assemblies.descriptionURL", it.next());
      assertEquals("b2.descriptionURL", it.next());

      query = queries.get("feature.license");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.license", it.next());
      assertEquals("b2.assemblies.license", it.next());
      assertEquals("b2.license", it.next());

      query = queries.get("feature.licenseURL");
      assertNotNull(query);
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.licenseURL", it.next());
      assertEquals("b2.assemblies.licenseURL", it.next());
      assertEquals("b2.licenseURL", it.next());
   }

   @Test
   public void testAssemblyWithClassifier()
   {
      final FeaturePropertiesQueryFactory queryFactory = gLookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory
         .createPropertyQueries(true, false, "plugins", "plugins");
      assertEquals(11, queries.size());

      PropertiesQuery query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      assertEquals("Plugins", query.getDefaultValue());

      query = queries.get("feature.classifierLabelAppendix");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
   }

   @Test
   public void testSourceAssembly()
   {
      final FeaturePropertiesQueryFactory queryFactory = gLookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(true, true, "plugins", "");
      assertEquals(11, queries.size());

      PropertiesQuery query = queries.get("feature.name");
      assertNotNull(query);
      assertEquals("${feature.label} ${feature.classifierLabel} ${feature.classifierLabelAppendix}",
         query.getDefaultValue());
      Iterator<String> it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.featureName", it.next());

      query = queries.get("feature.label");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.featureLabel", it.next());
      assertEquals("b2.module.name", it.next());
      assertEquals("project.name", it.next());
      assertEquals("project.artifactId", it.next());

      query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.classifierLabel", it.next());

      query = queries.get("feature.classifierLabelAppendix");
      assertNotNull(query);
      assertEquals("(Sources)", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.sourceClassifierLabel", it.next());
      assertEquals("b2.assemblies.sourceClassifierLabel", it.next());
      assertEquals("b2.sourceClassifierLabel", it.next());

      query = queries.get("feature.providerName");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.providerName", it.next());
      assertEquals("b2.assemblies.providerName", it.next());
      assertEquals("b2.providerName", it.next());

      query = queries.get("feature.copyright");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.copyright", it.next());
      assertEquals("b2.assemblies.copyright", it.next());
      assertEquals("b2.copyright", it.next());

      query = queries.get("feature.copyrightURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.copyrightURL", it.next());
      assertEquals("b2.assemblies.copyrightURL", it.next());
      assertEquals("b2.copyrightURL", it.next());

      query = queries.get("feature.description");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.description", it.next());
      assertEquals("b2.assemblies.description", it.next());
      assertEquals("b2.description", it.next());

      query = queries.get("feature.descriptionURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.descriptionURL", it.next());
      assertEquals("b2.assemblies.descriptionURL", it.next());
      assertEquals("b2.descriptionURL", it.next());

      query = queries.get("feature.license");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.license", it.next());
      assertEquals("b2.assemblies.license", it.next());
      assertEquals("b2.license", it.next());

      query = queries.get("feature.licenseURL");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.plugins.licenseURL", it.next());
      assertEquals("b2.assemblies.licenseURL", it.next());
      assertEquals("b2.licenseURL", it.next());
   }

   @Test
   public void testSourceAssemblyWithClassifier()
   {
      final FeaturePropertiesQueryFactory queryFactory = gLookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(true, true, "plugins", "plugins");
      assertEquals(11, queries.size());

      PropertiesQuery query = queries.get("feature.classifierLabel");
      assertNotNull(query);
      assertEquals("Plugins", query.getDefaultValue());

      query = queries.get("feature.classifierLabelAppendix");
      assertNotNull(query);
      assertEquals("(Sources)", query.getDefaultValue());
   }
}
