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

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class SitesConverterTest {
   @Test
   public void testGetFeatureIdForAssembly() throws Exception {
      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.assemblies", "main, test");
      moduleProperties.put("b2.assemblies.main.classifier", "");

      SitesConverter converter = new DefaultConverter();

      try {
         converter.getSiteIdForAssembly(moduleProperties, "foo", null);
         fail();
      }
      catch (IllegalArgumentException e) {
      }

      assertEquals("foo.site", converter.getSiteIdForAssembly(moduleProperties, "foo", "main"));
      assertEquals("foo.test.site", converter.getSiteIdForAssembly(moduleProperties, "foo", "test"));

      moduleProperties.put("b2.assemblies.main.siteId", "bar");

      assertEquals("bar", converter.getSiteIdForAssembly(moduleProperties, "foo", "main"));
      assertEquals("foo.test.site", converter.getSiteIdForAssembly(moduleProperties, "foo", "test"));
   }

   @Test
   public void testGetAssemblyCategories() {
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
   public void testGetAssemblySiteFeatureMatcher() {
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
   public void testGetAssemblyCategoryFeatureMatcher() {
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
      assertEquals(1, matcher.getIncludes().size());
      assertEquals(1, matcher.getExcludes().size());
      assertEquals(".*", matcher.getIncludes().iterator().next());
      assertEquals("moduleId\\.sdk\\.feature", matcher.getExcludes().iterator().next());
   }

   @Test
   public void testSitesAppendix() throws Exception {
      PropertiesMap moduleProperties = new LinkedPropertiesMap();
      moduleProperties.put("b2.assemblies", "main, test");
      moduleProperties.put("b2.assemblies.main.classifier", "");

      SitesConverter converter = new DefaultConverter();

      assertEquals("foo.site", converter.getSiteIdForAssembly(moduleProperties, "foo", "main"));
      assertEquals("foo.test.site", converter.getSiteIdForAssembly(moduleProperties, "foo", "test"));

      moduleProperties.put("b2.sitesAppendix", "repo");

      assertEquals("foo.repo", converter.getSiteIdForAssembly(moduleProperties, "foo", "main"));
      assertEquals("foo.test.repo", converter.getSiteIdForAssembly(moduleProperties, "foo", "test"));
   }

}
