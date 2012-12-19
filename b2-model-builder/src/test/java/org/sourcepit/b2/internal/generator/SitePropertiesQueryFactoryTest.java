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
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.guplex.test.GuplexTest;

public class SitePropertiesQueryFactoryTest extends GuplexTest
{
   @Override
   protected boolean isUseIndex()
   {
      return true;
   }

   @Test
   public void test()
   {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("nls_de.b2.sourceClassifierLabel", "(Quelldateien)");


      final SitePropertiesQueryFactory queryFactory = gLookup(SitePropertiesQueryFactory.class);
      Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(properties, "sdk", "", "included");

      PropertiesQuery query;
      Iterator<String> it;

      query = queries.get("categories.included.name");
      assertNotNull(query);
      assertEquals("${categories.included.label} ${categories.included.labelAppendix}", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.categories.included.name", it.next());

      query = queries.get("categories.included.label");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.categories.included.label", it.next());
      assertEquals("b2.module.name", it.next());
      assertEquals("project.name", it.next());
      assertEquals("project.artifactId", it.next());

      query = queries.get("categories.included.labelAppendix");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.categories.included.labelAppendix", it.next());
      assertEquals("b2.assemblies.categories.included.labelAppendix", it.next());
      assertEquals("b2.module.categories.included.labelAppendix", it.next());

      query = queries.get("categories.included.description");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.categories.included.description", it.next());
      assertEquals("b2.module.description", it.next());
      assertEquals("project.description", it.next());
   }

   @Test
   public void testWithClassifier()
   {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("nls_de.b2.sourceClassifierLabel", "(Quelldateien)");


      final SitePropertiesQueryFactory queryFactory = gLookup(SitePropertiesQueryFactory.class);
      Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(properties, "sdk", "sdk", "assembled");

      PropertiesQuery query;
      Iterator<String> it;

      query = queries.get("categories.assembled.name");
      assertNotNull(query);
      assertEquals(
         "${categories.assembled.label} ${categories.assembled.classifierLabel} ${categories.assembled.labelAppendix}",
         query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.categories.assembled.name", it.next());

      query = queries.get("categories.assembled.label");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.categories.assembled.label", it.next());
      assertEquals("b2.module.name", it.next());
      assertEquals("project.name", it.next());
      assertEquals("project.artifactId", it.next());

      query = queries.get("categories.assembled.classifierLabel");
      assertNotNull(query);
      assertEquals("Sdk", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.categories.classifierLabel", it.next());
      assertEquals("b2.assemblies.sdk.classifierLabel", it.next());

      query = queries.get("categories.assembled.labelAppendix");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.categories.assembled.labelAppendix", it.next());
      assertEquals("b2.assemblies.categories.assembled.labelAppendix", it.next());
      assertEquals("b2.module.categories.assembled.labelAppendix", it.next());

      query = queries.get("categories.assembled.description");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.categories.assembled.description", it.next());
      assertEquals("b2.module.description", it.next());
      assertEquals("project.description", it.next());
   }
}
