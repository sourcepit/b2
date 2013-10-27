/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.files.ModuleFiles;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.common.utils.props.PropertiesSource;

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
         public void generate(EObject inputElement, PropertiesSource properties, ITemplates templates,
            ModuleFiles moduleFiles)
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
         public void generate(EObject inputElement, PropertiesSource properties, ITemplates templates,
            ModuleFiles moduleFiles)
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
