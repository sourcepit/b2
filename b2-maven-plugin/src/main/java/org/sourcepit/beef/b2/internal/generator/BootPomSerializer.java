/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.Resource;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingException;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelBuildingResult;
import org.apache.maven.model.building.StringModelSource;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingRequest;
import org.sourcepit.beef.b2.execution.IB2Listener;
import org.sourcepit.beef.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.maven.wrapper.internal.session.BootstrapSession;

/**
 * @author Bernd
 */
public class BootPomSerializer implements IB2Listener
{
   @Inject
   private BootstrapSession bootSession;

   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private ModelBuilder modelBuilder;

   public void startGeneration(AbstractModule module)
   {
      final MavenProject currentProject = bootSession.getCurrentProject();
      if (module.getDirectory().equals(currentProject.getBasedir()))
      {
         try
         {
            final File bootPomFile = persistB2BootPom(module, currentProject);
            persistModulePomTemplate(module, bootPomFile);
         }
         catch (IOException e)
         {
            throw new IllegalStateException(e);
         }
      }
   }

   /**
    * @param result
    * @return
    */
   protected Model emptyModel(ModelBuildingResult result)
   {
      return result.getEffectiveModel();
   }

   private void remove(Model model, Model superModel)
   {
      model.setRepositories(remove(model.getRepositories(), superModel.getRepositories()));
      model.setPluginRepositories(remove(model.getPluginRepositories(), superModel.getPluginRepositories()));
      model.setBuild(remove(model.getBuild(), superModel.getBuild()));
      model.setReporting(remove(model.getReporting(), superModel.getReporting()));
   }

   private <T> T remove(T object, T superObject)
   {
      if (object == null)
      {
         return null;
      }

      if (superObject == null)
      {
         return object;
      }

      if (object instanceof Collection)
      {
         Collection collection = (Collection) object;
         Collection superCollection = (Collection) superObject;
         List remove = new ArrayList(collection.size());
         for (Object entry : collection)
         {
            if (contains(superCollection, entry))
            {
               remove.add(entry);
            }
         }
         collection.removeAll(remove);
         return object;
      }

      if (object instanceof Build)
      {
         Build build = (Build) object;
         Build superBuild = (Build) superObject;

         build.setDirectory(remove(build.getDirectory(), superBuild.getDirectory()));
         build.setFinalName(remove(build.getFinalName(), superBuild.getFinalName()));
         build.setOutputDirectory(remove(build.getOutputDirectory(), superBuild.getOutputDirectory()));
         build
            .setScriptSourceDirectory(remove(build.getScriptSourceDirectory(), superBuild.getScriptSourceDirectory()));
         build.setSourceDirectory(remove(build.getSourceDirectory(), superBuild.getSourceDirectory()));
         build.setTestOutputDirectory(remove(build.getTestOutputDirectory(), superBuild.getTestOutputDirectory()));
         build.setTestSourceDirectory(remove(build.getTestSourceDirectory(), superBuild.getTestSourceDirectory()));

         build.setResources(remove(build.getResources(), superBuild.getResources()));
         build.setTestResources(remove(build.getTestResources(), superBuild.getTestResources()));
         build.setPlugins(remove(build.getPlugins(), superBuild.getPlugins()));

         return object;
      }

      if (object instanceof Reporting)
      {
         Reporting reporting = (Reporting) object;
         Reporting superReporting = (Reporting) superObject;
         reporting.setOutputDirectory(remove(reporting.getOutputDirectory(), superReporting.getOutputDirectory()));
         return object;
      }

      return equals(object, superObject) ? null : object;
   }

   protected boolean contains(Collection superCollection, Object entry)
   {
      for (Object superEntry : superCollection)
      {
         if (equals(entry, superEntry))
         {
            return true;
         }
      }
      return false;
   }

   protected boolean equals(Object o1, Object o2)
   {
      if (o1 == null)
      {
         return o2 == null;
      }

      if (o1 instanceof Resource)
      {
         Resource resource = (Resource) o1;
         Resource superResource = (Resource) o2;
         if (equals(resource.getDirectory(), superResource.getDirectory()))
         {
            return true;
         }
         return false;
      }

      return o1.equals(o2);
   }

   private File persistB2BootPom(AbstractModule module, final MavenProject currentProject) throws IOException
   {
      final File pomFile = createFile(module, "boot-pom.xml");
      final Model model = currentProject.getModel();
      writeMavenModel(model, pomFile);
      module.putAnnotationEntry("b2", "bootPom", pomFile.getAbsolutePath());
      return pomFile;
   }

   private void persistModulePomTemplate(AbstractModule module, final File bootPomFile) throws IOException
   {
      final Model templateModel;// = new DefaultModelReader().read(bootPomFile, null);
      // model.getDependencies().clear();

      try
      {
         MavenProject mavenProject = bootSession.getCurrentProject();

         final Model model = new Model();
         model.setModelVersion(mavenProject.getModelVersion());
         model.setGroupId(mavenProject.getGroupId());
         model.setArtifactId(mavenProject.getArtifactId());
         model.setVersion(mavenProject.getVersion());

         ByteArrayOutputStream out = new ByteArrayOutputStream();
         try
         {
            new DefaultModelWriter().write(out, null, model);
         }
         catch (IOException e)
         {
            throw new IllegalStateException(e);
         }

         String dummyModel = out.toString();
         File pomFile = new File(mavenProject.getBasedir(), "dummy.pom");
         StringModelSource modelSource = new StringModelSource(dummyModel, pomFile.getPath());

         ProjectBuildingRequest projectBuildingRequest = mavenProject.getProjectBuildingRequest();

         ModelBuildingRequest request = new DefaultModelBuildingRequest();
         request.setActiveProfileIds(projectBuildingRequest.getActiveProfileIds());
         request.setProfiles(projectBuildingRequest.getProfiles());
         request.setSystemProperties(projectBuildingRequest.getSystemProperties());
         request.setUserProperties(projectBuildingRequest.getUserProperties());
         request.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MINIMAL);
         request.setTwoPhaseBuilding(false);
         request.setModelSource(modelSource);
         request.setPomFile(pomFile);

         ModelBuildingResult result = modelBuilder.build(request);
         templateModel = mavenProject.getModel();
         remove(templateModel, emptyModel(result));
      }
      catch (ModelBuildingException e)
      {
         throw new IllegalStateException(e);
      }

      final File pomFile = createFile(module, "module-pom-template.xml");
      writeMavenModel(templateModel, pomFile);
      module.putAnnotationEntry("maven", "modulePomTemplate", pomFile.getAbsolutePath());
   }

   private File createFile(AbstractModule module, String fileName) throws IOException
   {
      final IInterpolationLayout layout = layoutMap.get(module.getLayoutId());
      final File pomFile = new File(layout.pathOfMetaDataFile(module, fileName));
      if (!pomFile.exists())
      {
         pomFile.getParentFile().mkdirs();
         pomFile.createNewFile();
      }
      return pomFile;
   }

   private void writeMavenModel(final Model model, final File pomFile) throws IOException
   {
      new DefaultModelWriter().write(pomFile, null, model);
   }
}
