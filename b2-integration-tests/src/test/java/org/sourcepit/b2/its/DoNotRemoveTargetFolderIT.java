/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;
import static org.sourcepit.b2.its.util.FilePresentMatcher.isNotPresent;
import static org.sourcepit.b2.its.util.FilePresentMatcher.isPresent;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

@SuppressWarnings("boxing")
public class DoNotRemoveTargetFolderIT extends AbstractB2IT
{
   private static final String PREREQUISITE_FAIL_MESSAGE = "Prerequesite for testing not satisfied - check resource directory";

   private File moduleDir;
   private File submodulePluginTargetFolder;
   private File workspaceTargetFolder;
   private File submodulePluginTargetFolderContent;

   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Before
   public void setUp() throws IOException
   {
      moduleDir = getResource(getClass().getSimpleName());

      workspaceTargetFolder = new File(moduleDir, "target");
      submodulePluginTargetFolder = new File(moduleDir, "submodule/plugins/somePlugin/target");
      submodulePluginTargetFolderContent = new File(moduleDir,
         "submodule/plugins/somePlugin/target/SubmodulePluginDummy.class");

      assertThat(PREREQUISITE_FAIL_MESSAGE, submodulePluginTargetFolder, isPresent());
      assertThat(PREREQUISITE_FAIL_MESSAGE, submodulePluginTargetFolderContent, isPresent());
   }

   /**
    * Detailed Test for {@link org.sourcepit.b2.internal.generator.PomGenerator}, 
    * {@link org.sourcepit.b2.internal.maven.MavenFileFlagsProvider} 
    * and {@link org.sourcepit.b2.internal.generator.B2Generator} so target folders are not marked wrongly
    * 
    * @throws Exception
    */
   @Test
   public void targetFolderShouldNotBeMarkedDerived() throws Exception
   {
      // GIVEN

      // WHEN
      int err = build(moduleDir, "-e", "-B", "validate");
      assertThat(err, is(0));

      PropertiesMap props = new LinkedPropertiesMap();
      props.load(new File(moduleDir, ".b2/moduleDirectory.properties"));

      // THEN
      assertEquals(0, props.getInt("submodule/plugins/somePlugin/target", 0) & FLAG_DERIVED);
      assertEquals(0, props.getInt("target", 0) & FLAG_DERIVED);
   }

   @Test
   public void buildsWithMvnCleanShouldDeleteTargetFolders() throws IOException
   {
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));

      assertThat(workspaceTargetFolder, isNotPresent());
      assertThat(submodulePluginTargetFolder, isNotPresent());
      assertThat(submodulePluginTargetFolderContent, isNotPresent());
   }

   /**
    * Two consecutive builds are necessary when checking this as b2 ({@link MavenFileFlagsProvider})
    * reacts differently when poms are present (2nd run)
    * 
    * @throws IOException
    */
   @Test
   public void consecutiveBuildsWithoutMvnCleanShouldNotDeleteTargetFolders() throws IOException
   {
      int err = build(moduleDir, "-e", "-B", "validate");
      assertThat(err, is(0));

      int secondRunErr = build(moduleDir, "-e", "-B", "validate");
      assertThat(secondRunErr, is(0));

      assertThat(workspaceTargetFolder, isPresent());
      assertThat(submodulePluginTargetFolder, isPresent());
      assertThat(submodulePluginTargetFolderContent, isPresent());
   }

}
