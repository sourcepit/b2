/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.model.io.ModelReader;
import org.apache.maven.model.merge.MavenModelMerger;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sonatype.guice.bean.binders.SpaceModule;
import org.sonatype.guice.bean.binders.WireModule;
import org.sonatype.guice.bean.reflect.URLClassSpace;
import org.sonatype.inject.BeanScanning;
import org.sourcepit.beef.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.beef.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.beef.b2.directory.parser.module.IModuleFilter;
import org.sourcepit.beef.b2.directory.parser.module.WhitelistModuleFilter;
import org.sourcepit.beef.b2.execution.B2;
import org.sourcepit.beef.b2.execution.IB2Listener;
import org.sourcepit.beef.b2.model.builder.util.DecouplingModelCache;
import org.sourcepit.beef.b2.model.builder.util.IConverter;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.session.B2Session;
import org.sourcepit.beef.b2.model.session.ModuleDependency;
import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.b2.model.session.SessionModelFactory;
import org.sourcepit.beef.maven.wrapper.internal.session.BootstrapSession;
import org.sourcepit.beef.maven.wrapper.internal.session.IMavenBootstrapperListener;
import org.sourcepit.tools.shared.resources.harness.SharedResourcesCopier;

import com.google.inject.Guice;
import com.google.inject.Injector;

@Component(role = IMavenBootstrapperListener.class)
public class B2MavenBootstrapperListener implements IMavenBootstrapperListener
{
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
   private Map<String, IInterpolationLayout> layoutMap;

   private final static String CACHE_KEY = B2MavenBootstrapperListener.class.getName() + "#modelCache";
   private final static String CACHE_KEY_SESSION = B2MavenBootstrapperListener.class.getName() + "#session";

   public void beforeProjectBuild(BootstrapSession bootSession, final MavenProject wrapperProject)
   {
      ResourceSet resourceSet = new ResourceSetImpl();
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("file", new XMIResourceFactoryImpl());

      B2Session b2Session = createB2Session(bootSession, resourceSet);

      initJsr330(bootSession, b2Session);

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
      modelCache.put(module);

      b2Session.getCurrentProject().setModuleModel(module);

      final File pomFile = new File(module.getAnnotationEntry(AbstractPomGenerator.SOURCE_MAVEN,
         AbstractPomGenerator.KEY_POM_FILE));

      wrapperProject.setContextValue("pom", pomFile);

      final String layoutId = module.getLayoutId();
      final IInterpolationLayout interpolationLayout = layoutMap.get(layoutId);
      if (interpolationLayout == null)
      {
         throw new IllegalStateException("Layout " + layoutId + " is not supported.");
      }
      final URI uri = URI.createFileURI(interpolationLayout.pathOfMetaDataFile(module, "b2.session"));

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

      bootSession.setData(CACHE_KEY_SESSION, uri.toString());

      storeModelCache(bootSession, modelCache);

      createAndattachModuleJar(wrapperProject, interpolationLayout, b2Session, module, converter);
   }

