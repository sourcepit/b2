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
      return false;
   }

   @Test
   public void test()
   {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("nls_de.b2.sourceClassifierLabel", "(Quelldateien)");


      final SitePropertiesQueryFactory queryFactory = gLookup(SitePropertiesQueryFactory.class);
      Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(properties, "sdk", "", "includes");

      PropertiesQuery query;
      Iterator<String> it;

      query = queries.get("categories.includes.name");
      assertNotNull(query);
      assertEquals("${categories.includes.label} ${categories.includes.labelAppendix}", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.includesCategoryName", it.next());

      query = queries.get("categories.includes.label");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.includesCategoryLabel", it.next());
      assertEquals("b2.module.name", it.next());
      assertEquals("project.name", it.next());
      assertEquals("project.artifactId", it.next());

      query = queries.get("categories.includes.labelAppendix");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.includesCategoryLabelAppendix", it.next());
      assertEquals("b2.assemblies.includesCategoryLabelAppendix", it.next());
      assertEquals("b2.module.includesCategoryLabelAppendix", it.next());

      query = queries.get("categories.includes.description");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.description", it.next());
      assertEquals("b2.assemblies.description", it.next());
      assertEquals("b2.module.description", it.next());
   }

   @Test
   public void testWithClassifier()
   {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("nls_de.b2.sourceClassifierLabel", "(Quelldateien)");


      final SitePropertiesQueryFactory queryFactory = gLookup(SitePropertiesQueryFactory.class);
      Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(properties, "sdk", "sdk", "assembly");

      PropertiesQuery query;
      Iterator<String> it;

      query = queries.get("categories.assembly.name");
      assertNotNull(query);
      assertEquals(
         "${categories.assembly.label} ${categories.assembly.classifierLabel} ${categories.assembly.labelAppendix}",
         query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.assemblyCategoryName", it.next());

      query = queries.get("categories.assembly.label");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.assemblyCategoryLabel", it.next());
      assertEquals("b2.module.name", it.next());
      assertEquals("project.name", it.next());
      assertEquals("project.artifactId", it.next());

      query = queries.get("categories.assembly.classifierLabel");
      assertNotNull(query);
      assertEquals("Sdk", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.classifierLabel", it.next());

      query = queries.get("categories.assembly.labelAppendix");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.assemblyCategoryLabelAppendix", it.next());
      assertEquals("b2.assemblies.assemblyCategoryLabelAppendix", it.next());
      assertEquals("b2.module.assemblyCategoryLabelAppendix", it.next());

      query = queries.get("categories.assembly.description");
      assertNotNull(query);
      assertEquals("", query.getDefaultValue());
      it = query.getKeys().iterator();
      assertEquals("b2.assemblies.sdk.description", it.next());
      assertEquals("b2.assemblies.description", it.next());
      assertEquals("b2.module.description", it.next());
   }
}
