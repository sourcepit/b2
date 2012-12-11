/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.release.phase;

import static org.junit.Assert.*;

import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.junit.Test;

public class RunPrepareGoalsPhaseTest
{

   @Test
   public void testGetGoals()
   {
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