   private void createAndattachModuleJar(MavenProject bootProject, IInterpolationLayout interpolationLayout,
      B2Session b2Session, AbstractModule module, IConverter converter)
   {
      List<File> files = new ArrayList<File>();
      files.add(new File(b2Session.eResource().getURI().toFileString()));
      files.add(new File(module.eResource().getURI().toFileString()));

      JarOutputStream jarOut = null;
      FileInputStream inputStream = null;
      try
      {
         final File moduleJar = new File(interpolationLayout.pathOfMetaDataFile(module,
            module.getId() + "-" + module.getVersion() + ".jar"));

         if (!moduleJar.exists())
         {
            moduleJar.getParentFile().mkdirs();
            moduleJar.createNewFile();
         }

         jarOut = new JarOutputStream(new FileOutputStream(moduleJar));
         for (File file : files)
         {
            JarEntry entry = new JarEntry(file.getName());
            jarOut.putNextEntry(entry);

            inputStream = new FileInputStream(file);
            IOUtils.copy(inputStream, jarOut);
            jarOut.closeEntry();
         }

         projectHelper.attachArtifact(bootProject, "jar", moduleJar);

         configureInstallPlugin(bootProject, module, moduleJar, converter);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
      finally
      {
         IOUtils.closeQuietly(inputStream);
         IOUtils.closeQuietly(jarOut);
      }
   }

   private void configureInstallPlugin(MavenProject bootProject, AbstractModule module, File moduleJar,
      IConverter converter)
   {
      final String template;

      InputStream openStream = null;
      try
      {
         final URL url = getClass().getClassLoader().getResource(
            "org/sourcepit/beef/b2/internal/generator/install-pom.xml");
         openStream = url.openStream();
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         IOUtils.copy(openStream, outputStream);

         template = new String(outputStream.toByteArray(), "UTF-8");
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
      finally
      {
         IOUtils.closeQuietly(openStream);
      }

      File bootPom = new File(module.getAnnotationEntry("b2", "bootPom"));

      PropertiesMap propertiesMap = new LinkedPropertiesMap();
      propertiesMap.put("pomFile", bootPom.getAbsolutePath());
      propertiesMap.put("file", moduleJar.getAbsolutePath());

      ArtifactRepository repo = bootProject.getDistributionManagementArtifactRepository();
      if (repo != null)
      {
         propertiesMap.put("repositoryId", repo.getId());
         propertiesMap.put("url", repo.getUrl());
         propertiesMap.put("b2.module.version", bootProject.getVersion());
      }

      String interpolate = converter.interpolate(template, propertiesMap);

      try
      {
         final ModelReader modelReader = new DefaultModelReader();
         final Map<String, String> options = Collections.singletonMap(ModelReader.IS_STRICT, "false");

         final Model installConfig = modelReader.read(new ByteArrayInputStream(interpolate.getBytes()), options);

         File pomFile = new File(module.getAnnotationEntry(PomGenerator.SOURCE_MAVEN, PomGenerator.KEY_POM_FILE));
         final Model model = modelReader.read(pomFile, options);

         new MavenModelMerger().merge(model, installConfig, false, null);

         new DefaultModelWriter().write(pomFile, null, model);
      }
      catch (IOException e)
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

            List<Dependency> dependencies = project.getDependencies();
            for (Dependency dependency : dependencies)
            {
               if ("pom".equals(dependency.getType()))
               {
                  ModuleDependency moduleDependency = SessionModelFactory.eINSTANCE.createModuleDependency();
                  moduleDependency.setGroupId(dependency.getGroupId());
                  moduleDependency.setArtifactId(dependency.getArtifactId());
                  moduleDependency.setVersionRange(dependency.getVersion());

                  moduleProject.getDependencies().add(moduleDependency);
               }
            }

            b2Session.getProjects().add(moduleProject);
            if (project.equals(session.getCurrentProject()))
            {
               b2Session.setCurrentProject(moduleProject);
            }
         }
      }
      return b2Session;
   }

   private void initJsr330(final BootstrapSession session, final B2Session b2Session)
   {
      final BootPomSerializer bootPomSerializer = new BootPomSerializer();
      final Injector injector = Guice.createInjector(new WireModule(new com.google.inject.AbstractModule()
      {
         @Override
         protected void configure()
         {
            bind(Logger.class).toInstance(logger);
            bind(BootstrapSession.class).toInstance(session);
            bind(B2Session.class).toInstance(b2Session);
            bind(LegacySupport.class).toInstance(legacySupport);
            bind(ModelBuilder.class).toInstance(modelBuilder);

            bind(IB2Listener.class).toInstance(bootPomSerializer);
         }
      }, new SpaceModule(new URLClassSpace(getClass().getClassLoader()), BeanScanning.CACHE)));
      injector.injectMembers(bootPomSerializer);
      injector.injectMembers(this);
   }

   private DecouplingModelCache initModelCache(BootstrapSession session, ResourceSet resourceSet)
   {
      final DecouplingModelCache modelCache = new DecouplingModelCache(resourceSet);
      modelCache.getIdToLayoutMap().putAll(layoutMap);

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

   }
}
