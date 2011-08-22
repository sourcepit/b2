/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.maven.internal.wrapper;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.sourcepit.beef.maven.wrapper.internal.session.AbstractMavenBootstrapper;
import org.sourcepit.beef.maven.wrapper.internal.session.ISessionListener;

@Component(role = ISessionListener.class)
public class B2MavenBootstrapper extends AbstractMavenBootstrapper
{
   protected List<File> getModuleDescriptors(MavenSession session) throws MavenExecutionException
   {
      final File baseDir = new File(session.getRequest().getBaseDirectory());
      return findModuleDescriptors(baseDir);
   }

   protected void afterWrapperProjectsInitialized(MavenSession session, List<MavenProject> bootstrapProjects)
   {
      if (!bootstrapProjects.isEmpty())
      {
         final MavenProject mainProject = bootstrapProjects.get(bootstrapProjects.size() - 1);
         final File pom = (File) mainProject.getContextValue("pom");
         if (pom != null)
         {
            final MavenExecutionRequest request = session.getRequest();
            request.setPom(pom);
         }
      }
   }

   protected List<File> findModuleDescriptors(final File baseDir)
   {
      final List<File> moduleFiles = new ArrayList<File>();
      final File moduleFile = findModuleFile(baseDir);
      if (moduleFile != null)
      {
         collectModuleFiles(moduleFiles, baseDir);
         moduleFiles.add(moduleFile);
      }
      return moduleFiles;
   }

   private File findModuleFile(File member)
   {
      if (member.isDirectory() && member.canRead())
      {
         final File projectFile = new File(member, "module.xml");
         if (projectFile.canRead())
         {
            return projectFile;
         }
      }
      return null;
   }

   private void collectModuleFiles(final List<File> moduleFiles, File baseDir)
   {
      baseDir.listFiles(new FileFilter()
      {
         public boolean accept(File member)
         {
            final File moduleFile = findModuleFile(member);
            if (moduleFile != null)
            {
               collectModuleFiles(moduleFiles, member);
               moduleFiles.add(moduleFile);
            }
            return false;
         }
      });
   }
}
