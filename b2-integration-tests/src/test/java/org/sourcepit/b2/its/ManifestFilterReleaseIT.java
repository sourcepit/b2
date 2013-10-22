/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
