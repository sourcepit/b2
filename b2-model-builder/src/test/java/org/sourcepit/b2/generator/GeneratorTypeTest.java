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
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class GeneratorTypeTest extends TestCase {
   public void testCompare() throws Exception {
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
