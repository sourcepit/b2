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

package org.sourcepit.b2.validation;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;
import org.sourcepit.common.utils.lang.Exceptions;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class ProjectEncodingConstraintTest
{
   private Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = new Workspace(new File(env.getBuildDir(), "test-ws"), false);

   private ProjectEncodingConstraint constraint;

   private RecordingLogger logger;

   @Before
   public void setUp()
   {
      logger = new RecordingLogger();
      constraint = new ProjectEncodingConstraint(logger);
   }

   @Test
   public void testOk() throws Exception
   {
      final File bundleDir = ws.newDir("bundle");

      final PluginProject pluginProject = newPluginProject(bundleDir);

      final PropertiesMap buildProperties = new LinkedPropertiesMap();

      logger.getMessages().clear();
      constraint.validate(pluginProject, buildProperties, true);
      assertThat(logger.getMessages().size(), is(0));

      final PropertiesMap projectProperties = new LinkedPropertiesMap();
      projectProperties.put("encoding/<project>", "UTF-8");
      save(projectProperties, ws.newFile("bundle/.settings/org.eclipse.core.resources.prefs"));

      buildProperties.put("project.build.sourceEncoding", "UTF-8");

      logger.getMessages().clear();
      constraint.validate(pluginProject, buildProperties, true);
      assertThat(logger.getMessages().size(), is(0));
   }

   @Test
   public void testQuickFix() throws Exception
   {
      final File bundleDir = ws.newDir("bundle");

      final PluginProject pluginProject = newPluginProject(bundleDir);

      final PropertiesMap buildProperties = new LinkedPropertiesMap();

      logger.getMessages().clear();
      constraint.validate(pluginProject, buildProperties, true);
      assertThat(logger.getMessages().size(), is(0));

      buildProperties.put("project.build.sourceEncoding", "UTF-8");

      logger.getMessages().clear();
      constraint.validate(pluginProject, buildProperties, true);
      assertThat(logger.getMessages().size(), is(1));
      assertEquals("WARN bundle: Expected project encoding UTF-8 but is <not-set>. (applied quick fix)", logger
         .getMessages().get(0));

      final PropertiesMap projectProperties = new LinkedPropertiesMap();
      projectProperties.load(ws.newFile("bundle/.settings/org.eclipse.core.resources.prefs"));
      assertEquals("UTF-8", projectProperties.get("encoding/<project>"));
   }

   private PluginProject newPluginProject(final File bundleDir)
   {
      final PluginProject pluginProject = ModuleModelFactory.eINSTANCE.createPluginProject();
      pluginProject.setId(bundleDir.getName());
      pluginProject.setDirectory(bundleDir);
      return pluginProject;
   }

   private void save(final PropertiesMap resourcePrefs, final File resourcePrefsFile)
   {
      OutputStream out = null;
      try
      {
         if (!resourcePrefsFile.exists())
         {
            resourcePrefsFile.getParentFile().mkdirs();
            resourcePrefsFile.createNewFile();
         }
         out = new BufferedOutputStream(new FileOutputStream(resourcePrefsFile));
         resourcePrefs.toJavaProperties().store(out, null);
      }
      catch (IOException e)
      {
         throw Exceptions.pipe(e);
      }
      finally
      {
         IOUtils.closeQuietly(out);
      }
   }

}
