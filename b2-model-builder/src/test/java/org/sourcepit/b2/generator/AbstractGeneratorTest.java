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

package org.sourcepit.b2.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.files.ModuleDirectory;
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
            ModuleDirectory moduleDirectory)
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
            ModuleDirectory moduleDirectory)
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
