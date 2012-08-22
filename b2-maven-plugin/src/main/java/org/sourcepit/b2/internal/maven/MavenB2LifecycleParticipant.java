/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sourcepit.b2.execution.AbstractB2SessionLifecycleParticipant;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.execution.B2SessionLifecycleParticipant;
import org.sourcepit.b2.internal.generator.AbstractPomGenerator;
import org.sourcepit.b2.internal.generator.FixedModelMerger;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.common.utils.lang.ThrowablePipe;

@Named
public class MavenB2LifecycleParticipant extends AbstractB2SessionLifecycleParticipant
   implements
      B2SessionLifecycleParticipant
{
   @Inject
   private MavenProjectHelper projectHelper;

   @Inject
   private LayoutManager layoutManager;

   @Inject
   private LegacySupport legacySupport;

   @Inject
   private B2SessionService b2SessionService;

   public void postPrepareProject(B2Session session, ModuleProject project, B2Request request, AbstractModule module,
      ThrowablePipe errors)
   {
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

      final MavenProject bootProject = legacySupport.getSession().getCurrentProject();

      // HACK
      // final DecouplingModelCache modelCache = (DecouplingModelCache)
      // request.getModelCache();DecouplingModelCache.class.getClassLoader()

      final ResourceSet resourceSet = b2SessionService.getCurrentResourceSet();

      if (session.eResource() != null)
      {
         resourceSet.getResources().remove(session.eResource());
         session.eResource().getContents().clear();
      }

      for (String classifier : classifiers)
      {
         final URI moduleUri = MavenURIResolver.toArtifactIdentifier(bootProject, classifier, "module").toUri();

         Resource moduleResource = module.eResource();
         if (moduleResource == null)
         {
            moduleResource = resourceSet.createResource(moduleUri);
            moduleResource.getContents().add(module);
            save(resourceSet, module);
         }
         else if (!moduleUri.equals(moduleResource.getURI()))
         {
            AbstractModule copy = EcoreUtil.copy(module);
            moduleResource = resourceSet.createResource(moduleUri);
            moduleResource.getContents().add(copy);
            save(resourceSet, copy);
         }
      }

      final File pomFile = new File(module.getAnnotationEntry(AbstractPomGenerator.SOURCE_MAVEN,
         AbstractPomGenerator.KEY_POM_FILE));

      bootProject.setContextValue("pom", pomFile);

      final String layoutId = module.getLayoutId();
      final File sessionFile = new File(layoutManager.getLayout(layoutId).pathOfMetaDataFile(module, "b2.session"));
      final URI uri = URI.createFileURI(sessionFile.getAbsolutePath());

      final Resource resource = resourceSet.createResource(uri);
      resource.getContents().add(session);
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

      // bootSession.setData(CACHE_KEY_SESSION, uri.toString());
      //
      // storeModelCache(bootSession, modelCache);
   }

   private void save(ResourceSet resourceSet, AbstractModule module)
   {
      Resource resource;

      if (module.eResource() != null)
      {
         resource = module.eResource();
      }
      else
      {
         final String layoutId = module.getLayoutId();
         final IInterpolationLayout layout = layoutManager.getLayout(layoutId);
         final URI uri = URI.createFileURI(layout.pathOfMetaDataFile(module, "b2.module"));
         resource = resourceSet.createResource(uri);
         resource.getContents().add(module);
      }

      try
      {
         resource.save(null);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }

   public static <T extends EObject> T copy(T eObject)
   {
      Copier copier = new Copier();
      EObject result = copier.copy(eObject);
      copier.copyReferences();

      @SuppressWarnings("unchecked")
      T t = (T) result;
      return t;
   }

   // private void storeModelCache(BootstrapSession session, final DecouplingModelCache modelCache)
   // {
   // session.setData(CACHE_KEY, modelCache.getDirToUriMap());
   // }

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
}
