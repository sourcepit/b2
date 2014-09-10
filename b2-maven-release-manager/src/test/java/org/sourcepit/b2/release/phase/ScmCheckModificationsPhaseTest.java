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
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.apache.maven.settings.Settings;
import org.apache.maven.shared.release.ReleaseExecutionException;
import org.apache.maven.shared.release.ReleaseFailureException;
import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.apache.maven.shared.release.env.ReleaseEnvironment;
import org.junit.Test;

public class ScmCheckModificationsPhaseTest
{

   @Test
   public void testCheckModificationExcludes() throws ReleaseExecutionException, ReleaseFailureException
   {
      ScmCheckModificationsPhase phase = new ScmCheckModificationsPhase();

      ReleaseDescriptor releaseDescriptor = new ReleaseDescriptor();
      assertEquals(0, releaseDescriptor.getCheckModificationExcludes().size());

      try
      {
         phase.execute(releaseDescriptor, (ReleaseEnvironment) null, null);
      }
      catch (NullPointerException e)
      {
      }

      checkScmExcludes(releaseDescriptor);

      try
      {
         phase.execute(releaseDescriptor, (Settings) null, null);
      }
      catch (NullPointerException e)
      {
      }

      checkScmExcludes(releaseDescriptor);
   }

   private void checkScmExcludes(ReleaseDescriptor releaseDescriptor)
   {
      final List<String> excludes = releaseDescriptor.getCheckModificationExcludes();
      assertEquals(7, excludes.size());
      assertTrue(excludes.contains("**" + File.separator + ".b2"));
      assertTrue(excludes.contains("**" + File.separator + "pom.xml"));
      assertTrue(excludes.contains("**" + File.separator + "module.xml.backup"));
      assertTrue(excludes.contains("**" + File.separator + "module.xml.tag"));
      assertTrue(excludes.contains("**" + File.separator + "module.xml.next"));
      assertTrue(excludes.contains("**" + File.separator + "module.xml.branch"));
      assertTrue(excludes.contains("**" + File.separator + "module.xml.releaseBackup"));
   }

}
