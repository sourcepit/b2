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

package org.sourcepit.b2.release.phase;

import static org.junit.Assert.assertEquals;

import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.junit.Test;

public class RunPrepareGoalsPhaseTest {

   @Test
   public void testGetGoals() {
      final RunPrepareGoalsPhase phase = new RunPrepareGoalsPhase();
      final ReleaseDescriptor releaseDescriptor = new ReleaseDescriptor();

      releaseDescriptor.setPreparationGoals(null);
      assertEquals(null, phase.getGoals(releaseDescriptor));

      releaseDescriptor.setPreparationGoals("");
      assertEquals("", phase.getGoals(releaseDescriptor));

      // Improvement #92
      releaseDescriptor.setPreparationGoals("none");
      assertEquals("", phase.getGoals(releaseDescriptor));

      releaseDescriptor.setPreparationGoals("clean");
      assertEquals("clean", phase.getGoals(releaseDescriptor));

      releaseDescriptor.setPreparationGoals("clean verify");
      assertEquals("clean verify", phase.getGoals(releaseDescriptor));
   }

}
