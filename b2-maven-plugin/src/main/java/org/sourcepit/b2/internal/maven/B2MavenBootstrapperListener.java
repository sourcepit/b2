/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;

import org.apache.maven.RepositoryUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.util.artifact.AbstractArtifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.guice.bean.binders.SpaceModule;
import org.sonatype.guice.bean.binders.WireModule;
import org.sonatype.guice.bean.reflect.URLClassSpace;
import org.sonatype.inject.BeanScanning;
import org.sourcepit.b2.directory.parser.module.IModuleFilter;
import org.sourcepit.b2.directory.parser.module.WhitelistModuleFilter;
import org.sourcepit.b2.execution.B2;
import org.sourcepit.b2.execution.IB2Listener;
import org.sourcepit.b2.internal.generator.AbstractPomGenerator;
import org.sourcepit.b2.internal.generator.BootPomSerializer;
import org.sourcepit.b2.internal.generator.DefaultTemplateCopier;
import org.sourcepit.b2.internal.generator.FixedModelMerger;
import org.sourcepit.b2.internal.generator.ITemplates;
import org.sourcepit.b2.internal.generator.MavenConverter;
import org.sourcepit.b2.internal.scm.svn.SCM;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.builder.util.DecouplingModelCache;
import org.sourcepit.b2.model.builder.util.IB2SessionService;
import org.sourcepit.b2.model.common.util.GavResourceUtils;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.Environment;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.SessionModelFactory;
import org.sourcepit.b2.model.session.SessionModelPackage;
import org.sourcepit.maven.wrapper.internal.session.BootstrapSession;
import org.sourcepit.maven.wrapper.internal.session.IMavenBootstrapperListener;
import org.sourcepit.tools.shared.resources.harness.SharedResourcesCopier;

import com.google.inject.Guice;
import com.google.inject.Injector;

@Component(role = IMavenBootstrapperListener.class)
public class B2MavenBootstrapperListener implements IMavenBootstrapperListener
{
   private final class B2ModelResourceURIResolver implements MavenProjectURIResolver
   {
      public URI resolveProjectUri(MavenProject project, String classifier, String type)
      {
         if ("module".equals(type))
         {
            final StringBuilder sb = new StringBuilder();
            sb.append("b2");
            if (classifier != null)
            {
               sb.append('-');
               sb.append(classifier);
            }
            sb.append('.');
            sb.append(type);

            final File file = new File(project.getBasedir(), ".b2/" + sb.toString());
            projectHelper.attachArtifact(project, type, classifier, file);
            return URI.createFileURI(file.getAbsolutePath());
         }
         return null;
      }
   }

   @Requirement
   private LegacySupport legacySupport;

   @Requirement
   private ModelBuilder modelBuilder;

   @Requirement
   private MavenProjectHelper projectHelper;

   @Requirement
   private Logger logger;

   @Inject
   private B2 b2;

   @Inject
   private LayoutManager layoutManager;

   @Inject
   private IB2SessionService sessionService;

   @Inject
   private SCM scm;

   private final static String CACHE_KEY = B2MavenBootstrapperListener.class.getName() + "#modelCache";
   private final static String CACHE_KEY_SESSION = B2MavenBootstrapperListener.class.getName() + "#session";

