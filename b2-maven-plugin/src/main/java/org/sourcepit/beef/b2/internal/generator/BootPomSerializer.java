/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingException;
import org.apache.maven.model.building.ModelBuildingRequest;
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
            persistB2BootPom(module, currentProject);
            persistModulePomTemplate(module, currentProject);
         }
         catch (IOException e)
         {
            throw new IllegalStateException(e);
         }
      }
   }

   private File persistB2BootPom(AbstractModule module, final MavenProject currentProject) throws IOException
   {
      final File pomFile = createFile(module, "boot-pom.xml");
      final Model model = currentProject.getModel();
      writeMavenModel(model, pomFile);
      module.putAnnotationEntry("b2", "bootPom", pomFile.getAbsolutePath());
      return pomFile;
   }

   private void persistModulePomTemplate(AbstractModule module, final MavenProject currentProject) throws IOException
   {
      final Model mavenDefaults = getMavenDefaults(currentProject);
      final Model templateModel = currentProject.getModel().clone();
      new ModelCutter().cut(templateModel, mavenDefaults);

      final File pomFile = createFile(module, "module-pom-template.xml");
      writeMavenModel(templateModel, pomFile);
      module.putAnnotationEntry("maven", "modulePomTemplate", pomFile.getAbsolutePath());
   }

   private Model getMavenDefaults(MavenProject mavenProject)
   {
      try
      {
         final Model model = new Model();
         model.setModelVersion(mavenProject.getModelVersion());
         model.setGroupId(mavenProject.getGroupId());
         model.setArtifactId(mavenProject.getArtifactId());
         model.setVersion(mavenProject.getVersion());
         model.setPackaging(mavenProject.getPackaging());

         final ByteArrayOutputStream out = new ByteArrayOutputStream();
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
         request.setProcessPlugins(true);
         request.setTwoPhaseBuilding(false);
         request.setModelSource(modelSource);
         request.setPomFile(pomFile);

         final Model defaultModel = modelBuilder.build(request).getEffectiveModel();
         defaultModel.setGroupId(null);
         defaultModel.setArtifactId(null);
         defaultModel.setVersion(null);
         defaultModel.setPomFile(null);
         return defaultModel;
      }
      catch (ModelBuildingException e)
      {
         throw new IllegalStateException(e);
      }
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
