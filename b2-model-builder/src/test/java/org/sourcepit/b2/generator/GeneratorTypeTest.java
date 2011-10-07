/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sourcepit.b2.generator.GeneratorType;

import junit.framework.TestCase;

public class GeneratorTypeTest extends TestCase
{
   public void testCompare() throws Exception
   {
      final List<GeneratorType> genTypes = new ArrayList<GeneratorType>();
      Collections.addAll(genTypes, GeneratorType.values());
      Collections.shuffle(genTypes);

      Collections.sort(genTypes);

      assertEquals(GeneratorType.PROJECT_GENERATOR, genTypes.get(0));
      assertEquals(GeneratorType.PROJECT_RESOURCE_GENERATOR, genTypes.get(1));
      assertEquals(GeneratorType.MODULE_RESOURCE_GENERATOR, genTypes.get(2));
      assertEquals(GeneratorType.PROJECT_RESOURCE_FILTER, genTypes.get(3));
      assertEquals(GeneratorType.MODULE_RESOURCE_FILTER, genTypes.get(4));
   }
}
