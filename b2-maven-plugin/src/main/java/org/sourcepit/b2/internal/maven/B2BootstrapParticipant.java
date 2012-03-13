/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sourcepit.b2.execution.B2;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.internal.generator.AbstractPomGenerator;
import org.sourcepit.b2.internal.generator.FixedModelMerger;
import org.sourcepit.b2.internal.scm.svn.SCM;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.builder.util.DecouplingModelCache;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.maven.bootstrap.participation.BootstrapParticipant;
import org.sourcepit.maven.bootstrap.participation.BootstrapSession;

@Named
public class B2BootstrapParticipant implements BootstrapParticipant
{
   @Inject
   private LegacySupport legacySupport;

   @Inject
   private MavenProjectHelper projectHelper;

   @Inject
   private B2 b2;

   @Inject
   private LayoutManager layoutManager;

   @Inject
   private B2SessionService sessionService;

   @Inject
   private SCM scm;

   @Inject
   private B2SessionInitializer b2SessionInitializer;

   private static final String CACHE_KEY = B2BootstrapParticipant.class.getName() + "#modelCache";
   private static final String CACHE_KEY_SESSION = B2BootstrapParticipant.class.getName() + "#session";

   public void beforeBuild(BootstrapSession bootSession, MavenProject bootProject)
   {
      final MavenSession mavenSession = legacySupport.getSession();
      final Properties properties = new Properties();
      properties.putAll(mavenSession.getSystemProperties());
      properties.putAll(mavenSession.getUserProperties());
      b2SessionInitializer.initialize(bootSession, properties);

      final B2Request b2Request = b2SessionInitializer.newB2Request(bootProject);

      final AbstractModule module = b2.generate(b2Request);

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

      // HACK
      final DecouplingModelCache modelCache = (DecouplingModelCache) b2Request.getModelCache();
      final ResourceSet resourceSet = sessionService.getCurrentResourceSet();
      for (String classifier : classifiers)
      {
         AbstractModule classifiedModule = module;
         if (classifiedModule.eResource() != null)
         {
            classifiedModule = EcoreUtil.copy(classifiedModule);
         }

         final URI moduleUri = MavenURIResolver.toArtifactIdentifier(bootProject, classifier, "module").toUri();
         resourceSet.createResource(moduleUri).getContents().add(classifiedModule);
         modelCache.put(classifiedModule);
      }

      final B2Session b2Session = sessionService.getCurrentSession();
      b2Session.getCurrentProject().setModuleModel(module);

      final File pomFile = new File(module.getAnnotationEntry(AbstractPomGenerator.SOURCE_MAVEN,
         AbstractPomGenerator.KEY_POM_FILE));

      bootProject.setContextValue("pom", pomFile);

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

      projectHelper.attachArtifact(bootProject, "session", null, sessionFile);

      processAttachments(bootProject, pomFile);

      bootSession.setData(CACHE_KEY_SESSION, uri.toString());

      storeModelCache(bootSession, modelCache);
   }

   private void storeModelCache(BootstrapSession session, final DecouplingModelCache modelCache)
   {
      session.setData(CACHE_KEY, modelCache.getDirToUriMap());
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

   public void afterBuild(BootstrapSession bootSession, MavenProject bootProject)
   {
      final B2Session b2Session = sessionService.getCurrentSession();
      for (ModuleProject project : b2Session.getProjects())
      {
         if (bootProject.getBasedir().equals(project.getDirectory()))
         {
            b2Session.setCurrentProject(project);
            break;
         }
      }

      final String setScmIgnoresProp = bootProject.getProperties().getProperty("b2.scm.setScmIgnores",
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
