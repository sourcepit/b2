/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import static org.junit.Assert.*;
import static org.sourcepit.common.utils.xml.XmlUtils.queryText;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
   public void test()
   {
      final FeaturePropertiesQueryFactory queryFactory = gLookup(FeaturePropertiesQueryFactory.class);

      final Map<String, PropertiesQuery> queries = queryFactory.createPropertyQueries(false, true, "plugins", "plugins");

      for (Entry<String, PropertiesQuery> entry : queries.entrySet())
      {
         String featureProperty = entry.getKey();
         PropertiesQuery query = entry.getValue();
         System.out.println(featureProperty + " (default: " + query.getDefaultValue() + ")");
         Collection<String> keys = query.getKeys();
         for (String string : keys)
         {
            System.out.println("   " + string);
         }
         System.out.println();
      }
   }
}
