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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.sourcepit.b2.its.util.FilePresentMatcher.isNotPresent;
import static org.sourcepit.b2.its.util.FilePresentMatcher.isPresent;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("boxing")
public class SkipB2IT extends AbstractB2IT {
   private static final String PREREQUISITE_FAIL_MESSAGE = "Prerequesite for testing not satisfied - check resource directory";

   private File moduleDir;
   private File moduleAB2Folder;
   private File workspaceB2Folder;
   private File moduleBB2Folder;

   @Override
   protected boolean isDebug() {
      return false;
   }

   @Before
   public void setUp() throws IOException {
      moduleDir = getResource(getClass().getSimpleName());

      workspaceB2Folder = new File(moduleDir, ".b2");
      moduleAB2Folder = new File(moduleDir, "module-a/.b2");
      moduleBB2Folder = new File(moduleDir, "module-b/.b2");

      assertThat(PREREQUISITE_FAIL_MESSAGE, moduleAB2Folder, isNotPresent());
      assertThat(PREREQUISITE_FAIL_MESSAGE, moduleBB2Folder, isNotPresent());
   }

   @Test
   public void buildsWithoutParameterShouldBootstrap() throws IOException {
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));

      assertThat(workspaceB2Folder, isPresent());
      assertThat(moduleAB2Folder, isPresent());
      assertThat(moduleBB2Folder, isPresent());
   }

   @Test
   public void projectShouldbuildWhenBuildingCleanAndThenInstallWithSkipBootstrapParameter() throws Exception {
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));

      assertThat(workspaceB2Folder, isPresent());
      assertThat(moduleAB2Folder, isPresent());
      assertThat(moduleBB2Folder, isPresent());

      int realErr = build(moduleDir, "-e", "-B", "clean", "install", "-DskipB2");
      assertThat(realErr, is(0));
   }

   @Test
   public void buildsWithParameterShouldNotBootstrap() throws IOException {
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven", "-DskipB2");
      assertThat(err, is(0));

      assertThat(workspaceB2Folder, isNotPresent());
      assertThat(moduleAB2Folder, isNotPresent());
      assertThat(moduleBB2Folder, isNotPresent());
   }


}
