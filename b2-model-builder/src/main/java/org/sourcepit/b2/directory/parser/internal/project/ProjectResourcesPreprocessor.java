/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import static org.sourcepit.b2.directory.parser.internal.project.ProjectResourcesUtils.getResourcesDir;
import static org.sourcepit.common.utils.lang.Exceptions.pipe;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.interpolation.AbstractValueSource;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.common.utils.file.FileUtils;
import org.sourcepit.common.utils.file.FileVisitor;
import org.sourcepit.common.utils.path.PathUtils;
import org.sourcepit.common.utils.props.PropertiesSource;
import org.sourcepit.tools.shared.resources.harness.AbstractPropertyInterpolator;

public class ProjectResourcesPreprocessor implements ProjectPreprocessingParticipant
{
   @Override
   public void preprocess(Project project, PropertiesSource properties)
   {
      final File projectDir = project.getDirectory();
      final File resourcesDir = getResourcesDir(projectDir, properties);
      if (resourcesDir.exists())
      {
         processResources(resourcesDir, projectDir, properties);
      }
   }

   private void processResources(File resourcesDir, File projectDir, PropertiesSource properties)
   {
      FileUtils.accept(resourcesDir, new ResourceProcessor(resourcesDir, projectDir, properties));
   }

   private class ResourceProcessor implements FileVisitor
   {
      private class FileCopier extends AbstractPropertyInterpolator
      {
         public void copy(File srcFile, File destFile) throws IOException
         {
            InputStream from = null;
            OutputStream to = null;
            try
            {
               from = new BufferedInputStream(new FileInputStream(srcFile));
               to = new BufferedOutputStream(new FileOutputStream(destFile));
               newCopier().copy(from, to, "UTF-8", destFile);
            }
            finally
            {
               IOUtils.closeQuietly(from);
               IOUtils.closeQuietly(to);
            }
         }
      }

      private final File resourcesDir;
      private final File projectDir;
      private final PropertiesSource properties;

      public ResourceProcessor(File resourcesDir, File projectDir, PropertiesSource properties)
      {
         this.resourcesDir = resourcesDir;
         this.projectDir = projectDir;
         this.properties = properties;
      }

      @Override
      public boolean visit(File file)
      {
         final String path = PathUtils.getRelativePath(file, resourcesDir, "/");
         if (path.length() > 0)
         {
            process(file, projectDir, path, properties);
         }
         return true;
      }

      private void process(File srcFile, File baseDir, String destPath, PropertiesSource properties)
      {
         final File destFile = new File(baseDir, destPath);
         if (destFile.exists())
         {
            try
            {
               org.apache.commons.io.FileUtils.forceDelete(destFile);
            }
            catch (IOException e)
            {
               throw pipe(e);
            }
         }

         if (srcFile.isDirectory())
         {
            try
            {
               org.apache.commons.io.FileUtils.forceMkdir(destFile);
            }
            catch (IOException e)
            {
               throw pipe(e);
            }
         }
         else
         {
            try
            {
               processFile(srcFile, destFile, baseDir, destPath, properties);
            }
            catch (IOException e)
            {
               throw pipe(e);
            }
         }
      }

      private void processFile(File srcFile, File destFile, File baseDir, String destPath,
         final PropertiesSource properties) throws IOException
      {
         FileCopier fileCopier = new FileCopier();
         fileCopier.getValueSources().add(new AbstractValueSource(false)
         {
            public Object getValue(String expression)
            {
               return properties.get(expression);
            }
         });
         fileCopier.copy(srcFile, destFile);
      }
   }

}
