/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.maven.core;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sourcepit.b2.files.ModuleDirectory;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.common.utils.adapt.AbstractAdapterFactory;
import org.sourcepit.common.utils.adapt.Adapters;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;


public class B2MavenBridge
{
   private MavenSession session;

   private B2MavenBridge(MavenSession session)
   {
      this(session, initResourceSet(session));
   }

   private static ResourceSet initResourceSet(MavenSession session)
   {
      final ResourceSet resourceSet = createResourceSet();
      for (MavenProject mavenProject : session.getProjects())
      {
         if (isModuleDir(mavenProject.getBasedir()))
         {
            setUriMappings(mavenProject.getBasedir(), resourceSet);
         }
      }
      return resourceSet;
   }

   private B2MavenBridge(MavenSession session, ResourceSet resourceSet)
   {
      this.session = session;
      connect(session, resourceSet);
   }

   private static boolean isModuleDir(File basedir)
   {
      return new File(basedir, "module.xml").exists();
   }

   private static void setUriMappings(final File moduleDir, ResourceSet resourceSet)
   {
      PropertiesMap uriMap = new LinkedPropertiesMap();
      uriMap.load(new File(moduleDir, ".b2/uriMap.properties"));

      for (Entry<String, String> entry : uriMap.entrySet())
      {
         URI key = URI.createURI(entry.getKey());
         URI value = URI.createURI(entry.getValue());
         resourceSet.getURIConverter().getURIMap().put(key, value);
      }
   }

   private static ResourceSet createResourceSet()
   {
      final ResourceSet resourceSet = new ResourceSetImpl();
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("gav", new XMIResourceFactoryImpl());
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("file", new XMIResourceFactoryImpl());
      resourceSet.getPackageRegistry().put(ModuleModelPackage.eNS_URI, ModuleModelPackage.eINSTANCE);
      return resourceSet;
   }

   // TODO move
   public static URI toArtifactURI(MavenProject project, String type, String classifier)
   {
      final StringBuilder sb = new StringBuilder();
      sb.append(project.getGroupId());
      sb.append("/");
      sb.append(project.getArtifactId());
      sb.append("/");
      sb.append(type);
      if (classifier != null && classifier.length() > 0)
      {
         sb.append("/");
         sb.append(classifier);
      }
      sb.append("/");
      sb.append(project.getVersion());
      return URI.createURI("gav:/" + sb.toString());
   }


   public static B2MavenBridge get(MavenSession mavenSession)
   {
      return Adapters.adapt(new AbstractAdapterFactory()
      {
         @Override
         protected <A> A newAdapter(Object adaptable, Class<A> adapterType)
         {
            return (A) new B2MavenBridge((MavenSession) adaptable);
         }
      }, mavenSession, B2MavenBridge.class);
   }

   public static B2MavenBridge get(MavenSession mavenSession, final ResourceSet resourceSet)
   {
      return Adapters.adapt(new AbstractAdapterFactory()
      {
         @Override
         protected <A> A newAdapter(Object adaptable, Class<A> adapterType)
         {
            return (A) new B2MavenBridge((MavenSession) adaptable, resourceSet);
         }
      }, mavenSession, B2MavenBridge.class);
   }

   private void connect(MavenSession mavenSession, ResourceSet resourceSet)
   {
      final Map<File, Project> dirToProjectMap = new HashMap<File, Project>();
      for (MavenProject mavenProject : mavenSession.getProjects())
      {
         if (isModuleProject(resourceSet, mavenProject))
         {
            if (getContextValue(mavenProject, AbstractModule.class) != null)
            {
               throw new IllegalStateException("b2 maven bridge already connected");
            }

            final URI uri = B2MavenBridge.toArtifactURI(mavenProject, "module", null);
            final AbstractModule module = (AbstractModule) resourceSet.getResource(uri, true).getContents().get(0);

            mavenProject.setContextValue(AbstractModule.class.getName(), module);

            for (ProjectFacet<Project> projectFacet : module.getFacets(ProjectFacet.class))
            {
               for (Project project : projectFacet.getProjects())
               {
                  dirToProjectMap.put(project.getDirectory(), project);
               }
            }

            final File moduleDir = module.getDirectory();
            final File fileFlagsFile = new File(moduleDir, ".b2/moduleDirectory.properties");

            final ModuleDirectory moduleDirectory;
            if (fileFlagsFile.exists())
            {
               moduleDirectory = ModuleDirectory.load(moduleDir, fileFlagsFile);
            }
            else
            {
               moduleDirectory = new ModuleDirectory(moduleDir, Collections.<File, Integer> emptyMap());
            }

            mavenProject.setContextValue(ModuleDirectory.class.getName(), moduleDirectory);
         }
      }

      for (MavenProject mavenProject : mavenSession.getProjects())
      {
         final Project project = dirToProjectMap.get(mavenProject.getBasedir());
         if (project != null)
         {
            mavenProject.setContextValue(Project.class.getName(), project);
         }
      }
   }

   static boolean isModuleProject(ResourceSet resourceSet, MavenProject mavenProject)
   {
      Resource resource;
      try
      {
         final URI uri = toArtifactURI(mavenProject, "module", null);
         resource = resourceSet.getResource(uri, true);
      }
      catch (RuntimeException e)
      {
         resource = null;
      }
      return resource != null && !resource.getContents().isEmpty();
   }

   @SuppressWarnings("unchecked")
   private static <T> T getContextValue(MavenProject mavenProject, Class<T> type)
   {
      return (T) mavenProject.getContextValue(type.getName());
   }

   public void disconnect(MavenSession mavenSession)
   {
      for (MavenProject mavenProject : mavenSession.getProjects())
      {
         mavenProject.setContextValue(AbstractModule.class.getName(), null);
         mavenProject.setContextValue(Project.class.getName(), null);
      }
   }

   public ModuleDirectory getModuleDirectory(MavenProject mavenProject)
   {
      return getContextValue(mavenProject, ModuleDirectory.class);
   }

   public AbstractModule getModule(MavenProject mavenProject)
   {
      return getContextValue(mavenProject, AbstractModule.class);
   }

   public Project getEclipseProject(MavenProject mavenProject)
   {
      return getContextValue(mavenProject, Project.class);
   }
}
