/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.RepositoryUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.interpolation.ValueSource;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.tycho.core.utils.PlatformPropertiesUtils;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.util.artifact.AbstractArtifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sourcepit.b2.directory.parser.module.IModuleFilter;
import org.sourcepit.b2.directory.parser.module.WhitelistModuleFilter;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.internal.generator.DefaultTemplateCopier;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.internal.generator.VersionUtils;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.builder.util.BasicConverter;
import org.sourcepit.b2.model.common.util.ArtifactIdentifier;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.Environment;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.b2.model.session.SessionModelPackage;
import org.sourcepit.common.utils.adapt.Adapters;
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
   private B2SessionService sessionService;

   @Inject
   private ModelContextAdapterFactory adapterFactory;

   @Inject
   private BasicConverter converter;

   public B2Session initialize(MavenSession bootSession, Properties properties)
   {
      intEMF();
      return initB2Session(bootSession, properties);
   }

   private void intEMF()
   {
      ModuleModelPackage.eINSTANCE.getClass();
      SessionModelPackage.eINSTANCE.getClass();
   }

   private B2Session initB2Session(MavenSession bootSession, Properties properties)
   {
      ModelContext modelContext = adapterFactory.adapt(bootSession, bootSession.getCurrentProject());
      sessionService.setCurrentResourceSet(modelContext.getResourceSet());

      B2Session b2Session = createB2Session(modelContext.getResourceSet(), bootSession);
      sessionService.setCurrentSession(b2Session);

      Adapters.removeAdapters(bootSession, B2Session.class);
      Adapters.addAdapter(bootSession, b2Session);

      final int currentIdx = bootSession.getProjects().indexOf(bootSession.getCurrentProject());
      b2Session.setCurrentProject(b2Session.getProjects().get(currentIdx));

      final ModuleProject currentProject = b2Session.getCurrentProject();
      final MavenProject project = bootSession.getCurrentProject();
      configureModuleProject(currentProject, project, properties);

      return b2Session;
   }

   private B2Session createB2Session(ResourceSet resourceSet, MavenSession bootSession)
   {
      final B2Session b2Session;
      b2Session = SessionModelFactory.eINSTANCE.createB2Session();

      for (MavenProject project : bootSession.getProjects())
      {
         final ModuleProject moduleProject = SessionModelFactory.eINSTANCE.createModuleProject();
         moduleProject.setGroupId(project.getGroupId());
         moduleProject.setArtifactId(project.getArtifactId());
         moduleProject.setVersion(project.getVersion());
         moduleProject.setDirectory(project.getBasedir());

         final MavenProject currentProject = bootSession.getCurrentProject();
         if (!project.equals(currentProject))
         {
            final ModelContext modelContext = ModelContextAdapterFactory.get(project);
            if (modelContext != null)
            {
               final URI moduleUri = modelContext.getModuleUri();
               final AbstractModule module = (AbstractModule) resourceSet.getEObject(moduleUri, true);
               moduleProject.setModuleModel(module);
            }
         }

         b2Session.getProjects().add(moduleProject);
         if (project.equals(currentProject))
         {
            b2Session.setCurrentProject(moduleProject);
         }
      }

      return b2Session;
   }

   private void configureModuleProject(ModuleProject moduleProject, MavenProject bootProject, Properties properties)
   {
      configureModuleDependencies(moduleProject, bootProject);
      configureModuleTargetEnvironments(moduleProject, bootProject, properties);
   }

   private void configureModuleDependencies(ModuleProject moduleProject, MavenProject bootProject)
   {
      Set<Artifact> dependencies = bootProject.getArtifacts();
      for (Artifact dependency : dependencies)
      {
         if ("module".equals(dependency.getType()))
         {
            ModuleDependency moduleDependency = SessionModelFactory.eINSTANCE.createModuleDependency();
            moduleDependency.setGroupId(dependency.getGroupId());
            moduleDependency.setArtifactId(dependency.getArtifactId());
            moduleDependency.setClassifier(dependency.getClassifier());
            moduleDependency.setVersionRange(dependency.getBaseVersion());

            moduleProject.getDependencies().add(moduleDependency);
         }
      }
   }

   private void configureModuleTargetEnvironments(ModuleProject currentProject, MavenProject project,
      Properties properties)
   {
      for (Plugin plugin : project.getBuildPlugins())
      {
         if ("org.eclipse.tycho:target-platform-configuration".equals(plugin.getKey()))
         {
            Xpp3Dom configuration = (Xpp3Dom) plugin.getConfiguration();
            if (configuration != null)
            {
               Xpp3Dom envs = configuration.getChild("environments");
               if (envs != null)
               {
                  for (Xpp3Dom envNode : envs.getChildren("environment"))
                  {
                     Environment env = SessionModelFactory.eINSTANCE.createEnvironment();

                     Xpp3Dom node = envNode.getChild("os");
                     if (node != null)
                     {
                        env.setOs(node.getValue());
                     }

                     node = envNode.getChild("ws");
                     if (node != null)
                     {
                        env.setWs(node.getValue());
                     }

                     node = envNode.getChild("arch");
                     if (node != null)
                     {
                        env.setArch(node.getValue());
                     }

                     currentProject.getEnvironements().add(env);
                  }
               }
            }
         }
      }

      if (currentProject.getEnvironements().isEmpty())
      {
         String os = PlatformPropertiesUtils.getOS(properties);
         String ws = PlatformPropertiesUtils.getWS(properties);
         String arch = PlatformPropertiesUtils.getArch(properties);

         Environment env = SessionModelFactory.eINSTANCE.createEnvironment();
         env.setOs(os);
         env.setWs(ws);
         env.setArch(arch);

         currentProject.getEnvironements().add(env);
      }
   }

   public B2Request newB2Request(MavenProject bootProject)
   {
      final PropertiesSource moduleProperties = createSource(legacySupport.getSession(), bootProject);

      final B2Session b2Session = sessionService.getCurrentSession();
      final ResourceSet resourceSet = sessionService.getCurrentResourceSet();

      processDependencies(resourceSet, b2Session.getCurrentProject(), bootProject);

      final File moduleDir = bootProject.getBasedir();
      logger.info("Building model for directory " + moduleDir.getName());

      final ITemplates templates = new DefaultTemplateCopier(Optional.of(moduleProperties));

      final Set<File> whitelist = new HashSet<File>();
      for (ModuleProject project : b2Session.getProjects())
      {
         whitelist.add(project.getDirectory());
      }
      final IModuleFilter fileFilter = new WhitelistModuleFilter(whitelist);

      final B2Request b2Request = new B2Request();
      b2Request.setModuleDirectory(moduleDir);
      b2Request.setModuleProperties(moduleProperties);
      b2Request.setModuleFilter(fileFilter);
      b2Request.setInterpolate(!converter.isSkipInterpolator(moduleProperties));
      b2Request.setTemplates(templates);
      return b2Request;
   }

   private void processDependencies(ResourceSet resourceSet, ModuleProject moduleProject,
      final MavenProject wrapperProject)
   {
      final ModelContext modelContext = ModelContextAdapterFactory.get(wrapperProject);

      SetMultimap<AbstractModule, FeatureProject> foo = LinkedHashMultimap.create();
      foo.putAll(modelContext.getMainScope());
      foo.putAll(modelContext.getTestScope());

      final B2Session session = moduleProject.getSession();

      for (Entry<AbstractModule, Collection<FeatureProject>> entry : foo.asMap().entrySet())
      {
         ArtifactIdentifier id = toArtifactId(entry.getKey().eResource().getURI());

         if (session.getProject(id.getGroupId(), id.getArtifactId(), id.getVersion()) == null)
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
                  moduleProject.putAnnotationEntry("b2.resolvedSites", uniqueId.toString().replace(':', '_'), siteUrl);

                  logger.info("Using site " + siteUrl);
               }
            }
         }
      }
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

      final AbstractArtifact siteArtifact = new DefaultArtifact(artifact.getGroupId(), artifact.getArtifactId(),
         cl.toString(), "zip", artifact.getVersion());

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
