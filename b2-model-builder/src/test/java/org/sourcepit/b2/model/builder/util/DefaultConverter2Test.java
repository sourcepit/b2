/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class DefaultConverter2Test
{

   @Test
   public void testGetRequiredFeatures()
   {
      final String key = "b2.facets[\"foo\"].requiredFeatures";

      Converter2 converter = new DefaultConverter2();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<RuledReference> list;
      RuledReference requirement;


      // empty
      list = converter.getRequiredFeatures(properties, "foo");
      assertEquals(0, list.size());

      properties.put(key, " ,, ");
      list = converter.getRequiredFeatures(properties, "foo");
      assertEquals(0, list.size());

      // invalid
      properties.put(key, "foo.feature:!.0.0");
      try
      {
         converter.getRequiredFeatures(properties, "foo");
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }
      
      properties.put(key, "foo.feature:1.0.0:murks");
      try
      {
         converter.getRequiredFeatures(properties, "foo");
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }
      
      properties.put(key, "foo.feature:1.0.0:perfect:murks");
      try
      {
         converter.getRequiredFeatures(properties, "foo");
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      // valid
      properties.put(key, "foo.feature, foo.feature:1.0.0, foo.feature:1.0.0:perfect");
      list = converter.getRequiredFeatures(properties, "foo");
      assertEquals(3, list.size());

      requirement = list.get(0);
      assertEquals("foo.feature", requirement.getId());
      assertNull(requirement.getVersion());
      assertSame(VersionMatchRule.COMPATIBLE, requirement.getMatchRule());

      requirement = list.get(1);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertSame(VersionMatchRule.COMPATIBLE, requirement.getMatchRule());

      requirement = list.get(2);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertSame(VersionMatchRule.PERFECT, requirement.getMatchRule());
   }

}
