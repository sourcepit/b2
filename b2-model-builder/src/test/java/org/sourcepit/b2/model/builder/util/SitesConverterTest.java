/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class SitesConverterTest
{
   @Test
   public void testGetAssemblyCategories()
   {
      SitesConverter converter = new DefaultConverter();
      PropertiesMap moduleProperties = new LinkedPropertiesMap();

      List<String> categories;

      categories = converter.getAssemblyCategories(moduleProperties, "sdk");
      assertEquals(2, categories.size());
      assertEquals("assembled", categories.get(0));
      assertEquals("included", categories.get(1));

      moduleProperties.put("b2.assemblies.categories", "");
      categories = converter.getAssemblyCategories(moduleProperties, "sdk");
      assertEquals(0, categories.size());

      moduleProperties.put("b2.assemblies.sdk.categories", "foo, foo, foo");
      categories = converter.getAssemblyCategories(moduleProperties, "sdk");
      assertEquals(1, categories.size());
      assertEquals("foo", categories.get(0));
   }

   @Test
   public void testGetAssemblySiteFeatureMatcher()
   {
      SitesConverter converter = new DefaultConverter();
      PropertiesMap moduleProperties = new LinkedPropertiesMap();

      PathMatcher matcher;

      matcher = converter.getAssemblySiteFeatureMatcher(moduleProperties, "sdk");
      assertNotNull(matcher);
      assertEquals(0, matcher.getExcludes().size());
      assertEquals(1, matcher.getIncludes().size());
      assertEquals(".*", matcher.getIncludes().iterator().next());

      moduleProperties.put("b2.assemblies.siteFeaturesFilter", "!**.sdk.**,**.tests.**");

      matcher = converter.getAssemblySiteFeatureMatcher(moduleProperties, "sdk");
      assertNotNull(matcher);
      assertEquals(1, matcher.getExcludes().size());
      assertEquals(1, matcher.getIncludes().size());
      assertEquals(".*\\.sdk\\..*", matcher.getExcludes().iterator().next());
      assertEquals(".*\\.tests\\..*", matcher.getIncludes().iterator().next());

      moduleProperties.put("b2.assemblies.sdk.siteFeaturesFilter", "!**");

      matcher = converter.getAssemblySiteFeatureMatcher(moduleProperties, "sdk");
      assertNotNull(matcher);
      assertEquals(1, matcher.getExcludes().size());
      assertEquals(0, matcher.getIncludes().size());
      assertEquals(".*", matcher.getExcludes().iterator().next());
   }

   @Test
   public void testGetAssemblyCategoryFeatureMatcher()
   {
      SitesConverter converter = new DefaultConverter();
      PropertiesMap moduleProperties = new LinkedPropertiesMap();

      PathMatcher matcher;

      matcher = converter.getAssemblyCategoryFeatureMatcher(moduleProperties, "moduleId", "sdk", "foo");
      assertNotNull(matcher);
      assertEquals(0, matcher.getExcludes().size());
      assertEquals(1, matcher.getIncludes().size());
      assertEquals(".*", matcher.getIncludes().iterator().next());

      matcher = converter.getAssemblyCategoryFeatureMatcher(moduleProperties, "moduleId", "sdk", "assembled");
      assertNotNull(matcher);
      assertEquals(0, matcher.getExcludes().size());
      assertEquals(1, matcher.getIncludes().size());
      assertEquals("moduleId\\.sdk\\.feature", matcher.getIncludes().iterator().next());

      matcher = converter.getAssemblyCategoryFeatureMatcher(moduleProperties, "moduleId", "sdk", "included");
      assertNotNull(matcher);
      assertEquals(0, matcher.getIncludes().size());
      assertEquals(1, matcher.getExcludes().size());
      assertEquals("moduleId\\.sdk\\.feature", matcher.getExcludes().iterator().next());
   }

}
