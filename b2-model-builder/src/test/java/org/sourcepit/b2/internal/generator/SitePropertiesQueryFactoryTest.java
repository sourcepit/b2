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
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class SitePropertiesQueryFactoryTest extends InjectedTest
{
   @Test
   public void test()
   {
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("project.name", "Core Plug-ins");
      properties.put("nls_de.project.name", "Kern Plug-ins");
      properties.put("nls_de.b2.sourceClassifierLabel", "(Quelldateien)");


      final SitePropertiesQueryFactory queryFactory = lookup(SitePropertiesQueryFactory.class);
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


      final SitePropertiesQueryFactory queryFactory = lookup(SitePropertiesQueryFactory.class);
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
