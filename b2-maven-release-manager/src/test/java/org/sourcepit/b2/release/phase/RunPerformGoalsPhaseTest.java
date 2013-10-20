/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.release.phase;

import static org.junit.Assert.assertEquals;

import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.junit.Test;

public class RunPerformGoalsPhaseTest
{

   @Test
   public void testGetGoals()
   {
      final RunPerformGoalsPhase phase = new RunPerformGoalsPhase();
      final ReleaseDescriptor releaseDescriptor = new ReleaseDescriptor();

      releaseDescriptor.setPerformGoals(null);
      assertEquals(null, phase.getGoals(releaseDescriptor));

      releaseDescriptor.setPerformGoals("");
      assertEquals("", phase.getGoals(releaseDescriptor));

      // Improvement #92
      releaseDescriptor.setPerformGoals("none");
      assertEquals("", phase.getGoals(releaseDescriptor));

      releaseDescriptor.setPerformGoals("clean");
      assertEquals("clean", phase.getGoals(releaseDescriptor));

      releaseDescriptor.setPerformGoals("clean verify");
      assertEquals("clean verify", phase.getGoals(releaseDescriptor));
   }

}
