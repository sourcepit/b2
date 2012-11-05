/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.util.Map.Entry;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.ExecutionEvent;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sourcepit.b2.maven.core.B2MavenBridge;
import org.sourcepit.b2.model.module.ModuleModelPackage;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.SessionModelPackage;
import org.sourcepit.common.maven.testing.ChainedExecutionListener;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

@Component(role = AbstractMavenLifecycleParticipant.class)
public class B2MavenLifecycleParticipant extends AbstractMavenLifecycleParticipant
{
   @Requirement
   private B2MavenBridge b2Bridge;

   @Override
   public void afterProjectsRead(MavenSession mavenSession) throws MavenExecutionException
   {
      super.afterProjectsRead(mavenSession);
      ChainedExecutionListener listener = new ChainedExecutionListener(mavenSession.getRequest().getExecutionListener())
      {
         @Override
         public void projectStarted(ExecutionEvent event)
         {
            super.projectStarted(event);
            afterProjectStarted(event.getSession(), event.getProject());
         }
      };
      mavenSession.getRequest().setExecutionListener(listener);
   }

   private File currentModuleDir;

   private B2Session currentSession;

   private void afterProjectStarted(MavenSession mavenSession, MavenProject mavenProject)
   {
      final File moduleDir = getModuleDir(mavenSession, mavenProject);
      if (!moduleDir.equals(currentModuleDir))
      {
         if (currentSession != null)
         {
            b2Bridge.disconnect(mavenSession, currentSession);
         }

         currentSession = loadB2Session(moduleDir);
         currentModuleDir = moduleDir;

         b2Bridge.connect(mavenSession, currentSession);
      }
   }

   private File getModuleDir(MavenSession mavenSession, MavenProject mavenProject)
   {
      final File moduleDir = findModuleDir(mavenProject.getBasedir());
      checkState(moduleDir != null, "Project '" + mavenProject.toString() + "' is not contained in any module.");

      MavenProject moduleProject = null;
      for (MavenProject project : mavenSession.getProjects())
      {
         if (moduleDir.equals(project.getBasedir()))
         {
            moduleProject = project;
            break;
         }
      }

      checkState(moduleProject != null, "Project '" + mavenProject.toString() + "' is not contained in any module.");
      return moduleDir;
   }

   private B2Session loadB2Session(final File moduleDir)
   {
      ResourceSet resourceSet = createResourceSet();
      resourceSet.getPackageRegistry().put(ModuleModelPackage.eNS_URI, ModuleModelPackage.eINSTANCE);
      resourceSet.getPackageRegistry().put(SessionModelPackage.eNS_URI, SessionModelPackage.eINSTANCE);

      PropertiesMap uriMap = new LinkedPropertiesMap();
      uriMap.load(new File(moduleDir, ".b2/uriMap.properties"));

      for (Entry<String, String> entry : uriMap.entrySet())
      {
         URI key = URI.createURI(entry.getKey());
         URI value = URI.createURI(entry.getValue());
         resourceSet.getURIConverter().getURIMap().put(key, value);
      }

      URI sessionUri = URI.createFileURI(new File(moduleDir, ".b2/b2.session").getAbsolutePath());

      return (B2Session) resourceSet.getResource(sessionUri, true).getContents().get(0);
   }

   private static ResourceSet createResourceSet()
   {
      final ResourceSet resourceSet = new ResourceSetImpl();
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("gav", new XMIResourceFactoryImpl());
      resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put("file", new XMIResourceFactoryImpl());
      return resourceSet;
   }

   private static File findModuleDir(File dir)
   {
      if (new File(dir, "module.xml").exists())
      {
         return dir;
      }
      final File parentDir = dir.getParentFile();
      return parentDir == null ? null : findModuleDir(parentDir);
   }
}
