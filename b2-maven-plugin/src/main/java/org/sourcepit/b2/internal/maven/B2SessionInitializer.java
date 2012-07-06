/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.RepositoryUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
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
import org.sourcepit.b2.internal.generator.MavenConverter;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.builder.util.DecouplingModelCache;
import org.sourcepit.b2.model.common.util.GavResourceUtils;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.Environment;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.b2.model.session.SessionModelPackage;
import org.sourcepit.common.utils.adapt.Adapters;
import org.sourcepit.maven.bootstrap.participation.BootstrapSession;
import org.sourcepit.tools.shared.resources.harness.SharedResourcesCopier;

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
   private BootstrapSessionService bootSessionService;

   private static final String CACHE_KEY = B2BootstrapParticipant.class.getName() + "#modelCache";

   private static final String CACHE_KEY_SESSION = B2BootstrapParticipant.class.getName() + "#session";

   public B2Session initialize(BootstrapSession bootSession, Properties properties)
   {
      bootSessionService.setBootstrapSession(bootSession);
      intEMF();
      return initB2Session(bootSession, properties);
   }

   private void intEMF()
   {
      ModuleModelPackage.eINSTANCE.getClass();
      SessionModelPackage.eINSTANCE.getClass();
   }

   private B2Session initB2Session(BootstrapSession bootSession, Properties properties)
   {
      B2Session b2Session = Adapters.getAdapter(bootSession, B2Session.class);
      if (b2Session == null)
      {
         MavenURIResolver mavenURIResolver = new MavenURIResolver(bootSession.getBootstrapProjects(),
            bootSession.getCurrentBootstrapProject());
         mavenURIResolver.getProjectURIResolvers().add(new B2ModelResourceURIResolver(projectHelper));

         ResourceSet resourceSet = new ResourceSetImpl();
         resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("file", new XMIResourceFactoryImpl());
         GavResourceUtils.configureResourceSet(resourceSet, mavenURIResolver);
         sessionService.setCurrentResourceSet(resourceSet);

         b2Session = createB2Session(bootSession, resourceSet, properties);
         sessionService.setCurrentSession(b2Session);
         
         Adapters.addAdapter(bootSession, b2Session);
      }
      else
      {
         sessionService.setCurrentSession(b2Session);
         sessionService.setCurrentResourceSet(b2Session.eResource().getResourceSet());
      }

      final int currentIdx = bootSession.getBootstrapProjects().indexOf(bootSession.getCurrentBootstrapProject());
      b2Session.setCurrentProject(b2Session.getProjects().get(currentIdx));

      final ModuleProject currentProject = b2Session.getCurrentProject();
      final MavenProject project = bootSession.getCurrentBootstrapProject();
      configureModuleProject(currentProject, project, properties);

      return b2Session;
   }

   private B2Session createB2Session(BootstrapSession session, ResourceSet resourceSet, Properties properties)
   {
      final B2Session b2Session;

      final String uri = (String) session.getData(CACHE_KEY_SESSION);
      if (uri != null)
      {
         Resource resource = resourceSet.getResource(URI.createURI(uri), true);
         B2Session source = (B2Session) resource.getContents().get(0);
         b2Session = EcoreUtil.copy(source);
         for (ModuleProject moduleProject : b2Session.getProjects())
         {
            if (moduleProject.getDirectory().equals(session.getCurrentBootstrapProject().getBasedir()))
            {
               b2Session.setCurrentProject(moduleProject);
            }
         }
      }
      else
      {
         b2Session = SessionModelFactory.eINSTANCE.createB2Session();

         for (MavenProject project : session.getBootstrapProjects())
         {
            final ModuleProject moduleProject = SessionModelFactory.eINSTANCE.createModuleProject();
            moduleProject.setGroupId(project.getGroupId());
            moduleProject.setArtifactId(project.getArtifactId());
            moduleProject.setVersion(project.getVersion());
            moduleProject.setDirectory(project.getBasedir());

            b2Session.getProjects().add(moduleProject);
            if (project.equals(session.getCurrentBootstrapProject()))
            {
               b2Session.setCurrentProject(moduleProject);
            }
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
      final MavenConverter converter = new MavenConverter(legacySupport.getSession(), bootProject);

      final B2Session b2Session = sessionService.getCurrentSession();
      final ResourceSet resourceSet = sessionService.getCurrentResourceSet();

      sessionService.setCurrentProperties(converter.getProperties());

      processDependencies(resourceSet, b2Session.getCurrentProject(), bootProject);

      final DecouplingModelCache modelCache = initModelCache(bootSessionService.getBootstrapSession(), resourceSet);

      final File moduleDir = bootProject.getBasedir();
      logger.info("Building model for directory " + moduleDir.getName());

      final ITemplates templates = new DefaultTemplateCopier()
      {
         @Override
         protected void addValueSources(SharedResourcesCopier copier, Properties properties)
         {
            super.addValueSources(copier, properties);
            copier.getValueSources().addAll(converter.getValueSources());
         }
      };

      final Set<File> whitelist = new HashSet<File>();
      for (ModuleProject project : b2Session.getProjects())
      {
         whitelist.add(project.getDirectory());
      }
      final IModuleFilter fileFilter = new WhitelistModuleFilter(whitelist);

      final B2Request b2Request = new B2Request();
      b2Request.setModuleDirectory(moduleDir);
      b2Request.setConverter(converter);
      b2Request.setModelCache(modelCache);
      b2Request.setModuleFilter(fileFilter);
      b2Request.setInterpolate(!converter.isSkipInterpolator());
      b2Request.setTemplates(templates);
      return b2Request;
   }

   private DecouplingModelCache initModelCache(BootstrapSession session, ResourceSet resourceSet)
   {
      final DecouplingModelCache modelCache = new DecouplingModelCache(resourceSet, layoutManager);

      @SuppressWarnings("unchecked")
      final Map<File, String> dirToUriMap = (Map<File, String>) session.getData(CACHE_KEY);
      if (dirToUriMap != null)
      {
         modelCache.getDirToUriMap().putAll(dirToUriMap);
      }
      return modelCache;
   }

   private void processDependencies(ResourceSet resourceSet, ModuleProject moduleProject,
      final MavenProject wrapperProject)
   {
      for (Artifact artifact : wrapperProject.getArtifacts())
      {
         if ("module".equals(artifact.getType()))
         {
            final B2Session session = moduleProject.getSession();
            String groupId = artifact.getGroupId();
            String artifactId = artifact.getArtifactId();
            String version = artifact.getBaseVersion();

            if (session.getProject(groupId, artifactId, version) == null)
            {
               final Artifact siteArtifact = resolveSiteZip(wrapperProject, artifact);
               final File zipFile = siteArtifact.getFile();

               final String path = zipFile.getAbsolutePath().replace('\\', '/');
               final String siteUrl = "jar:file:" + path + "!/";
               moduleProject.putAnnotationEntry("b2.resolvedSites", artifact.getId().replace(':', '_'), siteUrl);

               logger.info("Using site " + siteUrl);
            }
         }
      }
   }

   private Artifact resolveSiteZip(final MavenProject wrapperProject, Artifact artifact)
   {
      final String classifier = artifact.getClassifier();

      final StringBuilder cl = new StringBuilder();
      cl.append("site");
      if (classifier != null && classifier.length() > 0)
      {
         cl.append('-');
         cl.append(classifier);
      }

      final AbstractArtifact siteArtifact = new DefaultArtifact(artifact.getGroupId(), artifact.getArtifactId(),
         cl.toString(), "zip", artifact.getBaseVersion());

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
}
