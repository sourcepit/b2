/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.sourcepit.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.b2.model.builder.util.AbstractConverter;
import org.sourcepit.b2.model.builder.util.IConverter;

public class AbstractConverterTest extends TestCase
{
   public void testInterpolator() throws Exception
   {
      PropertiesMap map = new LinkedPropertiesMap();
      map.put("foo", "bar");

      AbstractConverter converter = newConvertert(map, false);
      assertEquals("bar", converter.interpolate("${foo}"));
      assertEquals("foo bar", converter.interpolate("foo ${foo}"));
      assertEquals("${foo}", converter.interpolate("\\${foo}"));
      assertEquals("${murks}", converter.interpolate("${murks}"));

      map = new LinkedPropertiesMap();
      map.put("foo", "bär");
      assertEquals("bär", converter.interpolate("${foo}", map));
   }

   public void testToClassiferLabel() throws Exception
   {
      AbstractConverter converter = newConvertert(null, false);

      try
      {
         converter.toClassifierLabel(null);
         fail();
      }
      catch (NullPointerException e)
      {
      }

      assertEquals("", converter.toClassifierLabel(""));
      assertEquals("Sources", converter.toClassifierLabel("sources"));
      assertEquals("Sources", converter.toClassifierLabel("Sources"));
      assertEquals("Test Sources", converter.toClassifierLabel("test.sources"));
   }

   public void testToValidIdentifier() throws Exception
   {
      try
      {
         AbstractConverter.toValidId(null);
         fail();
      }
      catch (NullPointerException e)
      {
      }

      assertEquals("_", AbstractConverter.toValidId(""));
      assertEquals("_", AbstractConverter.toValidId("_"));
      assertEquals("_3er", AbstractConverter.toValidId("3er"));
      assertEquals("hans_wurst", AbstractConverter.toValidId("hans wurst"));
   }

   public void testToValidClassifer() throws Exception
   {
      try
      {
         AbstractConverter.toValidClassifier(null);
         fail();
      }
      catch (NullPointerException e)
      {
      }

      assertEquals("", AbstractConverter.toValidClassifier(""));
      assertEquals("_", AbstractConverter.toValidClassifier("_"));
      assertEquals("_3er", AbstractConverter.toValidClassifier("3er"));
      assertEquals("hans_wurst", AbstractConverter.toValidClassifier("hans wurst"));
   }

   public void testDefaultProperties() throws Exception
   {
      AbstractConverter converter = newConvertert(null, true);
      PropertiesMap propertiesMap = converter.getProperties();

      assertEquals("sources", propertiesMap.get("features.source.classifer"));
      assertEquals("source", propertiesMap.get("plugins.source.classifer"));
      assertEquals("", propertiesMap.get("facet.plugins.classifier"));
      assertEquals("sdk, ${pluginFacets.classifers}", propertiesMap.get("features"));
      assertEquals("**.feature, !**.sdk.**, !**.tests.**", propertiesMap.get("feature.sdk.filter"));
      assertEquals("SDK", propertiesMap.get("feature.sdk.classifier.label"));
      assertEquals("**.sdk.**", propertiesMap.get("layout.composite.feature.sdk.filter"));
      assertEquals("!**.composite.**, **.sdk.**", propertiesMap.get("layout.composite.category.sdk.filter"));
      assertEquals("!**.composite.**, !**.sdk.**, !**.tests.**,!**.sources.**",
         propertiesMap.get("layout.composite.category.public.filter"));
      assertEquals("sdk, internal, public", propertiesMap.get("categories"));
      assertEquals("**.sdk.**", propertiesMap.get("category.sdk.filter"));
      assertEquals("", propertiesMap.get("category.internal.filter"));
      assertEquals("!**.sdk.**, !**.tests.**, !**.tests, !**.sources.**, !**.source",
         propertiesMap.get("category.public.filter"));
      assertEquals("SDK", propertiesMap.get("category.sdk.classifier.label"));
      assertEquals("", propertiesMap.get("category.public.classifier.label"));
      assertEquals("public, sdk, internal", propertiesMap.get("sites"));
      assertEquals("sdk", propertiesMap.get("site.sdk.categories"));
      assertEquals("sdk, internal, public", propertiesMap.get("site.internal.categories"));
      assertEquals("public", propertiesMap.get("site.public.categories"));
      assertEquals("", propertiesMap.get("site.public.classifier"));
   }

   public void testGetFeatureClassifiers() throws Exception
   {
      // this tests tests if the plugins facet name will be correctly replaced with an empty string as defined via
      // "facet.plugins.classifier". Depending on the ordering of the input set this has failed on linux systems

      final Set<String> reference = new HashSet<String>();
      reference.add("sdk"); // defined via "features=sdk, ${pluginFacets.classifers}"
      reference.add("tests");
      reference.add("");

      AbstractConverter converter = newConvertert(null, true);

      Set<String> facetNames = new LinkedHashSet<String>();
      facetNames.add("plugins");
      facetNames.add("tests");

      assertEquals(reference, converter.getFeatureClassifiers(facetNames));

      facetNames = new LinkedHashSet<String>();
      facetNames.add("tests");
      facetNames.add("plugins");

      assertEquals(reference, converter.getFeatureClassifiers(facetNames));
   }

   public void testSkipInterpolator() throws Exception
   {
      PropertiesMap properties = new LinkedPropertiesMap();

      IConverter converter = newConvertert(properties, false);
      assertFalse(converter.isSkipInterpolator());

      converter = newConvertert(properties, true);
      assertFalse(converter.isSkipInterpolator());

      properties.put("b2.skipInterpolator", "true");
      converter = newConvertert(properties, true);
      assertTrue(converter.isSkipInterpolator());
   }

   private AbstractConverter newConvertert(final PropertiesMap map, boolean loadDefaultProperties)
   {
      final PropertiesMap propertiesMap = map == null ? new LinkedPropertiesMap() : map;
      AbstractConverter converter = new AbstractConverter()
      {
         public String getNameSpace()
         {
            return "foo";
         }

         public String getModuleVersion()
         {
            return "1.0";
         }

         @Override
         protected PropertiesMap getPropertiesMap()
         {
            return propertiesMap;
         }
      };
      if (loadDefaultProperties)
      {
         propertiesMap.putAll(converter.loadConverterProperties());
      }
      return converter;
   }

}
