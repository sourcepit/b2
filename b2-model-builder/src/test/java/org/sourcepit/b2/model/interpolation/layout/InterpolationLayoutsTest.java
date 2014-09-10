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

package org.sourcepit.b2.model.interpolation.layout;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.sisu.launch.InjectedTestCase;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.ModuleModelFactory;

public class InterpolationLayoutsTest extends InjectedTestCase
{
   @Inject
   private List<IInterpolationLayout> layouts;

   public void testIdOfProject() throws Exception
   {
      BasicModule module = ModuleModelFactory.eINSTANCE.createBasicModule();
      module.setId("foo.module");
      assertFalse(layouts.isEmpty());

      for (IInterpolationLayout layout : layouts)
      {
         assertEquals("foo.module.feature", layout.idOfFeatureProject(module, null));
         assertEquals("foo.module.feature", layout.idOfFeatureProject(module, ""));
         assertEquals("foo.module.sdk.feature", layout.idOfFeatureProject(module, "sdk"));
      }
   }
}
