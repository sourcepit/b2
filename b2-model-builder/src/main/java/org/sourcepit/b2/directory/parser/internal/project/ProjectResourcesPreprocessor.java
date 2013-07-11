/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import static org.sourcepit.common.utils.lang.Exceptions.pipe;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sourcepit.b2.internal.cleaner.IModuleGarbageCollector;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.utils.content.ContentTypes;
import org.sourcepit.common.utils.props.PlexusPropertyFilterFactory;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.common.utils.props.PropertyConverter;
import org.sourcepit.common.utils.resources.AbstractResourceFilter;
import org.sourcepit.common.utils.resources.DefaultResourceFilter;
import org.sourcepit.common.utils.resources.FileStorage;
import org.sourcepit.common.utils.resources.FileTraverser;

@Named
public class ProjectResourcesPreprocessor
   implements
      IModuleGarbageCollector,
      ProjectDetectionRule<Project>,
      ProjectPreprocessingParticipant
{
   @Inject
   private ProjectDetector projectDetector;

   @Override
   public boolean isGarbage(File file)
   {
      return false;
   }

   @Override
   public Project detect(File directory, PropertiesSource properties)
   {
      final File resourcesDir = getResourcesDir(directory, properties);
      if (resourcesDir.exists())
      {
         final Project project = projectDetector.detect(resourcesDir, properties);
         if (project != null && project.getDirectory() != null)
         {
            project.setDirectory(directory);
         }
         return project;
      }
      return null;
   }

   @Override
   public void preprocess(Project project, PropertiesSource properties)
   {
      final File projectDir = project.getDirectory();
      final File resourcesDir = getResourcesDir(projectDir, properties);
      if (resourcesDir.exists())
      {
         try
         {
            processResources(resourcesDir, projectDir, properties);
         }
         catch (IOException e)
         {
            throw pipe(e);
         }
      }
   }

   private void processResources(File resourcesDir, File projectDir, PropertiesSource properties) throws IOException
   {
      AbstractResourceFilter filter = createResourceFilter(properties);
      filter.copyAndFilter(new FileTraverser(resourcesDir), new FileStorage(projectDir));
   }

   @Inject
   private Map<String, PropertyConverter> propertyConverters;

   private AbstractResourceFilter createResourceFilter(PropertiesSource properties)
   {
      final String targetEncoding = "UTF-8";
      final PlexusPropertyFilterFactory filterFactory = new PlexusPropertyFilterFactory("\\");
      return new DefaultResourceFilter(ContentTypes.getDefault(), targetEncoding, properties, propertyConverters,
         filterFactory);
   }

   public static File getResourcesDir(File projectDir, PropertiesSource properties)
   {
      return new File(projectDir, "resources");
   }

}
