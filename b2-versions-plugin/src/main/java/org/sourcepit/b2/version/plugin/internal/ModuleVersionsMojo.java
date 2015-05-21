/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */


package org.sourcepit.b2.version.plugin.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;

import javax.inject.Inject;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.IOUtils;
import org.apache.maven.artifact.manager.WagonManager;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.artifact.resolver.ResolutionErrorHandler;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.path.PathTranslator;
import org.apache.maven.repository.RepositorySystem;
import org.apache.maven.settings.Settings;
import org.codehaus.mojo.versions.api.DefaultVersionsHelper;
import org.codehaus.mojo.versions.api.PomHelper;
import org.codehaus.mojo.versions.api.VersionsHelper;
import org.codehaus.mojo.versions.rewriting.ModifiedPomXMLEventReader;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.WriterFactory;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.codehaus.stax2.XMLInputFactory2;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.maven.core.B2MavenBridge;

/**
 * @author eddi-weiss
 */

@SuppressWarnings("deprecation")
public abstract class ModuleVersionsMojo extends AbstractMojo {

   protected B2MavenBridge bridge;

   @Parameter(property = "project", readonly = true, required = true)
   protected MavenProject project;

   @Parameter(property = "reactorProjects", readonly = true, required = true)
   protected List<MavenProject> reactorProjects;

   @Parameter(defaultValue = "${session}")
   protected MavenSession session;

   @Parameter(defaultValue = "${localRepository}")
   protected ArtifactRepository localRepository;

   @Inject
   protected RepositorySystem repository;
   @Inject
   private ResolutionErrorHandler resolutionErrorHandler;

   private VersionsHelper helper;
   @Inject
   private PathTranslator pathTranslator;
   @Inject
   private ArtifactMetadataSource artifactMetadataSource;
   @Inject
   private WagonManager wagonManager;
   @Inject
   private org.apache.maven.artifact.factory.ArtifactFactory artifactFactory;
   @Inject
   private ArtifactResolver artifactResolver;
   @Parameter(defaultValue = "${settings}")
   private Settings settings;
   @Parameter(defaultValue = "serverId", property = "maven.version.rules.serverId")
   private String serverId;
   @Parameter(property = "maven.version.rules")
   private String rulesUri;


   @Override
   public final void execute() throws MojoExecutionException, MojoFailureException {
      InputStream stream = null;
      try {
         bridge = B2MavenBridge.get(session);
         ModuleDirectory directory = bridge.getModuleDirectory(project);
         if (directory != null) {
            File dir = directory.getFile();
            final File moduleFile = new File(dir, "module.xml");
            stream = new FileInputStream(moduleFile);
            final Model projectModel = new MavenXpp3Reader().read(stream);
            processModule(moduleFile, projectModel);
         }
      }
      catch (FileNotFoundException e) {
         throw new MojoExecutionException("", e);
      }
      catch (IOException e) {
         throw new MojoExecutionException("", e);
      }
      catch (XmlPullParserException e) {
         throw new MojoExecutionException("", e);
      }
      finally {
         IOUtils.closeQuietly(stream);
         bridge.disconnect(session);
      }
   }

   private void processModule(File moduleFile, final Model projectModel) throws MojoExecutionException,
      MojoFailureException {
      try {
         StringBuilder input = PomHelper.readXmlFile(moduleFile);
         ModifiedPomXMLEventReader reader = createReader(input);

         updateModule(reader, projectModel);

         if (reader.isModified()) {
            writeFile(moduleFile, input);
         }
      }
      catch (IOException e) {
         getLog().error(e);
      }
      catch (XMLStreamException e) {
         getLog().error(e);
      }
   }

   protected abstract void updateModule(ModifiedPomXMLEventReader reader, Model projectModel)
      throws MojoExecutionException, MojoFailureException, XMLStreamException;

   private ModifiedPomXMLEventReader createReader(StringBuilder input) {
      ModifiedPomXMLEventReader reader = null;
      try {
         XMLInputFactory factory = XMLInputFactory2.newInstance();
         factory.setProperty(XMLInputFactory2.P_PRESERVE_LOCATION, Boolean.TRUE);
         reader = new ModifiedPomXMLEventReader(input, factory);
      }
      catch (XMLStreamException e) {
         getLog().error(e);
      }
      return reader;
   }


   private void writeFile(File moduleFile, StringBuilder content) throws IOException {
      Writer writer = WriterFactory.newXmlWriter(moduleFile);
      try {
         IOUtil.copy(content.toString(), writer);
      }
      finally {
         IOUtils.closeQuietly(writer);
      }
   }


   public VersionsHelper getHelper() throws MojoExecutionException {
      if (helper == null) {
         helper = new DefaultVersionsHelper(artifactFactory, artifactResolver, artifactMetadataSource,
            project.getRemoteArtifactRepositories(), project.getRemotePluginRepositories(), localRepository,
            wagonManager, settings, serverId, rulesUri, getLog(), session, pathTranslator);
      }
      return helper;
   }
}
