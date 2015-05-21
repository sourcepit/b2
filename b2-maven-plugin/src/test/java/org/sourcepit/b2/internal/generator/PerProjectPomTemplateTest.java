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

package org.sourcepit.b2.internal.generator;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.sourcepit.b2.files.ModuleDirectory.FLAG_DERIVED;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.ModelReader;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.junit.Test;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.internal.maven.MavenB2RequestFactory;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest2;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.common.maven.testing.MavenExecutionResult2;

public class PerProjectPomTemplateTest extends AbstractB2SessionWorkspaceTest2 {
   @Inject
   private LegacySupport legacySupport;

   @Inject
   private MavenB2RequestFactory b2SessionInitializer;

   @Inject
   private IB2ModelBuilder modelBuilder;

   @Inject
   private PomGenerator pomGenerator;

   @Test
   public void test() throws Exception {
      final File projectDir = getResource("feature_53_per-project-pom-templates");

      final MavenExecutionResult2 result = buildProject(new File(projectDir, "module.xml"));
      final MavenSession mavenSession = result.getSession();
      legacySupport.setSession(mavenSession);

      final List<MavenProject> projects = mavenSession.getProjects();

      final Properties properties = new Properties();
      properties.putAll(mavenSession.getSystemProperties());
      properties.putAll(mavenSession.getUserProperties());

      for (MavenProject project : projects) {
         mavenSession.setCurrentProject(project);

         final B2Request b2Request = b2SessionInitializer.newB2Request(mavenSession, project);

         final AbstractModule module = modelBuilder.build(b2Request);

         final B2GenerationRequest request = new B2GenerationRequest();
         request.setModule(module);
         ModuleDirectory moduleDirectory = new ModuleDirectory(module.getDirectory(), null);
         moduleDirectory.addFlags(new File(module.getDirectory(), ".b2"), FLAG_DERIVED);
         request.setModuleDirectory(moduleDirectory);
         request.setModuleProperties(b2Request.getModuleProperties());
         request.setTemplates(b2Request.getTemplates());

         final B2Generator generator = new B2Generator(Collections.singletonList(pomGenerator),
            Collections.<B2GeneratorLifecycleParticipant> emptyList());
         generator.generate(request);

         PluginProject pluginProject = findProject(module, PluginsFacet.class, "org.sourcepit.b2.feature53.bundle");
         assertThat(pluginProject, notNullValue());

         Model pluginPom = readPom(new File(pluginProject.getDirectory(), "pom.xml"));
         // assert packaging NOT overridden
         assertThat(pluginPom.getPackaging(), equalTo("eclipse-plugin"));
         // assert entry from project template
         assertThat(pluginPom.getProperties().getProperty("type"), equalTo("plugin"));

         PluginProject testProject = findProject(module, PluginsFacet.class, "org.sourcepit.b2.feature53.bundle.tests");
         assertThat(testProject, notNullValue());

         Model testPom = readPom(new File(testProject.getDirectory(), "pom.xml"));
         // assert entry from project template
         assertThat(testPom.getProperties().getProperty("type"), equalTo("test"));
      }
   }

   private Model readPom(File pom) {
      final Map<String, String> options = new HashMap<String, String>();
      options.put(ModelReader.IS_STRICT, Boolean.FALSE.toString());
      try {
         return new DefaultModelReader().read(pom, options);
      }
      catch (IOException e) {
         throw new IllegalStateException(e);
      }
   }

   private <P extends Project> P findProject(AbstractModule module, Class<? extends ProjectFacet<P>> facetType,
      String projectId) {
      for (ProjectFacet<P> projectFacet : module.getFacets(facetType)) {
         final P project = projectFacet.getProjectById(projectId);
         if (project != null) {
            return project;
         }
      }
      return null;
   }
}
