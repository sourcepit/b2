/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.beef.b2.internal.generator;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.apache.maven.model.Model;
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
            final IInterpolationLayout layout = layoutMap.get(module.getLayoutId());
            final File pomFile = new File(layout.pathOfMetaDataFile(module, "boot-pom.xml"));
            if (!pomFile.exists())
            {
               pomFile.getParentFile().mkdirs();
               pomFile.createNewFile();
            }
            final Model model = currentProject.getModel();
            new DefaultModelWriter().write(pomFile, null, model);
            module.putAnnotationEntry("maven", "bootPom", pomFile.getAbsolutePath());
         }
         catch (IOException e)
         {
            throw new IllegalStateException(e);
         }
      }
   }
}
