/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.maven.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.Project;
import org.sourcepit.b2.model.module.ProjectFacet;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.common.utils.adapt.AbstractAdapterFactory;
import org.sourcepit.common.utils.adapt.Adapters;

@Component(role = B2MavenBridge.class)
public class B2MavenBridge
{
   private static final String CTX_KEY_ADAPTER_LIST = B2MavenBridge.class.getCanonicalName() + "#adapterList";

   public void connect(MavenSession mavenSession, final B2Session b2Session)
   {
      if (!adapt(mavenSession, b2Session))
      {
         throw new IllegalStateException("Maven session is already connected with a b2 session");
      }

      final Map<File, Project> dirToProjectMap = new HashMap<File, Project>();
      for (ModuleProject moduleProject : b2Session.getProjects())
      {
         final AbstractModule module = moduleProject.getModuleModel();
         for (ProjectFacet<Project> projectFacet : module.getFacets(ProjectFacet.class))
         {
            for (Project project : projectFacet.getProjects())
            {
               dirToProjectMap.put(project.getDirectory(), project);
            }
         }
      }

      for (MavenProject mavenProject : mavenSession.getProjects())
      {
         final List<Object> adapterList = getAdapterList(mavenProject, true);

         final File basedir = mavenProject.getBasedir();
         for (ModuleProject moduleProject : b2Session.getProjects())
         {
            if (basedir.equals(moduleProject.getDirectory()))
            {
               addAdapterUnique(adapterList, ModuleProject.class, moduleProject);
               addAdapterUnique(adapterList, AbstractModule.class, moduleProject.getModuleModel());
               Adapters.addAdapter(moduleProject, mavenProject);
               Adapters.addAdapter(moduleProject.getModuleModel(), mavenProject);
               break;
            }

            final Project p = dirToProjectMap.get(basedir);
            if (p != null)
            {
               addAdapterUnique(adapterList, Project.class, p);
               Adapters.addAdapter(p, mavenProject);
               break;
            }
         }
      }
   }

   private static boolean adapt(MavenSession mavenSession, final B2Session b2Session)
   {
      final boolean[] result = new boolean[1];
      final AbstractAdapterFactory adapterFactory = new AbstractAdapterFactory()
      {
         @Override
         @SuppressWarnings("unchecked")
         protected <A> A newAdapter(Object adaptable, Class<A> adapterType)
         {
            result[0] = true;
            return (A) b2Session;
         }
      };
      Adapters.adapt(adapterFactory, mavenSession, B2Session.class);
      if (result[0])
      {
         Adapters.addAdapter(b2Session, mavenSession);
      }
      return result[0];
   }

   private static <A> void addAdapterUnique(List<Object> adapterList, Class<? super A> adapterType, A adapter)
   {
      if (Adapters.findAdapter(adapterList, adapterType) != null)
      {
         throw new IllegalStateException("Adapter for type " + adapterType.getName() + " already registered");
      }
      adapterList.add(adapter);
   }

   private static List<Object> getAdapterList(MavenProject project, boolean createOnDemand)
   {
      synchronized (project)
      {
         @SuppressWarnings("unchecked")
         List<Object> adapterList = (List<Object>) project.getContextValue(CTX_KEY_ADAPTER_LIST);
         if (adapterList == null && createOnDemand)
         {
            adapterList = new ArrayList<Object>();
            project.setContextValue(CTX_KEY_ADAPTER_LIST, adapterList);
         }
         return adapterList;
      }
   }

   public B2Session getB2Session(MavenSession mavenSession)
   {
      return Adapters.getAdapter(mavenSession, B2Session.class);
   }

   public MavenSession getMavenSession(B2Session b2Session)
   {
      return Adapters.getAdapter(b2Session, MavenSession.class);
   }

   public ModuleProject findContainingModuleProject(MavenProject mavenProject)
   {
      ModuleProject moduleProject = null;
      MavenProject parentProject = mavenProject;
      while (parentProject != null && moduleProject == null)
      {
         moduleProject = getModuleProject(parentProject);
         parentProject = parentProject.getParent();
      }
      return moduleProject;
   }

   public ModuleProject getModuleProject(MavenProject mavenProject)
   {
      return findAdapter(mavenProject, ModuleProject.class);
   }

   public MavenProject getMavenProject(ModuleProject moduleProject)
   {
      return Adapters.getAdapter(moduleProject, MavenProject.class);
   }

   public MavenProject getMavenProject(AbstractModule module)
   {
      return Adapters.getAdapter(module, MavenProject.class);
   }

   public MavenProject getMavenProject(Project eclipseProject)
   {
      return Adapters.getAdapter(eclipseProject, MavenProject.class);
   }

   public AbstractModule getModule(MavenProject mavenProject)
   {
      return findAdapter(mavenProject, AbstractModule.class);
   }

   public Project getEclipseProject(MavenProject mavenProject)
   {
      return findAdapter(mavenProject, Project.class);
   }

   private static <A> A findAdapter(MavenProject mavenProject, Class<A> adapterType)
   {
      final List<Object> adapterList = getAdapterList(mavenProject, false);
      return adapterList == null ? null : Adapters.findAdapter(adapterList, adapterType);
   }
}
