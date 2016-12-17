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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.RepositoryUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.execution.B2RequestFactory;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.files.ModuleDirectoryFactory;
import org.sourcepit.b2.internal.generator.DefaultTemplateCopier;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.common.util.ArtifactIdentifier;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.common.utils.content.ContentTypes;
import org.sourcepit.common.utils.props.PropertiesSource;

import com.google.common.base.Optional;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

@Named("maven")
public class MavenB2RequestFactory implements B2RequestFactory {

   private static final Logger LOGGER = LoggerFactory.getLogger(MavenB2RequestFactory.class);

   @Inject
   private LegacySupport legacySupport;

   @Inject
   private RepositorySystem repositorySystem;


   @Inject
   private ModelContextAdapterFactory adapterFactory;

   @Inject
   private BasicConverter converter;

   @Inject
   private ModuleDirectoryFactory moduleDirectoryFactory;

   @Inject
   private MavenModulePropertiesFactory modulePropertiesFactory;

   public B2Request newRequest(List<File> projectDirs, int currentIdx) {
      final MavenSession bootSession = legacySupport.getSession();
      final MavenProject bootProject = bootSession.getProjects().get(currentIdx);
      return newB2Request(bootSession, bootProject);
   }

   public B2Request newB2Request(MavenSession bootSession, MavenProject bootProject) {
      ModuleModelPackage.eINSTANCE.getClass();

      final PropertiesSource moduleProperties = modulePropertiesFactory
         .createModuleProperties(legacySupport.getSession(), bootProject);

      final ResourceSet resourceSet = adapterFactory.adapt(bootSession, bootProject).getResourceSet();
      processDependencies(resourceSet, bootSession, bootProject);

      final File moduleDir = bootProject.getBasedir();
      LOGGER.info("Building model for directory " + moduleDir.getName());

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(moduleProperties));

      final B2Request b2Request = new B2Request();
      b2Request.setModuleProperties(moduleProperties);
      b2Request.setInterpolate(!converter.isSkipInterpolator(moduleProperties));
      b2Request.setTemplates(templates);
      b2Request.setContentTypes(ContentTypes.DEFAULT);

      for (MavenProject project : bootSession.getProjects()) {
         final File projectDir = project.getBasedir();

         if (!projectDir.equals(bootProject.getBasedir())) {
            final ModelContext modelContext = ModelContextAdapterFactory.get(project);
            if (modelContext != null) {
               final URI moduleUri = modelContext.getModuleUri();
               final AbstractModule module = (AbstractModule) resourceSet.getEObject(moduleUri, true);
               b2Request.getModulesCache().put(module.getDirectory(), module);
            }
         }
      }

      ModuleDirectory moduleDirectory = moduleDirectoryFactory.create(moduleDir, moduleProperties);
      b2Request.setModuleDirectory(moduleDirectory);

      return b2Request;
   }

   private void processDependencies(ResourceSet resourceSet, MavenSession mavenSession,
      final MavenProject wrapperProject) {
      final ModelContext modelContext = ModelContextAdapterFactory.get(wrapperProject);

      SetMultimap<AbstractModule, FeatureProject> foo = LinkedHashMultimap.create();
      foo.putAll(modelContext.getMainScope());
      foo.putAll(modelContext.getTestScope());

      final Map<String, String> sites = new LinkedHashMap<String, String>();

      for (Entry<AbstractModule, Collection<FeatureProject>> entry : foo.asMap().entrySet()) {
         ArtifactIdentifier id = toArtifactId(entry.getKey().eResource().getURI());

         MavenProject mavenProject = findMavenProject(mavenSession, entry.getKey().getDirectory());
         if (mavenProject == null) {
            // TODO move test sites to test projects
            for (FeatureProject featureProject : entry.getValue()) {
               List<String> classifiers = B2MetadataUtils.getAssemblyClassifiers(featureProject);
               for (String classifier : classifiers) {
                  final Artifact siteArtifact = resolveSiteZip(wrapperProject, id, classifier);
                  final File zipFile = siteArtifact.getFile();

                  final String path = zipFile.getAbsolutePath().replace('\\', '/');
                  final String siteUrl = "jar:file:" + path + "!/";

                  final ArtifactIdentifier uniqueId = new ArtifactIdentifier(id.getGroupId(), id.getArtifactId(),
                     id.getVersion(), classifier, id.getType());

                  sites.put(uniqueId.toString().replace(':', '_'), siteUrl);

                  LOGGER.info("Using site " + siteUrl);
               }
            }
         }
      }

      wrapperProject.setContextValue("b2.resolvedSites", sites);
   }

   private static MavenProject findMavenProject(MavenSession mavenSession, File directory) {
      for (MavenProject mavenProject : mavenSession.getProjects()) {
         if (mavenProject.getBasedir().equals(directory)) {
            return mavenProject;
         }
      }
      return null;
   }

   private static ArtifactIdentifier toArtifactId(URI uri) {
      final String[] segments = uri.segments();

      final boolean hasClassifier = segments.length == 5;

      String version = hasClassifier ? segments[4] : segments[3];
      String classifier = hasClassifier ? segments[3] : null;

      return new ArtifactIdentifier(segments[0], segments[1], version, classifier, segments[2]);
   }

   private Artifact resolveSiteZip(final MavenProject wrapperProject, ArtifactIdentifier artifact, String classifier) {
      final StringBuilder cl = new StringBuilder();
      cl.append("site");
      if (classifier != null && classifier.length() > 0) {
         cl.append('-');
         cl.append(classifier);
      }

      String groupId = artifact.getGroupId();
      String artifactId = artifact.getArtifactId();
      String extension = "zip";
      String version = artifact.getVersion();

      String classi = cl.toString();

      return resolveArtifact(wrapperProject, groupId, artifactId, extension, version, classi);
   }

   private Artifact resolveArtifact(final MavenProject wrapperProject, String groupId, String artifactId,
      String extension, String version, String classifier) {
      final org.eclipse.aether.artifact.Artifact siteArtifact = new DefaultArtifact(groupId, artifactId, classifier,
         extension, version);

      ArtifactRequest request = new ArtifactRequest();
      request.setArtifact(siteArtifact);
      request.setRepositories(wrapperProject.getRemoteProjectRepositories());

      try {
         final ArtifactResult result = repositorySystem.resolveArtifact(legacySupport.getRepositorySession(), request);
         return RepositoryUtils.toArtifact(result.getArtifact());
      }
      catch (ArtifactResolutionException e) {
         throw new IllegalStateException(e);
      }
   }

}
