/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;

import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import org.sonatype.guice.bean.binders.SpaceModule;
import org.sonatype.guice.bean.binders.WireModule;
import org.sonatype.guice.bean.reflect.URLClassSpace;
import org.sonatype.inject.BeanScanning;
import org.sourcepit.beef.b2.directory.parser.module.IModuleFilter;
import org.sourcepit.beef.b2.directory.parser.module.WhitelistModuleFilter;
import org.sourcepit.beef.b2.model.builder.util.DecouplingModelCache;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.beef.b2.model.module.AbstractModule;
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
   private Logger logger;

   @Inject
   private B2 b2;

   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   private final static String CACHE_KEY = B2MavenBootstrapperListener.class.getName() + "#modelCache";

   public void beforeProjectBuild(BootstrapSession session, MavenProject wrapperProject)
   {
      initJsr330(session, wrapperProject);

      final DecouplingModelCache modelCache = initModelCache(session);

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
      for (MavenProject mavenProject : session.getWrapperProjects())
      {
         whitelist.add(mavenProject.getBasedir());
      }

      final IModuleFilter fileFilter = new WhitelistModuleFilter(whitelist);

      final AbstractModule generate = b2.generate(moduleDir, modelCache, fileFilter, converter, templates);
      modelCache.put(generate);

      final File pomFile = new File(generate.getAnnotationEntry(AbstractPomGenerator.SOURCE_MAVEN,
         AbstractPomGenerator.KEY_POM_FILE));

      wrapperProject.setContextValue("pom", pomFile);

      storeModelCache(session, modelCache);
   }

   private void initJsr330(final BootstrapSession session, final MavenProject wrapperProject)
   {
      final Injector injector = Guice.createInjector(new WireModule(new com.google.inject.AbstractModule()
      {
         @Override
         protected void configure()
         {
            bind(Logger.class).toInstance(logger);
            bind(BootstrapSession.class).toInstance(session);
         }
      }, new SpaceModule(new URLClassSpace(getClass().getClassLoader()), BeanScanning.CACHE)));
      injector.injectMembers(this);
   }

   private DecouplingModelCache initModelCache(BootstrapSession session)
   {
      final DecouplingModelCache modelCache = new DecouplingModelCache();
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
