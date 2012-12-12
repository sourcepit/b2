/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
