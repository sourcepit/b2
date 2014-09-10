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

package org.sourcepit.b2.its;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.sourcepit.b2.its.ReleaseIT.setScm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sourcepit.b2.its.util.GitSCM;
import org.sourcepit.b2.its.util.SCM;

public class ManifestFilterReleaseIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void test() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());
      final SCM scm = new GitSCM(moduleDir);

      final File moduleXML = new File(moduleDir, "module.xml");

      final File mfFile = new File(moduleDir, "bundle.a/META-INF/MANIFEST.MF");
      assertFalse(mfFile.exists());

      setScm(scm, asList(moduleXML));
      scm.create();

      final String releaseVersion = "1.0.0";
      final String developmentVersion = "2.0.0-SNAPSHOT";

      final List<String> args = new ArrayList<String>();
      args.add("-e");
      args.add("-B");
      args.add("clean");
      args.add("release:prepare");
      args.add("release:perform");
      args.add("-Dtycho.mode=maven");
      args.add("-DreleaseVersion=" + releaseVersion);
      args.add("-DdevelopmentVersion=" + developmentVersion);
      args.add("-Dresume=false");
      args.add("-DignoreSnapshots=true");

      build(moduleDir, args.toArray(new String[args.size()]));

      assertTrue(mfFile.exists());

      scm.switchVersion(releaseVersion);

      assertFalse(mfFile.exists());
   }

}