   public void beforeProjectBuild(BootstrapSession bootSession, final MavenProject wrapperProject)
   {
      ModuleModelPackage.eINSTANCE.getClass();
      SessionModelPackage.eINSTANCE.getClass();

      B2SessionService sessionService = new B2SessionService();
      initJsr330(bootSession, sessionService);

      MavenURIResolver mavenURIResolver = new MavenURIResolver(bootSession.getBootstrapProjects(),
         bootSession.getCurrentProject());
      mavenURIResolver.getProjectURIResolvers().add(new B2ModelResourceURIResolver());

      ResourceSet resourceSet = new ResourceSetImpl();
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("file", new XMIResourceFactoryImpl());
      GavResourceUtils.configureResourceSet(resourceSet, mavenURIResolver);
      sessionService.setCurrentResourceSet(resourceSet);

      B2Session b2Session = createB2Session(bootSession, resourceSet);
      sessionService.setCurrentSession(b2Session);

      processDependencies(resourceSet, b2Session.getCurrentProject(), wrapperProject);

      final DecouplingModelCache modelCache = initModelCache(bootSession, resourceSet);

      final File moduleDir = wrapperProject.getBasedir();
      logger.info("Building model for directory " + moduleDir.getName());

      final MavenConverter converter = new MavenConverter(legacySupport.getSession(), wrapperProject);
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

      final AbstractModule module = b2.generate(moduleDir, modelCache, fileFilter, converter, templates);

      final LinkedHashSet<String> classifiers = new LinkedHashSet<String>();
      for (SitesFacet sitesFacet : module.getFacets(SitesFacet.class))
      {
         for (SiteProject siteProject : sitesFacet.getProjects())
         {
            String cl = siteProject.getClassifier();
            classifiers.add(cl == null || cl.length() == 0 ? null : cl);
         }
      }

      // add default model if no site classifiers are specified
      if (classifiers.isEmpty())
      {
         classifiers.add(null);
      }

      for (String classifier : classifiers)
      {
         AbstractModule _module = module;
         if (_module.eResource() != null)
         {
            _module = EcoreUtil.copy(_module);
         }

         final URI moduleUri = MavenURIResolver.toArtifactIdentifier(wrapperProject, classifier, "module").toUri();
         resourceSet.createResource(moduleUri).getContents().add(_module);
         modelCache.put(_module);
      }

      b2Session.getCurrentProject().setModuleModel(module);

      final File pomFile = new File(module.getAnnotationEntry(AbstractPomGenerator.SOURCE_MAVEN,
         AbstractPomGenerator.KEY_POM_FILE));

      wrapperProject.setContextValue("pom", pomFile);

      final String layoutId = module.getLayoutId();
      final File sessionFile = new File(layoutManager.getLayout(layoutId).pathOfMetaDataFile(module, "b2.session"));
      final URI uri = URI.createFileURI(sessionFile.getAbsolutePath());

      final Resource resource = resourceSet.createResource(uri);
      resource.getContents().add(b2Session);
      try
      {
         resource.save(null);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      projectHelper.attachArtifact(wrapperProject, "session", null, sessionFile);

      processAttachments(wrapperProject, pomFile);

      bootSession.setData(CACHE_KEY_SESSION, uri.toString());

      storeModelCache(bootSession, modelCache);
   }

   private void processAttachments(MavenProject wrapperProject, File pomFile)
   {
      final List<Artifact> attachedArtifacts = wrapperProject.getAttachedArtifacts();
      if (attachedArtifacts == null)
      {
         return;
      }

      Xpp3Dom artifactsNode = new Xpp3Dom("artifacts");
      for (Artifact artifact : attachedArtifacts)
      {
         Xpp3Dom artifactNode = new Xpp3Dom("artifact");

         if (artifact.getClassifier() != null)
         {
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
      try
      {
         moduleModel = new DefaultModelReader().read(pomFile, null);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      new FixedModelMerger().merge(moduleModel, model, false, null);
      try
      {
         new DefaultModelWriter().write(pomFile, null, moduleModel);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   @Requirement
   private RepositorySystem repositorySystem;

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

   private B2Session createB2Session(BootstrapSession session, ResourceSet resourceSet)
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
            if (moduleProject.getDirectory().equals(session.getCurrentProject().getBasedir()))
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
            if (project.equals(session.getCurrentProject()))
            {
               b2Session.setCurrentProject(moduleProject);
            }
         }
      }

      ModuleProject currentProject = b2Session.getCurrentProject();
      MavenProject project = session.getCurrentProject();

      Set<Artifact> dependencies = project.getArtifacts();
      for (Artifact dependency : dependencies)
      {
         if ("module".equals(dependency.getType()))
         {
            ModuleDependency moduleDependency = SessionModelFactory.eINSTANCE.createModuleDependency();
            moduleDependency.setGroupId(dependency.getGroupId());
            moduleDependency.setArtifactId(dependency.getArtifactId());
            moduleDependency.setClassifier(dependency.getClassifier());
            moduleDependency.setVersionRange(dependency.getVersion());

            currentProject.getDependencies().add(moduleDependency);
         }
      }

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

      return b2Session;
   }

   private void initJsr330(final BootstrapSession session, final B2SessionService sessionService)
   {
      final BootPomSerializer bootPomSerializer = new BootPomSerializer();
      final Injector injector = Guice.createInjector(new WireModule(new com.google.inject.AbstractModule()
      {
         @Override
         protected void configure()
         {
            bind(Logger.class).toInstance(logger);
            bind(BootstrapSession.class).toInstance(session);
            bind(LegacySupport.class).toInstance(legacySupport);
            bind(ModelBuilder.class).toInstance(modelBuilder);
            bind(IB2SessionService.class).toInstance(sessionService);

            bind(IB2Listener.class).toInstance(bootPomSerializer);
         }
      }, new SpaceModule(new URLClassSpace(getClass().getClassLoader()), BeanScanning.CACHE)));
      injector.injectMembers(bootPomSerializer);
      injector.injectMembers(this);
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

   private void storeModelCache(BootstrapSession session, final DecouplingModelCache modelCache)
   {
      session.setData(CACHE_KEY, modelCache.getDirToUriMap());
   }

   public void afterProjectBuild(BootstrapSession session, MavenProject wrapperProject)
   {
      final B2Session b2Session = sessionService.getCurrentSession();
      for (ModuleProject project : b2Session.getProjects())
      {
         if (wrapperProject.getBasedir().equals(project.getDirectory()))
         {
            b2Session.setCurrentProject(project);
            break;
         }
      }

      final String setScmIgnoresProp = wrapperProject.getProperties().getProperty("b2.scm.setScmIgnores",
         System.getProperty("b2.scm.setScmIgnores", "false"));

      final boolean isSetScmIgnores = Boolean.valueOf(setScmIgnoresProp).booleanValue();
      if (isSetScmIgnores)
      {
         ModuleProject project = b2Session.getCurrentProject();
         if (project != null && project.getModuleModel() != null)
         {
            scm.doSetScmIgnores(project.getModuleModel());
         }
      }
   }
}
