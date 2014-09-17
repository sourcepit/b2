/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.sourcepit.b2.its.util.FilePresentMatcher.isNotPresent;
import static org.sourcepit.b2.its.util.FilePresentMatcher.isPresent;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("boxing")
public class SkipB2IT extends AbstractB2IT
{
   private static final String PREREQUISITE_FAIL_MESSAGE = "Prerequesite for testing not satisfied - check resource directory";

   private File moduleDir;
   private File moduleAB2Folder;
   private File workspaceB2Folder;
   private File moduleBB2Folder;

   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Before
   public void setUp() throws IOException
   {
      moduleDir = getResource(getClass().getSimpleName());

      workspaceB2Folder = new File(moduleDir, ".b2");
      moduleAB2Folder = new File(moduleDir, "module-a/.b2");
      moduleBB2Folder = new File(moduleDir, "module-b/.b2");

      assertThat(PREREQUISITE_FAIL_MESSAGE, moduleAB2Folder, isNotPresent());
      assertThat(PREREQUISITE_FAIL_MESSAGE, moduleBB2Folder, isNotPresent());
   }

   @Test
   public void buildsWithoutParameterShouldBootstrap() throws IOException
   {
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));

      assertThat(workspaceB2Folder, isPresent());
      assertThat(moduleAB2Folder, isPresent());
      assertThat(moduleBB2Folder, isPresent());
   }
   
   @Test
   public void projectShouldbuildWhenBuildingCleanAndThenInstallWithSkipBootstrapParameter() throws Exception
   {           
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));

      assertThat(workspaceB2Folder, isPresent());
      assertThat(moduleAB2Folder, isPresent());
      assertThat(moduleBB2Folder, isPresent());
      
      int realErr = build(moduleDir, "-e", "-B", "clean", "install", "-DskipB2");
      assertThat(realErr, is(0));
   }
   
   @Test
   public void buildsWithParameterShouldNotBootstrap() throws IOException
   {
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven", "-DskipB2");
      assertThat(err, is(0));

      assertThat(workspaceB2Folder, isNotPresent());
      assertThat(moduleAB2Folder, isNotPresent());
      assertThat(moduleBB2Folder, isNotPresent());
   }


}
