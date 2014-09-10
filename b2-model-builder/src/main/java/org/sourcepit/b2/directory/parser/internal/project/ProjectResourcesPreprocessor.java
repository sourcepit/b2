/*
 * Copyright 2014 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.b2.directory.parser.internal.project;

import static org.sourcepit.common.utils.lang.Exceptions.pipe;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

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
public class ProjectResourcesPreprocessor implements ProjectDetectionRule<Project>, ProjectPreprocessingParticipant
{
   @Inject
   private ProjectDetector projectDetector;

   @Inject
   private ContentTypes contentTypes;

   @Override
   public Project detect(File directory, PropertiesSource properties)
   {
      final File resourcesDir = getResourcesDir(directory, properties);
      if (resourcesDir.exists() && resourcesDir.isDirectory())
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
      if (resourcesDir.exists() && resourcesDir.isDirectory())
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
      final String targetEncoding = properties.get("project.build.sourceEncoding", Charset.defaultCharset().name());
      final PlexusPropertyFilterFactory filterFactory = new PlexusPropertyFilterFactory("\\");
      return new DefaultResourceFilter(contentTypes, targetEncoding, properties, propertyConverters, filterFactory);
   }

   public static File getResourcesDir(File projectDir, PropertiesSource properties)
   {
      return new File(projectDir, properties.get("b2.projects.resourcesDirectory", "res"));
   }

}
