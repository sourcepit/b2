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

import static org.mockito.Mockito.mock;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.shared.release.phase.ReleasePhase;
import org.codehaus.plexus.ContainerConfiguration;
import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.PlexusTestCase;

public class CompatibilityTest extends PlexusTestCase
{
   @Override
   protected void customizeContainerConfiguration(ContainerConfiguration cc)
   {
      super.customizeContainerConfiguration(cc);
      cc.setClassPathScanning(PlexusConstants.SCANNING_INDEX).setAutoWiring(true).setName("maven");
   }

   public void testAssureAllReleasePhasesAreImplementedByB2() throws Exception
   {
      LegacySupport buildContext = lookup(LegacySupport.class);
      buildContext.setSession(mock(MavenSession.class));

      @SuppressWarnings({ "unchecked", "rawtypes" })
      final Map<String, ReleasePhase> releasePhasesMap = (Map) getContainer().lookupMap(ReleasePhase.ROLE);
      assertFalse(releasePhasesMap.isEmpty());

      for (Entry<String, ReleasePhase> entry : releasePhasesMap.entrySet())
      {
         final String roleHint = entry.getKey();
         final String implName = entry.getValue().getClass().getName();

         assertTrue("Release phase " + roleHint + " must be implemented by b2. Current impl: " + implName,
            implName.startsWith("org.sourcepit.b2.release.phase."));
      }
   }
}
