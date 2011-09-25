/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.project.MavenProject;
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
      final Model model = new DefaultModelReader().read(bootPomFile, null);
      // model.getDependencies().clear();

      final File pomFile = createFile(module, "module-pom-template.xml");
      writeMavenModel(model, pomFile);
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
