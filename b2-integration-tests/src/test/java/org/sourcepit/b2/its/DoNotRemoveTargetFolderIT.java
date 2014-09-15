/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.sourcepit.b2.its.util.FilePresentMatcher.*;
import static org.junit.Assert.*;
import static org.sourcepit.b2.files.ModuleDirectory.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class DoNotRemoveTargetFolderIT extends AbstractB2IT
{
   private static final String PREREQUISITE_FAIL_MESSAGE = "Prerequesite for testing not satisfied - check resource directory";
   
   private File moduleDir;
   private File submodulePluginTargetFolder;
   private File submodulePluginTargetFolderContent; 
   
   @Rule
   public ErrorCollector errorCollector = new ErrorCollector();
   
   @Override
   protected boolean isDebug()
   {
      return false;
   }
   
   @Before
   public void setUp() throws IOException
   {
      moduleDir = getResource(getClass().getSimpleName());
      
      submodulePluginTargetFolder = new File(moduleDir, "submodule/plugins/somePlugin/target");
      submodulePluginTargetFolderContent = new File(moduleDir, "submodule/plugins/somePlugin/target/SubmodulePluginDummy.class");
      
      assertThat(PREREQUISITE_FAIL_MESSAGE, submodulePluginTargetFolder, isPresent());
      assertThat(PREREQUISITE_FAIL_MESSAGE, submodulePluginTargetFolderContent, isPresent());
   }
   
   /**
    * Detailed Test for TODO and TODO so target folders are not marked wrongly
    * @throws Exception
    */
   @Test
   public void targetFolderShouldNotBeMarkedDerived() throws Exception
   {
      // GIVEN
      
      // WHEN
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));
      
      PropertiesMap props = new LinkedPropertiesMap();
      props.load(new File(moduleDir, ".b2/moduleDirectory.properties"));

      // THEN
      assertEquals(FLAG_FORBIDDEN | FLAG_HIDDEN, props.getInt("target", 0));      
      assertEquals(FLAG_FORBIDDEN | FLAG_HIDDEN, props.getInt("submodule/plugins/somePlugin/target",0)); 
   }
   
   @Test
   public void buildsWithMvnCleanShouldDeleteTargetFolders() throws IOException 
   {
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));
      
      assertThat(submodulePluginTargetFolder, isNotPresent());
      assertThat(submodulePluginTargetFolderContent, isNotPresent());
   }   

   /**
    * Two consecutive builds are necessary when checking this as b2 ({@link MavenFileFlagsProvider}) 
    * reacts differently when poms are present (2nd run)
    * @throws IOException 
    */
   @Test
   public void consecutiveBuildsWithoutMvnCleanShouldNotDeleteTargetFolders() throws IOException 
   {
      int err = build(moduleDir, "-e", "-B", "validate");
      assertThat(err, is(0));

      int secondRunErr = build(moduleDir, "-e", "-B", "validate");
      assertThat(secondRunErr, is(0));

      assertThat(submodulePluginTargetFolder, isPresent());
      assertThat(submodulePluginTargetFolderContent, isPresent());
   }

}
