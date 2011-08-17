/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.beef.b2.maven.internal.wrapper;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.building.StringModelSource;
import org.apache.maven.model.io.ModelWriter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.sourcepit.beef.b2.maven.internal.converter.IModuleDescriptorConverter;
import org.sourcepit.beef.maven.wrapper.internal.session.AbstractMavenBootstrapper;
import org.sourcepit.beef.maven.wrapper.internal.session.ISessionListener;

@Component(role = ISessionListener.class)
public class B2MavenBootstrapper extends AbstractMavenBootstrapper
{
   @Requirement
   private ModelWriter modelWriter;

   @Requirement
   private PlexusContainer plexus;

   protected List<ModelSource> createWrapperProjectsModelSources(MavenSession session) throws MavenExecutionException
   {
      final List<ModelSource> modelSources = new ArrayList<ModelSource>();
      final File baseDir = new File(session.getRequest().getBaseDirectory());
      final List<File> moduleDescriptors = findModuleDescriptors(baseDir);
      if (moduleDescriptors == null || moduleDescriptors.size() == 0)
      {
         return modelSources;
      }

      for (File moduleDescriptor : moduleDescriptors)
      {

         final IModuleDescriptorConverter converter = lookupConverter(moduleDescriptor);
         try
         {
            final Model model = converter.convert(moduleDescriptor);
            final StringWriter sw = new StringWriter();
            modelWriter.write(sw, null, model);
            modelSources.add(new StringModelSource(sw.toString(), moduleDescriptor.getPath()));
         }
         catch (IOException e)
         {
            throw new MavenExecutionException("Unable to build maven model for module descriptor: " + moduleDescriptor,
               e);
         }
      }
      return modelSources;
   }

   private IModuleDescriptorConverter lookupConverter(File moduleDescriptor) throws MavenExecutionException
   {
      try
      {
         return plexus.lookup(IModuleDescriptorConverter.class, moduleDescriptor.getName());
      }
      catch (ComponentLookupException e)
      {
         throw new MavenExecutionException("Unable to find converter for module descriptor: " + moduleDescriptor, e);
      }
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
