/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

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
import org.sourcepit.common.maven.testing.ChainedExecutionListener;
import org.sourcepit.common.utils.adapt.AbstractAdapterFactory;
import org.sourcepit.common.utils.adapt.Adapters;
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

   private void afterProjectStarted(MavenSession mavenSession, MavenProject mavenProject)
   {
      Adapters.adapt(new AbstractAdapterFactory()
      {
         @Override
         @SuppressWarnings("unchecked")
         protected <A> A newAdapter(Object adaptable, Class<A> adapterType)
         {
            return (A) adaptB2((MavenSession) adaptable);
         }
      }, mavenSession, ResourceSet.class);
   }

   private ResourceSet adaptB2(MavenSession mavenSession)
   {
      final ResourceSet resourceSet = createResourceSet();
      for (MavenProject mavenProject : mavenSession.getProjects())
      {
         if (isModuleDir(mavenProject.getBasedir()))
         {
            setUriMappings(mavenProject.getBasedir(), resourceSet);
         }
      }
      b2Bridge.connect(mavenSession, resourceSet);
      return resourceSet;
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
}
