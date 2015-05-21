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

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.sourcepit.b2.execution.AbstractB2SessionLifecycleParticipant;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.execution.B2SessionLifecycleParticipant;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.internal.generator.AbstractPomGenerator;
import org.sourcepit.b2.internal.generator.ModelTemplateMerger;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

@Named
public class MavenB2LifecycleParticipant extends AbstractB2SessionLifecycleParticipant
   implements
      B2SessionLifecycleParticipant {
   @Inject
   private MavenProjectHelper projectHelper;

   @Inject
   private LayoutManager layoutManager;

   @Inject
   private LegacySupport legacySupport;

   public void postPrepareProject(File project, B2Request request, AbstractModule module, ThrowablePipe errors) {
      final ModuleDirectory moduleDirectory = request.getModuleDirectory();
      ModuleDirectory.save(moduleDirectory, newFile(module, "moduleDirectory.properties"));

      final MavenProject bootProject = legacySupport.getSession().getCurrentProject();

      final ModelContext modelContext = ModelContextAdapterFactory.get(bootProject);
      final ResourceSet resourceSet = modelContext.getResourceSet();

      Resource moduleResource = resourceSet.createResource(modelContext.getModuleUri());
      moduleResource.getContents().add(module);
      try {
         moduleResource.save(null);
      }
      catch (IOException e) {
         throw new IllegalStateException(e);
      }


      final File pomFile = new File(module.getAnnotationData(AbstractPomGenerator.SOURCE_MAVEN,
         AbstractPomGenerator.KEY_POM_FILE));

      bootProject.setContextValue("pom", pomFile);


      PropertiesMap uriMap = new LinkedPropertiesMap();
      for (Entry<URI, URI> entry : resourceSet.getURIConverter().getURIMap().entrySet()) {
         uriMap.put(entry.getKey().toString(), entry.getValue().toString());
      }
      uriMap.store(newFile(module, "uriMap.properties"));

      final URI fileURI = resourceSet.getURIConverter().normalize(modelContext.getModuleUri());
      projectHelper.attachArtifact(bootProject, "module", null, new File(fileURI.toFileString()));

      processAttachments(bootProject, pomFile);
   }

   private File newFile(AbstractModule module, String fileName) {
      final String layoutId = module.getLayoutId();
      File file = new File(layoutManager.getLayout(layoutId).pathOfMetaDataFile(module, fileName));
      return file;
   }

   private void processAttachments(MavenProject wrapperProject, File pomFile) {
      final List<Artifact> attachedArtifacts = wrapperProject.getAttachedArtifacts();
      if (attachedArtifacts == null) {
         return;
      }

      Xpp3Dom artifactsNode = new Xpp3Dom("artifacts");
      for (Artifact artifact : attachedArtifacts) {
         Xpp3Dom artifactNode = new Xpp3Dom("artifact");

         if (artifact.getClassifier() != null) {
            Xpp3Dom classifierNode = new Xpp3Dom("classifier");
            classifierNode.setValue(artifact.getClassifier());
            artifactNode.addChild(classifierNode);
         }

         Xpp3Dom typeNode = new Xpp3Dom("type");
         typeNode.setValue(artifact.getType());
         artifactNode.addChild(typeNode);

         Xpp3Dom fileNode = new Xpp3Dom("file");
         fileNode.setValue(artifact.getFile().getAbsolutePath());
         artifactNode.addChild(fileNode);

         artifactsNode.addChild(artifactNode);
      }

      Xpp3Dom configNode = new Xpp3Dom("configuration");
      configNode.addChild(artifactsNode);

      PluginExecution exec = new PluginExecution();
      exec.setId("b2-attach-artifatcs");
      exec.setPhase("initialize");
      exec.getGoals().add("attach-artifact");
      exec.setConfiguration(configNode);

      Plugin plugin = new Plugin();
      plugin.setGroupId("org.codehaus.mojo");
      plugin.setArtifactId("build-helper-maven-plugin");
      plugin.setVersion("1.7");
      plugin.getExecutions().add(exec);
      plugin.setInherited(false);

      Build build = new Build();
      build.getPlugins().add(plugin);

      Model model = new Model();
      model.setBuild(build);

      final Model moduleModel;
      try {
         moduleModel = new DefaultModelReader().read(pomFile, null);
      }
      catch (IOException e) {
         throw new IllegalStateException(e);
      }

      new ModelTemplateMerger().merge(moduleModel, model, false, null);
      try {
         new DefaultModelWriter().write(pomFile, null, moduleModel);
      }
      catch (IOException e) {
         throw new IllegalStateException(e);
      }
   }
}
