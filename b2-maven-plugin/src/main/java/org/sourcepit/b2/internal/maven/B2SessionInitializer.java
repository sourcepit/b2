/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.util.ArrayList;
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
import org.apache.maven.project.MavenProjectHelper;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.interpolation.ValueSource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.slf4j.Logger;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sourcepit.b2.directory.parser.module.WhitelistModuleFilter;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.internal.generator.DefaultTemplateCopier;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.internal.generator.VersionUtils;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.common.util.ArtifactIdentifier;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.common.utils.props.AbstractPropertiesSource;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.tools.shared.resources.harness.ValueSourceUtils;

import com.google.common.base.Optional;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

@Named
public class B2SessionInitializer
{
   @Inject
   private LegacySupport legacySupport;

   @Inject
   private RepositorySystem repositorySystem;

   @Inject
   private MavenProjectHelper projectHelper;

   @Inject
   private Logger logger;

   @Inject
   private LayoutManager layoutManager;

   @Inject
   private ModelContextAdapterFactory adapterFactory;

   @Inject
   private BasicConverter converter;

   private void intEMF()
   {
      ModuleModelPackage.eINSTANCE.getClass();
   }

   public B2Request newB2Request(MavenSession bootSession, MavenProject bootProject)
   {
      intEMF();

      final PropertiesSource moduleProperties = createSource(legacySupport.getSession(), bootProject);

      final ResourceSet resourceSet = adapterFactory.adapt(bootSession, bootProject).getResourceSet();
      processDependencies(resourceSet, bootSession, bootProject);

      final File moduleDir = bootProject.getBasedir();
      logger.info("Building model for directory " + moduleDir.getName());

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(moduleProperties));

      final B2Request b2Request = new B2Request();
      b2Request.setModuleDirectory(moduleDir);
      b2Request.setModuleProperties(moduleProperties);
      b2Request.setInterpolate(!converter.isSkipInterpolator(moduleProperties));
      b2Request.setTemplates(templates);

      final List<File> projectDirs = new ArrayList<File>();
      for (MavenProject project : bootSession.getProjects())
      {
         final File projectDir = project.getBasedir();
         projectDirs.add(projectDir);

         if (!projectDir.equals(bootProject.getBasedir()))
         {
            final ModelContext modelContext = ModelContextAdapterFactory.get(project);
            if (modelContext != null)
            {
               final URI moduleUri = modelContext.getModuleUri();
               final AbstractModule module = (AbstractModule) resourceSet.getEObject(moduleUri, true);
               b2Request.getModulesCache().put(module.getDirectory(), module);
            }
         }
      }

      b2Request.setModuleFilter(new WhitelistModuleFilter(projectDirs));

      return b2Request;
   }

   private void processDependencies(ResourceSet resourceSet, MavenSession mavenSession,
      final MavenProject wrapperProject)
   {
      final ModelContext modelContext = ModelContextAdapterFactory.get(wrapperProject);

      SetMultimap<AbstractModule, FeatureProject> foo = LinkedHashMultimap.create();
      foo.putAll(modelContext.getMainScope());
      foo.putAll(modelContext.getTestScope());

      final Map<String, String> sites = new LinkedHashMap<String, String>();

      for (Entry<AbstractModule, Collection<FeatureProject>> entry : foo.asMap().entrySet())
      {
         ArtifactIdentifier id = toArtifactId(entry.getKey().eResource().getURI());

         MavenProject mavenProject = findMavenProject(mavenSession, entry.getKey().getDirectory());
         if (mavenProject == null)
         {
            // TODO move test sites to test projects
            for (FeatureProject featureProject : entry.getValue())
            {
               List<String> classifiers = B2MetadataUtils.getAssemblyClassifiers(featureProject);
               for (String classifier : classifiers)
               {
                  final Artifact siteArtifact = resolveSiteZip(wrapperProject, id, classifier);
                  final File zipFile = siteArtifact.getFile();

                  final String path = zipFile.getAbsolutePath().replace('\\', '/');
                  final String siteUrl = "jar:file:" + path + "!/";

                  final ArtifactIdentifier uniqueId = new ArtifactIdentifier(id.getGroupId(), id.getArtifactId(),
                     id.getVersion(), classifier, id.getType());

                  sites.put(uniqueId.toString().replace(':', '_'), siteUrl);

                  logger.info("Using site " + siteUrl);
               }
            }
         }
      }

      wrapperProject.setContextValue("b2.resolvedSites", sites);
   }

   private static MavenProject findMavenProject(MavenSession mavenSession, File directory)
   {
      for (MavenProject mavenProject : mavenSession.getProjects())
      {
         if (mavenProject.getBasedir().equals(directory))
         {
            return mavenProject;
         }
      }
      return null;
   }

   private static ArtifactIdentifier toArtifactId(URI uri)
   {
      final String[] segments = uri.segments();

      final boolean hasClassifier = segments.length == 5;

      String version = hasClassifier ? segments[4] : segments[3];
      String classifier = hasClassifier ? segments[3] : null;

      return new ArtifactIdentifier(segments[0], segments[1], version, classifier, segments[2]);
   }

   private Artifact resolveSiteZip(final MavenProject wrapperProject, ArtifactIdentifier artifact, String classifier)
   {
      final StringBuilder cl = new StringBuilder();
      cl.append("site");
      if (classifier != null && classifier.length() > 0)
      {
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
      String extension, String version, String classifier)
   {
      final org.sonatype.aether.artifact.Artifact siteArtifact = new DefaultArtifact(groupId, artifactId, classifier,
         extension, version);

      ArtifactRequest request = new ArtifactRequest();
      request.setArtifact(siteArtifact);
      request.setRepositories(wrapperProject.getRemoteProjectRepositories());

      try
      {
         final ArtifactResult result = repositorySystem.resolveArtifact(legacySupport.getRepositorySession(), request);
         return RepositoryUtils.toArtifact(result.getArtifact());
      }
      catch (ArtifactResolutionException e)
      {
         throw new IllegalStateException(e);
      }
   }

   public static PropertiesSource createSource(MavenSession mavenSession, MavenProject project)
   {
      final PropertiesMap propertiesMap = B2ModelBuildingRequest.newDefaultProperties();
      propertiesMap.put("b2.moduleVersion", VersionUtils.toBundleVersion(project.getVersion()));
      propertiesMap.put("b2.moduleNameSpace", project.getGroupId());
      propertiesMap.putMap(project.getProperties());
      propertiesMap.putMap(mavenSession.getSystemProperties());
      propertiesMap.putMap(mavenSession.getUserProperties());

      final List<ValueSource> valueSources = new ArrayList<ValueSource>();

      final List<String> prefixes = new ArrayList<String>();
      prefixes.add("pom");
      prefixes.add("project");
      valueSources.add(ValueSourceUtils.newPrefixedValueSource(prefixes, project));
      valueSources.add(ValueSourceUtils.newPrefixedValueSource("session", mavenSession));

      final Settings settings = mavenSession.getSettings();
      if (settings != null)
      {
         valueSources.add(ValueSourceUtils.newPrefixedValueSource("settings", settings));
         valueSources.add(ValueSourceUtils.newSingleValueSource("localRepository", settings.getLocalRepository()));
      }

      return new AbstractPropertiesSource()
      {
         public String get(String key)
         {
            final String value = propertiesMap.get(key);
            if (value != null)
            {
               return value;
            }
            for (ValueSource valueSource : valueSources)
            {
               final Object oValue = valueSource.getValue(key);
               if (oValue != null)
               {
                  return oValue.toString();
               }
            }
            return null;
         }
      };
   }
}
