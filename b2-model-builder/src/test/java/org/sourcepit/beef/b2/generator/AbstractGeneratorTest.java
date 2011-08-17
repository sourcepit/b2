/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.internal.generator.ITemplates;
import org.sourcepit.beef.b2.model.builder.util.IConverter;

public class AbstractGeneratorTest extends TestCase
{
   public void testCompareTo() throws Exception
   {
      AbstractGenerator gen1 = new AbstractGenerator()
      {
         @Override
         public GeneratorType getGeneratorType()
         {
            return GeneratorType.PROJECT_GENERATOR;
         }

         @Override
         protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
         {
         }

         @Override
         public void generate(EObject inputElement, IConverter converter, ITemplates templates)
         {
         }
      };

      AbstractGenerator gen2 = new AbstractGenerator()
      {
         @Override
         public GeneratorType getGeneratorType()
         {
            return GeneratorType.PROJECT_RESOURCE_GENERATOR;
         }

         @Override
         protected void addInputTypes(Collection<Class<? extends EObject>> inputTypes)
         {
         }

         @Override
         public void generate(EObject inputElement, IConverter converter, ITemplates templates)
         {
         }
      };

      assertTrue(gen1.compareTo(gen2) < 0); // lesser means more important..

      List<AbstractGenerator> generators = new ArrayList<AbstractGenerator>();
      generators.add(gen2);
      generators.add(gen1);

      Collections.sort(generators);

      assertEquals(gen1, generators.get(0));
      assertEquals(gen2, generators.get(1));
   }
}
