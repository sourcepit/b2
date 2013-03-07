/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.project.MavenProject;
import org.sourcepit.b2.execution.IB2Listener;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.module.AbstractModule;

/**
 * @author Bernd
 */
@Named
public class BootPomSerializer implements IB2Listener
{
   @Inject
   private LegacySupport legacySupport;

   @Inject
   private Map<String, IInterpolationLayout> layoutMap;

   @Inject
   private ModulePomBuilder modulePomBuilder;

   public void startGeneration(AbstractModule module)
   {
      final MavenProject currentProject = legacySupport.getSession().getCurrentProject();
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
      final Model model = currentProject.getOriginalModel().clone();
      writeMavenModel(model, pomFile);
      module.setAnnotationData("b2", "bootPom", pomFile.getAbsolutePath());
      return pomFile;
   }

   private void persistModulePomTemplate(AbstractModule module, final MavenProject currentProject) throws IOException
   {
      final Model pomTemplate = modulePomBuilder.buildModulePom(currentProject);
      final File pomFile = createFile(module, "module-pom-template.xml");
      writeMavenModel(pomTemplate, pomFile);
      module.setAnnotationData("maven", "modulePomTemplate", pomFile.getAbsolutePath());
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
